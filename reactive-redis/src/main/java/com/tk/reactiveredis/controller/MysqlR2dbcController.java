package com.tk.reactiveredis.controller;

import com.tk.reactiveredis.entity.Student;
import com.tk.reactiveredis.reposity.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Description:
 * @Date : 2023/04/14 19:57
 * @Auther : tiankun
 */
@RestController
@RequestMapping("mysql")
public class MysqlR2dbcController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public Flux<Student> index() {
        return studentRepository.findAll();
    }

}
