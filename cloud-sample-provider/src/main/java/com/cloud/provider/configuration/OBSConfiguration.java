package com.cloud.provider.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName OBSConfiguration
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/1 9:20
 */
@ConfigurationProperties(value = "huawei.obs")
public class OBSConfiguration {
    private String[] endPoints;
    private int socketTimeout;
    private int connectionTimeout;
    private boolean httpsOnly;
    private String accessKey;
    private String secretKey;

    public String[] getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(String[] endPoints) {
        this.endPoints = endPoints;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public boolean isHttpsOnly() {
        return httpsOnly;
    }

    public void setHttpsOnly(boolean httpsOnly) {
        this.httpsOnly = httpsOnly;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

  }
