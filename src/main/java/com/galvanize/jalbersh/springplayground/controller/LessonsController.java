package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import com.galvanize.jalbersh.springplayground.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    @Autowired
    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void delete(@PathVariable long id) {
        System.out.println("delete path param id="+id);
        this.repository.deleteById(id);
    }

    @RequestMapping(value = "", method = POST, consumes = "application/json", produces = "application/json")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/5", method = POST, produces = "application/json")
    public Lesson create5() {
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/find/{title}", method = GET, produces = "application/json")
    public List<Lesson> getByTitle(@PathVariable String title) {
        System.out.println("in getByTitle with "+title);
        List<Lesson> lessons = repository.findByTitle(title);
        System.out.println("lessons="+lessons);
        return lessons;
    }

    @RequestMapping(value = "/between", method = GET, produces = "application/json")
    public List<Lesson> getLessonsBetween(@RequestParam(required = true) String date1,
                                          @RequestParam(required = true) String date2) {
        System.out.println("in getBetween: got date1str="+date1+" and date2str="+date2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        Date ddate1 = null;
        Date ddate2 = null;
        try {
            ddate1 = sdf.parse(date1);
           ddate2 = sdf.parse(date2);
        } catch (ParseException pe) {
            System.out.println("ParseException caught: "+pe.getMessage());
            return null;
        } catch (NumberFormatException nfe) {
            System.out.println("ParseException caught: "+nfe.getMessage());
            return null;
        }

        System.out.println("calling repository.findAllDeliveredOnBetween with date1="+date1+" and date2="+date2);
        List<Lesson> lessons = this.repository.findAllDeliveredOnBetween(ddate1,ddate2);
        System.out.println("lessons="+lessons);
        return lessons;
    }

    @RequestMapping(value = "/{id}", method = POST, produces = "application/json")
    public Lesson createId(@PathVariable long id) {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/{id}", method = PATCH, produces = "application/json")
    public Lesson updateId(@PathVariable long id) {
        System.out.println("path param id="+id);
        Lesson lesson = this.repository.findById(id).orElse(new Lesson(id,"JPL",Calendar.getInstance().getTime()));
        this.repository.save(lesson);
        lesson.setId(5L);
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/count", method = GET, produces = "application/json")
    public long getCount() {
        System.out.println("in getCount");
        return this.repository.count();
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public Iterable<Lesson> all() {
        System.out.println("in GET /lessons");
        return this.repository.findAll();
    }

    @RequestMapping(value = "/5", method = GET, produces = "application/json")
    public Optional<Lesson> get5() {
        System.out.println("in GET /lessons/5");
        return this.repository.findById(5L);
    }

    @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
    public Lesson getById(@PathVariable long id) {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
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