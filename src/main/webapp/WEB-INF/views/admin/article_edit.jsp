<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>修改：${article.title}</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 
  <%@ include file="include/css.jsp"%>
  
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

  <%@ include file="include/header.jsp"%>
    
  <%@ include file="include/siderbar.jsp"%>     

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
      <!-- Default box -->     
      <div class="box box-primary">
          <div class="container">
          
          
            <form action="/admin/article/edit" method="post" class="form-horizontal" id="editForm">
              <br>
              
               <div class="form-group">
                  <input type="text" class="form-control" name="title" value="${article.title}" placeholder="请输入主题标题">
               </div>
               <div class="form-group">
               
               	  <input type="hidden" name="id" value="${article.id}">
                  <input type="text" class="form-control" name="labelnames" 
                  value="<c:forEach items="${article.labelList}" var="label" varStatus="vs">${label.name}<c:if test="${not vs.last}">,</c:if></c:forEach>" placeholder="请为主题贴上标签，多个标签使用，号分开">
               </div>
               <div class="form-group"> 
               	  <!-- 隐藏修改文章的id -->
                  <input type="hidden" name="id" value="${article.id}">
                  <textarea class="from-control" name="content" id="editor"  placeholder="正文从这里开始...">${article.content}</textarea>
               </div>
              
               <div class="form-group">
                 <div class="col-sm-6" style="padding:0px">
                  <select class="form-control" name="nodeid" id="nodeid" style="margin-top:15px">
                      <option value="">请选择节点</option>
                     <!-- 从后端get请求获取节点列表并显示在前端页面 -->
                      <c:forEach items="${nodeList}" var="node">
                      		<option value="${node.id}" <c:if test="${node.id == article.nodeid}">selected</c:if> >${node.name}</option>
                      </c:forEach>
                      
                  </select>
                </div>
               </div>
               <br>
               <div class="form-group">
                  <button id="editBtn" class="btn btn-primary">更新文章</button>
               </div>
            </form>

          </div>
  
      </div>
      <!-- /.box -->
    
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 2.3.8
    </div>
    <strong>Copyright &copy; 2014-2016 <a href="http://www.kaishengit.com/" target="_blank">凯盛软件</a>.</strong> All rights
    reserved.
  </footer>
</div>
<!-- ./wrapper -->

  <%@ include file="include/js.jsp"%>

<!-- 富文本编辑器 -->
<script src="/static/plugins/editer/scripts/module.min.js"></script>
<script src="/static/plugins/editer/scripts/hotkeys.min.js"></script>
<script src="/static/plugins/editer/scripts/uploader.min.js"></script>
<script src="/static/plugins/editer/scripts/simditor.min.js"></script>
</body>
<script>
  $(function(){
    var editor = new Simditor({
    	textarea: $("#editor")
    });
    
    $("#editBtn").click(function(){
    	$("#editForm").submit();
    });
	
    $.validator.addMethod("labelValidate", function(value, element) {
  	  return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5]+(,[a-zA-Z0-9\u4e00-\u9fa5]+)*$/.test(value);
  	}, "please notice your doc");
    
    $("#editForm").validate({
    	errorClass : "text-danger",
    	errorElement : "span",
    	rules : {
    		title : {
    			required : true
    		},
    		labelnames : {
    			required : true,
    			labelValidate : true
    		},
    		content : {
    			required : true
    		},
    		node : {
    			required : true
    		}
    	},
    	messages : {
    		title : {
    			required : "请填写文章标题"
    		},
    		labelnames : {
    			required : "请填写文章标签",
    			labelValidate : "格式不正确,请用英文,隔开"
    		},
    		content : {
    			required : "请填写文章内容"
    		},
    		node : {
    			required : "请选择文章节点"
    		}
    	},
    	
    	submitHandler : function(form) {
    		$.ajax({
    			url : "/admin/article/edit",
    			type : "post",
    			data : $(form).serialize(),
    			beforeSend : function(){
    				$("#editBtn").text("文章更新中...").attr("disabled","disabled");
    			},
    			success : function(json) {
    				if(json.state == "success") {
    					window.location.href = "/user/article/detail?id="+json.data;
    				} else {
    					layer.msg(json.message)
    				}
    			},
    			error : function(){
    				layer.alert("系统异常，请稍后再试")
    			},
    			complete : function(){
    				$("#editBtn").text("更新文章").removeAttr("disabled");
    			}
    			
    			
    		});
    	}
    	
    	
    });
    
    
  });


</script>
</html>
    
    