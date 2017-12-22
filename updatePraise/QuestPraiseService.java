package com.fzu.edu.service;

import com.fzu.edu.model.QuestPraise;

import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
public interface QuestPraiseService {
    List<QuestPraise> getAllQuestPraise();
    QuestPraise insertRow(int studentId, int questionId, String praise_date);//插入新记录
}
