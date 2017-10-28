package com.kaishengit.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.dao.NotifyDao;
import com.kaishengit.entity.Admin;
import com.kaishengit.entity.Notify;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.Page;

public class AdminService {
	
	NotifyDao notifyDao = new NotifyDao();
	
	public Admin login(String username,String password) {
		AdminDao adminDao = new AdminDao();
		Admin admin = adminDao.findAdminByUsername(username);
		String salt = Config.get("user.password.salt");
		String encodePassword = DigestUtils.md5Hex(salt + password);
		if(admin != null && encodePassword.equals(admin.getPassword())) {
			//1.记录登录日志  2.更新登录时间
			return admin; 
		} else {
			throw new ServiceException("用户名或密码错误");
		}
	}

	
	public Page<Notify> findNotifyList(String p) {
		if(StringUtils.isNumeric(p)) {
			int pageNo = Integer.parseInt(p);
			int count = notifyDao.count();
			Page<Notify> page = new Page<>(pageNo,count);
			List<Notify> notifyList = notifyDao.findByPage(page.getStart(),page.getPageSize());
			
			page.setItems(notifyList);
			return page;
		} else {
			throw new ServiceException("参数异常");
		}
	}


	/**
	 * 查询所有消息列表
	 * @return
	 */
	public List<Notify> findAllNotify() {
		return notifyDao.findAll();
	}


	/**
	 * 根据ids更新消息状态，将消息标记为已读
	 * @param ids
	 */
	public void readNotifyByIds(String ids) {
		String[] idArray = ids.split(",");
		for(String id : idArray) {
			//更新对应消息
			Notify notify = notifyDao.findById(Integer.parseInt(id));
			//更新对象消息的状态
			notify.setState(Notify.NOTIFY_READ); //魔法数字  1
			notify.setReadTime(new Timestamp(System.currentTimeMillis()));
			notifyDao.update(notify);
		}
	}
	
	
	
	
}
