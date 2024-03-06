package com.example.demo.service;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final JWTservice jwtService;

    @Autowired
    public StudentService(StudentRepository studentRepository, JWTservice jwtService) {
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
    }

    public String saveStudent(Student student) {
        studentRepository.saveStudent(student);
        return jwtService.generateJWT(student.getId());
    }

    public Student getStudent(String jwt) {
        Claims claims = jwtService.parseJWT(jwt);
        String id = claims.getSubject();
        return studentRepository.getStudent(id);
    }
}

