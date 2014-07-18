<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>添加数据源</title>
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
			selectDownMenu("address","addressIds","canvasDiv",{demo:true});
			var val = $("#databaseType").val();
			$("input[name='databaseType']").each(function(){
				if(val==$(this).val())
		        	$(this).attr("checked", true);
		    });
			$("input[name='databaseType']").click(function(){
				$("#databaseType").val($(this).val());
			});
			
			$("#btn").click(function(){
				var dialog = art.dialog({id: 'N3690',title: false});

				// jQuery ajax   
				$.ajax({
				    url: 'list',
				    success: function (data) {
				        dialog.content(data);
				    },
				    cache: false
				});
			});
			
			$("#btnSubmit").click(function(){
				$("#inputForm").ajaxSubmit({
	                type: 'post',
	                url: 'save' ,
	                success: function(data){
	                	if(data.msg==0){
	                		art.dialog.alert('保存成功！');
		                    $( "#inputForm").resetForm();
	                	}else
	                		art.dialog.alert('保存失败！');
	                		
	                },
	                error: function(XmlHttpRequest, textStatus, errorThrown){
	                	art.dialog.alert('网络不通，保存失败！');
	                }
	            });
			});
			
			$("#btntest").click(function(){
				$("#inputForm").ajaxSubmit({
	                type: 'post',
	                url: 'databaseTest' ,
	                success: function(data){
	                	if(data.msg==0)
	                		art.dialog.alert('测试连接成功！');
	                	else
	                		art.dialog.alert( '测试连接失败！');
	                },
	                error: function(XmlHttpRequest, textStatus, errorThrown){
	                	art.dialog.alert( '网络不通，测试连接失败！');
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
		<li><a href="${ctx_a}/database/list">数据源列表</a></li>
		<li class="active"><a href="${ctx_a}/database/form?id=${database.id}">${not empty database.id?'修改':'添加'}数据源</a></li>
	</ul><br/>
	<form id="inputForm" action="${ctx_a}/database/save" method="post" class="form-horizontal">
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="数据库名">
				数据库名必须为真实数据库名，例如,填写“easyssh”，在数据库必须存在该库，如下图：<br/>
				<img src="${ctxStatic}/images/tip/databaseName.ico"></img>
				</div>
				<label class="control-label">数据库名:</label>
			</div>
			<div class="controls">
				<input id="databaseName" name="database.databaseName" value="${database.databaseName}">
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="数据库类型">
				目前数据库类型支持：oracle、sqlserver、mysql三种类型：<br/>
				</div>
				<label class="control-label">数据库类型:</label>
			</div>
			<div class="controls">
				<input id="databaseType" type="hidden" name="database.databaseType" value="${database.databaseType}">
				<c:forEach items="${databaseTypes}" var="dt">
				<input type="radio" name="databaseType" value="${dt.key}" />${dt.value}
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="ip地址">
				ip地址必须为能够访问的ip地址，例如：127.0.0.1<br/>
				如果是sqlserver存在实例名，则填写"ip地址/实例名"，例如：127.0.0.1/sql2008
				</div>
				<label class="control-label">ip地址:</label>
			</div>
			<div class="controls">
				<input id="ip" name="database.ip" value="${database.ip}">
				<input type="hidden" id="id" name="database.id" value="${database.id}">
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="端口">
				非必填。默认端口：<br/>
				mysql数据库:3306<br/>
				oracle数据库:1521<br/>
				sqlserver数据库:1433<br/>
				</div>
				<label class="control-label">端口:</label>
			</div>
			<div class="controls">
				<input id="port" name="database.port" value="${database.port}">
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="用户名">
				数据库的登入用户名，例如：“root”，“sa”，“sysdba”等。
				</div>
				<label class="control-label">用户名:</label>
			</div>
			<div class="controls">
				<input id="user" name="database.user" value="${database.user}">
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="密码">
				数据库的登入密码，例如：“11111”等。
				</div>
				<label class="control-label">密码:</label>
			</div>
			<div class="controls">
				<input id="password" name="database.password" value="${database.password}">
			</div>
		</div>
		<div class="form-actions">
			<input id="btntest" class="btn btn-primary" type="button" value="测试"/>&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<input type="text" name="address" id="address" >
		<input type="hidden" name="addressIds" id="addressIds" >
		<div id="canvasDiv" demo="true"></div>
		<input id="btn" class="btn btn-primary" type="button" value="示例"/>&nbsp;
	</form>
</body>
</html>