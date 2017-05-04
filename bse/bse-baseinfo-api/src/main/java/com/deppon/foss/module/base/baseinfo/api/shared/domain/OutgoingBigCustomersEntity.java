package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 外发大客户实体
 * 
 * @author 310854
 * 
 * @date 2016-2-25 下午3:56:58
 * 
 */
public class OutgoingBigCustomersEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 642148563922404382L;

	/**
	 * 编号
	 */
	private String id;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 修改时间
	 */
	private String modifyTime;
	
	/**
	 * 创建人
	 */
	private String createUserCode;
	
	/**
	 * 修改人
	 */
	private String  modifyUserCode;
	
	/**
	 * 客户CODE
	 */
	private String outgoingBigCustomersCode;

	/**
	 * 客户名称
	 */
	private String outgoingBigCustomersName;

	/**
	 * 备注
	 */
	private String outgoingBigCustomersRemark;

	/**
	 * 是否有效
	 */
	private String active;
	
	private String activeStr;

	public String getOutgoingBigCustomersCode() {
		return outgoingBigCustomersCode;
	}

	public void setOutgoingBigCustomersCode(String outgoingBigCustomersCode) {
		this.outgoingBigCustomersCode = outgoingBigCustomersCode;
	}

	public String getOutgoingBigCustomersName() {
		return outgoingBigCustomersName;
	}

	public void setOutgoingBigCustomersName(String outgoingBigCustomersName) {
		this.outgoingBigCustomersName = outgoingBigCustomersName;
	}

	public String getOutgoingBigCustomersRemark() {
		return outgoingBigCustomersRemark;
	}

	public void setOutgoingBigCustomersRemark(String outgoingBigCustomersRemark) {
		this.outgoingBigCustomersRemark = outgoingBigCustomersRemark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
		if("Y".equals(active)){
			this.setActiveStr("是");
		} else {
			this.setActiveStr("否");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getActiveStr() {
		return activeStr;
	}

	public void setActiveStr(String activeStr) {
		this.activeStr = activeStr;
	}

	@Override
	public String toString() {
		return "OutgoingBigCustomersEntity [outgoingBigCustomersCode="
				+ outgoingBigCustomersCode + ", outgoingBigCustomersName="
				+ outgoingBigCustomersName + ", outgoingBigCustomersRemark="
				+ outgoingBigCustomersRemark + ", active=" + active + "]";
	}

}
