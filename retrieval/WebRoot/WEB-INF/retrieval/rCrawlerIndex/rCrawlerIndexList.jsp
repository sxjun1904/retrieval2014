<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>索引设置管理</title>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#init").click(function(){
				$.ajax({
				    url: 'init',
				    success: function (data) {
				    	if(data.msg==0)
				    		art.dialog.alert('后台启动初始化！');
				    },
				    cache: false
				});
			});
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
		<li class="active"><a href="${ctx_a}/rCrawlerIndex/list">爬虫索引列表</a></li>
		<li><a href="${ctx_a}/rCrawlerIndex/form">爬虫索引添加</a></li>
	</ul>
	<button id="init" class="btn btn-primary">初始化索引</button>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>分类名称</th><th>分类</th><th>URL地址</th><th>线程数</th><th>爬取深度</th><th>信息</th><th>启用状态</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${rCrawlerIndex}" var="rCrawlerIndex">
			<tr>
				<td>${rCrawlerIndex.name}</td>
			    <td>${rCrawlerIndex.indexCategory.indexInfoType}</td>
				<td>${rCrawlerIndex.url}</td>
				<td>${rCrawlerIndex.numberOfCrawlers}</td>
				<td>${rCrawlerIndex.maxDepthOfCrawling}</td>
				<td>${rCrawlerIndex.error}</td>
				<td>${rCrawlerIndex.isOn}</td>
				<td>
    				<a href="${ctx_a}/rCrawlerIndex/form?id=${rCrawlerIndex.id}&categoryid=${rCrawlerIndex.indexCategory.id}">修改</a>
					<a href="${ctx_a}/rCrawlerIndex/delete/${rCrawlerIndex.id}" onclick="return confirmx('确认要删除该索引设置吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
