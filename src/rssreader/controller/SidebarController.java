package rssreader.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import rssreader.view.FeedListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private BorderPane masterPane;

    @FXML
    private Pane newPostsItem;

    @FXML
    private Pane readLaterItem;

    @FXML
    private Pane favoritesItem;


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

        feedListView.getStylesheets().add(getClass().getResource("/rssreader/resources/feedList.css").toExternalForm());
    }

    @FXML
    public void onMenuItemClick(MouseEvent event) throws Exception{

        String mode = "";

        if (event.getSource() == newPostsItem){
            mode = "NewPosts";
        }
        else if (event.getSource() == readLaterItem){
            mode = "ReadLater";
        }
        else if (event.getSource() == favoritesItem){
            mode = "Favorites";
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/rssreader/layout/posts.fxml"));
        Parent root = fxmlLoader.load();
        PostsController controller = fxmlLoader.getController();

        controller.setLabel(mode);
        masterPane.setCenter(root);
    }

    @FXML
    public void onNewPostsClick(MouseEvent event) throws Exception{

        System.out.println(((Control)event.getSource()).getId());

        try{


        }
        catch (Exception e){

        }

    }

    @FXML
    private void onReadLaterClick(MouseEvent event){

        try{

            Parent newPosts = FXMLLoader.load(getClass().getResource("/rssreader/layout/posts.fxml"));
            masterPane.setCenter(newPosts);
        }
        catch (Exception e){

        }
    }

    @FXML
    private void onFavoritesClick(MouseEvent event){

        try{

            Parent newPosts = FXMLLoader.load(getClass().getResource("/rssreader/layout/posts.fxml"));
            masterPane.setCenter(newPosts);
        }
        catch (Exception e){

        }
    }

    @FXML
    private void onAddNewClick(MouseEvent event){

    }
}
