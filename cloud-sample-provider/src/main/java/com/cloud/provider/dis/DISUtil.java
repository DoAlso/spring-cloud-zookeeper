package com.cloud.provider.dis;

import com.cloud.provider.configuration.DISConfiguration;
import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.DISClientBuilder;

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

    public static void setDiStreamReader(DISStreamReader diStreamReader) {
        DISUtil.diStreamReader = diStreamReader;
    }
}
