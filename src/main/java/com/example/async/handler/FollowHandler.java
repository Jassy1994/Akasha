package com.example.async.handler;

import com.example.Model.EntityType;
import com.example.Model.Message;
import com.example.Model.User;
import com.example.Service.InformationService;
import com.example.Service.MessageService;
import com.example.Service.UserService;
import com.example.async.EventHandler;
import com.example.async.EventModel;
import com.example.async.EventType;
import com.example.util.ZixunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Jassy on 2017/3/8.
 * description:
 */
@Component
public class FollowHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    InformationService informationService;

    @Autowired
    UserService userService;

    /**
     * 这个EventModel是用户关注事件发生时创建的;
     * @param model
     */
    @Override
    public void doHandle(EventModel model) {
        Message message=new Message();
        message.setFromId(ZixunUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setSendDate(new Date());
        User user=userService.selectById(model.getActorId());
        if(model.getEntityType()== EntityType.ENTITY_INFORMATION){
            message.setContent("用户"+user.getUsername()+"关注了你的资讯" +
                    informationService.selectById(model.getEntityId()).getTitle() +
                    ":http://127.0.0.1:8080/information/"+model.getEntityId());
        }else if(model.getEntityType()==EntityType.ENTITY_USER){
            message.setContent("用户"+user.getUsername()+"关注了你,http://127.0.0.1:8080/user/"+model.getActorId());
        }
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportedEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
