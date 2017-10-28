<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- forEach内部格式化时间 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>文章列表</title>
      <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
    <%@ include file="include/css.jsp" %>
    
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
            <div class="row">
                <div class="col-md-9">
                    <c:forEach items="${page.items}" var="article">
                        <div class="article-span">
                                <div class="media article">
                                        
	                                <div class="media-body">
	                                    <a href="/user/article/detail?id=${article.id}"><span class="media-heading"> ${article.title }</span></a> <span class="time"><fmt:formatDate value="${article.createTime }" type="both" /></span>
	                                    <p class="">${article.simpleContent}</p>
	                                    <div class="meta">
		                                    <c:forEach items="${article.labelList }" var="label" varStatus="vs">
		                                    	<a href="/user/article/list?labelid=${label.id}"><span class="label label-info">${label.name}</span></a> 
		                                    </c:forEach>
	                                    </div> 
	                                </div>
	                            
	                                <div class="media-right">
	                                	<c:if test="${not empty article.picture}">
                                           	${article.picture}
                                        </c:if>
	                                </div>
	                                
                                </div>
                        </div>
                    </c:forEach>
                        
                        <div class="text-center">
                            <ul id="pagination" class="pagination pagination-lg"></ul>
                        </div>
                </div>  
                  
                <div class="col-md-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">浏览排行</h3>
                        </div>

                            <!-- List group -->
                            <ul class="list-group text-primary">
                               
                               <c:forEach items="${sortList}" var="sort" varStatus="vs">
                            	
	                                <li class="list-group-item">${vs.count} ${sort.title}<label style="margin-left:5px"
	                                
		                                <c:choose>
											<c:when test="${vs.count == 1}">
												class="label label-danger"
											</c:when>
											<c:when test="${vs.count == 2}">
												class="label label-primary"
											</c:when>
											<c:otherwise>
												class="label label-default"
											</c:otherwise>
										</c:choose>
	                                >${sort.scannum}</label></li>
	                                
                            	</c:forEach>
                                
                            </ul>
                    </div>
                </div>
            </div>
        </div>

	<%@ include file="include/js.jsp"%>
	     
    <!-- page -->
<script src="/static/dist/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function(){
      
    	 $("#pagination").twbsPagination({
             totalPages:"${page.pageTotal}",
             visiblePages:3,
             href:"/user/article/list?p={{number}}&nodeid=${param.nodeid}&labelid=${param.labelid}&keys=${requestScope.keys}", 
            		                            //&nodeid=&labelid=&keys= encodeURIComponent("${param.keys}"
             first: "首页",
             prev: "上一页",
             next:"下一页",
             last:"末页"
           });  
        
    });
</script>   
</body>
</html>