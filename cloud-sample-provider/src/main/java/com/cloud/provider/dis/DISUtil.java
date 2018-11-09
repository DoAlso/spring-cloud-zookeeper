package com.cloud.provider.dis;

import com.cloud.provider.configuration.DISConfiguration;
import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.DISClientBuilder;
import com.huaweicloud.dis.iface.data.request.StreamType;
import com.huaweicloud.dis.iface.stream.request.CreateStreamRequest;
import com.huaweicloud.dis.iface.stream.request.DeleteStreamRequest;
import com.huaweicloud.dis.util.DataTypeEnum;

/**
 * @author Hyx
 */
public class DISUtil {

    private static DIS dis;

    private static DISStreamReader diStreamReader;

    public static DIS getInstance(DISConfiguration disProperties) {
        if (dis == null) {
            synchronized (DISUtil.class) {
                if (dis == null) {
                    dis = createDISClient(disProperties);
                }
            }
        }
        return dis;
    }

    private static DIS createDISClient(DISConfiguration disProperties) {
        return DISClientBuilder.standard()
                .withRegion(disProperties.getRegion())
                .withEndpoint(disProperties.getEndPoint())
                .withAk(disProperties.getAccessKey())
                .withSk(disProperties.getSecretKey())
                .withProjectId(disProperties.getProjectId())
                .build();
    }

    public static void reader(String streamName){
        diStreamReader.reader(dis,streamName);
    }


    /**
     * 创建DIS流
     * @param streamName
     * @throws Exception
     */
    public static void createStream(String streamName) throws Exception{
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
     * 删除DIS流
     * @param streamName
     * @throws Exception
     */
    public static void deleteStream(String streamName) throws Exception{
        DeleteStreamRequest deleteStreamRequest = new DeleteStreamRequest();
        deleteStreamRequest.setStreamName(streamName);
        dis.deleteStream(deleteStreamRequest);
    }

    public static void setDiStreamReader(DISStreamReader diStreamReader) {
        DISUtil.diStreamReader = diStreamReader;
    }
}
