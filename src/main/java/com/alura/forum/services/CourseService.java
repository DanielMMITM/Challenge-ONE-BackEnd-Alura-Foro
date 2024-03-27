package com.alura.forum.services;

import com.alura.forum.models.course.Course;
import com.alura.forum.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> listCourses(){
        return courseRepository.findAll();
    }
}
