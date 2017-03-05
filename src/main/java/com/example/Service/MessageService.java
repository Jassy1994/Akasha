package com.example.Service;

import com.example.DAO.MessageDAO;
import com.example.Model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jassy on 2017/3/1.
 * description:
 */
@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    /**
     *
     * @param message
     * @return
     */
    public int addMessage(Message message){
        //添加敏感词过滤代码;
        return messageDAO.addMessage(message)>0?message.getId():0;
    }

    /**
     * 得到会话的细节信息，选择从offset开始的limit条私信;
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationDetail(String conversationId,int offset,int limit){
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    /**
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationList(int userId,int offset,int limit){
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getUnreadMessageNum(int userId,String conversationId){
        return messageDAO.getUnreadMessageCount(userId, conversationId);
    }
}
