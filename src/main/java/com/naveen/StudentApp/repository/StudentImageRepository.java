package com.naveen.StudentApp.repository;

import com.naveen.StudentApp.entity.StudentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentImageRepository extends JpaRepository<StudentImage, Integer> {
    Optional<StudentImage> findByName(String filename);

}
