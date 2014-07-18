<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>索引分类管理</title>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/jquery-Tooltip/stylesheets/jquery.tooltip/jquery.tooltip.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-Tooltip/javascripts/jquery.tooltip.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("div.item").tooltip();
			$(".control-label").mouseover(function(){
				$(this).css({"color":"#335A9F","font-weight":"bold"});
			});
			$(".control-label").mouseout(function(){
				$(this).css({"color":"black","font-weight":"normal"});
			});
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
	<style type="text/css">
      div#item_1 { position: absolute; }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx_a}/indexCategory/list">索引分类列表</a></li>
		<li class="active"><a href="${ctx_a}/indexCategory/form?id=${indexCategory.id}">${not empty indexCategory.id?'修改':'添加'}索引分类</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="indexCategory" action="${ctx_a}/indexCategory/save" method="post" class="form-horizontal">
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="索引分类">一般填写中文，比如“新闻”、“图片”、“体育”。<br/>或为英文，例如“news”、“picture”、“sport”等<br/>
				展示位置如下图：<br/>
				<img src="${ctxStatic}/images/tip/indexInfoType.ico"></img>
				</div>
				<label class="control-label">索引分类:</label>
			</div>
			<div class="controls">
					<input id="indexInfoType" name="indexCategory.indexInfoType" value=${indexCategory.indexInfoType}>
					<input id="id" type="hidden"  name="indexCategory.id" value=${indexCategory.id}>
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="路径类型">路径类型分为3种：<br/>DB:数据库类型；IMAGE:图片类型；FILE:文件类型<br/>
				选择类型主要用来区分：1.索引。2.索引文件夹。如下图：
				<img src="${ctxStatic}/images/tip/indexPathType.ico"></img>
				</div>
				<label class="control-label">路径类型:</label>
			</div>
			<div class="controls">
				<input id="indexPathType" type="hidden" name="indexCategory.indexPathType" value="${indexCategory.indexPathType}">
				<c:forEach items="${indexPathTypes}" var="indexPathType">
				<input type="radio" name="indexPathType" value="${indexPathType.key}" />${indexPathType.value}
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="路径">路径一般为英文,例如：“SPORTS”,“INFO”,“IMAGE”等<br/>
				多层路径配置时加斜杠，例如：DB/NEWS，表示在DB文件夹下创建一个NEWS的文件夹来存放索引。创建文件夹如下图：<br/>
				<img src="${ctxStatic}/images/tip/indexPath.ico"></img><br/>
				"_1"表示第一个文件夹，当索引达到指定大小，会重新创建一个新的文件夹"_2","_3"...依次类推。
				</div>
				<label class="control-label">路径:</label>
			</div>
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
