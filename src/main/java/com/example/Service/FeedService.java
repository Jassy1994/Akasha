package com.example.Service;

import com.example.DAO.FeedDAO;
import com.example.Model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jassy on 2017/3/3.
 * description:
 */
@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;

    public List<Feed> getUserFeeds(int maxId,int userIds,int count){
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }
    public Feed getFeedById(int id){
        return feedDAO.getFeedById(id);
    }

    public boolean addFeed(Feed feed){
        feedDAO.addFeed(feed);
        return feed.getId()>0;
    }
}
