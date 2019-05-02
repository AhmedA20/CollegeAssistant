package com.example.collegeassistant.Models;

import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;


public class User {

    @Nullable private String uid;
    @Nullable private String username;
    @Nullable private String urlPicture;
    @Nullable private boolean isProfessor;
    @NotNull private boolean isNew;

    //---Student Specific
    @Nullable private String eduYear;//----corresponds to grade the year the student is assigned to.
    @Nullable private String department;//---- the department the student assigned to.

    //---Professor specific
    @Nullable private String assignedDepartment;

    public User() { }

    //used for submitting new users only
    /*public User(boolean isNew){
        this.isNew = isNew;
    }*/

    //----Abstract Student
    public User(String uid, String year,String department,boolean isProfessor,boolean isNew) {
        this.uid = uid;
        this.eduYear = year;
        this.department = department;
        this.isProfessor = isProfessor;
        this.isNew = isNew;
    }

    //----Abstract Professor
    public User(String uid, String assignedDepartment, boolean isProfessor, boolean isNew) {
        this.uid = uid;
        this.assignedDepartment = assignedDepartment;
        this.isProfessor = isProfessor;
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
    public boolean getIsProfessor(){return isProfessor;}

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setAssignedDepartment(String assignedDepartment) { this.assignedDepartment = assignedDepartment; }
    public void setDepartment(String department) { this.department = department; }
    public void setEduYear(String eduYear) { this.eduYear = eduYear; }
    public void setIsNew(boolean isNew){ this.isNew = isNew;}
    public void setIsProfessor(boolean isProfessor){ this.isProfessor = isProfessor;}

}
