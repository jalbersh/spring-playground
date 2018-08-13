package com.galvanize.jalbersh.springplayground.repository;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<Lesson, Long> {

}

