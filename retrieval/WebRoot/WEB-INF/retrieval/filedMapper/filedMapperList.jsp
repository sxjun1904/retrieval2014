<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>字段映射管理</title>
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
		<li class="active"><a href="${ctx_a}/filedMapper/list">字段映射列表</a></li>
		<li><a href="${ctx_a}/filedMapper/form">字段映射添加</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>数据库字段</th><th>索引字段</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${filedMapper}" var="filedMapper">
			<tr>
				<td><a href="${ctx_a}/filedMapper/form?id=${filedMapper.id}">${filedMapper.sqlField}</a></td>
				<td>${filedMapper.indexField}</td>
				<td>
    				<a href="${ctx_a}/filedMapper/form/${filedMapper.id}">修改</a>
					<a href="${ctx_a}/filedMapper/delete/${filedMapper.id}" onclick="return confirm('确认要删除该字段映射吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
