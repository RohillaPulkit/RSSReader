package rssreader.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import rssreader.model.RSSItem;

public class PostGridCell extends GridPane {
    private Label labelTitle;
    private Label labelDate;
    private Label labelChannelName;
    private ImageView imageView;

    private RSSItem rssItem;

    public PostGridCell(RSSItem rssItem){
        labelTitle = new Label();
        labelDate = new Label();
        labelChannelName= new Label();
        imageView = new ImageView();

        labelTitle.setWrapText(true);

        this.rssItem = rssItem;

        setUpGrid();
    }

    public RSSItem getRssItem(){
        return rssItem;
    }

    private void setUpGrid(){

        setMargin(labelTitle, new Insets(0,0,0,10));
        setMargin(labelDate, new Insets(0,0,0,10));
        setMargin(labelChannelName, new Insets(0,0,0,10));

        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(200);
        setMinHeight(200);
        setPrefHeight(200);
        setPrefWidth(Control.USE_COMPUTED_SIZE);
        setHgap(10);
        setVgap(5);

        getStyleClass().add("cell")      ;
        getStylesheets().add(getClass().getResource("/css/postGridCell.css").toExternalForm());

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());

        clip.setArcWidth(5);
        clip.setArcHeight(5);

        setClip(clip);

        imageView.fitWidthProperty().bind(widthProperty().multiply(0.5));
        imageView.fitHeightProperty().bind(heightProperty());

        add(imageView,0,0,3,1);

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

        getRowConstraints().add(rowConstraints);
        getColumnConstraints().add(columnConstraints);

        add(labelTitle,1,0);

        add(labelDate,1,1);
        RowConstraints dateRowConstraints = new RowConstraints();
        dateRowConstraints.setPercentHeight(10);
        dateRowConstraints.setFillHeight(true);
        dateRowConstraints.setVgrow(Priority.ALWAYS);

        getRowConstraints().add(dateRowConstraints);

        add(labelChannelName,1,2);
        RowConstraints channelRowConstraints = new RowConstraints();
        channelRowConstraints.setPercentHeight(10);
        channelRowConstraints.setFillHeight(true);
        channelRowConstraints.setVgrow(Priority.ALWAYS);

        getRowConstraints().add(channelRowConstraints);

        ColumnConstraints column2Constraints = new ColumnConstraints();
        column2Constraints.setHgrow(Priority.SOMETIMES);
        column2Constraints.setPercentWidth(50);

        getColumnConstraints().add(column2Constraints);

        labelTitle.setText(rssItem.getTitle());
        labelDate.setText(rssItem.getPublicationDate().toString());
        labelChannelName.setText(rssItem.getChannelName());

        Task<Image> getImageTask = new Task<>() {

            @Override
            protected Image call() throws Exception {

                Image itemImage = new Image(rssItem.getImageURL());
                return itemImage;
            }
        };

        getImageTask.setOnSucceeded(event -> {
            imageView.setImage(getImageTask.getValue());
        });
        new Thread(getImageTask).start();
    }
}
