<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理系统 - 主页</title>

<link rel="shortcut icon" href="favicon.ico">
<link href="css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/style.min862f.css?v=4.1.0" rel="stylesheet">

</head>
</head>
<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="nav-close">
			<i class="fa fa-times-circle"></i>
		</div>
		<div class="sidebar-collapse">
			<ul class="nav" id="side-menu">
				<li class="nav-header">
					<div class="dropdown profile-element">
						<span><img alt="image" class="img-circle"
							<c:if test="${user.img==null}">src="img/hu.jpg"</c:if>
							<c:if test="${user.img!=null}">src="${user.img}"</c:if>
							width=50px height=50px /></span> <a data-toggle="dropdown"
							class="dropdown-toggle" href="#"> <span class="clear">
								<span class="block m-t-xs"><strong class="font-bold">${user.username}</strong></span>
								<span class="text-muted text-xs block"><c:if
										test="${user.seller==1}">卖家</c:if> <c:if
										test="${user.seller==2}">超级管理员</c:if><b class="caret"></b></span>
						</span>
						</a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
							<li class="divider"></li>
							<li><a href="login.html">安全退出</a></li>
						</ul>
					</div>
					<div class="logo-element">宝+</div>
				</li>
				<!--卖家显示-->
				<c:if test="${user.seller==1}">
					<li><a href="#"><i class="fa fa-desktop"></i> <span
							class="nav-label">商品管理</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="static/add.html">增加商品</a></li>
							<li><a class="J_menuItem" href="static/showgoods.jsp">货物仓库</a>
							</li>
						</ul></li>
					<li><a href="#"><i class="fa fa-flask"></i> <span
							class="nav-label">交易管理</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/page/show/finish">已卖出的货物</a>
							</li>
							<li><a class="J_menuItem" href="/page/show/comments">评价管理</a></li>
						</ul></li>
					<li><a href="#"><i class="fa fa-flask"></i> <span
							class="nav-label">订单管理</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/page/order">订单详情</a></li>
						</ul></li>

					<li><a href="#"><i class="fa fa-flask"></i> <span
							class="nav-label">物流管理</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="/send/logistics">物流跟踪</a></li>
						</ul></li>
				</c:if>
				<!--如果是管理员，增加几个功能-->
				<c:if test="${user.seller==2}">
					<li><a href="#"><i class="fa fa-flask"></i> <span
							class="nav-label">页面</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="static/startpage.html">启动页</a></li>
							<li><a class="J_menuItem" href="static/homepageAD.html">首页轮播页</a></li>
						</ul></li>
					<li><a href="#"><i class="fa fa-flask"></i> <span
							class="nav-label">版本</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="static/appupdate.html">app版本更新</a></li>
						</ul></li>
				</c:if>
			</ul>
		</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
				<div class="navbar-header">
					<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
						href="#"><i class="fa fa-bars"></i> </a>
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li class="dropdown"><a class="dropdown-toggle count-info"
						data-toggle="dropdown" href="#"> <i class="fa fa-bell"></i> <span
							class="label label-primary" id="news"></span>
					</a>
						<ul class="dropdown-menu dropdown-alerts">
							<li><a href="/page/order"
								class="J_menuItem message neworder">
									<div>
										新订单<span class="label label-primary" id="neworder"></span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li><a href="/page/show/comments"
								class="J_menuItem message newcomments">
									<div>
										新评论<span class="label label-primary" id="newcomments"></span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li><a href="/page/show/comments"
								class="J_menuItem message newreply">
									<div>
										新回复<span class="label label-primary" id="newreply"></span>
									</div>
							</a></li>
						</ul></li>
				</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
				<div class="page-tabs-content">
					<a href="javascript:;" class="active J_menuTab"
						data-id="index_v1.html">首页</a>
				</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a href="login.html" class="roll-nav roll-right J_tabExit"><i
					class="fa fa fa-sign-out"></i>退出</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="/index/showdatas" frameborder="0" data-id="index_v1.html"
					seamless></iframe>
			</div>

		</div>
		<!--右侧部分结束-->

		<script src="js/jquery.min.js?v=2.1.4"></script>
		<script src="js/bootstrap.min.js?v=3.3.6"></script>
		<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
		<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="js/plugins/layer/layer.min.js"></script>
		<script src="js/hplus.min.js?v=4.1.0"></script>
		<script type="text/javascript" src="js/contabs.min.js"></script>
		<script src="js/plugins/pace/pace.min.js"></script>

		<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
		<!--websocket-->
		<script type="text/javascript">
			function setContent() {
				if ($("#news").html() == "") {
					$("#news").html("1");
				} else {
					var value = parseInt($("#news").html()) + 1;
					$("#news").html("");
					$("#news").html(value);
				}
			}
			var ws;
			$(document).ready(function() {
				ws = new WebSocket("ws://localhost:8087/dwr/websocket");

				/* 连接打开 */
				ws.onopen = function() {

				};
				/* 回调 */
				ws.onmessage = function(event) {

					if (event.data == "订单") {
						//收到新订单
						setContent();
						if ($("#neworder").html() == "") {
							$("#neworder").html("1");
						} else {
							var value = parseInt($("#neworder").html()) + 1;
							$("#neworder").html("");
							$("#neworder").html(value);
						}
					} else if (event.data == "评论") {
						//收到新评论
						setContent();
						if ($("#newcomments").html() == "") {
							$("#newcomments").html("1");
						} else {
							var value = parseInt($("#newcomments").html()) + 1;
							$("#newcomments").html("");
							$("#newcomments").html(value);
						}
					} else if (event.data == "回复") {
						//收到新回复
						setContent();
						if ($("#newreply").html() == "") {
							$("#newreply").html("1");
						} else {
							var value = parseInt($("#newreply").html()) + 1;
							$("#newreply").html("");
							$("#newreply").html(value);
						}
					} else {
						//关闭状态有消息，连接服务器返回保存的数据
						var jsonobj = eval('(' + event.data + ')');
						$("#news").html(jsonobj.count);//设置总数量
						$("#neworder").html(jsonobj.ordernum);//设置订单数量
						$("#newcomments").html(jsonobj.commentsnum);//设置评论数量
						$("#newreply").html(jsonobj.replynum);//设置回复数量
					}

				};
				/* 连接关闭 */
				ws.onclose = function(event) {
				};

				//监听a标签的点击事件
				$("a.message").click(function() {
					//消除消息的数量
					$("#news").html("");
				});
				$(".neworder").click(function() {
					$("#neworder").html("");
				});
				$(".newcomments").click(function() {
					$("#newcomments").html("");
				});
				$(".newreply").click(function() {
					$("#newreply").html("");
				});
			});
		</script>
		<!--websocket结束-->
</body>


</html>