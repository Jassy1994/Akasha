package com.example.DAO;

import com.example.Model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import static com.example.DAO.LoginTicketDAO.INSERT_FIELD;
import static com.example.DAO.LoginTicketDAO.TABLE_NAME;

/**
 * Created by Jassy on 2017/2/24.
 * description:
 */

@Repository
@Mapper
public interface LoginTicketDAO {

    String TABLE_NAME = "login_ticket_table";
    String INSERT_FIELD = "user_id,expired,is_valid_status,ticket";
    String SELECT_FIELD = "id," + INSERT_FIELD;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELD,
            ")values(#{userId},#{expired},#{isValidStatus},#{ticket})"})
    int addLoginTicket(LoginTicket ticket);

    @Select({"select * from ", TABLE_NAME, " where id=#{id}"})
    LoginTicket getTicketById(int id);

    @Select({"select * from ",TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set is_valid_status=#{isValidStatus} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("isValidStatus") boolean isValidStatus);
}
