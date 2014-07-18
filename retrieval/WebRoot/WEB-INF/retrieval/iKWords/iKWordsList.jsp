<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>分词工具管理</title>
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
			$("#addSubmit").click(function(){
				if($("#words").val()!=null&&$("#words").val()!=''){
					art.dialog.confirm('你确定要将词元“'+$("#words").val()+'”加到词典库吗？', function () {
						$("#inputForm").ajaxSubmit({
			                type: 'post',
			                url: 'save' ,
			                success: function(data){
			                	if(data.msg==0){
			                		art.dialog.alert('添加新词“'+$("#words").val()+'”成功！');
			                	}else
			                		art.dialog.alert('“'+$("#words").val()+'”该词已经存在，无需添加！');
			                		
			                },
			                error: function(XmlHttpRequest, textStatus, errorThrown){
			                	art.dialog.alert('网络不通，保存失败！');
			                }
			            });
					});
				}else{
					art.dialog.alert('没有输入词元，请检查！');
				}
			});
			$("#delSubmit").click(function(){
				if($("#words").val()!=null&&$("#words").val()!=''){
					art.dialog.confirm('你确定要将词元“'+$("#words").val()+'”从词典库中删除吗？', function () {
						$("#inputForm").ajaxSubmit({
			                type: 'post',
			                url: 'delete' ,
			                success: function(data){
			                	if(data.msg==0){
			                		art.dialog.alert('词“'+$("#words").val()+'”已经成功删除！');
			                	}else
			                		art.dialog.alert('“'+$("#words").val()+'”不存在，无需删除！');
			                		
			                },
			                error: function(XmlHttpRequest, textStatus, errorThrown){
			                	art.dialog.alert('网络不通，保存失败！');
			                }
			            });
					});
				}else{
					art.dialog.alert('没有输入词元，请检查！');
				}
			});
			$("#btnWords").click(function(){
				if($("#words").val()!=null&&$("#words").val()!=''){
					//$("#inputForm").attr("action",$("#ctx_a").val()+"/iKWords/words");
					//$("#inputForm").submit();
					 $("#inputForm").ajaxSubmit({
		                type: 'post',
		                url: 'words' ,
		                success: function(data){
		                	$("#_iKWords_lable").html("分 词:");
		                	$("#_iKWords").html(data.iKWords);
		                	$("#_iKWords").css({"background-color":"#00C5F6","width":"500px","border-radius":"8px","padding":"5px","font-weight":"bold"});
		                },
		                error: function(XmlHttpRequest, textStatus, errorThrown){
		                	art.dialog.alert('网络不通，保存失败！');
		                }
		            }); 
				}else{
					art.dialog.alert('没有输入词元，请检查！');
				}
			});
		});
	</script>
	<style type="text/css">
      div#item_1 { position: absolute; }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx_a}/iKWords/list">分词工具</a></li>
	</ul>
	<form id="inputForm" modelAttribute="iKWords" action="${ctx_a}/iKWords/save" method="post" class="form-horizontal">
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="词 元">
				按钮：分词、添加、删除。<br/>
				分词：在词元输入框中输入词汇，例如“沈晓军”，点分词，可查看到分词后结果，词与词之间用“|”隔开，如下图：<br/>
				<img src="${ctxStatic}/images/tip/sxjun_1.ico"></img><br/>
				添加：将词元加入到词典库中，添加后在搜索页面搜“沈晓军”会当做一个词去搜，不添加的话，会分成“沈”、“晓”、“军”三个字去搜索。例如将“沈晓军”加入到词典库，点分词查看到如下：<br/>
				<img src="${ctxStatic}/images/tip/sxjun_2.ico"></img><br/>
				删除：将词元从词典库中删除（只能删自定义词库中的词）。例如将“沈晓军”从词典库中删除，删除后点分词查看到如下：<br/>
				<img src="${ctxStatic}/images/tip/sxjun_1.ico"></img>
				</div>
				<label class="control-label">词 元:</label>
			</div>
			<div class="controls">
				<input type="hidden" id="ctx_a"  value="${ctx_a}"/>
				<input id="words" name="iKWords.words" style="width:500px"/>
				<input id="btnWords" class="btn btn-primary" type="button" value="分词"/>&nbsp;&nbsp;<input id="addSubmit" class="btn btn-primary" type="button" value="添加"/>&nbsp;&nbsp;<input id="delSubmit" class="btn btn-primary" type="button" value="删除"/>
			</div>
		</div>
		<div class="control-group">
			<div id="item_1" class="item">
				<div class="tooltip_description" style="display:none" title="分 词">
				展示分词的结果，词与词之间用“ | ”隔开。如下如：<br/>
				<img src="${ctxStatic}/images/tip/sxjun_1.ico"></img><br/>
				</div>
				<label class="control-label" id="_iKWords_lable"></label>
			</div>
			<div class="controls">
				<div id="_iKWords"></div>
			</div>
		</div>
	</form>
</body>
</html>
