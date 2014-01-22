<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>数据库索引管理管理</title>
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
		<li class="active"><a href="${ctx}/rDatabaseIndex/list">数据库索引管理列表</a></li>
		<li><a href="${ctx}/rDatabaseIndex/form">数据库索引管理添加</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>备注</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${rDatabaseIndex}" var="rDatabaseIndex">
			<tr>
				<td><a href="${ctx}/rDatabaseIndex/form?id=${rDatabaseIndex.id}">${rDatabaseIndex.name}</a></td>
				<td>${rDatabaseIndex.name}</td>
				<td>
    				<a href="${ctx}/rDatabaseIndex/form/${rDatabaseIndex.id}">修改</a>
					<a href="${ctx}/rDatabaseIndex/delete/${rDatabaseIndex.id}" onclick="return confirmx('确认要删除该数据库索引管理吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
