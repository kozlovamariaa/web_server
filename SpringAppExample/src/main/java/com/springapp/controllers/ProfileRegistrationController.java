package com.springapp.controllers;

import com.springapp.dao.UserDAO;
import com.springapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class ProfileRegistrationController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void ProfileRegistrationController(){}

    //hash password
    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "Error, check your entered data";
        }
        if(!userDAO.isExist(user.getEmail())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDAO.save(user);
            return "The user is successfully registered!";
        }
        else{
            return "Such a user is already registered";
        }
    }
}
