<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>快递</title>
<link rel="shortcut icon" href="favicon.ico">
<link href="../css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="../css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="../css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="../css/plugins/steps/jquery.steps.css" rel="stylesheet">
<link href="../css/animate.min.css" rel="stylesheet">
<link href="../css/style.min862f.css?v=4.1.0" rel="stylesheet">
<link href="//g.alicdn.com/??tm/wuliu-kuaidi/1.0.9/kuaidi/index.css"
	type="text/css" rel="stylesheet" />
<%
	request.setCharacterEncoding("utf-8");
%>
</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox">
					<div class="ibox-title">
						<h5>寄件</h5>
					</div>
					<div class="ibox-content">
						<form id="form" class="wizard-big" method="post">
							<h1>填写寄件人/收件人地址</h1>
							<fieldset>
								<div class="module j_module">
									<div class="module-head">寄件人地址</div>
									<div class="module-body poster">
										<input type="hidden" name="orderid" id="orderId"
											value="${orderId}" />
										<table class="senderInfo" id="J_posterSenderInfo"
											style="display: table;">

											<tr>
												<td class="t-all"><span class="c-red">*</span>&nbsp;寄件人：</td>
												<td><input type="text" name="name"
													class="form-control required" /></td>
											</tr>
											<tr>
												<td class="t-all"><span class="c-red">*</span>&nbsp;地址：</td>
												<td>
													<div class="address" id="s_address_container">
														<select id="Province">
														</select> <select id="City">
														</select> <select id="Area">
														</select>
													</div> <input type="hidden" id="s_prov_value" name="province" />
													<input type="hidden" id="s_city_value" name="city" /> <input
													type="hidden" id="s_area_value" name="area" />
												</td>
											</tr>
											<tr>
												<td>&nbsp;详细地址：</td>
												<td><input type="text"
													class="addressText  form-control" name="address" /></td>
											</tr>
											<tr>
												<td class="t-all"><span class="c-red">*</span>&nbsp;邮编：</td>
												<td><input type="text" name="zipcode" id="zipcode"
													class="form-control required" /></td>
											</tr>
											<tr>
												<td class="t-all"><span class="c-red">*</span>&nbsp;手机：</td>
												</td>
												<td><input type="text"
													class="mobile form-control required phone" name="mobile"
													id="mobile" />&nbsp;&nbsp;</td>
											</tr>

										</table>
									</div>
								</div>
							</fieldset>
							<h1>选择快递公司</h1>
							<fieldset>
								<div class="row">
									<div class="module-body postCompany">

										<table class="mui-table">
											<colgroup>
												<col width="40%">
												<col width="40%">
												<col width="20%">
											</colgroup>
											<tbody>

												<tr>
													<td class="search-table-logo"><input type="radio"
														class="J_SelectCompany" value="中国邮政" name="company">
														<img
														src="//img.alicdn.com/tps/i2/T1kH40FPFcXXc_d6ve-160-80.jpg">
														中国邮政</td>

													<td class="search-table-logo"><input type="radio"
														class="J_SelectCompany" value="EMS" name="company">
														<img
														src="//img.alicdn.com/tps/i2/T1q1d6FKlaXXc_d6ve-160-80.jpg">
														EMS</td>

													<td class="search-table-logo"><input type="radio"
														class="J_SelectCompany" value="圆通速递" name="company">
														<img
														src="//img.alicdn.com/tps/i3/T1Z.V2FS8bXXc_d6ve-160-80.jpg">
														圆通速递</td>

												</tr>
												<tr>
													<td class="search-table-logo"><input type="radio"
														class="J_SelectCompany" value="中通快递" name="company">
														<img
														src="//img.alicdn.com/tps/i1/T1aPR1FLXcXXc_d6ve-160-80.jpg">
														中通快递</td>

													<td class="search-table-logo"><input type="radio"
														class="J_SelectCompany" value="韵达快递" name="company">
														<img
														src="//img.alicdn.com/tps/i1/T1y8h0FLpcXXc_d6ve-160-80.jpg">
														韵达快递</td>

													<td class="search-table-logo"><input type="radio"
														class="J_SelectCompany" value="顺丰速运" name="company">
														<img
														src="//img.alicdn.com/tps/i4/T1qpX0FPFcXXc_d6ve-160-80.jpg">
														顺丰速运</td>

												</tr>
											</tbody>

										</table>

									</div>
								</div>
							</fieldset>


							<h1>完成预约</h1>
							<fieldset>
								<div class="remarks">
									<table>
										<tr>
											<td>快递公司:</td>
											<td><span id="cp"></span></td>
										</tr>
										<tr>
											<td>货物名称：</td>
											<td><input type="text" name="goodsname" value=""
												class="form-control required" /></td>
										</tr>
										<tr>
											<td>货值:</td>
											<td><input type="text" name="goodsvalue" value=""
												id="goodsvalue" class="form-control required" />
												&nbsp;&nbsp;元&nbsp;&nbsp;填写物品价值，便于您投诉维权。</td>
										</tr>
										<tr>
											<td>备注:</td>
											<td><input type="text" name="remark" value=""
												class="remarksText form-control" /></td>
										</tr>
										<tr>
											<td>寄件人信息:</td>
											<td id="info"></td>
										</tr>
										<tr>
											<td>目标地址:</td>
											<td><input type="radio" name="targetinfo"
												checked="checked" value="${address}" />${address}</td>
										</tr>
									</table>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>


	<script src="../js/jquery.min.js?v=2.1.4"></script>
	<script src="../js/bootstrap.min.js?v=3.3.6"></script>
	<script src="../js/content.min.js?v=1.0.0"></script>
	<script src="../js/plugins/staps/jquery.steps.min.js"></script>
	<script src="../js/plugins/validate/jquery.validate.min.js"></script>
	<script src="../js/plugins/validate/messages_zh.min.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							// 手机号码验证
							jQuery.validator
									.addMethod(
											"isMobile",
											function(value, element) {
												var length = value.length;
												var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
												return this.optional(element)
														|| (length == 11 && mobile
																.test(value));
											}, "请正确填写您的手机号码");
							//邮编地址验证
							jQuery.validator.addMethod("isZipCode", function(
									value, element) {
								var length = value.length;
								var zipcode = /^[1-9][0-9]{5}$/;
								return this.optional(element)
										|| (length == 6 && zipcode.test(value));
							}, "请正确填写邮编地址");
							//金钱的验证
							jQuery.validator
									.addMethod(
											"isMoney",
											function(value, element) {
												var length = value.length;
												var money = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
												return this.optional(element)
														|| (money.test(value));
											}, "请正确填写金额");

							$("#wizard").steps();
							$("#form")
									.steps(
											{
												bodyTag : "fieldset",
												onStepChanging : function(
														event, currentIndex,
														newIndex) {
													if (currentIndex > newIndex) {
														return true
													}
													if (newIndex === 3
															&& Number($("#age")
																	.val()) < 18) {
														return false
													}
													var form = $(this);
													if (currentIndex < newIndex) {
														$(
																".body:eq("
																		+ newIndex
																		+ ") label.error",
																form).remove();
														$(
																".body:eq("
																		+ newIndex
																		+ ") .error",
																form)
																.removeClass(
																		"error")
													}
													form.validate().settings.ignore = ":disabled,:hidden";
													return form.valid()
												},
												onStepChanged : function(event,
														currentIndex,
														priorIndex) {
													if (currentIndex == 2) {
														//获取选中的快递公司
														var value = $(
																'input:radio[name="company"]:checked')
																.val();

														if (value == null)
															$("#cp").val(
																	"没有设置快递公司");
														else {
															$("#cp")
																	.html(value);

														}

														//设置寄件人信息
														var name = $(
																'input:text[name="name"]')
																.val();
														var address = $(
																"#s_prov_value")
																.val()
																+ " "
																+ $(
																		"#s_city_value")
																		.val()
																+ " "
																+ $(
																		"#s_area_value")
																		.val()
																+ " "
																+ $(
																		'input:text[name="address"]')
																		.val()
																+ " ";
														var zipcode = $(
																'input:text[name="zipcode"]')
																.val();
														var mobile = $(
																'input:text[name="mobile"]')
																.val();
														$("#info")
																.html(
																		name
																				+ " "
																				+ address
																				+ " "
																				+ zipcode
																				+ " "
																				+ mobile);
													}

													if (currentIndex === 2
															&& Number($("#age")
																	.val()) >= 18) {
														$(this).steps("next")

													}
													if (currentIndex === 2
															&& priorIndex === 3) {
														$(this).steps(
																"previous")
													}

												},
												onFinishing : function(event,
														currentIndex) {
													var form = $(this);
													form.validate().settings.ignore = ":disabled";
													return form.valid()
												},
												onFinished : function(event,
														currentIndex) {
													var form = $(this);
													$
															.ajax({
																cache : true,
																type : "POST",
																url : "/send/goods",
																data : form
																		.serialize(),// 你的formid
																async : false,
																error : function(
																		request) {
																	alert("提交失败，请稍后重试");
																},
																success : function(
																		data) {
																	alert("提交成功,物流号为:"
																			+ data.data);
																	location.href = "/page/order";
																}
															});

												}
											})
									.validate(
											{
												errorPlacement : function(
														error, element) {
													element.before(error)
												},
												rules : {
													mobile : {
														required : true,
														minlength : 11,
														isMobile : true
													},
													zipcode : {
														required : true,
														minlength : 6,
														isZipCode : true
													},
													goodsvalue : {
														required : true,
														isMoney : true
													}
												},
												messages : {
													mobile : {
														required : "请输入手机号码",
														minlength : "必须是11位数字",
														isMobile : "请输入正确的手机格式"
													},
													zipcode : {
														required : "请输入邮编地址",
														minlength : "必须是6位数字",
														isZipCode : "请输入正确的邮编地址格式"
													},
													goodsvalue : {
														required : true,
														isMoney : "请输入金钱的正确格式"
													}
												}
											})

							//初始化城市联动select
							province();
							$('#Province').bind("change", city);
							$('#City').bind("change", area);

							/*省(jsonp)*/
							function province() {
								$
										.ajax({
											dataType : "jsonp",
											type : "get",
											url : "http://localhost:8083/logistics/location",
											data : {
												"type" : "province"
											},
											jsonp : 'callback',
											jsonpCallback : 'mycallback',
											success : function(data) {
												var json = eval(data.data);
												for (var i = 0; i < json.length; i++) {
													$("#Province")
															.append(
																	"<option value=" + json[i].provinceid + ">"
																			+ json[i].province
																			+ "</option>");
												}

												city();
											}
										})
							}
							;
							/*城市(jsonp)*/
							function city() {
								$("#City").html("");
								//设置隐藏域的内容，方便提交
								$("#s_prov_value").attr("value",
										$("#Province option:selected").html());
								$
										.ajax({

											dataType : "jsonp",
											type : "get",
											url : "http://localhost:8083/logistics/location",
											data : {
												"type" : "city",
												"id" : $(
														"#Province option:selected")
														.val()
											},
											jsonp : 'callback',
											jsonpCallback : 'mycallback',
											success : function(data) {
												var json = eval(data.data);
												for (var i = 0; i < json.length; i++) {
													$("#City")
															.append(
																	"<option value=" + json[i].cityid + ">"
																			+ json[i].city
																			+ "</option>");
												}
												//设置隐藏域的内容，方便提交

												area();
											}
										})
							}
							;
							/*区域(jsonp)*/
							function area() {
								$("#Area").html("");
								$("#s_city_value").attr("value",
										$("#City option:selected").html());
								$
										.ajax({
											dataType : "jsonp",
											type : "get",
											url : "http://localhost:8083/logistics/location",
											data : {
												"type" : "area",
												"id" : $(
														"#City option:selected")
														.val()
											},
											jsonp : 'callback',
											jsonpCallback : 'mycallback',
											success : function(data) {
												var json = eval(data.data);
												for (var i = 0; i < json.length; i++) {
													$("#Area")
															.append(
																	"<option value=" + json[i].areaid + ">"
																			+ json[i].area
																			+ "</option>");
												}
												$("#s_area_value")
														.attr(
																"value",
																$(
																		"#Area option:selected")
																		.html());
											}
										})
							}
							;

							//监听区选择列表，为隐藏域赋值
							$("#Area").change(
									function() {
										//设置隐藏域的内容，方便提交
										$("#s_area_value").attr(
												"value",
												$("#Area option:selected")
														.val());
									})
							//为隐藏域orderid设置值

						});
	</script>
	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>