package rssreader.model;

import java.util.Date;

public class RSSItem {

    private String title;
    private String description;
    private String channelName;

    private String itemURL;
    private Date publicationDate;

    public RSSItem(String title,
                   String description,
                   String channelName,
                   String itemURL,
                   Date publicationDate){
        this.title = title;
        this.description = description;
        this.channelName = channelName;
        this.itemURL = itemURL;
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
    public String getItemUrl(){
        return itemURL;
    }
    public Date getPublicationDate(){
        return publicationDate;
    }
}
