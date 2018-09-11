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
	width: 500px;
	margin: 20px auto;
}

#search button {
	float: right;
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
</style>
</head>
<body>
	<div class="page-header">
		<h2>项目信息表</h2>
	</div>
	<div id="search">
		<form action="project" method="post" role="form">
			<input type="hidden" name="type" value="search">
			<div class="form-group">
				<div class="col-sm-8">
					<input type="text" value="${name }" class="form-control" id="name" name="name" placeholder="请输入项目名称">
				</div>
				<div class="col-sm-4">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>
		</form>
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
						<li><a href="project?type=search&name=${name }&page=1">首页</a></li>
						<li><a href="project?type=search&name=${name }&page=${pageModel.prevPage() }">上一页</a></li>
						<c:forEach begin="${pageModel.getBeginPage() }" end="${pageModel.getEndPage() }" varStatus="status">
							<li <c:if test="${pageModel.currPage == status.index }">class="active"</c:if>><a href="project?type=search&name=${name }&page=${status.index }">${status.index }</a></li>
						</c:forEach>
						<li><a href="project?type=search&name=${name }&page=${pageModel.nextPage() }">下一页</a></li>
						<li><a href="project?type=search&name=${name }&page=${pageModel.pages }">尾页</a></li>
					</ul>
				</td>
			</tr>
		</table>
		<button id="showAdd" class="btn btn-primary">增加项目</button>
		<button id="showUpdate" class="btn btn-primary">修改项目</button>
		<button id="delete" class="btn btn-primary">删除项目</button>
		<button id="updateBatch" class="btn btn-primary">批量修改</button>
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
		$("#showAdd").click(function() {
			location.href = ("project?type=showAdd");
		})
		$("#showUpdate").click(function() {	
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中员工！");
			} else {  
				location.href = ("project?type=showUpdate&name=${name }&ids="+ selectId + "&page=" + ${pageModel.currPage } );
			}
		})   
		$("#delete").click(function() { 
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中员工！");
			} else {
				location.href = ("project?type=delete&name=${name }&ids=" + selectId+ "&page=" + ${pageModel.currPage } );
			}
		})
		$("#updateBatch").click(function() {
			var proList = new Array(); 
			$("#project .change").each(function(index, element) {
				var id = $(this).data("id");
				var name = $(this).find("input[name=name]").val();
				var pro = {
							id : id,
							name : name
							}
				proList.push(pro);
			})
			var proJson = JSON.stringify(proList);
			proJson = proJson.replace(/{/g, "%7b");
			proJson = proJson.replace(/}/g, "%7d");
			location.href = ("project?type=updateBatch&name=${name }&proJson="+ proJson + "&page=" + ${pageModel.currPage } );
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
		$(".tr").dblclick(function() {
			clearTimeout(timer);
			$(this).unbind("dblclick");
			$(this).unbind("click");
			if ($(this).hasClass("select")) {
				$(this).removeClass("select");
			}
			$(this).addClass("change");
			var name = $(this).children("td").eq(1).text();
			$(this).children().eq(1).html("<input type='text' name='name' class='td1' value='"+name+"'>");
		}) 
	</script>
	<script type="text/javascript">
		
	</script>
</body>
</html>