package rssreader.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import rssreader.database.DBManager;
import rssreader.model.RSSCategory;
import rssreader.view.ContentTileCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class AddContentController implements Initializable {

    @FXML private TilePane tilePane;
    @FXML private Button btnDownload;

    private ArrayList<RSSCategory> categories;

    private HashSet<ContentTileCell> selectionModel = new HashSet<>();

    public AddContentController() {

        categories = DBManager.getCategories();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (RSSCategory category : categories){

            ContentTileCell cell = new ContentTileCell(category);

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

                ContentTileCell tappedCell = (ContentTileCell) event.getSource();

                if (selectionModel.contains(tappedCell)){

                    selectionModel.remove(tappedCell);
                }
                else
                {
                    selectionModel.add(tappedCell);
                }

                tappedCell.toggleSelection();

                updateDownloadButtonState();
            });

            tilePane.getChildren().add(cell);
        }

        updateDownloadButtonState();
    }

    private void updateDownloadButtonState(){

        if (selectionModel.size() > 0){

            btnDownload.setDisable(false);
        }
        else
        {
            btnDownload.setDisable(true);
        }
    }

    @FXML
    private void onDownloadButtonClick(MouseEvent event){

        // logging
        System.out.println("Dowloading :");
        selectionModel.forEach(n -> System.out.println(n.getCategory().getName()));

    }
}
