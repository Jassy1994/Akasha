package com.example.DAO;

import com.example.Model.Information;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static com.example.DAO.InformationDAO.TABLE_NAME;

/**
 * Created by Jassy on 2017/2/21.
 */
@Mapper
public interface InformationDAO {

    String TABLE_NAME = "information_table";
    String INSERT_FIELD = "user_id,title,content,link,image,agreement_num,comment_num,release_date";
    String SELECT_FIELD = "id," + INSERT_FIELD;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELD,
            ") values(#{userId},#{title},#{content},#{link},#{image},#{agreementNum},#{commentNum},#{releaseDate})"})
    int addInformation(Information information);

    @Select({"select * from ", TABLE_NAME, " where id=#{id}"})
    Information getInformationById(int id);

    List<Information> getLatestInformations(@Param("userId")int userId,
                                            @Param("offset")int offset,
                                            @Param("limit")int limit);

    @Update({"update ", TABLE_NAME, " set title=#{title} where id=#{id}"})
    void updateTitle(@Param("title") String title, @Param("id") int id);

    @Update({"update ", TABLE_NAME, " set content=#{content} where id=#{id}"})
    void updateContent(@Param("content") String content, @Param("id") int id);

    @Update({"update ", TABLE_NAME, " set image=#{image} where id=#{id}"})
    void updateImage(@Param("image") String image, @Param("id") int id);

    @Update({"update ", TABLE_NAME, " set agreement_num=#{agreementNum} where id=#{id}"})
    void updateAgreementNum(@Param("agreementNum") int agreementNum, @Param("id") int id);

    @Update({"update ", TABLE_NAME, " set comment_num=#{commentNum} where id=#{id}"})
    void updateCommentNum(@Param("commentNum") int commentNum, @Param("id") int id);
}
