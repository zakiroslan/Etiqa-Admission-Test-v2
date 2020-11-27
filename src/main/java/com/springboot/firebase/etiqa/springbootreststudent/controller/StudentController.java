package com.springboot.firebase.etiqa.springbootreststudent.controller;

import com.springboot.firebase.etiqa.springbootreststudent.model.Student;
import com.springboot.firebase.etiqa.springbootreststudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//@CrossOrigin(origins = {"http://localhost:4200\",http://localhost:8081"})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/student")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String courseID) throws InterruptedException, ExecutionException {

        try {
            List<Student> studList = new ArrayList<Student>();

            if (courseID == null) {
                studList = studentService.getAllStudents();
                System.out.println("get all student");
                if (studList.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(studList, HttpStatus.OK);
                }
            }
            else {
                studList = studentService.getStudentByCourseID(courseID);
                return new ResponseEntity<>(studList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable String id) throws InterruptedException, ExecutionException {
        return studentService.getStudentById(id);
    }

    @PostMapping("/student")
    public ResponseEntity<Void> createStudent(@RequestBody Student student) throws InterruptedException, ExecutionException {

        System.out.println(student);
        Student createdStudent = studentService.saveStudent(student);

        //Location
        //Get current resource url
        ///{id}
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdStudent.getID()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id,  @RequestBody Student student) throws InterruptedException, ExecutionException {
        Student studentUpdated = studentService.saveStudent(student);

        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @DeleteMapping("/student")
    public ResponseEntity<String> deleteAllStudent() throws ExecutionException, InterruptedException {

        studentService.deleteAllStudents();

        return ResponseEntity.status(HttpStatus.OK).body("All Students deleted succesfully");

    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) throws ExecutionException, InterruptedException {

        Student student = studentService.deleteStudent(id);

        if(student != null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    /*//@GetMapping("/student/course/{courseID}")
    @RequestMapping(method = RequestMethod.GET)
    public List<Student> getStudentByCourseID(@RequestParam(value = "courseID") String courseID) throws InterruptedException, ExecutionException {
        System.out.println(courseID);
        return studentService.getStudentByCourseID(courseID);
    }*/
}