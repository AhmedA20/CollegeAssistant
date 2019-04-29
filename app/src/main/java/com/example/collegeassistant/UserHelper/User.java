package com.example.collegeassistant.UserHelper;

import com.google.firebase.database.annotations.Nullable;


public class User {

    @Nullable private String uid;
    @Nullable private String username;
    @Nullable private String urlPicture;
    private boolean isNew;

    //---Student Specific
    private String eduYear;//----corresponds to grade the year the student is assigned to.
    private String department;//---- the department the student assigned to.

    //---Professor specific
    private String assignedDepartment;

    public User() { }

    //----Abstract Student
    public User(String uid, String year,String department, boolean isNew) {
        this.uid = uid;
        this.eduYear = year;
        this.department = department;
        this.isNew = isNew;
    }

    //----Abstract Professor
    public User(String uid, String assignedDepartment, boolean isNew) {
        this.uid = uid;
        this.assignedDepartment = assignedDepartment;
        this.isNew = isNew;
    }

    //----Student Constructor
    public User(String uid, String username, String urlPicture, String year,String department) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.eduYear = year;
        this.department = department;
    }

    //----Professor Constructor
    public User(String uid, String username, String urlPicture, String assignedDepartment) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.assignedDepartment = assignedDepartment;
    }



    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public String getUrlPicture() { return urlPicture; }
    public String getAssignedDepartment() { return assignedDepartment; }
    public String getDepartment() { return department; }
    public String getEduYear() { return eduYear; }
    public boolean getIsNew(){return isNew;}

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setAssignedDepartment(String assignedDepartment) { this.assignedDepartment = assignedDepartment; }
    public void setDepartment(String department) { this.department = department; }
    public void setEduYear(String eduYear) { this.eduYear = eduYear; }
    public void setIsNew(boolean isNew){ this.isNew = isNew;}

}
