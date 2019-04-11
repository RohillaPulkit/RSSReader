package rssreader.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rssreader.model.RSSChannel;
import rssreader.utility.DownloadManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/layout/sidebar.fxml"));
        primaryStage.setTitle("RSS Reader");

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/sidebar.css").toExternalForm());

        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
