package com.springboot.firebase.etiqa.springbootreststudent.Resource;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.springboot.firebase.etiqa.springbootreststudent.model.Student;
import com.springboot.firebase.etiqa.springbootreststudent.service.FirebaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/*@RestController
public class StudentResource {
    @Autowired
    FirebaseInitializer db;

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents() throws InterruptedException, ExecutionException {
        List<Student> studList = new ArrayList<Student>();
        CollectionReference student= db.getFirebase().collection("Student");
        System.out.println(student);
        ApiFuture<QuerySnapshot> querySnapshot= student.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Student stud = doc.toObject(Student.class);
            studList.add(stud);
        }
        return studList;
    }
}*/
