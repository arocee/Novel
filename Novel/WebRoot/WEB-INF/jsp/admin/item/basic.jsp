<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a id='frameTop'></a>
<div class='infoArticle'>
	<div class='dataInfo infoSection'>
		<h1>数据信息</h1>
		<table>
			<tr>
				<th>type</th>
				<th>article</th>
				<th>section</th>
				<th>paragraph</th>
				<th>search</th>
				<th class='more'>&nbsp;</th>
			</tr>
			<tr>
				<td>4</td>
				<td>40</td>
				<td>4000</td>
				<td>4000</td>
				<td>200</td>
				<td></td>
			</tr>
		</table>
	</div>
	<div class='siteInfo infoSection'>
		<h1>网站信息</h1>
		<table>
			<tr>
				<th>前台访问量</th>
				<th>后台访问量</th>
				<th>管理人员</th>
			</tr>
			<tr>
				<td>3000</td>
				<td>500</td>
				<td>8</td>
			</tr>
		</table>
		<div class='siteDialog' id='siteDialog'></div>
	</div>
	<div class='countInfo infoSection'>
		<h1>搜索信息
			<select id='countType'>
				<option value='0'>全部</option>
				<option value='1' selected="selected">最近7天</option>
			</select>
		</h1>
		<div class='countDialog' id='countDialog'></div>
	</div>
	<div class='developInfo infoSection'>
		<h1>更新日志</h1>
		<table>
			<tr>
				<th>编号</th>
				<th>日期</th>
				<th>更新内容</th>
			</tr>
			<tr>
				<td>1</td>
				<td>2015-06-13</td>
				<td>项目启动，伟大工程开始</td>
			</tr>
			<tr>
				<td>2</td>
				<td>2015-06-20</td>
				<td>加入MAVEN支持，不再为jar包寻找头疼</td>
			</tr>
			<tr>
				<td>3</td>
				<td>2015-06-29</td>
				<td>页面静态化，优化性能，提升用户体验</td>
			</tr>
			<tr>
				<td>4</td>
				<td>2015-07-05</td>
				<td>加入Lucene全文索引机制，强大的搜索引擎，让快速定位不再是问题</td>
			</tr>
			<tr>
				<td>5</td>
				<td>2015-07-12</td>
				<td>后台管理页面启动，终于可以开始对网站内容进行管理了。最方便的就是随时添加新的小说创意</td>
			</tr>
			<tr>
				<td>6</td>
				<td>2015-08-02</td>
				<td>加入JUnit单元测试，方便程序的调试以及快速操作</td>
			</tr>
			<tr>
				<td>7</td>
				<td>2015-08-08</td>
				<td>加入对mybatis缓存的粒度控制，缓存利用率进一步提高，性能得到极大优化</td>
			</tr>
			<tr>
				<td>8</td>
				<td>2015-08-15</td>
				<td>后台页面增加登录功能，使后台页面更加完善</td>
			</tr>
			<tr>
				<td>8</td>
				<td>2015-08-23</td>
				<td>后台采用echarts图表，强大的图形化界面让数据一目了然</td>
			</tr>
		</table>
	</div>
</div>
<script src="/javascript/echarts/build/dist/echarts.js"></script>