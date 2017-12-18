
package com.fzu.edu.service;

import com.fzu.edu.model.Question;
import com.fzu.edu.model.User;

import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
public interface QuestionService {
    List<Question> getAllQuestion();
    Question questionInfo(int studentId);
    Question insertQuest(int userId,String content);//插入新问题
    Question updateTime(int questionId,String cur_time);// 更新提问时间
    Question getName(int studentId);//获得学生昵称
}
