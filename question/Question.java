package com.fzu.edu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
public class Question implements Serializable {
    private int id;
    private int studentId;
    private String releaseDate;
    private boolean isReward;
    private int reward;
    private int questionlabelId;
    private String content;
    private int praiseNum;
    private int commentNum;
    private boolean isDelete;
    private boolean isSolved;
    private User student;

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

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

    public boolean getIsReward() {
        return isReward;
    }

    public void setReward(boolean reward) {
        isReward = reward;
}

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getQuestionlabelId() {
        return questionlabelId;
    }

    public void setQuestionlabelId(int questionlabelId) {
        this.questionlabelId = questionlabelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public boolean getDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean getSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }
}
