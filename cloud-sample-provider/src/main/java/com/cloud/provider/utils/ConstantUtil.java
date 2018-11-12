package com.cloud.provider.utils;

import com.cloud.provider.configuration.FaceConfiguration;

/**
 * @ClassName ConstantUtil
 * @Description TODO
 * @Author huyaxi
 * @DATE 2018/11/6 14:37
 */
public class ConstantUtil {

    /**
     * 通用的键值
     */
    public static final class CommomKey {
        private static final String PROTOCOL = "https://";
        private static final String DELIMITER = "/";
        public static final String CURRENT_USER_TOKEN = "X-Token";
        public static final Integer SQL_ERROR = 0;
    }

    public static final class FaceApi {
        public static final String FACE_DETECT = "face-detect";
        public static final String FACE_SETS = "face-sets";
        public static final String FACES = "faces";
        public static final String SEARCH = "search";
    }

    public static class Swagger {
        public static final String TITLE = "卡孚云物联平台";
        public static final String DESCRIPTION = "本文档提供卡孚云物联平台前端调用接口说明";
    }

    public static String getFaceUrl(FaceConfiguration faceProperties,String... args){
        StringBuffer httpUrl = new StringBuffer(CommomKey.PROTOCOL)
                .append(faceProperties.getEndPoint())
                .append(CommomKey.DELIMITER)
                .append(faceProperties.getApiVersion())
                .append(CommomKey.DELIMITER)
                .append(faceProperties.getProjectId())
                .append(CommomKey.DELIMITER);
        for (int i = 0; i < args.length; i++){
            if(i == (args.length - 1)){
                httpUrl.append(args[i]);
            }else {
                httpUrl.append(args[i]).append(CommomKey.DELIMITER);
            }
        }
        return httpUrl.toString();
    }
}
