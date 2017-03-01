package com.example.Service;

import com.example.DAO.CommentDAO;
import com.example.Model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jassy on 2017/2/28.
 * description:
 */
@Service
public class CommentService {
    Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    CommentDAO commentDAO;

    public Comment selectCommentById(int id) {
        return commentDAO.getCommentById(id);
    }

    public void deleteCommentById(int id) {
        commentDAO.updateCommentById(1, id);
    }

    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }

    public int getCommentNumByEntity(int entityType,int entityId){
        return commentDAO.getCommentCountByEntity(entityType, entityId);
    }

    public int getCommentNumByUser(int userId){
        return commentDAO.getCommentCountByUser(userId);
    }

    public List<Comment> getCommentByEntity(int entityType,int entityId){
        return commentDAO.getCommentByEntity(entityType, entityId);
    }

}
