package rssreader.utility;

import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import javafx.concurrent.Task;
import org.jdom2.Element;
import rssreader.database.DBManager;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;

import javax.lang.model.util.Elements;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

            Date date = new Date();
            String sqlDateFormat = "yyyy-mm-dd hh:mm:ss";
            SimpleDateFormat dateFormatter = new SimpleDateFormat(sqlDateFormat);
            String dateString = dateFormatter.format(date);

            String defaultImageURL = "https://i.pinimg.com/474x/51/e3/41/51e3411c899b3074d6cce3aa5e83fe09.jpg";

            for (SyndEntry entry : feed.getEntries()){

                int category = rssChannel.getCategory();
                String channelName = rssChannel.getName();
                String title = entry.getTitle();
                String description = entry.getDescription().getValue();
                
//                List<Element> arrayList = entry.getForeignMarkup();
//                for(Element ele: arrayList){
//                    System.out.println("ele" + ele);
//                }

                rssItems.add(new RSSItem(category, channelName, title, description, defaultImageURL, dateString));
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
