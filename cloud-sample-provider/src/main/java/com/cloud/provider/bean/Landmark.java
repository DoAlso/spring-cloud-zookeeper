package com.cloud.provider.bean;

/**
 * @ClassName Landmark
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/7 10:21
 */
public class Landmark {
    private EyesContour eyes_contour;
    private MouthContour mouth_contour;
    private FaceContour face_contour;
    private EyebrowContour eyebrow_contour;
    private NoseContour nose_contour;

    public EyesContour getEyes_contour() {
        return eyes_contour;
    }

    public void setEyes_contour(EyesContour eyes_contour) {
        this.eyes_contour = eyes_contour;
    }

    public MouthContour getMouth_contour() {
        return mouth_contour;
    }

    public void setMouth_contour(MouthContour mouth_contour) {
        this.mouth_contour = mouth_contour;
    }

    public FaceContour getFace_contour() {
        return face_contour;
    }

    public void setFace_contour(FaceContour face_contour) {
        this.face_contour = face_contour;
    }

    public EyebrowContour getEyebrow_contour() {
        return eyebrow_contour;
    }

    public void setEyebrow_contour(EyebrowContour eyebrow_contour) {
        this.eyebrow_contour = eyebrow_contour;
    }

    public NoseContour getNose_contour() {
        return nose_contour;
    }

    public void setNose_contour(NoseContour nose_contour) {
        this.nose_contour = nose_contour;
    }
}
