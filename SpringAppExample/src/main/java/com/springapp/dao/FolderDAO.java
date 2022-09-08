package com.springapp.dao;

import com.springapp.util.PropertiesUtil;
import org.springframework.context.annotation.PropertySource;
import com.springapp.model.Folder;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.ArrayList;


@Component
@PropertySource("classpath:spring.properties")
public class FolderDAO {
    private static final String URL_KEY = "spring.datasource.url";
    private static final String USERNAME_KEY = "spring.datasource.username";
    private static final String PASSWORD_KEY = "spring.datasource.password";
    private static Connection connection;

    static
    {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            connection = DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),PropertiesUtil.get(PASSWORD_KEY));
            System.out.println(PropertiesUtil.get(URL_KEY) + " url");
            System.out.println(PropertiesUtil.get(USERNAME_KEY) + " username");
            System.out.println(PropertiesUtil.get(PASSWORD_KEY) + " password");
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public Folder createFolder(int userId, String folderTitle) {
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO userfolders (userid, foldertitle) VALUES (?, ?)");
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2, folderTitle);
            preparedStatement.executeUpdate();
            Folder selectedFolder = returnFolder(userId, folderTitle);
            return selectedFolder;
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Folder returnFolder(int userId, String folderTitle){
        Folder folder= null;
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM userfolders WHERE userid = ? AND foldertitle = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, folderTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            folder = new Folder();
            if(resultSet.next()){
                folder.setFolderId(resultSet.getInt("folderid"));
                folder.setUserId(resultSet.getInt("userid"));
                folder.setTitle(resultSet.getString("foldertitle"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return folder;
    }


    public Folder updateFolder(int userId, String newFolderTitle, int folderId ){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE userfolders SET foldertitle = ? WHERE folderid = ? AND userid = ?");

            preparedStatement.setString(1, newFolderTitle);
            preparedStatement.setInt(2, folderId);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
            Folder selectedFolder = returnFolder(userId, newFolderTitle);
            return selectedFolder;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       return null;
    }

    public boolean deleteFolder(int userId, int folderId){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM userfolders WHERE folderid = ? AND userid = ?");

            preparedStatement.setInt(1, folderId);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public ArrayList<Folder> getFolders(int userId){
        ArrayList <Folder> listOfFolders = new ArrayList<>();
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM userfolders WHERE userid = ? ");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Folder folder = new Folder();
                folder.setFolderId(resultSet.getInt("folderid"));
                folder.setUserId(resultSet.getInt("userid"));
                folder.setTitle(resultSet.getString("foldertitle"));
                listOfFolders.add(folder);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfFolders;
    }

    public boolean isExistFolder(int userId, int folderid){
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM userfolders WHERE userid = ? AND folderid = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, folderid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
               return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
