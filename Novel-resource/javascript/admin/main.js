require.config({
    //配置angular的路径
	baseUrl: '/javascript',  // TODO 路径
    paths:{
		"jquery": "jquery-1.11.1",
		"echarts": "echarts/build/dist",
		"ztree": "admin/jquery.ztree.core-3.5",
		'migrate': 'admin/jquery-migrate-1.2.1',
		'jcrop': 'admin/jquery.Jcrop',
		'form': 'admin/jquery.form'
    },
	shim: {
		'jcrop': {
			deps: ['jquery'],
　　　　　　exports: 'jcrop'
		},
		'ztree': {
			deps: ['jquery'],
　　　　　　exports: 'ztree'
		},
		'migrate': {
			deps: ['jquery'],
　　　　　　exports: 'migrate'
		}
	}
});

require(['jquery'], function($) {
	/* 设置当前菜单 */
	var args = getArgs();
	var page = args['page'];

	if(!page || page == 0) {
		$('#frameLeft').find('[data-menu=info], [data-menu=basic]').addClass('cur now');
	} else if(page == 1) {
		$('#frameLeft').find('[data-menu=info], [data-menu=log]').addClass('cur now');
	} else if(page == 2) {
		$('#frameLeft').find('[data-menu=edit], [data-menu=data]').addClass('cur now');
	} else if(page == 3) {
		$('#frameLeft').find('[data-menu=manage], [data-menu=user]').addClass('cur now');
	} else if(page == 4) {
		$('#frameLeft').find('[data-menu=manage], [data-menu=myinfo]').addClass('cur now');
	}

	/* ajax全局设置 */
	$.ajaxSetup({
		dataType: 'json',
		timeout: 3000,
		complete: function (xhr, status) {
			logOut(xhr);
		}
	});

	/* 回到顶部处理 */
	var toTop = $('#toTop');

	$('#frameRight').scroll(function(){  //只要窗口滚动,就触发下面代码 
		shouldShowToTop();
	});

	function shouldShowToTop(){
		var scrollt = $('#frameRight').scrollTop();

		if( scrollt > 200){  //判断滚动后高度超过200px,就显示  
			toTop.css('visibility', 'visible');
		}else{      
			toTop.css('visibility', 'hidden');
		}
	}

	shouldShowToTop();

	/* 菜单切换 */
	$('#frameLeft').find('h2').click(function(){
		$('#frameLeft').find('h2').removeClass('cur').next('ul').stop().slideUp(300).removeClass('cur');
		$(this).addClass('cur').next('ul').stop().slideDown(300).addClass('cur');

	});
});

function StringBuffer () {
  this._strings_ = new Array();
}

StringBuffer.prototype.append = function(str) {
  this._strings_.push(str);
};

StringBuffer.prototype.toString = function() {
  return this._strings_.join("");
};

StringBuffer.prototype.reset = function() {
  this._strings_.length = 0;
};

// 获取查询字符串
function getArgs(){
	var args = [];
	var qs = location.search.length > 0 ? location.search.substring(1) : '';
	var items = qs.split('&');
	var item = null, name = null, value = null;

	for(var i = 0; i < items.length; i ++) {
		item = items[i].split('=');
		name = item[0];
		value = item[1];
		args[name] = value;
	}
	return args;
}

function logOut(xhr) {
	var sessionstatus = xhr.getResponseHeader("sessionstatus");
	if(sessionstatus && sessionstatus == 'timeout') {
		window.location.replace('/Novel/admin/main'); // 登录超时
		return;
	}
}