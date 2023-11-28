package com.example.springbootproject;

import org.springframework.data.repository.CrudRepository;
public interface CourseInterface extends CrudRepository<Course,
        Integer> {
}
