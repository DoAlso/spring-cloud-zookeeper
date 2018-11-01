package com.cloud.provider.dis;

import com.huaweicloud.dis.DIS;

/**
 * @ClassName DIStreamEventReader
 * @Description 以事件的方式读取DIS流
 * @Author Hyx
 * @DATE 2018/11/1 10:47
 */
public class DIStreamEventReader implements DIStreamReader{

    @Override
    public void reader(DIS dis, String streamName) {
        System.out.println("执行事件读取流的任务...");
    }
}
