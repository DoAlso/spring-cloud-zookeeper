package com.cloud.provider.bean.hk;

import javax.xml.bind.annotation.XmlElement;

public class FDLibInfo {

    private Integer id;
    private String name;
    private String FDID;

    @XmlElement(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "FDID")
    public String getFDID() {
        return FDID;
    }

    public void setFDID(String FDID) {
        this.FDID = FDID;
    }
}
