package com.example.springbootproject;

import org.springframework.data.repository.CrudRepository;

public interface GradesInterface extends CrudRepository<Grades, Integer> {
}
