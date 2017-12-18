package com.fzu.edu.service.impl;


import com.fzu.edu.dao.QuestionDao;
import com.fzu.edu.model.Question;
import com.fzu.edu.model.User;
import com.fzu.edu.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
@Service("questionService")
@Transactional(rollbackFor = Exception.class)
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionDao questionDao;
    public List<Question> getAllQuestion(){

        return questionDao.selectAllQuestion();
    }

    public Question questionInfo(int studentId){
        Question question = new Question();
        question.setStudentId(studentId);
        return questionDao.questionInfo(question);
    }

    /*
   插入问题
    */
    public Question insertQuest(int userId,String content){
        Question question = new Question();
        question.setStudentId(userId);
        question.setContent(content);
        questionDao.insertQuest(question);
        return question;
    }
    /*
    更新提问时间
     */
    public Question updateTime(int questionId,String cur_time) {
        Question question = new Question();
        question.setId(questionId);
        question.setReleaseDate(cur_time);
        questionDao.updateTime(question);
        return question;
    }

    /*
    获得学生昵称
     */
    public Question getName(int studentId) {
        Question question = new Question();
        User user = new User();
        question.setStudentId(studentId);
        user.setStudentId(studentId);
        questionDao.getName(question,user);
        return question;
    }
}



