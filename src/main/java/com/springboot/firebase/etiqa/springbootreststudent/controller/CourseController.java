package com.springboot.firebase.etiqa.springbootreststudent.controller;

import com.springboot.firebase.etiqa.springbootreststudent.model.Course;
import com.springboot.firebase.etiqa.springbootreststudent.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/course")
    public ResponseEntity<List<Course>> getAllCourses() throws InterruptedException, ExecutionException {


        List<Course> courseList = new ArrayList<Course>();

        courseList = courseService.getAllCourses();
        if (courseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(courseList, HttpStatus.OK);
        }
    }

    @PostMapping("/addCourse")
    public ResponseEntity<Void> createCourse(@RequestBody Course course) throws InterruptedException, ExecutionException {

        Course createdCourse = courseService.saveCourse(course);

        //Location
        //Get current resource url
        ///{id}
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCourse.getID()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
