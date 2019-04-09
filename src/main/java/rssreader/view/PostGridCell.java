package rssreader.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import rssreader.model.RSSItem;

import java.io.IOException;

public class PostGridCell extends Pane {

   @FXML private Label title;
   @FXML private ImageView image;
   @FXML private Label publicationDate;
   @FXML private Label labelChannel;

   private RSSItem item;

   private Node root;

   public  PostGridCell(RSSItem item){

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/postGridCell.fxml"));
      fxmlLoader.setController(this);
      try {

         root = fxmlLoader.load();

      } catch (IOException exception) {

         throw new RuntimeException(exception);
      }
      this.item = item;

      setUpGrid();
   }

    public void setUpGrid(){
//       javafx.scene.shape.Rectangle clip = new Rectangle(200, 200);
//       clip.setArcWidth(40);
//       clip.setArcHeight(40);
//
//       setClip(clip);
       getChildren().add(root);
//       getStylesheets().add(getClass().getResource("/css/posts.css").toExternalForm());

       title.setText(item.getTitle());
       publicationDate.setText(item.getPublicationDate().toString());

       Task task = new Task<Void>() {

          @Override
          public Void call() {

             Image ItemImage = new Image(item.getItemUrl());

             Platform.runLater(() -> {

                image.setImage(ItemImage);
             });

             return null;
          }
       };
       new Thread(task).start();

    }
}
