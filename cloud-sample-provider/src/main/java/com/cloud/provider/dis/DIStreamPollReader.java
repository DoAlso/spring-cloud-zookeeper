package com.cloud.provider.dis;

import com.huaweicloud.dis.DIS;

/**
 * @ClassName DIStreamPollReader
 * @Description 以轮询的方式读取DIS流
 * @Author Hyx
 * @DATE 2018/11/1 10:44
 */
public class DIStreamPollReader implements DIStreamReader {

    /**
     * 配置数据下载分区ID
     */
    private String partitionId;

    @Override
    public void reader(DIS dis, String streamName) {
        System.out.println("执行轮询读取流的任务..."+streamName);

    }

}
