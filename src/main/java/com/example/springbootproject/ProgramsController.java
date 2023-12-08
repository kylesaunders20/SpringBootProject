package com.example.springbootproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/program")
public class ProgramsController {
    @Autowired
    private ProgramsInterface programsInterface;

    @PostMapping(path="/add")
    public @ResponseBody String addNewProgram(@RequestParam String programName,
                                              @RequestParam String campus) {
        Programs program = new Programs();
        program.setProgramName(programName);
        program.setCampus(campus);
        programsInterface.save(program);
        return "Program Saved";
    }

    @GetMapping(path="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Programs> getAllPrograms() {
        return programsInterface.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Programs> getProgramById(@PathVariable Integer pid) {
        Optional<Programs> program = programsInterface.findById(pid);
        if (program.isPresent()) {
            return ResponseEntity.ok(program.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<String> updateProgram(@PathVariable Integer pid,
                                                              @RequestParam String programName,
                                                              @RequestParam String campus) {
        Optional<Programs> programData = programsInterface.findById(pid);
        if (programData.isPresent()) {
            Programs program = programData.get();
            program.setProgramName(programName);
            program.setCampus(campus);
            programsInterface.save(program);
            return ResponseEntity.ok("Program Updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteProgram(@PathVariable Integer pid) {
        try {
            programsInterface.deleteById(pid);
            return ResponseEntity.ok("Program Deleted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error occurred while deleting the program");
        }
    }
}
