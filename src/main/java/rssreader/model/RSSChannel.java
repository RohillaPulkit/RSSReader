package rssreader.model;

public class RSSChannel {

    private int category;
    private String name;
    private String url;

    public RSSChannel(int category,
                      String name,
                      String url){
        this.category = category;
        this.name = name;
        this.url = url;
    }

    public int getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
