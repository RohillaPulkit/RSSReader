package rssreader.database;

import rssreader.model.RSSCategory;
import rssreader.model.RSSChannel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static ArrayList<RSSCategory> getCategories(){

        ArrayList<RSSCategory> list = new ArrayList<>();

        try {

            String query = "SELECT * from Category";
            PreparedStatement preparedStatement = DBManager.connector().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String imageURL = resultSet.getString("ImageURL");

                RSSCategory category = new RSSCategory(id, name, imageURL);
                list.add(category);
            }
        }
        catch (Exception ex){
            System.out.println(ex);
        }
        finally {

            return list;
        }
    }

    public static ArrayList<RSSCategory> getCategoriesWithChannels(){

        HashMap<Integer, RSSCategory> map = new HashMap<>();

        try {

            String query = "SELECT Category.ID as ID," +
                    "Category.Name as CategoryName," +
                    "Category.ImageURL," +
                    "Channel.Name as ChannelName," +
                    "Channel.URL" +
                    " from Category, Channel WHERE Category.ID = Channel.Category";
            PreparedStatement preparedStatement = DBManager.connector().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){

                int id = resultSet.getInt("ID");
                String name = resultSet.getString("CategoryName");
                String imageURL = resultSet.getString("ImageURL");
                String channelName = resultSet.getString("ChannelName");
                String channelURL = resultSet.getString("URL");

                RSSCategory category = map.getOrDefault(id, new RSSCategory(id, name, imageURL));
                RSSChannel channel = new RSSChannel(channelName, channelURL);
                category.addChannel(channel);

                map.put(id, category);
            }
        }
        catch (Exception ex){

            System.out.println(ex);
        }
        finally {

            ArrayList<RSSCategory> list = new ArrayList<>(map.values());
            return list;
        }
    }

}
