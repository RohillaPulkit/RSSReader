package rssreader.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import rssreader.model.RSSItem;
import rssreader.view.PostGridCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

enum SceneMode{

    NewPosts,
    ReadLater,
    Favorites,
    Channel
}

public class PostsController implements Initializable {

    @FXML
    private GridPane gridPane;

    private ArrayList<RSSItem> itemArrayList;

    private SidebarController sidebarController;
    private SceneMode sceneMode;
    private int itemIndex = 0;

    public void initScene(SidebarController sidebarController, SceneMode sceneMode){
        this.sidebarController = sidebarController;
        this.sceneMode = sceneMode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        itemArrayList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            itemArrayList.add(new RSSItem(String.valueOf(i), "Something", "One", "https://stmed.net/sites/default/files/styles/320x240/public/lakes-wallpapers-27929-5997666.jpg?itok=PgxpBNEY", new Date()));
        }

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

        sidebarController.showPostsDetail(rssItem, sceneMode);
    }
}
