<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>添加索引字段</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/head.jsp" %>
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
	<style type="text/css">
      div#item_1 { position: absolute; }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx_a}/initField/list">字段列表</a></li>
		<li class="active"><a href="${ctx_a}/initField/form?id=${initField.id}">${not empty initField.id?'修改':'添加'}字段</a></li>
	</ul><br/>
	<form id="inputForm" action="${ctx_a}/initField/save" method="post" class="form-horizontal">
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="字段名">必须填写英文，例如“CREATETIMNE”、“PAGE_URL”、“REMARK”。<br/>
				初始字段在每个索引表中都会存在，添加字段后，可以在“索引设置”中查看到，如下图：
				<img src="${ctxStatic}/images/tip/field.ico"></img>
				</div>
				<label class="control-label">字段名:</label>
			</div>
			<div class="controls">
				<input id="field" name="initField.field" value="${initField.field}">
				<input id="idd" type="hidden" name="initField.id" value="${initField.id}">
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="默认值">非必填。<br/>当初始字段对应数据库字段为空时，设置一个默认值。
				</div>
				<label class="control-label">默认值:</label>
			</div>
			<div class="controls">
				<input id="defaultValue" name="initField.defaultValue" value="${initField.defaultValue}">
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="字段类型">字段类型,目前常用的有3种类型，如下:<br/>
				KEYWORD：不分词，存储，适用于数据库主键字段、分类，用于“精确查询”，例如：用户主键字段，用户名，信息分类。<br/>
				CONTENT：分词，存储。适用于内容、正文字段，用于全文检索。例如，标题，摘要、正文。<br/>
				NUMBER：不分词，数据类型，存储。用于比较大小，例如：时间字段
				</div>
				<label class="control-label">字段类型:</label>
			</div>
			<div class="controls">
				<input id="fieldType" type="hidden" name="initField.fieldType" value="${initField.fieldType}">
				<c:forEach items="${itemTypes}" var="itemTypes">
				<input type="radio" name="fieldType" value="${itemTypes.value}" />${itemTypes.value}
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="字段描述">非必填。<br/>对初始字段的一个说明，例如：“时间字段”、“跳转地址”。
				</div>
				<label class="control-label">字段描述:</label>
			</div>
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