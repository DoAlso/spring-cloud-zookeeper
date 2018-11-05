package com.cloud.provider.dis;

import com.cloud.provider.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FaceDataCollector
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/5 13:08
 */
@Service
public class FaceDataCollector implements DISStreamCollector {

    @Autowired
    private TestService testService;

    @Override
    public void collection(Object object) {
        testService.doInsert(object);
    }
}
