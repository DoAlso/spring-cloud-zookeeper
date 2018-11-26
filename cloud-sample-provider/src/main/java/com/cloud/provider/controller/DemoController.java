package com.cloud.provider.controller;

import com.cloud.provider.bean.FaceSetInfo;
import com.cloud.provider.service.FaceDataBaseService;
import com.cloud.provider.service.FringeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/10/24 14:05
 */
@RestController
public class DemoController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private FaceDataBaseService faceDataBaseService;

    @Autowired
    private FringeNodeService fringeNodeService;


    @GetMapping("/serviceUrl")
    public String serviceUrl(){
        List<ServiceInstance> list = discoveryClient.getInstances("sample-consumer");
        if (list != null && list.size() > 0 ) {
            return list.get(0).getUri().toString();
        }
        return null;
    }

    @GetMapping("/dc")
    public String dc(){
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

    @PostMapping("/createFaceSets")
    public String createFaceDataBase(@RequestBody FaceSetInfo faceSetInfo) throws Exception{
        return faceDataBaseService.createFaceDataBase(faceSetInfo);
    }

    @PostMapping("/createFringeDevice")
    public String createFringeDevice(){
        fringeNodeService.createFringeDevice();
        return "SUCCESS";
    }
}
