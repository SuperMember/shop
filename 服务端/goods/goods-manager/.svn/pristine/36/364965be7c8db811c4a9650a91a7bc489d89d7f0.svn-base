<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>物流详情</title>
<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
<meta name="description"
	content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

<link rel="shortcut icon" href="favicon.ico">
<link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet">
</head>
<body class="gray-bg">
	<div class="row">
		<div class="col-sm-12">
			<div class="wrapper wrapper-content">
				<div class="row animated fadeInRight">
					<div class="col-sm-12">
						<div class="ibox float-e-margins">
							<div class="" id="ibox-content">
								<div id="vertical-timeline"
									class="vertical-container light-timeline">
									<c:if test="${fn:length(list)<=0}">
										<div class="vertical-timeline-block">
											<div class="vertical-timeline-icon blue-bg">
												<i class="fa fa-file-text"></i>
											</div>
											<div class="vertical-timeline-content">
												<h2>暂无快递信息</h2>
											</div>
										</div>
									</c:if>
									<c:if test="${fn:length(list)>0}">
										<c:forEach items="${list}" var="item">
											<div class="vertical-timeline-block">
												<div class="vertical-timeline-icon blue-bg">
													<i class="fa fa-file-text"></i>
												</div>
												<div class="vertical-timeline-content">
													<small>${item.time}</small>
													<h2>${item.context}</h2>
												</div>
											</div>
										</c:forEach>
									</c:if>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<script src="/js/jquery.min.js?v=2.1.4"></script>
	<script src="/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="/js/content.min.js?v=1.0.0"></script>
	<script>
		$(document).ready(function() {
			event.preventDefault();
			$("#ibox-content").addClass("ibox-content");
			$("#vertical-timeline").removeClass("light-timeline");
			$("#vertical-timeline").addClass("dark-timeline")

		});
	</script>
	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>