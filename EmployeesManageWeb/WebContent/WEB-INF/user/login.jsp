<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<style type="text/css">
h2 {
	text-align: center;
}

#login {
	width: 600px;
	margin: auto;
}
</style>
</head>
<body>
	<div class="page-header">
		<h2>登录</h2>
	</div>
	<div id="login">
		<form action="user" method="post" class="form-horizontal" role="form">
			<input type="hidden" name="type" value="doLogin">
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">用户名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name" value="${user }" placeholder="请输入用户名">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">密码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
				</div>
			</div>
			<div class="form-group">
				<label for="yzm" class="col-sm-2 control-label">验证码</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="yzm" name="yzm" placeholder="请输入验证码" autocomplete="off">
				</div>
				<div class="col-sm-2">
					<img alt="验证码" src="user?type=randomImage&i=1">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-8">
					<label>${message }</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">登录</button>
					<button type="button" class="btn btn-primary" id="register">注册</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript">
		$("#login img").click(function() {
			$(this).prop("src", "user?type=randomImage&i=" + Math.random());
		})
		$("#register").click(function() {
			location.href=("user?type=showRegister");
		})
		if(self!=top){
			top.location="user?type=showLogin";
		}
	</script>
</body>
</html>