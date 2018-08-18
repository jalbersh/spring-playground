package com.galvanize.jalbersh.springplayground.repository;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    public List<Lesson> findByTitle(String title);
    public List<Lesson> findByDeliveredOnBetween(Date date1, Date date2);
}

