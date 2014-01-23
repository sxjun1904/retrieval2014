<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>添加数据源</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			selectDownMenu("address","addressIds","canvasDiv",{demo:true});
			var val = $("#databaseType").val();
			$("input[name='databaseType']").each(function(){
				if(val==$(this).val())
		        	$(this).attr("checked", true);
		    });
			$("input[name='databaseType']").click(function(){
				$("#databaseType").val($(this).val());
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx_a}/database/list">数据源列表</a></li>
		<li class="active"><a href="${ctx_a}/database/form?id=${database.id}">${not empty database.id?'修改':'添加'}数据源</a></li>
	</ul><br/>
	<form id="inputForm" action="${ctx_a}/database/save" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">数据库名:</label>
			<div class="controls">
				<input id="databaseName" name="database.databaseName" value="${database.databaseName}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据库类型:</label>
			<div class="controls">
				<input id="databaseType" type="hidden" name="database.databaseType" value="${database.databaseType}">
				<c:forEach items="${databaseTypes}" var="dt">
				<input type="radio" name="databaseType" value="${dt.key}" />${dt.value}
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ip地址:</label>
			<div class="controls">
				<input id="ip" name="database.ip" value="${database.ip}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">端口:</label>
			<div class="controls">
				<input id="port" name="database.port" value="${database.port}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<input id="user" name="database.user" value="${database.user}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="password" name="database.password" value="${database.password}">
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<input type="text" name="address" id="address" >
		<input type="hidden" name="addressIds" id="addressIds" >
		<div id="canvasDiv" demo="true"></div>
	</form>
</body>
</html>