package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @ClassName ExcpSignByPcScanEntity
 * @Description 按件异常签收实体
 * @author xujun cometzb@126.com
 * @date 2012-12-26
 */
public class ExcpSignByPcScanEntity extends ScanMsgEntity {

	private static final long serialVersionUID = -8364559935496501665L;

	//异常原因	
	private String excpReason;
	//签收时间
	private Date signTime;
	//签收人
	private String signPerson;
	//到达联编号
	private String arrInfoCode;
	//付款方式
	private String payType;
	//签收情况
	private String signStatus;
	 //是否提供定额发票
    private String isofferInvoice;
	//银行交易流水号--代收货款流水号
	private String codBankTradeSerail;
	// 银行交易流水号--到付流水号
	private String bankTradeSerail;
	
	public String getIsofferInvoice() {
		return isofferInvoice;
	}

	public void setIsofferInvoice(String isofferInvoice) {
		this.isofferInvoice = isofferInvoice;
	}

	private List<PdaSignDetailEntity> labelCodes;

	public String getArrInfoCode() {
		return arrInfoCode;
	}

	public void setArrInfoCode(String arrInfoCode) {
		this.arrInfoCode = arrInfoCode;
	}

	public String getExcpReason() {
		return excpReason;
	}

	public void setExcpReason(String excpReason) {
		this.excpReason = excpReason;
	}

	public Date getSignTime() {
		return signTime;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignPerson() {
		return signPerson;
	}

	public void setSignPerson(String signPerson) {
		this.signPerson = signPerson;
	}

	public List<PdaSignDetailEntity> getLabelCodes() {
		return labelCodes;
	}

	public void setLabelCodes(List<PdaSignDetailEntity> labelCodes) {
		this.labelCodes = labelCodes;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCodBankTradeSerail() {
		return codBankTradeSerail;
	}

	public void setCodBankTradeSerail(String codBankTradeSerail) {
		this.codBankTradeSerail = codBankTradeSerail;
	}

	public String getBankTradeSerail() {
		return bankTradeSerail;
	}

	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}
}
