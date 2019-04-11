package rssreader.model;

import java.util.ArrayList;

public class RSSCategory {

    private int id;
    private String name;
    private String imageURL;
    private Boolean isSelected;
    private ArrayList<RSSChannel> channels;

    public RSSCategory(int id, String name, String imageURL, Boolean isSelected){

        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.isSelected = isSelected;
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

    public Boolean getSelected() {
        return isSelected;
    }

    public void addChannel(RSSChannel channel){

        if (channels == null){

            channels = new ArrayList<>();
        }

        channels.add(channel);
    }

    public ArrayList<RSSChannel> getChannels() {
        return channels;
    }

}
