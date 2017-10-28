<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${articleVO.article.title}</title>
      <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
    <%@ include file="include/css.jsp"%>
    
    <style>
        body {
            margin-top: 60px;
        }
        
    </style>
</head>
<body>
  
   <%@ include file="include/navbar.jsp" %>
              <!-- 文章列表开始 -->

              <div class="container">
                    <div class="box">
                            <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
                                <li><a href="/user/article/list">首页</a></li>
                                <li class="active">${articleVO.nodeName}</li>
                            </ul>
                            
                            <div class="topic-head">
                                <h3 class="title">${articleVO.article.title}</h3>
                            </div>
                           
                            <div class="topic-body">
								${articleVO.article.content}
                            </div>
                            
                            <div class="topic-toolbar">
                                   
                                <ul class="list-inline text-muted">
                                    <li><i class="fa fa-eye"></i> ${articleVO. article.scannum}</li>
                                    <li><i class="fa fa-commenting"></i> ${articleVO.article.replynum}</li>
                                </ul> 
                            </div>
                        </div>
                        <!--box end-->
                
                        <div class="box" style="margin-top:20px;">
                        
                            <div class="talk-item muted" style="font-size: 12px">
                                ${articleVO.article.replynum}个回复 | 直到<span id="lastreplytime">
	                                <c:choose>
	                                	<c:when test="${articleVO.article.lastreplyTime == null}">
	                                		${articleVO.article.createTime}
	                                	</c:when>
	                                	<c:otherwise>
	                                		${articleVO.article.lastreplyTime}
	                                	</c:otherwise>
	                                </c:choose>
                                </span>
                                
                                
                            </div>
                            
                            <c:forEach items="${articleVO.replyList}" var="reply" varStatus="vs">
                            	
                            	<c:if test="${reply.pid == 0}">
                            	
                            		<div class="talk-item">
		                                <table class="talk-table">
		                                  
		                                    <tr>
		                                        <td width="auto">
		                                            <a href="" style="font-size: 12px">${reply.userip}</a> 
		                                            <span style="font-size: 12px" class="reply">${reply.createtime}</span>
		                                            <br>
		                                            <p style="font-size: 14px">${reply.content}</p>
		                                        </td>
		                                        <td width="70" align="right" style="font-size: 12px">
		                                            <a href="javascript:;" rel="${vs.count}" replyid="${reply.id}" class="replyLink" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
		                                            <span class="badge"></span>
		                                        </td>
		                                    </tr>
		                                    
		                               </table>
		                               <!-- 对评论的回复 -->
		                               <c:forEach items="${articleVO.replyList}" var="reReply">
		                               		 <c:if test="${reply.id == reReply.pid}">
		                               		 
		                               		     <div class="talk-item">
					                               <table class="talk-table" >
						                                    <tr>
						                                        <td width="auto">
						                                            <a href="" style="font-size: 12px">${reReply.userip}</a> 
						                                            <span style="font-size: 12px" class="reply">${reReply.createtime}</span>
						                                            <br>
						                                            <p style="font-size: 14px">${reReply.content}</p>
						                                        </td>
						                                        <td width="70" align="right" style="font-size: 12px">
						                                        </td>
						                                    </tr>
					                               	</table>
					                             </div>
		                               		 
		                               		 </c:if>
		                               </c:forEach>
		                               <!-- 对评论的回复 -->
		                             
                               		</div>
                            		
                            	</c:if>
                               
                            </c:forEach>
                            
                             
                            
                        </div>
                
                        <div class="box" style="margin:20px 0px;">
                            <!-- 锚标记 -->
                            <a name="reply"></a>
                            
                            <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                            <form action="/user/reply" id="replyForm" method="post" style="padding: 15px;margin-bottom:0px;">
                                
                                <input type="hidden" name="articleId" value="${articleVO.article.id }"/>
                                <input type="hidden" name="replyId" id="replyId" value=""/>
                                <textarea name="editor" id="editor"></textarea>
                            </form>
                            <div class="talk-item muted" style="text-align: right;font-size: 12px">
                                <span class="pull-left">请尽量让自己的回复能够对别人有帮助!</span>
                                <button id="replyBtn" class="btn btn-primary">发布</button>
                            </div>
                        </div>
              </div>
              
  <%@ include file="include/js.jsp" %>

<script src="/static/plugins/editer/scripts/module.min.js"></script>
<script src="/static/plugins/editer/scripts/hotkeys.min.js"></script>
<script src="/static/plugins/editer/scripts/uploader.min.js"></script>
<script src="/static/plugins/editer/scripts/simditor.min.js"></script>

<script>
    $(function(){
    	
        var editor = new Simditor({
                textarea: $('#editor')
            });
        
        var badges = $(".badge");
        for(var i = 0; i < badges.length; i++) {
        	$(badges[i]).html(i+1);
        }
        
        moment.locale("zh-cn");
        
        $("#lastreplytime").text(moment($("#lastreplytime").text()).format("YYYY年MM月DD日 HH:mm:ss"));
        
        $(".reply").text(function(){
        	var time = $(this).text();
        	return moment(time).fromNow();
        });
        
        $("#replyBtn").click(function(){
        	var content = editor.getValue();//$("#editor").val();也可以使用选择器获取值
        	if(content) {
        		$("#replyForm").submit();
        	} else {
        		layer.msg("请填写评论内容");
        	}
        	
        });
        
        $(".replyLink").click(function(){
        	var count = $(this).attr("rel");
        	var replyid = $(this).attr("replyid");
        	$("#replyId").val(replyid);
        	
        	window.location.href = "#reply";///锚标记
        	editor.setValue("回复第" + count + "楼：&nbsp;");
        	editor.focus();
        });
        
        
    });
</script>   
</body>
</html>