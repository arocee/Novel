<#if (pv.page > 0)>
	<#if pv.now == 0><strong class='prevpage'><i class='triangle left'></i> 上一页</strong>
	<#else><a class='prevpage' href='${ pv.url}${ pv.now - 1}'><i class='triangle left'></i> 上一页</a>
	</#if>
	<#if (pv.page <= 5)>
		<#list 1..pv.page as p>
			<#if pv.now == p - 1><strong class='cur'>${p}</strong>
			<#else><a class='page' href='${ pv.url}${ p - 1}'>${ p}</a>
			</#if>
		</#list>
	<#else>
		<#if (pv.now < 4)>
			<#list 1..5 as p>
				<#if pv.now == p - 1><strong class='cur'>${p}</strong>
				<#else><a class='page' href='${ pv.url}${ p - 1}'>${ p}</a>
				</#if>
			</#list>
			<a class='page' href='${ pv.url}${ pv.page - 1}'>... ${ pv.page}</a>
		<#elseif (pv.now >= pv.page - 4)>
			<a class='page' href='${ pv.url}0'>1 ...</a>
			<#list pv.page - 4..pv.page as p>
				<#if pv.now == p - 1><strong class='cur'>${p}</strong>
				<#else><a class='page' href='${ pv.url}${ p - 1}'>${ p}</a>
				</#if>
			</#list>
		<#else>
			<a class='page' href='${ pv.url}0'>1 ...</a>
			<#list pv.now - 1..pv.now + 3 as p>
				<#if pv.now == p - 1><strong class='cur'>${p}</strong>
				<#else><a class='page' href='${ pv.url}${ p - 1}'>${ p}</a>
				</#if>
			</#list>
			<a class='page' href='${ pv.url}${ pv.page - 1}'>... ${ pv.page}</a>
		</#if>
	</#if>
	<#if pv.now + 1 == pv.page><strong class='nextpage'>下一页 <i class='triangle right'></i></strong>
	<#else><a class='nextpage' href='${ pv.url}${ pv.now + 1}'>下一页 <i class='triangle right'></i></a>
	</#if>
</#if>