package com.springapp.model;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "userfolders")
public class Folder {
    private int folderId;
    @ManyToOne
    private int userId;
    @NotEmpty(message = "Name should not be empty")
    private String title;
    public Folder(){

    }

    public Folder(int folderId, int userId, String title){
        this.folderId = folderId;
        this.userId = userId;
        this.title = title;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
