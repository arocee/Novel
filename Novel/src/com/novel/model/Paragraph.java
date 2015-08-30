package com.novel.model;

@SuppressWarnings("serial")
public class Paragraph implements java.io.Serializable {

    private Integer id;
    private String paragraph;
    private Integer sid;

    public Paragraph(){
    	
    }
    
    public Paragraph(Integer id, String paragraph, Integer sid) {
		this.id = id;
		this.paragraph = paragraph;
		this.sid = sid;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }
}