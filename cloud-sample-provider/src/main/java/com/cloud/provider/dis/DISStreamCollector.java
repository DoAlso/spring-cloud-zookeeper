package com.cloud.provider.dis;

import com.huaweicloud.dis.iface.data.response.Record;

/**
 * @ClassName DISStreamCollector
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/5 11:39
 */
public interface DISStreamCollector {

    /**
     * 数据采集
     * @param record
     */
    void collection(Record record) throws Exception;

}
