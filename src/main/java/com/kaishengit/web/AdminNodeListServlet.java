package com.kaishengit.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.kaishengit.entity.Node;
import com.kaishengit.service.ArticleService;
import com.kaishengit.util.Page;

@WebServlet("/admin/node/list")
public class AdminNodeListServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService service = new ArticleService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String p = req.getParameter("p");
		String keys = req.getParameter("keys");
		//解决查找框中关键字中文乱码问题
		if(StringUtils.isNotEmpty(keys)) {
			keys = new String(keys.getBytes("ISO8859-1"),"UTF-8");
		}
		
		Page<Node> page = service.findNodeListByParam(p,keys);
		req.setAttribute("page", page);
		forward("admin/node_list", req, resp);
		
	}
	
}
