package rssreader.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import rssreader.database.DBManager;
import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;
import rssreader.utility.DownloadManager;
import rssreader.view.PlaceHolderView;
import rssreader.view.PostGridView;

import java.util.ArrayList;

public class PostsController{

    public enum SceneMode{

        NewPosts,
        ReadLater,
        Favorites,
        Channel
    }

    @FXML private Label labelTitle;
    @FXML private Label labelSubtitle;

    @FXML private BorderPane masterPane;
    @FXML private AnchorPane detailPane;

    //DetailPane Properties
    @FXML private Label labelDetailTitle;
    @FXML private WebView webViewContent;
    @FXML private FontAwesomeIcon iconFavorite;

    private static final String emptyStar = "STAR_ALT";
    private static final String fullStar = "STAR";

    private SidebarController sidebarController;
    private SceneMode sceneMode;
    private RSSChannel rssChannel;
    private RSSItem selectedItem;
    private long downloadStartTime;

    public void initScene(SidebarController sidebarController, SceneMode sceneMode, RSSChannel rssChannel){

        detailPane.setOpacity(0);
        detailPane.setDisable(true);

        this.sidebarController = sidebarController;
        this.sceneMode = sceneMode;
        this.rssChannel = rssChannel;

        updatePrompts();

        if (sceneMode == SceneMode.NewPosts){

            getCategoriesWithChannels();
        }
        else if(sceneMode == SceneMode.ReadLater){

        }
        else if(sceneMode == SceneMode.Favorites){

        }
        else if (sceneMode == SceneMode.Channel){

            downloadChannel(rssChannel);
        }
    }

    private void getCategoriesWithChannels(){

        Task<ArrayList<RSSCategory>> getCategoriesTask = new Task<>() {

            @Override
            public ArrayList<RSSCategory> call() throws Exception {
                return DBManager.getCategoriesWithChannels();
            }
        };

        getCategoriesTask.setOnFailed(event -> {
            getCategoriesTask.getException().printStackTrace();
        });

        getCategoriesTask.setOnSucceeded(event ->{

            downloadCategoryChannels(getCategoriesTask.getValue());
        });

        new Thread(getCategoriesTask).start();
    }

    private void getItems(){

        Task<ArrayList<RSSItem>> getItemsTask = new Task<>() {

            @Override
            public ArrayList<RSSItem> call() throws Exception {
                return DBManager.getItems();
            }
        };

        getItemsTask.setOnFailed(event -> {
            getItemsTask.getException().printStackTrace();
        });

        getItemsTask.setOnSucceeded(event ->{
            updateCenterPane(getItemsTask.getValue());
        });

        new Thread(getItemsTask).start();
    }

    private void downloadCategoryChannels(ArrayList<RSSCategory> rssCategories){

        downloadStartTime = System.currentTimeMillis();

        Task startDownloadTask = new Task(){

            @Override
            protected Object call() throws Exception {

                DownloadManager.startParallelDownload(rssCategories);
                return null;
            }
        };

        startDownloadTask.setOnRunning(workerStateEvent -> {
            updateCenterPane(null);
        });

        startDownloadTask.setOnSucceeded(event -> {

            long endTime = System.currentTimeMillis();
            System.out.println("Time Taken :"+ (endTime - downloadStartTime) + "ms");
            getItems();
        });

        new Thread(startDownloadTask).start();
    }

    private void downloadChannel(RSSChannel rssChannel){

        Task startDownloadTask = new Task(){

            @Override
            protected Object call() throws Exception {

                DownloadManager.startDownloadForChannel(rssChannel);

                return null;
            }
        };

        startDownloadTask.setOnRunning(workerStateEvent -> {
            updateCenterPane(null);
        });

        startDownloadTask.setOnSucceeded(event -> {
            getItems();
        });

        new Thread(startDownloadTask).start();
    }

    private void updateCenterPane(ArrayList<RSSItem> rssItems){

        if(DownloadManager.isDownloading){

            PlaceHolderView placeHolderView = new PlaceHolderView("Downloading Feeds...");
            masterPane.setCenter(placeHolderView);
        }
        else{

            System.out.println("All Channels Downloaded");

            if (rssItems != null && rssItems.size() > 0){

                PostGridView gridView = new PostGridView(this, rssItems);
                masterPane.setCenter(gridView);
            }
            else
            {
                PlaceHolderView placeHolderView = new PlaceHolderView("Add content to get started.");
                masterPane.setCenter(placeHolderView);
            }

        }
    }

    private void updatePrompts(){

        System.out.println(sceneMode);

        String title = "";

        switch (sceneMode){
            case NewPosts:
                title = "New Posts";
                break;
            case ReadLater:
                title = "Read Later";
                break;
            case Favorites:
                title = "Favorites";
                break;
            case Channel:
                title = rssChannel.getName();
                break;
        }

        labelTitle.setText(title);
        labelSubtitle.setText(""); //Update with the response time
    }

    public void showPostDetail(RSSItem rssItem){

        selectedItem = rssItem;
        updateDetailPane();
    }

    private void updateDetailPane(){

        detailPane.setOpacity(1);
        detailPane.setDisable(false);

        labelDetailTitle.setText(selectedItem.getTitle());

        webViewContent.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/webviewStyle.css").toString());
        webViewContent.getEngine().loadContent(selectedItem.getDescription());
    }

    @FXML
    private void onBackButtonClick(MouseEvent event){

        detailPane.setOpacity(0);
        detailPane.setDisable(true);
    }

    @FXML
    private void onReadLaterButtonClick(MouseEvent event){

        System.out.println("Add to Read Later");
    }

    @FXML
    private void onFavoriteButtonClick(MouseEvent event){

        System.out.println("Add to favorites");
        updateFavoriteIcon();
    }

    private void updateFavoriteIcon(){

        if (iconFavorite.getIconName().equals(emptyStar)){

            iconFavorite.setIconName(fullStar);
        }
        else
        {
            iconFavorite.setIconName(emptyStar);
        }
    }
}
