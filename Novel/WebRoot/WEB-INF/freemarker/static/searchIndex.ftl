<#import "common.ftl" as com>
<@com.page title="搜索页" page="searchIndex">
	<div class='body' id='body'>
		<div class='searchField'>
			<p class='searchLogo'>
				<img src='/image/searchLogo.png' alt='logo' title='logo' />
			</p>
			<form name='searchIndexForm' action='/Novel/static/search.html' method="get">
				<div class='searchForm'>
					<div class='searchFormInner'>
						<input type='text' maxlength='15' name='keyWords' class='queryLong' id='queryLong' placeholder='搜索内容' autocomplete="off" /><input type='submit' id='searchBig' value='搜索' />
						<input type='hidden' name='searchType' value='0' />
					</div>
					<div class='autocomplete' id='autocomplete'>
						<div class='completeInner'>
							<ul id='autocompleteList'></ul>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</@com.page>