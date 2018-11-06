package com.cloud.provider.utils;

import com.cloud.provider.configuration.FaceConfiguration;

/**
 * @ClassName ConstantUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/6 14:37
 */
public class ConstantUtil {
    private static final String PROTOCOL = "https://";
    private static final String DELIMITER = "/";

    public static final String FACE_DETECT = "face-detect";

    public static String getFaceUrl(FaceConfiguration faceProperties,String api){
        StringBuilder httpUrl = new StringBuilder(PROTOCOL)
                .append(faceProperties.getEndPoint())
                .append(DELIMITER)
                .append(faceProperties.getApiVersion())
                .append(DELIMITER)
                .append(faceProperties.getProjectId())
                .append(DELIMITER)
                .append(api);
        return httpUrl.toString();
    }
}
