
package com.fzu.edu.service;

import com.fzu.edu.model.Question;

import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
public interface QuestionService {
    List<Question> getAllQuestion();
    Question questionInfo(int studentId);
    Question insertQuest(int userId, String content);//插入新问题
    Question updateTime(int questionId, String cur_time);// 更新提问时间
    List<Question> queryForList();//获得学生昵称
    //Question updatePraise(int questionId, boolean isPraise);// 更新评论数
    Question getQuestion(int questionId);//查找问题
}
