<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>已出售的货物</title>
<link rel="shortcut icon" href="favicon.ico">
<link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">

<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="col-sm-12">

				<c:forEach items="${list}" var="item">
					<div class="ibox-content p-xl">
						<div class="row">

							<div class="col-sm-6 text-left">

								<address>
									<strong>${item.receiver_name}</strong><br>
									${item.receiver_city}<br>
									${item.receiver_district}<br>
									${item.receiver_address}<br> 
									${item.receiver_zip}<br>
									<abbr title="Phone">手机:</abbr>${item.receiver_mobile}
								</address>
								<p>
									<span><strong>交易完成时间</strong> ${item.end_time}</span>
								</p>
							</div>
							
							<div class="col-sm-6 text-right">
								<img src="/img/icon_finish.png" width=100px height=100px/>
							</div>
						</div>

						<div class="table-responsive m-t">
							<table class="table invoice-table">
								<thead>
									<tr>
										<th>清单</th>
										<th>数量</th>
										<th>单价</th>
										<th>总价</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<div>
												<strong>${item.title}</strong>
											</div>
										</td>
										<td>${item.num}</td>
										<td>&yen;${item.price}</td>
										<td>&yen;${item.total_fee}</td>
									</tr>
								</tbody>
							</table>
						</div>

					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<script src="/js/jquery.min.js?v=2.1.4"></script>
	<script src="/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="/js/content.min.js?v=1.0.0"></script>
	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/invoice.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:47 GMT -->
</html>