package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 电子运单下单异常对应中文信息
 * @author 297064
 *
 */
public class EWaybillMessageEntity implements Serializable{

	
	/**
	 * id
	 */
	private String id;
	/**
	 * 异常编码
	 */
	private String failCode;
	/**
	 * 异常信息
	 */
	private String message;
	/**
	 * 是否激活
	 */
	private char active; 
    /**
     * 创建时间
     */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private String modifyTime;
    /**
     * 操作人
     */
	private String operator;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFailCode() {
		return failCode;
	}
	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public char getActive() {
		return active;
	}
	public void setActive(char active) {
		this.active = active;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
