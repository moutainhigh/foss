package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 临时租车标记时间管理信息
 * @author 218392  张永雪
 * @date 创建时间：2014-12-17 下午3:11:07
 */
public class TemporaryRentalCarMarkTimeManagementEntity extends BaseEntity{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 部门属性
	 */
	private String deptAttributes;
	
	/**
	 * 部门属性名字
	 */
	private String deptAttributesName;
	
	/**
	 * 设置时长（天）
	 */
	private String setTime;
	
	/**
	 * 操作人工号
	 */
	private String operationCode;
	
	/**
	 * 操作人姓名
	 */
	private String operationName;
	
	/**
	 * 是否有效：默认‘Y’
	 */
	private String active;
	
	/**
	 * 操作时间
	 */
	private Date operationDate;
	
	
	/**
	 * 获取操作时间
	 * @return
	 */
	public Date getOperationDate() {
		return operationDate;
	}

	/**
	 * 设置操作时间
	 * @param operationDate
	 */
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	/**
	 * 获取部门属性
	 * @return
	 */
	public String getDeptAttributes() {
		return deptAttributes;
	}

	/**
	 * 设置部门属性
	 * @param deptAttributes
	 */
	public void setDeptAttributes(String deptAttributes) {
		this.deptAttributes = deptAttributes;
	}

	/**
	 * 获取部门属性名字
	 * @return
	 */
	public String getDeptAttributesName() {
		return deptAttributesName;
	}

	/**
	 * 设置部门属性名字
	 * @param deptAttributes
	 */
	public void setDeptAttributesName(String deptAttributesName) {
		this.deptAttributesName = deptAttributesName;
	}

	/**
	 * 获取设置时长(天)
	 * @return
	 */
	public String getSetTime() {
		return setTime;
	}

	/**
	 * 设置 设置时长(天)
	 * @param setTime
	 */
	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}

	/**
	 * 获取操作人工号
	 * @return
	 */
	public String getOperationCode() {
		return operationCode;
	}

	/**
	 * 设置操作人工号
	 * @param operationCode
	 */
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	/**
	 * 获取操作人姓名
	 * @return
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * 设置操作人姓名
	 * @param operationName
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * 获取是否有效：默认‘Y’
	 * @return
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置是否有效
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
}
