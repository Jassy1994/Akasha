package com.example.DAO;

import com.example.Model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Jassy on 2017/2/21.
 */
@Repository
@Mapper
public interface CommentDAO {

    String TABLE_NAME = "comment_table";
    String INSERT_FIELD = "user_id,entity_type,entity_id,content,agreement_num,comment_date,status";
    String SELECT_FIELD = "id," + INSERT_FIELD;

    @Insert({"insert into ", TABLE_NAME, "(",
            INSERT_FIELD, ") values(#{userId},#{entityType},#{entityId},#{content},#{agreementNum},#{commentDate},#{status})"})
    int addComment(Comment comment);

    @Select({"select * from ", TABLE_NAME, " where id=#{id}"})
    Comment getCommentById(int id);

    @Select({"select count(id) from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId}"})
    int getCommentCountByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    @Select({"select count(*) from ", TABLE_NAME, " where user_id=#{userId}"})
    int getCommentCountByUser(int userId);

    @Select({"select * from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId}"})
    List<Comment> getCommentByEntity(int entityType, int EntityId);

    @Update({"update ", TABLE_NAME, " set status=#{status} where id=#{id}"})
    void updateCommentById(@Param("status") int status, @Param("id") int id);
}
