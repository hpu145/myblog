package com.kaishengit.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.ResultJson;
import com.kaishengit.service.AdminService;
import com.kaishengit.util.Page;

@WebServlet("/notify")
public class NotifyServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	AdminService service = new AdminService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String p = req.getParameter("p");
		p = StringUtils.isEmpty(p) ? "1" : p;
		
		Page<Notify> page = service.findNotifyList(p);
		req.setAttribute("page", page);
		forward("admin/notify",req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Notify> notifyList = service.findAllNotify();
		
		List<Notify> unreadNotifyList = Lists.newArrayList(Collections2.filter(notifyList, new Predicate<Notify>() {
			@Override
			public boolean apply(Notify notify) {
				return notify.getState() == 0;
			}
		}));
		ResultJson json = new ResultJson(unreadNotifyList.size());
		sendJson(json, resp);
	}

}
