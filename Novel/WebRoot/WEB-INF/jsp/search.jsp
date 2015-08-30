<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>小说灵感网——管理页面</title>
    <link rel="shortcut icon" href="/image/favicon.ico" />
	<link rel='stylesheet' href='/css/all.css' />
  </head>
  <body>
  	<div class='wrapper' data-page='search'>
  		<c:import url="common/header.jsp"></c:import>
	    <div class='body' id='body'>
			<div class='mainContent searchContent'>
				<div class='searchInfo'>
					<p><span></span>搜索“<c:out value='${keyWords}'></c:out>”的结果，共${count}条</p>
				</div>
				<div class='paragraphList'>
					<ul>
						<c:forEach var='novel' items='${novels }'>
							<li>
								<p>${novel.paragraph}<p>
								<div class='from'>来自 <a href='###'>${novel.type}</a> &gt; <a href='###'>${novel.article}</a> &gt; <a href='###'>${novel.section}</a></div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class='pager'>
				<c:import url="common/pager.jsp"></c:import>
			</div> 
		</div>
		<c:import url="common/footer.jsp"></c:import>
	</div>
	<script src="/javascript/jquery-1.11.1.js"></script>
	<script src="/javascript/all.js"></script>
  </body>
</html>
