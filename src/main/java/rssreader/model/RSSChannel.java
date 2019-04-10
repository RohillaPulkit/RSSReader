package rssreader.model;

public class RSSChannel {

    private String name;
    private String url;

    public RSSChannel(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
