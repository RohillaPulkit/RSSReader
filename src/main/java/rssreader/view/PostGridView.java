package rssreader.view;


import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import rssreader.controller.SidebarController;
import rssreader.model.RSSItem;

import java.util.ArrayList;
import java.util.Date;

public class PostGridView extends ScrollPane {
    private GridPane postGridPane;
    private SidebarController GridSidebarController;
    private ArrayList<RSSItem> rssItemArrayList;
    private String rssItemTitle;
    private String rssItemDescription;
    private String rssItemChannelName;

    private String rssItemImageURL;
    private Date rssItemPublicationDate;
    private int index = 0;
    public PostGridView(SidebarController GridSidebarController, ArrayList<RSSItem> rssItemArrayList){
        postGridPane = new GridPane();
        this.GridSidebarController = GridSidebarController;
        this.rssItemArrayList = rssItemArrayList;
        setUpGrid();
        updateGridView(rssItemArrayList);

    }
    public void setUpGrid(){
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(200);
        setMinHeight(200);
        setPrefHeight(200);
        setPrefWidth(Control.USE_COMPUTED_SIZE);
        postGridPane.setHgap(10);
        postGridPane.setVgap(5);
        getStyleClass().add("cell")      ;
        getStylesheets().add(getClass().getResource("/css/postGridCell.css").toExternalForm());
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());

        clip.setArcWidth(5);
        clip.setArcHeight(5);

        setClip(clip);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.SOMETIMES);
        rowConstraints.setValignment(VPos.TOP);
        rowConstraints.setFillHeight(true);
        rowConstraints.setPercentHeight(80);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        columnConstraints.setMinWidth(10);
        columnConstraints.setPrefWidth(100);
        columnConstraints.setPercentWidth(50);

        postGridPane.getRowConstraints().add(rowConstraints);
        postGridPane.getColumnConstraints().add(columnConstraints);


        RowConstraints dateRowConstraints = new RowConstraints();
        dateRowConstraints.setPercentHeight(10);
        dateRowConstraints.setFillHeight(true);
        dateRowConstraints.setVgrow(Priority.ALWAYS);

        postGridPane.getRowConstraints().add(dateRowConstraints);

        RowConstraints channelRowConstraints = new RowConstraints();
        channelRowConstraints.setPercentHeight(10);
        channelRowConstraints.setFillHeight(true);
        channelRowConstraints.setVgrow(Priority.ALWAYS);

        postGridPane.getRowConstraints().add(channelRowConstraints);

        ColumnConstraints column2Constraints = new ColumnConstraints();
        column2Constraints.setHgrow(Priority.SOMETIMES);
        column2Constraints.setPercentWidth(50);

        postGridPane.getColumnConstraints().add(column2Constraints);
    }
    public void updateGridView(ArrayList<RSSItem> rssItemArrayList){
        int numberOfRows = Math.round((float) rssItemArrayList.size() * (float) (2.0 / 3.0));

        postGridPane.getRowConstraints().clear();

        for (int gridRow = 0;  gridRow< numberOfRows; gridRow++) {

            if (gridRow % 2 != 0) {

                addGridCell(0, gridRow, 2, 1);

            }
            else {

                addGridCell(0, gridRow, 1, 1);
                addGridCell(1, gridRow, 1, 1);
            }

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(200);
            rowConstraints.setMaxHeight(200);
            rowConstraints.setPrefHeight(200);
            rowConstraints.setVgrow(Priority.ALWAYS);

            postGridPane.getRowConstraints().add(rowConstraints);

        }
    }
    private void addGridCell(int col, int row, int colSpan, int rowSpan){

        RSSItem centerItem = rssItemArrayList.get(index++);
        PostGridCell cell = new PostGridCell(centerItem);
        postGridPane.add(cell, col, row, colSpan, rowSpan); //node, col, row, width, height
        cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

            PostGridCell tappedCell = (PostGridCell) event.getSource();
            //gridShowPostDetail(tappedCell.getRssItem());
        });
    }

//    private void gridShowPostDetail(RSSItem rssItem){
//
//        GridSidebarController.showPostsDetail(rssItem.getChannelName(), rssItem);
//    }


}

