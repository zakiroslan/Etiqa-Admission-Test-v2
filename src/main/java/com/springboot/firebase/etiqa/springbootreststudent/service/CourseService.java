package com.springboot.firebase.etiqa.springbootreststudent.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.springboot.firebase.etiqa.springbootreststudent.model.Course;
import com.springboot.firebase.etiqa.springbootreststudent.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CourseService {

    public static final String COL_NAME="Course";

    @Autowired
    FirebaseInitializer db;

    public List<Course> getAllCourses() throws InterruptedException, ExecutionException {
        List<Course> courseList = new ArrayList<Course>();
        CollectionReference course= db.getFirebase().collection(COL_NAME);
        System.out.println(course);
        ApiFuture<QuerySnapshot> querySnapshot= course.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Course courseObj = doc.toObject(Course.class);
            courseList.add(courseObj);
        }
        return courseList;
    }

    public Course saveCourse(Course course) throws InterruptedException, ExecutionException {

        String documentID = db.getFirebase().collection(COL_NAME).document().getId();
        Map<String, Object> docData = new HashMap<>();
        docData.put("ID", documentID.toString());
        docData.put("Course_ID", course.getCourse_ID());
        docData.put("Course_Name", course.getCourse_Name());
        ApiFuture<WriteResult> collectionsApiFuture = db.getFirebase().collection(COL_NAME).document(documentID).set(docData);
        course.setID(documentID);
        System.out.println("Course ID: "+course.getID());

        return course;
    }
}
