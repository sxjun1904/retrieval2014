<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>触发器监控管理</title>
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
		<li><a href="${ctx_a}/monitorView/listTrigger">线程监控列表</a></li>
		<li><a href="${ctx_a}/monitorView/listIsInit">状态监控列表</a></li>
		<li><a href="${ctx_a}/monitorView/listCache">缓存监控列表</a></li>
		<li class="active"><a href="${ctx_a}/monitorView/listDSTG">触发器列表</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>数据源</th><th>触发器</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${dstgView}" var="dstgView">
			<tr>
				<td>${dstgView.databasename}</td>
				<td>${dstgView.triggername}</td>
				<td><a href="${ctx_a}/monitorView/deleteDstg/${dstgView.triggername}?id=${dstgView.id}" onclick="return confirm('确认要删除该索引分类吗？', this.href)">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
