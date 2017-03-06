package com.example.DAO;

import com.example.Model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.DAO.MessageDAO.INSERT_FIELD;
import static com.example.DAO.MessageDAO.TABLE_NAME;

/**
 * Created by Jassy on 2017/2/24.
 */
@Repository
@Mapper
public interface MessageDAO {

    String TABLE_NAME = "message_table";
    String INSERT_FIELD = "from_id,to_id,conversation_id,content,send_date";
    String SELECT_FIELD = "id," + INSERT_FIELD;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELD,
            ") values(#{fromId},#{toId},#{conversationId},#{content},#{sendDate})"})
    int addMessage(Message message);

    @Select({"select * from ", TABLE_NAME, " where id=#{id}"})
    Message findMessageById(int id);

    @Select({"select * from ", TABLE_NAME,
            " where conversationId=#{conversationId} order by send_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME,
            " where has_read=false and to_id=#{userId} and conversationId=#{conversationId}"})
    int getUnreadMessageCount(@Param("userId") int userId,
                            @Param("conversationId") String conversationId);

    @Select({"select ", INSERT_FIELD, " ,count(id) as id from (select * from ", TABLE_NAME,
            " where from_id=#{userId} or to_id=#{userId} order by send_date desc) tt group" +
                    " by conversation_id order by send_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);
}
