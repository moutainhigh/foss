package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class VtsCodAuditForSignEntity extends BaseEntity{

	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    /**
     * 运单号
     */
    private String waybillNo;
    /**
     * 状态
     */
    private String active;
    /**
     * 金额
     */
    private BigDecimal codAmount;
    /**
     * 锁定状态
     */
    private String lockStatus;
    
    private Date sigTime;
    private String origOrgCode;

    private String origOrgName;
    /**
     * 到达部门编号
     */
    private String destOrgCode;
    /**
     * 到达部门姓名
     */
    private String destOrgName;

    private String hasTrack;
    /**
     * 代收类型
     */
    private String codType;
    /**
     * 付款类型
     */
    private String paymentType;

    private Date billTime;

    private Date comfirmTime;
    /**
     * 开户行
     */
    private String accountNo;
    /**
     * 手机号
     * */
    private String mobileNo;

    private Date createDate;

    private Date modifyDate;

    private String createUser;

    private String modifyUser;
    
    private String bank;

	/**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 到达部门收银员姓名
     */
	private String destCashierName;
	/**
     * 到达部门电话
     */
    private String destMobilePhone;
    /**
     * 经营本部
     */
    private String manageDepartment;
	/**
     * 更改金额
     */
    private BigDecimal changeAmount ;
    /**
     * 现金金额
     */
	private BigDecimal  codAmountOfCH;
	/**
     * 银行卡金额
     */
    private BigDecimal  codAmountOfCD;
    /**
     * 电汇金额
     */
    private BigDecimal  codAmountOfTT;
    /**
     * 代收货款到付金额
     */
    private BigDecimal  codFCAmount;
    /**
     * 更改时间
     * */
    private  Date  changeTime;
    
    
    private List<String> batchNo;


	public List<String> getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(List<String> batchNo) {
		this.batchNo = batchNo;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	private int billSignDiffer;
    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public int getBillSignDiffer() {
        return billSignDiffer;
    }

    public void setBillSignDiffer(int billSignDiffer) {
        this.billSignDiffer = billSignDiffer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public BigDecimal getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(BigDecimal codAmount) {
        this.codAmount = codAmount;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Date getSigTime() {
        return sigTime;
    }

    public void setSigTime(Date sigTime) {
        this.sigTime = sigTime;
    }

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

    public String getOrigOrgName() {
        return origOrgName;
    }

    public void setOrigOrgName(String origOrgName) {
        this.origOrgName = origOrgName;
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName;
    }

    public String getHasTrack() {
        return hasTrack;
    }

    public void setHasTrack(String hasTrack) {
        this.hasTrack = hasTrack;
    }

    public String getCodType() {
        return codType;
    }

    public void setCodType(String codType) {
        this.codType = codType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public Date getComfirmTime() {
        return comfirmTime;
    }

    public void setComfirmTime(Date comfirmTime) {
        this.comfirmTime = comfirmTime;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public BigDecimal getCodAmountOfCH() {
		return codAmountOfCH;
	}

	public void setCodAmountOfCH(BigDecimal codAmountOfCH) {
		this.codAmountOfCH = codAmountOfCH;
	}

	public BigDecimal getCodAmountOfCD() {
		return codAmountOfCD;
	}

	public void setCodAmountOfCD(BigDecimal codAmountOfCD) {
		this.codAmountOfCD = codAmountOfCD;
	}

	public BigDecimal getCodAmountOfTT() {
		return codAmountOfTT;
	}

	public void setCodAmountOfTT(BigDecimal codAmountOfTT) {
		this.codAmountOfTT = codAmountOfTT;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public BigDecimal getCodFCAmount() {
		return codFCAmount;
	}

	public void setCodFCAmount(BigDecimal codFCAmount) {
		this.codFCAmount = codFCAmount;
	}
	public String getDestCashierName() {
		return destCashierName;
	}

	public void setDestCashierName(String destCashierName) {
		this.destCashierName = destCashierName;
	}
	
	public String getDestMobilePhone() {
		return destMobilePhone;
	}

	public void setDestMobilePhone(String destMobilePhone) {
		this.destMobilePhone = destMobilePhone;
	}
	public String getManageDepartment() {
		return manageDepartment;
	}

	public void setManageDepartment(String manageDepartment) {
		this.manageDepartment = manageDepartment;
	}
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}



	
}
