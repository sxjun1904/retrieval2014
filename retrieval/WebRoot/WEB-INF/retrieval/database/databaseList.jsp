<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>普通搜索</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/head.jsp" %>
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
	<style type="text/css">
		a:link {
		color:'#0000d5';
		}
		font{
		size:3;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx_a}/database/list">数据源列表</a></li>
		<li><a href="${ctx_a}/database/form">添加数据源</a></li>
	</ul>
	<br/>
	<div>
	<%-- 	<div class="nav-collapse">
            <ul id="main_nav" class="nav">
		    	<li id="themeSwitch" class="dropdown">
			       	<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
				    <ul class="dropdown-menu">
				      <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme?theme=${dict.value}&url='+location.href">${dict.label}</a></li></c:forEach>
				    </ul>
			    </li>
            </ul>
         </div>
         <div style="float:left">
         	<form id="searchForm" modelAttribute="user" action="${ctx}/database/list" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="添加" onclick="return page();"/>
			</form>
         </div>
 --%>	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead><tr><th>数据库名</th><th>数据库类型</th><th>ip地址</th><th>端口</th><th>用户名</th><th>密码</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="databasePage">
			<tr>
				<td>
					<div>${databasePage.databaseName}</div>
				</td>
				<td>
					<div>${fns:getDictMapByKey(0,databasePage.databaseType)}</div>
				</td>
				<td>
					<div>${databasePage.ip}</div>
				</td>
				<td>
					<div>${databasePage.port}</div>
				</td>
				<td>
					<div>${databasePage.user}</div>
				</td>
				<td>
					<div>${databasePage.password}</div>
				</td>
				<td>
    				<a href="${ctx_a}/database/form?id=${databasePage.id}">修改</a>
    				<a href="${ctx_a}/database/delete/${databasePage.id}" onclick="return confirm('确认要删除该数据源吗？', this.href)">删除</a>
    			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>