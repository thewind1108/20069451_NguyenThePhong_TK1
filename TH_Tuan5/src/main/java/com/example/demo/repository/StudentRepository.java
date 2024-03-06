package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Student;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Repository
public class StudentRepository {
    private final JedisPool jedisPool;

    public StudentRepository() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        this.jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    }

    public void saveStudent(Student student) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(student.getId(), student.getName() + "," + student.getAge());
        }
    }

    public Student getStudent(String id) {
        try (Jedis jedis = jedisPool.getResource()) {
            String data = jedis.get(id);
            if (data != null) {
                String[] parts = data.split(",");
                Student student = new Student();
                student.setId(id);
                student.setName(parts[0]);
                student.setAge(Integer.parseInt(parts[1]));
                return student;
            }
        }
        return null;
    }
}


