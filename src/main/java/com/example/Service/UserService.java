package com.example.Service;

import com.example.DAO.LoginTicketDAO;
import com.example.DAO.UserDAO;
import com.example.Model.LoginTicket;
import com.example.Model.User;
import com.example.util.ZixunUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Jassy on 2017/2/24.
 * description: 提供User需要用到的方法,如注册,登录,登出等；
 */
@Service
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public User selectByName(String name) {
        return userDAO.getUserByName(name);
    }

    public User selectById(int id) {
        return userDAO.getUserById(id);
    }

    /**
     * 传入用户id，在数据库表中创建一个ticket,并带有默认参数;
     *
     * @param userId
     * @return
     */
    private String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 24 * 3600 * 1000);
        loginTicket.setExpired(date);
        loginTicket.setValidStatus(true);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, false);
    }

    /**
     * 注册新用户
     *
     * @param username
     * @param password
     */
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (username == null) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (password.length() < 6 || password.length() > 17) {
            map.put("msg", "密码长度在6到17位之间");
            return map;
        }
        if (selectByName(username) != null) {
            map.put("msg", "用户名已被注册");
            return map;
        }

        User newUser = new User();
        newUser.setUsername(username);
        //设定默认头像;
        newUser.setHeadUrl("");
        //加盐;
        newUser.setSalt(UUID.randomUUID().toString().substring(0, 5));
        //加密,防止数据被截获时密码泄露;
        newUser.setPassword(ZixunUtil.MD5(password + newUser.getSalt()));
        userDAO.addUser(newUser);

        //得到加入数据库中的user的id,创建ticket,此时状态是登录;
        String ticket = addLoginTicket(newUser.getId());
        map.put("ticket", ticket);
        map.put("userId", newUser.getId());
        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (username == null) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (password.length() < 6 || password.length() > 17) {
            map.put("msg", "密码长度在6到17位之间");
            return map;
        }
        User user = selectByName(username);
        if (user == null) {
            map.put("msg", "用户不存在");
            return map;
        }
        if (!ZixunUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }

        //验证完成;
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("userId", user.getId());
        return map;
    }
}
