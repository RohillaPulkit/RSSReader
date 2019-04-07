package rssreader.view;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import rssreader.model.RSSCategory;

import java.io.IOException;

public class ContentTileCell extends Pane{

    @FXML private Label titleLabel;

    @FXML private ImageView imageView;

    @FXML private FlowPane flowPaneOverlay;
    @FXML private FontAwesomeIcon selectionIcon;

    private RSSCategory category;

    private Node root;
    private Boolean isSelected = false;

    public ContentTileCell(RSSCategory category){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/contentTileCell.fxml"));
        fxmlLoader.setController(this);

        try {

            root = fxmlLoader.load();

        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }

        this.category = category;

        setupCell();
    }

    public RSSCategory getCategory(){
        return category;
    }

    public void setupCell(){

        Rectangle clip = new Rectangle(200, 200);
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        setClip(clip);
        getChildren().add(root);
        getStylesheets().add(getClass().getResource("/css/contentTileCell.css").toExternalForm());

        titleLabel.setText(category.getName());

        Task task = new Task<Void>() {

            @Override
            public Void call() {

                Image categoryImage = new Image(category.getImageURL());

                Platform.runLater(() -> {

                    imageView.setImage(categoryImage);
                });

                return null;
            }
        };
        new Thread(task).start();

        updateStyle();
    }

    public void toggleSelection(){

        isSelected = !isSelected;
        updateStyle();
    }

    private void updateStyle(){

        if (isSelected){

            flowPaneOverlay.setStyle("-fx-opacity: 0.6");
            selectionIcon.setOpacity(1);
        }
        else
        {
            flowPaneOverlay.setStyle("-fx-opacity: 0");
            selectionIcon.setOpacity(0);
        }
    }

}
