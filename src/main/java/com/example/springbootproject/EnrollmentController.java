package com.example.springbootproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentInterface enrollmentInterface;

    @PostMapping(path="/add")
    public @ResponseBody String addNewEnrollment(@RequestParam Integer courseId,
                                                 @RequestParam Integer studentId) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(courseId);
        enrollment.setStudentId(studentId);
        enrollmentInterface.save(enrollment);
        return "Enrollment Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Enrollment> getAllEnrollments() {
        return enrollmentInterface.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Integer eid) {
        Optional<Enrollment> enrollment = enrollmentInterface.findById(eid);
        if (enrollment.isPresent()) {
            return ResponseEntity.ok(enrollment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<String> updateEnrollment(@PathVariable Integer eid,
                                                                 @RequestParam Integer courseId,
                                                                 @RequestParam Integer studentId) {
        Optional<Enrollment> enrollmentData = enrollmentInterface.findById(eid);
        if (enrollmentData.isPresent()) {
            Enrollment enrollment = enrollmentData.get();
            enrollment.setCourseId(courseId);
            enrollment.setStudentId(studentId);
            enrollmentInterface.save(enrollment);
            return ResponseEntity.ok("Enrollment Updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteEnrollment(@PathVariable Integer eid) {
        try {
            enrollmentInterface.deleteById(eid);
            return ResponseEntity.ok("Enrollment Deleted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error occurred while deleting the enrollment");
        }
    }
}

