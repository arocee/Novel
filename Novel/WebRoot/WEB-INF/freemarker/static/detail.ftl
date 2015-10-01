<#import "common.ftl" as com>
<@com.page title="详情页" page="detail">
	<div class='path'>
		当前位置： <span class='pathRoot'><a href='/Novel/static/index.html'>首页</a></span> &gt; 
		<span class='pathRoot' id='openSlide'><a href='javascript:;'>${subNav.type}</a></span> &gt; 
		<span class='pathRoot'><a href='/Novel/static/detail-${tid}-${aid}-0.html'>${thirdNav.article}</a></span> &gt; 
		<span class='pathRoot'><a href='javascript:;'>${contents.section}</a></span>
		<div class='slideDown' id='slideDown'>
			<span class='triangle'></span>
			<i class='triangle'></i>
			<ul>
				<#list nav as type>
					<li title='进入${type.type}页面'>
						<a href='/Novel/static/detail-${type.id}-${type.articles[0].id}-0.html'>${type.type}</a>
					</li>
				</#list>
			</ul>
		</div>
	</div>
	<div class='body detailBody' id='body'>
		<div class='sider' id='sider'>
			<h1>${subNav.type}</h1>
			<ul>
				<#if subNav.articles??>
					<#list subNav.articles as article>
						<#if article.id == aid><li class='cur'><span>${article.article}</span></li>
	 					<#else><li><a href="/Novel/static/detail-${tid}-${article.id}-0.html">${article.article}</a></li>
	 					</#if> 
					</#list>
				</#if>
			</ul>
		</div>
		<div class='mainContent'>
			<div class='mainHeader'>
				二级类目：
				<#if thirdNav.sections??>
					<#list thirdNav.sections as section>
						<#if section.id == sid><span class='cur'>${section.section}</span>
	 					<#else><span><a href="/Novel/static/detail-${tid}-${aid}-${section.id}.html">${section.section}</a></span>
	 					</#if> 
					</#list>
				</#if>
			</div>
			<div class='paragraphList'>
				<ul>
					<#if contents.paragraphes??>
						<#list contents.paragraphes as paragraph>
							<li>
								<p>${paragraph.paragraph}</p>
							</li>
						</#list>
					</#if>
				</ul>
			</div>
		</div>
	</div>
</@com.page>  