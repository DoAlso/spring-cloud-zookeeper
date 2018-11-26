package com.cloud.provider.bean;

import java.util.List;

/**
 * @ClassName FaceContour
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/7 10:23
 */
public class FaceContour {
    private List<Point> point;

    public List<Point> getPoint() {
        return point;
    }

    public void setPoint(List<Point> point) {
        this.point = point;
    }
}
