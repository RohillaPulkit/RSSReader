package rssreader.utility;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.modules.mediarss.types.MediaContent;
import com.rometools.modules.mediarss.types.MediaGroup;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import javafx.concurrent.Task;

import rssreader.database.DBManager;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

            Date date = feed.getPublishedDate();
            if (date == null){
                date = new Date();
            }

            String sqlDateFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormatter = new SimpleDateFormat(sqlDateFormat);

            String defaultImageURL = "https://drive.google.com/uc?id=1pbu-afM8sZm3QKSfO3DBNbT699cn36fu";

            for (SyndEntry entry : feed.getEntries()){

                int category = rssChannel.getCategory();
                String channelName = rssChannel.getName();
                String title = entry.getTitle();
                String description = entry.getDescription() != null ? entry.getDescription().getValue() : "";
                Boolean isReadLater = false;
                Boolean isFavorite = false;
                Date pubDate = entry.getPublishedDate();

                if(pubDate != null){
                    date = pubDate;
                }

                String imageURL = null;

                outerloop: for (Module module : entry.getModules()) {
                    if (module instanceof MediaEntryModule) {
                        MediaEntryModule media = (MediaEntryModule)module;
                        for (MediaGroup group : media.getMediaGroups()) {
                            for(MediaContent content : group.getContents()){
                                imageURL = content.getReference().toString();
                                break outerloop;
                            }
                        }
                    }
                }

                if(imageURL == null){

                    imageURL = defaultImageURL;
                }

                String dateString = dateFormatter.format(date);

                rssItems.add(new RSSItem(category, channelName, title, description, imageURL,
                        dateString, isReadLater, isFavorite));
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
