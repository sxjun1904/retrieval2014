<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理管理</title>
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
		<li><a href="${ctx_a}/user/list">用户管理列表</a></li>
		<li class="active"><a href="${ctx_a}/user/form?id=${user.id}">${not empty user.id?'修改':'添加'}用户管理</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="user" action="${ctx_a}/user/save" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<input id="realname" name="user.realname" value="${user.realname}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<input id="id" name="user.id" type="hidden" value="${user.id}">
				<input id="username" name="user.username" value="${user.username}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="password" type="password" name="user.password" value="${user.password}">
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>
