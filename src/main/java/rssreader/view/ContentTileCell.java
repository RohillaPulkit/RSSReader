package rssreader.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import rssreader.model.RSSCategory;

import java.io.IOException;

public class ContentTileCell extends Pane {

    @FXML private Label titleLabel;

    @FXML private ImageView imageView;

    private Node root;

    public ContentTileCell(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/contentTileCell.fxml"));
        fxmlLoader.setController(this);

        try {

            root = (Node)fxmlLoader.load();

        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }

        Rectangle clip = new Rectangle(200, 200);
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        setClip(clip);
        getChildren().add(root);
        getStylesheets().add(getClass().getResource("/css/contentTileCell.css").toExternalForm());
    }

    public void updateContent(RSSCategory category){

        titleLabel.setText(category.getName());

        Task task = new Task<Void>() {

            @Override
            public Void call() {

                Image categoryImage = new Image(category.getImageURL());

                Platform.runLater(() -> {

                    imageView.setImage(categoryImage);
                });

                return null;
            }
        };
        new Thread(task).start();
    }



}
