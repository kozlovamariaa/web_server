package com.springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.springapp.dao.UserDAO;
import com.springapp.dao.FolderDAO;
import com.springapp.dao.TaskDAO;
import com.springapp.model.Task;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/userTasks")
public class TaskController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    FolderDAO folderDAO;
    @Autowired
    TaskDAO taskDAO;

    @GetMapping("/getTasks")
    public ArrayList<Task> getUserTasks(@RequestHeader("Authorization") String authorization,@RequestParam("folderid") int folderid){
        int id = userDAO.getUserId(authorization);
        ArrayList <Task> listOfTasks = taskDAO.getTasksInFolder(id, folderid);
        return listOfTasks ;
    }


    @PostMapping("/createTask")
    public String createTask(@RequestHeader("Authorization") String authorization, @ModelAttribute("task") @Valid Task task, @RequestParam("folderid") int folderId){
        System.out.println("create task");
        int id = userDAO.getUserId(authorization);
        if(folderDAO.isExistFolder(id, folderId)){
            //если папка с таким именем существует, создаем в ней таску
            taskDAO.createTask(id, folderId, task);
            return "200 OK";
        }
       else {
           return "This folder does not exist";
        }
    }

    @PostMapping("/updateTask/{id}")
    public String updateTask(@RequestHeader("Authorization") String authorization, @RequestParam("folderid") int folderId, @ModelAttribute("task") @Valid Task task, @PathVariable("id") int taskId){
        int id = userDAO.getUserId(authorization);
        if(folderDAO.isExistFolder(id, folderId)){
            taskDAO.updateTask(id, folderId, task, taskId);
            return "200 OK";
        }
        else{
            return "This folder does not exist";
        }
    }

    @PostMapping("/closeTask")
    public String closeTask(@RequestHeader("Authorization") String authorization, @RequestParam("folderid") int folderid,@RequestParam("taskid") int taskid ){
        int id = userDAO.getUserId(authorization);
        if(folderDAO.isExistFolder(id, folderid)){
            taskDAO.setTaskAsCompleted(id, folderid, taskid);
            return "Task is closed";
        }
        return "Error, try again";
    }

    @PostMapping("/reopenTask")
    public String reopenTask(@RequestHeader("Authorization") String authorization, @RequestParam("folderid") int folderid,@RequestParam("taskid") int taskid ) {
        int id = userDAO.getUserId(authorization);
        if(folderDAO.isExistFolder(id, folderid)){
            taskDAO.setTaskAsReopened(id, folderid, taskid);
            return "Task is opened";
        }
        return "Error, try again";
    }

    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@RequestHeader("Authorization") String authorization, @RequestParam("folderid") int folderid, @PathVariable("id") int taskId){
        int userId = userDAO.getUserId(authorization);
        if (taskDAO.isAvailableForUser(userId, folderid, taskId)){
            taskDAO.deleteTask(userId, folderid, taskId);
            return "200 OK";
        }
       else {
           return "Error, try again";
        }
    }
}


