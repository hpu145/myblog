package com.kaishengit.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.kaishengit.entity.Admin;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;

@WebServlet("/login")
public class LoginServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("username".equals(cookie.getName())) {
					req.setAttribute("username", cookie.getValue());
				}
			}
			
		}
		
		forward("admin/login",req,resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		
		//根据用户名和密码进行登录，如果登录成功获得对应的admin对象
		AdminService service = new  AdminService();
		Map<String,Object> map = new HashMap<>();
		
		try{
			Admin admin = service.login(username, password);
			//admin存入session
			HttpSession session = req.getSession();
			session.setAttribute("curr_admin", admin);
			//如果remember不为空，将username存入cookie
			if(StringUtils.isNotEmpty(remember)) {
				Cookie cookie = new Cookie("username",username);
				cookie.setDomain("localhost");
				cookie.setPath("/");
				cookie.setMaxAge(30 * 24 * 60 * 60);//30天
				cookie.setHttpOnly(true);
				resp.addCookie(cookie);
			} else {
				//remember为空即是用户不需记住用户名，将username的cookie时间设置为0
				Cookie[] cookies = req.getCookies();
				if(cookies != null) {
					
					for(Cookie cookie : cookies) {
						if("username".equals(cookie.getName())){
							cookie.setDomain("localhost");
							cookie.setPath("/");
							cookie.setMaxAge(0);
							cookie.setHttpOnly(true);
							resp.addCookie(cookie);
						}
					}
				}
				
			}
			
			map.put("state", "success");
			
		} catch(ServiceException e) {
			map.put("state", "error");
			map.put("message", e.getMessage());
		}
		//发送json到页面
		sendJson(map,resp);
		
	}
	
}
