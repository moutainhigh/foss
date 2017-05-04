package com.deppon.foss.module.settlement.common.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class CodAuditForVtsSignEntity extends BaseEntity {
		
    private String id;
    private String waybillNo;
    private String active;
    private BigDecimal codAmount;
    private String lockStatus;
    
    private Date sigTime;
    private String origOrgCode;

    private String origOrgName;
    private String destOrgCode;
    private String destOrgName;

    private String hasTrack;
    private String codType;
    private String paymentType;

    private Date billTime;

    private Date comfirmTime;
    private String accountNo;
    private String mobileNo;

    private Date createDate;

    private Date modifyDate;

    private String createUser;

    private String modifyUser;
    
    private String bank;

    private String customerCode;
    private String customerName;
	private String destCashierName;
    private String destMobilePhone;
    private String manageDepartment;
    private BigDecimal changeAmount ;
	private BigDecimal  codAmountOfCH;
    private BigDecimal  codAmountOfCD;
  
    private BigDecimal  codAmountOfTT;
    
    private BigDecimal  codFCAmount;
    
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
