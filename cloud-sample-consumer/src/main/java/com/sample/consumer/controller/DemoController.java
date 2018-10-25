package com.sample.consumer.controller;

import com.sample.consumer.client.DcFeignClient;
import com.sample.consumer.client.RibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @ClassName DemoController
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/10/24 14:05
 */
@RestController
public class DemoController {

    @Autowired
    private RibbonClient ribbonClient;

    @Autowired
    private DcFeignClient feignClient;

    @GetMapping("/serviceUrl")
    public String serviceUrl(){
        return ribbonClient.getServiceUrl();
    }

    @GetMapping("/dc")
    public String feign(){
        return feignClient.consumer();
    }
}
