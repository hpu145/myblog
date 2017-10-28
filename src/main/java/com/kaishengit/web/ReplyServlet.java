package com.kaishengit.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.ArticleService;

@WebServlet("/user/reply")
public class ReplyServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService service = new ArticleService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String articleId = req.getParameter("articleId");
		String replyId = req.getParameter("replyId");//回复的回复
		String content = req.getParameter("editor");
		String userip = req.getRemoteAddr();
		try{
			service.addReply(articleId,replyId,content,userip);
			resp.sendRedirect("/user/article/detail?id=" + articleId);
		} catch(ServiceException e) {
			resp.sendError(404,e.getMessage());
		}
	}
	
	
}
