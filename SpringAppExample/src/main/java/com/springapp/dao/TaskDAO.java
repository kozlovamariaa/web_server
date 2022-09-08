package com.springapp.dao;

import com.springapp.util.PropertiesUtil;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import com.springapp.model.Task;
import java.sql.*;
import java.util.ArrayList;


@Component
@PropertySource("classpath:spring.properties")
public class TaskDAO {
    private static final String URL_KEY = "spring.datasource.url";
    private static final String USERNAME_KEY = "spring.datasource.username";
    private static final String PASSWORD_KEY = "spring.datasource.password";
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),PropertiesUtil.get(PASSWORD_KEY));
            System.out.println(PropertiesUtil.get(URL_KEY) + " url");
            System.out.println(PropertiesUtil.get(USERNAME_KEY) + " username");
            System.out.println(PropertiesUtil.get(PASSWORD_KEY) + " password");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void createTask(int userid, int folderid, Task task) {
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO usertasks (userid, folderid, taskstatus, tasktitle, date_of_creating_task, description) VALUES (?, ?, ?, ?, ?, ?)");


            preparedStatement.setInt(1,userid);
            preparedStatement.setInt(2, folderid);
            preparedStatement.setString(3, String.valueOf(Task.Status.NEW));
            preparedStatement.setString(4, task.getTaskTitle());
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.setString(6, task.getDescription());

            preparedStatement.executeUpdate();

        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public void updateTask(int userId, int folderid, Task task, int taskid) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE usertasks SET tasktitle = ?, description = ?, date_of_creating_task = ? " +
                            "WHERE taskid = ? AND folderid = ? AND userid = ?");

            preparedStatement.setString(1, task.getTaskTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.setInt(4, taskid);
            preparedStatement.setInt(5, folderid);
            preparedStatement.setInt(6, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTask(int userId, int folderid, int taskid) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM usertasks WHERE taskid = ? AND folderid = ? AND userid = ?");

            preparedStatement.setInt(1, taskid);
            preparedStatement.setInt(2, folderid);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList <Task> getTasksInFolder(int userId, int folderid) {
        ArrayList <Task> listOfTasks = new ArrayList<>();
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM usertasks WHERE userid = ? AND folderid = ?");

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, folderid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Task task = new Task();
                task.setTaskId(resultSet.getInt("taskId"));
                task.setUserId(resultSet.getInt("userid"));
                task.setFolderId(resultSet.getInt("folderid"));
                task.setStatus(resultSet.getString("taskstatus"));
                task.setTaskTitle(resultSet.getString("tasktitle"));
                task.setDateOfCreatingTask(resultSet.getTimestamp("date_of_creating_task").toLocalDateTime());
                task.setDescription(resultSet.getString("description"));
                listOfTasks.add(task);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfTasks;
    }

    public void setTaskAsCompleted(int userId, int folderid, int taskid){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE usertasks SET taskstatus = ? WHERE taskid = ? AND userid = ? AND folderid = ? ");

            preparedStatement.setString(1, String.valueOf(Task.Status.CLOSED));
            preparedStatement.setInt(2, taskid);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, folderid);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setTaskAsReopened(int userid, int folderid, int taskid){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE usertasks SET taskstatus = ? WHERE taskid = ? AND userid = ? AND folderid = ? ");

            preparedStatement.setString(1, String.valueOf(Task.Status.NEW));
            preparedStatement.setInt(2, taskid);
            preparedStatement.setInt(3, userid);
            preparedStatement.setInt(4, folderid);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isAvailableForUser (int userid, int folderid, int taskid){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from usertasks WHERE taskid = ? AND userid = ? AND folderid = ? ");
            preparedStatement.setInt(1, taskid);
            preparedStatement.setInt(2, userid);
            preparedStatement.setInt(3, folderid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
//                System.out.println(resultSet.getString(taskid) + "taskid");
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void deleteAllTasks(int userId, int folderid) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM usertasks WHERE folderid = ? AND userId = ?");

            preparedStatement.setInt(1, folderid);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}




