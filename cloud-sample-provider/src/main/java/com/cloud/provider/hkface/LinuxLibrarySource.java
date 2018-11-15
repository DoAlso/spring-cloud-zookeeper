package com.cloud.provider.hkface;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @ClassName LinuxLibrarySource
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/14 11:29
 */
@Service
@Conditional(LinuxCondition.class)
public class LinuxLibrarySource implements LibrarySource {

    @Override
    public void login(String serial, String ip, String port, String username, String password) {

    }

    @Override
    public boolean checkFaceFaceCapabilities(String arg) {
        return false;
    }

    @Override
    public void createFaceDataBase(String xmlParam, String serial) {

    }

    @Override
    public boolean modifyFaceDataBase(String faceId, String faceDataBaseName, String serial) {
        return false;
    }

    @Override
    public void uploadFile(String serial, String faceDataBaseId, InputStream inputStream, String name) {

    }

    @Override
    public void deleteFaceData(String faceDataBaseId, String faceId, String serial) {

    }

    @Override
    public void setUpAlarmChan(String serial) {

    }

    @Override
    public void CloseAlarmChan(String serial) {

    }
}
