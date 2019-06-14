package com.spring.learn.common.exception;

import java.io.Serializable;

import static com.spring.learn.common.utils.ExpPrefixUtil.*;

/**
 * 全局的异常状态码 和 异常描述
 *
 * PS:异常码一共由5位组成，前两位为固定前缀，请参考{@link com.spring.learn.common.utils.ExpPrefixUtil}
 */
@SuppressWarnings("all")
public enum ExpCodeEnum implements Serializable {

    /** 通用异常 */
    UNKNOW_ERROR(ComExpPrefix + "000", "未知异常"),
    ERROR_404(ComExpPrefix + "001", "没有该接口"),
    PARAM_NULL(ComExpPrefix + "002", "参数为空"),
    PARAM_ERROR(ComExpPrefix + "003", "参数错误"),
    NO_REPEAT(ComExpPrefix + "004", "请勿重复提交"),
    SESSION_NULL(ComExpPrefix + "005", "请求头中SessionId不存在"),
    HTTP_REQ_METHOD_ERROR(ComExpPrefix + "006", "HTTP请求方法不正确"),
    JSONERROR(ComExpPrefix + "007", "JSON解析异常"),
    UPLOAD_FAILED(ComExpPrefix + "008","上传文件失败"),
    EMAIL_FORMAT_INCORRECT(ComExpPrefix + "009","邮箱格式有误！"),

    /** User模块异常 */
    USERNAME_NULL(UserExpPrefix + "000", "用户名为空"),
    PASSWD_NULL(UserExpPrefix + "001", "密码为空"),
    AUTH_NULL(UserExpPrefix + "002", "手机、电子邮件、用户名 至少填一个"),
    LOGIN_FAIL(UserExpPrefix + "003", "登录失败"),
    UNLOGIN(UserExpPrefix + "004", "尚未登录"),
    NO_PERMISSION(UserExpPrefix + "005", "没有权限"),
    PHONE_NULL(UserExpPrefix + "006", "手机号为空"),
    MAIL_NULL(UserExpPrefix + "007", "电子邮件为空"),
    USERTYPE_NULL(UserExpPrefix + "008", "用户类别为空"),
    ROLE_NULL(UserExpPrefix + "009", "角色为空"),
    ROLEID_NULL(UserExpPrefix + "010", "roleId为空"),
    MENUIDLIST_NULL(UserExpPrefix + "011", "menuIdList为空"),
    PERMISSIONIDLIST_NULL(UserExpPrefix + "012", "permissionIdList为空"),
    NAME_NULL(UserExpPrefix + "013", "name为空"),

    /** Product模块异常 */
    PRODUCT_NAME_NULL(ProdExpPrefix + "000", "产品名称为空"),

    /** Order模块异常 */
    PROCESSREQ_ORDERID_NULL(OrderExpPrefix + "000", "受理请求中的orderId为空"),
    ORDER_NOT_EXIST(OrderExpPrefix + "001", "OrderId未查询到订单信息"),

    /** Analysis模块异常 */
    XXXX_NULL(AnlsExpPrefix + "000", "XXXX异常"),

    /** Common模块异常*/


    /** Seckill模块异常*/
    SECKILL_MUCH(SeckillExpPrefix + "000","哎呦喂，人也太多了，请稍后！"),
    SECKILL_SUCCESS(SeckillExpPrefix + "001","秒杀成功"),
    SECKILL_END(SeckillExpPrefix + "002","秒杀结束"),
    SECKILL_REPEAT_KILL(SeckillExpPrefix + "003","重复秒杀"),
    SECKILL_INNER_ERROR(SeckillExpPrefix + "004","系统异常"),
    SECKILL_DATE_REWRITE(SeckillExpPrefix + "005","数据篡改"),

    END(OrderExpPrefix + "XXX", "XXX");




    private String code;
    private String message;

    ExpCodeEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
