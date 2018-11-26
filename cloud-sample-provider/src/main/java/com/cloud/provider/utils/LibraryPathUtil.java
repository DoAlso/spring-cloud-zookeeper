package com.cloud.provider.utils;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName LibraryPathUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/14 16:43
 */
public class LibraryPathUtil {

    public static String DLL_PATH;
    public static String LIB_PATH;

    static {
        String dllPath = (LibraryPathUtil.class.getResource("/lib").getPath()).replaceAll("%20", " ").substring(1).replace("/", "\\");
        String libPath = (LibraryPathUtil.class.getResource("/hklib").getPath()).replaceAll("%20", " ").substring(1).replace("/", "\\");
        try {
            DLL_PATH = java.net.URLDecoder.decode(dllPath,"utf-8");
            LIB_PATH = java.net.URLDecoder.decode(libPath,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
