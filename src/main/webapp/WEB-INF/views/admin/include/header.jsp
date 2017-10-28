<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="main-header">
        <!-- Logo -->
        <a href="#" class="logo">
          <span class="logo-mini">博客</span>
          <span class="logo-lg">我的博客</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
           
          </a>
    
          <div class="navbar-custom-menu">
              <ul class="nav navbar-nav">
                 
                 <!-- User Account: style can be found in dropdown.less -->
                 <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle">
                      <img src="/static/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                      <span class="hidden-xs">Alexander Pierce</span>
                    </a>
                  </li>
                  
                  <!-- Notifications: style can be found in dropdown.less -->
                  <li class="dropdown user-menu">
                    <a href="/user/article/list" class="dropdown-toggle">
                      <span class="hidden-xs"><i></i> 返回客户端</span>
                    </a>
                  </li>
                  
                  <li class="dropdown notifications-menu">
                    <a href="/notify" class="dropdown-toggle">
                      <i class="fa fa-bell-o"></i>
                      <span class="label label-danger" id="unreadcount"></span> 消息通知
                    </a>
                  </li>
                 
                  <li class="dropdown user user-menu">
                      <a href="/logout" class="dropdown-toggle">
                        <span class="hidden-xs"><i class="fa fa-sign-in"></i> 退出系统</span>
                      </a>
                 </li>
                    
                </ul>
              </div>
            </nav>
          </div>
        </nav>
      </header>
    
      <!-- =============================================== -->