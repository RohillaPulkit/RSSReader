module application{
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.web;
    requires fontawesomefx;
    requires sqlite.jdbc;
    requires java.sql;
    requires com.rometools.rome;
    requires com.rometools.rome.modules;

    exports rssreader.launcher to javafx.graphics;
    exports rssreader.controller to javafx.fxml;
    opens rssreader.controller to javafx.fxml;
    opens rssreader.view to javafx.fxml;

}