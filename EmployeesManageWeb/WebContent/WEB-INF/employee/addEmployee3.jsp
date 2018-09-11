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
	width: 600px;
	margin: 20px auto;
}

#avatars img {
	width: 100px;
	height: 100px;
}
</style>
</head>
<body>
	<div id="main">
		<form action="employee" method="post" class="form-horizontal" role="form">
			<input type="hidden" name="type" value="addEmp3">
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <input type="radio" name="sex" value="male" checked> 男
					</label> <label class="radio-inline"> <input type="radio" name="sex" value="female"> 女
					</label>
				</div>
			</div>
			<div class="form-group">
				<label for="age" class="col-sm-2 control-label">年龄</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="age" name="age" placeholder="请输入年龄">
				</div>
			</div>
			<div class="form-group">
				<label for="dept" class="col-sm-2 control-label">部门</label>
				<div class="col-sm-10">
					<select id="dept" name="dept" class="form-control">
						<option value="0">-</option>
						<c:forEach items="${deptList }" var="dept">
							<option value=${dept.id }>${dept.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="avatar" class="col-sm-2 control-label">员工头像</label>
				<div class="col-sm-5">
					<input type="file" accept="image/*" class="form-control" id="avatar" name="avatar" multiple>
				</div>
				<div class="col-sm-2 col-sm-offset-1 ">
					<button type="button" id="upload" class="btn btn-primary">上传</button>
				</div>
			</div>
			<div class="form-group" id="avatars"></div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">增加</button>
					<button type="button" id="return" class="btn btn-primary">返回</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript">
		$("#return").click(function() {
			location.href = ("employee?type=show");
		})
		$("#upload").click(function() {
			var formData = new FormData();
			for (var i = 0; i < $("[name=avatar]")[0].files.length; i++) {
				formData.append("avatar", $("[name=avatar]")[0].files[i]);
			}
			$.ajax({
				url : "employee?type=upload",
				type : "post",
				data : formData,
				cache : false,
				processData : false,
				contentType : false,
				dataType : "text",
				success : function(data) {
					var str = "<img src='pic/"+data+"'>";
					str += "<input type='hidden' name='avatarName' value='"+data+"'>";
					$("#avatars").append(str);
				}
			})
		})
		$(document).on("click", "#avatars img", function() {
			var avatarSrc = $(this).prop("src");
			var img = $(this);
			$.ajax({
				url : "employee",
				type : "post",
				data : {
					type : "delete2",
					avatarSrc : avatarSrc
				},
				dataType : "text",
				success : function(data) {
					img.next().remove();
					img.remove();
				}
			})
		})
	</script>
</body>
</html>