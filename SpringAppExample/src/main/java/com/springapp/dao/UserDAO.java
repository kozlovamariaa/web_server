package com.springapp.dao;

import com.springapp.dto.UserDTO;
import com.springapp.util.PropertiesUtil;
import lombok.NonNull;
import com.springapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.sql.*;


@Component
@PropertySource("classpath:spring.properties")
public class UserDAO {
    private static final String URL_KEY = "spring.datasource.url";
    private static final String USERNAME_KEY = "spring.datasource.username";
    private static final String PASSWORD_KEY = "spring.datasource.password";
    private static Connection connection;
    private UserDTO userDTO;

    static {

        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            System.out.println("static block UserDAO");
            connection = DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),PropertiesUtil.get(PASSWORD_KEY));
            System.out.println(PropertiesUtil.get(URL_KEY) + " url");
            System.out.println(PropertiesUtil.get(USERNAME_KEY) + " username");
            System.out.println(PropertiesUtil.get(PASSWORD_KEY) + " password");

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void save(User user) {
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO usertable (userName, userLastname, email, password) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getLastname());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public boolean isExist(@NonNull String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from usertable " +
                    "where email = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            //user exists in DB
            if(resultSet.next()){
                return true;
            }
            //user does not exist in DB
            else {
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public User show(int id) {
        User user = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM usertable WHERE id = ?");

            preparedStatement.setInt(1, id);
            System.out.println("id from com.springapp.dao" + id);

            ResultSet resultSet = preparedStatement.executeQuery();

            user = new User();
            if(resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("username"));
                user.setLastname(resultSet.getString("userlastname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public UserDTO updateProfile(User user, int useriId){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE usertable SET username = ?, userlastname = ?, email = ?, password = ? WHERE id  = ?");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, useriId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        userDTO = getById(useriId);
        return userDTO;
    }

    public User getByEmail(@NonNull String login) {
        User user = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM usertable WHERE email = ? ");

            preparedStatement.setString(1, login);
            System.out.println("id from com.springapp.dao" + login);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = new User();
            if(resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("username"));
                user.setLastname(resultSet.getString("userlastname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public UserDTO getById(int userId) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM usertable WHERE id = ?");

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            userDTO = new UserDTO();
            if(resultSet.next()){
                userDTO.setId(resultSet.getInt("id"));
                userDTO.setName(resultSet.getString("username"));
                userDTO.setLastname(resultSet.getString("userlastname"));
                userDTO.setEmail(resultSet.getString("email"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userDTO;
    }

    public void saveUserToken(int userid, String token){
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO usertoken (userid, user_token) VALUES (?, ?)");
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, token);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getUserId(String token){
        int id = 0;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usertoken WHERE user_token = ? ");
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                id = resultSet.getInt("userid");
                System.out.println("user id " + id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
}


