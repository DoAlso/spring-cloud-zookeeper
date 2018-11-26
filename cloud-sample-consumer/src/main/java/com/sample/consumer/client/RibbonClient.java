package com.sample.consumer.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RibbonClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/10/24 14:18
 */
@Component
public class RibbonClient {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public String getServiceUrl(){
        return restTemplate.getForObject("face://sample-provider/serviceUrl",String.class);
    }

    public String fallback(){
        return "error";
    }
}
