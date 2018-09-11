<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	font-size: 20px;
}

* {
	margin: 0;
	padding: 0;
}

#project {
	width: 820px;
	height: 110px;
	border: 2px solid #4B0082;
	overflow: auto;
}

#but {
	text-align: center;
}

span {
	margin: 10px 50px;
}

#otherProject {
	width: 820px;
	height: 110px;
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

<div class="page-header">
	<h3>${dept.name }</h3>
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
<script type="text/javascript">
	$("li").draggable({
		revert : "invalid",
		helper : "clone",
		cursor : "move"
	})
	$("#project").droppable({
		activeClass : "ui-state-default",
		hoverClass : "ui-state-hover",
		accept : ".down",
		drop : function(event, ui) {
			ui.draggable.removeClass("down");
			ui.draggable.addClass("up");
			$("#project ul").append(ui.draggable);
			var id = ui.draggable.data("id");
			$.ajax({
				url : "dept",
				data : {
					type : "addPro3",
					depId : "${dept.id }",
					proIds : id
				},
				dataType : "text",
				type : "post",
				success : function(data) {
					if (data == "true") {

					}
				}
			})
		}
	})
	$("#otherProject").droppable({
		activeClass : "ui-state-default",
		hoverClass : "ui-state-hover",
		accept : ".up",
		drop : function(event, ui) {
			ui.draggable.removeClass("up");
			ui.draggable.addClass("down");
			$("#otherProject ul").append(ui.draggable);
			var id = ui.draggable.data("id");
			$.ajax({
				url : "dept",
				data : {
					type : "deletePro3",
					depId : "${dept.id }",
					proIds : id
				},
				dataType : "text",
				type : "post",
				success : function(data) {
					if (data == "true") {

					}
				}
			})
		}
	})
</script>
