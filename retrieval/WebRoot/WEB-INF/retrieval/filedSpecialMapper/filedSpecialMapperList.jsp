<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>特殊字段映射管理</title>
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
		<li class="active"><a href="${ctx}/filedSpecialMapper/list">特殊字段映射列表</a></li>
		<li><a href="${ctx}/filedSpecialMapper/form">特殊字段映射添加</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>数据库字段</th><th>类型</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${filedSpecialMapper}" var="filedSpecialMapper">
			<tr>
				<td><a href="${ctx}/filedSpecialMapper/form?id=${filedSpecialMapper.id}">${filedSpecialMapper.sqlField}</a></td>
				<td>${filedSpecialMapper.SpecialType}</td>
				<td>
    				<a href="${ctx}/filedSpecialMapper/form/${filedSpecialMapper.id}">修改</a>
					<a href="${ctx}/filedSpecialMapper/delete/${filedSpecialMapper.id}" onclick="return confirm('确认要删除该特殊字段映射吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
