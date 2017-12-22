package com.fzu.edu.dao;

import com.fzu.edu.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
@Repository
public interface QuestionDao {
    List<Question> selectAllQuestion();
    Question questionInfo(Question question);//学生的问题

    void insertQuest(Question question);//插入新问题
    void updateTime(Question question);//更新提问时间

    List<Question> queryForList();//获得学生昵称

    Question getQuestion(Question question);//获得问题
    void updatePraise(Question question);//更新评论数
}
