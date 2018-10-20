package com.fahimabrar.tracker;

/**
 * Created by FAHIM on 7/5/2018.
 */

public class DataModel {

    public String title;
    public String id;
    public String image;

    public String getId() {
        return id;
    }

    public String getImage() {
        return "https://advisor-assistant.000webhostapp.com/image/"+image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
