package com.cloud.provider.service.impl;

import com.cloud.provider.bean.Face;
import com.cloud.provider.configuration.FaceConfiguration;
import com.cloud.provider.face.FaceHttpClient;
import com.cloud.provider.service.TestService;
import com.cloud.provider.utils.ConstantUtil;
import com.cloud.provider.utils.FastJsonUtil;
import com.huaweicloud.dis.iface.data.response.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/1 11:42
 */
@Service
public class TestServiceImpl implements TestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);
    @Autowired
    private FaceConfiguration faceProperties;

    @Override
    public void doInsert(Record record) {
        LOGGER.info("Get record [{}], partitionKey [{}], sequenceNumber [{}],threadName [{}].",
                new String(record.getData().array()),
                record.getPartitionKey(),
                record.getSequenceNumber(),
                Thread.currentThread().getName());
        Face face = FastJsonUtil.toBean(new String(record.getData().array()),Face.class);
        //获取人脸标签信息
        Map<String,Object> params = new HashMap<>(2);
        params.put("image_base64",face.getImage_data());
        params.put("attributes","0,1,2,3,4,5");
        LOGGER.info("Request url is : {}",ConstantUtil.getFaceUrl(faceProperties,ConstantUtil.FACE_DETECT));
        LOGGER.info("Request params is : {}",FastJsonUtil.toJSONString(params));
        String result = FaceHttpClient.post(faceProperties.getServiceName(),faceProperties.getRegion(),
                faceProperties.getAccessKey(),faceProperties.getSecretKey(),
                ConstantUtil.getFaceUrl(faceProperties,ConstantUtil.FACE_DETECT),FastJsonUtil.toJSONString(params));
        LOGGER.info("result is : {}",result);
        // 将抓拍的数据上传到OBS并将返回的信息存入本地库进行关联

    }
}
