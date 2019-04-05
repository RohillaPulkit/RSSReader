package rssreader.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class FeedListViewCell extends ListCell<String> {

    @FXML
    private Label titleLabel;

    @FXML
    private FlowPane flowPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(String feedTitle, boolean empty) {
        super.updateItem(feedTitle, empty);

        if (empty || feedTitle == null){

            setText(null);
            setGraphic(null);
        }
        else
        {
            if (mLLoader == null) {

                mLLoader = new FXMLLoader(getClass().getResource("/layout/feedListCell.fxml"));
                mLLoader.setController(this);

                try {

                    mLLoader.load();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            getStylesheets().add(getClass().getResource("/rssreader/resources/feedList.css").toExternalForm());

            titleLabel.setText(feedTitle);

            setText(null);
            setGraphic(flowPane);
        }
    }
}
