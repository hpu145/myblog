package com.kaishengit.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kaishengit.entity.Admin;

public class LoginFilter extends AbstractFilter{

	List<String> urlList = null;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String url = filterConfig.getInitParameter("validateUrl");
		urlList = Arrays.asList(url.split(","));
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		//获取客户端请求uri
		String uri = req.getRequestURI();
		if(validateUri(uri)) {//如果输入uri合法，获取session并验证是否已经存在
			HttpSession session = req.getSession();
			Admin admin = (Admin)session.getAttribute("curr_admin");
			if(admin == null) {
				resp.sendRedirect("/login?callback=" + uri);
			} else {
				chain.doFilter(req, resp);
			}
		} else {
			chain.doFilter(req, resp);
		}
	}

	/**
	 * 对客户端的uri进行验证，返回true或false
	 * @param uri
	 * @return
	 */
	private boolean validateUri(String uri) {
		if(urlList == null || urlList.size() == 0) {
			return false;
		}
		for(String url : urlList) {
			if(uri.startsWith(url)) {
				return true;
			}
		}
		
		return false;
	}

}
