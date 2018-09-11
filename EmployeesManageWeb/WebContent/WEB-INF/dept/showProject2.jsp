<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<link rel="stylesheet" type="text/css" href="css/icon.css">
<style type="text/css">
@font-face {
	font-family: 'Glyphicons Halflings';
	src: url('bootstrap/fonts/glyphicons-halflings-regular.eot');
	src: url('bootstrap/fonts/glyphicons-halflings-regular.eot?#iefix')
		format('embedded-opentype'),
		url('bootstrap/fonts/glyphicons-halflings-regular.woff')
		format('woff'),
		url('bootstrap/fonts/glyphicons-halflings-regular.ttf')
		format('truetype'),
		url('bootstrap/fonts/glyphicons-halflings-regular.svg#glyphicons_halflingsregular')
		format('svg');
}

.glyphicon {
	position: relative;
	top: 1px;
	display: inline-block;
	font-family: 'Glyphicons Halflings';
	-webkit-font-smoothing: antialiased;
	font-style: normal;
	font-weight: normal;
	line-height: 1;
	-moz-osx-font-smoothing: grayscale;
	font-size: 50px;
}

* {
	margin: 0;
	padding: 0;
}

#main {
	width: 1020px;
	margin: auto;
}

#project {
	width: 1020px;
	height: 220px;
	border: 2px solid #4B0082;
	overflow: auto;
}

.select {
	background-color: #337ab7;
	color: white;
}

#but {
	text-align: center;
}

span {
	margin: 20px 50px;
	cursor: pointer;
}

span:hover {
	color: #337ab7;
}

#otherProject {
	width: 1020px;
	height: 220px;
	border: 2px solid #4B0082;
	overflow: auto;
}

ul li {
	text-align: center;
	float: left;
	margin: 10px 10px;
}

.over {
	background: #FBEC88;
}
</style>
</head>
<body>
	<div id="main">
		<div class="page-header">
			<h2>${dept.name }</h2>
		</div>
		<div id="project">
			<ul class="list-group">
				<c:forEach items="${proList }" var="pro">
					<li data-id="${pro.id }" class="list-group-item up">${pro.name }</li>
				</c:forEach>
			</ul>
		</div>
		<div id="but">
			<span id="up" class="glyphicon glyphicon-arrow-up"> </span> <span id="down" class="glyphicon glyphicon-arrow-down"></span>
		</div>
		<div id="otherProject">
			<ul class="list-group">
				<c:forEach items="${otherProList }" var="pro">
					<li data-id="${pro.id }" class="list-group-item down">${pro.name }</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		$("#project li").click(function() {
			$(this).toggleClass("select");
		})
		$("#otherProject li").click(function() {
			$(this).toggleClass("select");
		})
		$("#up").click(function() {
			var ids = "";
			$("#otherProject .select").each(function() {
				var id = $(this).data("id");
				ids += id + ",";
			})
			ids = ids.substring(0, ids.length - 1);
			if (ids.length < 1) {
				alert("请先选中项目");
			} else {
				//location.href = ("dept?type=addPro3&depId=${dept.id }&proIds=" + ids);
				$.ajax({
					url : "dept",
					data : {
						type : "addPro3",
						depId : "${dept.id }",
						proIds : ids
					},
					dataType : "text",
					type : "post",
					success : function(data) {
						if (data == "true") {
							var pro = $("#otherProject .select");
							pro.toggleClass("select");
							$("#project ul").append(pro);
						}
					}
				})
			}
		})
		$("#down").click(function() {
			var ids = "";
			$("#project .select").each(function() {
				var id = $(this).data("id");
				ids += id + ",";
			})
			ids = ids.substring(0, ids.length - 1);
			if (ids.length < 1) {
				alert("请先选中项目");
			} else {
				//location.href = ("dept?type=deletePro3&depId=${dept.id }&proIds=" + ids);
				$.ajax({
					url : "dept",
					data : {
						type : "deletePro3",
						depId : "${dept.id }",
						proIds : ids
					},
					dataType : "text",
					type : "post",
					success : function(data) {
						if (data == "true") {
							var pro = $("#project .select");
							pro.toggleClass("select");
							$("#otherProject ul").append(pro);
						}
					}
				})
			}
		})
	</script>
</body>
</html>