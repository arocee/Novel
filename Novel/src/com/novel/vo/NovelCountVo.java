package com.novel.vo;

@SuppressWarnings("serial")
public class NovelCountVo implements java.io.Serializable {

	private Integer tc;  // type数量
	private Integer ac; // article数量
	private Integer sc; // section数量
	private Integer pc;  // paragraph数量
	private Integer scc;  // search数量
	
	public NovelCountVo() {
		
	}

	public NovelCountVo(Integer tc, Integer ac, Integer sc, Integer pc,
			Integer scc) {
		this.tc = tc;
		this.ac = ac;
		this.sc = sc;
		this.pc = pc;
		this.scc = scc;
	}

	public Integer getTc() {
		return tc;
	}

	public void setTc(Integer tc) {
		this.tc = tc;
	}

	public Integer getAc() {
		return ac;
	}

	public void setAc(Integer ac) {
		this.ac = ac;
	}

	public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

	public Integer getPc() {
		return pc;
	}

	public void setPc(Integer pc) {
		this.pc = pc;
	}

	public Integer getScc() {
		return scc;
	}

	public void setScc(Integer scc) {
		this.scc = scc;
	}
	
}
