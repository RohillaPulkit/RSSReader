package rssreader.utility;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import javafx.concurrent.Task;
import rssreader.database.DBManager;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;

import java.net.URL;
import java.util.ArrayList;

public class RSSChannelDownloader implements Runnable{

    RSSChannel rssChannel;

    public RSSChannelDownloader(RSSChannel rssChannel){
        this.rssChannel = rssChannel;
    }

    @Override
    public void run() {

        System.out.println("Downloading..."+rssChannel.getName());
        ArrayList<RSSItem> rssItems;

        try {
            URL feedUrl = new URL(rssChannel.getUrl());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            rssItems = new ArrayList<>();

            for (SyndEntry entry : feed.getEntries()){

                int category = rssChannel.getCategory();
                String channelName = rssChannel.getName();
                String title = entry.getTitle();
                String description = entry.getDescription().getValue();
                // Add/find  date and image url

                rssItems.add(new RSSItem(category, channelName, title, description, "", ""));
            }

            Task task = new Task() {
                @Override
                protected Object call() throws Exception {

                    DBManager.insertRSSItems(rssItems);

                    return null;
                }
            };
            Thread insertDataThread = new Thread(task);

            try {

                insertDataThread.start();
                insertDataThread.join();
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }

            System.out.println("Download Finished For "+rssChannel.getName());
        }
        catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage() + "For Channel "+rssChannel.getName());
        }
    }
}
