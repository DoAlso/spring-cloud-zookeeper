package com.cloud.provider.dis;

import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.iface.data.request.StreamType;
import com.huaweicloud.dis.iface.stream.request.CreateStreamRequest;
import com.huaweicloud.dis.iface.stream.request.DeleteStreamRequest;
import com.huaweicloud.dis.util.DataTypeEnum;

public class DISStreamManager {
    /**
     * 创建流
     * @param dis
     * @param streamName
     */
    public void createStream(DIS dis,String streamName) throws Exception{
        CreateStreamRequest createStreamRequest = new CreateStreamRequest();
        createStreamRequest.setStreamName(streamName);
        // COMMON 普通通道; ADVANCED 高级通道
        createStreamRequest.setStreamType(StreamType.COMMON.name());
        createStreamRequest.setDataType(DataTypeEnum.BLOB.name());
        createStreamRequest.setPartitionCount(1);
        createStreamRequest.setDataDuration(24);
        dis.createStream(createStreamRequest);
    }

    /**
     * 删除流
     * @param dis
     * @param streamName
     */
    public void deleteStream(DIS dis,String streamName) throws Exception{
        DeleteStreamRequest deleteStreamRequest = new DeleteStreamRequest();
        deleteStreamRequest.setStreamName(streamName);
        dis.deleteStream(deleteStreamRequest);
    }

    /**
     * 订阅流
     * @param dis
     * @param StreamName
     */
    public void describeStream(DIS dis,String StreamName){

    }
}
