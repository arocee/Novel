<#import "common.ftl" as com>
<@com.page title="首页" page="main">
	<div class='banner'>
		<img src='/image/banner.jpg' alt='banner' title='banner' />
	</div>
	<div class='itemList'>
		<div class='items'>
			<h1>全部类目</h1>
			<div>
				<ul>
					<#list nav as type>
						<li class='itemPart'>
							<h2>${type.type}</h2>
							<ol>
								<#if type.articles??>
									<#list type.articles as article>
										<#if article_has_next>
											<li>
												<div class='itemField'>
													<h3>${article.article}</h3>
													<#if article.sections??>
														<#list article.sections as section>
															<a href='/Novel/static/detail-${type.id}-${article.id}-${section.id}.html'>${section.section}</a>
														</#list>
													</#if>
												</div>
											</li>
										<#else>
											<li>
												<div class='itemField bottomField'>
													<h3>${article.article}</h3>
													<#if article.sections??>
														<#list article.sections as section>
															<a href='/Novel/static/detail-${type.id}-${article.id}-${section.id}.html'>${section.section}</a>
														</#list>
													</#if>
												</div>
											</li>
										</#if>
									</#list>
								</#if>
							</ol>
						</li>
					</#list>
				</ul>
			</div>
		</div>
	</div>
	<div class='intro'>
		<ul>
			<li class='intro1'>
				<span></span>
				<h3>Hard</h3>
				<p>历时5年精心积累，所有灵感尽皆原创，无私奉献为您助跑！3000+条创意灵感供您任选，只有您想不到的，没有满足不了您的。</p>
			</li>
			<li class='intro2'>
				<span></span>
				<h3>Helpful</h3>
				<p>您是否有写小说搜肠刮肚也写不出来的时候，您是否有为了一个情节的描写而绞尽脑汁的时候，您是否有没有创作源泉而抓耳挠腮的时候，如今，有了小说灵感网，专为您的小说提供无限创意，从此，妈妈再也不用担心我的小说了。</p>
			</li>
			<li class='intro3'>
				<span></span>
				<h3>Index</h3>
				<p>根据每种灵感创意的类型分门别类。建立强大的索引机制，帮您在浩瀚的创意中迅速找到您的需求。若您还没有具体需求，那么小说灵感网也能为您指点方向。</p>
			</li>
		</ul>
		<div class='clear'></div>
	</div>
	<div class='legalStatement'>
		<div class='legalInner'>
			<p id='legalDetail'><strong>【声明】</strong>小说灵感网对本站所有“灵感”享有软件著作权。本站所有灵感皆可用于对小说创作提供参考。这也是本站的初衷。但严禁剽窃其内容用于除此之外的其它任何商业行为。一经发现，必将追究相关法律责任！ 特此声明！2015年6月22日</p>
		</div>
	</div>
</@com.page>  