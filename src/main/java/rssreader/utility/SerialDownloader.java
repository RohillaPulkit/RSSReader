package rssreader.utility;

import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;

import java.util.ArrayList;

public class SerialDownloader extends Thread {

    private volatile ArrayList<RSSCategory> rssCategories;
    private volatile boolean isRunning;
    private Thread downloadThread;

    SerialDownloader(ArrayList<RSSCategory> rssCategories){

        this.rssCategories = rssCategories;
        isRunning = true;
    }

    public void terminate(){
        isRunning = false;
    }

    @Override
    public void run() {

        outerloop: for (RSSCategory category : rssCategories){

            for (RSSChannel channel: category.getChannels()){

                if (isRunning){

                    downloadThread = new Thread(new RSSChannelDownloader(channel));

                    try{
                        downloadThread.start();
                        downloadThread.join();
                    }
                    catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                }
                else
                {
                    downloadThread.interrupt();

                    System.out.println("Stopped!!!");
                    isRunning = false;
                    break outerloop;
                }
            }
        }
    }
}
