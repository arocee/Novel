package com.novel.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Section implements java.io.Serializable {
	
    private Integer id;
    private String section;
    private Integer aid;
    private List<Paragraph> paragraphes = new ArrayList<>(0);

    public Section(){
    	
    }
    
    public Section(Integer id, String section, Integer aid,
			List<Paragraph> paragraphes) {
		this.id = id;
		this.section = section;
		this.aid = aid;
		this.paragraphes = paragraphes;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

	public List<Paragraph> getParagraphes() {
		return paragraphes;
	}

	public void setParagraphes(List<Paragraph> paragraphes) {
		this.paragraphes = paragraphes;
	}
}