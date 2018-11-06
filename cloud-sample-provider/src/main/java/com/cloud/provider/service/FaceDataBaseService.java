package com.cloud.provider.service;

import com.cloud.provider.bean.FaceSetInfo;

public interface FaceDataBaseService {

    /**
     * 创建人脸库
     * @param faceSetInfo
     * @throws Exception
     */
    boolean createFaceDataBase(FaceSetInfo faceSetInfo) throws Exception;

    /**
     * 读取指定项目下所有的人脸库
     * @throws Exception
     */
    void readAllFaceDataBase() throws Exception;


    /**
     * 查询指定的人脸库
     * @throws Exception
     */
    void readFaceDataBase() throws Exception;


    /**
     * 删除人脸库
     * @throws Exception
     */
    boolean deleteFaceDataBase() throws Exception;
}
