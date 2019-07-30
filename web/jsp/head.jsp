<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2019/7/17
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品的菜单栏和导航条</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
</head>
<body>
        <%--    只要页面一加载就触发ajax  ，也就是下面的一部分内容    --%>
        <script type="text/javascript">
            $(function () {
                /*执行Ajax代码*/
                var url = "${pageContext.request.contextPath}/category";
                var params = "method=findCategory";

                /*固定套路有两个：
                $.get
                $.post
                        url:servlet的访问路径
                        params:要带给servlet的参数
                        funciotn(d):响应成功要执行的函数, d:服务器返回的值===response.getWriter().print(json);
                */

                $.post(url,params,function (d) {
                    // alert("ok");
                    //a是索引，b是当前索引的内容
                    $(d).each(function (a,b) {
                        // alert(a+":"+b.cname);
                        // alert(b.cname);
                        //把数据都放在导航条上,u1代表下面的100行，将原来的分类注释掉，然后使用append的方式补充分类
                        $("#u1").append("<li> <a href=${pageContext.request.contextPath}/product?method=findProduct&pageNumber=1&cid="+b.cid+">"+b.cname+"</a></li>");
                    })
                },"json")
            })
        </script>
        <%--抽取导航条--%>
        <!--
            	时间：2015-12-30
            	描述：菜单栏
         -->
        <div class="container-fluid">
            <div class="col-md-4">
                <img src="${pageContext.request.contextPath}/img/logo2.png" />
            </div>
            <div class="col-md-5">
                <img src="${pageContext.request.contextPath}/img/header.png" />
            </div>
            <div class="col-md-3" style="padding-top:20px">
                <%--    SESSION不为空则表示登录成功了，显示这一部分内容   --%>
                <c:if test="${not empty sessionScope.user}">
                    <ol class="list-inline" >
                        <li>${user.name}：欢迎你！</li>
                        <li><a href="${pageContext.request.contextPath}/order?method=findOrder&pageNumber=1">我的订单</a></li>
                        <li><a href="cart.htm">购物车</a></li>
                        <li><a href="${pageContext.request.contextPath}/user?method=quit">退出</a></li>
                    </ol>

                </c:if>
                <%--    SESSION为空表示现在处于非登录状态，显示这一部分内容   --%>
                <c:if test="${empty sessionScope.user}">
                    <ol class="list-inline">
                        <li><a href="${pageContext.request.contextPath}/user?method=loginUI">登录</a></li>
                            <%--倘若jsp文件夹放在web-inf下，则无法通过浏览器直接访问。先访问servlet，再通过servlet转发给web-inf下--%>
                            <%--以后最好不要通过jap访问jsp。先访问servlet，再通过servlet访问jsp--%>
                        <li><a href="${pageContext.request.contextPath}/user?method=registerUI">注册</a></li>
                        <li><a href="cart.htm">购物车</a></li>
                    </ol>
                </c:if>


            </div>
        </div>
        <!--
            时间：2015-12-30
            描述：导航条
        -->
        <div class="container-fluid">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">首页</a>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul id="u1" class="nav navbar-nav">
                            <%--这些数据都从数据库获取--%>
                            <%--<li class="active"><a href="product_list.htm">手机数码<span class="sr-only">(current)</span></a></li>--%>
                            <%--<li><a href="#">电脑办公</a></li>--%>
                            <%--<li><a href="#">电脑办公</a></li>--%>
                            <%--<li><a href="#">电脑办公</a></li>--%>
                        </ul>
                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Search">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>

                    </div>
                    <!-- /.navbar-collapse -->
                </div>
                <!-- /.container-fluid -->
            </nav>
        </div>
</body>
</html>
