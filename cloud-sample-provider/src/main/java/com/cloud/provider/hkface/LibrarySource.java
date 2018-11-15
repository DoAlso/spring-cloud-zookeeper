package com.cloud.provider.hkface;

import java.io.InputStream;

/**
 * @ClassName LibrarySource
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/14 11:22
 */
public interface LibrarySource {

    /**
     * NVR 设备登录
     * @param serial
     * @param ip
     * @param port
     * @param username
     * @param password
     */
    void login(String serial, String ip, String port, String username, String password);


    /**
     * 检NVR设备是否具有人脸能力
     * @param arg
     * @return
     */
    boolean checkFaceFaceCapabilities(String arg);


    /**
     * 创建NVR设备人脸库
     * @param xmlParam
     * @param serial
     */
    void createFaceDataBase(String xmlParam,String serial);


    /**
     * 修改人脸库信息
     * @param faceId
     * @param faceDataBaseName
     * @param serial
     * @return
     */
    boolean modifyFaceDataBase(String faceId,String faceDataBaseName,String serial);


    /**
     * 上传人脸数据
     * @param serial
     * @param faceDataBaseId
     * @param inputStream
     * @param name
     */
    void uploadFile(String serial, String faceDataBaseId, InputStream inputStream, String name);


    /**
     * 删除指定设备的人脸数据
     * @param faceDataBaseId
     * @param faceId
     * @param serial
     */
    void deleteFaceData(String faceDataBaseId,String faceId,String serial);


    /**
     * 设置布防
     * @param serial
     */
    void setUpAlarmChan(String serial);


    /**
     *
     * @param serial
     */
    void CloseAlarmChan(String serial);
}
