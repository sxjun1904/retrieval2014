<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')}</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<%@include file="/WEB-INF/include/dialog.jsp" %>
	<style type="text/css">
		body{TEXT-ALIGN: center;}
		.info{
			margin-top:200px;
		}
	</style>
	<script type="text/javascript"> 
	</script>
</head>
<body>
	<div id="main">
		<div class="info">
			<span style="color:#2A00FF"><h1>全文检索</h1></span>
			<h1>系统未授权或已过期</h1>
			<h1>请联系作者进行注册，谢谢！</h1><br>
			<h4>沈晓军  QQ:74276334</h4>
			Copyright &copy; 2012-${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a> - Powered By <a href="#" target="_blank">Retrieval</a> ${fns:getConfig('version')}
		</div>
	</div>
</body>
</html>