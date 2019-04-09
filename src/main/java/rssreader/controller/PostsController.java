package rssreader.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import rssreader.model.RSSItem;
import rssreader.view.ContentTileCell;
import rssreader.view.PostGridCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PostsController implements Initializable {

    @FXML
    private GridPane gridPane;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<RSSItem> itemArrayList = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            itemArrayList.add(new RSSItem(String.valueOf(i), "Something", "One", "https://stmed.net/sites/default/files/styles/320x240/public/lakes-wallpapers-27929-5997666.jpg?itok=PgxpBNEY", new Date()));
        }

        int numberOfRows = Math.round((float) itemArrayList.size() * (float) (2.0 / 3.0));
        int itemIndex = 0;

        gridPane.getRowConstraints().clear();

        for (int row = 0; row < numberOfRows; row++) {

            if (row % 2 != 0) {

                RSSItem centerItem = itemArrayList.get(itemIndex++);
                PostGridCell cell = new PostGridCell(centerItem);
                gridPane.add(cell, 0, row, 2, 1); //node, col, row, width, height
                cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

                    PostGridCell tappedCell = (PostGridCell) event.getSource();
                    System.out.println(tappedCell.getRssItem().getTitle());
                });

            }
            else {

                RSSItem leftItem = itemArrayList.get(itemIndex++);
                PostGridCell leftCell = new PostGridCell(leftItem);
                gridPane.add(leftCell, 0, row, 1, 1); //node, col, row
                leftCell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

                    PostGridCell tappedCell = (PostGridCell) event.getSource();
                    System.out.println(tappedCell.getRssItem().getTitle());

                });

                RSSItem rightItem = itemArrayList.get(itemIndex++);
                PostGridCell rightCell = new PostGridCell(rightItem);
                gridPane.add(rightCell, 1, row, 1, 1);
                rightCell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

                    PostGridCell tappedCell = (PostGridCell) event.getSource();
                    System.out.println(tappedCell.getRssItem().getTitle());
                });
            }

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(200);
            rowConstraints.setMaxHeight(200);
            rowConstraints.setPrefHeight(200);
            rowConstraints.setVgrow(Priority.ALWAYS);

            gridPane.getRowConstraints().add(rowConstraints);

        }

    }
}
