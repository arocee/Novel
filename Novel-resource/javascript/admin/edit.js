require(['jquery'], function($) {

	var baseUrl = "/Novel/admin";

	$.ajaxSetup({
		dataType: 'json',
		timeout: 3000,
		complete: function (xhr, status) {
			var sessionstatus = xhr.getResponseHeader("sessionstatus");
			if(sessionstatus && sessionstatus == 'timeout') {
				window.location.replace(baseUrl + '/main'); // 登录超时
				return;
			}
		}
	});

	/* 增 */
	var addModel, editModel;
	var tid, aid, sid, pid, type, article, section, paragraph;
	$('.newAdd').click(function(){
		var admin = $(this).parents('.admin');
		var parent = admin.prev('.admin');
		var clazz = admin.find('> h1').html();
		
		if(parent.length)
			var par = parent.find('.cur').find('a').html();
		else
			var par = '';

		var _this = this;

		$('#cover').fadeIn();
		$('#addfield').find('.par').html(par).end().find('.clazz').html(clazz).end().find('input[name=name]').val('').end().fadeIn(function(){
			if($(_this).is('#addArticle')) {
				addModel = "addArticle";
				tid = $('#typelist').find('.cur').get(0).dataset.id;
				$('#addfield').find('input[name=name]').attr('maxlength', 5);
			} else if($(_this).is('#addSection')) {
				addModel = "addSection";
				aid = $('#articlelist').find('.cur').get(0).dataset.id;
				$('#addfield').find('input[name=name]').attr('maxlength', 5);
			} else if($(_this).is('#addParagraph')) {
				addModel = "addParagraph";
				sid = $('#sectionlist').find('.cur').get(0).dataset.id;
				$('#addfield').find('input[name=name]').attr('maxlength', 500);
			}

			$(this).find('input[name=name]').focus();
		});
	});

	/* 取消 */
	$('.cancel').click(function(){
		dataInit(); // 复位数据

		$(this).parents('.dialog').fadeOut();
		$('#cover').fadeOut();
	});

	/* 回车提交 */
	$('.dialog').find('input[name=name]').keyup(function(e){
		if(e.keyCode == 13) {
			$(this).parents('.dialog').find('.confirm').click();
		}
	});

	/* 提交 */
	$('.confirm').click(function(){
		var dialog = $(this).parents('.dialog');
		var name = dialog.find('input[name=name]').val();

		if(!name) {
			alert('请输入内容！');
			return;
		}

		if(dialog.is('#addfield')){
			// 添加 
			if(addModel == "addArticle") {
				if(!tid || !/^\d+$/.test(tid)) {
					alert('丢失关键参数');
					return;
				} else if(!$('#addfield').find('input[name=name]').val()){
					alert('不能为空');
					return;
				} else if($('#addfield').find('input[name=name]').val().length > 5){
					alert('字数超过限制');
					return;
				}
				var u = "/addArticle/easy";
				var d = "tid=" + tid + "&article=" + encodeURI(name);
			} else if(addModel == "addSection") {
				if(!aid || !/^\d+$/.test(aid)) {
					alert('丢失关键参数');
					return;
				} else if(!$('#addfield').find('input[name=name]').val()){
					alert('不能为空');
					return;
				} else if($('#addfield').find('input[name=name]').val().length > 5){
					alert('字数超过限制');
					return;
				}
				var u = "/addSection/easy";
				var d = "aid=" + aid + "&section=" + encodeURI(name);
			} else if(addModel == "addParagraph") {
				if(!sid || !/^\d+$/.test(sid)) {
					alert('丢失关键参数');
					return;
				} else if(!$('#addfield').find('input[name=name]').val()){
					alert('不能为空');
					return;
				} else if($('#addfield').find('input[name=name]').val().length > 500){
					alert('字数超过限制');
					return;
				}
				var u = "/addParagraph/easy";
				var d = "sid=" + sid + "&paragraph=" + encodeURI(name);
			}

			$.ajax({
				url: baseUrl + u,
				type: 'post',
				data: d,
				beforeSend: function(){
					$('#addfield').stop().hide();
					$('#cover').stop().fadeIn();
					$('#loading').stop().show();
				},
				complete: function(){
					$('#loading').stop().hide();
				},
				error: function(){
					$('#addfield').stop().fadeIn(function(){
						alert('请求不成功！');
					});
				},
				success: function(data){
					if(data.success) {
						if(addModel == "addArticle") {
							var dom = "<span data-id='" + data.id + "' data-tid='" + tid + "'><a href='javascript:void(0);'>" + name + "</a><i class='edit' title='编辑'></i><i class='delete' title='删除'></i></span>";
							$('#articlelist').append(dom);
						} else if(addModel == "addSection") {
							var dom = "<span data-id='" + data.id + "' data-aid='" + aid + "'><a href='javascript:void(0);'>" + name + "</a><i class='edit' title='编辑'></i><i class='delete' title='删除'></i></span>";
							$('#sectionlist').append(dom);
						} else if(addModel == "addParagraph") {
							var dom = "<li data-id='" + data.id + "' data-sid='" + sid + "'>" + 
										"<p>" + name + "</p>" + 
										"<div>" + 
											"<a class='edit' href='javascript:void(0);' title='编辑'>编辑<i></i></a><a class='delete' href='javascript:void(0);' title='删除'>删除<i></i></a>" + 
										"</div>" + 
									"</li>"
							$('#paragraphlist').append(dom);
						}

						dataInit(); // 复位数据

						$('#cover').stop().fadeOut();
					} else {
						$('#addfield').stop().fadeIn(function(){
							alert('请求不成功！');
						});
					}
				}
			});
		} else if(dialog.is('#editfield')) {
			// 编辑
			if(editModel == "editArticle") {
				if(!aid || !/^\d+$/.test(aid)) {
					alert('丢失关键参数');
					return;
				} else if(!$('#editfield').find('input[name=name]').val()){
					alert('不能为空');
					return;
				} else if($('#editfield').find('input[name=name]').val().length > 5){
					alert('字数超过限制');
					return;
				} else if($('#editfield').find('input[name=name]').val().trim() == $('#articlelist').find('> span[data-id=' + aid + ']').find('a').html()){
					// 没有修改
					$('#editfield').stop().hide();
					$('#cover').stop().fadeOut();
					return;
				}
				var u = "/updateArticle/easy";
				var d = "id=" + aid + "&article=" + encodeURI(name);
			} else if(editModel == "editSection") {
				if(!sid || !/^\d+$/.test(sid)) {
					alert('丢失关键参数');
					return;
				} else if(!$('#editfield').find('input[name=name]').val()){
					alert('不能为空');
					return;
				} else if($('#editfield').find('input[name=name]').val().length > 5){
					alert('字数超过限制');
					return;
				} else if($('#editfield').find('input[name=name]').val().trim() == $('#sectionlist').find('> span[data-id=' + sid + ']').find('a').html()){
					// 没有修改
					$('#editfield').stop().hide();
					$('#cover').stop().fadeOut();
					return;
				}
				var u = "/updateSection/easy";
				var d = "id=" + sid + "&section=" + encodeURI(name);
			} else if(editModel == "editParagraph") {
				if(!pid || !/^\d+$/.test(pid)) {
					alert('丢失关键参数');
					return;
				} else if(!$('#editfield').find('input[name=name]').val()){
					alert('不能为空');
					return;
				} else if($('#editfield').find('input[name=name]').val().length > 500){
					alert('字数超过限制');
					return;
				}else if($('#editfield').find('input[name=name]').val().trim() == $('#paragraphlist').find('> li[data-id=' + pid + ']').find('p').html()){
					// 没有修改
					$('#editfield').stop().hide();
					$('#cover').stop().fadeOut();
					return;
				}
				var u = "/updateParagraph/easy";
				var d = "pid=" + pid + "&paragraph=" + encodeURI(name);
			}

			$.ajax({
				url: baseUrl + u,
				type: 'post',
				data: d,
				beforeSend: function(){
					$('#editfield').stop().hide();
					$('#cover').stop().fadeIn();
					$('#loading').stop().show();
				},
				complete: function(){
					$('#loading').stop().hide();
				},
				error: function(){
					$('#editfield').stop().fadeIn(function(){
						alert('请求不成功！');
					});
				},
				success: function(data){
					if(data.success) {
						if(editModel == "editArticle") {
							$('#articlelist').find('> span[data-id=' + aid + ']').find('a').html(name);
						} else if(editModel == "editSection") {
							$('#sectionlist').find('> span[data-id=' + sid + ']').find('a').html(name);
						} else if(editModel == "editParagraph") {
							$('#paragraphlist').find('> li[data-id=' + pid + ']').find('p').html(name);
						}
					
						dataInit(); // 复位数据

						$('#cover').stop().fadeOut();
					} else {
						$('#editfield').stop().fadeIn(function(){
							alert('请求不成功！');
						});
					}
				}
			});
		}
	});

	/* 删， 改 */
	$('.list').on('click', '.delete', function(e){
		e.stopPropagation();
		if(window.confirm('是否删除该条？')){
			var list = $(this).parents('.list');

			if(list.is('.articlelist')) {
				var id = $(this).parent('span').get(0).dataset.id;
				var url = "/deleteArticle/easy";
				var idd = 'aid';
			} else if(list.is('.sectionlist')) {
				var id = $(this).parent('span').get(0).dataset.id;
				var url = "/deleteSection/easy";
				var idd = 'sid';
			} else if(list.is('.paragraphlist')) {
				var id = $(this).parents('li').get(0).dataset.id;
				var url = "/deleteParagraph/easy"
				var idd = 'pid';
			}

			var _this = this;

			$.ajax({
				url: baseUrl + url,
				type: 'get',
				data: idd + '=' + id,
				beforeSend: function(){
					$('#cover').stop().fadeIn();
					$('#loading').stop().show();
				},
				complete: function(){
					$('#loading').stop().hide();
					$('#cover').stop().fadeOut();
				},
				error: function(){
					$(this).removeClass('cur');
					alert('请求不成功！');
				},
				success: function(data){
					if(data.success) {
						if(idd == 'aid') {
							var $_this = $(_this).parent('span');
							var isCur = false;
							if($_this.is('.cur')) {
								isCur = true;
							}
							$_this.remove();
							if(isCur && $('#articlelist').find('> span').length){
								$('#articlelist').find('> span').eq(0).click();
							}
						} else if(idd == 'sid') {
							var $_this = $(_this).parent('span');
							var isCur = false;
							if($_this.is('.cur')) {
								isCur = true;
							}
							$_this.remove();
							if(isCur && $('#sectionlist').find('> span').length){
								$('#sectionlist').find('> span').eq(0).click();
							}
						} else if(idd == 'pid') {
							$(_this).parents('li').remove();
						}
					} else {
						alert('请求不成功！');
					}
				}
			});
		}
	}).on('click', '.edit', function(e){
		e.stopPropagation();

		var list = $(this).parents('.list');

		if(list.is('.articlelist')) {
			aid = $(this).parent('span').get(0).dataset.id;
			var clazz = 'Type';
			var clazzName = $('#typelist').find('span.cur').find('a').html();
			var val = $(this).parent('span').find('a').html();
		} else if(list.is('.sectionlist')) {
			sid = $(this).parent('span').get(0).dataset.id;
			var clazz = 'Article';
			var clazzName = $('#articlelist').find('span.cur').find('a').html();
			var val = $(this).parent('span').find('a').html();
		} else if(list.is('.paragraphlist')) {
			pid = $(this).parents('li').get(0).dataset.id;
			var clazz = 'Section';
			var clazzName = $('#sectionlist').find('span.cur').find('a').html();
			var val = $(this).parents('li').find('p').html();
		}

		var _this = this;

		$('#cover').fadeIn();
		$('#editfield').find('.clazz').html(clazz).end().find('.clazzName').html(clazzName).end().find('input[name=name]').val(val).end().fadeIn(function(){
			if($(_this).parents('#articlelist').length) {
				editModel = "editArticle";
				$('#addfield').find('input[name=name]').attr('maxlength', 5);
			} else if($(_this).parents('#sectionlist').length) {
				editModel = "editSection";
				$('#addfield').find('input[name=name]').attr('maxlength', 5);
			} else if($(_this).parents('#paragraphlist').length) {
				editModel = "editParagraph";
				$('#addfield').find('input[name=name]').attr('maxlength', 500);
			}

			$(this).find('input[name=name]').focus();
		});
	});

	/* 查 */
	var sb = new StringBuffer();

	$('.list').on('click', 'span', function(){
		if($(this).is('.cur')) return;

		var pre_cur = $(this).siblings('.cur').removeClass('cur');
		$(this).addClass('cur');

		var id = $(this).get(0).dataset.id;

		var list = $(this).parents('.list');

		if(list.is('.typelist')) {
			var idd = 'tid';
		} else if(list.is('.articlelist')) {
			var idd = 'aid';
		} else if(list.is('.sectionlist')) {
			var idd = 'sid';
		}

		$.ajax({
			url: baseUrl + '/listNovel/easy',
			type: 'get',
			data: idd + '=' + id,
			error: function(){
				var pre_cur = $(this).siblings('.cur').addClass('cur');
				$(this).removeClass('cur');
				alert('请求不成功！');
			},
			success: function(data){
				if(data.success) {
					if(idd == 'tid') {
						$('#articlelist').html('');
						if(!data.articles) return;
						$.each(data.articles, function(i, a){
							var dom = "<span data-id='" + a.id + "' data-tid='" + a.tid + "'><a href='javascript:void(0);'>" + a.article + "</a><i class='edit' title='编辑'></i><i class='delete' title='删除'></i></span>&nbsp;";
							sb.append(dom);
						});
						
						$('#articlelist').html(sb.toString()).find(' > span').eq(0).addClass('cur');

						sb.reset();
						idd = 'aid';
					} 
					if(idd == 'aid') {
						$('#sectionlist').html('');
						if(!data.sections) return;
						$.each(data.sections, function(i, s){
							var dom = "<span data-id='" + s.id + "' data-tid='" + s.aid + "'><a href='javascript:void(0);'>" + s.section + "</a><i class='edit' title='编辑'></i><i class='delete' title='删除'></i></span>&nbsp;";
							sb.append(dom);
						});
						
						$('#sectionlist').html(sb.toString()).find(' > span').eq(0).addClass('cur');

						sb.reset();
						idd = 'sid';
					}
					if(idd == 'sid') {
						$('#paragraphlist').html('');
						if(!data.paragraphes) return;
						$.each(data.paragraphes, function(i, p){
							var dom = "<li data-id='" + p.id + "' data-sid='" + p.sid + "'>" + 
										"<p>" + p.paragraph + "</p>" + 
										"<div><a class='edit' href='javascript:void(0);' title='编辑'>编辑<i></i></a><a class='delete' href='javascript:void(0);' title='删除'>删除<i></i></a></div>" + 
									"</li>";
							sb.append(dom);
						});
						
						$('#paragraphlist').html(sb.toString());

						sb.reset();
					}
				} else {
					var pre_cur = $(this).siblings('.cur').addClass('cur');
					$(this).removeClass('cur');
					alert('请求不成功！');
				}
			}
		});
	});

	$(document).keyup(function(e){
		if(e.keyCode == 27) {
			if($('.dialog:visible').length) {
				dataInit(); // 复位数据

				$('.dialog').stop().fadeOut();
				$('#cover').fadeOut();
			}
		}
	});

	/* 重置索引 */
	$('#resetindex').click(function(){
		if(window.confirm('是否重置全文索引？')) {
			$.ajax({
				url: baseUrl + "/resetIndex/easy",
				type: 'get',
				beforeSend: function(){
					$('#cover').stop().fadeIn();
					$('#loading').stop().show();
				},
				complete: function(){
					$('#loading').stop().hide();
					$('#cover').stop().fadeOut();
				},
				error: function(){
					alert('请求不成功！');
				},
				success: function(data){
					if(data.success) {
						alert('重置索引成功！');
					} else {
						alert('请求不成功！');
					}
				}
			});
		}
	});

	/* 所有数据复位 */
	function dataInit(){
		addModel = null;
		tid = null;
		aid = null;
		sid = null;
	}
});