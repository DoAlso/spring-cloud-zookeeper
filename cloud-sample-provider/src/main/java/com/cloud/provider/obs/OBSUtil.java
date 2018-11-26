package com.cloud.provider.obs;

import com.cloud.provider.configuration.OBSConfiguration;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.*;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.InputStream;

/**
 * @ClassName OBSUtil
 * @Description TODO
 * @Author doalso
 * @DATE 2018/11/1 9:58
 */
public class OBSUtil {
    private OBSConfiguration obsProperties;
    private ObsClient obsClient;
    private OBSUtil(){
        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(obsProperties.getEndPoints()[0]);
        config.setSocketTimeout(obsProperties.getSocketTimeout());
        config.setConnectionTimeout(obsProperties.getConnectionTimeout());
        config.setHttpsOnly(obsProperties.isHttpsOnly());
        obsClient = new ObsClient(obsProperties.getAccessKey(), obsProperties.getSecretKey(), config);
    }

    static class OBSHolder{
        public static OBSUtil INSTANCE = new OBSUtil();
    }

    public static OBSUtil getInstance(){
        return OBSHolder.INSTANCE;
    }

    public void setObsProperties(OBSConfiguration obsProperties) {
        this.obsProperties = obsProperties;
    }

    /**
     * 创建桶-此处的名称必须要加“-”
     * @param bucketName
     * @return
     * @throws Exception
     */
    public ObsBucket createBucket(String bucketName) throws Exception {
        if (StringUtils.isBlank(bucketName)) {
            return null;
        }
        if (obsClient.headBucket(bucketName)) {
            return null;
        }
        return obsClient.createBucket(bucketName);
    }

    /**
     * 删除桶的操作
     *
     * @param bucketName
     * @return
     * @throws Exception
     */
    public HeaderResponse delBucket(String bucketName) throws Exception {
        return obsClient.deleteBucket(bucketName);
    }


    /**
     * put请求存储对象
     *
     * @param bucketName
     * @param objectKey
     * @param file
     * @param metadata
     * @return
     * @throws Exception
     */
    public PutObjectResult putObject(String bucketName, String objectKey, File file, ObjectMetadata metadata) throws Exception {
        return obsClient.putObject(bucketName, objectKey, file, metadata);
    }

    /**
     * put请求存储对象
     *
     * @param bucketName
     * @param objectKey
     * @param file
     * @return
     * @throws Exception
     */
    public PutObjectResult putObject(String bucketName, String objectKey, File file) throws Exception {
        return obsClient.putObject(bucketName, objectKey, file);
    }

    /**
     * @param bucketName
     * @param objectKey
     * @param inputStream
     * @param metadata
     * @return
     * @throws Exception
     */
    public PutObjectResult putObject(String bucketName, String objectKey, InputStream inputStream, ObjectMetadata metadata) throws Exception {
        return obsClient.putObject(bucketName, objectKey, inputStream, metadata);
    }

    /**
     * @param bucketName
     * @param objectKey
     * @param inputStream
     * @return
     * @throws Exception
     */
    public PutObjectResult putObject(String bucketName, String objectKey, InputStream inputStream) throws Exception {
        return obsClient.putObject(bucketName, objectKey, inputStream);
    }

    /**
     * 获取对象数据
     *
     * @param bucketName
     * @param objectKey
     * @return
     * @throws Exception
     */
    public ObsObject getObject(String bucketName, String objectKey) throws Exception {
        return obsClient.getObject(bucketName, objectKey);
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param objectKey
     * @return
     */
    public HeaderResponse deleteFile(String bucketName, String objectKey) {
        return obsClient.deleteObject(bucketName, objectKey);
    }

}
