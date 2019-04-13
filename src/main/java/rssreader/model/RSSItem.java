package rssreader.model;

public class RSSItem {

    private int category;
    private String channelName;

    private String title;
    private String description;

    private String imageURL;
    private String publicationDate;

    private Boolean isReadLater;
    private Boolean isFavorite;

    public RSSItem(int category,
                   String channelName,
                   String title,
                   String description,
                   String imageURL,
                   String publicationDate,
                   Boolean isReadLater,
                   Boolean isFavorite){
        this.category = category;
        this.title = title;
        this.description = description;
        this.channelName = channelName;
        this.imageURL = imageURL;
        this.publicationDate = publicationDate;
        this.isReadLater = isReadLater;
        this.isFavorite = isFavorite;
    }

    public void setReadLater(Boolean readLater) {
        isReadLater = readLater;
    }
    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
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
    public Boolean getReadLater() {
        return isReadLater;
    }
    public Boolean getFavorite() {
        return isFavorite;
    }
}
