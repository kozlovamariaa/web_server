package com.springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.springapp.dao.UserDAO;
import com.springapp.dao.FolderDAO;
import com.springapp.model.Folder;
import com.springapp.dao.TaskDAO;
import java.util.ArrayList;

@RestController
@RequestMapping("/userFolders")

public class FoldersController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    FolderDAO folderDAO;
    @Autowired
    TaskDAO taskDAO;

    @GetMapping("/getFolders")
    public ArrayList<Folder> getAllUserFolders(@RequestHeader("Authorization") String authorization){
        int id = userDAO.getUserId(authorization);
        ArrayList <Folder> listOfFolders = folderDAO.getFolders(id);
        return listOfFolders;
    }

    @PostMapping("/updateFolder/{id}")
    private Folder updateFolder(@RequestHeader("Authorization") String authorization, @RequestParam("folderTitle") String title, @PathVariable("id") int folderId){
        int id = userDAO.getUserId(authorization);
        return folderDAO.updateFolder(id, title, folderId);
    }

    @PostMapping("/createNewFolder")
    public Folder createFolder(@RequestParam("folderTitle") String title, @RequestHeader("Authorization") String authorization){
        int id = userDAO.getUserId(authorization);
        Folder newFolder = folderDAO.createFolder(id, title);
        return newFolder;
    }

    @DeleteMapping("/deleteFolder/{id}")
    public ArrayList<Object> deleteFolder(@RequestHeader("Authorization") String authorization, @PathVariable("id") int folderId ){
        ArrayList<Object> listOfFolders = new ArrayList<>();
        int id = userDAO.getUserId(authorization);
        if(folderDAO.deleteFolder(id, folderId)){
            taskDAO.deleteAllTasks(id, folderId);
            listOfFolders.add("Folder and tasks deleted");
            listOfFolders.add(folderDAO.getFolders(id));
            return listOfFolders;
        }
        else {
            listOfFolders.add("Error, try again");
            return listOfFolders;
        }
    }
}
