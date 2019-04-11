package rssreader.utility;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import rssreader.model.RSSChannel;

import java.net.URL;

public class RSSChannelDownloader implements Runnable{

    RSSChannel rssChannel;

    public RSSChannelDownloader(RSSChannel rssChannel){
        this.rssChannel = rssChannel;
    }

    @Override
    public void run() {

        System.out.println("Downloading..."+rssChannel.getName());

        try {
            URL feedUrl = new URL(rssChannel.getUrl());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            System.out.println("Download Finished For "+rssChannel.getName());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
}
