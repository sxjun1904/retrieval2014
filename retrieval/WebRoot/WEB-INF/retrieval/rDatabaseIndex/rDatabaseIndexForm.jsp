<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>数据库索引管理管理</title>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/rDatabaseIndex/list">数据库索引管理列表</a></li>
		<li class="active"><a href="${ctx}/rDatabaseIndex/form?id=${rDatabaseIndex.id}">${not empty rDatabaseIndex.id?'修改':'添加'}数据库索引管理</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="rDatabaseIndex" action="${ctx}/rDatabaseIndex/save" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<input id="name" name="rDatabaseIndex.name" value=${rDatabaseIndex.name}>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<input id="remarks" name="rDatabaseIndex.remarks" value=${rDatabaseIndex.remarks}>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>
