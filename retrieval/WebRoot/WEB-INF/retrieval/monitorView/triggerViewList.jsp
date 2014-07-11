<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>线程监控管理</title>
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
		<li class="active"><a href="${ctx_a}/monitorView/listTrigger">线程监控列表</a></li>
		<li><a href="${ctx_a}/monitorView/listIsInit">状态监控列表</a></li>
		<li><a href="${ctx_a}/monitorView/listDSTG">触发器列表</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>线程名称</th><th>任务类型</th><th>任务开始时间</th><th>下一次执行时间</th></tr></thead>
		<tbody>
		<c:forEach items="${triggerView}" var="triggerView">
			<tr>
				<td>${triggerView.name}</td>
				<td>${triggerView.classSimpleName}</td>
				<td>${triggerView.startTime}</td>
				<td>${triggerView.nextFireTime}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
