package com.cloud.provider.dis;

import com.cloud.provider.service.FaceHistoryService;
import com.huaweicloud.dis.iface.data.response.Record;
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
    private FaceHistoryService faceHistoryService;

    @Override
    public void collection(Record record) throws Exception{
        faceHistoryService.createFaceCaptureHis(record);
    }
}
