package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;

/**
 * 现金收款单批量修改使用DTO 【1、确认收银；】
 * 
 * @author ibm-zhuwei
 * @date 2012-12-17 上午10:59:59
 */
public class BillCashCollectionDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8105916157310980642L;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人工号
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 收银确认人工号
	 */
	private String cashConfirmUserCode;

	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;

	/**
	 * 收银确认时间
	 */
	private Date cashConfirmTime;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 应付单集合
	 */
	private List<BillCashCollectionEntity> billCashCollections;

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	/**
	 * @param cashConfirmUserCode
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	/**
	 * @return cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	/**
	 * @param cashConfirmUserName
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
	}

	/**
	 * @return cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	/**
	 * @param cashConfirmTime
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	/**
	 * @return billCashCollections
	 */
	public List<BillCashCollectionEntity> getBillCashCollections() {
		return billCashCollections;
	}

	/**
	 * @param billCashCollections
	 */
	public void setBillCashCollections(
			List<BillCashCollectionEntity> billCashCollections) {
		this.billCashCollections = billCashCollections;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
