package com.example.springbootproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/student")
public class StudentController {
    @Autowired
    private StudentInterface studentInterface;
    @PostMapping(path="/add")
    public @ResponseBody String addNewStudent (@RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam String email,
                                               @RequestParam String address,
                                               @RequestParam String city,
                                               @RequestParam String postal,
                                               @RequestParam String phone)
    {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setAddress(address);
        student.setCity(city);
        student.setPostal(postal);
        student.setPhone(phone);
        studentInterface.save(student);
        return "Student Saved";
    }
    @GetMapping(path="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Student> getAllStudents() {
        return studentInterface.findAll();
    }
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Student> getStudentById(@PathVariable Integer studentID) {
        Optional<Student> student = studentInterface.findById(studentID);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}