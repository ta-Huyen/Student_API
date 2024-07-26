package com.example.Student_API.service;

import com.example.Student_API.entity.Student;
import com.example.Student_API.exception.StudentException;
import com.example.Student_API.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public List<Student> findAll() {
        return (List<Student>) repository.findAll();
    }

    private void validateInfo(Student student) throws StudentException {
        if (student.getFullName() == null || student.getFullName().isEmpty()) {
            throw new StudentException("Student's name cannot be null or empty");
        }
        if (student.getBirthday() == null) {
            throw new StudentException("Student's birthday cannot be null");
        }
        if (student.getGender() == null || student.getGender().isEmpty()) {
            throw new StudentException("Student's gender cannot be null or empty");
        }
        if (student.getMajor() == null || student.getMajor().isEmpty()) {
            throw new StudentException("Student's major cannot be null or empty");
        }
        if (student.getHometown() == null || student.getHometown().isEmpty()) {
            throw new StudentException("Student's hometown cannot be null or empty");
        }
        if (student.getClassName() == null || student.getClassName().isEmpty()) {
            throw new StudentException("Student's class name cannot be null or empty");
        }
        if (Float.valueOf(student.getAverageMark()) == null) {
            student.setAverageMark(0.0F);
        }
    }

    public void addStudent(Student student) throws StudentException {
        validateInfo(student);

        try {
            repository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new StudentException("Data integrity violation: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new StudentException("An error occurred while saving: " + e.getMessage(), e);
        }
    }

    public Student updateStudent(int id, Student student) throws StudentException{
        Optional<Student> studentOptional = repository.findById(id);
        if (studentOptional.isPresent()) {
            validateInfo(student);

            Student studentGetOpt = studentOptional.get();
            studentGetOpt.setFullName(student.getFullName());
            studentGetOpt.setBirthday(student.getBirthday());
            studentGetOpt.setClassName(student.getClassName());
            studentGetOpt.setMajor(student.getMajor());
            studentGetOpt.setHometown(student.getHometown());
            studentGetOpt.setGender(student.getGender());
            studentGetOpt.setAverageMark(student.getAverageMark());

            return repository.save(studentGetOpt);
        } else {
            throw new StudentException("Student not found");
        }
    }

    public void deleteById(int id) throws StudentException {
        Optional<Student> studentOptional = repository.findById(id);
        if (studentOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new StudentException("Student with this Id" + id + "does not exist!");
        }
    }

    public List<Student> searchStudent(String fullName, String fromDate, String toDate, String gender, String hometown,
                                       String className, String major, float minAvgMark, float maxAvgMark, String query) {
        return repository.findByCriteria(fullName, fromDate, toDate, gender, hometown, className, major, minAvgMark,
                maxAvgMark, query);
    }

    public List<Student> happyBirthdayToday() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();
        return repository.findBirthday(currentMonth, currentDay);
    }
}
