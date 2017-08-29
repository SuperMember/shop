<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
<meta name="description"
	content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

<link rel="shortcut icon" href="favicon.ico">
<link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet">
<title>物流信息</title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight blog">
		<div class="row">
			<div class="col-lg-4">
				<c:forEach items="${list}" var="item">
					<div class="ibox">
						<div class="ibox-content">
							<h2>${item.company}</h2>
							<div class="small m-b-xs">
								<strong>物流单号:</strong> <span class="text-muted">${item.id}</span>
							</div>
							<div class="small m-b-xs">
								<strong>寄件方:</strong> <span class="text-muted">${item.name}
									${item.province} ${item.city} ${item.area} ${address}</span>
							</div>
							<div class="small m-b-xs">
								<strong>接收方:</strong> <span class="text-muted">${item.targetinfo}</span>
							</div>
							<div class="row">
								<div class="col-md-6">
									<button class="btn btn-primary btn-xs" id="detail"
										value="${item.id}" type="button">详细物流</button>
								</div>
								<div class="col-md-6">
									<div class="small text-right">
										<h3>正在派送中...</h3>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

		</div>
	</div>
	<script src="/js/jquery.min.js?v=2.1.4"></script>
	<script src="/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="/js/content.min.js?v=1.0.0"></script>
	<script src="/js/plugins/layer/layer.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#detail").click(function() {
				var value = $(this).val();
				parent.layer.open({
					type : 2,
					title : '物流详情',
					shadeClose : true,
					shade : 0.6,
					area : [ '500px', '90%' ],
					content : '/show/detail/logistics?logisticsId=' + value //iframe的url
				});
			});
		});
	</script>
	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>