package com.springapp.controllers;

import com.springapp.dto.UserDTO;
import com.springapp.dao.FolderDAO;
import com.springapp.dao.UserDAO;
import com.springapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/userprofile")
public class ProfileController {
    @Autowired
    UserDAO userDAO;
    UserDTO userDTO;
    @Autowired
    FolderDAO folderDAO;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/main")
    public UserDTO openUserPage(@RequestHeader("Authorization") String authorization){
        int id = userDAO.getUserId(authorization);
        userDTO = userDAO.getById(id);
        return userDTO;
    }

    @PostMapping("/updateProfile")
    public Object updateProfile(@RequestHeader("Authorization") String authorization, @ModelAttribute("user") @Valid User user){
        int id = userDAO.getUserId(authorization);
        if(!userDAO.isExist(user.getEmail())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDTO = userDAO.updateProfile(user, id);
            return userDTO;
        }
        else{
            return "Change email. User with the same email is already registered";
        }
    }
}

