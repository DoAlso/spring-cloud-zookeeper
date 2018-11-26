package com.cloud.provider.bean.hk;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "FDLibInfoList")
public class FDLibInfoList {
    private List<FDLibInfo> fdLibInfoList;

    @XmlElement(name = "FDLibInfo")
    public List<FDLibInfo> getFdLibInfoList() {
        return fdLibInfoList;
    }

    public void setFdLibInfoList(List<FDLibInfo> fdLibInfoList) {
        this.fdLibInfoList = fdLibInfoList;
    }
}
