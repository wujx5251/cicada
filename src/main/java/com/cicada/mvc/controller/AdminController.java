package com.cicada.mvc.controller;

import com.cicada.storage.bean.UserInfo;
import com.cicada.storage.dao.LdapDao;
import com.cicada.storage.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jinxiao.wu
 * Date: 1/18/18
 * Time: 15:21
 */
@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private static final String result = "{\"status\":%s,\"message\":\"%s\"}";

    @Autowired
    private LdapDao ldapDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/doLogin")
    @ResponseBody
    public String login(UserInfo user, HttpServletRequest req) {
        try {
            user = ldapDao.getLoginInfo(user.getLoginId(), user.getPassword());
            if (null != user) {
                user.setCreateTime(System.currentTimeMillis());
                user.setLoginTime(System.currentTimeMillis());
                if (userDao.update(user) < 1) {
                    userDao.save(user);
                }
                req.getSession().setAttribute("user", user);
                req.getSession().setAttribute("uname", user.getEmail());
                return String.format(result, 1, "登录成功！");
            }
        } catch (Exception e) {
            logger.error("登陆异常", e);
        }
        return String.format(result, 0, "用户不存在或账号密码错误！");
    }


    @RequestMapping("/doLogout")
    @ResponseBody
    public void logout(HttpServletRequest req) {
        req.getSession().invalidate();
    }


}
