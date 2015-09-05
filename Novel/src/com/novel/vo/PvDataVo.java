package com.novel.vo;

import java.util.List;

@SuppressWarnings("serial")
public class PvDataVo implements java.io.Serializable {

	private String date;
	private List<PvVo> pvVo;
	
	public PvDataVo() {
		
	}
	
	public PvDataVo(String date, List<PvVo> pvVo) {
		this.date = date;
		this.pvVo = pvVo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<PvVo> getPvVo() {
		return pvVo;
	}

	public void setPvVo(List<PvVo> pvVo) {
		this.pvVo = pvVo;
	}
	
	
}
