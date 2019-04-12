package rssreader.model;

import java.util.Date;

public class RSSItem {

    private String title;
    private String description;
    private String channelName;
    private int category;

    private String imageURL;
    private String publicationDate;

    public RSSItem(int category,
                   String channelName,
                   String title,
                   String description,
                   String imageURL,
                   String publicationDate){
        this.category = category;
        this.title = title;
        this.description = description;
        this.channelName = channelName;
        this.imageURL = imageURL;
        this.publicationDate = publicationDate;
    }

    public int getCategory() {
        return category;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getChannelName() {
        return channelName;
    }
    public String getImageURL(){
        return imageURL;
    }
    public String getPublicationDate(){
        return publicationDate;
    }
}
