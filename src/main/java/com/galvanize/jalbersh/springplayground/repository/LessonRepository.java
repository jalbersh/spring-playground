package com.galvanize.jalbersh.springplayground.repository;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    public List<Lesson> findByTitle(String title);
}

