var baseUrl = '/Novel/static/';
$(function(){
	// 主页
	if(!$('body[data-page=main]').length) 
		return;

	/* ‘说明’淡入 */
	var introLi = $('.intro').find('li'),
		ilw = introLi.outerWidth(),
		ilh = introLi.outerHeight(),
		$window = $(window);

	introLi.addClass('introTrans');

	$window.on('scroll.fadeIn, resize.fadeIn', function(){
		fadeIn();
	});


	function fadeIn(){
		var sl = $window.scrollLeft(),
			st = $window.scrollTop(),
			wh = $window.height(),
			ww = $window.width();

		introLi.each(function(i, n){
			var io = $(n).offset();
			if(io.top >= st && io.top <= st + wh && io.left >= sl && io.left <= sl + ww) {
				$(this).removeClass('introTrans');
			} else if(ww <= ilw || wh <= ilh){
				introLi.removeClass('introTrans');
			}

			if(!introLi.filter('.introTrans').length){
				$window.off('.fadeIn');
			}
		});
	}

	fadeIn();

	/* 滚动播放声明 */
	var legalDetail = $('#legalDetail');
	
	var ld_width = legalDetail.width();
	var ld_left = legalDetail.position().left;

	(function(){
		var arg = arguments;

		legalDetail.animate({
			'left': - ld_width
		}, 35000, 'linear', function(){
			$(this).css('left', ld_left);
			arg.callee();
		});
	})();

	legalDetail.on('selectstart', function(){
		return false;
	});
});

$(function(){
	// 详情页
	if(!$('body[data-page=detail]').length)
		return;

	/* 右侧菜单固定 */
	var body = $('#body');
	var sider = body.find('#sider');
	var headFix = $('#headFix');
	var mainContent = $('.mainContent');

	var paragraphList = mainContent.find('.paragraphList');

	var b_off = body.offset();
	var b_height = body.outerHeight();
	var h_height = headFix.height();
	var p_off = paragraphList.offset();
	var p_height = paragraphList.outerHeight();
	var s_height = sider.outerHeight();

	$(window).scroll(sideFix).resize(function(){
		b_off = body.offset();
		sideFix();
	});

	function sideFix(){
		var top = $(window).scrollTop();

		// 10为margin
		if(top >= b_off.top - h_height - 10) {

			sider.css({
				'left': b_off.left,
				/*'top': b_off.top,*/
				'top': 0,
				'bottom': 'auto',
				'position': 'fixed'
			});

			if(sider.offset().top + s_height > b_off.top + b_height){
				sider.css({
					'position': 'absolute',
					'top': 'auto',
					'left': 0,
					'bottom': 0
				});
			}
		} else {
			sider.css('position', 'static');
		}
	}

	sideFix();

	/* 调整'body'的高度 */
	body.height(function(){
		if(sider.outerHeight(true) > mainContent.outerHeight(true)) {
			return sider.outerHeight(true);
		} else {
			return 'auto';
		}
	});

	/* 下拉菜单 */
	var openSlide = $('#openSlide'),
		slideDon = $('#slideDown');

	var slideShow = false;

	openSlide.hover(function(){
		slideShow = true;
		slideDon.stop().slideDown();
	}, function(){
		slideShow = false;
		setTimeout(function(){
			if(!slideShow){
				slideDon.stop().slideUp();
			}
		}, 100);
	});

	slideDon.hover(function(){
		openSlide.mouseenter();
	}, function(){
		openSlide.mouseleave();
	});
});

$(function(){
	// 搜索入口页
	if(!$('body[data-page=searchIndex]').length)
		return;

	var queryLong = $('#queryLong'),
		autocomplete = $('#autocomplete'),
		autocompleteList = autocomplete.find('#autocompleteList'),
		searchIndexForm = $('form[name=searchIndexForm]');

	var keys= [],
		wordsList = {},
		currentKey;

	queryLong.focus(function(){
		var key = $.trim($(this).val());
		autoComplete(key);
	}).blur(function(){
		setTimeout(function(){
			autocomplete.hide();
		}, 150);
	}).on('input', function(){
		var key = $.trim($(this).val());
		autoComplete(key);
	}).on('keydown', function(e){
		if(autocomplete.is(':hidden')){
			return;
		} else if(e.keyCode == 13){
			e.preventDefault();

			if(autocompleteList.find('.cur').length){
				queryLong.val(autocompleteList.find('.cur').text());
			}
			autocomplete.hide();
			searchIndexForm.submit();
		} else if(e.keyCode == 27) {
			autocomplete.hide();
		}

		var curLi = autocompleteList.find('.cur');
		var index = curLi.index();
		if(e.keyCode == 40){
			if(!curLi.length){
				autocompleteList.find('li').first().addClass('cur');
				return;
			}
			// 向下
			curLi.removeClass('cur');
			if(index == autocompleteList.find('li').length - 1){
				autocompleteList.find('li').first().addClass('cur');
			} else {
				curLi.next('li').addClass('cur');
			}
		} else if(e.keyCode == 38) {
			if(!curLi.length){
				autocompleteList.find('li').last().addClass('cur');
				return;
			}
			// 向上
			curLi.removeClass('cur');
			if(index == 0){
				autocompleteList.find('li').last().addClass('cur');
			} else {
				curLi.prev('li').addClass('cur');
			}
		}
	});

	queryLong.on('propertychange', function(){
		var key = $.trim($(this).val());
		autoComplete(key);
	});

	autocompleteList.on('click', 'li', function(){
		var keyword = $(this).text();
		queryLong.val(keyword);
	}).on('mouseover', 'li', function(){
		if($(this).is('.cur')) {
			return;
		}
		autocompleteList.find('.cur').removeClass('cur');
		$(this).addClass('cur');
	});

	function autoComplete(key){
		if(!key) {
			autocomplete.hide();
			return;
		}

		autocompleteList.find('li').remove();

		setTimeout(function(){
			if(key == $.trim($(queryLong).val())){
				toDo(); // 增加延迟
			}
		}, 500);

		// 进行自动补全之前的准备工作
		function toDo() {
			if($.inArray(key, keys) == -1){
				keys.push(key);
				currentKey = key; // 当前关键字标记

				$.ajax({
					url: baseUrl + 'hotKeywords',
					type: 'get',
					dataType: 'json',
					data: {
						key: key
					},
					success: function(data){
						if(data.success) {
							if(!data.keywords.length){
								return;
							}
							var arr = [];
							$.each(data.keywords, function(i, n){
								arr.push(n.keyword);
							});
							wordsList[key] = arr;
							
							if(key == currentKey)
								listHotByKey(wordsList[key]);
						} else {
							// 把数组中的相关数字移除
							var index = $.inArray(key, keys);
							if(index == -1) 
								return;
							keys.splice(index, 1);
						}
					},
					error: function(){
						// 把数组中的相关数字移除
						var index = $.inArray(key, keys);
						if(index == -1) 
								return;
						keys.splice(index, 1);
					}
				});
			} else if(wordsList[key]) {
				listHotByKey(wordsList[key]);
			}
		}
	}

	function listHotByKey(keywords){
		$.each(keywords, function(i, n){
			autocompleteList.append($('<li></li>').text(n));
		});

		autocomplete.show();
	}
});

$(function(){
	$(document).on('selectstart', function(){
		return false;
	}).on('dragstart', function(){
		return false;
	}).on('contextmenu', function(){
		return false;
	});

	/* 回到顶部处理 */
	var toTop = $('#toTop');

	$(window).scroll(function(){  //只要窗口滚动,就触发下面代码 
		shouldShowToTop();
	});

	function shouldShowToTop(){
		var scrollt = $(document).scrollTop();

		if( scrollt > 200){  //判断滚动后高度超过200px,就显示  
			toTop.css('visibility', 'visible');
		}else{      
			toTop.css('visibility', 'hidden');
		}
	}

	shouldShowToTop();

	/* 搜索提示 */
	var query = $('#query'),
		autocom = $('#autocom'),
		completeList = autocom.find('#completeList'),
		searchForm = $('form[name=search]');

	var isSending;

	query.focus(function(){
		if(!isSending) {
			isSending = true;

			$.ajax({
				url: baseUrl + 'hotKeywords',
				type: 'get',
				dataType: 'json',
				success: function(data){
					if(data.success) {
						$.each(data.keywords, function(i, n){
							completeList.append($('<li></li>').text(n.keyword));
						});

						listHot();
					} else {
						isSending = false;
					}
				},
				error: function(){
					isSending = false;
				}
			});
		} 
		if(completeList.find('li').length) {
			listHot();
		}
	}).blur(function(){
		setTimeout(function(){
			completeList.find('li').removeClass('cur');
			autocom.hide();
		}, 150);
	}).on('input', function(){
		listHot();
	}).on('keydown', function(e){
		if(autocom.is(':hidden')){
			return;
		} else if(e.keyCode == 13){
			e.preventDefault();

			if(completeList.find('.cur').length){
				query.val(completeList.find('.cur').text());
			}
			autocom.hide();
			searchForm.submit();
		} else if(e.keyCode == 27) {
			autocom.hide();
		}

		var curLi = completeList.find('.cur');
		var index = curLi.index();
		if(e.keyCode == 40){
			if(!curLi.length){
				completeList.find('li').first().addClass('cur');
				return;
			}
			// 向下
			curLi.removeClass('cur');
			if(index == completeList.find('li').length - 1){
				completeList.find('li').first().addClass('cur');
			} else {
				curLi.next('li').addClass('cur');
			}
		} else if(e.keyCode == 38) {
			if(!curLi.length){
				completeList.find('li').last().addClass('cur');
				return;
			}
			// 向上
			curLi.removeClass('cur');
			if(index == 0){
				completeList.find('li').last().addClass('cur');
			} else {
				curLi.prev('li').addClass('cur');
			}
		}
	});

	query.on('propertychange', function(){
		listHot();
	});

	completeList.on('click', 'li', function(){
		var keyword = $(this).text();
		query.val(keyword);
	}).on('mouseover', 'li', function(){
		if($(this).is('.cur')) {
			return;
		}
		completeList.find('.cur').removeClass('cur');
		$(this).addClass('cur');
	});

	function listHot() {
		if(!$.trim(query.val()) && query.is(':focus')){
			autocom.show();
		} else {
			autocom.hide();
		}
	}
});