package rssreader.controller;

import javafx.concurrent.Task;
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

    private SidebarController sidebarController;

    private ArrayList<RSSCategory> categories;

    private HashSet<ContentTileCell> selectionModel = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCategories();
    }

    public void initScreen(SidebarController sidebarController){

        this.sidebarController = sidebarController;
    }

    private void getCategories(){

        Task<ArrayList<RSSCategory>> getCategoriesTask = new Task<>() {

            @Override
            public ArrayList<RSSCategory> call() throws Exception {
                return DBManager.getCategories();
            }
        };

        getCategoriesTask.setOnFailed(event -> {
            getCategoriesTask.getException().printStackTrace();
        });

        getCategoriesTask.setOnSucceeded(event ->{
            categories = getCategoriesTask.getValue();
            updateContentPane();
        });

        new Thread(getCategoriesTask).start();
    }

    @FXML
    private void onDownloadButtonClick(MouseEvent event){

        updateCategorySelection();
    }

    private void updateContentPane(){

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

            if(category.getSelected()){

                selectionModel.add(cell);
            }

            tilePane.getChildren().add(cell);
        }

        updateDownloadButtonState();
    }

    public void updateCategorySelection(){

        Task updateCategoryTask = new Task() {

            @Override
            protected Object call() throws Exception {

                DBManager.updateCategoriesSelection(categories);
                return null;
            }
        };

        updateCategoryTask.setOnFailed(event -> {
            updateCategoryTask.getException().printStackTrace();
        });

        updateCategoryTask.setOnSucceeded(event ->{

            sidebarController.onDownloadButtonClick();

        });

        new Thread(updateCategoryTask).start();
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

}
