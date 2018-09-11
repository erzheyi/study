<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="jquery-ui-1.12.1.custom/jquery-ui.min.css">
<style type="text/css">
h2 {
	text-align: center;
}

#search {
	width: 600px;
	margin: 20px auto;
}

#search button {
	float: right;
}

#main {
	width: 600px;
	margin: 20px auto;
}

#dept .select {
	background-color: #337ab7;
	color: white;
}

#dept td, th {
	text-align: center;
}

#dept .td1 {
	width: 60px;
	height: 20px;
}

button {
	margin-top: 5px;
}

#modal-body {
	overflow: auto;
}
</style>
</head>
<body>
	<div class="page-header">
		<h2>部门信息表</h2>
	</div>
	<div id="search">
		<form action="dept" method="post" role="form">
			<input type="hidden" name="type" value="search">
			<div class="form-group">
				<div class="col-sm-5">
					<input type="text" value="${name }" class="form-control" id="name" name="name" placeholder="请输入部门名称" AUTOCOMPLETE="OFF">
				</div>
				<div class="col-sm-5">
					<input type="text" value="${num == -1 ? '' : num }" class="form-control" id="num" name="num" placeholder="请输入员工数目">
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>
		</form>
	</div>
	<div id="main">
		<table id="dept" class="table table-bordered table-striped table-hover">
			<tr>
				<th>id</th>
				<th>名称</th>
				<th>员工数目</th>
			</tr>
			<c:forEach items="${pageModel.list }" var="dep">
				<tr class="tr" data-id="${dep.id }">
					<td>${dep.id }</td>
					<td>${dep.name }</td>
					<td>${dep.empNum }<a href="employee?type=search&dept=${dep.id }">&nbsp;&nbsp;> </a></td>
				</tr>
			</c:forEach>
			<tr>
				<td align="center" colspan="5">
					<ul class="pagination pagination-sm">
						<li><a href="dept?type=search&name=${name }&num=${num == -1 ? '' : num }&page=1">首页</a></li>
						<li><a href="dept?type=search&name=${name }&num=${num == -1 ? '' : num }&page=${pageModel.prevPage() }">上一页</a></li>
						<c:forEach begin="${pageModel.getBeginPage() }" end="${pageModel.getEndPage() }" varStatus="status">
							<li <c:if test="${pageModel.currPage == status.index }">class="active"</c:if>><a href="dept?type=search&name=${name }&num=${num == -1 ? '' : num }&page=${status.index }">${status.index }</a></li>
						</c:forEach>
						<li><a href="dept?type=search&name=${name }&num=${num == -1 ? '' : num }&page=${pageModel.nextPage() }">下一页</a></li>
						<li><a href="dept?type=search&name=${name }&num=${num == -1 ? '' : num }&page=${pageModel.pages }">尾页</a></li>
					</ul>
				</td>
			</tr>
		</table>
		<div class="modal fade" id="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">部门项目管理</h4>
					</div>
					<div class="modal-body" id="modal-body" ></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<button id="showAdd" class="btn btn-primary">增加部门</button>
		<button id="showUpdate" class="btn btn-primary">修改部门</button>
		<button id="delete" class="btn btn-primary">删除部门</button>
		<button id="updateBatch" class="btn btn-primary">批量修改</button>
		<button id="showProject" class="btn btn-primary">项目管理</button>
		<button id="showProject2" class="btn btn-primary">项目管理2</button>
		<button id="showProject3" class="btn btn-primary">项目管理3</button>
		<button id="showProject4" class="btn btn-primary">项目管理4</button>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	<script type="text/javascript">
		var selectId = new Array();
		function getSelect() {
			$("#dept .select").each(function() {
				var id = $(this).data("id");
				selectId.push(id);
			})
		}
		$("#showAdd").click(function() {
			location.href = ("dept?type=showAdd");
		})
		$("#showUpdate").click(function() {	
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中部门！");
			} else {  
				location.href = ("dept?type=showUpdate&name=${name }&num=${num == -1 ? '' : num }&ids="+ selectId + "&page=" + ${pageModel.currPage } );
			}
		})    
		$("#delete").click(function() { 
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中部门！");
			} else {
				location.href = ("dept?type=delete&name=${name }&num=${num == -1 ? '' : num }&ids=" + selectId+ "&page=" + ${pageModel.currPage } );
			}
		})
		$("#updateBatch").click(function() {
			var depList = new Array();
			$("#dept .change").each(function(index, element) {
				var id = $(this).data("id");
				var name = $(this).find("input[name=name]").val();
				var dep = {
							id : id,
							name : name
							}
				depList.push(dep);
			})
			var depJson = JSON.stringify(depList);
			depJson = depJson.replace(/{/g, "%7b");
			depJson = depJson.replace(/}/g, "%7d");
			location.href = ("dept?type=updateBatch&name=${name }&num=${num == -1 ? '' : num }&depJson="+ depJson + "&page=" + ${pageModel.currPage } );
		}) 
		$("#showProject").click(function(){
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中部门！");
			} else if(selectId.length > 1) {
				selectId.splice(0,selectId.length);
				alert("只能选择一个部门！");
			} else {
				location.href = ("dept?type=showProject&depName=${name }&depNum=${num }&depPage=${pageModel.currPage }&id=" + selectId);
			}
		})
		$("#showProject2").click(function(){
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中部门！");
			} else if(selectId.length > 1) {
				selectId.splice(0,selectId.length);
				alert("只能选择一个部门！");
			} else {
				location.href = ("dept?type=showProject2&depName=${name }&depNum=${num }&depPage=${pageModel.currPage }&id=" + selectId);
			}
		})
		$("#showProject3").click(function(){
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中部门！");
			} else if(selectId.length > 1) {
				selectId.splice(0,selectId.length);
				alert("只能选择一个部门！");
			} else {
				location.href = ("dept?type=showProject3&depName=${name }&depNum=${num }&depPage=${pageModel.currPage }&id=" + selectId);
			}
		})
		$("#showProject4").click(function(){
			getSelect();
			if (selectId.length < 1) {
				alert("请先选中部门！");
			} else if(selectId.length > 1) {
				selectId.splice(0,selectId.length);
				alert("只能选择一个部门！");
			} else {
				var id = selectId[0];
				$("#modal-body").load("dept",{
					type:"showProject4",
					id:id
				});
				$("#dialog").modal("show");
				selectId.splice(0,selectId.length);
			}			
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
</body>
</html>