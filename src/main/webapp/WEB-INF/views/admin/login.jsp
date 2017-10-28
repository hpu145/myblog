<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>登录</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="/static/dist/css/font-awesome.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

  <style type="text/css">
		body{
			background-image:url(/static/dist/img/blog.png);
			padding-top: 120px;
		}
	
	</style>
</head>
<body class="col-sm-offset-3 col-sm-6 col-lg-offset-4 col-lg-4 ">

  <div style="background-color: #fff;">
    <!-- Main content -->
    <section class="content">
      <div class="" style="border-bottom: 1px solid #f0f0f0">
        <div class="box-header">
        <h3 class="text-center">博客登录</h3>
        </div>
      </div>
    
       <div class="box-solid">
        <div class="box-body">
            <!-- /.box-header -->
            <!-- form start -->
            <form action="/admin/login" class="form-horizontal" method="post" id="loginForm">
              <div class="box-body">
                <div class="alert alert-error" id="message" hidden><button class="close" data-dismiss="alert">×</button>对不起,用户名或密码错误</div>
                <fieldset>
                  <div class="form-group">
                     	<label class="col-sm-1 control-label"><i class="fa fa-user"></i></label>
					    <div class="col-sm-11">
					      <input type="text" class="form-control" id="username" name="username" value="${username}" placeholder="登录帐号">
					    </div>
                  </div>
                  <div class="form-group">
                      	<label class="col-sm-1 control-label"><i class="fa fa-lock"></i></label>
					    <div class="col-sm-11">
					      <input type="password" class="form-control" id="password" name="password" placeholder="登录密码">
					    </div>
                  </div>
                </fieldset>
                <br>  
                <div id="remember-me" class="form-group">
					<div class="col-sm-offset-1 col-sm-11">
						<input type="checkbox" name="remember" <c:if test="${not empty username}">checked</c:if> id="remember" style="margin-right:4px;">
						<label>记住用户名</label>
					</div>
                </div>
            </div>
                <!-- /.box-body -->
            <br>
        </form>
        
            <div>
              <button type="button" id="loginBtn" class="btn btn-info  btn-lg btn-block">
                	进入系统
              </button>
            </div>
        </div>
        </div>
    </section>
  </div>

<!-- jQuery 2.2.3 -->
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/dist/js/jquery.validate.min.js"></script>
<script>
	$(function(){
		
		$("#loginBtn").click(function(){
			$("#loginForm").submit();
		});
		
		//从LoginFilter 获得url中的callback
		var callback = "${param.callback}";
		
		$("#loginForm").validate({
			errorClass : "text-danger",
			errorElement : "span",
			rules : {
				username : {
					required : true
				},
				password : {
					required : true
				}
			},
			messages : {
				username : {
					required : "请输入用户名"
				},
				password : {
					required : "请输入密码"
				}
			},
			submitHandler : function(form) {//写在validate里面
				$.ajax({
					url : "/login",
					type : "post",
					data : $(form).serialize(),
					
					beforeSend : function(){
						$("#loginBtn").text("正在登录中...").attr("disabled","disabled");
					},
					
					success : function(data){
						if(data.state == "success") {
							if(callback) {
								window.location.href = callback;
							} else {
								window.location.href = "/admin/article/list";
							}
							
						} else {
							$("#message").show();
						}
					},
					
					error : function(){
						alert("服务器异常，请稍候再试");
					},
					
					complete : function(){
						$("#loginBtn").text("进入系统").removeAttr("disabled");
					}
					
				});
			}
			
		});
		
	});

</script>
</body>
</html>
