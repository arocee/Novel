<#import "common.ftl" as com>
<@com.page title="详情页" page="detail">
	<div class='body detailBody' id='body'>
		<div class='sider' id='sider'>
			<h1>${subNav.type}</h1>
			<ul>
				<#list subNav.articles as article>
					<#if article.id == aid><li class='cur'><span>${article.article}</span></li>
 					<#else><li><a href="/Novel/static/detail-${tid}-${article.id}-0.html">${article.article}</a></li>
 					</#if> 
				</#list>
			</ul>
		</div>
		<div class='mainContent'>
			<div class='mainHeader'>
				二级类目：
				<#list thirdNav.sections as section>
					<#if section.id == sid><span class='cur'>${section.section}</span>
 					<#else><span><a href="/Novel/static/detail-${tid}-${aid}-${section.id}.html">${section.section}</a></span>
 					</#if> 
				</#list>
			</div>
			<div class='paragraphList'>
				<ul>
					<#list contents.paragraphes as paragraph>
						<li>${paragraph.paragraph}</li>
					</#list>
				</ul>
			</div>
		</div>
	</div>
</@com.page>  