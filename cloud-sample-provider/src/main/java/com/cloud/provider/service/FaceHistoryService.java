package com.cloud.provider.service;

import com.huaweicloud.dis.iface.data.response.Record;

/**
 * @author huyaxi
 * 人脸抓怕历史接口
 */
public interface FaceHistoryService {

    /**
     * 记录人脸抓拍历史
     * @param record
     * @throws Exception
     */
    void createFaceCaptureHis(Record record) throws Exception;
}
