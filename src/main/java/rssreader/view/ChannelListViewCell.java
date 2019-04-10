package rssreader.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import rssreader.model.RSSChannel;

import java.io.IOException;

public class ChannelListViewCell extends ListCell<RSSChannel> {

    @FXML
    private Label titleLabel;

    @FXML private FlowPane flowPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(RSSChannel rssChannel, boolean empty) {
        super.updateItem(rssChannel, empty);

        if (empty || rssChannel == null){

            setText(null);
            setGraphic(null);
        }
        else
        {
            if (mLLoader == null) {

                mLLoader = new FXMLLoader(getClass().getResource("/layout/channelListCell.fxml"));
                mLLoader.setController(this);

                try {

                    mLLoader.load();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            titleLabel.setText(rssChannel.getName());

            setText(null);
            setGraphic(flowPane);
        }
    }
}
