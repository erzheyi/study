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

#main {
	width: 500px;
	margin: 20px auto;
}

#project .select {
	background-color: #337ab7;
	color: white;
}

#project td, th {
	text-align: center;
}

#project .td1 {
	width: 120px;
	height: 20px;
}

.otherPro {
	width: 150px;
	height: 32px;
}
</style>
</head>
<body>
	<div class="page-header">
		<h2>${dept.name }</h2>
	</div>
	<div id="main">
		<table id="project" class="table table-bordered table-striped table-hover">
			<tr>
				<th>id</th>
				<th>项目名称</th>
			</tr>
			<c:forEach items="${pageModel.list }" var="pro">
				<tr class="tr" data-id="${pro.id }">
					<td>${pro.id }</td>
					<td>${pro.name }</td>
				</tr>
			</c:forEach>
			<tr>
				<td align="center" colspan="5">
					<ul class="pagination pagination-sm">
						<li><a href="dept?type=showProject&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&id=${dept.id }&page=1">首页</a></li>
						<li><a href="dept?type=showProject&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&id=${dept.id }&page=${pageModel.prevPage() }">上一页</a></li>
						<c:forEach begin="${pageModel.getBeginPage() }" end="${pageModel.getEndPage() }" varStatus="status">
							<li <c:if test="${pageModel.currPage == status.index }">class="active"</c:if>><a
								href="dept?type=showProject&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&id=${dept.id }&page=${status.index }">${status.index }</a></li>
						</c:forEach>
						<li><a href="dept?type=showProject&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&id=${dept.id }&page=${pageModel.nextPage() }">下一页</a></li>
						<li><a href="dept?type=showProject&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&id=${dept.id }&page=${pageModel.pages }">尾页</a></li>
					</ul>
				</td>
			</tr>
		</table>
		<select id="otherPro" class="otherPro">
			<c:forEach items="${otherProList }" var="pro">
				<option value="${pro.id }">${pro.name }</option>
			</c:forEach>
		</select>
		<button id="add" class="btn btn-primary">增加项目</button>
		<button id="delete" class="btn btn-primary">删除项目</button>
		<button id="add2" class="btn btn-primary">增加项目2</button>
		<button id="delete2" class="btn btn-primary">删除项目2</button>
		<button id="return" class="btn btn-primary">返回</button>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript">
		var selectId = new Array();
		function getSelect() {
			$("#project .select").each(function() {
				var id = $(this).data("id");
				selectId.push(id);
			})
		}
		$("#add")
				.click(
						function() {
							var id = $("#otherPro").val();
							if (id == null) {
								alert("没有项目可以添加!");
							} else {
								location.href = ("dept?type=addPro&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&depId=${dept.id }&proId=" + id);
							}
						})
		$("#delete")
				.click(
						function() {
							getSelect();
							if (selectId.length < 1) {
								alert("请先选中员工！");
							} else {
								location.href = ("dept?type=deletePro&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&page=${pageModel.currPage }&depId=${dept.id }&ids=" + selectId);
							}
						})
		$("#add2").click(function() {
			var id = $("#otherPro").val();
			if (id == null) {
				alert("没有项目可以添加!");
			} else {
				//								location.href = ("dept?type=addPro&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&depId=${dept.id }&proId=" + id);
				$.ajax({
					url : "dept",
					data : {
						type : "addPro2",
						depId : "${dept.id }",
						proId : id
					},
					dataType : "json",
					type : "post",
					success : function(data) {
						alert(data);
					}
				})
			}
		})
		$("#delete2")
				.click(
						function() {
							getSelect();
							if (selectId.length < 1) {
								alert("请先选中员工！");
							} else {
								location.href = ("dept?type=deletePro&depName=${param.depName }&depNum=${param.depNum }&depPage=${param.depPage }&page=${pageModel.currPage }&depId=${dept.id }&ids=" + selectId);
							}
						})
		$("#return")
				.click(
						function() {
							location.href = ("dept?type=search&name=${param.depName }&num=${param.depNum }&page=${param.depPage }");
						})
	</script>
	<script type="text/javascript">
		var timer = null;
		$(".tr").click(function() {
			var temp = $(this);
			clearTimeout(timer);
			timer = setTimeout(function() {
				temp.toggleClass("select");
			}, 200);
		})
	</script>
	<script type="text/javascript">
		
	</script>
</body>
</html>