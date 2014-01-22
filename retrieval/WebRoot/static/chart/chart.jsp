<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息量统计</title>
	<!-- <meta name="decorator" content="default"/> -->
	<%-- <link href="${ctxStatic}/myjs/css/category.css" type="text/css" rel="stylesheet" /> --%>
	<link href="${ctxStatic}/myjs/css/scroll.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctxStatic}/ichartjs/ichart.1.2.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic}/myjs/line.js"></script> 
	<script type="text/javascript" src="${ctxStatic}/myjs/pie.js"></script> 
	<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="${ctxStatic}/myjs/selectDownMenu.js"></script> 
	<script type="text/javascript" src="${ctxStatic}/myjs/scroll.js"></script>
	<script type="text/javascript" src="${ctxStatic}/myjs/picShift.js"></script>
	<%-- <script type="text/javascript" src="${ctxStatic}/myjs/leftCategory.js"></script>  --%>
	<script type="text/javascript">
		$(function(){
			/* pie2D_1("canvasDiv1");
			area2D_1("canvasDiv3");
			line2D_1("canvasDiv2"); 
			selectDownMenu("address","addressIds","canvasDiv");
			leftCatefory("canvasDiv");*/
			var opt = {demo:true};
			//getScroll("canvasDiv",opt);
			getPicShift("canvasDiv",opt);
		}); 
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/chart/chart">统计报表汇总</a></li>
	</ul> --%>
	<!-- <div id='canvasDiv1' demo="true"></div>
	<div id='canvasDiv2' demo="true"></div>
	<div id='canvasDiv3' demo="true"></div>
	<div id='canvasDiv4' demo="true"></div> -->
	<!--地址选择一：<br/>
	 <input type="text" name="address" id="address" >
	<input type="hidden" name="addressIds" id="addressIds" >
	<div id="canvasDiv" demo="true" mutil="true"></div> -->
	<div id="canvasDiv"></div> 
	<%-- <tags:message content="${message}"/> --%>
</body>
</html>