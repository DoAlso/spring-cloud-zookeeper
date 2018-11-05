package com.cloud.provider.dis;

import com.huaweicloud.dis.DIS;

/**
 * @ClassName DISStreamPollReader
 * @Description 以轮询的方式读取DIS流
 * @Author Hyx
 * @DATE 2018/11/1 10:44
 */
public class DISStreamPollReader implements DISStreamReader {

    private DISStreamCollector disStreamCollector;

    @Override
    public void reader(DIS dis, String streamName){
        System.out.println("执行轮询读取流的任务..."+streamName);
        collection("我是要入库的face数据,我来自"+streamName);
    }


    private void collection(Object object) {
        disStreamCollector.collection(object);
    }

    public void setDisStreamCollector(DISStreamCollector disStreamCollector) {
        this.disStreamCollector = disStreamCollector;
    }
}
