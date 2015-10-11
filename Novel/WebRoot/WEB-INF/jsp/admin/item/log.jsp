<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class='logType' id='logType'>
	<ul>
		<c:forEach items="${logs }" var="log" varStatus="status">
			<c:choose>
				<c:when test="${status.index == 0 }">
					<li class='cur' title='查看${log.log }日志'>${log.log }</li>
				</c:when>
				<c:otherwise>
					<li title='查看${log.log }日志'>${log.log }</li>
				</c:otherwise>				
			</c:choose>
		</c:forEach>
	</ul>
	<div class='logDate'>
		<label for='logDate'>请选择所查看的日志日期：</label>
		<select name='logDate' id='logDate'>
			<c:if test="${fn:length(logs) > 0}">
				<c:forEach items="${logs[0].fileNames }" var="filename" varStatus="status">
					<c:choose>
						<c:when test="${status.index == 0 }">
							<option selected='selected' value='${filename }'>${filename }</option>
						</c:when>
						<c:otherwise>
							<option value='${filename }'>${filename }</option>
						</c:otherwise>						
					</c:choose>
				</c:forEach>
			</c:if>
		</select>
	</div>
</div>
<div class='logWrapper'>
	<div class='log' id='log'>${content }</div>
</div>
<script>
	var logNames = [];
	
	var defaultLogContent = '${content }';
	
	<c:forEach items="${logs }" var="log">
		var fileNames = [];
		
		<c:forEach items="${log.fileNames }" var="fileName">
			fileNames.push("${fileName }");
		</c:forEach>
		
		logNames.push(fileNames);
	</c:forEach>
</script>