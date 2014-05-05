<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>索引设置管理</title>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var num_1 = 0 ;
			var initfieldstr = '<option value="">请选择</option>';
			function addAll(){
				$("#tableName").append('<option value="">请选择</option>');
		    	$("#keyField").append('<option value="">请选择</option>');
		    	$("#defaultTitleFieldName").append('<option value="">请选择</option>');
		    	$("#defaultResumeFieldName").append('<option value="">请选择</option>');
			}
			
			function clearAll(){
				$("#tableName").empty();
		    	$("#keyField").empty();
		    	$("#defaultTitleFieldName").empty();
		    	$("#defaultResumeFieldName").empty();
			}
			
			$.ajax({
			    url: 'indexPathes',
			    success: function (data) {
			    	var len = data.indexPathes.length;
			    	$("#indexPathCate").append('<option value="">请选择</option>');
			    	for(var i = 0;i<len;i++){
			    		var path_name = data.indexPathes[i].index_name.split(";");
			    		$("#indexPathCate").append('<option value="'+path_name[0]+'">'+path_name[1]+'</option>');
			    	}
			    	
			    	if($("#indexPath").val() !=null && $("#indexPath").val() !=""){
			    		$("#indexPathCate").val($("#indexPath").val());
			    		changePathCate();
			    	}
			    },
			    cache: false
			});
			
			$.ajax({
			    url: 'databases',
			    success: function (data) {
			    	var len = data.dbs.length;
			    	clearAll();
			    	addAll();
			    	$("#database").append('<option value="">请选择</option>');
			    	for(var i = 0;i<len;i++){
			    		var id_name = data.dbs[i].dbname.split(";");
			    		$("#database").append('<option value="'+id_name[0]+'">'+id_name[1]+'</option>');
			    	}
			    	
			    	if($("#database_id").val() !=null && $("#database_id").val() !=""){
			    		$("#database").val($("#database_id").val());
				    	changetables();
				    	if($("#database_rmDuplicate").val()==0)
				    		$("#rmDuplicate_0").attr("checked",true);
				    	else if($("#database_rmDuplicate").val()==1)
				    		$("#rmDuplicate_1").attr("checked",true);
				    	
				    	if($("#database_style").val()==0)
				    		$("#style_0").attr("checked",true);
				    	else if($("#database_style").val()==1)
				    		$("#style_1").attr("checked",true);
				    	
				    	if($("#database_isInit").val()==0)
				    		$("#isInit_0").attr("checked",true);
				    	else if($("#database_isInit").val()==1)
				    		$("#isInit_1").attr("checked",true);
				    	
				    	if($("#database_isOn").val()==0)
				    		$("#isOn_0").attr("checked",true);
				    	else if($("#database_isOn").val()==1)
				    		$("#isOn_1").attr("checked",true);
				    	
				    	if($("#database_indexOperatorType").val()==0)
				    		$("#indexOperatorType_0").attr("checked",true);
				    	else if($("#database_indexOperatorType").val()==1)
				    		$("#indexOperatorType_1").attr("checked",true);
			    	}
			    	
			    },
			    cache: false
			});
			
			$.ajax({
			    url: 'initFields',
			    success: function (data) {
			    	var len = data.initFields.length;
			    	for(var i = 0;i<len;i++){
			    		initfieldstr +='<option value="'+data.initFields[i].field+'">'+data.initFields[i].field+'</option>';
			    	}
			    },
			    cache: false
			});
			
			var changetables = function(){
				var id=$("#database").children('option:selected').val();
				$.ajax({
				    url: 'tables?id='+id,
				    success: function (_data) {
				    	var len = _data.tables.length;
				    	clearAll();
				    	addAll();
				    	for(var i = 0;i<len;i++){
				    		$("#tableName").append('<option value="'+_data.tables[i].table_name+'">'+_data.tables[i].table_name+'</option>');
				    	}
				    	if($("#database_tableName").val() !=null && $("#database_tableName").val() !=""){
					    	$("#tableName").val($("#database_tableName").val());
					    	changeTableName();	
				    	}
				    },
				    cache: false
				});
			};
			
			var changeTableName = function(){
				var param=$("#tableName").children('option:selected').val();//这就是selected的值 
				$.ajax({
				    url: 'fields?table='+param+'&id='+$("#database").val(),
				    success: function (_data) {
				    	var len = _data.fields.length;
				    	$("#keyField").empty();
				    	$("#keyField").append('<option value="">请选择</option>');
				    	for(var i = 0;i<len;i++){
				    		$("#keyField").append('<option value="'+_data.fields[i].COLUMN_NAME+'">'+_data.fields[i].COLUMN_NAME+'</option>');
				    	}
				    	
				    	$("#defaultTitleFieldName").empty();
				    	$("#defaultTitleFieldName").append('<option value="">请选择</option>');
				    	for(var i = 0;i<len;i++){
				    		$("#defaultTitleFieldName").append('<option value="'+_data.fields[i].COLUMN_NAME+'">'+_data.fields[i].COLUMN_NAME+'</option>');
				    	}
				    	
				    	$("#defaultResumeFieldName").empty();
				    	$("#defaultResumeFieldName").append('<option value="">请选择</option>');
				    	for(var i = 0;i<len;i++){
				    		$("#defaultResumeFieldName").append('<option value="'+_data.fields[i].COLUMN_NAME+'">'+_data.fields[i].COLUMN_NAME+'</option>');
				    	}
				    	if($("#database_keyField").val() !=null && $("#database_keyField").val() !=""){
					    	$("#keyField").val($("#database_keyField").val());
				    	}
				    	if($("#database_defaultTitleFieldName").val() !=null && $("#database_defaultTitleFieldName").val() !=""){
					    	$("#defaultTitleFieldName").val($("#database_defaultTitleFieldName").val());
				    	}
				    	if($("#database_defaultResumeFieldName").val() !=null && $("#database_defaultResumeFieldName").val() !=""){
					    	$("#defaultResumeFieldName").val($("#database_defaultResumeFieldName").val());
				    	}
				    },
				    cache: false
				});
			};
			
			var changePathCate = function(){
				var param=$("#indexPathCate").children('option:selected').val();//这就是selected的值 
				if(param!=null&&param!="");
				$.ajax({
				    url: 'indexPathType?id='+param,
				    success: function (_data) {
				    	if("IMAGE"==_data.pathtype){
				    		$(".c_1").css("display", "none");
				    		$(".c_2").css("display", "block");
				    	}else{
				    		$(".c_1").css("display", "block");
				    		$(".c_2").css("display", "none");
				    	}
				    },
				    cache: false
				});
			}
			
			$('#database').change(function(){ 
				changetables();
			});
			
			$('#tableName').change(function(){ 
				changeTableName();
			});
			
			$('#indexPathCate').change(function(){ 
				changePathCate();
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
			
			$("#addJustScheduleBody").click(function(){
				var tabid=document.getElementById("justScheduleBody");
				if(num_1<tabid.childNodes.length)
					num_1 = tabid.childNodes.length;
				$("#justScheduleBody").append('<tr id="_f'+num_1+'">'+
						'<td></input><input name="justSchedule.expression"></input></td>'+
						'<td>'+
						'	<input type="button" onclick="del_1(\'_f'+num_1+'\')" value="删除"></input>'+
						'</td>'+
					'</tr>');
				num_1++;
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
		
		function del_1(id){
			var trnode=document.getElementById(id); 
			trnode.parentNode.removeChild(trnode);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx_a}/rDatabaseIndex/list">索引设置列表</a></li>
		<li class="active"><a href="${ctx_a}/rDatabaseIndex/form?id=${rDatabaseIndex.id}">${not empty rDatabaseIndex.id?'修改':'添加'}索引设置</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="rDatabaseIndex" action="${ctx_a}/rDatabaseIndex/save" method="post" class="form-horizontal">
	<div style="float:left;width:40%;background-color:white">
		<div class="control-group">
			<label class="control-label">是否启用:</label>
			<div class="controls">
				<input type="hidden" id="database_isOn" value="${rDatabaseIndex.isOn}" />
				<input type="radio" id="isOn_0" name="rDatabaseIndex.isOn" value="0" checked="true"/>启用
				<input type="radio" id="isOn_1" name="rDatabaseIndex.isOn" value="1" />禁止
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">索引风格:</label>
			<div class="controls">
				<input type="hidden" id="database_style" value="${rDatabaseIndex.style}" />
				<input type="radio" id="style_0" name="rDatabaseIndex.style" value="0" checked="true"/>rest风格
				<input type="radio" id="style_1" name="rDatabaseIndex.style" value="1" />复合风格
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否已初始化:</label>
			<div class="controls">
				<input type="hidden" id="database_isInit" value="${rDatabaseIndex.isInit}" />
				<input type="radio" id="isInit_0" name="rDatabaseIndex.isInit" value="0" checked="true"/>否
				<input type="radio" id="isInit_1" name="rDatabaseIndex.isInit" value="1" />是
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">索引分类:</label>
			<div class="controls">
				<input type="hidden" id="indexPath" value="${rDatabaseIndex.indexPath_id}"></input>
				<select id="indexPathCate" name="rDatabaseIndex.indexPath_id"></select> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据源:</label>
			<div class="controls">
				<!-- <input type="text"  id="address" >
				<input type="hidden" name="rDatabaseIndex.database_id" id="addressIds" >
				<div id="canvasDiv"></div> -->
				<input type="hidden" id="id" name="rDatabaseIndex.id" value="${rDatabaseIndex.id}"></input>
				<input type="hidden" id="database_id" value="${rDatabaseIndex.database_id}"></input>
				<select id="database" name="rDatabaseIndex.database_id"></select> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表:</label>
			<div class="controls">
				<input type="hidden" id="database_tableName" value="${rDatabaseIndex.tableName}"></input>
				<select id="tableName" name="rDatabaseIndex.tableName"></select> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主键:</label>
			<div class="controls">
				<input type="hidden" id="database_keyField" value="${rDatabaseIndex.keyField}"></input>
				<select id="keyField" name="rDatabaseIndex.keyField"></select> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<input type="hidden" id="database_defaultTitleFieldName" value="${rDatabaseIndex.defaultTitleFieldName}"></input>
				<select id="defaultTitleFieldName" name="rDatabaseIndex.defaultTitleFieldName"></select> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">摘要:</label>
			<div class="controls">
				<input type="hidden" id="database_defaultResumeFieldName" value="${rDatabaseIndex.defaultResumeFieldName}"></input>
				<select id="defaultResumeFieldName" name="rDatabaseIndex.defaultResumeFieldName"></select> 
			</div>
		</div>
		
		<div class="control-group c_2">
			<label class="control-label">图片字段:</label>
			<div class="controls" >
				 <input  type="text" name="rDatabaseIndex.binaryField" id="binaryField" value="${rDatabaseIndex.binaryField}"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">查询条件:</label>
			<div class="controls" >
				 <input  type="text" name="rDatabaseIndex.condtion" id="condtion" value="${rDatabaseIndex.condtion}"/>
			</div>
		</div>
		
		<div class="control-group c_1">
			<label class="control-label">是否去重复:</label>
			<div class="controls">
				<input type="hidden" id="database_rmDuplicate" value="${rDatabaseIndex.rmDuplicate}" />
				<input type="radio" id="rmDuplicate_0" name="rDatabaseIndex.rmDuplicate" value="0" checked="true"/>是
				<input type="radio" id="rmDuplicate_1" name="rDatabaseIndex.rmDuplicate" value="1" />否
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">操作类型:</label>
			<div class="controls">
				<input type="hidden" id="database_indexOperatorType" value="${rDatabaseIndex.indexOperatorType}" />
				<input type="radio" id="indexOperatorType_0" name="rDatabaseIndex.indexOperatorType" value="0" checked="true" />增加
				<input type="radio" id="indexOperatorType_1" name="rDatabaseIndex.indexOperatorType" value="1" />更新
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">触发器表:</label>
			<div class="controls">
				 <input type="text" name="rDatabaseIndex.indexTriggerRecord" id="indexTriggerRecord" value="${rDatabaseIndex.indexTriggerRecord}">
			</div>
		</div>
		<div class="control-group c_1">
			<label class="control-label">拦截器:</label>
			<div class="controls">
				 <input type="text" name="rDatabaseIndex.databaseRecordInterceptor" id="databaseRecordInterceptor" value="${rDatabaseIndex.databaseRecordInterceptor}">
			</div>
		</div>
		</div>
		<div style="float:right;width:60%">
			<div style="height:200px;background-color:white">
			    <div style="height:30px;background-color:#D6DFF6">
			    	<b>字段映射：</b>
			    </div>
			    <div style="overflow-y:scroll;height:170px">
					<table id="fieldMapperTable" class="table table-striped table-bordered table-condensed">
						<thead><tr><th>索引段字</th><th>数据库字段</th></tr></thead>
						<tbody id="filedMapperBody">
						<c:choose>
							<c:when test='${empty rDatabaseIndex.id}'>
								<c:forEach items="${initFields}" var="initFields" >
									<tr>
										<td>${initFields.field}<input name="filedMapper.indexField" value="${initFields.field}" type="hidden"></input></td>
										<td><input name="filedMapper.sqlField" value=""></input></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${rDatabaseIndex.filedMapperLsit}" var="filedMapper" >
									<tr>
										<td>${filedMapper.indexField}<input name="filedMapper.indexField" value="${filedMapper.indexField}" type="hidden"></input></td>
										<td><input name="filedMapper.sqlField" value="${filedMapper.sqlField}"></input></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
				</div>
			</div>
			<div style="height:200px;background-color:white">
			    <div style="height:30px;background-color:#D6DFF6">
			    	<b>特殊字段映射：</b>
			    </div>
			    <div style="overflow-y:scroll;height:170px">
					<table id="filedSpecialMapperTable" class="table table-striped table-bordered table-condensed">
						<thead><tr><th>特殊处理类型</th><th>数据库字段</th></tr></thead>
						<tbody id="filedSpecialMapperBody">
						<c:choose>
							<c:when test='${empty rDatabaseIndex.id}'>
									<tr>
										<td>RMHTML<input type="hidden" name="filedSpecialMapper.specialType" value="1" ></input></td>
										<td><input name="filedSpecialMapper.sqlField" value=""></input></td>
									</tr>
									<tr>
										<td>BLOB<input type="hidden" name="filedSpecialMapper.specialType" value="2" ></input></td>
										<td><input name="filedSpecialMapper.sqlField" value=""></input></td>
									</tr>
									<tr>
										<td>CLOB<input type="hidden" name="filedSpecialMapper.specialType" value="3" ></input></td>
										<td><input name="filedSpecialMapper.sqlField" value=""></input></td>
									</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${rDatabaseIndex.filedSpecialMapperLsit}" var="filedSpecialMapper" >
									<tr>
										<td>
											<c:choose>
												<c:when test='${filedSpecialMapper.specialType==1}'>RMHTML</c:when>
												<c:when test='${filedSpecialMapper.specialType==2}'>BLOB</c:when>
												<c:when test='${filedSpecialMapper.specialType==3}'>CLOB</c:when>
												<c:otherwise>PLAIN</c:otherwise>
											</c:choose>
											<input type="hidden" name="filedSpecialMapper.specialType" value="${filedSpecialMapper.specialType}" ></input>
										</td>
										<td><input name="filedSpecialMapper.sqlField" value="${filedSpecialMapper.sqlField}"></input></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
				</div>
			</div>
			<div style="height:200px;background-color:white">
			    <div style="height:30px;background-color:#D6DFF6">
			    	<b>任务调度：</b>
			    	<div style="float:right">
			    		<input type="button" class="btn btn-primary" id="addJustScheduleBody"  value="添加" ></input>
			    	</div>
			    </div>
			    <div style="overflow-y:scroll;height:170px">
			    	<table id="justScheduleTable" class="table table-striped table-bordered table-condensed">
						<thead><tr><th>表达式</th><th>操作</th></tr></thead>
						<tbody id="justScheduleBody">
								<c:forEach items="${rDatabaseIndex.justScheduleList}" var="justSchedule" varStatus="status">
									<tr id="_f${status.index}">
										<td><inputt type="hidden" name="justSchedule.id" value="${justSchedule.id}"><inputt type="hidden" name="justSchedule.scheduleName" value="${justSchedule.scheduleName}"></input><input name="justSchedule.expression" value="${justSchedule.expression}"></input></td>
										<td>
											<input type="button" onclick="del_1('_f${status.index}')" value="删除"></input>
										</td>
									</tr>
								</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="form-actions" style="clear:both">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>
