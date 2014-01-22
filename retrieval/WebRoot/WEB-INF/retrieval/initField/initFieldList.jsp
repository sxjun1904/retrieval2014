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
		<li class="active"><a href="${ctx}/initField/list">字段列表</a></li>
		<li><a href="${ctx}/initField/form">添加字段</a></li>
	</ul>
	<br/>
	<div>
		<div class="nav-collapse">
            <ul id="main_nav" class="nav">
		    	<li id="themeSwitch" class="dropdown">
			       	<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
				    <ul class="dropdown-menu">
				      <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme?theme=${dict.value}&url='+location.href">${dict.label}</a></li></c:forEach>
				    </ul>
			    </li>
            </ul>
         </div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead><tr><th>字段</th><th>字段类型</th><th>默认值</th><th>描述</th></thead>
		<tbody>
		<c:forEach items="${initField}" var="initField">
			<tr>
				<td>
					<div>${initField.field}</div>
				</td>
				<td>
					<div>${initField.fieldType}</div>
				</td>
				<td>
					<div>${initField.defaultValue}</div>
				</td>
				<td>
					<div>${initField.description}</div>
				</td>
				<td>
    				<a href="${ctx}/initField/form/${initField.id}">修改</a>
    				<a href="${ctx}/initField/delete/${initField.id}" onclick="return confirm('确认要删除该字段吗？', this.href)">删除</a>
    			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>