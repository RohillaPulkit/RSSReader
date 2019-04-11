package rssreader.utility;


import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;

import java.util.ArrayList;

public class DownloadManager {

    public static boolean isDownloading = false;

    public static void startSerialDownload(ArrayList<RSSCategory> rssCategories){

        DownloadManager.isDownloading = true;

        for (RSSCategory category : rssCategories){

            for (RSSChannel channel: category.getChannels()){

                Thread downloadThread = new Thread(new RSSChannelDownloader(channel));

                try{
                    downloadThread.start();
                    downloadThread.join();
                }
                catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }

        DownloadManager.isDownloading = false;
    }

}

