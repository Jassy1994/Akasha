package com.example.Controller;

import com.example.Model.*;
import com.example.Service.*;
import com.example.async.EventModel;
import com.example.async.EventProducer;
import com.example.async.EventType;
import com.example.util.ZixunUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jassy on 2017/2/28.
 * description:
 */
@Controller
public class InformationController {

    private final static Logger logger = LoggerFactory.getLogger(InformationController.class);

    @Autowired
    InformationService informationService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;

    @Autowired
    AgreementService agreementService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/information/{id}"}, method = {RequestMethod.GET})
    public String InformationDetail(Model model, @PathVariable("id") int id) {
        Information information = informationService.selectById(id);
        model.addAttribute("information", information);

        List<Comment> commentList = commentService.getCommentByEntity(EntityType.ENTITY_INFORMATION, id);
        List<ViewObject> comments = new ArrayList<>();
        //填充评论区;
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            if (hostHolder.getUser() == null) {
                vo.set("agreed", 0);
            } else {
                vo.set("agreed", agreementService.getAgreementStatus(
                        hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }
            vo.set("agreementNum", agreementService.getAgreementNum(EntityType.ENTITY_COMMENT, comment.getId()));
            vo.set("user", userService.selectById(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments", comments);

        List<ViewObject> followers = new ArrayList<>();
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_INFORMATION, id, 10);
        for (int userId : users) {
            ViewObject vo=new ViewObject();
            User user=userService.selectById(userId);
            if(user==null){
                continue;
            }
            vo.set("name",user.getUsername());
            vo.set("headUrl",user.getHeadUrl());
            vo.set("id",user.getId());
            followers.add(vo);
        }
        model.addAttribute("followers", followers);
        if(hostHolder.getUser()!=null){
            model.addAttribute("followed",followService.isFollower(hostHolder.getUser().getId(),
                    EntityType.ENTITY_QUESTION,id));
        }else{
            model.addAttribute("followed",false);
        }
        return "detail";
    }


    @RequestMapping(path = "/information/add", method = RequestMethod.POST)
    @ResponseBody
    public String addInformation(@RequestParam("title") String title,
                                 @RequestParam("content") String content){
        try{
            Information information=new Information();
            information.setTitle(title);
            information.setContent(content);
            information.setReleaseDate(new Date());
            //可设置匿名用户;
            if(hostHolder.getUser()==null){
                information.setUserId(ZixunUtil.ANONYMOUS_USERID);
            }else{
                information.setUserId(hostHolder.getUser().getId());
            }
            if(informationService.releaseInformation(information)>0){
                eventProducer.releaseEvent(new EventModel().setEventType(EventType.ADD_INFORMATION)
                        .setActorId(information.getUserId()).setEntityId(information.getId())
                        .setExt("title",information.getTitle()).setExt("content",information.getContent()));
                return ZixunUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("发布资讯失败! "+e.getMessage());
        }
        return ZixunUtil.getJSONString(1,"失败");
    }
}
