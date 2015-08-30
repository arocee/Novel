package com.novel.vo;

@SuppressWarnings("serial")
public class PagerVo implements java.io.Serializable {

	private Integer page;
	private Integer now;
	private String url;
	
	public PagerVo(){
		
	}
	
	public PagerVo(Integer page, Integer now, String url){
		this.page = page;
		this.now = now;
		this.url = url;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getNow() {
		return now;
	}

	public void setNow(Integer now) {
		this.now = now;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
