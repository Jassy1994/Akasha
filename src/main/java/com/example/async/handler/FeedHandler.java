package com.example.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.Model.EntityType;
import com.example.Model.Feed;
import com.example.Model.Information;
import com.example.Model.User;
import com.example.Service.FeedService;
import com.example.Service.FollowService;
import com.example.Service.InformationService;
import com.example.Service.UserService;
import com.example.async.EventHandler;
import com.example.async.EventModel;
import com.example.async.EventType;
import com.example.util.JedisAdapter;
import com.example.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Jassy on 2017/3/8.
 * description:
 */
@Component
public class FeedHandler implements EventHandler{

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    InformationService informationService;

    @Autowired
    JedisAdapter jedisAdapter;

    private String buildFeedData(EventModel model){
        Map<String,String> map=new HashMap<>();
        User actor=userService.selectById(model.getActorId());
        if(actor==null){
            return null;
        }
        map.put("userId",String.valueOf(actor.getId()));
        map.put("username",actor.getUsername());
        map.put("headUrl",actor.getHeadUrl());

        //发布、评论、赞同或关注了资讯,将资讯信息保存;
        if(model.getEntityType()==EntityType.ENTITY_INFORMATION){
            Information information=informationService.selectById(model.getEntityId());
            if(information==null){
                return null;
            }

            map.put("informationId",String.valueOf(information.getId()));
            map.put("informationTitle",information.getTitle());
            map.put("event",String.valueOf(model.getEventType().getValue()));
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    /**
     *
     * @param model
     */
    @Override
    public void doHandle(EventModel model) {

        Feed feed=new Feed();
        feed.setFeedDate(new Date());
        feed.setUserId(model.getActorId());
        feed.setType(model.getEventType().getValue());
        feed.setData(buildFeedData(model));
        if(feed.getData()==null){
            //不支持的feed;
            return;
        }
        feedService.addFeed(feed);

        //给所有关注我的人推送新鲜事;
        List<Integer> followers=followService.getFollowers(EntityType.ENTITY_USER,model.getActorId(),
                (int)followService.getFollowersNum(EntityType.ENTITY_USER,model.getActorId()));
        followers.add(0);
        for(Integer i:followers){
            //我的粉丝的时间线;
            String timelineKey= RedisKeyUtil.getTimelineKey(i);
            //如果粉丝的时间线中已有20条新鲜事了,就不推送;
            if(jedisAdapter.scard(timelineKey)>=20){
                continue;
            }
            jedisAdapter.lpush(timelineKey,String.valueOf(feed.getId()));
        }
    }

    @Override
    public List<EventType> getSupportedEventTypes() {
        return Arrays.asList(new EventType[]{EventType.ADD_INFORMATION,EventType.FOLLOW,EventType.COMMENT,EventType.AGREEMENT});
    }
}
