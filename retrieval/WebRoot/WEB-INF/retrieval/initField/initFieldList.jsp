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
		<li class="active"><a href="${ctx_a}/initField/list">字段列表</a></li>
		<li><a href="${ctx_a}/initField/form">添加字段</a></li>
	</ul>
	<br/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead><tr><th>字段</th><th>字段类型</th><th>默认值</th><th>描述</th><th>操作</th></thead>
		<tbody>
		<c:forEach items="${initField}" var="initField">
			<tr>
				<td>
					<div>${initField.field}</div>
				</td>
				<td>
					<div>${fns:getDictMapByKey(2,initField.fieldType)}</div>
				</td>
				<td>
					<div>${initField.defaultValue}</div>
				</td>
				<td>
					<div>${initField.description}</div>
				</td>
				<td>
    				<a href="${ctx_a}/initField/form?id=${initField.id}">修改</a>
    				<a href="${ctx_a}/initField/delete/${initField.id}" onclick="return confirm('确认要删除该字段吗？', this.href)">删除</a>
    			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>