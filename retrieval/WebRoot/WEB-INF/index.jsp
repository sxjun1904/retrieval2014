<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')}</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/include/head.jsp" %>
	<%@include file="/WEB-INF/include/dialog.jsp" %>
	<style type="text/css">
		#main {padding:0;margin:0;} #main .container-fluid{padding:0 7px 0 10px;}
		#header {margin:0 0 10px;position:static;} #header li {font-size:14px;_font-size:12px;}
		#header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:26px;padding-left:33px;}
		#footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
		#footer, #footer a {color:#999;} 
	</style>
	<script type="text/javascript"> 
		$(document).ready(function() {
			$("#menu a.menu").click(function(){
				$("#menu li.menu").removeClass("active");
				$(this).parent().addClass("active");
				if(!$("#openClose").hasClass("close")){
					$("#openClose").click();
				}
			});
		});
	</script>
</head>
<body>
	<div id="main">
		<div id="header" class="navbar navbar-fixed-top">
	      <div class="navbar-inner">
	      	 <div class="brand">${fns:getConfig('productName')}</div>
	         <div class="nav-collapse">
	           <ul id="menu" class="nav">
				 <c:set var="firstMenu" value="true"/>
				 <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
					<c:if test="${menu.parentid eq 1&&menu.isShow eq 1}">
						<li class="menu ${firstMenu?' active':''}"><a class="menu" href="${ctx_a}/menu?parentId=${menu.id}" target="menuFrame" >${menu.name}</a></li>
						<c:if test="${firstMenu}">
							<c:set var="firstMenuId" value="${menu.id}"/>
						</c:if>
						<c:set var="firstMenu" value="false"/>
					</c:if>
				 </c:forEach>
	           </ul>
	           <ul class="nav pull-right">
			  	 <li id="themeSwitch" class="dropdown">
			       	<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
				    <ul class="dropdown-menu">
				      <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${ctx_a}/theme?theme=${dict.value}&url='+location.href">${dict.label}</a></li></c:forEach>
				    </ul>
				    <!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
			     </li>
			  	 <li class="dropdown">
				    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好,管理员</a>
				    <ul class="dropdown-menu">
				      <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
				      <li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
				    </ul>
			  	 </li>
			  	 <li><a href="${ctx}/logout" title="退出登录">退出</a></li>
			  	 <li>&nbsp;</li>
	           </ul>
	         </div><!--/.nav-collapse -->
	      </div>
	    </div>
	    <div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="left">
					<iframe id="menuFrame" name="menuFrame" src="${ctx_a}/menu?parentId=${firstMenuId}" style="overflow:visible;"
						scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
				</div>
				<div id="openClose" class="close">&nbsp;</div>
				<div id="right">
					<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;"
						scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
				</div>
			</div>
		    <div id="footer" class="row-fluid">
	            Copyright &copy; 2012-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By <a href="https://github.com/thinkgem/jeesite" target="_blank">JeeSite</a> ${fns:getConfig('version')}
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		var leftWidth = "160"; // 左侧窗口大小
		function wSize(){
			var minHeight = 500, minWidth = 980;
			var strs=getWindowSize().toString().split(",");
			$("#menuFrame, #mainFrame, #openClose").height((strs[0]<minHeight?minHeight:strs[0])-$("#header").height()-$("#footer").height()-32);
			$("#openClose").height($("#openClose").height()-5);
			if(strs[1]<minWidth){
				$("#main").css("width",minWidth-10);
				$("html,body").css({"overflow":"auto","overflow-x":"auto","overflow-y":"auto"});
			}else{
				$("#main").css("width","auto");
				$("html,body").css({"overflow":"hidden","overflow-x":"hidden","overflow-y":"hidden"});
			}
			$("#right").width($("#content").width()-$("#left").width()-$("#openClose").width()-5);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>