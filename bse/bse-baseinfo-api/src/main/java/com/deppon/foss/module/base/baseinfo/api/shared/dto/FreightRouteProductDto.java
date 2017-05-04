package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 走货路径产品定义
 * 
 * @author ibm-zhuwei
 * @date 2013-7-18 下午3:46:02
 */
public class FreightRouteProductDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1873459138090239430L;

	private String code;

	private String description;

	private Long levels;

	private String priority;

	private FreightRouteProductDto parent;
	
	public FreightRouteProductDto(String code, String description, Long levels, String priority) {
		this.code = code;
		this.description = description;
		this.levels = levels;
		this.priority = priority;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getLevels() {
		return levels;
	}

	public void setLevels(Long levels) {
		this.levels = levels;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public FreightRouteProductDto getParent() {
		return parent;
	}

	public void setParent(FreightRouteProductDto parent) {
		this.parent = parent;
	}

}
