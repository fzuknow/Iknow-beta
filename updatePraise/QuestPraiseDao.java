package com.fzu.edu.dao;

import com.fzu.edu.model.QuestPraise;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
@Repository
public interface QuestPraiseDao {
    List<QuestPraise> selectAllQuestPraise();
    void insertRow(QuestPraise questPraise);//插入新记录
}
