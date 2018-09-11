<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

#main {
	width: 1536px;
	height: 600px;
}

#left {
	width: 200px;
	height: 600px;
	float: left;
	padding-left: 20px;
}

#right {
	width: 1319px;
	height: 600px;
	float: left;
	background-color: #ccc;
}

#top {
	width: 1536px;
	clear: both;
	height: 60px;
	background-color: #DDDDDD;
}

#visit {
	width: 150px;
	height: 60px;
	float: right;
	margin-right: 36px;
	background-color: #FFCCCC;
}

#pageview {
	width: 150px;
	height: 30px;
	text-align: center;
	line-height: 30px;
}

#online {
	width: 150px;
	height: 30px;
	text-align: center;
	line-height: 30px;
}

#bottom {
	width: 1536px;
	clear: both;
	height: 60px;
	background-color: #DDDDDD;
}

.yi {
	width: 180px;
	height: 40px;
	color: #fff;
	background-color: #337ab7;
	border-radius: 4px;
	text-align: center;
	line-height: 40px;
	margin-top: 10px;
	cursor: pointer;
}

.er {
	list-style: none;
}

.er li {
	width: 180px;
	height: 30px;
	color: #F5F5F5;
	background-color: #191970;
	border-radius: 4px;
	text-align: center;
	line-height: 30px;
	margin-top: 10px;
}

.er a {
	color: #F5F5F5;
	text-decoration: none;
}

.h {
	display: none;
}
</style>
</head>
<body>
	<div id="top">
		<div id="visit">
			<div id="pageview">网站访问量：${applicationScope.pageView }</div>
			<div id="online">在线人数：</div>
		</div>
	</div>
	<div id="main">
		<div id="left">
			<div class="yi">员工</div>
			<div class="h">
				<ul class="er">
					<li><a href="employee" target="right"><p>员工管理</p></a></li>
					<li><a href="employee?type=showAdd" target="right"><p>增加员工</p></a></li>
					<li><a href="employee?type=showAdd2" target="right"><p>增加员工2</p></a></li>
					<li><a href="employee?type=showAdd3" target="right"><p>增加员工3</p></a></li>
					<li id="updateEmp">修改员工</li>
				</ul>
			</div>
			<div class="yi">部门</div>
			<div class="h">
				<ul class="er">
					<li><a href="dept" target="right"><p>部门管理</p></a></li>
					<li><a href="dept?type=showAdd" target="right"><p>增加部门</p></a></li>
				</ul>
			</div>
			<div class="yi">项目</div>
			<div class="h">
				<ul class="er">
					<li><a href="project" target="right"><p>项目管理</p></a></li>
					<li><a href="project?type=showAdd" target="right"><p>增加项目</p></a></li>
				</ul>
			</div>
			<div class="yi">绩效</div>
			<div class="h">
				<ul class="er">
					<li><a href="score" target="right"><p>绩效管理</p></a></li>
					<li><a href="score?type=search2" target="right"><p>绩效管理2</p></a></li>
				</ul>
			</div>
		</div>
		<iframe id="right" name="right" scrolling="no" frameborder="0" src="employee"></iframe>
	</div>
	<div id="bottom"></div>
	<script type="text/javascript">
		$(".yi").click(function() {
			$(this).next().slideToggle(500);
		})
	</script>
	<script type="text/javascript">
		$("#updateEmp").click(function() {
			alert($("#right").contents().find(".select").find("td").eq(1).text());
		})
	</script>
	<script type="text/javascript">	
		var websocket = null;

		if ('WebSocket' in window) {
			websocket = new WebSocket("ws://192.168.0.179:8080/EmployeesManageWeb/online");
		} else {
			alert('没有建立websocket连接')
		}

		//连接发生错误的回调方法
		websocket.onerror = function() {
			console.log("连接错误！");
		};

		//连接成功建立的回调方法
		websocket.onopen = function(event) {
			console.log("建立连接！");
		}

		//接收到消息的回调方法
		websocket.onmessage = function(event) {
			console.log(event.data);
			$("#online").html("在线人数："+event.data);
		}

		//连接关闭的回调方法
		websocket.onclose = function() {
			console.log("close!");
		}

		//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口。
		window.onbeforeunload = function() {
			websocket.close();
		}

	</script>
</body>
</html>