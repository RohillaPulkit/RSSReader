package rssreader.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import rssreader.database.DBManager;
import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;
import rssreader.utility.DownloadManager;
import rssreader.view.PlaceHolderView;
import rssreader.view.PostGridCell;

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
    @FXML private GridPane gridPane;

    private ArrayList<RSSItem> itemArrayList;

    private SidebarController sidebarController;
    private SceneMode sceneMode;
    private RSSChannel rssChannel;

    private int itemIndex = 0;

    public void initScene(SidebarController sidebarController, SceneMode sceneMode, RSSChannel rssChannel){
        this.sidebarController = sidebarController;
        this.sceneMode = sceneMode;
        this.rssChannel = rssChannel;

        updatePrompts();
        getCategoriesWithChannels();
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

    private void downloadCategoryChannels(ArrayList<RSSCategory> rssCategories){

        Task startDownloadTask = new Task(){

            @Override
            protected Object call() throws Exception {

                DownloadManager.startSerialDownload(rssCategories);

                return null;
            }
        };

        startDownloadTask.setOnRunning(workerStateEvent -> {
            updatePlaceHolder();
        });
        startDownloadTask.setOnSucceeded(event -> {

            System.out.println("All Channels Downloaded");
        });

        new Thread(startDownloadTask).start();
    }

    private void updatePlaceHolder(){

        if(DownloadManager.isDownloading){

            PlaceHolderView placeHolderView = new PlaceHolderView("Downloading Feeds...");
            masterPane.setCenter(placeHolderView);
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

    private void updateGrid(){ //Take This To PostView File

        int numberOfRows = Math.round((float) itemArrayList.size() * (float) (2.0 / 3.0));

        gridPane.getRowConstraints().clear();

        for (int row = 0; row < numberOfRows; row++) {

            if (row % 2 != 0) {

                addGridCell(0, row, 2, 1);

            }
            else {

                addGridCell(0, row, 1, 1);
                addGridCell(1, row, 1, 1);
            }

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(200);
            rowConstraints.setMaxHeight(200);
            rowConstraints.setPrefHeight(200);
            rowConstraints.setVgrow(Priority.ALWAYS);

            gridPane.getRowConstraints().add(rowConstraints);

        }
    }

    private void addGridCell(int col, int row, int colSpan, int rowSpan){

        RSSItem centerItem = itemArrayList.get(itemIndex++);
        PostGridCell cell = new PostGridCell(centerItem);
        gridPane.add(cell, col, row, colSpan, rowSpan); //node, col, row, width, height
        cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

            PostGridCell tappedCell = (PostGridCell) event.getSource();
            showPostDetail(tappedCell.getRssItem());
        });
    }

    private void showPostDetail(RSSItem rssItem){

        sidebarController.showPostsDetail(rssChannel, rssItem, sceneMode);
    }
}
