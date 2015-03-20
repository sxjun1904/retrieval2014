<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>索引设置管理</title>
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
			    url: 'indexPathes?t=2',
			    success: function (data) {
			    	var len = data.indexPathes.length;
			    	$("#indexPathCate").append('<option value="">请选择</option>');
			    	for(var i = 0;i<len;i++){
			    		var path_name = data.indexPathes[i].index_name.split(";");
			    		$("#indexPathCate").append('<option value="'+path_name[0]+'">'+path_name[1]+'</option>');
			    	}
			    	
			    	if($("#indexPath").val() !=null && $("#indexPath").val() !=""){
			    		$("#indexPathCate").val($("#indexPath").val());
			    		//changePathCate();
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
				    	$("#tableName").append('<option value="'+$("#database_tableName").val()+'">'+$("#database_tableName").val()+'</option>');
				    	$("#tableName").val($("#database_tableName").val());
				    	$("#keyField").append('<option value="'+$("#database_keyField").val()+'">'+$("#database_keyField").val()+'</option>');
				    	$("#keyField").val($("#database_keyField").val());
				    	$("#defaultTitleFieldName").append('<option value="'+$("#database_defaultTitleFieldName").val()+'">'+$("#database_defaultTitleFieldName").val()+'</option>');
				    	$("#defaultTitleFieldName").val($("#database_defaultTitleFieldName").val());
				    	$("#defaultResumeFieldName").append('<option value="'+$("#database_defaultResumeFieldName").val()+'">'+$("#database_defaultResumeFieldName").val()+'</option>');
				    	$("#defaultResumeFieldName").val($("#database_defaultResumeFieldName").val());
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
				    	else if($("#database_isInit").val()==2)
				    		$("#isInit_2").attr("checked",true);
				    	
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
				    		$("#tableName").append('<option value="'+_data.tables[i].TABLE_NAME+'">'+_data.tables[i].TABLE_NAME+'</option>');
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
			
			/* var changePathCate = function(){
				var param=$("#indexPathCate").children('option:selected').val();//这就是selected的值 
				alert(param);
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
			} */
			
			$('#database').change(function(){ 
				changetables();
			});
			
			$('#tableName').change(function(){ 
				changeTableName();
			});
			
			/* $('#indexPathCate').change(function(){ 
				changePathCate();
			}); */
			
			
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
	<style type="text/css">
      div#item_1 { position: absolute; }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx_a}/rDatabaseIndex/list">索引设置列表</a></li>
		<li><a href="${ctx_a}/rDatabaseIndex/form?id=${rDatabaseIndex.id}">${not empty rDatabaseIndex.id?'修改':'添加'}索引设置</a></li>
		<li class="active"><a href="${ctx_a}/rDatabaseIndex/imageForm">图片索引添加</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="rDatabaseIndex" action="${ctx_a}/rDatabaseIndex/save" method="post" class="form-horizontal">
	<div style="float:left;width:40%;background-color:white">
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="是否启用">
				是：表示该索引设置起作用，可以初始化、更新、删除索引<br/>
				否：表示该索引设置不起作用，不能初始化更新、删除索引<br/>
				</div>
				<label class="control-label">是否启用:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_isOn" value="${rDatabaseIndex.isOn}" />
				<input type="radio" id="isOn_0" name="rDatabaseIndex.isOn" value="0" checked="true"/>启用
				<input type="radio" id="isOn_1" name="rDatabaseIndex.isOn" value="1" />禁止
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="风格">
				rest风格:定时任务不起作用<br/>
				复合风格:可支持"rest分格"和"定时任务"共同作用<br/>
				<img src="${ctxStatic}/images/tip/style.ico"></img>
				</div>
				<label class="control-label">索引风格:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_style" value="${rDatabaseIndex.style}" />
				<input type="radio" id="style_1" name="rDatabaseIndex.style" value="1" checked="true"/>复合风格
				<input type="radio" id="style_0" name="rDatabaseIndex.style" value="0" />rest风格
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="是否已初始化">
				该字段主要在索引出问题时用来重建索引，（注：无需配置）<br/>
				否：0，表示未初始化<br/>
				是：1，表示已初始化<br/>
				整理：1，表示正在初始化、更新、删除、整理索引<br/>
				</div>
				<label class="control-label">是否已初始化:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_isInit" value="${rDatabaseIndex.isInit}" />
				<input type="radio" id="isInit_0" name="rDatabaseIndex.isInit" value="0" checked="true"/>否
				<input type="radio" id="isInit_1" name="rDatabaseIndex.isInit" value="1" />是
				<input type="radio" id="isInit_2" name="rDatabaseIndex.isInit" value="2" />整理
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="索引分类">
				选择一个索引对应的分类,选择后，搜索出来的分类展示在如下位置：<br/>
				<img src="${ctxStatic}/images/tip/indexPath_id.ico"></img>
				<img src="${ctxStatic}/images/tip/indexInfoType.ico"></img>
				</div>
				<label class="control-label">索引分类:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="indexPath" value="${rDatabaseIndex.indexPath_id}"></input>
				<select id="indexPathCate" name="rDatabaseIndex.indexPath_id" class="combobox"></select> 
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="数据源">
				选择一个配置的数据源，选择数据源之后才能配置表、主键、标题等字段，例如"easyssh"<br/>
				</div>
				<label class="control-label">数据源:</label>
			</div>
			<div class="controls">
				<!-- <input type="text"  id="address" >
				<input type="hidden" name="rDatabaseIndex.database_id" id="addressIds" >
				<div id="canvasDiv"></div> -->
				<input type="hidden" id="id" name="rDatabaseIndex.id" value="${rDatabaseIndex.id}"></input>
				<input type="hidden" id="database_id" value="${rDatabaseIndex.database_id}"></input>
				<select id="database" name="rDatabaseIndex.database_id" class="combobox"></select> 
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="表">
				选择一个炫耀检索信息的表,例如：“test_web”<br/>
				该表中必须要包含：主键、标题、摘要、创建时间、跳转地址字段。如果没有对应字段，可以通过配置视图。字段对应展示如下图：<br/>
				<img src="${ctxStatic}/images/tip/table.ico" style="height:80px"></img>
				</div>
				<label class="control-label">表:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_tableName" value="${rDatabaseIndex.tableName}"></input>
				<select id="tableName" name="rDatabaseIndex.tableName" class="combobox"></select> 
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="主键">
				选择一个表的主键，最好是选一个UUID的作为主键,能保证绝对唯一，例如:“infoid”、“rowguid”<br/>
				</div>
				<label class="control-label">主键:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_keyField" value="${rDatabaseIndex.keyField}"></input>
				<select id="keyField" name="rDatabaseIndex.keyField" class="combobox"></select> 
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="标题">
				选择一个表的标题字段，例如：“title”,显示如下图：<br/>
				<img src="${ctxStatic}/images/tip/table.ico" style="height:80px"></img>
				</div>
				<label class="control-label">标题:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_defaultTitleFieldName" value="${rDatabaseIndex.defaultTitleFieldName}"></input>
				<select id="defaultTitleFieldName" name="rDatabaseIndex.defaultTitleFieldName" class="combobox"></select> 
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="摘要">
				选择一个表的摘要字段，例如：“resume”,显示如下图：<br/>
				<img src="${ctxStatic}/images/tip/table.ico" style="height:80px"></img>
				</div>
				<label class="control-label">摘要:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_defaultResumeFieldName" value="${rDatabaseIndex.defaultResumeFieldName}"></input>
				<select id="defaultResumeFieldName" name="rDatabaseIndex.defaultResumeFieldName" class="combobox"></select> 
			</div>
		</div>
		
		<div class="control-group c_2">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="图片字段">
				该字段必须为二进制图片字段，选择后会读取生成缩略图在工程的img路径下：<br/>
				</div>
				<label class="control-label">图片字段:</label>
			</div>
			<div class="controls" >
				 <input  type="text" name="rDatabaseIndex.binaryField" id="binaryField" value="${rDatabaseIndex.binaryField}"/>
			</div>
		</div>
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="查询条件">
				查询数据库时删选条件，例如：“and status='1' and flag='0'”：<br/>
				</div>
				<label class="control-label">查询条件:</label>
			</div>
			<div class="controls" >
				 <input  type="text" name="rDatabaseIndex.condtion" id="condtion" value="${rDatabaseIndex.condtion}"/>
			</div>
		</div>
		
		<%-- <div class="control-group c_1">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="是否去重复">
				去除索引中的重复字段，默认为“是”，无需更改。<br/>
				</div>
				<label class="control-label">是否去重复:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_rmDuplicate" value="${rDatabaseIndex.rmDuplicate}" />
				<input type="radio" id="rmDuplicate_0" name="rDatabaseIndex.rmDuplicate" value="0" checked="true"/>是
				<input type="radio" id="rmDuplicate_1" name="rDatabaseIndex.rmDuplicate" value="1" />否
			</div>
		</div> --%>
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="操作类型">
				操作类型有两种:更新、增加。默认为更新<br/>
				更新：如果数据库中增加则增加一条索引记录，如果数据库更新则更新索引记录，如果数据库删除则删除一条索引记录。(绝大多数采用此方式，例如：帖子A的内容发生了变化，全文检索会更新变化的内容)<br/>
				新增：如果数据库中无伦增加、删除、更新，都会生成一条索引。（例如：帖子A的内容发生了变化，会增加一条索引记录，这样就会搜索到多条相同记录）
				</div>
				<label class="control-label">操作类型:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="database_indexOperatorType" value="${rDatabaseIndex.indexOperatorType}" />
				<input type="radio" id="indexOperatorType_1" name="rDatabaseIndex.indexOperatorType" value="1" checked="true"/>更新
				<input type="radio" id="indexOperatorType_0" name="rDatabaseIndex.indexOperatorType" value="0"  />增加
			</div>
		</div>
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="触发器表">
				非必填。<br/>
				触发器表作用：根据此表来新增、修改、删除对应的记录，常用于上面选择的表为视图的情况。<br/>
				如果不填，则默认为上面所选的表，例如：“easyssh”。<br/>
				如果填，必选要选一张主表作为触发器表。</br>
				</div>
				<label class="control-label">触发器表:</label>
			</div>
			<div class="controls">
				 <input type="text" name="rDatabaseIndex.indexTriggerRecord" id="indexTriggerRecord" value="${rDatabaseIndex.indexTriggerRecord}">
			</div>
		</div>
		<%-- <div class="control-group c_1">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="拦截器">
				非必填。<br/>
				拦截器作用：主要用来对生成索引前的数据做一些修改<br/>
				不填，采用默认的拦截器即可。<br/>
				</div>
				<label class="control-label">拦截器:</label>
			</div>
			<div class="controls">
				 <input type="text" name="rDatabaseIndex.databaseRecordInterceptor" id="databaseRecordInterceptor" value="${rDatabaseIndex.databaseRecordInterceptor}">
			</div>
		</div> --%>
		</div>
		<div style="float:right;width:60%">
			<div style="height:200px;background-color:white">
			    <div style="height:30px;background-color:#D6DFF6">
				    <div id="item_1" class="item">
						<div class="tooltip_description" style="display:none" title="字段映射">
						配置索引字段与数据库字段的对应关系，用分号“;”隔开<br/>
						索引字段（初始字段）：即全文检索中的字段。<br/>
						数据库字段：即数据库中表的字段。<br/>
						配置后会将表的字段转成索引字段，例如：表A的url字段，对应到索引的page_url字段。<br/>
						生成索引时，url的字段值就会生成到索引的page_url字段中
						</div>
				    	<b>字段映射：</b>
			    	</div>
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
			    	 <div id="item_1" class="item">
						<div class="tooltip_description" style="display:none" title="特殊字段映射">
						特殊字段映射有三种类型：包含html标签，clob字段，blob字段，字段用分号“;”隔开<br/>
						包含html标签（RMHTML）：填在此处的字段会去掉其中的html标签例如：“resume;content”<br/>
						BLOB：该字段会被当做流来读取。例如：“resume”<br/>
						CLOB：该字段会被当做字节流来读取。例如：“content”<br/>
						</div>
			    		<b>特殊字段映射：</b>
			    	</div>
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
			    	 <div id="item_1" class="item">
						<div class="tooltip_description" style="display:none" title="任务调度">
						任务调度分为两种模式：simple模式和corn模式<br/>
						<b>simple模式</b>：“s”表示秒，“m”表示分，“h”表示小时。<br/>
						20s:表示20秒调度一次；<br/>5m：表示5分钟调度一次。<br/>1h：表示一小时调度一次。如下图配置1分钟调度一次<br/>
						<img src="${ctxStatic}/images/tip/simple.ico"></img><br/>
						<b>corn模式</b>：采用quartz的corn表达式。（详细百度：quartz表达式）<br/>
						0 0/10 * * * ? ：表示每隔10分钟执行一次。<br/>
						0 0 * * * ? ：表示每隔1小时执行一次。<br/>
						0 15 10 ? * * ：每天10点15分触发，如下表示每隔5分钟执行一次：<br/>
						<img src="${ctxStatic}/images/tip/corn.ico"></img>
						</div>
			    		<b>任务调度：</b>
			    	</div>
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
