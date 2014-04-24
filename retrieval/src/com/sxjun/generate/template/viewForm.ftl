<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>${functionName}管理</title>
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
			
			$("#btnSubmit").click(function(){
				$("#inputForm").ajaxSubmit({
	                type: 'post',
	                url: 'save' ,
	                success: function(data){
	                	if(data.msg==0){
	                		art.dialog.alert('保存成功！');
	                	}else
	                		art.dialog.alert('保存失败！');
	                		
	                },
	                error: function(XmlHttpRequest, textStatus, errorThrown){
	                	art.dialog.alert('网络不通，保存失败！');
	                }
	            });
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${r"${ctx_a}"}/${urlPrefix}/list">${functionName}列表</a></li>
		<li class="active"><a href="${r"${ctx_a}"}/${urlPrefix}/form?id=${"${"+className+".id}"}">${r"${not empty "+className+".id?'修改':'添加'}"}${functionName}</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="${className}" action="${r"${ctx_a}"}/${urlPrefix}/save" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<input id="name" name="${className}.name" value=${"${"+className+".name}"}>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<input id="remarks" name="${className}.remarks" value=${"${"+className+".remarks}"}>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>
