package com.goldcard.nbiot.web.home.controller.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goldcard.nbiot.common.dal.dataobject.sys.Tree;
import com.goldcard.nbiot.manager.sys.service.ResourcesService;
import com.goldcard.nbiot.web.home.tools.ShiroSessionTools;

@Controller
public class MainController { 

	@Autowired
	ResourcesService resourcesService;
	/**
     * 进入主页面
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
    	String userId = ShiroSessionTools.getUserInfo().getId();
    	List<Tree> menus = resourcesService.getResourcesList(userId);
    	request.setAttribute("menus", menus);
    	return "home";
    }
    
}
