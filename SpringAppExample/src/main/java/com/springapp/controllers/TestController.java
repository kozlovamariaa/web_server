package com.springapp.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hi")
public class TestController {

    @GetMapping("/hello")
    public String helloWorld(@RequestParam(value="name", defaultValue="World") String name) {
        System.out.println("test controller");
        return "Hello "+name+"!!";
    }
}
