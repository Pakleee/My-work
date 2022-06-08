package com.example.demo.controllers;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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
    @GetMapping("/user")
    public String user (Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "user";
    }
    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable(value = "id") long id, Model model) {
        if (!userRepo.existsById(id)) {
            return "redirect:/user";
        }
        Optional<User> user = userRepo.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "user-info";
    }

    @GetMapping("/user/{id}/edit")
    public String userEdit(@PathVariable(value = "id") long id,  Model model) {
        if (!userRepo.existsById(id)) {
            return "redirect:/user";
        }
        Optional<User> user = userRepo.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "user-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String managerUserRepoEdit(@PathVariable(value = "id") long id,
                                      @RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String email,
                                      @RequestParam int age,
                                      @RequestParam Map<String, String> form,
                                      Model model) {
        if (!userRepo.existsById(id)) {
            return "redirect:/user";
        }
        User user = userRepo.findById(id).orElseThrow();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAge(age);
//        Set<String> roles =  Arrays.stream(Role.values())
//            .map(Role::name)
//            .collect(Collectors.toSet());
//        user.getRoles().clear();
//
//        for (String key : form.keySet()){
//        if (roles.contains(key)){
//        user.getRoles().add(Role.valueOf(key));
//            }
//        }

        userRepo.save(user);
        return "redirect:/user";
    }

    @PostMapping("/user/{id}/remove")
    public String managerUserDelete(@PathVariable(value = "id") long id, Model model) {
        User user = userRepo.findById(id).orElseThrow();
        userRepo.delete(user);
        return "redirect:/user";
    }
}
