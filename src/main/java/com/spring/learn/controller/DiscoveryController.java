package com.spring.learn.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author J.zhu
 * @date 2019/6/6
 */
@Controller
@RequestMapping("discovery")
@Slf4j
public class DiscoveryController {

    @NacosInjected
    private NamingService namingService;

    @GetMapping(value = "/get/{serviceName}")
    @ResponseBody
    public List<Instance> get(@PathVariable String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }
}
