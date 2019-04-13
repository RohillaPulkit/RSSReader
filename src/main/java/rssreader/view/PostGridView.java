package rssreader.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import rssreader.controller.PostsController;
import rssreader.model.RSSItem;

import java.util.ArrayList;

public class PostGridView extends ScrollPane {

    private GridPane postGridPane;
    private PostsController postsController;

    private ArrayList<RSSItem> rssItemArrayList;
    private int index = 0;
    ColumnConstraints columnConstraints;

    public PostGridView(PostsController postsController, ArrayList<RSSItem> rssItemArrayList){

        this.postsController = postsController;
        this.rssItemArrayList = rssItemArrayList;

        postGridPane = new GridPane();

        setupView();
        updateGridView();
    }

    public void setupView(){

        getStylesheets().add(getClass().getResource("/css/posts.css").toExternalForm());
        getStyleClass().add("pane");

        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setFitToWidth(true);
        setFitToHeight(false);

        setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        postGridPane.getStyleClass().add("pane");
        postGridPane.setHgap(10);
        postGridPane.setVgap(10);
        postGridPane.setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        postGridPane.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        postGridPane.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);

        columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        columnConstraints.setMinWidth(10);
        columnConstraints.setPrefWidth(100);
        columnConstraints.setPercentWidth(50);

        postGridPane.getColumnConstraints().add(columnConstraints);

        setContent(postGridPane);
    }

    public void updateGridView(){

        Task task = new Task() {
            
            @Override
            protected Object call() {

                int numberOfRows = Math.round((float) rssItemArrayList.size() * (float) (2.0 / 3.0));

                for (int gridRow = 0;  gridRow < numberOfRows; gridRow++) {

                    if (gridRow % 2 != 0) {

                        addGridCell(0, gridRow, 2, 1, true);


                    }
                    else {
                        addGridCell(0, gridRow, 1, 1, false);
                        addGridCell(1, gridRow, 1, 1, false);
                    }

                    RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setMinHeight(10);
                    rowConstraints.setMaxHeight(500);
                    rowConstraints.setPrefHeight(200);
                    rowConstraints.setVgrow(Priority.ALWAYS);

                    Platform.runLater(() -> {
                        postGridPane.getRowConstraints().add(rowConstraints);
                    });
                }

                return null;
            }
        };
        new Thread(task).start();


    }

    private void addGridCell(int col, int row, int colSpan, int rowSpan, boolean isOddRow){

        if (index >= rssItemArrayList.size())
            return;

        RSSItem centerItem = rssItemArrayList.get(index++);
        PostGridCell cell = new PostGridCell(centerItem, isOddRow);

        Platform.runLater(() -> {
            postGridPane.add(cell, col, row, colSpan, rowSpan); //node, col, row, width, height

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                PostGridCell tappedCell = (PostGridCell) event.getSource();
                postsController.showPostDetail(tappedCell.getRssItem());
            });
        });

    }
}

