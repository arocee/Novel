<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a id='frameTop'></a>
<div>
	<div class='type_admin admin'>
		<h1>type</h1>
		<div class='typelist list'>
			<div id='typelist'>
				<c:forEach var='type' items="${types}">
					<c:if test='${type.id == tid }'>
						<span class='cur' data-id='${type.id }'><a href="javascript:void(0);">${type.type }</a><!-- <i class='edit' title='编辑'></i><i class='delete' title='删除'></i> --></span>	
					</c:if>
					<c:if test='${type.id != tid }'>
						<span data-id='${type.id }'><a href="javascript:void(0);">${type.type }</a><!-- <i class='edit' title='编辑'></i><i class='delete' title='删除'></i> --></span>
					</c:if>
				</c:forEach>					
			</div>
		</div>
	</div>
	<div class='article_admin admin'>
		<h1>article</h1>
		<div class='articlelist list'>
			<div id='articlelist'>
				<c:forEach var='article' items="${articles}">
					<c:if test='${article.id == aid }'>
						<span class='cur' data-id='${article.id }' data-tid='${article.tid }'><a href="javascript:void(0);">${article.article }</a><i class='edit' title='编辑'></i><i class='delete' title='删除'></i></span>
					</c:if>
					<c:if test='${article.id != aid }'>
						<span data-id='${article.id }' data-tid='${article.tid }'><a href="javascript:void(0);">${article.article }</a><i class='edit' title='编辑'></i><i class='delete' title='删除'></i></span>
					</c:if>
				</c:forEach>					
			</div>
			<div><a id='addArticle' class='newAdd' href='javascript:void(0);'>添加类目&nbsp;<i>+</i></a></div>
		</div>
	</div>
	<div class='section_admin admin'>
		<h1>section</h1>
		<div class='sectionlist list'>
			<div id='sectionlist'>
				<c:forEach var='section' items="${sections}">
					<c:if test='${section.id == sid }'>
						<span class='cur' data-id='${section.id }' data-aid='${section.aid }'><a href="javascript:void(0);">${section.section }</a><i class='edit' title='编辑'></i><i class='delete' title='删除'></i></span>
					</c:if>
					<c:if test='${section.id != sid }'>
						<span data-id='${section.id }' data-aid='${section.aid }'><a href="javascript:void(0);">${section.section }</a><i class='edit' title='编辑'></i><i class='delete' title='删除'></i></span>						
					</c:if>
				</c:forEach>					
			</div>
			<div><a id='addSection' class='newAdd' href='javascript:void(0);'>添加类目&nbsp;<i>+</i></a></div>
		</div>
	</div>
	<div class='paragraph_admin admin'>
		<h1>paragraph</h1>
		<div class='paragraphlist list'>
			<div>
				<a id='addParagraph' class='newAdd' href='javascript:void(0);'>添加类目&nbsp;<i>+</i></a>
				<a href='javascript:void(0);' id='resetindex'>重置全文索引</a>
			</div>
			<ul id='paragraphlist'>
				<c:forEach var='paragraph' items='${paragraphes }'>
					<li data-id='${paragraph.id }' data-sid='${paragraph.sid }'>
						<p>${paragraph.paragraph }</p>
						<div><a class='edit' href='javascript:void(0);' title='编辑'>编辑<i></i></a><a class='delete' href='javascript:void(0);' title='删除'>删除<i></i></a></div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
