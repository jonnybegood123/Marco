package com.jonathanrobins.marco;

/**
 * Created by Jonathan on 7/4/2015.
 */
public class RowItem {
    private int imageId;
    private String name;

    public RowItem(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return name;
    }
    public void setDesc(String desc) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}