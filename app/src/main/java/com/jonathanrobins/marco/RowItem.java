package com.jonathanrobins.marco;

/**
 * Created by Jonathan on 7/4/2015.
 */
public class RowItem {
    private int imageId;
    private String name;
    private int checkbox;

    public RowItem(int imageId, String name, int imageId2) {
        this.imageId = imageId;
        this.name = name;
        this.checkbox = imageId2;
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
    public int getCheckbox() {
        return checkbox;
    }
    public void setCheckbox(int checkbox) {
        this.checkbox = checkbox;
    }
    @Override
    public String toString() {
        return name;
    }
}