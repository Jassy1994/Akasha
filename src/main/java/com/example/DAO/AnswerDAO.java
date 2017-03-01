package com.example.DAO;

import com.example.Model.Answer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import static com.example.DAO.AnswerDAO.INSERT_FIELD;
import static com.example.DAO.AnswerDAO.TABLE_NAME;

/**
 * Created by Jassy on 2017/2/21.
 */
@Mapper
public interface AnswerDAO {
    String TABLE_NAME="answer_table";
    String INSERT_FIELD="user_id,question_id,is_anonymous,content,agreement_num,comment_num,answer_date";
    String SELECT_FIELD="id,"+INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,
            ") values (#{userId},#{questionId},#{isAnonymous},#{content},#{agreementNum},#{commentNum},#{answerDate})"})
    int addAnswer(Answer answer);

    @Select({"select * from ",TABLE_NAME," where id=#{id}"})
    Answer getAnswerById(int id);
}
