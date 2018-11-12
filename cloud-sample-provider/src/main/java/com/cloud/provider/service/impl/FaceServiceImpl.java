package com.cloud.provider.service.impl;

import com.cloud.provider.bean.FaceCreateParam;
import com.cloud.provider.configuration.FaceConfiguration;
import com.cloud.provider.face.FaceHttpClient;
import com.cloud.provider.service.FaceService;
import com.cloud.provider.utils.ConstantUtil;
import com.cloud.provider.utils.FastJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FaceServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/12 13:28
 */
@Service
public class FaceServiceImpl implements FaceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FaceServiceImpl.class);

    @Autowired
    private FaceConfiguration faceProperties;

    @Override
    public boolean createFace(FaceCreateParam faceCreateParam) {
        Map<String,Object> params = new HashMap<>(2);
        params.put("image_base64",faceCreateParam.getImage_base64());
        params.put("external_fields",faceCreateParam.getExternal_fields());
        String result = FaceHttpClient.post(faceProperties.getServiceName(),faceProperties.getRegion(),
                faceProperties.getAccessKey(),faceProperties.getSecretKey(),
                ConstantUtil.getFaceUrl(faceProperties,ConstantUtil.FaceApi.FACE_SETS,faceCreateParam.getFace_set_name(),ConstantUtil.FaceApi.FACES),
                FastJsonUtil.toJSONString(params));

        LOGGER.info("Result is : {}",result);
        return true;
    }

}
