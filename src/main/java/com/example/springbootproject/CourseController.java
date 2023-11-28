package com.example.springbootproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/course")
public class CourseController {
    @Autowired
    private CourseInterface courseInterface;
    @PostMapping(path="/add")
    public @ResponseBody String addNewCourse (@RequestParam String courseName,
                                               @RequestParam String courseNumber,
                                               @RequestParam String capacity)
    {
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseNumber(courseNumber);
        course.setCapacity(capacity);
        courseInterface.save(course);
        return "Course Saved";
    }
    @GetMapping(path="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Course> getAllCourses() {
        return courseInterface.findAll();
    }
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Course> getCourseById(@PathVariable Integer courseID) {
        Optional<Course> course = courseInterface.findById(courseID);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}