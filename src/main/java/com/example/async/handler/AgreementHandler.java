package com.example.async.handler;

import com.example.Model.Message;
import com.example.Model.User;
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
 * Created by Jassy on 2017/3/2.
 * description:
 */
@Component
public class AgreementHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message=new Message();
        message.setFromId(ZixunUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setSendDate(new Date());
        User user=userService.selectById(model.getActorId());
        //message.setContent("用户"+user.getUsername()+"赞了你的"+);
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportedEventTypes() {
        return Arrays.asList(EventType.AGREEMENT);
    }
}
