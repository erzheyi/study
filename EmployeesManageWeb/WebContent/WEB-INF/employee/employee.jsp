<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

#search {
	width: 650px;
	margin: 20px auto;
}

#main {
	width: 600px;
	margin: 20px auto;
}

#employee .select {
	background-color: #337ab7;
	color: white;
}

#employee td, th {
	text-align: center;
}

#employee .td1 {
	width: 100px;
	height: 20px;
}

#employee .td2 {
	width: 40px;
	height: 20px;
}

#employee .td3 {
	width: 60px;
	height: 20px;
}

#employee .td4 {
	width: 80px;
	height: 20px;
}

#employee .img {
	width: 30px;
	height: 30px;
	margin: auto;
}

#employee img {
	width: 30px;
	height: 30px;
	border-radius: 50%;
}

#employee #show {
	position: absolute;
	width: 100px;
	height: 100px;
	border-radius: 50%;
	z-index: 1;
}
</style>
</head>
<body>
	<div class="page-header">
		<h2>员工信息表</h2>
	</div>
	<div id="search">
		<form action="employee?type=search" method="post" role="form">
			<div class="form-group">
				<div class="col-sm-3">
					<input type="text" value="${name }" class="form-control" id="name" name="name" placeholder="姓名">
				</div>
				<div class="col-sm-2">
					<select id="sex" name="sex" class="form-control">
						<option value="">性别</option>
						<option value="male" <c:if test="${sex eq 'male' }">selected</c:if>>男</option>
						<option value="female" <c:if test="${sex eq 'female' }">selected</c:if>>女</option>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" value="${age == -1 ? '' : age }" class="form-control" id="age" name="age" placeholder="年龄">
				</div>
				<div class="col-sm-3">
					<select id="dept" name="dept" class="form-control">
						<option value="">部门</option>
						<c:forEach items="${deptList }" var="dept">
							<option value="${dept.id }" <c:if test="${deptId  == dept.id }">selected</c:if>>${dept.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>
		</form>
	</div>
	<div id="main">
		<table id="employee" class="table table-bordered table-striped table-hover">
			<tr>
				<th>头像</th>
				<th>id</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
				<th>部门</th>
			</tr>
			<c:forEach items="${pageModel.list }" var="emp">
				<tr class="tr" data-id="${emp.id }">
					<td><c:if test="${not empty emp.avatar }">
							<div class="img">
								<img alt="头像" src="${emp.avatar }">
							</div>
						</c:if></td>
					<td>${emp.id }</td>
					<td>${emp.name }</td>
					<td>${emp.sex }</td>
					<td>${emp.age }</td>
					<td>${emp.dept.name }</td>
				</tr>
			</c:forEach>
			<tr>
				<td align="center" colspan="6">
					<ul class="pagination pagination-sm">
						<li><a href="employee?type=search&name=${name }&sex=${sex }&age=${age == -1 ? '' : age }&dept=${deptId }&page=1">首页</a></li>
						<li><a href="employee?type=search&name=${name }&sex=${sex }&age=${age == -1 ? '' : age }&dept=${deptId }&page=${pageModel.prevPage() }">上一页</a></li>
						<c:forEach begin="${pageModel.getBeginPage() }" end="${pageModel.getEndPage() }" varStatus="status">
							<li <c:if test="${pageModel.currPage == status.index }">class="active"</c:if>><a
								href="employee?type=search&name=${name }&sex=${sex }&age=${age == -1 ? '' : age }&dept=${deptId }&page=${status.index }">${status.index }</a></li>
						</c:forEach>
						<li><a href="employee?type=search&name=${name }&sex=${sex }&age=${age == -1 ? '' : age }&dept=${deptId }&page=${pageModel.nextPage() }">下一页</a></li>
						<li><a href="employee?type=search&name=${name }&sex=${sex }&age=${age == -1 ? '' : age }&dept=${deptId }&page=${pageModel.pages }">尾页</a></li>
					</ul>
				</td>
			</tr>
		</table>
		<button id="showAdd" class="btn btn-primary">增加员工</button>
		<button id="showUpdate" class="btn btn-primary">修改员工</button>
		<button id="delete" class="btn btn-primary">删除员工</button>
		<button id="updateBatch" class="btn btn-primary">批量修改</button>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript">
		var selectId = new Array();
		function getSelect() {
			$("#employee .select").each(function(index) {
				var id = $(this).data("id");
				selectId.push(id);
			})
		}
		$("#showAdd").click(function() {
			location.href = ("employee?type=showAdd");
		})
		$("#showUpdate").click(function() {	
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中员工！");
			} else { 
				location.href = ("employee?type=showUpdate&ids="+ selectId + "&name=${name }&sex=${sex }&age=${age == -1 ? '' : age }&dept=${deptId }&page=" + ${pageModel.currPage } );
			}
		})   
		$("#delete").click(function() { 
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中员工！");
			} else {
				location.href = ("employee?type=delete&ids=" + selectId+ "&name=${name }&sex=${sex }&age=${age == -1 ? '' : age }&dept=${deptId }&page=" + ${pageModel.currPage } );
			}
		})
		$("#updateBatch").click(function() {
			var empList = new Array();
			$("#employee .change").each(function(index, element) {
				var id = $(this).data("id");
				var name = $(this).find("input[name=name]").val();
				var sex = $(this).find("select[name=sex]").val();
				var age = $(this).find("input[name=age]").val();
				var dept = $(this).find("select[name=dept]").val();
				var emp = {
							id : id,
							name : name,
							sex : sex,
							age : age,
							dept: {id:dept}
							}
				empList.push(emp);
			})
			var empJson = JSON.stringify(empList);
			empJson = empJson.replace(/{/g, "%7b");
			empJson = empJson.replace(/}/g, "%7d");
			location.href = ("employee?type=updateBatch&empJson="+ empJson + "&name=${name }&sex=${sex }&age=${age == -1 ? '' : age }&dept=${deptId }&page=" + ${pageModel.currPage } );
		})
	</script>
	<script type="text/javascript">
		var timer = null;
		$(".tr").click(function() {
			var temp = $(this);
			clearTimeout(timer);
			timer = setTimeout(function() {
				if (!temp.hasClass("change")) {
					temp.toggleClass("select");
				}
			}, 200);
		})
		$(".tr").dblclick(function() {
			clearTimeout(timer);
			$(this).unbind("dblclick");
			$(this).unbind("click");
			if ($(this).hasClass("select")) {
				$(this).removeClass("select");
			}
			$(this).addClass("change");
			var name = $(this).children("td").eq(1).text();
			var sex = $(this).children("td").eq(2).text();
			var age = $(this).children("td").eq(3).text();
			var dept = $(this).children("td").eq(4).text();
			$(this).children().eq(1).html("<input type='text' name='name' class='td1' value='"+name+"'>");
			if (sex == "男") {
				$(this).children().eq(2).html("<select name='sex' class='td2'><option selected='selected' value='male'>男</option><option value='female'>女</option></select>");
			} else {
				$(this).children().eq(2).html("<select name='sex' class='td2'><option value='male'>男</option><option selected='selected' value='female'>女</option></select>");
			}
			$(this).children().eq(3).html("<input type='text' name='age' class='td3' value='"+age+"'>");
			var deptOption = "";
			var deptStr = "${deptStr }";
			var str = deptStr.split(";");
			for (var i = 0; i < str.length; i++) {
				var s = str[i].split(",");
				var id = s[0];
				var name = s[1];
				if (name == dept) {
					deptOption += "<option value='"+id+"' selected>" + name + "</option>"
				} else {
					deptOption += "<option value='"+id+"'>"+ name + "</option>"
				}
			}
			$(this).children().eq(4).html("<select name='dept' class='td4'><option value='0'>-</option>" + deptOption + "</select>");
		})  
	</script>
	<script type="text/javascript">
		$("#employee .img").hover(function(event){
			var left = event.pageX - 50;
			var top = event.pageY - 50;
			$(this).append("<img src='"+$(this).children().prop("src")+"' id='show' style='left:"+left+"px;top:"+top+"px'></img>");
		},function(){
			$("#show").remove();
		})
	</script>
</body>
</html>