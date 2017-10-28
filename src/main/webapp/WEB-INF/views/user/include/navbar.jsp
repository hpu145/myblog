<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
                <div class="container">
                  <!-- Brand and toggle get grouped for better mobile display -->
                  <div class="navbar-header">
                    <a class="navbar-brand" href="javascript:;"><i class="fa fa-weibo"></i></a>
                  </div>
              
                  <!-- Collect the nav links, forms, and other content for toggling -->
                  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                      <li><a href="/user/article/list">全部</a></li>
                      
                       <c:forEach items="${nodeList}" var="node">
          					<li><a href="/user/article/list?nodeid=${node.id}">${node.name}</a></li>
         			   </c:forEach>
                      
                    </ul>
                    
                    <!-- user页面搜索框 -->
                    <form action="/user/article/list" method="get" class="navbar-form navbar-right pull-right">
                      <div class="form-group">
                        <input type="text" class="form-control" name="keys" placeholder="Search">
                      </div>
                      <button class="btn btn-default"><i class="fa fa-search"></i></button>
                    </form>
                     <!-- user页面搜索框 -->
                    
                    <ul class="nav navbar-nav pull-right">
                      <li><a href="/admin/article/list">系统管理</a></li>
                    </ul>
                   
                  </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
              </nav>
              <!-- 导航栏结束 -->