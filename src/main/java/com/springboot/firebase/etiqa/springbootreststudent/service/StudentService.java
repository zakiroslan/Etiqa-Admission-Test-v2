package com.springboot.firebase.etiqa.springbootreststudent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.protobuf.Api;
import com.springboot.firebase.etiqa.springbootreststudent.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//CRUD operations
@Service
public class StudentService {

    public static final String COL_NAME="Student";

    @Autowired
    FirebaseInitializer db;

    public List<Student> getAllStudents() throws InterruptedException, ExecutionException {
        List<Student> studList = new ArrayList<Student>();
        CollectionReference student= db.getFirebase().collection(COL_NAME);
        System.out.println(student);
        ApiFuture<QuerySnapshot> querySnapshot= student.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Student stud = doc.toObject(Student.class);
            studList.add(stud);
        }
        return studList;
    }

    public Student getStudentById(String id) throws InterruptedException, ExecutionException {

        DocumentReference documentReference = db.getFirebase().collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Student student = null;
        if(document.exists()) {
            student = document.toObject(Student.class);
            return student;
        }
        else {
            return null;
        }

    }

    public Student saveStudent(Student student) throws InterruptedException, ExecutionException {

        ApiFuture<QuerySnapshot> getAllStudent = db.getFirebase().collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = getAllStudent.get().getDocuments();

        Boolean foundStudent = false;

        for (QueryDocumentSnapshot document : documents) {
            //update if found ID
            if(document.getId().equals(student.getID())) {
                System.out.println("Document ID: "+document.getId());
                System.out.println("Student ID: "+student.getID());
                DocumentReference studentDR = db.getFirebase().collection(COL_NAME).document(document.getId());
                studentDR.delete();
                studentDR.set(student);
                foundStudent = true;
            }
        }

        if (foundStudent == false) {

            System.out.println(student.getName());
            System.out.println(student.getEmail());
            System.out.println(student.getCourseID());

            String documentID = db.getFirebase().collection(COL_NAME).document().getId();
            Map<String, Object> docData = new HashMap<>();
            docData.put("Name", student.getName());
            docData.put("ID", documentID.toString());
            docData.put("IC_No", student.getIC_No());
            docData.put("Email", student.getEmail());
            docData.put("CourseID", student.getCourseID());
            docData.put("CourseName", student.getCourseName());
            ApiFuture<WriteResult> collectionsApiFuture = db.getFirebase().collection(COL_NAME).document(documentID).set(docData);
            student.setID(documentID);
            System.out.println("Student ID: "+student.getID());
        }

        return student;
    }

    public Student deleteStudent(String id) throws ExecutionException, InterruptedException {

        Student student = getStudentById(id);

        if(student == null) {
            return null;
        } else {
            ApiFuture<WriteResult> writeResult = db.getFirebase().collection(COL_NAME).document(id.toString()).delete();
            return student;
        }

    }

    public void deleteAllStudents() throws InterruptedException, ExecutionException {

        ApiFuture<QuerySnapshot> getAllStudent = db.getFirebase().collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = getAllStudent.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            //loop through documents and delete each of it
            DocumentReference studentDR = db.getFirebase().collection(COL_NAME).document(document.getId());
            studentDR.delete();
        }
    }

    public List<Student> getStudentByCourseID(String courseID) throws InterruptedException, ExecutionException {

        List<Student> studList = new ArrayList<Student>();
        CollectionReference student= db.getFirebase().collection(COL_NAME);
        System.out.println(student);
        ApiFuture<QuerySnapshot> querySnapshot= student.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Student stud = doc.toObject(Student.class);
            System.out.println(stud);
            System.out.println(stud.getCourseID());
            String getCourseID = stud.getCourseID();
            if (getCourseID != null) {
                if(stud.getCourseID().equalsIgnoreCase(courseID)) {
                    studList.add(stud);
                }
            }
        }

        return studList;
    }
}
