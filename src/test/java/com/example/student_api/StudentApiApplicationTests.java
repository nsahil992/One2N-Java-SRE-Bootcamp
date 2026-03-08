package com.example.student_api;

import com.example.student_api.controller.HealthController;
import com.example.student_api.controller.StudentController;
import com.example.student_api.entity.Students;
import com.example.student_api.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({StudentController.class, HealthController.class})
class StudentApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Students student;

    @BeforeEach
    void setup() {
        student = new Students();
        student.setId(1);
        student.setName("Tim Cook");
        student.setEmail("tim@apple.com");
        student.setAge(17);
    }

    @Test
    void shouldCreateStudent() throws Exception {

        when(studentService.addStudent(student)).thenReturn(student);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllStudents() throws Exception {

        when(studentService.getAllStudents()).thenReturn(List.of(student));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tim Cook"));
    }

    @Test
    void shouldGetStudentById() throws Exception {

        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("tim@apple.com"));
    }

    @Test
    void shouldUpdateStudent() throws Exception {

        when(studentService.updateStudent(1L, student)).thenReturn(student);

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteStudent() throws Exception {

        doNothing().when(studentService).deleteStudentById(1L);

        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnHealthcheck() throws Exception {

        mockMvc.perform(get("/api/v1/healthcheck"))
                .andExpect(status().isOk())
                .andExpect(content().string("UP"));
    }
}