package com.cloud.provider.service.impl;

import com.cloud.provider.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/1 11:42
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public void doInsert() {
        System.out.println("执行OBS相关的插入操作");
    }
}
