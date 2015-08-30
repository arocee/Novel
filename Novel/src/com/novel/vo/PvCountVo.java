package com.novel.vo;

@SuppressWarnings("serial")
public class PvCountVo implements java.io.Serializable {

	private Integer fc;  // 前台访问量
	private Integer bc;  // 后台访问量
	private Integer uc;  // 管理人员数
	
	public PvCountVo(){
		
	}

	public PvCountVo(Integer fc, Integer bc, Integer uc) {
		this.fc = fc;
		this.bc = bc;
		this.uc = uc;
	}

	public Integer getFc() {
		return fc;
	}

	public void setFc(Integer fc) {
		this.fc = fc;
	}

	public Integer getBc() {
		return bc;
	}

	public void setBc(Integer bc) {
		this.bc = bc;
	}

	public Integer getUc() {
		return uc;
	}

	public void setUc(Integer uc) {
		this.uc = uc;
	}
	
}
