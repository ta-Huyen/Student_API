package com.example.Student_API.controller;

import com.example.Student_API.entity.Student;
import com.example.Student_API.exception.StudentException;
import com.example.Student_API.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService sService;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/getAll")
    public List<Student> getAll() {
        return sService.findAll();
    }

    @GetMapping("/get")
    public ResponseEntity<List<Student>> showStudentList() {
        logger.info("Request to list all students received");
        try {
            List<Student> students = sService.findAll();
            if (students == null || students.isEmpty()) {
                logger.info("No students found");
                return ResponseEntity.noContent().build();
            }
            logger.info("Returning list of students: {}", students);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error("Error retrieving students", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewStudent(@RequestBody Student student) {
        logger.info("Request to add new student: {}", student);
        try {
            sService.addStudent(student);
            logger.info("Student is added successfully: {}", student);
            return ResponseEntity.ok("Student is added successfully");
        } catch (StudentException e) {
            logger.error("StudentException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while adding student", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student student) {
        logger.info("Request to update student with ID: {} and details: {}", id, student);
        try {
            Student updatedStudent = sService.updateStudent(id, student);
            logger.info("Student is updated successfully: {}", updatedStudent);
            return ResponseEntity.ok(updatedStudent);
        } catch (StudentException e) {
            logger.error("StudentException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while updating student", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        logger.info("Request to delete student with ID: {}", id);
        try {
            sService.deleteById(id);
            logger.info("Student is deleted successfully with ID: {}", id);
            return ResponseEntity.ok("Student is deleted successfully");
        } catch (StudentException e) {
            logger.error("StudentException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudent(@RequestParam Map<String, String> params) {
        logger.info("Request to search students with criteria: {}", params);
        try {
            String fullName = params.get("fullName");
            String fromDate = params.get("fromDate");
            String toDate = params.get("toDate");
            String gender = params.get("gender");
            String hometown = params.get("hometown");
            String className = params.get("className");
            String major = params.get("major");
            float minAverageMark = params.containsKey("minAverageMark") ? Float.parseFloat(params.get("minAverageMark")) : 0.0f;
            float maxAverageMark = params.containsKey("maxAverageMark") ? Float.parseFloat(params.get("maxAverageMark")) : 0.0f;
            String query = params.get("query");

            List<Student> students = sService.searchStudent(fullName, fromDate, toDate, gender, hometown, className,
                    major,
                    minAverageMark, maxAverageMark, query);
            logger.info("Search result: {}", students);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error("Error searching student", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/happy_birthday")
    public ResponseEntity<List<Student>> happyBirthday() {
        logger.info("Request to get students with birthday being today");
        try {
            List<Student> list = sService.happyBirthdayToday();
            logger.info("Student with birthday today: {}", list);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            logger.error("Error fetching student with birthdays today", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
