package rssreader.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import rssreader.model.RSSItem;
import rssreader.view.FeedListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private BorderPane masterPane;

    @FXML
    private Button btnNewPosts, btnReadLater, btnFavorites;

    @FXML private Accordion accordion;



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
         ListView<String> feedListView = new ListView();
//         feedListView.setItems(feedList);
        feedListView.setMinSize(100,100);
        feedListView.getItems().setAll(feedList);
        feedListView.setOnMouseClicked(e -> clickList(feedListView.getSelectionModel().getSelectedItem()));
        TitledPane t1 = new TitledPane();
        t1.setText("Tech");
        t1.setContent(feedListView);
        TitledPane t2 = new TitledPane("Food", new Button());
        TitledPane t3 = new TitledPane("News", new ListView());
        TitledPane t4 = new TitledPane("Sports",new ListView());
        TitledPane t5 = new TitledPane("comic", new ListView());
        TitledPane t6 = new TitledPane("Marketing", new ListView());
        TitledPane t7 = new TitledPane("Gaming", new ListView());
        TitledPane t8 = new TitledPane("business",new ListView());
        TitledPane t9 = new TitledPane("movies", new ListView());


        accordion.getPanes().addAll(t1, t2, t3, t4, t5, t6, t7, t8, t9);

        //       feedListView.setItems(feedList);
//        feedListView.setCellFactory(feedListView -> new FeedListViewCell());
//
//    accordion.getStylesheets().add(getClass().getResource("/css/.css").toExternalForm());
    }
    public void clickList(String item){
        System.out.println(item);
    }

    @FXML
    public void onMenuButtonClick(MouseEvent event) throws Exception{

        String mode = "";

        if (event.getSource() == btnNewPosts){

            mode = "NewPosts";
            goToPosts();
        }
        else if (event.getSource() == btnReadLater){

            mode = "ReadLater";

        }
        else if (event.getSource() == btnFavorites){

            mode = "Favorites";

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

    public void goToPosts(){

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/posts.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/css/posts.css").toExternalForm());

            PostsController postsController = fxmlLoader.getController();
            postsController.setSidebarController(this);

            masterPane.setCenter(root);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showPostsDetail(RSSItem rssItem){

        System.out.println("Showing detail for "+rssItem.getTitle());
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/postDetail.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/css/postDetail.css").toExternalForm());
            PostDetailController detailController = fxmlLoader.getController();
            detailController.setRssItem(rssItem);

            masterPane.setCenter(root);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
