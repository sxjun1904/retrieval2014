<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>索引管理管理</title>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.combobox').css({"width":"100px"});
			$('#_type').val($('#_itemTypes').val());
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<style>
		.input-medium{
			font:16px/22px arial;
			width:250px;
			height:30px;
			margin-left:15px;
			border-radius:8px
		}
		.input-medium_1{
			font:16px/22px arial;
			width:80px;
			height:30px;
			margin-left:15px;
			border-radius:8px
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx_a}/indexManager/list">索引管理列表</a></li>
	</ul>
	 <div style="float:left">
         	<form id="searchForm" modelAttribute="user" action="${ctx_a}/indexManager/list" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="_itemTypes" type="hidden" value="${simpleItem.fieldType}"/>
				<label class="control-label">类型:</label>
				<select id="_type" class="combobox" name="simpleItem.fieldType">
				  <c:forEach items="${itemTypes}" var="itemTypes">
					<option value="${itemTypes.value}" >${itemTypes.value}</option>
				  </c:forEach>
				</select>
				<label class="control-label">字段:</label>
				<input id="field" name="simpleItem.field" class="input-medium_1" value="${simpleItem.field}">
				<label class="control-label">关键字:</label>
				<input id="keyword" name="simpleItem.keyword" class="input-medium" value="${simpleItem.keyword}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查 询" onclick="return page();"/>
			</form>
    </div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标题</th><th>摘要</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="retrievalPage">
			<tr>
				<td>${retrievalPage.title}</td>
				<td>${retrievalPage.content}</td>
				<td>
    				<%-- <a href="${ctx_a}/indexManager/form?id=${indexManager.id}">修改</a> --%>
					<a href="${ctx_a}/indexManager/delete/${retrievalPage.retrievalResultFields['_IID']}" onclick="return confirmx('确认要删除该索引管理吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
