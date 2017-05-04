package com.deppon.foss.module.transfer.load.api.shared.define;

public enum LoadStatueEnum {

	
	/**
	* @fields CREATE 创建状态
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:26:19
	* @version V1.0
	*/
	CREATE(1, "创建"), 
	
	/**
	* @fields FINISH 完成状态
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:26:33
	* @version V1.0
	*/
	FINISH(2, "完成");

	private LoadStatueEnum(Integer code, String name) {
		this.name = name;
		this.code = code;
	}

	public static String getName(Integer code) {
		for (LoadStatueEnum c : LoadStatueEnum.values()) {
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
