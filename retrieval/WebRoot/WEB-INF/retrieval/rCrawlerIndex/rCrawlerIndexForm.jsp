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
			//var initfieldstr = '<option value="">请选择</option>';
			
			$.ajax({
			    url: 'indexPathes?t=3',
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
			    },
			    cache: false
			});
			
			/* $.ajax({
			    url: 'initFields',
			    success: function (data) {
			    	var len = data.initFields.length;
			    	for(var i = 0;i<len;i++){
			    		initfieldstr +='<option value="'+data.initFields[i].field+'">'+data.initFields[i].field+'</option>';
			    	}
			    },
			    cache: false
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
		<li><a href="${ctx_a}/rCrawlerIndex/list">爬虫索引列表</a></li>
		<li class="active"><a href="${ctx_a}/rCrawlerIndex/form">爬虫索引添加</a></li>
	</ul><br/>
	<form id="inputForm" modelAttribute="rCrawlerIndex" action="${ctx_a}/rCrawlerIndex/save" method="post" class="form-horizontal">
	<div style="float:left;width:40%;background-color:white">
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="触发器表">
				必填。<br/>
				设置索引的名称<br/>
				例如：招投标网站、人事招聘网站</br>
				</div>
				<label class="control-label">索引名称:</label>
			</div>
			<div class="controls">
				 <input type="text" name="rCrawlerIndex.name" id="name" value="${rCrawlerIndex.name}">
			</div>
		</div>
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="是否启用">
				是：表示该索引设置起作用，可以初始化、更新、删除索引<br/>
				否：表示该索引设置不起作用，不能初始化更新、删除索引<br/>
				</div>
				<label class="control-label">是否启用:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="id" name="rCrawlerIndex.id" value="${rCrawlerIndex.id}"></input>
				<input type="hidden" id="database_isOn" value="${rCrawlerIndex.isOn}" />
				<input type="radio" id="isOn_0" name="rCrawlerIndex.isOn" value="0" checked="true"/>启用
				<input type="radio" id="isOn_1" name="rCrawlerIndex.isOn" value="1" />禁止
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
				<input type="hidden" id="database_isInit" value="${rCrawlerIndex.isInit}" />
				<input type="radio" id="isInit_0" name="rCrawlerIndex.isInit" value="0" checked="true"/>否
				<input type="radio" id="isInit_1" name="rCrawlerIndex.isInit" value="1" />是
				<input type="radio" id="isInit_2" name="rCrawlerIndex.isInit" value="2" />整理
			</div>
		</div>
		
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
				<input type="hidden" id="database_indexOperatorType" value="${rCrawlerIndex.indexOperatorType}" />
				<input type="radio" id="indexOperatorType_1" name="rCrawlerIndex.indexOperatorType" value="1" checked="true"/>更新
				<input type="radio" id="indexOperatorType_0" name="rCrawlerIndex.indexOperatorType" value="0"  />增加
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
				<input type="hidden" id="indexPath" value="${rCrawlerIndex.indexPath_id}"></input>
				<select id="indexPathCate" name="rCrawlerIndex.indexPath_id" class="combobox"></select> 
			</div>
		</div>
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="URL地址">
				必填。<br/>
				需要爬的网站地址，用英文分号分隔<br/>
				例如：http://www.jszj.com.cn/zaojia;http://www.jtzyzg.org.cn/</br>
				</div>
				<label class="control-label">URL地址:</label>
			</div>
			<div class="controls">
				 <input type="text" name="rCrawlerIndex.url" id="url" value="${rCrawlerIndex.url}">
			</div>
		</div>
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="线程数">
				非必填。开启的线程数，必须是数字<br/>
				默认为：5<br/>
				例如：5、7、10等</br>
				</div>
				<label class="control-label">线程数:</label>
			</div>
			<div class="controls">
				 <input type="text" name="rCrawlerIndex.numberOfCrawlers" id="numberOfCrawlers" value="${rCrawlerIndex.numberOfCrawlers}">
			</div>
		</div>
		
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="爬取深度">
				非必填。深度，即从入口URL开始算，URL是第几层。如入口A是1，从A中找到了B，B中又有C，则B是2，C是3<br/>
				默认为：2<br/>
				例如：5、7、10等</br>
				</div>
				<label class="control-label">爬取深度:</label>
			</div>
			<div class="controls">
				 <input type="text" name="rCrawlerIndex.maxDepthOfCrawling" id="maxDepthOfCrawling" value="${rCrawlerIndex.maxDepthOfCrawling}">
			</div>
		</div>
		
	</div>	
		<div style="float:right;width:60%">
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
								<c:forEach items="${rCrawlerIndex.justScheduleList}" var="justSchedule" varStatus="status">
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
