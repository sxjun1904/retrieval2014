<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>状态监控管理</title>
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
		<li class="active"><a href="${ctx_a}/monitorView/listIsInit">状态监控列表</a></li>
		<li><a href="${ctx_a}/monitorView/listCache">缓存监控列表</a></li>
		<li><a href="${ctx_a}/monitorView/listDSTG">触发器列表</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>分类</th><th>数据源/名称</th><th>表</th><th>状态</th><th>索引时间</th><th>建议</th></tr></thead>
		<tbody>
		<c:forEach items="${isInitView}" var="isInitView">
			<tr>
				<td>${isInitView.indexInfoType}</td>
				<td>${isInitView.databaseName}</td>
				<td>${isInitView.tableName}</td>
				<td>${isInitView.isInit}</td>
				<td>${isInitView.mediacyTime}</td>
				<td>${isInitView.info}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
