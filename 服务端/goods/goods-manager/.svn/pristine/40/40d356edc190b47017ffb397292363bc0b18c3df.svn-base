<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>登录</title>
<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
<meta name="description"
	content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/style.min.css" rel="stylesheet">
<link href="css/login.min.css" rel="stylesheet">

<script type="text/javascript" src="js/jquery.min.js"></script>
<link href="css/tab/login2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/tab/login.js"></script>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script>
	if (window.top !== window.self) {
		window.top.location = window.location
	};
</script>

</head>

<body class="signin">
	<div class="signinpanel">
		<div class="row">
			<div class="col-sm-7">
				<div class="signin-info">
					<div class="m-b"></div>

				</div>
			</div>
			<div class="col-sm-5">
				<h1 style="margin: 10px; margin-left: 80px;">商城管理</h1>
				<div class="login" style="margin-top: 0px;">
					<div class="header">
						<div class="switch" id="switch">
							<a class="switch_btn_focus" id="switch_qlogin"
								href="javascript:void(0);" tabindex="7">卖家入口</a> <a
								class="switch_btn" id="switch_login" href="javascript:void(0);"
								tabindex="8">管理员入口</a>
							<div class="switch_bottom" id="switch_bottom"
								style="position: absolute; width: 64px; left: 0px;"></div>
						</div>
					</div>


					<div class="web_qr_login" id="web_qr_login"
						style="display: block; height: 235px;">

						<!--卖家登录-->
						<div class="web_login" id="web_login">
							<div class="login-box">
								<div class="login_form">
									<form action="/index.html" name="loginform"
										accept-charset="utf-8" id="login_seller_form"
										class="loginForm" method="post">
										<div>
											<font color="red">${msg}</font>
										</div>
										<input type="hidden" name="type" value="1" /> <input
											type="text" class="form-control uname" placeholder="卖家帐号"
											name="username" /> <input type="password"
											class="form-control pword m-b" placeholder="密码"
											name="password" />
										<button class="btn btn-success btn-block">登录</button>
									</form>
								</div>

							</div>

						</div>
						<!--卖家登录end-->
					</div>

					<div class="qlogin" id="qlogin" style="display: none;">

						<!--管理员登录-->
						<div class="web_login">
							<div class="login-box">
								<div class="login_form">
									<form action="/index.html" name="loginform"
										accept-charset="utf-8" id="login_seller_form"
										class="loginForm" method="post">
										<input type="hidden" name="type" value="2" /> <input
											type="text" class="form-control uname" placeholder="管理员帐号"
											name="username" /> <input type="password"
											class="form-control pword m-b" placeholder="密码"
											name="password" />
										<button class="btn btn-success btn-block">登录</button>
									</form>
								</div>
							</div>
						</div>
						<!--管理员end-->
					</div>
				</div>
			</div>
		</div>
		<div class="signup-footer">
			<div class="pull-left">&copy; 2017 All Rights Reserved.</div>
		</div>
	</div>
</body>
</html>

