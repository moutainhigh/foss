package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * 客户信息： 类型包含：客户、空运代理、偏线代理
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-26 下午4:34:25
 */
public class CustomerInfoDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8391019412082673541L;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
