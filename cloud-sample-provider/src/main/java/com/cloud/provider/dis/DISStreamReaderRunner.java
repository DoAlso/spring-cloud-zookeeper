package com.cloud.provider.dis;

import com.cloud.provider.configuration.DISConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DISStreamReaderRunner
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/5 13:34
 */
@Component
public class DISStreamReaderRunner implements ApplicationRunner {

    @Autowired
    private DISConfiguration disProperties;

    @Autowired
    private FaceDataCollector faceDataCollector;

    private List<String> stringMap = Arrays.asList("dis-flow","dis-face");

    @Override
    public void run(ApplicationArguments args) {
        DISStreamPollReader disStreamReader = new DISStreamPollReader();
        disStreamReader.setDisStreamCollector(faceDataCollector);
        DISStreamReaderStarter readerStarter = new DISStreamReaderStarter(
                new ThreadPoolExecutor(1,5,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>()),
                stringMap,disStreamReader,disProperties);
        readerStarter.start();
    }
}
