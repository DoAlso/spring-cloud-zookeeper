package com.cloud.provider.utils;

import com.cloud.provider.configuration.FaceConfiguration;

/**
 * @ClassName ConstantUtil
 * @Description TODO
 * @Author huyaxi
 * @DATE 2018/11/6 14:37
 */
public class ConstantUtil {
    private static final String PROTOCOL = "https://";
    private static final String DELIMITER = "/";

    public static final String FACE_DETECT = "face-detect";
    public static final String FACE_SETS = "face-sets";

    public static String getFaceUrl(FaceConfiguration faceProperties,String... args){
        StringBuffer httpUrl = new StringBuffer(PROTOCOL)
                .append(faceProperties.getEndPoint())
                .append(DELIMITER)
                .append(faceProperties.getApiVersion())
                .append(DELIMITER)
                .append(faceProperties.getProjectId())
                .append(DELIMITER);
        for (int i = 0; i < args.length; i++){
            if(i == (args.length - 1)){
                httpUrl.append(args[i]);
            }else {
                httpUrl.append(args[i]).append(DELIMITER);
            }
        }
        return httpUrl.toString();
    }
}
