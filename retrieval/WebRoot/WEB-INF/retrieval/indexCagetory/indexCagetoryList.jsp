<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>索引分类管理</title>
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
		<li class="active"><a href="${ctx}/indexCagetory/list">索引分类列表</a></li>
		<li><a href="${ctx}/indexCagetory/form">索引分类添加</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>索引分类</th><th>路径类型</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${indexCagetory}" var="indexCagetory">
			<tr>
				<td><a href="${ctx}/indexCagetory/form?id=${indexCagetory.id}">${indexCagetory.indexInfoType}</a></td>
				<td>${indexCagetory.IndexPathType}</td>
				<td>${indexCagetory.IndexPath}</td>
				<td>
    				<a href="${ctx}/indexCagetory/form/${indexCagetory.id}">修改</a>
					<a href="${ctx}/indexCagetory/delete/${indexCagetory.id}" onclick="return confirmx('确认要删除该索引分类吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
