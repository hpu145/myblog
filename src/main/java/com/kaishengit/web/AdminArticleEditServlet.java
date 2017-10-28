package com.kaishengit.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaishengit.entity.Article;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.ResultJson;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.ArticleService;

@WebServlet("/admin/article/edit")
public class AdminArticleEditServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService service = new ArticleService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		//根据id获得要修改的article对象
		Article article = service.findArticleById(id);
		//获得所有节点列表
		List<Node> nodeList = service.findAllNode();
		req.setAttribute("article", article);
		req.setAttribute("nodeList", nodeList);
		forward("admin/article_edit",req,resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String title = req.getParameter("title");
		String labelNames = req.getParameter("labelnames");
		String content = req.getParameter("content");
		String node = req.getParameter("nodeid");
		ResultJson result = null;
		
		try{
			service.editArticle(id,title,labelNames,content,node);
			result = new ResultJson(Integer.parseInt(id));
			sendJson(result,resp);
			
		} catch(ServiceException e) {
			result = new ResultJson(e.getMessage());
			sendJson(result,resp);
		}
	}
	
	
	
	
}
