<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>添加索引字段</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var val = $("#fieldType").val();
			$("input[name='fieldType']").each(function(){
				if(val==$(this).val())
		        	$(this).attr("checked", true);
		    });
			$("input[name='fieldType']").click(function(){
				$("#fieldType").val($(this).val());
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
		<li><a href="${ctx_a}/initField/list">字段列表</a></li>
		<li class="active"><a href="${ctx_a}/initField/form?id=${initField.id}">${not empty initField.id?'修改':'添加'}字段</a></li>
	</ul><br/>
	<form id="inputForm" action="${ctx_a}/initField/save" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">字段名:</label>
			<div class="controls">
				<input id="field" name="initField.field" value="${initField.field}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">默认值:</label>
			<div class="controls">
				<input id="defaultValue" name="initField.defaultValue" value="${initField.defaultValue}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段类型:</label>
			<div class="controls">
				<input id="fieldType" type="hidden" name="initField.fieldType" value="${initField.fieldType}">
				<c:forEach items="${itemTypes}" var="itemTypes">
				<input type="radio" name="fieldType" value="${itemTypes.key}" />${itemTypes.value}
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段描述:</label>
			<div class="controls">
				<input id="description" name="initField.description" value="${initField.description}">
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>