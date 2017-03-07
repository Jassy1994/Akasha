package com.example.Controller;

import com.example.Model.HostHolder;
import com.example.Model.Message;
import com.example.Model.User;
import com.example.Model.ViewObject;
import com.example.Service.MessageService;
import com.example.Service.UserService;
import com.example.async.EventProducer;
import com.example.util.ZixunUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jassy on 2017/3/7.
 * description:
 */
@Controller
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {
        if (hostHolder.getUser() == null) {
            return "redirect/reglogin";
        }
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Message message : conversationList) {
            ViewObject vo = new ViewObject();
            vo.set("message", message);
            int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            vo.set("user", userService.selectById(targetId));
            vo.set("unread", messageService.getUnreadMessageNum(localUserId, message.getConversationId()));
            vos.add(vo);
        }
        model.addAttribute("conversations", vos);
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = RequestMethod.GET)
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> vos = new ArrayList<>();
            for (Message message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", message.getFromId());
                vos.add(vo);
            }
            model.addAttribute("messages", vos);
        } catch (Exception e) {
            logger.error("拉取私信详情失败! " + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"msg/addMessage"}, method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return ZixunUtil.getJSONString(999, "未登录");
            }

            User user = userService.selectByName(toName);
            if (user == null) {
                return ZixunUtil.getJSONString(1, "用户不存在");
            }

            Message message = new Message();
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setSendDate(new Date());
            message.setContent(content);
            messageService.addMessage(message);
            return ZixunUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("发送私信失败! " + e.getMessage());
            return ZixunUtil.getJSONString(1, "发送私信失败! ");
        }
    }

}
