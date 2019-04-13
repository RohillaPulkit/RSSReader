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

            String defaultImageURL = "https://i.pinimg.com/474x/51/e3/41/51e3411c899b3074d6cce3aa5e83fe09.jpg";

            for (SyndEntry entry : feed.getEntries()){

                int category = rssChannel.getCategory();
                String channelName = rssChannel.getName();
                String title = entry.getTitle();
                String description = entry.getDescription().getValue();
                Boolean isReadLater = false;
                Boolean isFavorite = false;
                Date pubDate = entry.getPublishedDate();

                if(pubDate != null){
                    date = pubDate;
                }

//                List<Element> arrayList = entry.getForeignMarkup();
//                for(Element ele: arrayList){
//                    System.out.println("ele" + ele);
//                }

                String dateString = dateFormatter.format(date);

                rssItems.add(new RSSItem(category, channelName, title, description, defaultImageURL,
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
