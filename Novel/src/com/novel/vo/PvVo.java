package com.novel.vo;

@SuppressWarnings("serial")
public class PvVo implements java.io.Serializable {

	private Integer type;
	private Integer resultcount;
	
	public PvVo() {
		
	}

	public PvVo(Integer type, Integer resultcount) {
		this.type = type;
		this.resultcount = resultcount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getResultcount() {
		return resultcount;
	}

	public void setResultcount(Integer resultcount) {
		this.resultcount = resultcount;
	}
}
