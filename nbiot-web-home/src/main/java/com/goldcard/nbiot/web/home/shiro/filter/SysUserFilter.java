package com.goldcard.nbiot.web.home.shiro.filter;

import com.goldcard.nbiot.common.model.User;
import com.goldcard.nbiot.manager.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

public class SysUserFilter extends PathMatchingFilter {

    @Resource
    UserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        List<User> list = userService.selectListByUsername(username);
        request.setAttribute("user", list.get(0));
        return true;
    }
}