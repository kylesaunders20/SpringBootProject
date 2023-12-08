package com.example.springbootproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/grades")
public class GradesController {
    @Autowired
    private GradesInterface gradesInterface;

    @PostMapping(path="/add")
    public @ResponseBody String addNewGrade(@RequestParam Integer studentId,
                                            @RequestParam Integer courseId,
                                            @RequestParam String grade) {
        Grades grades = new Grades();
        grades.setStudentId(studentId);
        grades.setCourseId(courseId);
        grades.setGrade(grade);
        gradesInterface.save(grades);
        return "Grade Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Grades> getAllGrades() {
        return gradesInterface.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Grades> getGradeById(@PathVariable Integer gid) {
        Optional<Grades> grade = gradesInterface.findById(gid);
        if (grade.isPresent()) {
            return ResponseEntity.ok(grade.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<String> updateGrade(@PathVariable Integer gid,
                                                            @RequestParam Integer studentId,
                                                            @RequestParam Integer courseId,
                                                            @RequestParam String grade) {
        Optional<Grades> gradeData = gradesInterface.findById(gid);
        if (gradeData.isPresent()) {
            Grades grades = gradeData.get();
            grades.setStudentId(studentId);
            grades.setCourseId(courseId);
            grades.setGrade(grade);
            gradesInterface.save(grades);
            return ResponseEntity.ok("Grade Updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteGrade(@PathVariable Integer gid) {
        try {
            gradesInterface.deleteById(gid);
            return ResponseEntity.ok("Grade Deleted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error occurred while deleting the grade");
        }
    }
}

