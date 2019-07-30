<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2019/7/15
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>标题</title>
  </head>
  <body>
      <%--请求转发到jsp/index.jsp
          好处：能去servlet绕一圈取数据，然后到index.jsp
      --%>
      <%--<%request.getRequestDispatcher("/jsp/index.jsp").forward(request,response);%>--%>
      <%request.getRequestDispatcher("/product?method=findList").forward(request,response);%>
  </body>
</html>
