<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/index_v3.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:18:46 GMT -->
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>首页数据</title>
<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
<meta name="description"
	content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

<link rel="shortcut icon" href="favicon.ico">
<link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">

<!-- Morris -->
<link href="/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">

<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>收入</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins">${data.income }</h1>
						<small>总收入</small>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<span class="label label-info pull-right">全年</span>
						<h5>订单</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins">${data.ordernum}</h1>

						<small>订单总数</small>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>完成交易</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins">${data.finish}</h1>
						<small>完成交易数</small>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>交易关闭</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins">${data.close}</h1>
						<small>交易关闭数</small>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>订单</h5>
						<div class="pull-right">
							<div class="btn-group">
								<input type="text" class="btn-xs btn-white" id="year"
									value="2017" size="4" /><label>年</label> <select id="data"
									class="btn btn-xs btn-white">
									<option value="1">一月</option>
									<option value="2">二月</option>
									<option value="3">三月</option>
									<option value="4">四月</option>
									<option value="5">五月</option>
									<option value="6">六月</option>
									<option value="7">七月</option>
									<option value="8">八月</option>
									<option value="9">九月</option>
									<option value="10">十月</option>
									<option value="11">十一月</option>
									<option value="12">十二月</option>
								</select>
							</div>
						</div>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-9">
								<!-- <div class="flot-chart">
									<div class="flot-chart-content" id="flot-dashboard-chart"></div>
								</div> -->
								<div class="flot-chart">
									<div class="flot-chart-content" id="flot-line-chart"></div>
								</div>
							</div>
							<div class="col-sm-3">
								<ul class="stat-list">
									<li>
										<h2 class="no-margins">${data.ordernum}</h2> <small>订单总数</small>
									</li>
									<li>
										<h2 class="no-margins ">${data.close}</h2> <small>最近一个月订单</small>
										<div class="progress progress-mini">
											<div style="width: 60%;" class="progress-bar"></div>
										</div>
									</li>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>最新评论(最新10条)</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="close-link"> <i class="fa fa-times"></i>
							</a>
						</div>
					</div>
					<c:forEach items="${data.list}" var="comment">
						<div class="ibox-content">
							<div class="feed-activity-list">

								<div class="feed-element">
									<div>
										<a href="javascript:void(0);" class="pull-left"> <img
											width=30px height=30px alt="image" src="/img/a1.jpg">
										</a> <strong>${comment.buyername}</strong>
										<div>${comment.comments}</div>
										<small class="text-muted"> <fmt:formatDate
												value="${comment.time}" pattern="yyyy-MM-dd HH:mm:ss" />
										</small>
									</div>
								</div>

							</div>
						</div>
					</c:forEach>
				</div>
			</div>

			<div class="col-sm-8">

				<div class="row">
					<div class="col-sm-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>交易排行榜(前六名)</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">

								<div class="row">
									<div class="col-sm-12">
										<table class="table table-hover margin bottom">
											<thead>
												<tr>
													<th style="width: 5%" class="text-center">序号</th>
													<th>交易</th>
													<th class="text-center">交易次数</th>
													<th class="text-center">销售额</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${goodrank}" var="rank" varStatus="status">
													<tr>
														<td class="text-center">${status.index+1}</td>
														<td><small>${rank.goodname}</small></td>
														<td class="text-center small">${rank.ordernum}</td>
														<td class="text-center"><span
															class="label label-primary">&yen;${rank.income}</span></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
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
	<script src="/js/plugins/flot/jquery.flot.js"></script>
	<script src="/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="/js/plugins/flot/jquery.flot.spline.js"></script>
	<script src="/js/plugins/flot/jquery.flot.resize.js"></script>
	<script src="/js/plugins/flot/jquery.flot.pie.js"></script>
	<script src="/js/plugins/flot/jquery.flot.symbol.js"></script>
	<script src="/js/plugins/peity/jquery.peity.min.js"></script>
	<script src="/js/demo/peity-demo.min.js"></script>
	<script src="/js/content.min.js?v=1.0.0"></script>
	<script src="/js/plugins/jquery-ui/jquery-ui.min.js"></script>
	<script src="/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	<script src="/js/plugins/easypiechart/jquery.easypiechart.js"></script>
	<script src="/js/plugins/sparkline/jquery.sparkline.min.js"></script>
	<script>
		$(document).ready(function() {
			//获取当前月
			var date = new Date;
			var month = date.getMonth() + 1;//当前月份
			//设置select默认选中
			$("#data").val(month);

			$("#data").change(function() {

				//获取选中值
				var selected = $(this).children('option:selected');
				var value = selected.val();//select选中的值,月份

				//获取输入框的年份
				var year = $("#year").val();
				//参数,格式为2017-04
				var time = year + "-" + (value <= 9 ? ("0" + value) : value);
				//ajax请求数据
				//订单情况
				$.ajax({
					url : "/index/order",
					data : {
						current : time
					},
					dataType : "json",
					success : function(result) {
						if (result.status == 200) {
							var json = eval(result.data);
							var e = {
								series : {
									lines : {
										show : !0,
										lineWidth : 2,
										fill : !0,
										fillColor : {
											colors : [ {
												opacity : 0
											}, {
												opacity : 0
											} ]
										}
									}
								},
								yaxis : {
									max : result.max < 10 ? 10 : result.max,
								},
								xaxis : {
									tickDecimals : 0
								},
								colors : [ "#1ab394" ],
								grid : {
									color : "#999999",
									hoverable : !0,
									clickable : !0,
									tickColor : "#D4D4D4",
									borderWidth : 0
								},
								legend : {
									show : !1
								},
								tooltip : !0,
								tooltipOpts : {
									content : value + "月%x号: %y单"
								}
							}, o = {
								label : "bar",
								data : json
							};
							$.plot($("#flot-line-chart"), [ o ], e)
						}

					}
				});

			});
			$("#data").trigger('change');
		});
	</script>
	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/index_v3.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:18:49 GMT -->
</html>
