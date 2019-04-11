package rssreader.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;
import rssreader.view.PlaceHolderView;
import rssreader.view.PostGridCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PostsController implements Initializable {

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        itemArrayList = new ArrayList<>();
//        for (int i = 0; i <= 10; i++) {
//            itemArrayList.add(new RSSItem(String.valueOf(i), "Something", "One", "https://stmed.net/sites/default/files/styles/320x240/public/lakes-wallpapers-27929-5997666.jpg?itok=PgxpBNEY", new Date()));
//        }

        if (itemArrayList.size() == 0){

            PlaceHolderView placeHolderView = new PlaceHolderView("Start by adding content");
            masterPane.setCenter(placeHolderView);
        }
        else
        {
            updateGrid();
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

    private void updateGrid(){

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
