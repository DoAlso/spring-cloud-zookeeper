package com.cloud.provider.service.impl;

import com.cloud.provider.bean.FaceSet;
import com.cloud.provider.bean.FaceSetInfo;
import com.cloud.provider.configuration.FaceConfiguration;
import com.cloud.provider.dao.FaceSetMapper;
import com.cloud.provider.face.FaceHttpClient;
import com.cloud.provider.service.FaceDataBaseService;
import com.cloud.provider.utils.ConstantUtil;
import com.cloud.provider.utils.FastJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FaceDataBaseServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/9 14:04
 */
@Service
public class FaceDataBaseServiceImpl implements FaceDataBaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FaceDataBaseServiceImpl.class);

    @Resource
    private FaceSetMapper faceSetMapper;
    @Autowired
    private FaceConfiguration faceProperties;

    @Override
    public boolean createFaceDataBase(FaceSetInfo faceSetInfo) throws Exception {
        Map<String,Object> params = new HashMap<>(2);
        params.put("face_set_name",faceSetInfo.getFace_set_name());
        params.put("face_set_capacity",faceSetInfo.getFace_set_capacity());
        params.put("external_fields", FastJsonUtil.textToJson(FastJsonUtil.MapToString(faceSetInfo.getExternal_fields())));
        LOGGER.info("Request Params : {}",FastJsonUtil.toJSONString(params));
        String result = FaceHttpClient.post(faceProperties.getServiceName(),faceProperties.getRegion(),
                faceProperties.getAccessKey(),faceProperties.getAccessKey(),
                ConstantUtil.getFaceUrl(faceProperties,ConstantUtil.FACE_SETS),FastJsonUtil.toJSONString(params));
        LOGGER.info("Result is : {}",result);
        FaceSet faceSet = new FaceSet();
        faceSet.setCreateDate(new Date(System.currentTimeMillis()));
        faceSet.setFaceSetName(faceSetInfo.getFace_set_name());
        faceSet.setFaceSetCapacity(faceSetInfo.getFace_set_capacity());
        faceSet.setFaceSetType((byte)1);
        faceSet.setFaceSetId("");
        faceSet.setProjectId((Long) faceSetInfo.getExternal_fields().get("projectId"));
        faceSetMapper.insertSelective(faceSet);
        return false;
    }

    @Override
    public void readAllFaceDataBase() throws Exception {

    }

    @Override
    public void readFaceDataBase() throws Exception {

    }

    @Override
    public boolean deleteFaceDataBaseById(Long id) throws Exception {
        //TODO 根据ID查询对应的人脸库名称
        LOGGER.info("Request Url : {}", ConstantUtil.getFaceUrl(faceProperties,ConstantUtil.FACE_SETS,"hoolink"));
        String result = FaceHttpClient.delete(faceProperties.getServiceName(),faceProperties.getRegion(),
                faceProperties.getAccessKey(),faceProperties.getSecretKey(),
                ConstantUtil.getFaceUrl(faceProperties,ConstantUtil.FACE_SETS,"hoolink"));
        LOGGER.info("Result is : {}",result);
        return false;
    }
}
