package com.spring.learn.controller;

import com.spring.learn.common.entity.Result;
import com.spring.learn.common.zookeeper.CuratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author J.zhu
 * @date 2019/6/20
 */
@RequestMapping("/zk")
@RestController
@Slf4j
public class ZookeeperController {

    @GetMapping("/lock")
    @ResponseBody
    public Result lock() {
        CuratorUtil.main(null);
        return Result.newSuccessResult("zk lock");
    }
}
