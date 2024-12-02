package com.student.entity;

public class Student {
    private String studentId;
    private String studentName;
    private boolean absent = false;
    private boolean leave = false;
    private boolean answered = false;
    private Group group;

    public Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public String getId() {
        return this.studentId;
    }

    public String getName() {
        return this.studentName;
    }

    public void setName(String name) {
        this.studentName = name;
    }

    public void setId(String id) {
        this.studentId = id;
    }

    public boolean isAbsent() {
        return this.absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }

    public boolean isLeave() {
        return this.leave;
    }

    public void setLeave(boolean leave) {
        this.leave = leave;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isAnswered() {
        return this.answered;
    }

}
