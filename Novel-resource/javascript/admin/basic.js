require(['jquery', 'echarts', 'echarts/chart/pie', 'echarts/chart/line'], function($, ec) {

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

	// 搜索饼状图
	var pieChart = ec.init(document.getElementById('countDialog'));
	// 访问折线图
	var lineChart = ec.init(document.getElementById('siteDialog'));

	var pieOptions = [], lineOption, days = [];

	/* 初始化 */
	$.ajax({
		url: baseUrl + '/data/diagram',
		type: 'get',
		dataType: 'json',
		data: {
			date: $('#countType option:selected').text()
		},
		beforeSend: function(){
			$('#countType').attr('disabled', 'disabled');
			pieChart.showLoading({  
				text : "图表数据正在努力加载..."  
			});
			lineChart.showLoading({  
				text : "图表数据正在努力加载..."  
			});
		},
		complete: function() {
			$('#countType').removeAttr('disabled');
			pieChart.hideLoading(); 
			lineChart.hideLoading(); 
		},
		error: function() {
			alert('数据加载失败');
		},
		success: function(data) {
			if(data.success) {
				days = data.days;
				var	pvDataVoes = data.pvDataVoes, 
					searches = data.searches;

				// 对日期进行补全
				$.each(days, function(i, n){
					if(!pvDataVoes[i] || pvDataVoes[i].date != n) {
						var pvElem = {
							date: n,
							pvVo: [{
								type: 0,
								resultcount: 0
							}, {
								type: 1,
								resultcount: 0
							}]
						}
						pvDataVoes.splice(i, 0, pvElem);
					}
				});

				lineOption = getLineOption(pvDataVoes);
				lineChart.setOption(lineOption);

				pieOptions[pieOptions.length - 1] = getPieOption(searches, data.date);
				pieChart.setOption(pieOptions[pieOptions.length - 1]);
			} else {
				alert('数据加载失败');
			}
		}
	});

	$('#countType').change(function(){
		var index = $(this).val();
		if(pieOptions[index]) {
			pieChart.clear();
			pieChart.setOption(pieOptions[index]);
			return;
		}

		$.ajax({
			url: baseUrl + '/data/searchdiagram',
			type: 'get',
			dataType: 'json',
			data: {
				date: $('#countType option:selected').text()
			},
			beforeSend: function(){
				$('#countType').attr('disabled', 'disabled');
				pieChart.showLoading({  
					text : "图表数据正在努力加载..."  
				});
			},
			complete: function() {
				$('#countType').removeAttr('disabled');
				pieChart.hideLoading(); 
			},
			error: function() {
				alert('数据加载失败');
			},
			success: function(data) {
				if(data.success) {
					var	searches = data.searches;

					pieOptions[index] = getPieOption(searches, data.date);
					pieChart.clear();
					pieChart.setOption(pieOptions[index]);
				} else {
					alert('数据加载失败');
				}
			}
		});
	});

	//折线图
	function getLineOption(pvData){
		var d = [], fd = [], bd = [];

		for(var i = 0; i < days.length; i ++) {
			d.push(days[i].substring(5, 7) + days[i].substring(8));
		}

		$.each(pvData, function(i, n){
			if(!n.pvVo[0]) {
				if(!n.pvVo[1].type) {
					fd.push(n.pvVo[1].resultcount);
					bd.push(0);
				} else {
					bd.push(n.pvVo[1].resultcount);
					fd.push(0);
				}
			} else if(!n.pvVo[1]){
				if(!n.pvVo[0].type) {
					fd.push(n.pvVo[0].resultcount);
					bd.push(0);
				} else {
					bd.push(n.pvVo[0].resultcount);
					fd.push(0);
				}
			} else if(!n.pvVo[1].type) {
				fd.push(n.pvVo[0].resultcount);
				bd.push(n.pvVo[1].resultcount);
			} else {
				fd.push(n.pvVo[1].resultcount);
				bd.push(n.pvVo[0].resultcount);
			}
		});
		
		var option = {
				tooltip : {
					trigger: 'axis',
					formatter: function (params,ticket,callback) {
						var res = params[0].name.substring(0, 2) + '月' + params[0].name.substring(2) + '日';
						for (var i = 0, l = params.length; i < l; i++) {
							res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
						}
						return res;
					}
				},
				legend: {
					data:['前台访问量','后台访问量']
				},
				toolbox: {
					show : false
				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						boundaryGap : false,
						data : d
					}
				],
				yAxis : [
					{
						type : 'value'
					}
				],
				series : [
					{
						name:'前台访问量',
						type:'line',
						stack: '总量',
						data: fd,
					},
					{
						name:'后台访问量',
						type:'line',
						stack: '总量',
						data: bd,
					}
				]
		};
		return option;
	}

	// 饼状图
	function getPieOption(searchData, date){
		var d = [];

		$.each(searchData, function(i, n){
			d.push({
				value: n.times,
				name: n.keyword,
				result: n.resultcount
			});
		});

		if(!d.length) {
			d.push({
				value: 1,
				name: '无搜索记录',
				result: '本日无搜索记录'
			});
		}

		var option = {
			title : {
				text: date,
				subtext: '(top20)'
			},
			tooltip : {
				trigger: 'item',
				formatter: function(params,ticket,callback){
					if(!/^\d+$/.test(params.data.result)) {
						return params.data.name;
					}
					return params.data.name + ':' + params.data.value + '次(' + params[3] + '%)' + '<br />返回结果数：' + params.data.result
				}
			},
			legend: {
				show: false,
				data: []
			},
			toolbox: {
				show : false
			},
			series : [
				{
					name: date + '的数据',
					type: 'pie',
					center: ['50%', '45%'],
					radius: '50%',
					data: d
				}
			]
		}
		return option;
	};
});
