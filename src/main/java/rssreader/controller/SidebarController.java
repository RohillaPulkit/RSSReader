package rssreader.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getCategoriesWithChannels();
        navigateToPosts(PostsController.SceneMode.NewPosts, null);
    }

    @FXML
    private void onMenuButtonClick(MouseEvent event){

        if (event.getSource() == btnNewPosts){

            navigateToPosts(PostsController.SceneMode.NewPosts, null);
        }
        else if (event.getSource() == btnReadLater){

            navigateToPosts(PostsController.SceneMode.ReadLater, null);

        }
        else if (event.getSource() == btnFavorites){

            navigateToPosts(PostsController.SceneMode.Favorites, null);
        }
    }

    @FXML
    private void onAddContentClick(MouseEvent event){

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/addContent.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/css/addContent.css").toExternalForm());
            AddContentController controller = fxmlLoader.getController();
            controller.initScreen(this);

            masterPane.setCenter(root);
        }
        catch (Exception ex){

            System.out.println(ex);
        }
    }

    private void onChannelItemClick(RSSChannel channel){

        navigateToPosts(PostsController.SceneMode.Channel, channel);
    }

    private void getCategoriesWithChannels(){

        Task<ArrayList<RSSCategory>> getCategoriesTask = new Task<>() {

            @Override
            public ArrayList<RSSCategory> call() throws Exception {
                return DBManager.getCategoriesWithChannels();
            }
        };

        getCategoriesTask.setOnFailed(event -> {
            getCategoriesTask.getException().printStackTrace();
        });

        getCategoriesTask.setOnSucceeded(event ->{
            rssCategories = getCategoriesTask.getValue();
            updateChannelsPane();
        });

        new Thread(getCategoriesTask).start();
    }

    private void updateChannelsPane(){

        accordion.getPanes().clear();

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

    //Public Methods
    public void navigateToPosts(PostsController.SceneMode sceneMode, RSSChannel rssChannel){

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/posts.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/css/posts.css").toExternalForm());

            PostsController postsController = fxmlLoader.getController();
            postsController.initScene(this, sceneMode, rssChannel);

            masterPane.setCenter(root);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showPostsDetail(RSSChannel rssChannel, RSSItem rssItem, PostsController.SceneMode sceneMode){

        System.out.println("Showing detail for "+rssItem.getTitle());

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/postDetail.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/css/postDetail.css").toExternalForm());
            PostDetailController detailController = fxmlLoader.getController();
            detailController.initScreen(this, rssChannel, rssItem, sceneMode);

            masterPane.setCenter(root);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onDownloadButtonClick(){

        getCategoriesWithChannels();
        navigateToPosts(PostsController.SceneMode.NewPosts, null);
    }

}
