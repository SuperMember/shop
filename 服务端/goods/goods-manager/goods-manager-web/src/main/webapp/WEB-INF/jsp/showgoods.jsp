<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- Mirrored from www.zi-han.net/theme/hplus/table_basic.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:20:01 GMT -->
<head>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>商品详情</title>
<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台" />
<meta name="description"
	content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术" />

<link rel="shortcut icon" href="favicon.ico" />
<link href="../css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet" />
<link href="../css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet" />
<link href="../css/plugins/iCheck/custom.css" rel="stylesheet" />
<link href="../css/animate.min.css" rel="stylesheet" />
<link href="../css/style.min862f.css?v=4.1.0" rel="stylesheet" />
<link rel="stylesheet" href="../css/style/pagination.css" />


</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>商品详情</h5>

					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-5 m-b-xs">
								<div data-toggle="buttons" class="btn-group">
									<button type="button" id="delete"
										class="btn btn-sm btn-primary">删除</button>
								</div>
							</div>
							<div class="col-sm-4 m-b-xs">
								<div data-toggle="buttons" class="btn-group">
									<label class="btn btn-sm btn-white "> <input
										type="radio" id="option1" name="options" value="1" />上架
									</label> <label class="btn btn-sm btn-white "> <input
										type="radio" id="option2" name="options" value="2" />下架
									</label>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="input-group">
									<input type="text" placeholder="请输入关键词"
										class="input-sm form-control" id="searchtext" /> <span
										class="input-group-btn">
										<button type="button" class="btn btn-sm btn-primary"
											id="search">搜索</button>
									</span>
								</div>
							</div>

						</div>
						<div class="table-responsive">
							<table class="table table-striped">
								<thead>
									<tr>

										<th><input type="checkbox" id="checkall" /></th>
										<th>序号</th>
										<th>标题</th>
										<th>卖点</th>
										<th>图片</th>
										<th>价格(双击修改)</th>
										<th>库存(双击修改)</th>
										<th>类别</th>
										<th>状态</th>
										<th></th>
									</tr>
								</thead>
								<tbody id="goodsbody">

								</tbody>
							</table>
						</div>

					</div>
				</div>
			</div>

		</div>
		<div class="M-box1"></div>
	</div>
	<script src="../js/jquery.min.js?v=2.1.4"></script>
	<script src="../js/bootstrap.min.js?v=3.3.6"></script>
	<script src="../js/plugins/peity/jquery.peity.min.js"></script>
	<script src="../js/content.min.js?v=1.0.0"></script>
	<script src="../js/plugins/iCheck/icheck.min.js"></script>
	<script src="../js/demo/peity-demo.min.js"></script>
	<script src="../js/jquery.pagination.js"></script>
	<script>
		var pageCount;
		/*填充数据进入tbody*/
		function content(result) {
			$("#goodsbody").html("");//清空页面

			$
					.each(
							result,
							function(index, content) {
								//第一列数据,以下累加
								var one = "<td><input type=\"checkbox\"  class=\"i-checks\" name=\"itemIds\" value=\""
										+ content.id + "\"</></td>";//复选框
								var id = "<td >" + content.id + "</td>";
								var title = "<td><a href=\"javascript:void(0)\" id=\"example\" class=\"btn btn-success \" rel=\"popover\" data-content=\""
										+ content.title
										+ "\" data-original-title=\"标题\">详情</a></td>";
								//替换掉a标签
								var a = new RegExp("<a[^<>]+>.+?</a>", "g");
								var sellpoint = "<td><a href=\"javascript:void(0)\" id=\"example\" class=\"btn btn-success \" rel=\"popover\"  data-original-title=\"卖点\" data-content=\""
										+ content.sellPoint.replace(a, "")
										+ "\">详情</a></td>";
								var image = "<td><a target=\"_blank\" href=\""+content.image.split(",")[0]+"\"><img width=50px height=60px src=\""
										+ content.image.split(",")[0]
										+ "\"</></a></td>";
								var price = "<td class=\"edit\" name=\"price\">&yen;"
										+ content.price + "</td>";
								var num = "<td class=\"edit\" name=\"num\">"
										+ content.num + "</td>";
								var cid = "<td><a href=\"javascript:void(0)\" class=\"easyui-linkbutton selectItemCat\">"
										+ content.cidname + "</a></td>";
								//1上架，2下架
								var select;
								if (content.status == 1) {
									select = "<select class=\"status\" name=\"status\">"
											+ "<option value='1' selected=\"selected\">上架</option>"
											+ "<option value='2' >下架</option>"
											+ "</select>";
								} else {
									select = "<select class=\"status\" name=\"status\">"
											+ "<option value='1'>上架</option>"
											+ "<option value='2' selected=\"selected\">下架</option>"
											+ "</select>";
								}
								var status = "<td>" + select + "</td>";
								//根据时间相差一天为显示最新，其他不显示
								//由于服务器传来的时间为long型
								//所以要先将long型转化成date类型
								var imgnew;//图片
								var newTime = (new Date()).getTime();
								//判断时间相差
								if (content.updated != null
										&& (newTime - content.updated <= 86400000)) {
									//小于两天，显示最新标签
									imgnew = "<td><img src=\"../img/icon_new.png\" width=30px height=30px class=\"new\" style=\"visibility:visible;\"/></td>";//存放图片，用于展示是最新添加的商品

								} else {
									imgnew = "<td><img  width=30px height=30px class=\"new\" style=\"display:none;\"/></td>";
								}
								$("#goodsbody").append(
										"<tr>" + one + id + title + sellpoint
												+ image + price + num + cid
												+ status + imgnew + "</tr>");
								//悬浮弹出框
								$(".btn-success").popover();
							});

			//重新初始化全选checkbox的状态为未选中
			$("#checkall").attr("checked", false);
		}
		//初始化第一页函数
		function initFirstPage(status) {
			//利用ajax请求第一页的数据
			$.ajax({
				type : 'GET',
				url : "/goods/list",
				data : {
					page : "1",
					rows : "15",
					status : status
				},
				success : function(data) {
					//分页插件
					//初始化第一页
					var result = eval(data.rows);//将json数据转化成数组
					content(result);//渲染视图

					$('.M-box1').pagination(
							{

								pageCount : Math.ceil(data.total / 15) == 0 ? 1
										: Math.ceil(data.total / 15),
								jump : true,
								callback : function(api) {
									var data = {
										page : api.getCurrent(),
										rows : '15',
										status : status
									};
									$.getJSON('/goods/list', data, function(
											json) {

										if (json.rows != null
												&& json.rows.length != 0) {
											var result = eval(json.rows);//将json数据转化成数组
											content(result);
										}
									});
								}
							});
				},
				dataType : "json"
			});
		}
		$(document)
				.ready(
						function() {
							$(".i-checks").iCheck({
								checkboxClass : "icheckbox_square-green",
								radioClass : "iradio_square-green",
							})
							initFirstPage(0);
							//监听select选择，保存上架或下架状态
							$("#goodsbody").on(
									"change",
									"select",
									function() {
										var selectthis = $(this);
										var selected = selectthis
												.children('option:selected');
										var value = selected.val();//select选中的值
										//询问是否进行更改
										var msg;
										if (value == "1")
											msg = "你确定要上架该商品吗?";
										else {
											msg = "你确定要下架该商品吗?";
										}
										if (confirm(msg)) {
											//ajax更改商品状态
											//获取该行商品id
											var itemId = selectthis.parents(
													"tr").children("td").eq(1)
													.html();
											$.ajax({
												ype : 'GET',
												url : "/goods/edit",
												data : {
													itemId : itemId,
													status : value
												},
												success : function(data) {
													if (data.status == 200) {
														alert("修改成功");
														if (value == "1") {
															value = "2";
														} else {
															value = "1";
														}
														initFirstPage(value);
													} else {
														alert("修改失败，请稍后重试");
													}
												}
											});
										} else {
											//还原select状态
											//找到该行的select
											var status;
											if (value == "1") {
												status = "2";
											} else {
												status = "1";
											}
											selectthis.val(status);
										}
									});
							//实现双击文字出现编辑框
							//获取class为caname的元素 
							$("#goodsbody")
									.on(
											"dblclick",
											".edit",
											function() {
												var td = $(this);
												var txt = td.text();//标签内容
												var type = td.attr("name");//获取name属性，用于区分修改哪一列
												var input = $("<input type='text'value='" + txt + "'/>");
												td.html(input);
												input.click(function() {
													return false;
												});
												//获取焦点 
												input.trigger("focus");
												//文本框失去焦点后提交内容，重新变为文本 
												input
														.blur(function() {
															var newtxt = $(this)
																	.val();
															//判断文本有没有修改 
															if (newtxt != txt) {
																//进行修改
																td.html(newtxt);
																//获取该行商品id
																var itemId = $(
																		td)
																		.parents(
																				"tr")
																		.children(
																				"td")
																		.eq(1)
																		.html();
																if (confirm("保存修改?")) {
																	//ajax进行修改
																	$
																			.ajax({
																				type : 'GET',
																				url : "/goods/edit",
																				data : {
																					itemId : itemId,
																					value : newtxt,
																					type : type
																				},
																				success : function(
																						data) {
																					if (data.status == 200) {
																						alert("修改成功");
																					} else {
																						alert("修改失败，请稍后重试");
																					}
																				}
																			});
																} else {
																	//没有进行修改
																	//还原初始数值
																	td
																			.html(txt);
																}
															} else {
																//没有进行修改
																td.html(newtxt);
															}
														});
											});

							//监听复选框的点击，进行删除操作
							//监听全选按钮的点击
							$("#checkall").click(
									function() {
										//设置其他的checkbox为选中状态
										$("input[class='i-checks']").prop(
												"checked", this.checked);//注意用attr只会执行一次全选反选

									});

							//监听删除按钮的点击
							$("#delete")
									.click(
											function() {
												if ($("input[type='checkbox']")
														.is(':checked')) {
													//如果有复选框选中状态
													//执行删除操作
													if (confirm("确定要进行删除操作?")) {
														//ajax进行删除操作
														var checked = [];
														//获取所有选中的checkbox的值，存放进数组中
														$(
																'input:checkbox:checked')
																.each(
																		function() {
																			checked
																					.push($(
																							this)
																							.val());
																		});
														$
																.ajax({
																	type : 'GET',//提交方式 post 或者get  
																	url : "/goods/delete",
																	traditional : true, //必须加上该句话来序列化  
																	data : {
																		'itemIds' : checked
																	},//提交的参数   
																	success : function(
																			data) {
																		if (data.status == 200) {
																			alert("删除成功");
																			initFirstPage(0);//重新加载第一页数据
																		} else {
																			alert("删除失败，请稍后重试！");
																		}
																	}
																});
													}
												} else {
													alert("请至少选择一项！");
												}
											});
							//监听radio的选中情况
							$("input:radio[name='options']").change(function() {
								//请求ajax获取相对应的查询条件
								var value = $(this).val();
								initFirstPage(value);
							});
							//监听搜索框的点击事件
							$("#search")
									.click(
											function() {
												//ajax访问搜索服务进行搜索
												//获取搜索框的内容
												var value = $("#searchtext")
														.val();
												if (value == null
														|| value == "") {
													initFirstPage(0);//如果没有输入搜索条件直接搜索全部
												} else {
													$
															.ajax({
																type : 'GET',//提交方式 post 或者get  
																url : "http://localhost:8084/search/",
																data : {
																	q : value
																},//提交的参数   
																success : function(
																		data) {
																	if (data.status == 200) {
																		content(data.data.itemList);//重新加载第一页数据
																		var val = $(
																				"#searchtext")
																				.val();
																		//重新初始化分页插件
																		$(
																				'.M-box1')
																				.pagination(
																						{

																							pageCount : Math
																									.ceil(data.data.total / 15) == 0 ? 1
																									: Math
																											.ceil(data.data.total / 15),
																							jump : true,
																							callback : function(
																									api) {
																								var data = {
																									page : api
																											.getCurrent(),
																									rows : '15',
																									q : val
																								};
																								$
																										.ajax({
																											data : data,
																											url : 'http://localhost:8084/search/',
																											dataType : "jsonp",
																											jsonp : 'callback',
																											jsonpCallback : 'mycallback',
																											success : function(
																													json) {
																												var result = eval(json.data.itemList);//将json数据转化成数组
																												content(result);
																											}
																										});
																							}
																						});
																	} else {

																	}
																},
																jsonp : 'callback',
																jsonpCallback : 'mycallback',
																dataType : "jsonp"
															});
												}

											});
						});
	</script>
	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/table_basic.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:20:01 GMT -->
</html>