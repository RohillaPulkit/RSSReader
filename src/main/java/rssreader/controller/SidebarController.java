package rssreader.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import rssreader.database.DBManager;
import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;
import rssreader.view.ChannelListViewCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private BorderPane masterPane;

    @FXML
    private Button btnNewPosts, btnReadLater, btnFavorites;

    @FXML private Accordion accordion;

    private ArrayList<RSSCategory> rssCategories;

    public SidebarController(){

        rssCategories = DBManager.getCategoriesWithChannels();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (RSSCategory category : rssCategories){

            ObservableList<RSSChannel> channels = FXCollections.observableArrayList(category.getChannels());

            ListView<RSSChannel> channelListView = new ListView();
            channelListView.setMinSize(100,90);
            channelListView.setOnMouseClicked(e -> onChannelItemClick(channelListView.getSelectionModel().getSelectedItem()));
            channelListView.setCellFactory(listView -> new ChannelListViewCell());
            channelListView.setItems(channels);
            channelListView.getStylesheets().add(getClass().getResource("/css/channelList.css").toExternalForm());

            TitledPane titledPane = new TitledPane();
            titledPane.setText(category.getName());
            titledPane.setContent(channelListView);
            
            accordion.getPanes().add(titledPane);
        }
    }

    @FXML
    public void onMenuButtonClick(MouseEvent event) throws Exception{

        if (event.getSource() == btnNewPosts){

            navigateToPosts(SceneMode.NewPosts);
        }
        else if (event.getSource() == btnReadLater){

            navigateToPosts(SceneMode.ReadLater);

        }
        else if (event.getSource() == btnFavorites){

            navigateToPosts(SceneMode.Favorites);
        }

    }

    private void onChannelItemClick(RSSChannel channel){

        System.out.println("Showing detail for "+channel.getName());

        navigateToPosts(SceneMode.Channel);
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

    public void navigateToPosts(SceneMode sceneMode){

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/posts.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/css/posts.css").toExternalForm());

            PostsController postsController = fxmlLoader.getController();
            postsController.initScene(this, sceneMode);

            masterPane.setCenter(root);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showPostsDetail(RSSItem rssItem, SceneMode sceneMode){

        System.out.println("Showing detail for "+rssItem.getTitle());

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/postDetail.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/css/postDetail.css").toExternalForm());
            PostDetailController detailController = fxmlLoader.getController();
            detailController.initScreen(this, rssItem, sceneMode);

            masterPane.setCenter(root);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
