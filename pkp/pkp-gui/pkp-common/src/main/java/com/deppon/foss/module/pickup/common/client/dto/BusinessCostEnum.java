package com.deppon.foss.module.pickup.common.client.dto;

/**
 * 业务费用枚举
 * @author WangQianJin
 * @date 2013-7-23 下午3:37:09
 */
public enum BusinessCostEnum {
	
    //中转费
	ZZ("ZZ", "中转费"), 
	//更改费
	GGF("GGF", "更改费");
	
	
	/**
	 * 费用的CODE
	 */
	private String code;
	
	/**
	 * 费用的名称
	 */
	private String name;
	
	/**
	 * 构造方法
	 * @param code
	 * @param name
	 */
	private BusinessCostEnum(String code, String name){
		this.code = code;
		this.name = name;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	

}