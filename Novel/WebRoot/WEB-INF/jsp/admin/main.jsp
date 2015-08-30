<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>小说灵感网——管理页面</title>
    <link rel="shortcut icon" href="/image/favicon.ico" />
	<link rel='stylesheet' href='/css/admin.css' />
  </head>
  <body data-page='admin'>
	<div class='header'>
		<h1>小说灵感网</h1>
		<div class='userinfo'>
			<a href='/Novel/admin/main?page=4'>欢迎你，${sessionScope.user.username }</a> |
			<a href='/Novel/admin/logout'>退出登录</a>
		</div>
	</div>
	<div class='frame'>
		<div class='frameLeft' id='frameLeft'>
			<h2 data-menu='info'><i></i>信息</h2>
			<ul data-menu='info'>
				<li data-menu='basic'><a href='/Novel/admin/main?page=0'>基本信息</a></li>
				<li data-menu='log'><a href='/Novel/admin/main?page=1'>日志查看</a></li>
			</ul>
			<h2 data-menu='edit'><i></i>编辑</h2>
			<ul data-menu='edit'>
				<li data-menu='data'><a href='/Novel/admin/main?page=2'>数据编辑</a></li>
			</ul>
			<h2 data-menu='manage'><i></i>管理</h2>
			<ul data-menu='manage'>
				<li data-menu='user'><a href='/Novel/admin/main?page=3'>用户管理</a></li>
				<li data-menu='member'><a href='/Novel/admin/main?page=4'>个人管理</a></li>
			</ul>
		</div>
		<div class='frameRight' id='frameRight'>
			<c:choose>
				<c:when test="${page == 0 }">
					<c:import url="item/basic.jsp"></c:import>
				</c:when>
				<c:when test="${page == 1 }">
					<c:import url="item/log.jsp"></c:import>
				</c:when>
				<c:when test="${page == 2 }">
					<c:import url="item/edit.jsp"></c:import>
				</c:when>
				<c:when test="${page == 3 }">
					<c:import url="item/user.jsp"></c:import>
				</c:when>
				<c:when test="${page == 4 }">
					<c:import url="item/member.jsp"></c:import>
				</c:when>
			</c:choose>
		</div>
	</div>
	<div class='footer'>
		<p>novel-inspiration&copy; 2015</p>
	</div>
	<div class='cover' id='cover'></div>
	<div class='editfield dialog' id='editfield'>
		<h1>修改名字</h1>
		<div class='fieldBody'>
			<p><span class='clazz'></span>：<span class='clazzName'></span></p>
			<input name='name' type='text' placeholder='请输入新名字' />
		</div>
		<div class='fieldfoot'>
			<a href='javascript:void(0);' class='confirm editCon'>确定</a>
			<a href='javascript:void(0);' class='cancel'>取消</a>
		</div>
	</div>
	<div class='addfield dialog' id='addfield'>
		<h1>添加子项目</h1>
		<div class='fieldBody'>
			<p>上级类目：<span class='par'></span></p>
			<p>所属类目：<span class='clazz'></span></p>
			<input name='name' type='text' placeholder='请输入名字' />
		</div>
		<div class='fieldfoot'>
			<a href='javascript:void(0);' class='confirm addCon'>确定</a>
			<a href='javascript:void(0);' class='cancel'>取消</a>
		</div>
	</div>
	<div class='loading' id='loading'></div>
	<ul class="tbui_aside_float_bar">
		<li class="tbui_aside_fbar_button tbui_fbar_top" id='toTop'>
			<a href="#frameTop"></a>
		</li>
	</ul>
	<script src="/javascript/admin/require.js"></script>
	<script src="/javascript/admin/main.js"></script>
	<c:choose>
		<c:when test="${page == 0 }">
			<script src="/javascript/admin/basic.js"></script>
		</c:when>
		<c:when test="${page == 1 }">
			<script src="/javascript/admin/log.js"></script>
		</c:when>
		<c:when test="${page == 2 }">
			<script src="/javascript/admin/edit.js"></script>
		</c:when>
		<c:when test="${page == 3 }">
			<script src="/javascript/admin/user.js"></script>
		</c:when>
		<c:when test="${page == 4 }">
			<script src="/javascript/admin/member.js"></script>
		</c:when>
	</c:choose>
  </body>
</html>