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

.employee {
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
		<c:forEach items="${empList }" var="emp" varStatus="status">
			<form action="employee" method="post" class="form-horizontal employee" role="form">
				<input type="hidden" name="id" value=${emp.id }>
				<div class="form-group">
					<label for="name${status.index }" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="name${status.index }" name="name" placeholder="请输入姓名" value=${emp.name }>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性别</label>
					<div class="col-sm-10">
						<label class="radio-inline"> <input type="radio" name="sex" value="male" <c:if test="${emp.sex eq '男' }">
						checked </c:if>> 男
						</label> <label class="radio-inline"> <input type="radio" name="sex" value="female" <c:if test="${emp.sex eq '女' }"> checked </c:if>> 女
						</label>
					</div>
				</div>
				<div class="form-group">
					<label for="age${status.index }" class="col-sm-2 control-label">年龄</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="age${status.index }" name="age" placeholder="请输入年龄" value=${emp.age }>
					</div>
				</div>
				<div class="form-group">
					<label for="dept" class="col-sm-2 control-label">部门</label>
					<div class="col-sm-10">
						<select id="dept" name="dept" class="form-control">
							<option value="0">-</option>
							<c:forEach items="${deptList }" var="dept">
								<option value=${dept.id } <c:if test="${emp.dept.id == dept.id }"> selected </c:if>>${dept.name }</option>
							</c:forEach>
						</select>
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
			var emps = "";
			$(".employee").each(function(index) {
				var id = $(this).find("input[name=id]").val();
 				var name = $(this).find("input[name=name]").val();
				var sex = $(this).find("input[name=sex]:checked").val();
				var age = $(this).find("input[name=age]").val();
				var dept = $(this).find("select[name=dept]").val();
				emps += id + "," + name + "," + sex+ "," + age + "," + dept + ";";
			})
			emps = emps.substring(0, emps.length - 1);
			location.href = ("employee?type=updateEmp&emps=" + emps + "&name=${param.name }&sex=${param.sex }&age=${param.age == -1 ? '' : param.age }&dept=${param.dept }&page=" + ${param.page });
		})
		$("#return").click(function() { 
			location.href = ("employee?type=search&name=${param.name }&sex=${param.sex }&age=${param.age == -1 ? '' : param.age }&dept=${param.dept }&page=" + ${param.page });
		})
	</script>
</body>
</html>