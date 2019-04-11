package rssreader.database;

import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;

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
                RSSChannel channel = new RSSChannel(channelName, channelURL);
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

}
