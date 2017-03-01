package com.example;

import com.example.DAO.*;
import com.example.Model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jassy on 2017/2/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AkashaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    AnswerDAO answerDAO;

    @Autowired
    InformationDAO informationDAO;

    @Autowired
    CommentDAO commentDAO;

    @Test
    public void initData(){
        for(int i=1;i<6;i++){
            User user=new User();
            user.setUsername("user"+String.valueOf(i));
            user.setPassword("123456"+String.valueOf(i));
            user.setIntroduction("I'm user"+String.valueOf(i));
            userDAO.addUser(user);
        }
        for(int i=1;i<5;i++){
            Question question=new Question();
            question.setUserId(i);
            question.setQuestion("Question"+String.valueOf(i));
            question.setAnonymous(false);
            question.setDescription("Des");
            Date date=new Date();
            question.setAskDate(date);
            questionDAO.addQuestion(question);
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        {
            Answer answer=new Answer();
            answer.setAnonymous(false);
            answer.setUserId(1);
            answer.setAgreementNum(10);
            Date date=new Date();
            answer.setAnswerDate(date);
            answer.setQuestionId(2);
            answer.setContent("666");
            answerDAO.addAnswer(answer);
        }
        {
            Information information=new Information();
            information.setAgreementNum(15);
            information.setUserId(2);
            information.setCommentNum(3);
            information.setContent("Jassy!");
            information.setLink("http://www.baidu.com");
            Date date=new Date();
            information.setReleaseDate(date);
            information.setTitle("Akasha");
            informationDAO.addInformation(information);
        }
        {
            Comment comment=new Comment();
            comment.setContent("aaa");
            comment.setUserId(4);
            comment.setAgreementNum(3);
            comment.setEntityId(1);
            comment.setEntityType(1);
            Date date=new Date();
            comment.setCommentDate(date);
            commentDAO.addComment(comment);
        }
        System.out.println(simpleDateFormat.format(questionDAO.getQuestionByUserId(4).getAskDate()));
        System.out.println(userDAO.getUserById(3).getIntroduction());
    }
}
