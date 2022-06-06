package com.example.demo.controllers;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.models.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping ("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping ("/registration")
    public String addUser (User user, Map<String,Object> model){
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null){
            model.put("message","Пользователь добавлен!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
