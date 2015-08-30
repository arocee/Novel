$(function(){
	// 主页
	if(!$('body[data-page=main]').length) 
		return;

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
		})
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
				'top': b_off.top,
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
});