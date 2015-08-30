package com.novel.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Type implements java.io.Serializable {
	
    private Integer id;
    private String type;
    private List<Article> articles = new ArrayList<>(0);
    
    public Type(){
    	
    }

    public Type(Integer id, String type, List<Article> articles) {
		this.id = id;
		this.type = type;
		this.articles = articles;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}