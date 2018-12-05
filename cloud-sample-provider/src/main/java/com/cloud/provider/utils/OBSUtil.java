package com.cloud.provider.utils;

import com.hoolink.sdk.constants.AddressConstants;
import com.hoolink.sdk.enums.PropertyCfg;
import com.hoolink.sdk.log.LogUtil;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OBSUtil
 * @Description TODO
 * @Author doalso
 * @DATE 2018/11/1 9:58
 */
public class OBSUtil {
    private static Logger logger = LoggerFactory.getLogger(OBSUtil.class);
    private static ObsClient obsClient;
    private static List<PartEtag> partETags = Collections.synchronizedList(new ArrayList<PartEtag>());

    public static ObsClient getInstance() {
        if (obsClient == null) {
            synchronized (OBSUtil.class) {
                if (obsClient == null) {
                    obsClient = createObsClient();
                }
            }
        }
        return obsClient;
    }

    /**
     * 创建桶-此处的名称必须要加“-”
     * @param bucketName
     * @return
     * @throws Exception
     */
    public static ObsBucket createBucket(String bucketName) throws Exception {
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
    public static HeaderResponse delBucket(String bucketName) throws Exception {
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
    public static PutObjectResult putObject(String bucketName, String objectKey, File file, ObjectMetadata metadata) throws Exception {
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
    public static PutObjectResult putObject(String bucketName, String objectKey, File file) throws Exception {
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
    public static PutObjectResult putObject(String bucketName, String objectKey, InputStream inputStream, ObjectMetadata metadata) throws Exception {
        return obsClient.putObject(bucketName, objectKey, inputStream, metadata);
    }

    /**
     * @param bucketName
     * @param objectKey
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static PutObjectResult putObject(String bucketName, String objectKey, InputStream inputStream) throws Exception {
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
    public static ObsObject getObject(String bucketName, String objectKey) throws Exception {
        return obsClient.getObject(bucketName, objectKey);
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param objectKey
     * @return
     */
    public static HeaderResponse deleteFile(String bucketName, String objectKey) {
        return obsClient.deleteObject(bucketName, objectKey);
    }

    /**
     * 流式上传文件
     * @param bucketName
     * @param objectKey
     * @param inputStream
     * @return
     */
    public static String getObsUrl(String bucketName,String objectKey, InputStream inputStream) throws Exception{
        PutObjectResult result = obsClient.putObject(bucketName,objectKey,inputStream);
        inputStream.close();
        return result.getObjectUrl();
    }


    private static class PartUploader implements Runnable {
        private File sampleFile;
        private long offset;
        private long partSize;
        private int partNumber;
        private String uploadId;
        private String bucketName;
        private String objectKey;
        private ObsClient obsClient;

        public PartUploader(File sampleFile, long offset, long partSize, int partNumber, String uploadId, String bucketName, String objectKey, ObsClient obsClient)
        {
            this.sampleFile = sampleFile;
            this.offset = offset;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
            this.bucketName = bucketName;
            this.objectKey = objectKey;
            this.obsClient = obsClient;
        }

        @Override
        public void run()
        {
            try
            {
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setObjectKey(objectKey);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setFile(sampleFile);
                uploadPartRequest.setPartSize(this.partSize);
                uploadPartRequest.setOffset(this.offset);
                uploadPartRequest.setPartNumber(this.partNumber);

                UploadPartResult uploadPartResult = obsClient.uploadPart(uploadPartRequest);
                LogUtil.info(logger,"Part# {} done\n",this.partNumber);
                partETags.add(new PartEtag(uploadPartResult.getEtag(), uploadPartResult.getPartNumber()));
            }
            catch (Exception e)
            {
                LogUtil.info(logger,"error is : {}",e);
            }
        }
    }



    private static String claimUploadId(String bucketName, String objectKey, ObsClient obsClient) throws ObsException {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, objectKey);
        InitiateMultipartUploadResult result = obsClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }


    private static void completeMultipartUpload(String uploadId, String bucketName, String objectKey, ObsClient obsClient) throws ObsException {
        Collections.sort(partETags,Comparator.comparingInt(PartEtag::getPartNumber));
        LogUtil.info(logger,"Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, objectKey, uploadId, partETags);
        obsClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

    /**
     * 输入流转文件
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static File inputstreamToFile(InputStream inputStream) throws IOException{
        File file = new File(new StringBuffer(System.getProperty("user.dir")).append("/tmp/").append(UUID.randomUUID().toString()).toString());
        file.deleteOnExit();
        FileUtils.copyInputStreamToFile(inputStream,file);
        return file;
    }


    private static void listAllParts(String bucketName, String objectKey, String uploadId, ObsClient obsClient) throws ObsException {
        System.out.println("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectKey, uploadId);
        ListPartsResult partListing = obsClient.listParts(listPartsRequest);

        for (Multipart part : partListing.getMultipartList())
        {
            System.out.println("\tPart#" + part.getPartNumber() + ", ETag=" + part.getEtag());
        }
        System.out.println();
    }


    /**
     * 异步大文件上传
     * @param bucketName
     * @param objectKey
     * @param part
     * @throws Exception
     */
    public static void asyncUpload(String bucketName, String objectKey, MultipartFile part) throws Exception{
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(50,200,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10));
        try {
            String uploadId = claimUploadId(bucketName, objectKey, obsClient);
            LogUtil.info(logger, "Claiming a new upload id " + uploadId + "\n");
            File sampleFile = inputstreamToFile(part.getInputStream());
            long size = sampleFile.length();
            long partSize = 5 * 1024 * 1024L;
            long partCount = size % partSize == 0 ? size / partSize : size / partSize + 1;
            if (partCount > ConstantUtil.File.MAX_PART_COUNT) {
                throw new RuntimeException("Total parts count should not exceed 10000");
            } else {
                LogUtil.info(logger, "Total parts count:{} ", partCount);
            }

            LogUtil.info(logger, "Begin to upload multiparts to OBS from a file\n");
            for (int i = 0; i < partCount; i++) {
                long offset = i * partSize;
                long currPartSize = (i + 1 == partCount) ? size - offset : partSize;
                executorService.execute(new PartUploader(sampleFile, offset, currPartSize, i + 1, uploadId, bucketName, objectKey, obsClient));
            }

            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (partETags.size() != partCount) {
                throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
            } else {
                LogUtil.info(logger, "Succeed to complete multiparts into an object named :{}", objectKey);
            }
            listAllParts(bucketName,objectKey,uploadId,obsClient);
            completeMultipartUpload(uploadId, bucketName, objectKey, obsClient);
            partETags.clear();
        }catch (ObsException e){
            LogUtil.info(logger,"Response Code: {}" , e.getResponseCode());
            LogUtil.info(logger,"Error Message: {}" , e.getErrorMessage());
            LogUtil.info(logger,"Error Code: {}" , e.getErrorCode());
            LogUtil.info(logger,"Request ID:  {}" ,e.getErrorRequestId());
            LogUtil.info(logger,"Host ID:  {}" , e.getErrorHostId());
        }
    }


    private static ObsClient createObsClient(){
        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(AddressConstants.getProperty(PropertyCfg.HUAWEI_OBS_ENDPOINT.getKey()));
        config.setSocketTimeout(Integer.parseInt(AddressConstants.getProperty(PropertyCfg.HUAWEI_OBS_SOCKET_TIMOUT.getKey())));
        config.setConnectionTimeout(Integer.parseInt(AddressConstants.getProperty(PropertyCfg.HUAWEI_OBS_CONNECTION_TIMEOUT.getKey())));
        config.setHttpsOnly(Boolean.valueOf(AddressConstants.getProperty(PropertyCfg.HUAWEI_OBS_HTTPSONLY.getKey())));
        return new ObsClient(AddressConstants.getProperty(PropertyCfg.HUAWEI_ACCESSKEY.getKey()),AddressConstants.getProperty(PropertyCfg.HUAWEI_SECRETKEY.getKey()), config);
    }

}
