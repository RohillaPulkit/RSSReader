package rssreader.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import rssreader.view.FeedListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private BorderPane masterPane;

    @FXML
    private Button btnNewPosts, btnReadLater, btnFavorites;

    @FXML
    private ListView feedListView;

    ObservableList<String> feedList;

    public SidebarController(){

        feedList = FXCollections.observableArrayList();
        feedList.add("1");
        feedList.add("2");
        feedList.add("3");
        feedList.add("4");
        feedList.add("5");
        feedList.add("6");
        feedList.add("7");
        feedList.add("8");
        feedList.add("9");
        feedList.add("10");
        feedList.add("11");
        feedList.add("12");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        feedListView.setItems(feedList);
        feedListView.setCellFactory(feedListView -> new FeedListViewCell());

        feedListView.getStylesheets().add(getClass().getResource("/css/feedList.css").toExternalForm());
    }

    @FXML
    public void onMenuButtonClick(MouseEvent event) throws Exception{

        String mode = "";

        if (event.getSource() == btnNewPosts){

            mode = "NewPosts";

        }
        else if (event.getSource() == btnReadLater){

            mode = "ReadLater";

        }
        else if (event.getSource() == btnFavorites){

            mode = "Favorites";
        }

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/posts.fxml"));
            Parent root = fxmlLoader.load();
            PostsController controller = fxmlLoader.getController();

            controller.setLabel(mode);
            masterPane.setCenter(root);
        }
        catch (Exception ex){
            System.out.println(ex);
        }

    }

    @FXML
    private void onAddContentClick(MouseEvent event) throws Exception{

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/addContent.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/css/addContent.css").toExternalForm());

            masterPane.setCenter(root);
        }
        catch (Exception ex){

            System.out.println(ex);
        }
    }

}
