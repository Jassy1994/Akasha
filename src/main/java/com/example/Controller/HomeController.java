package com.example.Controller;


import com.example.Model.*;
import com.example.Service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jassy on 2017/2/24.
 */
@Controller
public class HomeController {

    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @Autowired
    InformationService informationService;

    @Autowired
    CommentService commentService;

    @Autowired
    AgreementService agreementService;

    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    List<ViewObject> getInformations(int userId, int offset, int limit) {
        List<Information> informationList= informationService.selectLatestInformations(userId, offset, limit);
        List<ViewObject> vos=new ArrayList<>();
        for(Information information:informationList){
            ViewObject vo=new ViewObject();
            vo.set("information",information);
            vo.set("user",userService.selectById(information.getUserId()));
            vo.set("agreement",agreementService.getAgreementNum(1,information.getId()));
            vo.set("comment",commentService.getCommentNumByEntity(1,information.getId()));
            vos.add(vo);
        }
        return vos;
    }


    @RequestMapping(path = {"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getInformations(1,0,10));
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.GET,RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId")int userId){
        model.addAttribute("vos",getInformations(userId,0,10));
        User user=userService.selectById(userId);
        ViewObject vo=new ViewObject();
        vo.set("user",user);
        vo.set("commentNum",commentService.getCommentNumByUser(userId));
        vo.set("followerNum",followService.getFollowersNum(EntityType.ENTITY_USER,userId));
        vo.set("followeeNum",followService.getFolloweesNum(userId,EntityType.ENTITY_USER));

        if(hostHolder.getUser()!=null){
            vo.set("followed",followService.isFollower(hostHolder.getUser().getId(),EntityType.ENTITY_USER,userId));
        }else{
            vo.set("followed",false);
        }
        model.addAttribute("profileUser",vo);
        return "profile";
    }
}
