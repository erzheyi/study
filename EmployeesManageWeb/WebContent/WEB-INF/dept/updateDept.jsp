<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<style type="text/css">
#main {
	width: 1200px;
	margin: auto;
}

.dept {
	width: 500px;
	float: left;
	margin: 25px 50px 25px 50px;
	padding: 10px 10px 10px 0;
	border: 1px solid #ccc;
}

#btn {
	clear: both;
	height: 50px;
	text-align: center;
}
</style>
</head>
<body>
	<div id="main">
		<c:forEach items="${depList }" var="dep" varStatus="status">
			<form action="dept" method="post" class="form-horizontal dept" role="form">
				<input type="hidden" name="id" value=${dep.id }>
				<div class="form-group">
					<label for="name${status.index }" class="col-sm-2 control-label">部门名称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="name${status.index }" name="name" placeholder="请输入部门名称" value=${dep.name }>
					</div>
				</div>
			</form>
		</c:forEach>
		<div id="btn">
			<button type="button" id="update" class="btn btn-primary">修改</button>
			<button type="button" id="return" class="btn btn-primary">返回</button>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript">
		$("#update").click(function() {
			var deps = "";
			$(".dept").each(function(index) {
				var id = $(this).find("input[name=id]").val();
 				var name = $(this).find("input[name=name]").val();
				deps += id + "," + name + ";";
			})
			deps = deps.substring(0, deps.length - 1);
			location.href = ("dept?type=updateDep&name=${param.name }&num=${param.num == -1 ? '' : param.num }&deps=" + deps + "&page=" + ${param.page });
		})
		$("#return").click(function() { 
			location.href = ("dept?type=search&name=${param.name }&num=${param.num == -1 ? '' : param.num }&page=" + ${param.page });
		})
	</script>
</body>
</html>