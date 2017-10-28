package com.kaishengit.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.ResultJson;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.ArticleService;


@WebServlet("/admin/article/add")
public class AdminArticleAddServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService articleService = new ArticleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取节点列表,GET请求需要携带节点列表
		List<Node> nodeList = articleService.findAllNode();
		req.setAttribute("nodeList", nodeList);
		forward("admin/article_add",req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String labelnames = req.getParameter("labelnames");
		String content = req.getParameter("content");
		String node = req.getParameter("node");
		ResultJson result = null;
		int id = articleService.addArticle(title,labelnames,content,node);
		try {
			result = new ResultJson(id);
			sendJson(result,resp);
		} catch (ServiceException e) {
			result = new ResultJson(e.getMessage());
			sendJson(result,resp);
		}
		
	}
	
}
