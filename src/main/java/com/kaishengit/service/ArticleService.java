package com.kaishengit.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.kaishengit.dao.ArticleDao;
import com.kaishengit.dao.ArticleLabelDao;
import com.kaishengit.dao.LabelDao;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.NotifyDao;
import com.kaishengit.dao.ReplyDao;
import com.kaishengit.entity.Article;
import com.kaishengit.entity.Label;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.Reply;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Page;

public class ArticleService {

	
	NodeDao nodeDao = new NodeDao();
	ArticleDao articleDao = new ArticleDao();
	LabelDao labelDao = new LabelDao();
	ArticleLabelDao articleLabelDao = new ArticleLabelDao();
	ReplyDao replyDao = new ReplyDao();
	NotifyDao notifyDao = new NotifyDao();
	/**
	 * 查询所有节点列表
	 * @return 节点列表
	 */
	
	public List<Node> findAllNode() {
		return nodeDao.findAll();
	}
	
	/**
	 * 新增文章
	 * @param title
	 * @param labelnames
	 * @param content
	 * @param node
	 * @return
	 */
	/**
	 * @param title
	 * @param labelnames
	 * @param content
	 * @param nodeId
	 * @return
	 */
	public int addArticle(String title, String labelnames, String content, String nodeId) {
		if(StringUtils.isNumeric(nodeId)) {
			//1.封装article对象
			Article article = new Article();
			article.setTitle(title);
			article.setContent(content);
			article.setNodeid(Integer.parseInt(nodeId));
			
			//使用jsoup获得simplecontent与第一个picture
			Document doc = Jsoup.parseBodyFragment(content);
			String simpleContent = doc.select("p").first().toString();
			//System.out.println(simpleContent);
			Element picEle = doc.select("img").first();
			if(picEle != null) {
				picEle.attr("width","100px");
				picEle.attr("height","100px");
				String picture = picEle.toString();
				article.setPicture(picture);
			}
			
			article.setSimpleContent(simpleContent);
			
			//2.article对象存入数据库
			int articleId = articleDao.add(article);
			
			//3.处理文章标签labelnames
			//创建article与labelnames的关联关系之前，判断labelnames是否存在，如果不存在先创建标签
			String[] names = labelnames.split(",");
			List<Integer> idList = new ArrayList<>();
			for(String name : names) {
				Label label = labelDao.findLabelByName(name);
				if(label != null) {//标签label如果已经存在 直接添加关联关系
					idList.add(label.getId());
					
				} else {//标签label不存在，先创建label，再添加关联关系
					label = new Label();
					label.setName(name);
					int labelId = labelDao.add(label);
					idList.add(labelId);
				}
			}
			
			articleLabelDao.patchAdd(articleId, idList);
			return articleId;
		} else {
			throw new ServiceException("参数异常");
		}
	}

	public Article findArticleById(String id) {
		if(StringUtils.isNumeric(id)) {
			Article article = articleDao.findById(Integer.parseInt(id));
			if(article != null) {
				//增加一次浏览量
				article.setScannum(article.getScannum() + 1);
				articleDao.update(article);
				//获得该文章对应的label列表
				List<Label> labelList = labelDao.findLabelByArticleId(article.getId());
				article.setLabelList(labelList);
				return article;
			} else {
				throw new ServiceException("对不起，该文章不存在或已经被删除!");
			}	
		} else {
			throw new ServiceException("参数异常");
		}
	}

	/**根据articleid查询所有回复
	 * @param articleid
	 * @return
	 */
	public List<Reply> findAllReply(int articleid) {
		return replyDao.findAllByArticleId(articleid);
	}

	/**
	 * 根据id查询对应的节点
	 * @param nodeid
	 * @return
	 */
	public Node findNodeById(int nodeid) {
		return nodeDao.findById(nodeid);
	}

	/**
	 * 添加文章的评论
	 * @param articleId
	 * @param content
	 * @param userip
	 * 
	 * @param replyId 对应pid
	 */
	public void addReply(String articleId, String replyId,String content, String userip) {
		if(StringUtils.isNumeric(articleId)) {
			Article article = articleDao.findById(Integer.parseInt(articleId));
			if(article != null) {
				Reply reply = new Reply();
				reply.setArticleId(article.getId());
				reply.setContent(content);
				
				if(StringUtils.isNotEmpty(replyId)) {
					reply.setPid(Integer.parseInt(replyId));
				}
				if("0:0:0:0:0:0:0:1".equals(userip)) {
					userip = "127.0.0.1";
				}
				reply.setUserip(userip);
				replyDao.add(reply);
				// 更新 对应article的回复数量和最后回复时间
				article.setReplynum(article.getReplynum() + 1);
				// new Date().getTime() 获得当前时间时间戳
				article.setLastreplyTime(new Timestamp(System.currentTimeMillis()));
				articleDao.update(article);
				
				//增加消息，通知管理员收到新回复
				Notify notify = new Notify();
				notify.setContent("您发布的文章<a href=\"/user/article/detail?id=" + articleId + "\">《"+ article.getTitle() +"》</a>有了新的回复,请点击查看!");
				notifyDao.add(notify);
				
			} else {
				throw new ServiceException("对不起，该文章不存在或已经被删除!");
			}
			
		} else {
			throw new ServiceException("参数异常");
		}	
	}

	/**
	 * 根据参数查询对应的文章列表
	 * @param nodeId
	 * @param labelId
	 * @param keys
	 * @param p
	 * @return
	 */
	public Page<Article> findArticleListByParams(String nodeId, String labelId, String keys, String p) {
		int pageNo = StringUtils.isNumeric(p) ? Integer.parseInt(p) : 1;
		
		Map<String,String> params = new HashMap<>();
		params.put("nodeId", nodeId);
		params.put("labelId", labelId);
		params.put("keys",keys);
		int count = articleDao.count(params);
		Page<Article> pages = new Page<>(pageNo, count);
 		
		params.put("start", String.valueOf(pages.getStart()));
		params.put("pageSize", String.valueOf(pages.getPageSize()));
		
		List<Article> articleList = articleDao.findAllByParams(params);
		for(Article art : articleList) {
			// 获得该文章对应的label列表
			List<Label> labelList = labelDao.findLabelByArticleId(art.getId());
			art.setLabelList(labelList);	
			// 获得该文章对应的节点对象
			Node node = nodeDao.findById(art.getNodeid());
			art.setNode(node);
		}
		pages.setItems(articleList);
		return pages;
	}


	
	/**
	 * 查询浏览排行
	 * @return
	 */
	public List<Article> findScanSortList() {
		return articleDao.findScanSortList();
	}

	/**
	 * 根据id删除对应的文章
	 * @param id
	 */
	public void delArticleById(String id) {
		if(StringUtils.isNumeric(id)){
			//判断该文章是否有评论，有就直接删除
			int articleId = Integer.parseInt(id);
			List<Reply> replyList = replyDao.findAllByArticleId(articleId);
			if(replyList.size() > 0) {
				replyDao.delByArticleId(articleId);
			}
			articleDao.delById(articleId);
			
		} else {
			throw new ServiceException("参数异常");
		}
		
	}

	/**
	 * 修改：更新文章
	 * @param id
	 * @param title
	 * @param labelNames
	 * @param content
	 * @param node
	 */
	public void editArticle(String id, String title, String labelNames, String content, String nodeId) {
		if(StringUtils.isNumeric(id) && StringUtils.isNumeric(nodeId)) {
			Article article = new Article();
			article.setId(Integer.parseInt(id));
			article.setTitle(title);
			article.setContent(content);
			article.setNodeid(Integer.parseInt(nodeId));
			//更新article
			articleDao.update(article);
			//处理labelNames  删除之前的关联关系
			articleLabelDao.delByAid(article.getId());
			//创建article与label的关联关系之前，验证标签是否存在
			
			String[] names = labelNames.split(",");
			List<Integer> idList = new ArrayList<>();
			for(String name : names) {
				Label label = labelDao.findLabelByName(name);
				if(label != null) {//label存在
					idList.add(label.getId());
				} else{//label不存在
					label = new Label();
					label.setName(name);
					int labelId = labelDao.add(label);
					idList.add(labelId);
				}
				
			}
			//创建关联关系
			articleLabelDao.patchAdd(article.getId(), idList);
			
		} else {
			throw new ServiceException("参数异常");
		}
		
	}

	public Page<Node> findNodeListByParam(String p, String keys) {
		int pageNo = StringUtils.isNumeric(p) ? Integer.parseInt(p) : 1;
		int count = nodeDao.countByParam(keys);
		Page<Node> pages = new Page<>(pageNo, count);
		
		// 根据参数获得对应的node集合
		List<Node> nodeList = nodeDao.findListByParam(keys,pages.getStart(), pages.getPageSize());
		pages.setItems(nodeList);
		
		return pages;
	}

	
	/**
	 * 校验新增的节点是否可以使用，true表示可用，false不可用
	 * @param nodeName
	 * @return
	 */
	public String validateNodeName(String nodeName) {
		Node node = nodeDao.findByName(nodeName);
		if(node == null) {
			return "true";
		} 
		return "false";
	}

	/**
	 * 添加节点
	 * @param nodeName
	 */
	public void addNode(String nodeName) {
		Node node = new Node();
		node.setName(nodeName);
		nodeDao.add(node);
	}

	/**
	 * 删除节点
	 * @param id
	 */
	public void delNodeById(String id) {
		if(StringUtils.isNumeric(id)){
			//判断该节点下是否有文章，有文章不能被删除
			int nodeId = Integer.parseInt(id);
			List<Article> articleList = articleDao.findAllByNodeId(nodeId);
			if(articleList != null && articleList.size() > 0) {
				throw new ServiceException("该节点下有文章，不能被删除!");
			}
			nodeDao.del(nodeId);
 		} else {
 			throw new ServiceException("参数异常");
 		}
	}
	
	/**
	 * 修改节点
	 * @param nodeName
	 * @param id
	 */
	public void updateNode(String nodeName ,String id) {
		if(StringUtils.isNumeric(id)) {
			Node node = nodeDao.findById(Integer.parseInt(id));
			if(node == null) {
				throw new ServiceException("该节点不存在");
			} else {
				node.setName(nodeName);
				nodeDao.update(node);
			}
		} else {
			throw new ServiceException("参数异常");
		}
	}

	public Page<Reply> findReplyListByParams(String keys, String p) {
		int pageNo = StringUtils.isNumeric(p) ? Integer.parseInt(p) : 1;
		int count = replyDao.countByParam(keys);
		Page<Reply> pages = new Page<>(pageNo, count);
		
		// 根据参数获得对应的reply集合
		List<Reply> replyList = replyDao.findListByParam(keys,pages.getStart(), pages.getPageSize());
		pages.setItems(replyList);
		
		return pages;
		
		
	}

	/**
	 * 根据reply的id删除评论
	 * @param id
	 */
	public void delReplyById(String id) {
		if(StringUtils.isNumeric(id)){
			
			int replyId = Integer.parseInt(id);
			Reply reply = replyDao.findReplyById(id);
			if(reply != null) {
				replyDao.delReplyById(replyId);
				Article article = articleDao.findArticleById(reply.getArticleId());
				article.setReplynum(article.getReplynum() - 1);
				articleDao.addReplyNum(article);
			}
			
		} else {
			throw new ServiceException("参数异常");
		}
	}
	
	
}
