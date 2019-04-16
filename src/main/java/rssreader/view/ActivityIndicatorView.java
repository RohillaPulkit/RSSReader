package rssreader.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ActivityIndicatorView extends FlowPane {

    private ProgressIndicator progressIndicator;
    private Label labelMessage;

    public ActivityIndicatorView(String text){

        labelMessage = new Label();
        progressIndicator = new ProgressIndicator();

        setupView(text);
    }

    private void setupView(String text){

        setAlignment(Pos.CENTER);

        labelMessage.setText(text);
        labelMessage.setTextFill(Color.color(72.0/255.0, 72.0/255.0, 72.0/255.0));
        labelMessage.setStyle("-fx-font: 20 Futura;");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(progressIndicator);
        vBox.getChildren().add(labelMessage);

        getChildren().add(vBox);
    }
}
