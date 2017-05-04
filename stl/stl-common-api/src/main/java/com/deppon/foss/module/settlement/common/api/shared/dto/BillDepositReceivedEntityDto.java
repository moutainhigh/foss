package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;

/**
 * 预收单公共dto
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-5 上午9:47:33
 */
public class BillDepositReceivedEntityDto implements Serializable {

	/**
	 * 预收单公共dto序列号
	 */
	private static final long serialVersionUID = 5755784324791299777L;

	/**
	 * 对账单号
	 */
	private String statementBillNo;
	
	/**
	 * 付款单号集
	 */
	private List<String> paymentNos;

	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 收银状态
	 */
	private String status;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 收银确认时间
	 */
	private Date cashConfirmTime;
	
	/**
	 * 收银人编码
	 */
	private String cashConfirmUserCode;
	
	/**
	 * 收养人名称
	 */
    private String cashConfirmUserName;
    
	/**
	 * 预收单集合
	 */
	List<BillDepositReceivedEntity> billDepositReceivedEntityList;

	/**
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	
	
	
	/**
	 * @return  the status
	 */
	public String getStatus() {
		return status;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return  the cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	
	/**
	 * @param cashConfirmTime the cashConfirmTime to set
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	/**
	 * @return  the cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	
	/**
	 * @param cashConfirmUserCode the cashConfirmUserCode to set
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	
	/**
	 * @return  the cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	
	/**
	 * @param cashConfirmUserName the cashConfirmUserName to set
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
	}

	/**
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

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
	 * @return billDepositReceivedEntityList
	 */
	public List<BillDepositReceivedEntity> getBillDepositReceivedEntityList() {
		return billDepositReceivedEntityList;
	}

	/**
	 * @param billDepositReceivedEntityList
	 */
	public void setBillDepositReceivedEntityList(
			List<BillDepositReceivedEntity> billDepositReceivedEntityList) {
		this.billDepositReceivedEntityList = billDepositReceivedEntityList;
	}

	
	/**
	 * @return  the paymentNos
	 */
	public List<String> getPaymentNos() {
		return paymentNos;
	}

	
	/**
	 * @param paymentNos the paymentNos to set
	 */
	public void setPaymentNos(List<String> paymentNos) {
		this.paymentNos = paymentNos;
	}

}
