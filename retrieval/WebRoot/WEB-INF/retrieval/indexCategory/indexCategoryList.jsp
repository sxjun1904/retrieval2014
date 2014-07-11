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
		<li class="active"><a href="${ctx_a}/indexCategory/list">索引分类列表</a></li>
		<li><a href="${ctx_a}/indexCategory/form">索引分类添加</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>索引分类</th><th>路径类型</th><th>路径</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${indexCategory}" var="indexCategory">
			<tr>
				<td><a href="${ctx_a}/indexCategory/form?id=${indexCategory.id}">${indexCategory.indexInfoType}</a></td>
				<td>${fns:getDictMapByKey(3,indexCategory.indexPathType)}</td>
				<td>${indexCategory.indexPath}</td>
				<td>
    				<a href="${ctx_a}/indexCategory/form?id=${indexCategory.id}">修改</a>
					<a href="${ctx_a}/indexCategory/delete/${indexCategory.id}" onclick="return confirm('确认要删除该索引分类吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
