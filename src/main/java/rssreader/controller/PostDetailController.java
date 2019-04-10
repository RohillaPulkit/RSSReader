package rssreader.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import rssreader.model.RSSItem;

public class PostDetailController {

    @FXML private Label labelTitle;
    @FXML private WebView webViewContent;
    @FXML private FontAwesomeIcon iconFavorite;

    private SidebarController sidebarController;
    private RSSItem rssItem;
    private SceneMode sceneMode;

    private static final String emptyStar = "STAR_ALT";
    private static final String fullStar = "STAR";

    public void initScreen(SidebarController sidebarController, RSSItem rssItem, SceneMode sceneMode){

        this.sidebarController = sidebarController;
        this.rssItem = rssItem;
        this.sceneMode = sceneMode;

        setupScreen();
    }

    private void setupScreen(){

        labelTitle.setText(rssItem.getTitle());

        webViewContent.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/webviewStyle.css").toString());
        webViewContent.getEngine().loadContent(rssItem.getDescription());
    }

    @FXML
    private void onBackButtonClick(MouseEvent event){

        sidebarController.navigateToPosts(sceneMode);
    }

    @FXML
    private void onReadLaterButtonClick(MouseEvent event){

        System.out.println("Add to Read Later");
    }

    @FXML
    private void onFavoriteButtonClick(MouseEvent event){

        System.out.println("Add to favorites");
        updateFavoriteIcon();
    }

    private void updateFavoriteIcon(){

        if (iconFavorite.getIconName().equals(emptyStar)){

            iconFavorite.setIconName(fullStar);
        }
        else
        {
            iconFavorite.setIconName(emptyStar);
        }
    }


}
