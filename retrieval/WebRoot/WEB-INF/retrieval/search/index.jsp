<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>普通搜索</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/select-search/js/jQselect.js" type="text/javascript"></script>
	<link href="${ctxStatic}/select-search/css/style.css" type="text/css" rel="stylesheet" />
	<style type="text/css">
		body {
		TEXT-ALIGN: center;
		}
		.input-medium{
			font:16px/22px arial;
			width:380px;
			height:28px;
			border:1px solid #b7d1eb;
		}
		 .searchBtn{
         	width:65px;
         	height:30px;
         	margin-left:15px;
         	background-color:
         }
         .form_search{
         	margin-top:200px;
         } 
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#c").selectbox();
		$("#btnSubmit").click(function(){
			if($("#keyword").val()!=''){
				$("#searchForm").submit();
			}
		});
		var $inp = $('input'); //所有的input元素
		$inp.keypress(function (e) { //这里给function一个事件参数命名为e，叫event也行，随意的，e就是IE窗口发生的事件。
		    var key = e.which; //e.which是按键的值
		    if (key == 13) {
		    	if($("#keyword").val()!=''){
					$("#searchForm").submit();
				}
		    }
		});
	});
	</script>
</head>
<body>
	<div>
		<form id="searchForm" action="${ctx_f}/search/page" method="post" class="form_search">
			<div style="margin-top:5px;font-size:25px;font-weight:normal;font-familiy:Helvetica, Georgia, Arial, sans-serif, 黑体;clear:both;color:#99C5DD;">${fns:getConfig('productName')}</div>
			<div class="searchBar">
				<div class="select">
					<select id="c" style="display:none;">
						<option value="1" selected="selected">生活信息</option>
						<option value="2">店铺商家</option>
						<option value="3">新闻资讯</option>
						<option value="4">团购活动</option>
						<option value="5">招聘信息</option>
					</select>
				</div>
				<div style="float:left;margin-top:0px">
					<input id="keyword" name="simpleQuery.keyword" type="text" maxlength="200" class="input-medium" placeholder="--输入关键字--" onfocus="this.style.color='#666'"/>
					<input id="btnSubmit" class="searchBtn" type="button" value="搜索" /><br>
				</div>
				
			</div>
		</form>
	</div>
</body>
</html>