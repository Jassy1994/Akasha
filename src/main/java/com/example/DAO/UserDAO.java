package com.example.DAO;

import com.example.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by Jassy on 2017/2/19.
 * description: 完成对user_table的数据库操作,将User模型封装起来;
 */

@Repository
@Mapper
//Mapper说明和数据库一一匹配;
public interface UserDAO {
    String TABLE_NAME="user_table";
    String INSERT_FIELD="username,password,head_url,introduction,agreement_num,salt";
    String SELECT_FIELD="id,"+INSERT_FIELD;
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,
            ") values(#{username},#{password},#{headUrl}," +
                    "#{introduction},#{agreementNum},#{salt})"})
    int addUser(User user);

    @Select({"select * from ",TABLE_NAME," where id=#{id}"})
    User getUserById(int id);
    @Select({"select * from ",TABLE_NAME," where username=#{name}"})
    User getUserByName(String name);
}
