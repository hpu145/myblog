package com.kaishengit.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaishengit.entity.ResultJson;
import com.kaishengit.exception.DataAccessException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.ArticleService;

@WebServlet("/admin/node/del")
public class AdminNodeDelServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService service = new ArticleService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		ResultJson result = null; 
		try {
			service.delNodeById(id);
			result = new ResultJson();
			result.setState("success");
		} catch(ServiceException e) {
			result = new ResultJson(e.getMessage());
			
		} catch(DataAccessException e) {
			result = new ResultJson(e.getMessage());
		}
		sendJson(result,resp);
		
	}
	
	
}
