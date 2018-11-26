package com.cloud.provider.service;

import com.cloud.provider.bean.FaceCreateParam;

/**
 * 人脸数据操作接口
 * @author huyaxi
 */
public interface FaceService {

    /**
     * 上传人脸接口
     * @param faceCreateParam
     * @return
     */
    boolean createFace(FaceCreateParam faceCreateParam);
}
