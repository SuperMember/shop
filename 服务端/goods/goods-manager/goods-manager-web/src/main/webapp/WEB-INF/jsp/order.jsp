<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>订单</title>
<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
<meta name="description"
	content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

<link rel="shortcut icon" href="favicon.ico">
<link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="/css/plugins/footable/footable.core.css" rel="stylesheet">

<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="col-sm-12">
				<div class="col-sm-4 m-b-xs">
					<form action="/page/order" method="get" id="form">
						<select name="orderstatus" id="orderstatus">
							<option value=""
								<c:if test="${type==null}">selected="selected"</c:if>>全部</option>
							<option value="2"
								<c:if test="${type==2}">selected="selected"</c:if>>新订单</option>
							<option value="5"
								<c:if test="${type==5}">selected="selected"</c:if>>完成交易</option>
							<option value="4"
								<c:if test="${type==4}">selected="selected"</c:if>>已发货</option>
						</select> <input type="hidden" name="type" id="type" />
					</form>
				</div>
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>订单详情</h5>
					</div>
					<div class="ibox-content">

						<table class="footable table table-stripped toggle-arrow-tiny"
							data-page-size="8">
							<thead>
								<tr>
									<th data-toggle="true">订单id</th>
									<th>金额</th>
									<th>邮费</th>
									<th>状态</th>
									<th>创建时间</th>
									<th>预计发货时间</th>
									<th>交易完成</th>
									<th>交易关闭</th>
									<th data-hide="all">收货人全名</th>
									<th data-hide="all">固定电话</th>
									<th data-hide="all">移动电话</th>
									<th data-hide="all">省份</th>
									<th data-hide="all">城市</th>
									<th data-hide="all">区/县</th>
									<th data-hide="all">收货地址</th>
									<th data-hide="all">邮政编码</th>
									<th data-hide="all">留言</th>
									<th data-hide="all">商品数量</th>
									<th data-hide="all">商品标题</th>
									<th data-hide="all">商品单价</th>
									<th data-hide="all">商品总价</th>
									<th data-hide="all">商品图片连接</th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${itemList}" var="item">
									<input type="hidden" value="${item.status}" />
									<tr>
										<!--显示部分-->
										<td>${item.order_id}</td>
										<td>${item.payment}</td>
										<td>${item.post_fee}</td>
										<td><select class="status" name="status">
												<option
													<c:if test="${item.status==2}">selected="selected"</c:if>
													value="2">已付款</option>
												<option
													<c:if test="${item.status==3}">selected="selected"</c:if>
													value="3">接受订单</option>
												<option
													<c:if test="${item.status==4}">selected="selected"</c:if>
													value="4">已发货</option>
												<option
													<c:if test="${item.status==5}">selected="selected"</c:if>
													value="5">交易成功</option>
												<option
													<c:if test="${item.status==6}">selected="selected"</c:if>
													value="6">交易关闭</option>
										</select></td>
										<td>${item.create_time}</td>
										<td>${item.consign_time}</td>
										<td>${item.end_time}</td>
										<td>${item.close_time}</td>

										<!--隐藏部分(买家相关信息)-->
										<td>${item.receiver_name}</td>
										<td>${item.receiver_phone}</td>
										<td>${item.receiver_mobile}</td>
										<td>${item.receiver_state}</td>
										<td>${item.receiver_city}</td>
										<td>${item.receiver_district}</td>
										<td>${item.receiver_address}</td>
										<td>${item.receiver_zip}</td>
										<td><c:if test="${item.buyer_rate==1}">${item.buyer_message}</c:if>
											<c:if test="${item.buyer_rate==0}">该买家没有进行留言</c:if></td>
										<!-- 商品信息 -->
										<td>${item.num}</td>
										<td>${item.title}</td>
										<td>${item.price}</td>
										<td>${item.total_fee}</td>
										<td>${item.pic_path}</td>
										<td><c:url value="/send" var="url">
												<c:param name="orderId" value="${item.order_id}" />
												<c:param name="address"
													value="${item.receiver_name} ${item.receiver_phone} ${item.receiver_state} ${item.receiver_city} ${item.receiver_district} ${item.receiver_address}" />
											</c:url> <a href="<c:out value="${url}"/>"
											<c:if test="${item.status!=5 && item.status!=4}">class="btn btn-primary btn-xs"</c:if>><c:if
													test="${item.status!=5 && item.status!=4}">去发货</c:if></a></td>
										<td><img
											<c:if test="${item.create_time eq '2017-04-21'}">src="../img/icon_new.png"</c:if>
											width=30px height=30px class="new" style="display: none;" /></td>
									</tr>
									<input type="hidden" value="${item.user_id}"/>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5">
										<ul class="pagination pull-right"></ul>
									</td>
								</tr>
							</tfoot>
						</table>

					</div>
				</div>
			</div>
		</div>

	</div>
	<script src="/js/jquery.min.js?v=2.1.4"></script>
	<script src="/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="/js/plugins/footable/footable.all.min.js"></script>
	<script src="/js/content.min.js?v=1.0.0"></script>
	<script>
		$(document)
				.ready(
						function() {
							$(".footable").footable();

							//设置订单是否为新订单
							$("#orderstatus").change(function() {
								var value = $(this).val();
								if (value != null) {
									var $form = $("#form");
									$("#type").val(value);//参数
									$form.submit();
								}
							});

							$(".status")
									.change(
											function() {

												var selectthis = $(this);
												var hidden = $(this).parents(
														"tr").prev(
														"input:hidden").val();//原先值

												var userId = $(this).parents(
														"tr").next(
														"input:hidden").val();//用户id
														//alert(userId);
												var selected = selectthis
														.children('option:selected');
												var value = selected.val();//select选中的值

												//判断原先显示的select的选中值和重新选中的值
												if (value < hidden) {
													alert("流程不能往回走，请重新选择!");
													//还原select的设置值
													//找到该行的select
													selectthis.val(hidden);
												} else {
													var id = $(this).parents(
															"tr")
															.children("td").eq(
																	0).html();//订单id
													var itemId = id.substring(
															37, id.length);
													if (confirm("你确定进行修改吗？")) {
														//修改订单状态
														$
																.ajax({
																	type : 'GET',
																	url : "/page/eidt/order",
																	data : {
																		itemId : itemId,
																		status : value
																	},
																	success : function(
																			data) {
																		if (data.status == 200) {
																			alert("修改成功");
																			var time = data.data;
																			var strs = new Array(); //定义一数组 
																			strs = time
																					.split(","); //字符分割
																			selectthis
																					.parents(
																							"tr")
																					.children(
																							"td")
																					.eq(
																							5)
																					.html(
																							strs[0]);//获取更新时间列
																			//修改hidden的值
																			selectthis
																					.parents(
																							"tr")
																					.prev(
																							"input:hidden")
																					.val(
																							value);
																			if (value == 5
																					&& strs.length > 1) {
																				selectthis
																						.parents(
																								"tr")
																						.children(
																								"td")
																						.eq(
																								8)
																						.html(
																								strs[1]);
																			} else if (value = 6 && strs.length > 1) {
																				selectthis
																						.parents(
																								"tr")
																						.children(
																								"td")
																						.eq(
																								9)
																						.html(
																								strs[1]);
																			} else if (value = 4 && strs.length > 1) {
																				selectthis
																						.parents(
																								"tr")
																						.children(
																								"td")
																						.eq(
																								7)
																						.html(
																								strs[1]);
																			}
																			//websocket通知信息
																			parent.ws
																					.send(userId+","+selected.html());
																		} else {
																			alert("修改失败,请重新进行修改");
																		}
																	},
																	dataType : "json"
																});
													} else {
														//还原select的设置值
														//找到该行的select
														selectthis.val(hidden);
													}
												}

											});

						});
	</script>
	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/table_foo_table.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:20:03 GMT -->
</html>