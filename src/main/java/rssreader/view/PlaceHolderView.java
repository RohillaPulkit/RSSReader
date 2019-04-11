package rssreader.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class PlaceHolderView extends FlowPane{

    private Label labelPlaceHolder;

    public PlaceHolderView(String text){

        labelPlaceHolder = new Label();

        setupView(text);
    }

    private void setupView(String text){

        setAlignment(Pos.CENTER);

        labelPlaceHolder.setText(text);
        labelPlaceHolder.setTextFill(Color.color(72.0/255.0, 72.0/255.0, 72.0/255.0));
        labelPlaceHolder.setStyle("-fx-font: 20 Futura;");

        getChildren().add(labelPlaceHolder);
    }
}
