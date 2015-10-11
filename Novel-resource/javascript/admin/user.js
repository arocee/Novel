require(['jquery'], function($) {

	var baseUrl = "/Novel/admin";

	var id, groupId, username, u;

	/* 切换分组查看 */
	$('#group').find('li').click(function(){
		if($(this).is('.cur')) return;
		$(this).siblings('.cur').removeClass('cur');

		$(this).addClass('cur');

		var groupId = $(this).get(0).dataset.groupid;

		$('#userList').find('.users').remove();

		if(!groupId) {
			// 查看全部
			$.each(users, function(i, n){
				createUserList(n.members, n.groupId);
			});
		} else {
			$.each(users, function(i, n){
				if(groupId == n.groupId){
					createUserList(n.members, n.groupId);

					return false;
				}
			});
		}
	});

	/* 重置密码 */
	$('#userList').on('click', '.reset', function(){
		if(window.confirm('是否重置该用户的密码？')) {
			var id = $(this).parents('tr').get(0).dataset.id;

			$.ajax({
				url: baseUrl + '/user/reset/easy',
				type: 'post',
				dataType: 'json',
				data: {
					id: id
				},
				beforeSend: function(){
					$('#cover').stop().fadeIn();
					$('#loading').stop().show();
				},
				complete: function(xhr, status){
					logOut(xhr);

					$('#loading').stop().hide();
					$('#cover').stop().fadeOut();
				},
				error: function(){
					alert('请求不成功！');
				},
				success: function(data){
					if(data.success) {
						alert('密码重置成功！');
					} else {
						alert(data.msg);
					}
					resetData(); // 复位数据
				}
			});
		}
	});

	/* 移动分组 */
	$('#userList').on('click', '.move', function(){
		groupId = $(this).parents('tr').get(0).dataset.groupid,
		username = $(this).parents('tr').get(0).dataset.username,
		id = $(this).parents('tr').get(0).dataset.id,
		u = '/user/move/easy';

		$('#cover').stop().fadeIn();
		$('#userfield').stop().fadeIn().find('input[name=username]').attr('disabled', 'disabled').val(username).focus().end().find('select[name=group]').val(groupId);
	});

	/* 删除用户 */
	$('#userList').on('click', '.delete', function(){
		if(window.confirm('是否删除该用户？')) {
			id = $(this).parents('tr').get(0).dataset.id,
			groupId = $(this).parents('tr').get(0).dataset.groupid;

			if(!id || !/^\d+$/.test(id) || !groupId || !/^\d+$/.test(groupId)) {
				alert('丢失关键数据');
				return false;
			}

			$.ajax({
				url: baseUrl + '/user/del/easy',
				type: 'post',
				data: {
					id: id
				},
				beforeSend: function(){
					$('#cover').stop().fadeIn();
					$('#loading').stop().show();
				},
				complete: function(xhr, status){
					logOut(xhr);

					$('#loading').stop().hide();
					$('#cover').stop().fadeOut();
				},
				error: function(){
					alert('请求不成功！');
				},
				success: function(data){
					if(data.success) {
						$('#group').find('li[data-groupId=' + groupId + '], li:first').find('span').html(function(){
							return parseInt($(this).html()) - 1;
						});

						$('#userTotal').html(function(){
							return parseInt($(this).html()) - 1;
						});

						$.each(users, function(i, n){
							if(groupId == n.groupId){
								$.each(n.members, function(j, m){
									if(m.id == id) {
										n.members.splice(j, 1);
										return false;
									}
								});

								var curGroupId = $('#group').find('.cur').get(0).dataset.groupid;

								if(curGroupId == groupId){
									$('#userList').find('.users').remove();
									createUserList(n.members, groupId);  
								} else if(!curGroupId) {
									$('#userList').find('.users').remove();
									// 查看全部
									$.each(users, function(i, n){
										createUserList(n.members, n.groupId);
									});
								}else {
									$('#group').find('li').eq(0).click();
								}

								return false;
							}
						});
					} else {
						alert(data.msg);
					}

					resetData(); // 复位数据
				}
			});
		}
	});

	/* 添加用户 */
	$('#addUser').click(function(){
		u = '/user/reg/easy';

		$('#cover').stop().fadeIn();
		$('#userfield').stop().fadeIn().find('input[name=username]').removeAttr('disabled').val('').focus().end().find('select[name=group]').val(2);  // 默认为游客
	});

	/* 取消 */
	$('#userfield').find('.cancel').click(function(){
		resetData(); // 复位数据
		$('#userfield').fadeOut();
		$('#cover').fadeOut();
	});

	/* 提交 */
	$('#userfield').find('.confirm').click(function(){
		if(id) {
			// 移动分组
			var orignalGroupId = groupId;
			groupId = $('#userfield').find('select[name=group]').val();

			// 不需要移动
			if(orignalGroupId == groupId) {
				$('#userfield').stop().hide();
				$('#cover').stop().fadeOut();
			} else {
				if(!/^\d+$/.test(id) || !groupId || !/^\d+$/.test(groupId)) {
					alert('丢失关键数据');
					return;
				} else {
					$.ajax({
						url: baseUrl + u,
						type: 'post',
						data: {
							id: id,
							rule: groupId
						},
						beforeSend: function(){
							$('#userfield').stop().hide();
							$('#cover').stop().fadeIn();
							$('#loading').stop().show();
						},
						complete: function(xhr, status){
							logOut(xhr);

							$('#loading').stop().hide();
						},
						error: function(){
							$('#userfield').stop().fadeIn(function(){
								alert('请求不成功！');
							});
						},
						success: function(data){
							if(data.success) {
								$('#group').find('li[data-groupId=' + groupId + ']').find('span').html(function(){
									return parseInt($(this).html()) + 1;
								});
								$('#group').find('li[data-groupId=' + orignalGroupId + ']').find('span').html(function(){
									return parseInt($(this).html()) - 1;
								});

								var orgGrp, grp, des;

								$.each(users, function(i, n){
									if(groupId == n.groupId){
										grp = n.members,
										des = n.description;
									} else if(orignalGroupId == n.groupId) {
										orgGrp = n.members;
									}

									if(grp && orgGrp) {
										return false;
									}
								});

								// 数组移动
								$.each(orgGrp, function(i, n){
									if(n.id == id) {
										var user = orgGrp.splice(i, 1)[0];
										user['description'] = des;
										grp.push(user);
										return false;
									}
								});

								var curGroupId = $('#group').find('.cur').get(0).dataset.groupid;

								if(curGroupId == groupId){
									$('#userList').find('.users').remove();
									createUserList(grp, groupId);  
								} else if(!curGroupId) {
									$('#userList').find('.users').remove();
									// 查看全部
									$.each(users, function(i, n){
										createUserList(n.members, n.groupId);
									});
								}else {
									$('#group').find('li').eq(0).click();
								}

								resetData(); // 复位数据

								$('#cover').stop().fadeOut();
							} else {
								$('#userfield').stop().fadeIn(function(){
									alert(data.msg);
								});
							}
						}
					});
				}
			}
		} else {
			// 添加用户
			username = $('#userfield').find('input[name=username]').val().trim(),
			groupId = $('#userfield').find('select[name=group]').val();

			if(!username || !groupId || !/^\d$/.test(groupId)) {
				alert('丢失关键数据!');
				return;
			}

			if(username.length > 10 || username.length < 5 || !/^\w+$/.test(username)) {
				alert('用户名必须是5到10位，数字字母或下划线!');
				return;
			}

			// 验证用户名是否存在
			var exist = false;
			$.each(users, function(i, n){
				$.each(n.members, function(j, m){
					if(m.username == username){
						exist = true;
						return false;
					}
				});
			});

			if(exist){
				alert('该用户名已存在！');
				return;
			}

			$.ajax({
				url: baseUrl + u,
				type: 'post',
				data: {
					username: username,
					rule: groupId
				},
				beforeSend: function(){
					$('#userfield').stop().hide();
					$('#cover').stop().fadeIn();
					$('#loading').stop().show();
				},
				complete: function(xhr, status){
					logOut(xhr);

					$('#loading').stop().hide();
				},
				error: function(){
					$('#userfield').stop().fadeIn(function(){
						alert('请求不成功！');
					});
				},
				success: function(data){
					if(data.success) {
						$('#group').find('li[data-groupId=' + groupId + '], li:first').find('span').html(function(){
							return parseInt($(this).html()) + 1;
						});

						$('#userTotal').html(function(){
							return parseInt($(this).html()) + 1;
						});

						$.each(users, function(i, n){
							if(groupId == n.groupId){
								n.members.push({
									id: data.id,
									imgurl: data.imgurl,
									username: username,
									description: n.description,
									regtime: data.regtime
								});

								var curGroupId = $('#group').find('.cur').get(0).dataset.groupid;

								if(curGroupId == groupId){
									$('#userList').find('.users').remove();
									createUserList(n.members, n.groupId);
								} else if(!curGroupId){
									$('#userList').find('.users').remove();
									// 查看全部
									$.each(users, function(i, n){
										createUserList(n.members, n.groupId);
									});
								} else {
									$('#group').find('li').eq(0).click();
								}

								return false;
							}
						});
		
						resetData(); // 复位数据

						$('#cover').stop().fadeOut();
					} else {
						$('#userfield').stop().fadeIn(function(){
							alert(data.msg);
						});
					}
				}
			});
		}
	}).end().find('input[name=username]').keydown(function(e){
		if(e.keyCode == 13) {
			$('#userfield').find('.confirm').click();
		}
	});

	/**
	* 创建用户列表
	* @param list
	*/
	function createUserList(list, groupId) {
		$('#emptyTip').stop().hide();

		$.each(list, function(i, n){
			var dom = "<tr class='users' data-username='" + n.username + "' data-groupid='" + groupId + "' data-id='" + n.id + "'>" + 
						"<td>" + n.id + "</td>" + 
						"<td><img class='imgUrl' src='" + n.imgurl + "' /></td>" + 
						"<td>" + n.username + "</td>" + 
						"<td>" + n.description + "</td>" + 
						"<td>" + n.regtime + "</td>" + 
						"<td class='handle'>" + 
							"<a class='reset' href='javascript:void(0);' title='重置'>重置密码<i></i></a> " + 
							"<a class='move' href='javascript:void(0);' title='移动'>移动分组<i></i></a> " + 
							"<a class='delete' href='javascript:void(0);' title='删除'>删除用户<i></i></a>" + 
						"</td>"
					"</tr>";
			$('#userListBottom').before(dom);
		});

		if(!$('#userList').find('.users').length){
			$('#emptyTip').stop().fadeIn();
		}
	}

	/**
	* 重置数据
	*/
	function resetData() {
		id = null;
		groupId = null;
		username = null;
	}
});
