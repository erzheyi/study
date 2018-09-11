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
	width: 1000px;
	margin: 20px auto;
}

#search button {
	float: right;
}

#main {
	width: 1000px;
	margin: 20px auto;
}

#score td, th {
	text-align: center;
}

#score .td1 {
	width: 60px;
	height: 20px;
}

#update {
	margin-left: 475px;
}
</style>
</head>
<body>
	<div class="page-header">
		<h2>绩效考核</h2>
	</div>
	<div id="search">
		<form action="score" method="post" role="form">
			<input type="hidden" name="type" value="search">
			<div class="form-group">
				<div class="col-sm-2">
					<input type="text" value="${name }" class="form-control" id="name" name="name" placeholder="请输入员工姓名">
				</div>
				<div class="col-sm-2">
					<select id="dept" name="dept" class="form-control">
						<option value="">部门</option>
						<c:forEach items="${depList }" var="dep">
							<option value="${dep.id }" <c:if test="${depId  == dep.id }">selected</c:if>>${dep.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<select id="project" name="project" class="form-control">
						<option value="">项目</option>
						<c:forEach items="${proList }" var="pro">
							<option value="${pro.id }" <c:if test="${proId  == pro.id }">selected</c:if>>${pro.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" value="${value == -1 ? '' : value }" class="form-control" id="value" name="value" placeholder="请输入成绩">
				</div>
				<div class="col-sm-2">
					<select id="grade" name="grade" class="form-control">
						<option value="">等级</option>
						<c:forEach items="${graList }" var="gra">
							<option <c:if test="${grade  == gra }">selected</c:if>>${gra }</option>
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
		<table id="score" class="table table-bordered table-striped table-hover">
			<tr>
				<th>id</th>
				<th>姓名</th>
				<th>部门</th>
				<th>项目</th>
				<th>成绩</th>
				<th>等级</th>
			</tr>
			<c:forEach items="${pageModel.list }" var="sco">
				<tr class="td" data-id="${sco.id }">
					<td>${sco.id }</td>
					<td data-id="${sco.employee.id }">${sco.employee.name }</td>
					<td>${sco.employee.dept.name }</td>
					<td data-id="${sco.project.id }">${sco.project.name }</td>
					<td>${sco.value }</td>
					<td>${sco.grade }</td>
				</tr>
			</c:forEach>
			<tr>
				<td align="center" colspan="6">
					<ul class="pagination pagination-sm">
						<li><a href="score?type=search&name=${name }&dept=${depId }&project=${proId }&value=${value }&grade=${grade }&page=1">首页</a></li>
						<li><a href="score?type=search&name=${name }&dept=${depId }&project=${proId }&value=${value }&grade=${grade }&page=${pageModel.prevPage() }">上一页</a></li>
						<c:forEach begin="${pageModel.getBeginPage() }" end="${pageModel.getEndPage() }" varStatus="status">
							<li <c:if test="${pageModel.currPage == status.index }">class="active"</c:if>><a href="score?type=search&name=${name }&dept=${depId }&project=${proId }&value=${value }&grade=${grade }&page=${status.index }">${status.index }</a></li>
						</c:forEach>
						<li><a href="score?type=search&name=${name }&dept=${depId }&project=${proId }&value=${value }&grade=${grade }&page=${pageModel.nextPage() }">下一页</a></li>
						<li><a href="score?type=search&name=${name }&dept=${depId }&project=${proId }&value=${value }&grade=${grade }&page=${pageModel.pages }">尾页</a></li>
					</ul>
				</td>
			</tr>
		</table>
		<button id="update" class="btn btn-primary">修改</button>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript">
		$(".td").dblclick(function() {
			$(this).unbind("dblclick");
			$(this).addClass("change");
			var value = $(this).children("td").eq(4).text();
			$(this).children("td").eq(4).html("<input type='text' name='score' class='td1' value='"+value+"'>");
		}) 
	</script>
	<script type="text/javascript">
		$("#update").click(function(){
			var score = "";
			$(".change").each(function(){
				var id = $(this).data("id");
				var value = $(this).find("input").val();
				var empId = $(this).children("td").eq(1).data("id");
				var proId = $(this).children("td").eq(3).data("id");
				score += id+","+value+","+empId+","+proId+";"; 
			})
			score = score.substring(0,score.length-1);
			location.href=("score?type=update&score="+score);
		})
	</script>
</body>
</html>