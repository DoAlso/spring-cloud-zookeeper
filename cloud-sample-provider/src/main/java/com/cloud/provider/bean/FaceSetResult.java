package com.cloud.provider.bean;

/**
 * @ClassName FaceSetResult
 * @Description TODO
 * @Author huyaxi
 * @DATE 2018/11/6 16:28
 */
public class FaceSetResult {
    private FaceSetInfo face_set_info;
    private String error_code;
    private String error_msg;

    public FaceSetInfo getFace_set_info() {
        return face_set_info;
    }

    public void setFace_set_info(FaceSetInfo face_set_info) {
        this.face_set_info = face_set_info;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
