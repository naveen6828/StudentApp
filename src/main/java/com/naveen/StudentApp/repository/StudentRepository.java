package com.naveen.StudentApp.repository;

import com.naveen.StudentApp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByClassName(String className);

    @Query("SELECT s FROM Student s WHERE s.address.city = :city")
    List<Student> findByCity(String city);
}
