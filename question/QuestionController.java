package com.fzu.edu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fzu.edu.model.Question;
import com.fzu.edu.model.User;
import com.fzu.edu.service.QuestionService;
import com.fzu.edu.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ${iknow} on ${2017/11/1}.
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    private Logger log = Logger.getLogger(QuestionController.class);
    @Resource
    private QuestionService questionService;
    //private List<Question> questionList;
    private List<Question> queryInf;
    /*
    返回问题列表
     */
    @ResponseBody
    @RequestMapping("/questionshow")
    public List<Map<String, Object>> showQuestion(HttpServletRequest request, Model model) {
        log.info("查询问题信息");


        String result = "fail";
        //questionList = questionService.getAllQuestion();

        queryInf = questionService.queryForList();

        //model.addAttribute("questionList", questionList);
        model.addAttribute("questionList", queryInf);
        //将问题按时间进行排序
//        Collections.sort(questionList, new Comparator<Question>() {
//            public int compare(Question q1, Question q2) {
//                return q2.getReleaseDate().compareTo(q1.getReleaseDate());
//            }
//        });
        Collections.sort(queryInf, new Comparator<Question>() {
            public int compare(Question q1, Question q2) {
                return q2.getReleaseDate().compareTo(q1.getReleaseDate());
            }
        });

        //List<Map<String, Object>> allquestion= new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
//        for(int i = 0; i < questionList.size();i++){
//            result = JSON.toJSONString(questionList.get(i), SerializerFeature.DisableCircularReferenceDetect);
//            Map<String, Object> results = new HashMap<String, Object>();
//            results.put("result", result);
//            allquestion.add(results);
//        }

        for(int i = 0; i < queryInf.size();i++){
            result = JSON.toJSONString(queryInf.get(i), SerializerFeature.DisableCircularReferenceDetect);
            Map<String, Object> results = new HashMap<String, Object>();
            results.put("result", result);
            all.add(results);
        }
        System.out.println("关联信息"+all);
//        System.out.println("所有信息"+allquestion);
//        return allquestion;
        return all;
    }
    /*
    返回个人问题列表
     */
    @ResponseBody
    @RequestMapping(value = "/questionInf", method = RequestMethod.POST)
    public Map<String, Object> quesInformation(
//            questionList = questionService.getAllQuestion();
            @RequestParam(value = "userId") int studentId,
            HttpSession httpSession) {
        String result = "fail";
        try {
            System.out.println("学生ID："+studentId);
            Question question = questionService.questionInfo(studentId);
            //返回文章信息
            httpSession.setAttribute("currentQuestion", question);
            result =  JSON.toJSONString(question, SerializerFeature.DisableCircularReferenceDetect);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = "unexist";
        }
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("result", result);
        return results;
    }

    /*
    插入新问题
     */
    @ResponseBody
    @RequestMapping(value = "/quest", method = RequestMethod.POST)
    public Map<String, Object> Quest(
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "content") String content, HttpSession httpSession) {
        String result = "fail";
        try {
                System.out.println(userId + "," + content);
                Question question = questionService.insertQuest(userId,content);
                result = "成功插入问题！";

                // 获取系统当前时间，更新数据库中的时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                //获得userId对应的questionId
                questionService.updateTime(question.getId(),df.format(new Date()));

                httpSession.setAttribute("currentQuestion", question);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = "unexist";
        }
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("result", result);
        return results;
    }
}