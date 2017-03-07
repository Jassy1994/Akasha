package com.example.Controller;

import com.example.Model.Comment;
import com.example.Model.HostHolder;
import com.example.Service.AgreementService;
import com.example.Service.CommentService;
import com.example.Service.UserService;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Jassy on 2017/3/7.
 * description:
 */
@Controller
public class AgreementController {

    private final static Logger logger = LoggerFactory.getLogger(AgreementController.class);

    @Autowired
    AgreementService agreementService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    /**
     * 赞同功能，可以赞同资讯(Information),回答(Answer),评论(Comment);
     *
     * @param entityType
     * @param entityId
     * @return
     */
    @RequestMapping(path = {"/agree"}, method = RequestMethod.POST)
    @ResponseBody
    public String agree(@RequestParam("entityType") int entityType,
                        @RequestParam("entityId") int entityId) {
        if (hostHolder.getUser() == null) {
            return ZixunUtil.getJSONString(999,"未登录");
        }
        Comment comment = commentService.selectCommentById(entityId);
        // TODO: 2017/3/7 添加针对不同实体的处理方法;
        eventProducer.releaseEvent(new EventModel().setEventType(EventType.AGREEMENT)
                .setActorId(hostHolder.getUser().getId()).setEntityType(entityType).setEntityId(entityId)
                .setEntityOwnerId(comment.getUserId()));
        long agreementNum = agreementService.agreement(hostHolder.getUser().getId(), entityType, entityId);
        return ZixunUtil.getJSONString(0, String.valueOf(agreementNum));
    }

    @RequestMapping(path = {"/cancelAgree"}, method = RequestMethod.POST)
    @ResponseBody
    public String cancelAgree(@RequestParam("entityType") int entityType,
                              @RequestParam("entityId") int entityId) {
        if (hostHolder.getUser() == null) {
            return ZixunUtil.getJSONString(999,"未登录");
        }
        long agreementNum = agreementService.cancelAgreement(hostHolder.getUser().getId(), entityType, entityId);
        return ZixunUtil.getJSONString(0, String.valueOf(agreementNum));
    }

}
