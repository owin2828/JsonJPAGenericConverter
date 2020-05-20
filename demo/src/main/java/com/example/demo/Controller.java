package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
    @Autowired
    private StudentRepository repo;

    @PostMapping("/test")
    public List<Student> makeStudent(@RequestBody AdditionalData req){
        
        Student stu = new Student();
        stu.setAge(11);
        stu.setName("test");
        stu.setAdditionalData(req);
        repo.save(stu);

        return repo.findAll();
    }

    @GetMapping("/test/{id}")
    public Student makeStudent(@PathVariable Long id){

        return repo.findById(id).get();
    }
}