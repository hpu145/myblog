package com.kaishengit.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaishengit.service.AdminService;

@WebServlet("/notify/read")
public class NotifyReadServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	AdminService service = new AdminService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ids = req.getParameter("ids");
		service.readNotifyByIds(ids);
		sendText("success", resp);
	}

}
