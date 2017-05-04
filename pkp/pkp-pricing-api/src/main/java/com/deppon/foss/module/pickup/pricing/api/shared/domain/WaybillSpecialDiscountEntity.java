package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;
/**
 * 特殊折扣区域
 * 特殊折扣方案用于发往台湾、香港地区的快递不享受合同折扣、市场推广活动，快递折扣方案
 *
 */
public class WaybillSpecialDiscountEntity {
	
private String id;
	
	//部门code
	private String code;
	
	//部门名称
	private String name;
	
	//创建时间
	private Date creatTime;
	
	//修改时间
	private Date modifyTime;
	
	//是否启用
	private String active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
