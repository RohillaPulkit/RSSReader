package rssreader.utility;


import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {

    public static boolean isDownloading = false;

    public static void startDownloadForChannel(RSSChannel rssChannel){

        DownloadManager.isDownloading = true;

        Thread downloadThread = new Thread(new RSSChannelDownloader(rssChannel));

        try{
            downloadThread.start();
            downloadThread.join();
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        DownloadManager.isDownloading = false;

    }

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

    public static void startParallelDownload(ArrayList<RSSCategory> rssCategories){

        DownloadManager.isDownloading = true;

        ExecutorService executorDownload = Executors.newFixedThreadPool(20);

       for (RSSCategory category : rssCategories){
           for (RSSChannel channel: category.getChannels()){
               executorDownload.execute(new RSSChannelDownloader(channel));
           }
       }
       try{
           executorDownload.shutdown();
           while (!executorDownload.isTerminated()) {
               Thread.sleep(100);
           }
       }catch (InterruptedException ex){
           ex.printStackTrace();
       }

       DownloadManager.isDownloading = false;
   }

}

