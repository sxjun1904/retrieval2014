<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>普通搜索</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jPaginate/jquery.paginate.js" type="text/javascript"></script>
	<script src="${ctxStatic}/images-hover/js/images_hover_move.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jPaginate/css/style.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/images-hover/css/images_hover_move.css" type="text/css" rel="stylesheet" />
	<style type="text/css">
		/* body{
			overflow: hidden;
		} */
		a:link {
		color:'#0000d5';
		text-decoration:underline;
		front-style:normal;
		font-family:arial;
		font-size:normal;
		font-weight:normal;
		}
		.input-medium{
			font:16px/22px arial;
			width:450px;
			height:30px;
			margin-left:15px;
		}
		font{
		size:3;
		}
		.tcf{
			margin-left:15px;
			margin-top:10px;
		}
		.cont{
			margin-top:3px;
			margin-bottom:3px;
			font-size:13px;
			font-family:arial,sans-serif;
		}
		.info{
			width:100%;
			height:30px;
			line-height:30px;
			background-color:#D6E7EE;
            font-family: verdana;
			letter-spacing:2px;
			font-size:13px;
			padding-left:15px;
		}
		.demo{
             padding:10px;
             margin:10px auto;
             border: 1px solid #fff;
             background-color:#f7f7f7;
             font-family: arial;
             letter-spacing:2px;
         }
         .searchBtn{
         	width:65px;
         	height:30px;
         	margin-left:15px;
         	background-color:
         }
         .main{
         	height:90%;
         	/* overflow-y: auto; */
         }
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#images_hover_move").images_hover_move({
			window_width: '500',//幻灯片窗口宽度
			window_height: '190',//幻灯片窗口的高度
			border_color: '#999',
			title_color: '#333',
			title_background_color: '#CCC',
			background_color: '#f5f5f5'
		});
		
		$("#images_hover_move1").images_hover_move({
			window_width: '500',//幻灯片窗口宽度
			window_height: '100',//幻灯片窗口的高度
			border_color: '#999',
			title_color: '#333',
			title_background_color: '#CCC',
			background_color: '#f5f5f5'
		});
		
		window.onload = function() { 
			
			var totalCount= $('#totalCount').val();
			var pageSize = Math.ceil(totalCount/$('#pageSize').val());
			var pageNo = $('#pageNo').val();
			
			$("#pages").paginate({
				count 		: pageSize,
				start 		: pageNo,
				display     : 10,
				border					: true,
				border_color			: '#C2D5E3',
				text_color  			: '#1E63A0',
				background_color    	: 'white',	
				border_hover_color		: '#1E63A0',
				text_hover_color  		: 'red',
				background_hover_color	: '#CFE2ED', 
				//rotate      : false,
				images		: true,
				mouse		: 'press',
				onChange : function(page){
					$("#pageNo").val(page);
					$("#searchForm").submit();
				}
			});
		};
	});
	function page(n,s){
		//$("#pageNo").val(n);
		//$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
	</script>
</head>
<body>
	<div style="float:right">
		<a href="${ctx_f}/search/index">返回首页</a>
	</div>
	<div>
		<form id="searchForm" action="${ctx_f}/search/page" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="totalCount" name="totalCount" type="hidden" value="${page.count}"/>
			<div style="margin-top:5px;font-size:25px;font-weight:normal;font-familiy:Helvetica, Georgia, Arial, sans-serif, 黑体;float:left">${fns:getConfig('productName')}</div>
			<input id="keyword" name="simpleQuery.keyword" type="text" maxlength="200" class="input-medium" value="${simpleQuery.keyword}"/>
			<input id="btnSubmit" class="searchBtn" type="submit" value="搜索" /><br>
		</form>
	</div>
	<div class="info">
		<span>找到约 <font color="red">${page.count}</font>条结果，用时<font color="red">${time}</font>秒
			 	<c:forEach items="${page.group}" var="group">
			 		<a href="#" onclick="javascript:alert('${group.key}');return false;">${group.key}</a>(${group.value})
			 	</c:forEach>
		</span>
	</div>
	<div class="main">
		<div style="width:60%;float:left">
			<div>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<c:forEach items="${page.list}" var="retrievalPage">
					<tr>
						<td>
							<div class="tcf">
								<div style="font-size:medium;font-weight:normal"><a href="${retrievalPage.retrievalResultFields['PAGE_URL']}" target="_blank">${retrievalPage.title}</a></div>
								<div class="cont">${retrievalPage.content}</div>
								<div><span style="color:#008000;font-size:11pt !important">${retrievalPage.retrievalResultFields['_IBT']} ${retrievalPage.retrievalResultFields['PAGE_URL']} ${retrievalPage.retrievalResultFields['CREATETIME']}</span></div>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			<div class="demo">
				<div id="pages" style="margin-left:60px"></div>
			</div>
		</div>
		<div style="width:40%;float:right;margin-top:35px">
			<div id="images_hover_move" class="images_hover_move">
				<ul>
					<li><a href="#"><img src="../../static/images-hover/images/slideshow_large_1.jpg" title="title1"  height="194" /></a></li>
					<li><a href="#"><img src="../../static/images-hover/images/slideshow_large_2.jpg" title="title2"  height="194" /></a></li>
					<li><a href="#"><img src="../../static/images-hover/images/slideshow_large_3.jpg" title="title3"  height="203" /></a></li>
				</ul>
			</div>
			<div id="images_hover_move1" class="images_hover_move">
				<ul>
					<li><a href="#"><img src="../../static/images-hover/images/slideshow_large_1.jpg" title="Navigation1"  height="194" /></a></li>
					<li><a href="#"><img src="../../static/images-hover/images/slideshow_large_2.jpg" title="Navigation2"  height="194" /></a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>