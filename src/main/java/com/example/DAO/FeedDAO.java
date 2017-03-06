package com.example.DAO;

import com.example.Model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Jassy on 2017/3/3.
 * description:
 */
@Repository
@Mapper
public interface FeedDAO {

    String TABLE_NAME = "feed_table";
    String INSERT_FIELD = "user_id,type,feed_date,data";
    String SELECT_FIELD = "id," + INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME," (",INSERT_FIELD,
            ") values (#{userId},#{type},#{feedDate},#{data})"})
    int addFeed(Feed feed);

    @Select({"select * from ",TABLE_NAME," where id=#{id}"})
    Feed getFeedById(int id);

    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") int userIds,
                               @Param("count") int count);
}
