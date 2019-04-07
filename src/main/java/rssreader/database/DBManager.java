package rssreader.database;

import rssreader.model.RSSCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
}
