package com.jiuan.oa.android.app.andoncontact.im;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/8/3.
 */
public class Information {

    @SerializedName("Type")
    private int type;

    @SerializedName("FontSize")
    private double fontSize;

    @SerializedName("FontName")
    private String fontName;

    @SerializedName("R")
    private int r;

    @SerializedName("G")
    private int g;

    @SerializedName("B")
    private int b;

    @SerializedName("ID")
    private String id;

    @SerializedName("Text")
    private String text;

    @SerializedName("ImageName")
    private String imageName;

    public Information(){
        type = 1;
        fontSize = 15;
        fontName = "微软雅黑";
        r = 225;
        g = 225;
        b = 225;
        imageName = "";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
