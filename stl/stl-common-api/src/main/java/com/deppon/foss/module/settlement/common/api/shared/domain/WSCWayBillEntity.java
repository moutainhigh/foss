/**
 * @company : com.deppon
 * @poroject : foss结算
 * @copyright : copyright (c) 2016
 * 
 * @description: 待刷卡运单实体
 * @author : panshiqi (309613)
 * @date : 2016年2月18日 下午2:20:45
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 待刷卡运单实体
 * @className: WSCWayBillEntity
 * 
 * @authorCode 309613
 * @date 2016年2月18日 下午2:20:50
 * 
 */
public class WSCWayBillEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 待刷卡运单数据条目编号, 对应的是数据库中的 "ID" 字段
	 */
	private String wscItemId;

	/**
	 * 待刷卡运单数据自增长序号, 对应的是数据库中的 "WSC_ITEM_ID" 字段
	 */
	private String sequenceId;

	/**
	 * 运单号
	 */
	private String wayBillNo;

	/**
	 * 数据来源 {1-运单开单 2-运单更改}
	 */
	private String wayBillSource;

	/**
	 * 发货联系人编号
	 */
	private String sendCustomerCode;

	/**
	 * 发货联系人名称
	 */
	private String sendCustomerName;

	/**
	 * 开单部门编号
	 */
	private String createBillOrgCode;

	/**
	 * 开单部门名称
	 */
	private String createBillOrgName;

	/**
	 * 开单时间
	 */
	private Date createBillTime;

	/**
	 * 支付状态 { N-未支付  Y-已支付 }
	 */
	private String paymentStatus;

	/**
	 * 运单总金额
	 */
	private double wayBillAmount;

	/**
	 * 待刷卡金额
	 */
	private double waitSwipeAmount;

	/**
	 * 已刷卡金额
	 */
	private double alreadySwipeAmount;

	/**
	 * 刷卡部门编号
	 */
	private String swipeCardOrgCode;

	/**
	 * 刷卡部门名称
	 */
	private String swipeCardOrgName;

	/**
	 * 刷卡时间
	 */
	private Date swipeCardTime;

	/**
	 * 刷卡操作人编号
	 */
	private String swipeCardUserCode;

	/**
	 * 刷卡操作人名称
	 */
	private String swipeCardUserName;

	/**
	 * 交易流水号
	 */
	private String serialNo;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人编号
	 */
	private String createUserCode;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人编号
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 数据来源系统
	 */
	private String systemSour;
	
	public String getSystemSour() {
		return systemSour;
	}

	public void setSystemSour(String systemSour) {
		this.systemSour = systemSour;
	}

	/**  
	 * 获取 ID  
	 * @return id ID  
	 */
	public String getId() {
		return id;
	}

	/**  
	 * 设置 ID  
	 * @param id ID  
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**  
	 * 获取 待刷卡运单数据条目编号  
	 * @return wscItemId 待刷卡运单数据条目编号  
	 */
	public String getWscItemId() {
		return wscItemId;
	}

	/**  
	 * 设置 待刷卡运单数据条目编号  
	 * @param wscItemId 待刷卡运单数据条目编号  
	 */
	public void setWscItemId(String wscItemId) {
		this.wscItemId = wscItemId;
	}

	/**  
	 * 获取 待刷卡运单数据自增长序号对应的是数据库中的"WSC_ITEM_ID"字段  
	 * @return sequenceId 待刷卡运单数据自增长序号对应的是数据库中的"WSC_ITEM_ID"字段  
	 */
	public String getSequenceId() {
		return sequenceId;
	}

	/**  
	 * 设置 待刷卡运单数据自增长序号对应的是数据库中的"WSC_ITEM_ID"字段  
	 * @param sequenceId 待刷卡运单数据自增长序号对应的是数据库中的"WSC_ITEM_ID"字段  
	 */
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	/**  
	 * 获取 运单号  
	 * @return wayBillNo 运单号  
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}

	/**  
	 * 设置 运单号  
	 * @param wayBillNo 运单号  
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**  
	 * 获取 数据来源{1-运单开单2-运单更改}  
	 * @return wayBillSource 数据来源{1-运单开单2-运单更改}  
	 */
	public String getWayBillSource() {
		return wayBillSource;
	}

	/**  
	 * 设置 数据来源{1-运单开单2-运单更改}  
	 * @param wayBillSource 数据来源{1-运单开单2-运单更改}  
	 */
	public void setWayBillSource(String wayBillSource) {
		this.wayBillSource = wayBillSource;
	}

	/**  
	 * 获取 发货人编号  
	 * @return sendCustomerCode 发货人编号  
	 */
	public String getSendCustomerCode() {
		return sendCustomerCode;
	}

	/**  
	 * 设置 发货人编号  
	 * @param sendCustomerCode 发货人编号  
	 */
	public void setSendCustomerCode(String sendCustomerCode) {
		this.sendCustomerCode = sendCustomerCode;
	}

	/**  
	 * 获取 发货人名称  
	 * @return sendCustomerName 发货人名称  
	 */
	public String getSendCustomerName() {
		return sendCustomerName;
	}

	/**  
	 * 设置 发货人名称  
	 * @param sendCustomerName 发货人名称  
	 */
	public void setSendCustomerName(String sendCustomerName) {
		this.sendCustomerName = sendCustomerName;
	}

	/**  
	 * 获取 开单部门编号  
	 * @return createBillOrgCode 开单部门编号  
	 */
	public String getCreateBillOrgCode() {
		return createBillOrgCode;
	}

	/**  
	 * 设置 开单部门编号  
	 * @param createBillOrgCode 开单部门编号  
	 */
	public void setCreateBillOrgCode(String createBillOrgCode) {
		this.createBillOrgCode = createBillOrgCode;
	}

	/**  
	 * 获取 开单部门名称  
	 * @return createBillOrgName 开单部门名称  
	 */
	public String getCreateBillOrgName() {
		return createBillOrgName;
	}

	/**  
	 * 设置 开单部门名称  
	 * @param createBillOrgName 开单部门名称  
	 */
	public void setCreateBillOrgName(String createBillOrgName) {
		this.createBillOrgName = createBillOrgName;
	}

	/**  
	 * 获取 开单时间  
	 * @return createBillTime 开单时间  
	 */
	public Date getCreateBillTime() {
		return createBillTime;
	}

	/**  
	 * 设置 开单时间  
	 * @param createBillTime 开单时间  
	 */
	public void setCreateBillTime(Date createBillTime) {
		this.createBillTime = createBillTime;
	}

	/**  
	 * 获取 支付状态{N-未支付Y-已支付}  
	 * @return paymentStatus 支付状态{N-未支付Y-已支付}  
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**  
	 * 设置 支付状态{N-未支付Y-已支付}  
	 * @param paymentStatus 支付状态{N-未支付Y-已支付}  
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**  
	 * 获取 运单总金额  
	 * @return wayBillAmount 运单总金额  
	 */
	public double getWayBillAmount() {
		return wayBillAmount;
	}

	/**  
	 * 设置 运单总金额  
	 * @param wayBillAmount 运单总金额  
	 */
	public void setWayBillAmount(double wayBillAmount) {
		this.wayBillAmount = wayBillAmount;
	}

	/**  
	 * 获取 待刷卡金额  
	 * @return waitSwipeAmount 待刷卡金额  
	 */
	public double getWaitSwipeAmount() {
		return waitSwipeAmount;
	}

	/**  
	 * 设置 待刷卡金额  
	 * @param waitSwipeAmount 待刷卡金额  
	 */
	public void setWaitSwipeAmount(double waitSwipeAmount) {
		this.waitSwipeAmount = waitSwipeAmount;
	}

	/**  
	 * 获取 已刷卡金额  
	 * @return alreadySwipeAmount 已刷卡金额  
	 */
	public double getAlreadySwipeAmount() {
		return alreadySwipeAmount;
	}

	/**  
	 * 设置 已刷卡金额  
	 * @param alreadySwipeAmount 已刷卡金额  
	 */
	public void setAlreadySwipeAmount(double alreadySwipeAmount) {
		this.alreadySwipeAmount = alreadySwipeAmount;
	}

	/**  
	 * 获取 刷卡部门编号  
	 * @return swipeCardOrgCode 刷卡部门编号  
	 */
	public String getSwipeCardOrgCode() {
		return swipeCardOrgCode;
	}

	/**  
	 * 设置 刷卡部门编号  
	 * @param swipeCardOrgCode 刷卡部门编号  
	 */
	public void setSwipeCardOrgCode(String swipeCardOrgCode) {
		this.swipeCardOrgCode = swipeCardOrgCode;
	}

	/**  
	 * 获取 刷卡部门名称  
	 * @return swipeCardOrgName 刷卡部门名称  
	 */
	public String getSwipeCardOrgName() {
		return swipeCardOrgName;
	}

	/**  
	 * 设置 刷卡部门名称  
	 * @param swipeCardOrgName 刷卡部门名称  
	 */
	public void setSwipeCardOrgName(String swipeCardOrgName) {
		this.swipeCardOrgName = swipeCardOrgName;
	}

	/**  
	 * 获取 刷卡时间  
	 * @return swipeCardTime 刷卡时间  
	 */
	public Date getSwipeCardTime() {
		return swipeCardTime;
	}

	/**  
	 * 设置 刷卡时间  
	 * @param swipeCardTime 刷卡时间  
	 */
	public void setSwipeCardTime(Date swipeCardTime) {
		this.swipeCardTime = swipeCardTime;
	}

	/**  
	 * 获取 刷卡操作人编号  
	 * @return swipeCardUserCode 刷卡操作人编号  
	 */
	public String getSwipeCardUserCode() {
		return swipeCardUserCode;
	}

	/**  
	 * 设置 刷卡操作人编号  
	 * @param swipeCardUserCode 刷卡操作人编号  
	 */
	public void setSwipeCardUserCode(String swipeCardUserCode) {
		this.swipeCardUserCode = swipeCardUserCode;
	}

	/**  
	 * 获取 刷卡操作人名称  
	 * @return swipeCardUserName 刷卡操作人名称  
	 */
	public String getSwipeCardUserName() {
		return swipeCardUserName;
	}

	/**  
	 * 设置 刷卡操作人名称  
	 * @param swipeCardUserName 刷卡操作人名称  
	 */
	public void setSwipeCardUserName(String swipeCardUserName) {
		this.swipeCardUserName = swipeCardUserName;
	}

	/**  
	 * 获取 交易流水号  
	 * @return serialNo 交易流水号  
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**  
	 * 设置 交易流水号  
	 * @param serialNo 交易流水号  
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**  
	 * 获取 是否有效  
	 * @return active 是否有效  
	 */
	public String getActive() {
		return active;
	}

	/**  
	 * 设置 是否有效  
	 * @param active 是否有效  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**  
	 * 获取 创建时间  
	 * @return createTime 创建时间  
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * 设置 创建时间  
	 * @param createTime 创建时间  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**  
	 * 获取 创建人编号  
	 * @return createUserCode 创建人编号  
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**  
	 * 设置 创建人编号  
	 * @param createUserCode 创建人编号  
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**  
	 * 获取 创建人名称  
	 * @return createUserName 创建人名称  
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**  
	 * 设置 创建人名称  
	 * @param createUserName 创建人名称  
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**  
	 * 获取 修改时间  
	 * @return modifyTime 修改时间  
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**  
	 * 设置 修改时间  
	 * @param modifyTime 修改时间  
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**  
	 * 获取 修改人编号  
	 * @return modifyUserCode 修改人编号  
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**  
	 * 设置 修改人编号  
	 * @param modifyUserCode 修改人编号  
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**  
	 * 获取 修改人名称  
	 * @return modifyUserName 修改人名称  
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**  
	 * 设置 修改人名称  
	 * @param modifyUserName 修改人名称  
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**  
	 * 获取 备注  
	 * @return notes 备注  
	 */
	public String getNotes() {
		return notes;
	}

	/**  
	 * 设置 备注  
	 * @param notes 备注  
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 
	* @description: 获取关键参数的Json串
	* @title: getPointParamJson
	* @author panshiqi 309613
	* @date 2016年4月8日 上午10:10:58 
	* @return
	 */
	public String getPointParamJson() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			// 运单号
			sb.append("单号:");
			sb.append(this.getWayBillNo());
			sb.append(",");
			// 运单总金额
			sb.append("总金额:");
			sb.append(this.getWayBillAmount());
			sb.append(",");
			// 待刷卡金额
			sb.append("待刷卡金额:");
			sb.append(this.getWaitSwipeAmount());
			sb.append(",");
			// 已刷卡金额
			sb.append("已刷卡金额:");
			sb.append(this.getAlreadySwipeAmount());
			sb.append("}");
			// 返回Json串
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
