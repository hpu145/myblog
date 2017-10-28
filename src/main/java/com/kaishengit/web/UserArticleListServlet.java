package com.kaishengit.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.kaishengit.entity.Article;
import com.kaishengit.entity.Node;
import com.kaishengit.service.ArticleService;
import com.kaishengit.util.Page;

@WebServlet("/user/article/list")
public class UserArticleListServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService service = new ArticleService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nodeId = req.getParameter("nodeid");
		String labelId = req.getParameter("labelid");
		String keys = req.getParameter("keys");
		String p = req.getParameter("p");
		if(StringUtils.isNotEmpty(keys)) {
			keys = new String(keys.getBytes("ISO8859-1"),"utf-8");
		}
		// 1.获得page对象
		Page<Article> page = service.findArticleListByParams(nodeId, labelId, keys, p);
		// 2.获得所有的nodeList列表
		List<Node> nodeList = service.findAllNode();
		// 3.获得浏览排行榜
		List<Article> sortList = service.findScanSortList();
		req.setAttribute("page", page);
		req.setAttribute("nodeList", nodeList);
		req.setAttribute("sortList", sortList);
		req.setAttribute("keys", keys);
		forward("user/article_list", req, resp);
	}
	
}
