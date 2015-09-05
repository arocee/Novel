<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class='header'>
	<div class='header_inner'>
		<h1><a href='/Novel/index.html'>小说灵感网</a></h1>
		<div class='nav'>
			<ul class='navUl'>
				<c:forEach var='type' items='${ nav}'>
					<li class='navLI' title="进入${type.type }页面">
						<a class='navA' href='/Novel/detail-${type.id}-${type.articles[0].id}-0.html'>${type.type }<em class='triangle'></em></a>
						<div class='subNav'>
							<ol>
								<c:forEach var='article' items='${type.articles }'>
									<li title='进入${type.type}-${article.article}页面'>
										<a href='/Novel/detail-${type.id}-${article.id}-0.html'>${article.article}</a>
									</li>
								</c:forEach>
							</ol>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class='search'>
			<form action='/Novel/search.html' method="get">
				<input type='text' class='query' name='keyWords' placeholder='搜索内容' maxlength='15' value='${keyWords}' autocomplete="off" />
				<c:if test='!${empty tid }'>
					<input type='hidden' name='tid' value='${tid}' />
				</c:if>
				<c:if test='!${empty aid }'>
					<input type='hidden' name='aid' value='${aid}' />
				</c:if>
				<c:if test='!${empty sid }'>
					<input type='hidden' name='sid' value='${sid}' />
				</c:if>
				<select class='searchType' name='searchType'>
					<option value='0'>全部</option>
					<c:if test='!${empty tid }'>
						<option value='1'>本类</option>
					</c:if>
					<c:if test='!${empty aid }'>
						<option value='2'>本章</option>
					</c:if>
					<c:if test='!${empty sid }'>
						<option value='3'>本块</option>
					</c:if>
				</select>
				<input type='submit' id='search' value='搜索' />
			</form>
			<div class='autocom'>
				<div class='comInner'>
					<ul></ul>
				</div>
			</div>
		</div>
	</div>
</div>
<div class='headFix' id='headFix'></div>
