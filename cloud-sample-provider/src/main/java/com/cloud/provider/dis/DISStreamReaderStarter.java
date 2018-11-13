package com.cloud.provider.dis;

import com.cloud.provider.configuration.DISConfiguration;

import java.util.List;

/**
 * @ClassName DISStreamReaderStarter
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/1 16:53
 */
public class DISStreamReaderStarter {

    private List<String> projectStreams;

    private DISConfiguration disProperties;

    private DISStreamReader diStreamReader;

    public DISStreamReaderStarter(List<String> projectStreams, DISStreamReader streamReader, DISConfiguration disProperties){
        this.projectStreams = projectStreams;
        this.diStreamReader = streamReader;
        this.disProperties = disProperties;
    }

    /**
     * 开启所有项目的读流操作
     */
    public void start(){
        DISUtil.getInstance(disProperties);
        DISUtil.setDiStreamReader(diStreamReader);
        projectStreams.forEach((item)-> {
            DISUtil.setStreamName(item);
            DISUtil.reader();
        });
    }
}
