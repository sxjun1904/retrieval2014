<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>索引分类管理</title>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var val = $("#indexPathType").val();
			$("input[name='indexPathType']").each(function(){
				if(val==$(this).val())
		        	$(this).attr("checked", true);
		    });
			$("input[name='indexPathType']").click(function(){
				$("#indexPathType").val($(this).val());
			});
			
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
		<li><a href="${ctx_a}/indexCategory/list">索引分类列表</a></li>
		<li class="active"><a href="${ctx_a}/indexCategory/form?id=${indexCategory.id}">${not empty indexCategory.id?'修改':'添加'}索引分类</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="indexCategory" action="${ctx_a}/indexCategory/save" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">索引分类:</label>
			<div class="controls">
				<input id="indexInfoType" name="indexCategory.indexInfoType" value=${indexCategory.indexInfoType}>
				<input id="id" type="hidden"  name="indexCategory.id" value=${indexCategory.id}>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">路径类型:</label>
			<div class="controls">
				<input id="indexPathType" type="hidden" name="indexCategory.indexPathType" value="${indexCategory.indexPathType}">
				<c:forEach items="${indexPathTypes}" var="indexPathType">
				<input type="radio" name="indexPathType" value="${indexPathType.key}" />${indexPathType.value}
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">路径:</label>
			<div class="controls">
				<input id="indexPath" name="indexCategory.indexPath" value=${indexCategory.indexPath}>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>
