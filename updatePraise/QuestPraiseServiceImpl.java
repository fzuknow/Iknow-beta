package com.fzu.edu.service.impl;

import com.fzu.edu.dao.QuestPraiseDao;
import com.fzu.edu.model.QuestPraise;
import com.fzu.edu.service.QuestPraiseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
@Service("questPraiseService")
@Transactional(rollbackFor = Exception.class)
public class QuestPraiseServiceImpl implements QuestPraiseService {
    @Resource
    private QuestPraiseDao questPraiseDao;


    public List<QuestPraise> getAllQuestPraise(){

        return questPraiseDao.selectAllQuestPraise();
    }

    public QuestPraise insertRow(int studentId, int questionId, String praise_date){
        QuestPraise questPraise = new QuestPraise();
        questPraise.setStudentId(studentId);
        questPraise.setQuestionId(questionId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        questPraise.setPraiseDate(new Date());
        questPraiseDao.insertRow(questPraise);
        return questPraise;
    }
}
