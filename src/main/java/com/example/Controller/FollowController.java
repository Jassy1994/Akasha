package com.example.Controller;

import com.example.Model.*;
import com.example.Service.CommentService;
import com.example.Service.FollowService;
import com.example.Service.InformationService;
import com.example.Service.UserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jassy on 2017/3/7.
 * description:
 */
@Controller
public class FollowController {

    private final static Logger logger = LoggerFactory.getLogger(FollowController.class);

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    InformationService informationService;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    HostHolder hostHolder;

    /**
     * @param userId local用户关注userId的用户;
     * @return
     */
    @RequestMapping(path = {"/followUser"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return ZixunUtil.getJSONString(999, "未登录");
        }
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        eventProducer.releaseEvent(new EventModel().setEventType(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityType(EntityType.ENTITY_USER)
                .setEntityId(userId).setEntityOwnerId(userId));
        return ZixunUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweesNum(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
    }

    /**
     * @param userId 取关;
     * @return
     */
    @RequestMapping(path = {"/unfollowUser"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return ZixunUtil.getJSONString(999, "未登录");
        }
        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        return ZixunUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweesNum(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
    }

    /**
     * 关注这条资讯;
     *
     * @param informationId
     * @return
     */
    @RequestMapping(path = {"/followInformation"}, method = {RequestMethod.POST})
    @ResponseBody
    public String followInformation(@RequestParam("informationId") int informationId) {
        if (hostHolder.getUser() == null) {
            return ZixunUtil.getJSONString(999, "未登录");
        }
        Information information = informationService.selectById(informationId);
        if (information == null) {
            return ZixunUtil.getJSONString(1, "问题不存在");
        }
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_INFORMATION, informationId);
        eventProducer.releaseEvent(new EventModel().setEventType(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_INFORMATION).setEntityId(informationId)
                .setEntityOwnerId(information.getUserId()));

        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getUsername());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowersNum(EntityType.ENTITY_INFORMATION, informationId));
        return ZixunUtil.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(path = {"/unfollowInformation"}, method = RequestMethod.POST)
    @ResponseBody
    public String unfollowInformation(@RequestParam("informationId") int informationId) {
        if (hostHolder == null) {
            return ZixunUtil.getJSONString(999, "未登录");
        }
        Information information = informationService.selectById(informationId);
        if (information == null) {
            return ZixunUtil.getJSONString(1, "问题不存在");
        }
        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_INFORMATION, informationId);
        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowersNum(EntityType.ENTITY_INFORMATION, informationId));
        return ZixunUtil.getJSONString(ret ? 0 : 1, info);
    }

    /**
     * follower是关注...的用户;followee是用户关注的...
     * 得到关注userId的用户;
     *
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(path = {"/user/{userId}/followers"}, method = RequestMethod.GET)
    public String followers(Model model, @PathVariable("userId") int userId) {
        List<Integer> followerList = followService.getFollowers(EntityType.ENTITY_USER, userId, 10);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followers", getUserInfo(hostHolder.getUser().getId(), followerList));
        } else {
            model.addAttribute("followers", getUserInfo(0, followerList));
        }
        model.addAttribute("folllowersNum", followService.getFollowersNum(EntityType.ENTITY_USER, userId));
        model.addAttribute("curUser", userService.selectById(userId));
        return "followers";
    }

    /**
     * 得到userId用户关注的人;
     *
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(path = {"/user/{userId}/followees"}, method = RequestMethod.GET)
    public String followees(Model model, @PathVariable("userId") int userId) {
        List<Integer> followeeList = followService.getFollowees(userId, EntityType.ENTITY_USER, 10);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followees", getUserInfo(hostHolder.getUser().getId(), followeeList));
        } else {
            model.addAttribute("followees", getUserInfo(0, followeeList));
        }
        model.addAttribute("followeesNum", followService.getFolloweesNum(userId, EntityType.ENTITY_USER));
        model.addAttribute("curUser", userService.selectById(userId));
        return "followees";
    }

    /**
     * 得到userId用户关注的资讯;
     *
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(path = {"user/{userId}/followeeInfos"}, method = RequestMethod.GET)
    public String followeeInfos(Model model, @PathVariable("userId") int userId) {
        List<Integer> followeeInfoList = followService.getFollowees(userId, EntityType.ENTITY_INFORMATION, 10);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followeeInfos", getUserInfo(hostHolder.getUser().getId(), followeeInfoList));
        } else {
            model.addAttribute("followeeInfos", getUserInfo(0, followeeInfoList));
        }
        model.addAttribute("followeeInfosNum", followService.getFolloweesNum(userId, EntityType.ENTITY_INFORMATION));
        model.addAttribute("curUser", userService.selectById(userId));
        return "followeeInfos";
    }

    private List<ViewObject> getUserInfo(int localUserId, List<Integer> userList) {
        List<ViewObject> vos = new ArrayList<>();
        for (Integer uid : userList) {
            User user = userService.selectById(uid);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("commentsNum", commentService.getCommentNumByUser(uid));
            vo.set("followersNum", followService.getFollowersNum(EntityType.ENTITY_USER, uid));
            vo.set("followeesNum", followService.getFolloweesNum(uid, EntityType.ENTITY_USER));
            vo.set("followeeInfosNum", followService.getFolloweesNum(uid, EntityType.ENTITY_INFORMATION));
            if (localUserId != 0) {
                vo.set("followed", followService.isFollower(localUserId, EntityType.ENTITY_USER, uid));
            } else {
                vo.set("followed", false);
            }
            vos.add(vo);
        }
        return vos;
    }
}
