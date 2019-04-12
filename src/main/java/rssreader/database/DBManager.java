package rssreader.database;

import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;
import rssreader.model.RSSItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DBManager {

    public static Connection connector(){

        try{

            Connection connection = DriverManager.getConnection("jdbc:sqlite:database//rssreader.sqlite");
            return connection;
        }
        catch (Exception ex){
            return null;
        }
    }

    public static ArrayList<RSSCategory> getCategories() throws SQLException{

        ArrayList<RSSCategory> list = new ArrayList<>();

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * from Category";

        try {

            dbConnection = DBManager.connector();
            preparedStatement = dbConnection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String imageURL = resultSet.getString("ImageURL");
                Boolean isSelected = resultSet.getInt("IsSelected") == 1 ? true : false;

                RSSCategory category = new RSSCategory(id, name, imageURL, isSelected);
                list.add(category);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

            return list;
        }
    }

    public static ArrayList<RSSCategory> getCategoriesWithChannels() throws SQLException{

        HashMap<Integer, RSSCategory> map = new HashMap<>();

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT Category.ID as ID," +
                "Category.Name as CategoryName," +
                "Category.ImageURL," +
                "Channel.Name as ChannelName," +
                "Channel.URL" +
                " from Category, Channel WHERE Category.ID = Channel.Category AND Category.IsSelected = 1";

        try {

            dbConnection = DBManager.connector();
            preparedStatement = dbConnection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){

                int id = resultSet.getInt("ID");
                String name = resultSet.getString("CategoryName");
                String imageURL = resultSet.getString("ImageURL");
                String channelName = resultSet.getString("ChannelName");
                String channelURL = resultSet.getString("URL");
                Boolean isSelected = true; //True as we will getting channels for selected categories only

                RSSCategory category = map.getOrDefault(id, new RSSCategory(id, name, imageURL, isSelected));
                RSSChannel channel = new RSSChannel(id, channelName, channelURL);
                category.addChannel(channel);

                map.put(id, category);
            }
        }
        catch (Exception ex){

            System.out.println(ex);
        }
        finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

            ArrayList<RSSCategory> list = new ArrayList<>(map.values());
            return list;
        }
    }

    public static ArrayList<RSSItem> getItems() throws SQLException{

        ArrayList<RSSItem> list = new ArrayList<>();

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * from Item";

        try {

            dbConnection = DBManager.connector();
            preparedStatement = dbConnection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int category = resultSet.getInt("Category");
                String channelName = resultSet.getString("ChannelName");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String publicationDate = resultSet.getString("PublicationDate");
                String imageURL = resultSet.getString("ImageURL");

                RSSItem item = new RSSItem(category, channelName, title, description, imageURL, publicationDate);
                list.add(item);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

            return list;
        }
    }

    public static void updateCategoriesSelection(ArrayList<RSSCategory> categories) throws SQLException{

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE Category SET IsSelected = ? WHERE ID = ?";

        try {

            dbConnection = DBManager.connector();
            dbConnection.setAutoCommit(false); //Batch Update

            preparedStatement = dbConnection.prepareStatement(updateQuery);

            for (RSSCategory category: categories){

                int id = category.getId();
                int isSelected = category.getSelected() ? 1 : 0;

                preparedStatement.setInt(1, isSelected);
                preparedStatement.setInt(2, id);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            dbConnection.commit();
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
            dbConnection.rollback();
        }
        finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }

    }

    public static void insertRSSItems(ArrayList<RSSItem> items) throws SQLException{

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT or REPLACE INTO Item VALUES(?,?,?,?,?,?, 0, 0)"; //Category, ChannelName, Title, Description, PublicationDate, ImageURL

        try {

            dbConnection = DBManager.connector();
            dbConnection.setAutoCommit(false); //Batch Update

            preparedStatement = dbConnection.prepareStatement(insertQuery);

            for (RSSItem item: items){

                int category = item.getCategory();
                String channelName = item.getChannelName();
                String title = item.getTitle();
                String description = item.getDescription();
                String publicationDate = item.getPublicationDate();
                String imageURL = item.getImageURL();

                preparedStatement.setInt(1, category);
                preparedStatement.setString(2, channelName);
                preparedStatement.setString(3, title);
                preparedStatement.setString(4, description);
                preparedStatement.setString(5, publicationDate);
                preparedStatement.setString(6, imageURL);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            dbConnection.commit();

            System.out.println("Inserted Data");
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
            dbConnection.rollback();
        }
        finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }

    }

    public static void clearItems() throws SQLException{
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String deleteQuery = "DELETE FROM Item;";

        try {
            dbConnection = DBManager.connector();

            preparedStatement = dbConnection.prepareStatement(deleteQuery);
            preparedStatement.execute();
            System.out.println("Deleted all items");
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();

        }
        finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }

    }
}
