<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

	
			<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->

			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<%-- 静态包含一下 --%>
			<%@include file="/jsp/head.jsp" %>


		<div class="container">
			<div class="row">

				<div style="margin:0 auto;margin-top:10px;width:950px;">
					<strong>订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th colspan="4">订单编号:${orders.oid} </th>
								<th colspan="1">订单状态:
									<c:if test="${orders.state == 0}">
										<a href="#">去付款</a>
									</c:if>
									<c:if test="${orders.state == 1}">
										<a href="#">查物流详情</a>
									</c:if>
									<c:if test="${orders.state == 2}">
										<a href="#">去评价</a>
									</c:if>
									<c:if test="${orders.state == 3}">
										已完成订单
									</c:if>
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:if test="${not empty orders.list}">
								<c:forEach items="${orders.list}" var="orderItem">
									<tr class="active">
										<td width="60" width="40%">
											<input type="hidden" name="id" value="22">
											<img src="${pageContext.request.contextPath}/${orderItem.product.pimage}" width="70" height="60">
										</td>
										<td width="30%">
											<a target="_blank">${orderItem.product.pname} </a>
										</td>
										<td width="20%">
											￥${orderItem.product.shop_price}
										</td>
										<td width="10%">
											${orderItem.count}
										</td>
										<td width="15%">
											<span class="subtotal">￥${orderItem.subtotal}</span>
										</td>
									</tr>
								</c:forEach>
							</c:if>

						</tbody>
					</table>
				</div>

				<div style="text-align:right;margin-right:120px;">
					商品金额: <strong style="color:#ff6600;">￥${orders.total}元</strong>
				</div>

			</div>

			<div>
				<hr/>
				<form id="orderForm" action="${pageContext.request.contextPath}/order" method="post" class="form-horizontal" style="margin-top:5px;margin-left:150px;">
					<input type="hidden" name="method" value="payOrders">
					<input type="hidden" name="oid" value="${orders.oid}">
					<div class="form-group">
						<label for="username" class="col-sm-1 control-label">地址</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="username" name="address" placeholder="请输入收货地址">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-1 control-label">收货人</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="inputPassword3" name="name" placeholder="请输收货人">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-1 control-label">电话</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="confirmpwd" name="telephone" placeholder="请输入联系方式">
						</div>
					</div>


					<hr/>

					<div style="margin-top:5px;margin-left:150px;">
						<strong>选择银行：</strong>
						<p>
							<br/>
							<input type="radio" name="pd_FrpId" value="ICBC-NET-B2C" checked="checked" />工商银行
							<img src="${pageContext.request.contextPath}/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pd_FrpId" value="BOC-NET-B2C" />中国银行
							<img src="${pageContext.request.contextPath}/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行
							<img src="${pageContext.request.contextPath}/bank_img/abc.bmp" align="middle" />
							<br/>
							<br/>
							<input type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行
							<img src="${pageContext.request.contextPath}/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
							<img src="${pageContext.request.contextPath}/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" />建设银行
							<img src="${pageContext.request.contextPath}/bank_img/ccb.bmp" align="middle" />
							<br/>
							<br/>
							<input type="radio" name="pd_FrpId" value="GDB-NET-B2C" />广发银行
							<img src="${pageContext.request.contextPath}/bank_img/gf.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行
							<img src="${pageContext.request.contextPath}/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
							<img src="${pageContext.request.contextPath}/bank_img/cmb.bmp" align="middle" />

						</p>

						<hr/>
						<p style="text-align:right;margin-right:100px;">
							<a href="javascript:document.getElementById('orderForm').submit();">
								<img src="${pageContext.request.contextPath}/images/finalbutton.gif" width="204" height="51" border="0" />
							</a>
						</p>
						<hr/>
					</div>
				</form>
			</div>

		</div>

		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>

	</body>

</html>