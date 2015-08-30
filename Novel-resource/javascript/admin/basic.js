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
	var myChart = ec.init(document.getElementById('countDialog'));
	var option = pie();
	// 为echarts对象加载数据 
	myChart.setOption(option);

	// 访问折线图
	var myChart2 = ec.init(document.getElementById('siteDialog'));
	var option2 = lineOption();
	myChart2.setOption(option2);

	// 折线图
	function lineOption(){
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
						data : ['0816', '0817', '0818', '0819', '0820', '0821', '0822']
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
						data:[120, 132, 101, 134, 90, 230, 210]
					},
					{
						name:'后台访问量',
						type:'line',
						stack: '总量',
						data:[220, 182, 191, 234, 290, 330, 310]
					}
				]
		};
		return option;
	}

	// 饼状图
	function pie(){
		var option = {
			timeline : {
				data : [
					'2015-08-16', '2015-08-17', '2015-08-18', '2015-08-19', '2015-08-20',
					'2015-08-21', '2015-08-22'
				],
				label : {
					formatter : function(s) {
						return s.slice(5);
					}
				},
				x : '10px',
				width : '90%',
				currentIndex : 6
			},
			options : [
				{
					title : {
						text: '最近7天',
						subtext: '（top20）'
					},
					tooltip : {
						trigger: 'item',
						formatter: "{b} : {c} ({d}%)"
					},
					legend: {
						show: false,
						data:['Chrome','Firefox','Safari','IE9+','IE8-','IE10-','IE11-', 'edge']
					},
					toolbox: {
						show : false
					},
					series : [
						{
							name:'搜索信息（数据纯属虚构）',
							type:'pie',
							center: ['50%', '45%'],
							radius: '50%',
							data:[
								{value: 100,  name:'Chrome'},
								{value: 50,  name:'Firefox'},
								{value: 32,  name:'Safari'},
								{value: 17,  name:'IE9+'},
								{value: 5, name:'IE8-'},
								{value: 2, name:'IE10-'},
								{value: 1, name:'IE11-'},
								{value: 0, name:'edge'}
							]
						}
					]
				},
				{
					series : [
						{
							name:'浏览器（数据纯属虚构）',
							type:'pie',
							data:[
								{value: 100,  name:'Chrome'},
								{value: 50,  name:'Firefox'},
								{value: 32,  name:'Safari'},
								{value: 17,  name:'IE9+'},
								{value: 5, name:'IE8-'},
								{value: 2, name:'IE10-'},
								{value: 1, name:'IE11-'},
								{value: 0, name:'edge'}
							]
						}
					]
				},
				{
					series : [
						{
							name:'浏览器（数据纯属虚构）',
							type:'pie',
							data:[
								{value: 100,  name:'Chrome'},
								{value: 50,  name:'Firefox'},
								{value: 32,  name:'Safari'},
								{value: 17,  name:'IE9+'},
								{value: 5, name:'IE8-'},
								{value: 2, name:'IE10-'},
								{value: 1, name:'IE11-'},
								{value: 0, name:'edge'}
							]
						}
					]
				},
				{
					series : [
						{
							name:'浏览器（数据纯属虚构）',
							type:'pie',
							data:[
								{value: 100,  name:'Chrome'},
								{value: 50,  name:'Firefox'},
								{value: 32,  name:'Safari'},
								{value: 17,  name:'IE9+'},
								{value: 5, name:'IE8-'},
								{value: 2, name:'IE10-'},
								{value: 1, name:'IE11-'},
								{value: 0, name:'edge'}
							]
						}
					]
				},
				{
					series : [
						{
							name:'浏览器（数据纯属虚构）',
							type:'pie',
							data:[
								{value: 100,  name:'Chrome'},
								{value: 50,  name:'Firefox'},
								{value: 32,  name:'Safari'},
								{value: 17,  name:'IE9+'},
								{value: 5, name:'IE8-'},
								{value: 2, name:'IE10-'},
								{value: 1, name:'IE11-'},
								{value: 0, name:'edge'}
							]
						}
					]
				},
				{
					series : [
						{
							name:'浏览器（数据纯属虚构）',
							type:'pie',
							data:[
								{value: 100,  name:'Chrome'},
								{value: 50,  name:'Firefox'},
								{value: 32,  name:'Safari'},
								{value: 17,  name:'IE9+'},
								{value: 5, name:'IE8-'},
								{value: 2, name:'IE10-'},
								{value: 1, name:'IE11-'},
								{value: 0, name:'edge'}
							]
						}
					]
				},
				{
					series : [
						{
							name:'浏览器（数据纯属虚构）',
							type:'pie',
							data:[
								{value: 100,  name:'Chrome'},
								{value: 50,  name:'Firefox'},
								{value: 32,  name:'Safari'},
								{value: 17,  name:'IE9+'},
								{value: 5, name:'IE8-'},
								{value: 2, name:'IE10-'},
								{value: 1, name:'IE11-'},
								{value: 0, name:'edge'}
							]
						}
					]
				}
			]
		};

		return option;
	}
});