package rssreader.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import rssreader.model.RSSItem;

public class PostDetailController {

    @FXML private Label labelTitle;
    @FXML private Label labelDescription;

    private RSSItem rssItem;

    public void setRssItem(RSSItem rssItem) {

        this.rssItem = rssItem;

        setupScreen();
    }

    public void setupScreen(){

        labelTitle.setText(rssItem.getTitle());
        labelDescription.setText(rssItem.getDescription());
    }


}
