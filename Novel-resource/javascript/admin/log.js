require(['jquery'], function($) {

	var baseUrl = "/Novel/admin";

	/* 切换选项卡 */
	$('#logType').find('li').click(function(){
		if($(this).is('.cur')){
			return;
		}

		$('#logType').find('li.cur').removeClass('cur');
		$(this).addClass('cur');

		var index = $(this).index();

		$('#logDate').find('option').remove();

		$.each(logNames[index], function(i, n){
			if(i == 0){
				var dom = "<option value='" + n + "' selected='selected'>" + n + "</option>";
			} else {
				var dom = "<option value='" + n + "'>" + n + "</option>";
			}

			$('#logDate').append(dom);
		});

		$('#logDate').change();	
	});

	$('#logDate').change(function(){
		var fileName = $(this).val();
		getLog(fileName);
	});

	// 获取日志内容
	var logs = [];

	if(defaultLogContent){
		logs.push({
			fileName: $('#logDate').val(),
			logContent: defaultLogContent
		});		
	}

	function getLog(fileName) {
		var exist = false;

		$.each(logs, function(i, n){
			if(n.fileName == fileName) {
				$('#log').html(n.logContent);
				exist = true;
				return false;
			}
		});

		if(!exist){
			$.ajax({
				url: baseUrl + "/log/getLogContent/easy",
				dataType: "json",
				method: "get",
				data: {
					fileName: fileName
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
				success: function(data){
					if(data.success) {
						$('#log').html(data.content);

						logs.push({
							fileName: fileName,
							logContent: data.content
						});		
					} else {
						alert('请求不成功！');
					}
				},
				error: function(){
					alert('请求不成功！');
				}
			});
		}
	}
});
