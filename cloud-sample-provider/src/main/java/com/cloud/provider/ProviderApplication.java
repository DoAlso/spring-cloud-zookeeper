package com.cloud.provider;

import com.cloud.provider.configuration.DISConfiguration;
import com.cloud.provider.configuration.OBSConfiguration;
import com.cloud.provider.dis.DISUtil;
import com.cloud.provider.dis.DIStreamPollReader;
import com.cloud.provider.dis.DIStreamReaderRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.concurrent.*;

/**
 * Hello world!
 * @author doalso
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({OBSConfiguration.class, DISConfiguration.class})
public class ProviderApplication implements ApplicationRunner {

    @Autowired
    private DISConfiguration disProperties;

    private String[] streams = new String[]{"dis-face","dis-flow"};


    public static void main( String[] args ) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        DIStreamReaderRunner readerRunner = new DIStreamReaderRunner(
                new ThreadPoolExecutor(1,5,60L,TimeUnit.SECONDS,new LinkedBlockingQueue<>()),
                null,new DIStreamPollReader(),disProperties);
        readerRunner.run();
    }
}
