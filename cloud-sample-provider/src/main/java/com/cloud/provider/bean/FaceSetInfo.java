package com.cloud.provider.bean;

import java.util.Map;

/**
 * @ClassName FaceSetInfo
 * @Description TODO
 * @Author huyaxi
 * @DATE 2018/11/6 16:31
 */
public class FaceSetInfo {
    /**
     * 人脸所属项目
     */
    private Long project_id;
    /**
     *  人脸库名称
     */
    private String face_set_name;
    /**
     * 人脸库ID，随机生成的包含八个字符的字符串。
     */
    private String face_set_id;
    /**
     * 创建时间
     */
    private String create_date;
    /**
     * 人脸库最大的容量。
     */
    private Integer face_set_capacity;
    /**
     * 人脸库当中的人脸数量
     */
    private Integer face_number;
    /**
     * 用户的自定义字段
     */
    private Map<String,Object> external_fields;

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public String getFace_set_name() {
        return face_set_name;
    }

    public void setFace_set_name(String face_set_name) {
        this.face_set_name = face_set_name;
    }

    public String getFace_set_id() {
        return face_set_id;
    }

    public void setFace_set_id(String face_set_id) {
        this.face_set_id = face_set_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public Integer getFace_set_capacity() {
        return face_set_capacity;
    }

    public void setFace_set_capacity(Integer face_set_capacity) {
        this.face_set_capacity = face_set_capacity;
    }

    public Integer getFace_number() {
        return face_number;
    }

    public void setFace_number(Integer face_number) {
        this.face_number = face_number;
    }

    public Map<String, Object> getExternal_fields() {
        return external_fields;
    }

    public void setExternal_fields(Map<String, Object> external_fields) {
        this.external_fields = external_fields;
    }

    @Override
    public String toString() {
        return "FaceSetInfo{" +
                "project_id=" + project_id +
                ", face_set_name='" + face_set_name + '\'' +
                ", face_set_id='" + face_set_id + '\'' +
                ", create_date='" + create_date + '\'' +
                ", face_set_capacity=" + face_set_capacity +
                ", face_number=" + face_number +
                ", external_fields=" + external_fields +
                '}';
    }
}
