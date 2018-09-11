<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<style type="text/css">
#main {
	width: 400px;
	margin: 20px auto;
}
</style>
</head>
<body>
	<div id="main">
		<form action="project" method="post" class="form-horizontal"
			role="form">
			<input type="hidden" name="type" value="addPro">
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label">项目名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="name"
						name="name" placeholder="请输入项目名称">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-12">
					<button type="submit" class="btn btn-primary">增加</button>
					<button type="button" id="return" class="btn btn-primary">返回</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript">
		$("#return").click(function() {
			location.href = ("project?type=search");
		})
	</script>
</body>
</html>