package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class ShortFieldMapEntity extends BaseEntity  {

	/**
	 *  序列化UID
	 */
	private static final long serialVersionUID = -2385362691256711613L;

	/**
	 *  本外场code
	 */
	private String  code;
	/**
	 *  本外场名
	 */
	private String  name;
	/**
	 *  短距离外场code
	 */
	private String  shortFieldCode;
	/**
	 *  短距离外场名
	 */
	private String  shortFieldName;
	/**
	 *  状态（是否启用）
	 */
	private String  active;
	/**
	 *   下面是以上属性的get、set方法
	 */
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
	public String getShortFieldCode() {
		return shortFieldCode;
	}
	public void setShortFieldCode(String shortFieldCode) {
		this.shortFieldCode = shortFieldCode;
	}
	public String getShortFieldName() {
		return shortFieldName;
	}
	public void setShortFieldName(String shortFieldName) {
		this.shortFieldName = shortFieldName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}

}
