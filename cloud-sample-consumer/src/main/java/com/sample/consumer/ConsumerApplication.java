package com.sample.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 * @author doalso
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ConsumerApplication {
    public static void main( String[] args ) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
