package com.example.Student_API.repository;

import com.example.Student_API.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
    @Query(value = "SELECT s FROM student s WHERE " +
            "(:fullName IS NULL OR LOWER(s.full_name) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND " +
            "(:fromDate IS NULL OR s.birthday >= :fromDate) AND " +
            "(:toDate IS NULL OR s.birthday <= :toDate) AND " +
            "(:gender IS NULL OR LOWER(s.gender) = LOWER(:gender)) AND " +
            "(:hometown IS NULL OR LOWER(s.hometown) LIKE LOWER(CONCAT('%', :hometown, '%'))) AND " +
            "(:className IS NULL OR LOWER(s.class_name) LIKE LOWER(CONCAT('%', :className, '%'))) AND " +
            "(:major IS NULL OR LOWER(s.major) LIKE LOWER(CONCAT('%', :major, '%'))) AND " +
            "(:minAvgMark IS NULL OR s.average_mark >= :minAvgMark) AND " +
            "(:maxAvgMark IS NULL OR s.average_mark <= :maxAvgMark) AND " +
            "(COALESCE(:query, '') = '' OR " +
            "LOWER(s.full_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.gender) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.hometown) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.class_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.major) LIKE LOWER(CONCAT('%', :query, '%')))",
            nativeQuery = true
    )
    List<Student> findByCriteria(@Param("fullName") String fullName, @Param("fromDate") String fromDate,
                                 @Param("toDate") String toDate, @Param("gender") String gender, @Param("hometown") String hometown,
                                 @Param("className") String className, @Param("major") String major,
                                 @Param("minAvgMark") float minAvgMark, @Param("maxAvgMark") float maxAvgMark,
                                 @Param("query") String query);

    @Query(value = "SELECT * FROM student WHERE MONTH(birthday) = :month AND DAY(birthday) = :day", nativeQuery = true)
    List<Student> findBirthday(@Param("month") int currentMonth, @Param("day") int currentDay);
}
