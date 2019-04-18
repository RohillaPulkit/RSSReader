import org.junit.Test;
import rssreader.controller.PostsController;
import rssreader.database.DBManager;
import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;
import rssreader.utility.DownloadManager;
import rssreader.utility.RSSChannelDownloader;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RSSReaderTest{

    @Test
    public void getCategoriesTest(){

        DBManager.setIsTest(true);

        boolean thrown = false;

        try {
            ArrayList<RSSCategory> categories = DBManager.getCategories();
            assertEquals(categories.size(), 9);
        } catch (SQLException ex) {
            thrown = true;
        }

        assertEquals(thrown, false);
    }

    @Test
    public void getSelectedCategoriesWithChannelTest(){

        DBManager.setIsTest(true);

        boolean thrown = false;

        try {

            ArrayList<RSSCategory> categories = DBManager.getCategories();
            for(RSSCategory category: categories){
                category.setSelected(true);
            }
            DBManager.updateCategoriesSelection(categories);

            ArrayList<RSSCategory> categoriesWithChannels = DBManager.getCategoriesWithChannels();
            assertEquals(categoriesWithChannels.size(), 9);

            for(RSSCategory category : categoriesWithChannels){
                assertTrue(is4or5(category.getChannels().size()));
            }
        } catch (SQLException ex) {
            thrown = true;
        }

        assertEquals(thrown, false);
    }

    private Boolean is4or5(Integer val) {
        if(val == 4 || val == 5) {
            return true;
        }
        return false;
    }

    @Test
    public void getParallelDownloadFlagTest(){

        DBManager.setIsTest(true);

        boolean thrown = false;

        try {

            boolean flag = false;
            DBManager.updateDefaults(flag); //Set DB Flag

            boolean getDBFlag = DBManager.getParallelDownloadFlag();
            assertEquals(getDBFlag, false); //Get DB Flag

        } catch (SQLException ex) {
            thrown = true;
        }

        assertEquals(thrown, false);
    }

    @Test
    public void getClearItemsTest(){

        DBManager.setIsTest(true);

        boolean thrown = false;

        try {

            DBManager.clearItems();

            ArrayList<RSSItem> items = DBManager.getItems(PostsController.SceneMode.NewPosts, null);
            assertEquals(items.size(), 0);

        } catch (SQLException ex) {
            thrown = true;
        }

        assertEquals(thrown, false);
    }

    @Test
    public void RSSChannelDownloaderTest(){

        DBManager.setIsTest(true);

        boolean thrown = false;

        RSSChannel channel = new RSSChannel(0, "", "");
        Thread downloadThread = new Thread(new RSSChannelDownloader(channel));
        try{

            downloadThread.start();
            downloadThread.join();
        }
        catch (InterruptedException ex){

            thrown = true;
        }

        assertFalse(thrown);
    }

    @Test
    public void RSSChannelSerialDownloaderTest(){

        DBManager.setIsTest(true);

        boolean thrown = false;

        try{
            ArrayList<RSSCategory> categories = DBManager.getCategories();
            for(RSSCategory category: categories) {
                category.setSelected(true);
            }
            ArrayList<RSSCategory> categoriesWithChannels = DBManager.getCategoriesWithChannels();
            DownloadManager.startSerialDownload(categoriesWithChannels);
        }
        catch (Exception ex){
            thrown = true;
        }

        assertFalse(thrown);
    }

    @Test
    public void RSSChannelParallelDownloaderTest(){

        DBManager.setIsTest(true);

        boolean thrown = false;

        try{
            ArrayList<RSSCategory> categories = DBManager.getCategories();
            for(RSSCategory category: categories) {
                category.setSelected(true);
            }
            ArrayList<RSSCategory> categoriesWithChannels = DBManager.getCategoriesWithChannels();
            DownloadManager.startParallelDownload(categoriesWithChannels);
        }
        catch (Exception ex){
            thrown = true;
        }

        assertFalse(thrown);
    }
}