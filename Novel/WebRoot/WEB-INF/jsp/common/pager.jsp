<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test='${ requestScope.pv.page > 0}'>
	<c:choose>
	   	<c:when test="${ requestScope.pv.now == 0}">
	   		<strong class='prevpage'><i class='triangle left'></i> 上一页</strong>
	   	</c:when>
	   	<c:otherwise>
	   		<a class='prevpage' href='${ requestScope.pv.url}${ requestScope.pv.now - 1}'><i class='triangle left'></i> 上一页</a>
	   	</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${ requestScope.pv.page <= 5}">
			<c:forEach begin="1" end="${ requestScope.pv.page}" varStatus="status" step="1">
			   	<c:choose>
				   	<c:when test="${ requestScope.pv.now == status.index - 1}">
				   		<strong class='cur'>${ status.index}</strong>
				   	</c:when>
				   	<c:otherwise>
				   		<a class='page' href='${ requestScope.pv.url}${ status.index - 1}'>${ status.index}</a>
				   	</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${ requestScope.pv.now < 4}">
					<c:forEach begin="1" end="5" varStatus="status" step="1">
					   	<c:choose>
						   	<c:when test="${ requestScope.pv.now == status.index - 1}">
						   		<strong class='cur'>${ status.index}</strong>
						   	</c:when>
						   	<c:otherwise>
						   		<a class='page' href='${ requestScope.pv.url}${ status.index - 1}'>${ status.index}</a>
						   	</c:otherwise>
						</c:choose>
					</c:forEach>
					<a class='page' href='${ requestScope.pv.url}${ requestScope.pv.page - 1}'>... ${ requestScope.pv.page}</a>
				</c:when>
				<c:when test="${ requestScope.pv.now >= requestScope.pv.page - 4}">
					<a class='page' href='${ requestScope.pv.url}0'>1 ...</a>
					<c:forEach begin="${ requestScope.pv.page - 4}" end="${ requestScope.pv.page}" varStatus="status" step="1">
					   	<c:choose>
						   	<c:when test="${ requestScope.pv.now == status.index - 1}">
						   		<strong class='cur'>${ status.index}</strong>
						   	</c:when>
						   	<c:otherwise>
						   		<a class='page' href='${ requestScope.pv.url}${ status.index - 1}'>${ status.index}</a>
						   	</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<a class='page' href='${ requestScope.pv.url}0'>1 ...</a>				
					<c:forEach begin="${ requestScope.pv.now - 1}" end="${ requestScope.pv.now + 3}" varStatus="status" step="1">
					   	<c:choose>
						   	<c:when test="${ requestScope.pv.now == status.index - 1}">
						   		<strong class='cur'>${ status.index}</strong>
						   	</c:when>
						   	<c:otherwise>
						   		<a class='page' href='${ requestScope.pv.url}${ status.index - 1}'>${ status.index}</a>
						   	</c:otherwise>
						</c:choose>
					</c:forEach>
					
					<a class='page' href='${ requestScope.pv.url}${ requestScope.pv.page - 1}'>... ${ requestScope.pv.page}</a>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<c:choose>
	   	<c:when test="${ requestScope.pv.now + 1 == requestScope.pv.page}">
	   		<strong class='nextpage'>下一页 <i class='triangle right'></i></strong>
	   	</c:when>
	   	<c:otherwise>
	   		<a class='nextpage' href='${ requestScope.pv.url}${ requestScope.pv.now + 1}'>下一页 <i class='triangle right'></i></a>
	   	</c:otherwise>
	</c:choose>
</c:if>