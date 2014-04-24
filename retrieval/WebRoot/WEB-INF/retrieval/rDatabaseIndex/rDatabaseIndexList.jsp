<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>索引设置管理</title>
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
		<li class="active"><a href="${ctx_a}/rDatabaseIndex/list">索引设置列表</a></li>
		<li><a href="${ctx_a}/rDatabaseIndex/form">索引设置添加</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>数据源</th><th>表名</th><th>主键字段</th><th>标题字段</th><th>摘要字段</th><th>是否去重</th><th>信息</th><th>启用状态</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${rDatabaseIndex}" var="rDatabaseIndex">
			<tr>
				<td><a href="${ctx_a}/rDatabaseIndex/form?id=${rDatabaseIndex.id}">${rDatabaseIndex.database.databaseName}</a></td>
				<td>${rDatabaseIndex.tableName}</td>
				<td>${rDatabaseIndex.keyField}</td>
				<td>${rDatabaseIndex.defaultTitleFieldName}</td>
				<td>${rDatabaseIndex.defaultResumeFieldName}</td>
				<td>${rDatabaseIndex.rmDuplicate}</td>
				<td>${rDatabaseIndex.error}</td>
				<td>${rDatabaseIndex.isOn}</td>
				<td>
    				<a href="${ctx_a}/rDatabaseIndex/form?id=${rDatabaseIndex.id}">修改</a>
					<a href="${ctx_a}/rDatabaseIndex/delete/${rDatabaseIndex.id}" onclick="return confirmx('确认要删除该索引设置吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
