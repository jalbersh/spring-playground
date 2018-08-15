package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import com.galvanize.jalbersh.springplayground.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    @Autowired
    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @GetMapping("/5")
    public Optional<Lesson> get5() {
        return this.repository.findById(5l);
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

}

/*
insert into lessons (title, delivered_on) values ('Requests and Responses', '2017-02-05');
insert into lessons (title, delivered_on) values ('SQL', '2017-04-06');
insert into lessons (title, delivered_on) values ('Bellman Ford', '2017-05-02');
insert into lessons (title, delivered_on) values ('Some Other Lesson', '2017-05-02');
insert into lessons (title, delivered_on) values ('JPA', '2017-05-02');
 */