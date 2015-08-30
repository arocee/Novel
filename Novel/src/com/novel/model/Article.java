package com.novel.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Article implements java.io.Serializable {

    private Integer id;
    private String article;
    private Integer tid;
    private List<Section> sections = new ArrayList<>(0);

    public Article(){
    	
    }
    
    public Article(Integer id, String article, Integer tid,
			List<Section> sections) {
		this.id = id;
		this.article = article;
		this.tid = tid;
		this.sections = sections;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
}