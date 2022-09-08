package com.springapp.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


public class Task {
    private int taskId;
    private int userId;
    private int folderId;
    private String status;
    @NotEmpty(message = "Name should not be empty")
    private String taskTitle;
    private LocalDateTime dateOfCreatingTask;
    private String description;

    public Task(){

    }

    public Task(int taskId, int userId, int folderId, String status, String taskTitle, LocalDateTime dateOfCreatingTask){
        this.taskId = taskId;
        this.userId = userId;
        this.folderId = folderId;
        this.status = status;
        this.taskTitle = taskTitle;
        this.dateOfCreatingTask = dateOfCreatingTask;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setDateOfCreatingTask(LocalDateTime dateOfCreatingTask) {
        this.dateOfCreatingTask = dateOfCreatingTask;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public int getFolderId() {
        return folderId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getStatus() {
        return status;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public LocalDateTime getDateOfCreatingTask() {
        return dateOfCreatingTask;
    }

    public String getDescription() {
        return description;
    }

    public static enum Status{
        NEW,
        CLOSED
    }
}



