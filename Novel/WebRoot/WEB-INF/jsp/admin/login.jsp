<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${!empty sessionScope.user }">
	<c:redirect url="/admin/main"></c:redirect>
</c:if>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>小说灵感网——管理页面——登录</title>
    <link rel="shortcut icon" href="/image/favicon.ico" />
	<link rel='stylesheet' href='/css/admin.css' />
  </head>
  <body data-page='login'>
	<div class='wrapper loginWrapper'>
		<div class='mainContent'>
			<h1></h1>
			<c:if test="${!empty errorTip }">
				<p class='errorLine'>${errorTip }</p>	
			</c:if>
			<div class='loginPanel'>
				<div class='loginPanelInner'>
					<h2></h2>
					<form name='loginForm' action='/Novel/admin/member/login' method="post">
						<div class='controls'>
							<label for='username'>用户名：</label><input type='text' name='username' id='username' placeholder='请输入用户名' maxlength='10' autofocus='autofocus' />
							<p class='errorTip' id='userTip'>&nbsp;</p>
						</div>
						<div class='controls'>
							<label for='password'>密　码：</label><input type='password' name='password' id='password' placeholder='请输入密码' maxlength='10' />
							<p class='errorTip' id='passTip'>&nbsp;</p>
						</div>
						<div class='controls'>
							<input type='submit' value='登录' class='lgBtn' id='sub' />
							<input type='reset' value='取消' class='lgBtn' id='exit' />
						</div>
					</form>
				</div>
			</div>
			<p class='userTip'>建议使用chrome以达到最佳兼容性</p>
		</div>
	</div>
	<script src="/javascript/admin/require.js"></script>
	<script src="/javascript/admin/login.js"></script>
  </body>
</html>