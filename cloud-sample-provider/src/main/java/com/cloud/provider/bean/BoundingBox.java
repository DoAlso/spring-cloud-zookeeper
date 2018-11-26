package com.cloud.provider.bean;

/**
 * @ClassName BoundingBox
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/7 9:23
 */
public class BoundingBox {
    private Integer width;
    private Integer top_left_y;
    private Integer top_left_x;
    private Integer height;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getTop_left_y() {
        return top_left_y;
    }

    public void setTop_left_y(Integer top_left_y) {
        this.top_left_y = top_left_y;
    }

    public Integer getTop_left_x() {
        return top_left_x;
    }

    public void setTop_left_x(Integer top_left_x) {
        this.top_left_x = top_left_x;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
