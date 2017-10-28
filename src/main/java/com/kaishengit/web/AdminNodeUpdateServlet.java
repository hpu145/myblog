package com.kaishengit.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.ArticleService;

@WebServlet("/admin/node/update")
public class AdminNodeUpdateServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService service = new ArticleService(); 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nodeName = req.getParameter("updateNodeName");
		String id = req.getParameter("id");
		try{
			service.updateNode(nodeName, id);
			resp.sendRedirect("/admin/node/list");
		} catch(ServiceException e) {
			resp.sendError(404, e.getMessage());
		}	
	}
	
}
