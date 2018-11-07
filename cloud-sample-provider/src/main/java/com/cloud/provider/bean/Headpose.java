package com.cloud.provider.bean;

/**
 * @ClassName Headpose
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/7 9:28
 */
public class Headpose {
    private Integer yaw_angle;
    private Integer roll_angle;
    private Integer pitch_angle;

    public Integer getYaw_angle() {
        return yaw_angle;
    }

    public void setYaw_angle(Integer yaw_angle) {
        this.yaw_angle = yaw_angle;
    }

    public Integer getRoll_angle() {
        return roll_angle;
    }

    public void setRoll_angle(Integer roll_angle) {
        this.roll_angle = roll_angle;
    }

    public Integer getPitch_angle() {
        return pitch_angle;
    }

    public void setPitch_angle(Integer pitch_angle) {
        this.pitch_angle = pitch_angle;
    }
}
