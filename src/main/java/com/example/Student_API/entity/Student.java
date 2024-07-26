package com.example.Student_API.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Component
@Entity
@Table(name ="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 50)
    private String fullName;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(nullable = false, length = 100)
    private String className;
    @Column(nullable = false, length = 100)
    private String major;
    @Column(nullable = false, length = 100)
    private String hometown;
    @Column(nullable = false, length = 10)
    private String gender;
    @Column(nullable = false)
    private float averageMark;

    public Student() {
    }


    @Override
    public String toString() {
        return "Student {" +
                "Id:" + id +
                " Họ tên:" + fullName +
                " Ngày sinh:" + birthday +
                " Tên lớp:" + className +
                " Tên ngành:" + major +
                " Địa chỉ:" + hometown +
                " Giới tính:" + gender +
                " Điểm:" + averageMark +
                "}";
    }
}
