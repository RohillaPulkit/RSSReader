package rssreader.model;

import java.util.ArrayList;

public class RSSCategory {

    private int id;
    private String name;
    private String imageURL;

    public  RSSCategory(int id, String name, String imageURL){

        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getImageURL(){
        return imageURL;
    }

}
