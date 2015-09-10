<#macro page title page>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>小说灵感网——${title?html}</title>
<link rel="shortcut icon" href="/image/favicon.ico" />
<link rel='stylesheet' href='/css/all.css' />
</head>
<body data-page='${page}'>
	<div class='wrapper'>
		<div class='header'>
			<div class='header_inner'>
				<h1 title='小说灵感网'><a href='/Novel/static/index.html'>小说灵感网</a></h1>
				<div class='nav'>
					<ul class='navUl'>
						<#list nav as type>
							<li class='navLI' title='进入${type.type}页面'>
								<a class='navA' href='/Novel/static/detail-${type.id}-${type.articles[0].id}-0.html'>${type.type}<em class='triangle'></em></a>
								<div class='subNav'>
									<ol>
										<#list type.articles as article>
											<li title='进入${type.type}-${article.article}页面'><a href='/Novel/static/detail-${type.id}-${article.id}-0.html'>${article.article}</a></li>
										</#list>
									</ol>
								</div>
							</li>
						</#list>
					</ul>
				</div>
				<div class='search'>
					<form action='/Novel/static/search.html' method="get" name='search'>
						<input type='text' class='query' id='query' name='keyWords' placeholder='搜索内容' maxlength='15' value='${keyWords!}' autocomplete="off" />
						<#if tid??>
							<input type='hidden' name='tid' value='${tid}' />		 					
	 					</#if>
	 					<#if aid??>
	 						<input type='hidden' name='aid' value='${aid}' />		
	 					</#if> 
						<#if sid??>
	 						<input type='hidden' name='sid' value='${sid}' />		
	 					</#if>
						<select class='searchType' name='searchType'>
							<option value='0'>全部</option>
							<#if tid??>
								<option value='1'>本类</option>		 					
		 					</#if>
		 					<#if aid??>
		 						<option value='2'>本章</option>
		 					</#if> 
							<#if sid??>
		 						<option value='3'>本块</option>
		 					</#if>
						</select>
						<input type='submit' id='search' value='搜索' />
					</form>
					<div class='autocom' id='autocom'>
						<h3>热搜词...</h3>
						<div class='comInner'>
							<ul id='completeList'></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class='headFix' id='headFix'></div>
		
		<#nested>
		
		<div class='footFix'></div>
		<div class='footer'>
			<p>novel-inspiration&copy; 2015</p>
		</div>
	</div>
	<ul class="tbui_aside_float_bar">
		<li class="tbui_aside_fbar_button tbui_fbar_top" id='toTop'>
			<a href="#"></a>
		</li>
	</ul>
	<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"slide":{"type":"slide","bdImg":"5","bdPos":"right","bdTop":"250"}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
	<script src="/javascript/jquery-1.11.1.js"></script>
	<script src="/javascript/all.js"></script>
</body>
</html>
</#macro> 