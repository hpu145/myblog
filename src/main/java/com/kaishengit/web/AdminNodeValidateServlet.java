package com.kaishengit.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaishengit.service.ArticleService;

@WebServlet("/admin/node/validate")
public class AdminNodeValidateServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService service = new ArticleService(); 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nodename = req.getParameter("addnodename");
		String nodeName = new String(nodename.getBytes("ISO8859-1"),"utf-8");//解决get请求中文乱码
		String res = service.validateNodeName(nodeName);
		//System.out.println(res);
		sendText(res,resp);
	}

}
