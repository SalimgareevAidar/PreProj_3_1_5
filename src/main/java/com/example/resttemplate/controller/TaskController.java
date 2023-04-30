package com.example.resttemplate.controller;

import com.example.resttemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/complete")
public class TaskController {
    private final UserService userService;

    @Autowired

    TaskController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("")
    private String completeTask(){
        return userService.completeOperations();
    }
}
