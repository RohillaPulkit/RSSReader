package rssreader.utility;


import java.net.URL;
import java.io.InputStreamReader;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import rssreader.model.RSSChannel;

import java.net.URL;

    public class DownloadManager {

        public void startDownload(RSSChannel rssChannel){

            System.out.println("Downloading...");
            try {
                URL feedUrl = new URL(rssChannel.getUrl());
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

                System.out.println(feed);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: "+ex.getMessage());
            }
        }
    }
//initWithChannel fn

