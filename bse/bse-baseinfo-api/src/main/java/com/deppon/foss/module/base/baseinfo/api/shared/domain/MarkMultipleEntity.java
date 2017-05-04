package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 定义市场推广活动枚举实体
 * 类似数据字典的功能
 * @author 078816
 * @date   2014-04-01
 *
 */
public class MarkMultipleEntity extends BaseEntity {

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	//活动的crmId
	private BigDecimal activiteCrmId;
		
	//活动ID（FOSS）
	private String activiteId;
	
	//枚举类型
	private String enumType;
	
	//枚举值
	private String valueCode;
	
	//枚举名称
	private String valueName;
	
	//是否启用
    private String active;

	public BigDecimal getActiviteCrmId() {
		return activiteCrmId;
	}

	public void setActiviteCrmId(BigDecimal activiteCrmId) {
		this.activiteCrmId = activiteCrmId;
	}

	public String getActiviteId() {
		return activiteId;
	}

	public void setActiviteId(String activiteId) {
		this.activiteId = activiteId;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
