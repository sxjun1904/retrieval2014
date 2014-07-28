<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>缓存监控管理</title>
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
		<li class="active"><a href="${ctx_a}/monitorView/listCache">缓存监控列表</a></li>
		<li><a href="${ctx_a}/monitorView/listDSTG">触发器列表</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>缓存名称</th><th>缓存内容</th><th>创建时间</th><th>最后访问时间</th><th>过期时间</th><th>最后更新时间</th><th>命中次数</th><th>存活时间</th><th>空闲时间</th></tr></thead>
		<tbody>
		<c:forEach items="${cacheView}" var="cacheView">
			<tr>
				<td>${cacheView.cacheName}</td>
				<td>${cacheView.value}</td>
				<td>${cacheView.creationTime}</td>
				<td>${cacheView.lastAccessTime}</td>
				<td>${cacheView.expirationTime}</td>
				<td>${cacheView.lastUpdateTime}</td>
				<td>${cacheView.hitCount}</td>
				<td>${cacheView.timeToLive}</td>
				<td>${cacheView.timeToIdle}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
