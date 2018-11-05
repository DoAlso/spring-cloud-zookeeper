package com.cloud.provider;

import com.cloud.provider.configuration.DISConfiguration;
import com.cloud.provider.configuration.OBSConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 * @author doalso
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({OBSConfiguration.class, DISConfiguration.class})
public class ProviderApplication {


    public static void main( String[] args ) {
        SpringApplication.run(ProviderApplication.class, args);
    }

}
