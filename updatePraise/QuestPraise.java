package com.fzu.edu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
public class QuestPraise implements Serializable {
    private int id;
    private int studentId;
    private int questionId;
    private Date praiseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Date getPraiseDate() {
        return praiseDate;
    }

    public void setPraiseDate(Date praiseDate) {
        this.praiseDate = praiseDate;
    }
}
