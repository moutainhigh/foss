package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @ClassName NormVoteSignScanEntity
 * @Description 按票正常签收
 * @author xujun cometzb@126.com
 * @date 2012-12-26
 */
public class NormVoteSignScanEntity extends ScanMsgEntity {

	/**
	 * @description
	 */

	private static final long serialVersionUID = -5314246962420579642L;
	//车牌号
	private String truckCode;
	//到达联编号
	private String arrInfoCode;
	//签收人
	private String signPerson;
	// private List<ArrPayEntity> arrPayInfo;
	//流水号
	private List<PdaSignDetailEntity> labelCodes;
	//付款方式
	private String payType;
	//签收情况
	private String signStatus;
	//是否提供定额发票(Y/N)
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
	
	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getArrInfoCode() {
		return arrInfoCode;
	}

	public void setArrInfoCode(String arrInfoCode) {
		this.arrInfoCode = arrInfoCode;
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
