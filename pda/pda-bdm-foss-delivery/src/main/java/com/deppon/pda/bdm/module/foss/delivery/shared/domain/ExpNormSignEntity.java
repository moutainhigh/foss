package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;


/** 
  * @ClassName ExpNormSignEntity 
  * @Description 快递按件正常签收
  * @author zhangzhenxian 
  * @date 2013-7-25 上午9:23:39 
*/ 
public class ExpNormSignEntity extends ScanMsgEntity {
	/**
	 * @description
	 */

	private static final long serialVersionUID = 3845236835639452467L;

	   //车牌号
    private String truckCode;
    //到达联编号
    private String arrInfoCode;
    //签收人
    private String signPerson;
    //流水号
    // private List<ArrPayEntity> arrPayInfo;
    private List<PdaSignDetailEntity> labelCodes;
    //付款方式（代收货款支付类型）
    private String payType;
    /*快递新增修改字段*/
    //代收货款金额
    private double payAmount;
    //到付金额
    private double arriveAmount;
    //到付金额支付类型
    private String arriveType;
    //合并金额
    private double totalAmount;
    //合并类型
    private String totalType;
   
    /**
     * 签收情况
     */
    private String signStatus;
    //PDA串号   
    private String pdaSerial;   
    // 到付银行交易流水号  
    private String bankTradeSerail;
    //代收货款流水号
    private String codBankTradeSerail;
    //是否提供定额发票
    private String isofferInvoice;
    //派送开始时间
    private Date sendstart;
    //派送结束时间
    private Date sendend;
    //签收开始时间
    private Date signstart;
    //签收时间/签收结束时间
    private Date signTime;
    //签收类型
    private String signtypee;
    //刷卡支付开始时间
    private Date paystart;
    //刷卡支付结束时间
    private Date payend;
    //签收人类型
    private String signPerType;
    //刷卡信息
    private AccountStatementEntitys posCardInfo;
    //刷卡交易流水号
    private String tradeSerialNo;
    
    public String getTradeSerialNo() {
		return tradeSerialNo;
	}

	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}

	public AccountStatementEntitys getPosCardInfo() {
		return posCardInfo;
	}

	public void setPosCardInfo(AccountStatementEntitys posCardInfo) {
		this.posCardInfo = posCardInfo;
	}

	public String getSignPerType() {
		return signPerType;
	}

	public void setSignPerType(String signPerType) {
		this.signPerType = signPerType;
	}

	public String getIsofferInvoice() {
		return isofferInvoice;
	}

	public void setIsofferInvoice(String isofferInvoice) {
		this.isofferInvoice = isofferInvoice;
	}

	public String getTruckCode() {
        return truckCode;
    }

    public void setTruckCode(String truckCode) {
        this.truckCode = truckCode;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
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

    public String getPdaSerial() {
        return pdaSerial;
    }

    public void setPdaSerial(String pdaSerial) {
        this.pdaSerial = pdaSerial;
    }

    public String getBankTradeSerail() {
        return bankTradeSerail;
    }

    public void setBankTradeSerail(String bankTradeSerail) {
        this.bankTradeSerail = bankTradeSerail;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getArriveAmount() {
        return arriveAmount;
    }

    public void setArriveAmount(double arriveAmount) {
        this.arriveAmount = arriveAmount;
    }
    public String getArriveType() {
        return arriveType;
    }

    public void setArriveType(String arriveType) {
        this.arriveType = arriveType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public String getTotalType() {
        return totalType;
    }

    public void setTotalType(String totalType) {
        this.totalType = totalType;
    }

    public String getCodBankTradeSerail() {
        return codBankTradeSerail;
    }

    public void setCodBankTradeSerail(String codBankTradeSerail) {
        this.codBankTradeSerail = codBankTradeSerail;
    }

	public Date getSendstart() {
		return sendstart;
	}

	public void setSendstart(Date sendstart) {
		this.sendstart = sendstart;
	}

	public Date getSendend() {
		return sendend;
	}

	public void setSendend(Date sendend) {
		this.sendend = sendend;
	}

	public Date getSignstart() {
		return signstart;
	}

	public void setSignstart(Date signstart) {
		this.signstart = signstart;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSigntypee() {
		return signtypee;
	}

	public void setSigntypee(String signtypee) {
		this.signtypee = signtypee;
	}

	public Date getPaystart() {
		return paystart;
	}

	public void setPaystart(Date paystart) {
		this.paystart = paystart;
	}

	public Date getPayend() {
		return payend;
	}

	public void setPayend(Date payend) {
		this.payend = payend;
	}

    

}
