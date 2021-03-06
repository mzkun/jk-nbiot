package com.goldcard.nbiot.web.home.controller.sys;

import com.goldcard.nbiot.common.model.User;
import com.goldcard.nbiot.common.util.PageBase;
import com.goldcard.nbiot.manager.sys.service.UserService;
import com.goldcard.nbiot.web.home.common.ReturnBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/page")
    public String userList(HttpServletRequest request){
        return "sys/user";
    }

    @RequestMapping("/userList")
    @ResponseBody
    public PageBase<User> getUserList(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        int row = Integer.parseInt(request.getParameter("rows"));// 接受参数page和rows
        Map<String, Object> map = new HashMap<String, Object>();
        return userService.getUserList(map, page, row);
    }

    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        return "sys/userAdd";
    }

    @RequestMapping("/userAdd")
    @ResponseBody
    public String userAdd(User user, HttpServletRequest request) {
        try {
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "新增失败！！";
        }
        return "用户新增成功！！";
    }

    @RequestMapping("/enable")
    @ResponseBody
    public ReturnBody enable(String id) {
        ReturnBody returnBody = new ReturnBody();
        User user = new User();
        user.setId(id);
        try {
            userService.userEnable(user);
            returnBody.setMessage("启用成功！");
            returnBody.setResult(true);
            return returnBody;
        } catch (RuntimeException e) {
            e.printStackTrace();
            returnBody.setMessage(e.getMessage());
            return returnBody;
        }
    } 

    @RequestMapping("/unable")
    @ResponseBody
    public ReturnBody unable(String id) {
        ReturnBody returnBody = new ReturnBody();
        User user = new User();
        user.setId(id);
        try {
            userService.userUnable(user);
            returnBody.setMessage("禁用成功！");
            returnBody.setResult(true);
            return returnBody;
        } catch (RuntimeException e) {
            e.printStackTrace();
            returnBody.setMessage(e.getMessage());
            return returnBody;
        }
    }
}
