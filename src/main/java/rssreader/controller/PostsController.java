package rssreader.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import rssreader.model.RSSCategory;
import rssreader.model.RSSItem;
import rssreader.view.ContentTileCell;
import rssreader.view.PostGridCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PostsController implements Initializable {

    @FXML private GridPane gridPane;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<RSSItem> itemArrayList = new ArrayList<>();
        itemArrayList.add(new RSSItem("One", "Something", "One", "https://media.gettyimages.com/photos/robin-picture-id471196487?s=612x612",new Date()));
        itemArrayList.add(new RSSItem("Two", "Something", "One", "https://media.gettyimages.com/photos/robin-picture-id471196487?s=612x612", new Date()));
        itemArrayList.add(new RSSItem("Three", "Something", "One", "https://media.gettyimages.com/photos/robin-picture-id471196487?s=612x612", new Date()));
        itemArrayList.add(new RSSItem("Four", "Something", "One", "https://media.gettyimages.com/photos/robin-picture-id471196487?s=612x612", new Date()));
        itemArrayList.add(new RSSItem("Five", "Something", "One", "https://media.gettyimages.com/photos/robin-picture-id471196487?s=612x612", new Date()));
        itemArrayList.add(new RSSItem("Six", "Something", "One", "https://media.gettyimages.com/photos/robin-picture-id471196487?s=612x612", new Date()));
        itemArrayList.add(new RSSItem("Seven", "Something", "One", "https://media.gettyimages.com/photos/robin-picture-id471196487?s=612x612", new Date()));
        itemArrayList.add(new RSSItem("Eight", "Something", "One", "https://media.gettyimages.com/photos/robin-picture-id471196487?s=612x612", new Date()));

        int numberOfRows = Math.round((float)itemArrayList.size()*(float)(2.0/3.0));
        int itemIndex = 0;

        gridPane.setGridLinesVisible(true);
        gridPane.getRowConstraints().clear();

        for(int row = 0; row < numberOfRows; row++){

            if(row%2 != 0){

                RSSItem centerItem = itemArrayList.get(itemIndex++);
//                PostGridCell cell = new PostGridCell(centerItem);
//                cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                Button btn = new Button("ONE");
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                gridPane.add(btn, 0, row, 2, 1); //node, col, row, width, height
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setMinHeight(200);
                rowConstraints.setMaxHeight(200);
                rowConstraints.setPrefHeight(200);
                rowConstraints.setVgrow(Priority.ALWAYS);

                gridPane.getRowConstraints().add(rowConstraints);
            }
            else {

                RSSItem leftItem = itemArrayList.get(itemIndex++);
                PostGridCell leftCell = new PostGridCell(leftItem);

                gridPane.add(leftCell, 0,row ); //node, col, row

                RSSItem rightItem = itemArrayList.get(itemIndex++);
                PostGridCell rightCell = new PostGridCell(rightItem);

                gridPane.add(rightCell, 1,row);

                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setMinHeight(200);
                rowConstraints.setMaxHeight(200);
                rowConstraints.setPrefHeight(200);
                rowConstraints.setVgrow(Priority.ALWAYS);
                gridPane.getRowConstraints().add(rowConstraints);
            }
        }


//        int column = 0;
//        int row = 0;
//            for (RSSItem item : itemArrayList){
//
//                PostGridCell cell = new PostGridCell(item);
//
////                cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
////
////                    PostGridCell tappedCell = (PostGridCell) event.getSource();
////
//////                    tappedCell.showDownload();
////
////                });
//                 gridPane.add(cell,column++,row++);
//            }

    }
}
