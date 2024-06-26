package com.example.demo.Controller;

import com.example.demo.Model.Post;
import com.example.demo.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public String viewHomePage(Model model) {
        List<Post> listPosts = postService.getAllPosts();
        model.addAttribute("listPosts", listPosts);
        return "blog/index";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "blog/post";
    }

    @GetMapping("/new")
    public String showNewPostForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "blog/new_post";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post) {
        postService.savePost(post);
        return "redirect:/blog";
    }
}