package com.springboot.firebase.etiqa.springbootreststudent.model;

public class Student {

    private String ID;
    private String Name;
    private String IC_No;
    private String Email;
    private String CourseName;
    private String CourseID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIC_No() {
        return IC_No;
    }

    public void setIC_No(String IC_No) {
        this.IC_No = IC_No;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }
}
