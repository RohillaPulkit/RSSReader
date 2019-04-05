package rssreader.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PostsController {

    @FXML
    private Label titleLabel;

    public void setLabel(String title){

        titleLabel.setText(title);
    }
}
