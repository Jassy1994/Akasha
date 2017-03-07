package com.example.Controller;

import com.example.Model.Comment;
import com.example.Model.HostHolder;
import com.example.Service.CommentService;
import com.example.Service.InformationService;
import com.example.async.EventModel;
import com.example.async.EventProducer;
import com.example.async.EventType;
import com.example.util.ZixunUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by Jassy on 2017/3/7.
 * description:
 */
@Controller
public class CommentController {

    private final static Logger logger= LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    InformationService informationService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"addComment"},method = RequestMethod.POST)
    public String addComment(@RequestParam("entityType")int entityType,
                             @RequestParam("entityId")int entityId,
                             @RequestParam("content")String content){
        try {
            Comment comment=new Comment();
            comment.setContent(content);
            if(hostHolder.getUser()==null){
                comment.setUserId(ZixunUtil.ANONYMOUS_USERID);
            }else{
                comment.setUserId(hostHolder.getUser().getId());
            }
            comment.setCommentDate(new Date());
            comment.setEntityType(entityType);
            comment.setEntityId(entityId);
            commentService.addComment(comment);

            int count=commentService.getCommentNumByEntity(entityType,entityId);
            // TODO: 2017/3/7 添加针对不同实体的处理方法;添加通过entityType和entityId得到entityOwnerId的方法;
            eventProducer.releaseEvent(new EventModel().setEventType(EventType.COMMENT)
            .setActorId(comment.getUserId()).setEntityType(entityType).setEntityId(entityId));
        }catch (Exception e){
            logger.error("添加评论失败! "+e.getMessage());
        }
        // TODO: 2017/3/7 添加返回路径;
        return "redirect:/";
    }
}
