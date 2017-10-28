<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>消息通知</title>
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
       	<div class="box">
        	<div class="box-header with-border">
            	<div class="box-header">
					<button id="markBtn" style="margin-left: 8px;" disabled class="btn btn-success">标记为已读</button>
				</div>
       		</div>

        	<div class="box-body">
          
          		<table class="table">
		            <thead>
		                <tr>
		                    <th width="30"><input type="checkbox" id="ckFather"></th>
		                    <th width="200">发布日期</th>
		                    <th>内容</th>
		                </tr>
		            </thead>
		            
            		<tbody>
						<c:forEach items="${page.items}" var="notify">
						
							<c:choose>
								<c:when test="${notify.state == 0}">
									<tr style="font-weight:800;">
		                                <td><input value="${notify.id}" type="checkbox" class="ckSon"></td>
		                                <td><fmt:formatDate value="${notify.createTime}" type="both" /></td>
		                                <td>${notify.content}</td>
		                            </tr>
								</c:when>
								<c:otherwise>
									<tr>
		                                <td></td>
		                                <td><fmt:formatDate value="${notify.createTime}" type="both" /></td>
		                                <td>${notify.content}</td>
		                            </tr>
								</c:otherwise>
							</c:choose>
							
						</c:forEach>                            
            		</tbody>
        		</table>
          		<br> 
          		<ul id="pagination" class="pagination pull-right"></ul>
            </div>
        <!-- /.box-body -->
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

<!-- page -->
<script src="/static/dist/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function(){
      
        $("#pagination").twbsPagination({
         totalPages:"${page.pageTotal}",
         visiblePages:3,
         href:"/notify?p={{number}}",
         first: "首页",
         prev: "上一页",
         next:"下一页",
         last:"末页"
       });  
      	
        
        $("#ckFather").click(function(){
        	
        	var sons = $(".ckSon");
        	
        	for(var i = 0;i < sons.length;i++) {
        		sons[i].checked = $(this)[0].checked; //$(this).is(:"checked")
        	}
        	
        	//对“标记为已读”按钮设置
        	if($(this)[0].checked == true) {
        		$("#markBtn").removeAttr("disabled");
        	} else {
        		$("#markBtn").attr("disabled","disabled");
        	}
        });
        
        if($(".ckSon").length == 0){
        	$("#ckFather").attr("disabled","disabled");
        }
        
        $(".ckSon").click(function(){
        	var sons = $(".ckSon");
        	var num = 0;
        	for(var i=0;i<sons.length;i++) {
        		if(sons[i].checked == true) {
        			num++;
        		}
        	}
        	
        	if(num == sons.length) {
        		$("#ckFather")[0].checked = true;
        	} else {
        		$("#ckFather")[0].checked = false;
        	}
        	
        	if(num > 0) {
				$("#markBtn").removeAttr("disabled");
			} else {
				$("#markBtn").attr("disabled","disabled");
			}
        });
        
        
        $("#markBtn").click(function(){
        	var ids = [];
        	var sons = $(".ckSon");
        	for(var i=0;i<sons.length;i++) {
        		if(sons[i].checked == true) {
        			ids.push(sons[i].value);
        		}
        	}
        	//alert(ids);
        	$.post("/notify/read",{"ids":ids.join(",")},function(json){
        		if(json == "success") {
        			window.history.go(0);
        		}
        	});
        	
        	
        });
        
    });
    
</script>
</body>
</html>
