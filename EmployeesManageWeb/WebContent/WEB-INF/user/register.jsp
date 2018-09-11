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

#register {
	width: 600px;
	margin: auto;
}
</style>
</head>
<body>
	<div class="page-header">
		<h2>注册</h2>
	</div>
	<div id="register">
		<form action="user" method="post" class="form-horizontal" role="form">
			<input type="hidden" name="type" value="doRegister">
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">用户名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name" placeholder="请输入用户名">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">密码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
				</div>
			</div>
			<div class="form-group">
				<label for="repassword" class="col-sm-2 control-label">确认密码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="repassword" name="repassword" placeholder="确认密码">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">注册</button>
					<button type="button" class="btn btn-primary">返回</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>