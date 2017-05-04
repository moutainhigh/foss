package com.deppon.foss.module.transfer.load.api.shared.define;

public enum LoadSourceEnum {
	
	///快递 1, 零担0, 合单2

	
	/**
	* @fields KUIDI (1, "快递")
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:26:19
	* @version V1.0
	*/
	KUIDI(1, "快递"), 
	
	/**
	* @fields LINGDAN (0, "零担")
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:26:33
	* @version V1.0
	*/
	LINGDAN(0, "零担"),
	
	
	
	/**
	* @fields HEDAN (2, "合单")零担快递和单
	* @author 328864-foss-xieyang
	* @update 2016年6月13日 下午5:48:03
	* @version V1.0
	*/
	HEDAN(2, "合单");

	private LoadSourceEnum(Integer code, String name) {
		this.name = name;
		this.code = code;
	}

	public static String getName(Integer code) {
		for (LoadSourceEnum c : LoadSourceEnum.values()) {
			if (code.equals(c.getCode())) {
				return c.name;
			}
		}
		return null;
	}

	private Integer code;
	private String name;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
