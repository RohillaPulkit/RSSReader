package rssreader.model;

import java.util.Date;

public class RSSItem {

    private String title;
    private String description;
    private String channelName;

    private String imageURL;
    private Date publicationDate;

    public RSSItem(String title,
                   String description,
                   String channelName,
                   String imageURL,
                   Date publicationDate){
        this.title = title;
        this.description = description;
        this.channelName = channelName;
        this.imageURL = imageURL;
        this.publicationDate = publicationDate;
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
    public Date getPublicationDate(){
        return publicationDate;
    }
}
