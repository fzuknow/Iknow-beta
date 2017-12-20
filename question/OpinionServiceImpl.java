package com.fzu.edu.service.impl;

import com.fzu.edu.dao.OpinionDao;
import com.fzu.edu.model.Opinion;
import com.fzu.edu.service.OpinionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
@Service("opinionService")
@Transactional(rollbackFor = Exception.class)
public class OpinionServiceImpl implements OpinionService {
    @Resource
    private OpinionDao opinionDao;


    public List<Opinion> getAllOpinion(){

        return opinionDao.selectAllOpinion();
    }
}
