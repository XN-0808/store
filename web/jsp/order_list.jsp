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
			<%--静态包含一下--%>
			<%@include file="/jsp/head.jsp" %>

		</nav>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">

						<c:if test="${not empty pb.list}">
							<c:forEach items="${pb.list}" var="order">
								<tbody>
									<tr class="success">
										<th colspan="4">订单编号:${order.oid} </th>
										<th colspan="1">订单状态:
											<c:if test="${order.state == 0}">
												<a href="${pageContext.request.contextPath}/order?method=findByoid&oid=${order.oid}" style="color: red">去付款</a>
											</c:if>
											<c:if test="${order.state == 1}">
												<a href="#" style="color: red">查看物流</a>
											</c:if>
											<c:if test="${order.state == 2}">
												<a href="#" style="color: red">去评价</a>
											</c:if>
											<c:if test="${order.state == 3}">
												<a >已完成订单</a>
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
                                    <c:forEach items="${order.list}" var="orderItem">
                                        <tr class="active">
                                            <td width="60" width="40%">
                                                <input type="hidden" name="id" value="22">
                                                <img src="${pageContext.request.contextPath}/${orderItem.product.pimage}" width="70" height="60">
                                            </td>
                                            <td width="30%">
                                                <a target="_blank">${orderItem.product.pname}</a>
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

								</tbody>
							</c:forEach>
						</c:if>

					</table>
				</div>
			</div>

			<%--分页按钮--%>
			<div style="width:380px;margin:0 auto;margin-top:50px;" align="center">
				<ul class="pagination" style="text-align:center; margin-top:10px;">
					<%--   上一页按钮    class="disabled"是在第一页禁止点击上一页       --%>
					<c:if test="${pb.pageNumber==1}">
						<li class="disabled">
							<a href="#" aria-label="Previous">
								<span aria-hidden="true">&laquo</span>
							</a>
						</li>
					</c:if>
					<c:if test="${pb.pageNumber != 1}">
						<li>
							<a href="${pageContext.request.contextPath}/order?method=findOrder&pageNumber=${pb.pageNumber-1}" aria-label="Previous">
								<span aria-hidden="true">&laquo</span>
							</a>
						</li>
					</c:if>

					<%--页数按钮--%>
					<!-- class="active" 背景样式 -->
					<c:forEach begin="1" end="${pb.totalPage}" var="page">
						<c:if test="${pb.pageNumber==page}">
							<li class="active"><a href="#">${page}</a></li>
						</c:if>
						<c:if test="${pb.pageNumber!=page}">
							<li><a href="${pageContext.request.contextPath}/order?method=findOrder&pageNumber=${page}">${page}</a></li>
						</c:if>
					</c:forEach>
					<%--向右按钮--%>
					<c:if test="${pb.pageNumber==pb.totalPage}">
						<li class="disabled">
							<a href="#" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
							</a>
						</li>
					</c:if>

					<c:if test="${pb.pageNumber!=pb.totalPage}">
						<li >
							<a href="${pageContext.request.contextPath}/order?method=findOrder&pageNumber=${pb.pageNumber+1}" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
							</a>
						</li>
					</c:if>
				</ul>
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