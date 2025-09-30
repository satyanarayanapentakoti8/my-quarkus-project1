package com.example.repo;

import com.example.entity.Student;
import jakarta.enterprise.context.ApplicationScoped;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@ApplicationScoped
public interface StudentRepository extends JpaRepository<Student, String> {
}
