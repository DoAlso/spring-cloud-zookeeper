package com.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author doalso
 *
 */
@EnableConfigServer
@SpringCloudApplication
public class ConfigCenterApplication
{
    public static void main( String[] args ) {
        SpringApplication.run(ConfigCenterApplication.class, args);
    }
}
