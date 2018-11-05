package com.cloud.provider.dis;

/**
 * @ClassName DISStreamCollector
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/5 11:39
 */
public interface DISStreamCollector {

    /**
     * 数据采集
     * @param object
     */
    void collection(Object object);

}
