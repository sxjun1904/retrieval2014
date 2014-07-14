<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理管理</title>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx_a}/user/list">用户管理列表</a></li>
		<li><a href="${ctx_a}/user/form">用户管理添加</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>用户名</th><th>姓名</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${user}" var="user">
			<tr>
				<td><a href="${ctx_a}/user/form?id=${user.id}">${user.username}</a></td>
				<td>${user.realname}</td>
				<td>
    				<a href="${ctx_a}/user/form?id=${user.id}">修改</a>
					<a href="${ctx_a}/user/delete/${user.id}" onclick="return confirmx('确认要删除该用户管理吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
