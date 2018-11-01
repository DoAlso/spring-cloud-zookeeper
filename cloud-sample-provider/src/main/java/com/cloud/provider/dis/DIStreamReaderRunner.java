package com.cloud.provider.dis;

import com.cloud.provider.configuration.DISConfiguration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @ClassName DIStreamReaderRunner
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/1 16:53
 */
public class DIStreamReaderRunner {

    private ExecutorService executorService;

    private Map<Long,String> projectStreams;

    private DISConfiguration disProperties;

    private DIStreamReader diStreamReader;

    public DIStreamReaderRunner(ExecutorService executorService,Map<Long,String> projectStreams,DIStreamReader streamReader,DISConfiguration disProperties){
        this.executorService = executorService;
        this.projectStreams = projectStreams;
        this.diStreamReader = streamReader;
        this.disProperties = disProperties;
    }

    public void run(){
        DISUtil.getInstance(disProperties);
        DISUtil.setDiStreamReader(diStreamReader);
        for (Map.Entry<Long,String> entry : projectStreams.entrySet()){
            executorService.execute(()->{
                DISUtil.reader(entry.getValue());
            });
        }
    }
}
