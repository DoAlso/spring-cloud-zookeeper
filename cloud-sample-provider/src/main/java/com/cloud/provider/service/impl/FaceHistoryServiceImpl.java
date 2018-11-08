package com.cloud.provider.service.impl;

import com.cloud.provider.bean.Face;
import com.cloud.provider.bean.FaceCaptureHistory;
import com.cloud.provider.bean.FaceCaptured;
import com.cloud.provider.bean.Faces;
import com.cloud.provider.configuration.FaceConfiguration;
import com.cloud.provider.dao.FaceCaptureHistoryMapper;
import com.cloud.provider.face.FaceHttpClient;
import com.cloud.provider.service.FaceHistoryService;
import com.cloud.provider.utils.ConstantUtil;
import com.cloud.provider.utils.FastJsonUtil;
import com.cloud.provider.utils.FileTools;
import com.huaweicloud.dis.iface.data.response.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FaceHistoryServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/7 14:31
 */
@Service
public class FaceHistoryServiceImpl implements FaceHistoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FaceHistoryServiceImpl.class);
    private static final String FILE_PATH = "E:\\Picture";
    @Resource
    private FaceCaptureHistoryMapper faceCaptureHistoryMapper;
    @Autowired
    private FaceConfiguration faceProperties;

    @Override
    public void createFaceCaptureHis(Record record) throws Exception {
        LOGGER.info("Get record [{}], partitionKey [{}], sequenceNumber [{}],threadName [{}].",
                new String(record.getData().array()),
                record.getPartitionKey(),
                record.getSequenceNumber(),
                Thread.currentThread().getName());
        FaceCaptured faceCaptured = FastJsonUtil.toBean(new String(record.getData().array()), FaceCaptured.class);
        String fileName = new StringBuffer(String.valueOf(faceCaptured.getTime())).append(".jpeg").toString();
        FileTools.base64ToImage(FILE_PATH,fileName,faceCaptured.getImage_data(),false);
        // TODO 将抓拍的数据上传到OBS并将返回的信息存入本地库进行关联
        //获取人脸标签信息
        Map<String,Object> params = new HashMap<>(2);
        params.put("image_base64",faceCaptured.getImage_data());
        params.put("attributes","0,1,2,3,4,5");
        LOGGER.info("Request url is : {}", ConstantUtil.getFaceUrl(faceProperties,ConstantUtil.FACE_DETECT));
        LOGGER.info("Request params is : {}",FastJsonUtil.toJSONString(params));
        String result = FaceHttpClient.post(faceProperties.getServiceName(),faceProperties.getRegion(),
                faceProperties.getAccessKey(),faceProperties.getSecretKey(),
                ConstantUtil.getFaceUrl(faceProperties,ConstantUtil.FACE_DETECT),FastJsonUtil.toJSONString(params));
        LOGGER.info("result is : {}",result);
        //TODO 抓怕历史记录入库
//        Face face = FastJsonUtil.toBean(result,Faces.class).getFaces().get(0);
//        FaceCaptureHistory captureHistory = new FaceCaptureHistory();
//        faceCaptureHistoryMapper.insertSelective(captureHistory);
    }
}
