package com.novel.model;

@SuppressWarnings("serial")
public class Novel implements java.io.Serializable {

	private Integer tid;
	private Integer aid;
	private Integer sid;
	private Integer pid;
	private String type;
	private String article;
	private String section;
	private String paragraph;
	
	public Novel(){
		
	}

	public Novel(Integer tid, Integer aid, Integer sid, Integer pid, String type,
			String article, String section, String paragraph) {
		this.tid = tid;
		this.aid = aid;
		this.sid = sid;
		this.pid = pid;
		this.type = type;
		this.article = article;
		this.section = section;
		this.paragraph = paragraph;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getParagraph() {
		return paragraph;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	
}