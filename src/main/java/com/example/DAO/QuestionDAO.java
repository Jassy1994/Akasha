package com.example.DAO;

import com.example.Model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import static com.example.DAO.QuestionDAO.INSERT_FIELD;
import static com.example.DAO.QuestionDAO.TABLE_NAME;

/**
 * Created by Jassy on 2017/2/21.
 */
@Repository
@Mapper
public interface QuestionDAO {
    String TABLE_NAME="question_table";
    String INSERT_FIELD="question,user_id,is_anonymous,description,answer_num,ask_date";
    String SELECT_FIELD="id,"+INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,
            ") values(#{question},#{userId},#{isAnonymous},#{description},#{answerNum},#{askDate})"})
    int addQuestion(Question question);

    @Select({"select * from ",TABLE_NAME," where user_id=#{userId}"})
    Question getQuestionByUserId(int userId);

}
