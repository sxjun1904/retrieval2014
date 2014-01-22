<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>字段映射管理</title>
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
		<li><a href="${ctx}/filedMapper/list">字段映射列表</a></li>
		<li class="active"><a href="${ctx}/filedMapper/form?id=${filedMapper.id}">${not empty filedMapper.id?'修改':'添加'}字段映射</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="filedMapper" action="${ctx}/filedMapper/save" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">数据库字段:</label>
			<div class="controls">
				<input id="sqlField" name="filedMapper.sqlField" value=${filedMapper.sqlField}>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">索引字段:</label>
			<div class="controls">
				<input id="indexField" name="filedMapper.indexField" value=${filedMapper.indexField}>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>
