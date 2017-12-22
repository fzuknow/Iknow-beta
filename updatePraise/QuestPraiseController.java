package com.fzu.edu.controller;

import com.fzu.edu.model.QuestPraise;
import com.fzu.edu.service.QuestPraiseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
@Controller
@RequestMapping("/questPraise")
public class QuestPraiseController {
    private Logger log = Logger.getLogger(QuestPraiseController.class);

    @Resource
    private QuestPraiseService questPraiseService;

    @RequestMapping("/showQuestPraise")
    public String showQuestPraise(HttpServletRequest request, Model model){
        log.info("问题点赞");
        List<QuestPraise> questPraiseList = questPraiseService.getAllQuestPraise();
        model.addAttribute("questPraiseList",questPraiseList);
        return null;
    }

}
