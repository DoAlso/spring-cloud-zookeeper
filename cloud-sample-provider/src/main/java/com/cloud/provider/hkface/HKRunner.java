package com.cloud.provider.hkface;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName HKRunner
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/15 11:34
 */
@Component
public class HKRunner implements ApplicationRunner {

    @Resource
    private LibrarySource librarySource;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //TODO 查询出所有的NVR设备执行登录和布防
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,5,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        for(int i=0;i<1;i++){
            executor.execute(()->{
                librarySource.login("iDS-9616NX-I16/FA1620180517CCRRC22954263WCVU","192.168.1.175","22000","admin","hoolink@123");
                librarySource.checkFaceFaceCapabilities("iDS-9616NX-I16/FA1620180517CCRRC22954263WCVU");
                librarySource.setUpAlarmChan("iDS-9616NX-I16/FA1620180517CCRRC22954263WCVU");
            });
        }
    }
}
