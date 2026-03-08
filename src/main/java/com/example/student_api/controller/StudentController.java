package com.example.student_api.controller;

import com.example.student_api.entity.Students;
import com.example.student_api.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    // CREATE Student
    @PostMapping
    public ResponseEntity<Students> createStudent(@RequestBody Students student) {
        logger.info("Creating new student with email: {}", student.getEmail());
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    // GET All Students
    @GetMapping
    public ResponseEntity<List<Students>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // GET By id
    @GetMapping("/{id}")
    public ResponseEntity<Students> getStudentById(@PathVariable Long id) {
        logger.info("Fetching student with id: {}", id);
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Students> updateStudent(
            @PathVariable Long id,
            @RequestBody Students student) {

        logger.info("Updating student with id: {}", id);
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        logger.info("Deleting student with id: {}", id);
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}