package com.example.demo.entity;


import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

//
//    @RequestParam("userId") User user,
//    Model model) {
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//        user.setAge(age);
//        Set<String> roles =  Arrays.stream(Role.values())
//        .map(Role::name)
//        .collect(Collectors.toSet());
//        user.getRoles().clear();
//
//        for (String key : form.keySet()){
//        if (roles.contains(key)){
//        user.getRoles().add(Role.valueOf(key));
//        }
//        }
//        userRepo.save(user);
//        return "redirect:/user";