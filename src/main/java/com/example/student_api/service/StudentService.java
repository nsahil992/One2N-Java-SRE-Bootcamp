package com.example.student_api.service;

import com.example.student_api.entity.Students;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    Students addStudent(Students students);

    Students getStudentById(Long id);

    List<Students> getAllStudents();

    Students updateStudent(Long id, Students updatedStudent);

    void deleteStudentById(Long id);
}
