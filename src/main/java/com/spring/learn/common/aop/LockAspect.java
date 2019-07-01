package com.spring.learn.common.aop;

import com.spring.learn.common.annotation.LockKey;
import com.spring.learn.common.entity.Result;
import com.spring.learn.common.exception.CommonBizException;
import com.spring.learn.common.exception.ExpCodeEnum;
import com.spring.learn.common.redis.util.RedissLockUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Scope
@Aspect
@Order(1)
/**
 * 同步锁 AOP
 * 创建者	张志朋
 * 创建时间	2015年6月3日
 * @author Administrator
 * @author Administrator
 * @transaction 中  order 大小的说明
 * https://docs.spring.io/spring/docs/4.3.14.RELEASE/spring-framework-reference/htmlsingle/#transaction-declarative-annotations
 * https://docs.spring.io/spring/docs/4.3.14.RELEASE/javadoc-api/
 * order越小越是最先执行，但更重要的是最先执行的最后结束。order默认值是2147483647
 */
public class LockAspect {
	/**
     * 思考：为什么不用synchronized
     * service 默认是单例的，并发下lock只有一个实例
     * 互斥锁 参数默认false，不公平锁
     */
	private static  Lock lock = new ReentrantLock(true);

    /**
     * Service层切点     用于记录错误日志
     */
	@Pointcut("@annotation(com.spring.learn.common.annotation.Servicelock)")
	public void lockAspect() {
		
	}
	
    @Around("lockAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
    	lock.lock();
    	Object obj = null;
		try {
			obj = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();       
		} finally{
			lock.unlock();
		}
    	return obj;
    }

    /**
     * Service层切点     用于记录错误日志
     */
	@Pointcut("@annotation(com.spring.learn.common.annotation.RedisLock)")
	public void redisLockAspect() {

	}

	@Around("redisLockAspect()")
	public  Object redisLockAround(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        Long seckillId = getLockKey(joinPoint);
        if(seckillId != null){
            boolean res=false;
            try {
                res = RedissLockUtil.tryLock(seckillId+"", TimeUnit.SECONDS, 3, 20);
                if(res){
                    obj = joinPoint.proceed();
                }else{
                    return Result.newFailureResult(new CommonBizException(ExpCodeEnum.SECKILL_MUCH));
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException();
            } finally{
                if(res){
                    //释放锁
                    RedissLockUtil.unlock(seckillId+"");
                }
            }
        }
        return obj;
	}

    /**
     * 获取方法上加了{@link LockKey}注解的为lockKey
     * @param joinPoint
     * @return
     */
    private Long getLockKey(ProceedingJoinPoint joinPoint) {
	    Long lockKey = null;
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation: parameterAnnotations) {
            int paramIndex= ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
            for (Annotation annotation: parameterAnnotation) {
                if (annotation instanceof LockKey){
                    lockKey = (Long)args[paramIndex];
                }
            }
        }
        return lockKey;
    }

}
