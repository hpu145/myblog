package com.kaishengit.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaishengit.entity.Article;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Reply;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.ArticleService;
import com.kaishengit.vo.ArticleDetailVO;

@WebServlet("/user/article/detail")
public class ArticleDetailServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	ArticleService articleService = new ArticleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//从article_add.jsp页面获得id
		String id = req.getParameter("id");
		try {
			//1.根据id获得article对象
			Article article = articleService.findArticleById(id);
			if(article != null) {
				//2.获得所有nodeList列表
				List<Node> nodeList = articleService.findAllNode();
				//3.获得当前articleid对应的评论列表
				List<Reply> replyList = articleService.findAllReply(article.getId());
				//4.获得文章对应的nodename
				Node node = articleService.findNodeById(article.getNodeid());
				
				//ArticleDetailVO 进行封装
				ArticleDetailVO detailVO = new ArticleDetailVO();
				detailVO.setArticle(article);
				detailVO.setReplyList(replyList);
				detailVO.setNodeName(node.getName());
				
				req.setAttribute("nodeList", nodeList);
				req.setAttribute("articleVO", detailVO);//articleVO
				forward("user/article_detail", req, resp);
				
				
			} else {
				resp.sendError(404, "该文章不存在或已经被删除!");
			}
		} catch(ServiceException e) {
			resp.sendError(404, e.getMessage());
		}
		
	}
	
	
}
