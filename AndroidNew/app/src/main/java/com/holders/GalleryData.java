package com.holders;

public class GalleryData {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public GalleryData(String title, int image) {

        this.title = title;
        this.image = image;
    }

    String title;
    int image;
}
