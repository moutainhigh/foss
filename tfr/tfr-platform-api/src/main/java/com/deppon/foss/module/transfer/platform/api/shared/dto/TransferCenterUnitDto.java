/**
 * Project Name:tfr-platform-api
 * File Name:TransferCenterUnitDto.java
 * Package Name:com.deppon.foss.module.transfer.platform.api.shared.dto
 * Date:2014年12月30日上午9:30:46
 * Copyright (c) 2014, shiwei@outlook.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.math.BigDecimal;

/**
 * ClassName:TransferCenterUnitDto <br/>
 * Reason:	 场内布局图 单位dto. <br/>
 * Date:     2014年12月30日 上午9:30:46 <br/>
 * @author   045923
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TransferCenterUnitDto {
	
	/**
	 * 组织编码
	 */
	private String orgCode;
	
	/**
	 * 组织名称
	 */
	private String orgName;
	
	/**
	 * 长
	 */
	private BigDecimal length;
	
	/**
	 * 宽
	 */
	private BigDecimal width;
	
	/**
	 * 横坐标
	 */
	private BigDecimal x;
	
	/**
	 * 纵坐标
	 */
	private BigDecimal y;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 单位种类
	 */
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
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

}

