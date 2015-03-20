<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>普通搜索</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<script type="text/javascript">
	$(document).ready(function() {
		//$("#field0").val("CONTENT");
	});
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
	</script>
	<style type="text/css">
		a:link {
		color:'#0000d5';
		text-decoration:underline;
		}
		font{
		size:3;
		}
		.tcf{
		margin-left:15px;
		margin-top:10px;
		}
	</style>
</head>
<body>
	<div>
		<form id="searchForm" action="${ctx_f}/search/search" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${pageSize}"/>
			<div style="margin-top:5px;font-size:25px;font-weight:normal;font-familiy:Helvetica, Georgia, Arial, sans-serif, 黑体;float:left">${fns:getConfig('productName')}</div>
			<input id="keyword" name="simpleQuery.keyword" type="text" maxlength="200" class="input-medium" style="width:450px;height:30px;margin-left:15px;" value="${simpleQuery.keyword}"/>
			<%-- <!-- 标题字段 -->
			<input id="titleField" name="simpleQuery.titleField" type="hidden" value="_TITLE"/>
			<!-- 内容字段 -->
			<input id="contentField" name="simpleQuery.resumeField" type="hidden" value="_RESUME"/>
			<!-- 作为条件的查询字段 -->
			<c:forEach items="CONTENT,RESUME" var="field" varStatus="status">
				<input name="simpleQuery.simpleItems[${status.index}].field" type="hidden" value="${field}"/>
			</c:forEach>
			<!-- 需要附带查询出的字段 -->
			<c:forEach items="PAGE_URL,CREATETIME" var="field" varStatus="status">
				<input name="simpleQuery.queryFields[${status.index}]" type="hidden" value="${field}"/>
			</c:forEach> --%>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="搜索" onclick="return page();"/><br>
			<span style="color:#999999;font-size:13px">找到约 ${page.count}条结果，用时${time}秒</span>
		</form>
	</div>
	<div style="width:65%;">
	<div class="pagination">${page}</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<c:forEach items="${page.list}" var="retrievalPage">
			<tr>
				<td>
					<div class="tcf">
						<div style="font-size:medium;font-weight:normal"><a href="${retrievalPage.retrievalResultFields['PAGE_URL']}" target="_blank">${retrievalPage.title}</a></div>
						<div>${retrievalPage.content}</div>
						<div>
							<font color="#008000">${retrievalPage.retrievalResultFields['PAGE_URL']} ${retrievalPage.retrievalResultFields['CREATETIME']}</font>
						</div>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>