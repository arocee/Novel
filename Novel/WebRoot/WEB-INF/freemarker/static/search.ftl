<#import "common.ftl" as com>
<@com.page title="搜索页" page="search">
	<div class='body' id='body'>
		<div class='mainContent searchContent'>
			<div class='searchInfo'>
				<div class='link'>
					<a href='javascript:window.history.back();' class='back'>&lt;&lt; 返回上一页</a>&nbsp;&nbsp;&nbsp;
					<a href='/Novel/static/index.html' class='home'>返回首页 &gt;&gt;</a>
				</div>
				<p><span></span>搜索“${keyWords?html}”的结果，共${count}条</p>
			</div>
			<div class='paragraphList'>
				<ul>
					<#list novels as novel>
						<li>
							<p>${novel.paragraph}<p>
							<div class='from'>
								来自 <a href='/Novel/static/detail-${novel.tid}-${novel.aid}-${novel.sid}.html'>${novel.type}</a> &gt; <a href='/Novel/static/detail-${novel.tid}-${novel.aid}-${novel.sid}.html'>${novel.article}</a> &gt; <a href='/Novel/static/detail-${novel.tid}-${novel.aid}-${novel.sid}.html'>${novel.section}</a>
							</div>
						</li>
					</#list>
				</ul>
			</div>
			<#if (pv.page == 0)>
				<div class='sorry'></div>
			</#if>
		</div>
		<div class='pager'>
			<#include "pager.ftl" />
		</div> 
	</div>
</@com.page>