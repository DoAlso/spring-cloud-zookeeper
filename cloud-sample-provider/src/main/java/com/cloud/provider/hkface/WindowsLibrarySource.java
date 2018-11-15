package com.cloud.provider.hkface;

import com.cloud.provider.bean.hk.FDLibInfoList;
import com.cloud.provider.hkface.windows.HCNetSDK;
import com.cloud.provider.utils.CommonUtil;
import com.cloud.provider.utils.ConstantUtil;
import com.cloud.provider.utils.JaxbUtil;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WindowsLibrarySource
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/14 11:27
 */
@Service
@Conditional(WindowsCondition.class)
public class WindowsLibrarySource implements LibrarySource {
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowsLibrarySource.class);
    private static HCNetSDK hcNetSDK = HCNetSDK.INSTANCE;
    private Map<String,NativeLong> loginCache = new ConcurrentHashMap<>();
    private Map<String,NativeLong> alarmHandleCache = new ConcurrentHashMap<>();

    static {
        hcNetSDK.NET_DVR_Init();
    }

    /**
     * 超脑设备登录
     * @param serial
     * @param ip
     * @param port
     * @param username
     * @param password
     */
    @Override
    public void login(String serial, String ip, String port, String username, String password){
        NativeLong userId = loginCache.getOrDefault(serial,new NativeLong(-1));
        if(userId.intValue() > -1){
            //先注销
            hcNetSDK.NET_DVR_Logout(userId);
            loginCache.put(serial,new NativeLong(-1));
        }
        HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        userId = hcNetSDK.NET_DVR_Login_V30(ip, Short.valueOf(port), username, password, m_strDeviceInfo);
        if(userId.intValue() > -1){
            loginCache.put(serial,userId);
            LOGGER.info("device:{}login success!", new String(m_strDeviceInfo.sSerialNumber));
        }else {
            int code = hcNetSDK.NET_DVR_GetLastError();
            LOGGER.info("device login error:{}",code);
            loginCache.put(serial,new NativeLong(-1));
        }
    }


    /**
     * 检查指定设备是否具有人脸
     * 识别的能力
     * @param serial
     * @return
     */
    @Override
    public boolean checkFaceFaceCapabilities(String serial) {
        NativeLong userId = loginCache.get(serial);
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT inBuf = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        inBuf.dwSize = inBuf.size();
        HCNetSDK.BYTE_ARRAY ptrUrl = new HCNetSDK.BYTE_ARRAY(ConstantUtil.FaceHK.CAPABILITIES.length());
        System.arraycopy(ConstantUtil.FaceHK.CAPABILITIES.getBytes(), 0, ptrUrl.byValue, 0, ConstantUtil.FaceHK.CAPABILITIES.length());
        ptrUrl.write();
        inBuf.lpRequestUrl = ptrUrl.getPointer();
        inBuf.dwRequestUrlLen = ConstantUtil.FaceHK.CAPABILITIES.length();

        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT outBuf = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        outBuf.dwSize = outBuf.size();
        HCNetSDK.BYTE_ARRAY ptrOutByte = new HCNetSDK.BYTE_ARRAY(HCNetSDK.ISAPI_DATA_LEN);
        outBuf.lpOutBuffer = ptrOutByte.getPointer();
        outBuf.dwOutBufferSize = HCNetSDK.ISAPI_DATA_LEN;
        outBuf.write();
        return hcNetSDK.NET_DVR_STDXMLConfig(userId, inBuf, outBuf);
    }


    /**
     * 创建人脸库
     * @param xmlParam
     * @param serial
     */
    @Override
    public void createFaceDataBase(String xmlParam,String serial){
        NativeLong userId = loginCache.get(serial);
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struInput.dwSize = struInput.size();
        HCNetSDK.BYTE_ARRAY ptrUrl = new HCNetSDK.BYTE_ARRAY(HCNetSDK.BYTE_ARRAY_LEN);
        System.arraycopy(ConstantUtil.FaceHK.CREATE_FACE_DATABASE.getBytes(), 0, ptrUrl.byValue, 0, ConstantUtil.FaceHK.CREATE_FACE_DATABASE.length());
        ptrUrl.write();
        struInput.lpRequestUrl = ptrUrl.getPointer();
        struInput.dwRequestUrlLen = ConstantUtil.FaceHK.CREATE_FACE_DATABASE.length();

        String strInBuffer = new String(xmlParam);
        HCNetSDK.BYTE_ARRAY ptrByte = new HCNetSDK.BYTE_ARRAY(10 * HCNetSDK.BYTE_ARRAY_LEN);
        ptrByte.byValue = strInBuffer.getBytes();
        ptrByte.write();
        struInput.lpInBuffer = ptrByte.getPointer();
        struInput.dwInBufferSize = strInBuffer.length();
        struInput.write();

        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struOutput = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        struOutput.dwSize = struOutput.size();

        HCNetSDK.BYTE_ARRAY ptrOutByte = new HCNetSDK.BYTE_ARRAY(HCNetSDK.ISAPI_DATA_LEN);
        struOutput.lpOutBuffer = ptrOutByte.getPointer();
        struOutput.dwOutBufferSize = HCNetSDK.ISAPI_DATA_LEN;

        HCNetSDK.BYTE_ARRAY ptrStatusByte = new HCNetSDK.BYTE_ARRAY(HCNetSDK.ISAPI_STATUS_LEN);
        struOutput.lpStatusBuffer = ptrStatusByte.getPointer();
        struOutput.dwStatusSize = HCNetSDK.ISAPI_STATUS_LEN;
        struOutput.write();
        boolean result = hcNetSDK.NET_DVR_STDXMLConfig(userId, struInput, struOutput);
        if(result){
            String xmlStr = struOutput.lpOutBuffer.getString(0);
            FDLibInfoList fdLibInfoList = JaxbUtil.xmlToBean(xmlStr,FDLibInfoList.class);
            //TODO 在此处将数据传递出去
        }else {
            int code = hcNetSDK.NET_DVR_GetLastError();
            LOGGER.error("创建人脸库失败: {}", code);
        }
    }


    @Override
    public boolean modifyFaceDataBase(String faceId, String faceDataBaseName,String serial) {
        NativeLong userId = loginCache.get(serial);
        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struInput.dwSize = struInput.size();

        String str = "PUT /ISAPI/Intelligent/FDLib/"+faceId;
        HCNetSDK.BYTE_ARRAY ptrUrl = new HCNetSDK.BYTE_ARRAY(HCNetSDK.BYTE_ARRAY_LEN);
        System.arraycopy(str.getBytes(), 0, ptrUrl.byValue, 0, str.length());
        ptrUrl.write();
        struInput.lpRequestUrl = ptrUrl.getPointer();
        struInput.dwRequestUrlLen = str.length();

        String strInBuffer = new String("<CreateFDLibList><CreateFDLib><id>1</id><name>" + faceDataBaseName + "</name></CreateFDLib></CreateFDLibList>");
        HCNetSDK.BYTE_ARRAY ptrByte = new HCNetSDK.BYTE_ARRAY(10 * HCNetSDK.BYTE_ARRAY_LEN);
        ptrByte.byValue = strInBuffer.getBytes();
        ptrByte.write();
        struInput.lpInBuffer = ptrByte.getPointer();
        struInput.dwInBufferSize = strInBuffer.length();
        struInput.write();

        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struOutput = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        struOutput.dwSize = struOutput.size();

        HCNetSDK.BYTE_ARRAY ptrOutByte = new HCNetSDK.BYTE_ARRAY(HCNetSDK.ISAPI_DATA_LEN);
        struOutput.lpOutBuffer = ptrOutByte.getPointer();
        struOutput.dwOutBufferSize = HCNetSDK.ISAPI_DATA_LEN;

        HCNetSDK.BYTE_ARRAY ptrStatusByte = new HCNetSDK.BYTE_ARRAY(HCNetSDK.ISAPI_STATUS_LEN);
        struOutput.lpStatusBuffer = ptrStatusByte.getPointer();
        struOutput.dwStatusSize = HCNetSDK.ISAPI_STATUS_LEN;
        struOutput.write();

        if (hcNetSDK.NET_DVR_STDXMLConfig(userId, struInput, struOutput)) {
            LOGGER.info("修改人脸库成功");
            return true;
        } else {
            int code = hcNetSDK.NET_DVR_GetLastError();
            LOGGER.error("修改人脸库失败: {}", code);
            return false;
        }
    }

    /**
     * 上传文件
     * @param serial
     * @param faceDataBaseId
     * @return
     */
    @Override
    public void uploadFile(String serial,String faceDataBaseId,InputStream inputStream,String name){
        NativeLong userId = loginCache.get(serial);
        HCNetSDK.NET_DVR_FACELIB_COND struInput = new HCNetSDK.NET_DVR_FACELIB_COND();
        struInput.dwSize = struInput.size();
        struInput.szFDID = faceDataBaseId.getBytes();
        struInput.byConcurrent = 0;
        struInput.byCover = 1;
        struInput.byCustomFaceLibID = 0;

        struInput.write();
        Pointer lpInput = struInput.getPointer();

        NativeLong uploadHandle = hcNetSDK.NET_DVR_UploadFile_V40(userId, HCNetSDK.IMPORT_DATA_TO_FACELIB, lpInput, struInput.size(), null, null, 0);
        handleFaceUpload(inputStream,name,uploadHandle);
    }

    private void handleFaceUpload(InputStream inputStream, String name, NativeLong uploadHandle){
        if (uploadHandle.longValue() < 0) {
            LOGGER.info("NET_DVR_UploadFile_V40 fail,error:{}" , hcNetSDK.NET_DVR_GetLastError());
        } else {
            LOGGER.info("NET_DVR_UploadFile_V40 success");
        }
        if (uploadHandle.longValue() != -1) {
            if (hcNetSDK.NET_DVR_UploadClose(uploadHandle)) {
                LOGGER.info("NET_DVR_UploadClose success");
            } else {
                LOGGER.error("NET_DVR_UploadClose fail,error:{}" + hcNetSDK.NET_DVR_GetLastError());
            }
        }
        new Thread(()->{
            UploadSend(inputStream,name,uploadHandle);
            while (true) {
                if (-1 == uploadHandle.longValue()) {
                    return;
                }

                NativeLong uploadStatus = getUploadState(uploadHandle);
                if (uploadStatus.longValue() == 1) {
                    HCNetSDK.NET_DVR_UPLOAD_FILE_RET struPicRet = new HCNetSDK.NET_DVR_UPLOAD_FILE_RET();
                    struPicRet.write();
                    Pointer lpPic = struPicRet.getPointer();
                    boolean bRet = hcNetSDK.NET_DVR_GetUploadResult(uploadHandle, lpPic, struPicRet.size());
                    if (!bRet) {
                        LOGGER.error("NET_DVR_GetUploadResult failed with:" + hcNetSDK.NET_DVR_GetLastError());
                    } else {
                        LOGGER.info("NET_DVR_GetUploadResult succ");
                        struPicRet.read();
                        String m_picID = new String(struPicRet.sUrl);
                        LOGGER.info("PicID:{}" , m_picID);
                        LOGGER.info( "image upload success PID:{}" , m_picID);
                    }


                    if (hcNetSDK.NET_DVR_UploadClose(uploadHandle)) {
                        uploadHandle.setValue(-1);
                    }

                } else if (uploadStatus.longValue() >= 3 || uploadStatus.longValue() == -1) {
                    LOGGER.info("m_UploadStatus :{}" + uploadStatus);
                    hcNetSDK.NET_DVR_UploadClose(uploadHandle);
                    uploadHandle.setValue(-1);
                    break;
                }
            }
        }).start();
    }

    private NativeLong getUploadState(NativeLong uploadHandle) {
        IntByReference pInt = new IntByReference(0);
        NativeLong m_UploadStatus = hcNetSDK.NET_DVR_GetUploadState(uploadHandle, pInt);
        if (m_UploadStatus.longValue() == -1) {
            LOGGER.info("NET_DVR_GetUploadState fail,error=" + hcNetSDK.NET_DVR_GetLastError());
        } else if (m_UploadStatus.longValue() == 2) {
            LOGGER.info("is uploading!!!!  progress = " + pInt.getValue());
        } else if (m_UploadStatus.longValue() == 1) {
            LOGGER.info("progress = " + pInt.getValue());
            LOGGER.info("Uploading Succ!!!!!");
        } else {
            LOGGER.info("NET_DVR_GetUploadState fail  m_UploadStatus=" + m_UploadStatus);
            LOGGER.info("NET_DVR_GetUploadState fail,error=" + hcNetSDK.NET_DVR_GetLastError());
        }
        return m_UploadStatus;
    }


    /**
     * 上传人脸图片
     *
     * @param inputStream
     */
    private void UploadSend(InputStream inputStream, String name,NativeLong uploadHandle){
        String strInBuffer = "<FaceAppendData><name>"+name+"</name><bornTime /><sex /><province /><certificateType /><certificateNumber /></FaceAppendData>";
        InputStream xmlfile = null;
        try {
            xmlfile = new ByteArrayInputStream(strInBuffer.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        int picdataLength = 0;
        int xmldataLength = 0;
        try {
            picdataLength = inputStream.available();
            xmldataLength = xmlfile.available();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (picdataLength < 0 || xmldataLength < 0) {
            LOGGER.info("input file/xml dataSize < 0");
            return;
        }

        HCNetSDK.BYTE_ARRAY ptrpicByte = new HCNetSDK.BYTE_ARRAY(picdataLength);
        HCNetSDK.BYTE_ARRAY ptrxmlByte = new HCNetSDK.BYTE_ARRAY(xmldataLength);
        try {
            inputStream.read(ptrpicByte.byValue);
            xmlfile.read(ptrxmlByte.byValue);
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        ptrpicByte.write();
        ptrxmlByte.write();

        HCNetSDK.NET_DVR_SEND_PARAM_IN struSendParam = new HCNetSDK.NET_DVR_SEND_PARAM_IN();

        struSendParam.pSendData = ptrpicByte.getPointer();
        struSendParam.dwSendDataLen = picdataLength;
        struSendParam.pSendAppendData = ptrxmlByte.getPointer();
        struSendParam.dwSendAppendDataLen = xmldataLength;
        if(struSendParam.pSendData == null || struSendParam.pSendAppendData == null || struSendParam.dwSendDataLen == 0 || struSendParam.dwSendAppendDataLen == 0)
        {
            LOGGER.info("input file/xml data err");
            return;
        }

        struSendParam.byPicType = 1;
        struSendParam.dwPicMangeNo = 0;
        struSendParam.write();

        NativeLong iRet = hcNetSDK.NET_DVR_UploadSend(uploadHandle, struSendParam.getPointer(), null);

        LOGGER.info("iRet:{}" , iRet);
        if (iRet.longValue() < 0) {
            LOGGER.info("NET_DVR_UploadSend fail,error:{}" , hcNetSDK.NET_DVR_GetLastError());
        } else {
            LOGGER.info("NET_DVR_UploadSend success");
            LOGGER.info("dwSendDataLen :{}" , struSendParam.dwSendDataLen);
            LOGGER.info("dwSendAppendDataLen :{}" , struSendParam.dwSendAppendDataLen);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFaceData(String faceDataBaseId, String faceId, String serial) {
        NativeLong userid = loginCache.get(serial);
        String str = "DELETE /ISAPI/Intelligent/FDLib/" + faceDataBaseId + "/picture/" + faceId;

        HCNetSDK.NET_DVR_XML_CONFIG_INPUT struInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
        struInput.dwSize = struInput.size();

        HCNetSDK.BYTE_ARRAY ptrDeleteFaceLibUrl = new HCNetSDK.BYTE_ARRAY(HCNetSDK.BYTE_ARRAY_LEN);
        System.arraycopy(str.getBytes(), 0, ptrDeleteFaceLibUrl.byValue, 0, str.length());
        ptrDeleteFaceLibUrl.write();
        struInput.lpRequestUrl = ptrDeleteFaceLibUrl.getPointer();
        struInput.dwRequestUrlLen = str.length();
        struInput.lpInBuffer = null;
        struInput.dwInBufferSize = 0;
        struInput.write();

        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT struOutput = new HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT();
        struOutput.dwSize = struOutput.size();
        struOutput.lpOutBuffer = null;
        struOutput.dwOutBufferSize = 0;

        HCNetSDK.BYTE_ARRAY ptrStatusByte = new HCNetSDK.BYTE_ARRAY(HCNetSDK.ISAPI_STATUS_LEN);
        struOutput.lpStatusBuffer = ptrStatusByte.getPointer();
        struOutput.dwStatusSize = HCNetSDK.ISAPI_STATUS_LEN;
        struOutput.write();

        if(!hcNetSDK.NET_DVR_STDXMLConfig(userid, struInput, struOutput))
        {
            LOGGER.info("lpRequestUrl:{}" , str);
            LOGGER.error("NET_DVR_STDXMLConfig DELETE failed with:{}" , hcNetSDK.NET_DVR_GetLastError());
        }
        else
        {
            System.out.println("lpRequestUrl:" + str);
            System.out.println("NET_DVR_STDXMLConfig DELETE Succ!!!!!!!!!!!!!!!");
            LOGGER.info("image delete success PID:{}" , faceId);
        }
    }


    public class FMSGCallBack implements HCNetSDK.FMSGCallBack {
        @Override
        public void invoke(NativeLong lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
            //lCommand是传的报警类型
            switch (lCommand.intValue()) {
                //实时人脸抓拍上传，人脸识别结果上传
                case HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT:
                    HCNetSDK.NET_VCA_FACESNAP_RESULT strFaceSnapInfo = new HCNetSDK.NET_VCA_FACESNAP_RESULT();
                    strFaceSnapInfo.write();
                    Pointer pFaceSnapInfo = strFaceSnapInfo.getPointer();
                    pFaceSnapInfo.write(0, pAlarmInfo.getByteArray(0, strFaceSnapInfo.size()), 0, strFaceSnapInfo.size());
                    strFaceSnapInfo.read();
                    //报警设备IP地址
                    String sIP = new String(strFaceSnapInfo.struDevInfo.struDevIP.sIpV4).split("\0", 2)[0];
                    //eventParam.setChannelIp(sIP);
                    if (strFaceSnapInfo.dwFacePicLen > 0) {
                        byte[] smallImg = strFaceSnapInfo.pBuffer1.getByteArray(0, strFaceSnapInfo.dwFacePicLen);
                        if (smallImg.length > 0) {
                            LOGGER.info("This Can Cut Out Small Image:{}" , smallImg.length);
                            InputStream inputStream = new ByteArrayInputStream(smallImg);
                            try {
                                LOGGER.info(">>>>>>inputStream size:{}>>>>>>",inputStream.available());
                            }catch (Exception e){
                                LOGGER.error(">>>>>>>error is:{}>>>>>>",e);
                            }
                            //eventParam.setSmallImg(inputStream);
                        }
                    }
                    if (strFaceSnapInfo.dwBackgroundPicLen > 0) {
                        byte[] bigImg = strFaceSnapInfo.pBuffer2.getByteArray(0, strFaceSnapInfo.dwBackgroundPicLen);
                        if (bigImg.length > 0) {
                            LOGGER.info("This Can Cut Out Big Image:" + bigImg.length);
                            InputStream inputStream = new ByteArrayInputStream(bigImg);
                            //eventParam.setBigImg(inputStream);
                        }
                    }
                    //eventhandler.handler(eventParam);
                    break;
                //人脸黑名单比对报警
                case HCNetSDK.COMM_SNAP_MATCH_ALARM:
                    HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM strFaceSnapMatch = new HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM();
                    strFaceSnapMatch.write();
                    Pointer pFaceSnapMatch = strFaceSnapMatch.getPointer();
                    pFaceSnapMatch.write(0, pAlarmInfo.getByteArray(0, strFaceSnapMatch.size()), 0, strFaceSnapMatch.size());
                    strFaceSnapMatch.read();

                    //报警设备IP地址
                    String sIP1 = new String(pAlarmer.sDeviceIP).split("\0", 2)[0];

                    LOGGER.info("glasses:{}", CommonUtil.byteToInt(strFaceSnapMatch.struSnapInfo.byGlasses));
                    LOGGER.info("sex:{}", CommonUtil.byteToInt(strFaceSnapMatch.struSnapInfo.bySex));
                    LOGGER.info("ageGroup:{}", CommonUtil.byteToInt(strFaceSnapMatch.struSnapInfo.byAgeGroup));

                    //eventParam.setNvrIp(sIP1);
                    try{
                        String name =  new String(strFaceSnapMatch.struBlackListInfo.struBlackListInfo.struAttribute.byName,"GBK").trim();
                        LOGGER.info("name is ：{}",name);
                        //eventParam.setName(name);
                    }catch (UnsupportedEncodingException e){
                        LOGGER.error("error is:{}",e);
                    }
                    //eventParam.setXsd(strFaceSnapMatch.fSimilarity);
                    if(strFaceSnapMatch.struBlackListInfo.dwPIDLen > 0){
                        String result = strFaceSnapMatch.struBlackListInfo.pPID.getString(strFaceSnapMatch.struBlackListInfo.dwPIDLen);
                        result = result.substring(0,32);
                        System.out.println(result);
                        if(StringUtils.isNotBlank(result)) {
                            //eventParam.setNvrFaceId(result);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void setUpAlarmChan(String serial){
        NativeLong userId = loginCache.get(serial);
        if(userId == null || userId.intValue() < 0){
            LOGGER.info("Please Login Before");
            return;
        }
        NativeLong alarmHandle = alarmHandleCache.getOrDefault(serial,new NativeLong(-1));
        if(alarmHandle.intValue() < 0){
            Pointer pUser = null;
            if (!hcNetSDK.NET_DVR_SetDVRMessageCallBack_V30(new FMSGCallBack(), pUser))
            {
                LOGGER.error("Setting Callback Function Error!");
            }
            HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            m_strAlarmInfo.dwSize=m_strAlarmInfo.size();
            m_strAlarmInfo.byLevel=0;
            m_strAlarmInfo.byAlarmInfoType=1;
            m_strAlarmInfo.write();
            alarmHandle = hcNetSDK.NET_DVR_SetupAlarmChan_V41(userId, m_strAlarmInfo);
            if (alarmHandle.intValue() == -1) {
                LOGGER.error("Alarm Error!");
            } else {
                LOGGER.info("Alarm Success!");
            }
        }
    }


    /**
     * 关闭布防
     */
    @Override
    public void CloseAlarmChan(String serial) {
        NativeLong lAlarmHandle = alarmHandleCache.get(serial);
        if (lAlarmHandle != null && lAlarmHandle.intValue() > -1) {
            if(hcNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle)) {
                alarmHandleCache.put(serial,new NativeLong(-1));
            }
        }
    }
}
