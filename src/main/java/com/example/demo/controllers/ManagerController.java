package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ManagerController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/manager")
    public String manager(Model model) {
        model.addAttribute("title", "Страничка менеджера");
        return "manager";
    }

    @GetMapping("/manager/add")
    public String managerAdd(Model model) {
        return "manager-add";
    }

    @PostMapping("/manager/add")
    public String managerPostAdd(@RequestParam String name, @RequestParam String state, @RequestParam String category, @RequestParam double price, @RequestParam double discount, Model model) {
        Post post = new Post(name, category, state, price, discount);
        postRepository.save(post);
        return "redirect:/product";
    }

    @GetMapping("/product")
    public String product(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "product";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/product";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "product-info";
    }

    @GetMapping("/product/{id}/edit")
    public String productEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/product";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "product-edit";
    }

    @PostMapping("/product/{id}/edit")
    public String managerPostEdit(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String state, @RequestParam String category, @RequestParam double price, @RequestParam double discount, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/product";
        }
        Post post = postRepository.findById(id).orElseThrow();
        post.setName(name);
        post.setPrice(price);
        post.setDiscount(discount);
        post.setCategory(category);
        post.setDiscount(discount);
        postRepository.save(post);
        return "redirect:/product";
    }

    @PostMapping("/product/{id}/remove")
    public String managerPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/product";
    }

    @GetMapping("/basket")
    public String basket(Model model) {
        model.addAttribute("title", "Корзина");
        return "basket";
    }

    @GetMapping("/photo")
    public String photo(Model model) {
        model.addAttribute("title", "Лицо сайта");
        return "photo";
    }
}
