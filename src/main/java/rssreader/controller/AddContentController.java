package rssreader.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import rssreader.database.DBManager;
import rssreader.model.RSSCategory;
import rssreader.view.ContentTileCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddContentController implements Initializable {

    @FXML
    private TilePane tilePane;

    private ArrayList<RSSCategory> categories;

    public AddContentController() {

        categories = DBManager.getCategories();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (RSSCategory category : categories){

            ContentTileCell cell = new ContentTileCell();
            cell.updateContent(category);
            tilePane.getChildren().add(cell);

        }


//                for(int i=0; i<filmInfoList.size(); i++){
//                    updateProgress(i+1, filmInfoList.size());
//                    if(filmInfoList.get(i).getImageURL() != null) {
//                        Image searchItemImage = new Image(filmInfoList.get(i).getImageURL());
//                        ImageView searchItemImageView = new ImageView(searchItemImage);
//                        searchItemImageView.setPreserveRatio(true);
//                        Platform.runLater(() -> {
//                            searchmoviesTxwilePane.getChildren().add(searchItemImageView);
//                        });
//                    }
//                }
//        tilePane.getChildren().add(imageView);

    }

}
