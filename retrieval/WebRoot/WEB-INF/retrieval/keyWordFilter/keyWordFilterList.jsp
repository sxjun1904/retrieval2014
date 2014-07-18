<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>搜索过滤管理</title>
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
			$("#btnWords").click(function(){
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
		<li class="active"><a href="${ctx_a}/keyWordFilter/list">搜索过滤</a></li>
	</ul>
	<form id="inputForm" modelAttribute="iKWords" action="${ctx_a}/iKWords/save" method="post" class="form-horizontal">
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="关键字过滤">
				添加搜索时要过滤的关键字，词与词之间用“|”隔开，例如不许搜索“色情”和“反共产党”，添加如下图：<br/>
				<img src="${ctxStatic}/images/tip/filter.ico"></img>
				</div>
				<label class="control-label">关键字过滤:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="ctx_a"  value="${ctx_a}"/>
				<input id="keywords" name="keyWordFilter.keywords" value="${keyWordFilter.keywords}" style="width:500px"/>
				<input id="btnWords" class="btn btn-primary" type="button" value="保存"/>
			</div>
		</div>
	</form>
</body>
</html>
