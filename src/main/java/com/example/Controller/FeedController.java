package com.example.Controller;

import com.example.Model.EntityType;
import com.example.Model.Feed;
import com.example.Model.HostHolder;
import com.example.Service.FeedService;
import com.example.Service.FollowService;
import com.example.util.JedisAdapter;
import com.example.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jassy on 2017/3/7.
 * description: 如何选择推拉模式？对用户进行筛选。一个冷用户(一段时间不上线的用户)采用拉模式;
 * 对于关注大V的用户，不管用户是否活跃，都采用拉的方式；其他关注者不多的用户的新鲜事采用推模式;
 * 分级缓存，按月拆分保存等；让用户优先看到最近的，时间间隔较长的数据被访问的几率不大;
 * flyweight：新鲜事的去重，仅仅保存一份;
 */
@Controller
public class FeedController {
    private final static Logger logger = LoggerFactory.getLogger(FeedController.class);

    @Autowired
    FeedService feedService;

    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 推模式：从我的时间线里取出新鲜事,而新鲜事是由事件发生者发送到所有关注他的用户的TimeLine中;
     * 优点：直接推送给用户，但是如果关注我的人很多，就需要推送给很多人，占用存储空间;
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/pushfeeds"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String getPushFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(localUserId), 0, 10);
        List<Feed> feeds = new ArrayList<>();
        for (String fid : feedIds) {
            Feed feed = feedService.getFeedById(Integer.parseInt(fid));
            if (feed != null) {
                feeds.add(feed);
            }
        }
        model.addAttribute("feeds", feeds);
        return "feeds";
    }

    /**
     * 拉模式：用户先得到自己关注的实体列表;再通过实体列表去数据库中搜索这些实体的新鲜事；
     * 优点：用户上线才会去拉，平时不占用存储空间;
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/pullfeeds"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String getPullFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<Integer> followees = new ArrayList<>();
        if (localUserId != 0) {
            followees = followService.getFollowees(localUserId, EntityType.ENTITY_USER,
                    (int) followService.getFolloweesNum(localUserId, EntityType.ENTITY_USER));
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        model.addAttribute("feeds", feeds);
        return "feeds";
    }
}
