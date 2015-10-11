<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<div class='userWrapper'>
	<div class='userInner'>
		<div class='group' id='group'>
			<h1>用户分组</h1>
			<ul>
				<li class='cur'><em class='triangle'></em>全部（<span>${total }</span>）</li>
				<c:forEach var="userRuleVo" items="${userRuleVoes }" varStatus="status">
					<c:if test="${!status.last}">
						<li data-groupId='${userRuleVo.id }'><em class='triangle'></em>${userRuleVo.description }（<span>${fn:length(userRuleVo.users)}</span>）</li>
					</c:if>
					<c:if test="${status.last}">
						<li class='noB' data-groupId='${userRuleVo.id }'><em class='triangle'></em>${userRuleVo.description }（<span>${fn:length(userRuleVo.users)}</span>）</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class='userList' id='userList'>
			<table>
				<tr>
					<th>编号</th>
					<th>头像</th>
					<th>用户名</th>
					<th>所属分组</th>
					<th>注册时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="userRuleVo" items="${userRuleVoes }">
					<c:forEach var="user" items="${userRuleVo.users }">
						<tr class='users' data-username='${user.username }' data-groupid='${userRuleVo.id }' data-id='${user.id }'>
							<td>${user.id }</td>
							<td><img class='imgUrl' src='${user.imgurl }' /></td>
							<td>${user.username }</td>
							<td>${userRuleVo.description }</td>
							<td><fmt:formatDate value="${user.regtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td class='handle'>
								<a class='reset' href='javascript:void(0);' title='重置'>重置密码<i></i></a>
								<a class='move' href='javascript:void(0);' title='移动'>移动分组<i></i></a>
								<a class='delete' href='javascript:void(0);' title='删除'>删除用户<i></i></a>
							</td>
						</tr>
					</c:forEach>						
				</c:forEach>
				<tr id='userListBottom'>
					<td class='addUser' colspan='6'>
						<p class='emptyTip' id='emptyTip'>该组没有用户！</p>
						<a id='addUser' href='javascript:void(0);'>添加用户&nbsp;<i>+</i></a>
					</td>
				</tr>
			</table>
			<p class='userTotal'>当前用户总数：<span id='userTotal'>${total }</span></p>
		</div>
		<div class='clear'></div>
	</div>
</div>
<script>
	var users = [];
	
	<c:forEach var="userRuleVo" items="${userRuleVoes }">
		var groupId = ${userRuleVo.id },
			description = "${userRuleVo.description }",
			members = [];
		<c:forEach var="user" items="${userRuleVo.users }">
			members.push({
				id: ${user.id },
				imgurl: "${user.imgurl }",
				username: "${user.username }",
				description: "${userRuleVo.description }",
				regtime: "<fmt:formatDate value="${user.regtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
			});
		</c:forEach>
		users.push({
			groupId: groupId,
			description: description,
			members: members
		});
	</c:forEach>
</script>