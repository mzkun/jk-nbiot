package com.goldcard.nbiot.web.home.controller.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goldcard.nbiot.common.model.LoginUserInfo;

@Controller
public class UserLoginController {

	@RequestMapping("/userLogin")
	public void userLogin(HttpServletRequest request,HttpServletResponse response){
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		//String imageCode = request.getParameter("imageCode");
		HttpSession session = request.getSession();
		request.setAttribute("userName", userName);
		request.setAttribute("password", password);
		//request.setAttribute("imageCode", imageCode);
		try {
			if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
				request.setAttribute("error", "用户名或密码为空！");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			
			if (!("root".equals(userName) && "123456".equals(password))){
				request.setAttribute("error", "用户名或密码错误！");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			/*if (StringUtils.isEmpty(imageCode)) {
				request.setAttribute("error", "验证码为空！");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}*/
			/*if (!imageCode.equals(session.getAttribute("sRand"))) {
				request.setAttribute("error", "验证码错误！");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}*/
			session.setAttribute("userInfo", buildLoginUserInfo(userName, password, null));//取消验证码
			response.sendRedirect("home.jsp");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param imageCode
	 * @return
	 */
	private LoginUserInfo buildLoginUserInfo(String userName,String password,String imageCode){
        LoginUserInfo userInfo = new LoginUserInfo();
        userInfo.setUserName(userName);
        userInfo.setPassword(password);
        userInfo.setImageCode(imageCode);
	    return userInfo;
	}

}
