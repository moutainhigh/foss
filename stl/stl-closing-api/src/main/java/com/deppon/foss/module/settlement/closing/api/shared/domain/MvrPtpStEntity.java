package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 合伙人股份中转月报表entity
 */
public class MvrPtpStEntity extends BaseEntity implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6350362771612989118L;
	
	/**
	 * id
	 */
    private String id;

    /**
	 * 期间
	 */
    private String period;

    /**
	 * 业务类型（运输性质）
	 */
    private String productCode;

    /**
	 * 客户编码
	 */
    private String customerCode;

    /**
	 * 客户名称
	 */
    private String customerName;

    /**
	 * 始发部门编码
	 */
    private String origOrgCode;

    /**
	 * 始发部门名称
	 */
    private String origOrgName;

    /**
	 * 到达部门编码
	 */
    private String destOrgCode;

    /**
	 * 到达部门名称
	 */
    private String destOrgName;

    /**
	 * 应收部门编码
	 */
    private String recOrgCode;

    /**
	 * 应收部门名称
	 */
    private String recOrgName;

    /**
	 * 应付部门编码
	 */
    private String payOrgCode;

    /**
	 * 应付部门编码
	 */
    private String payOrgName;

    /**
	 * 预收部门编码
	 */
    private String depOrgCode;

    /**
	 * 预收部门名称
	 */
    private String depOrgName;

    /**
	 * 预收加盟商款项
	 */
    private BigDecimal depPtpAmount;

    /**
	 * 预收加盟冲应收代收货款已签收
	 */
    private BigDecimal depWoRecPod;

    /**
	 * 退预收付款申请
	 */
    private BigDecimal rdDrPayApply;

    /**
	 * 还款电汇未签收
	 */
    private BigDecimal orTtPod;

    /**
	 * 还款电汇已签收
	 */
    private BigDecimal orTtNpod;

    /**
	 * 理赔冲成本
	 */
    private BigDecimal claimOrigtCost;

    /**
	 * 理赔入收入
	 */
    private BigDecimal claimOrigtIncome;

    /**
	 * 代打木架应收
	 */
    private BigDecimal hitWoodenRec;

    /**
	 * 到达提成付款申请
	 */
    private BigDecimal arrivePayApply;

    /**
	 * 委托派费代付申请
	 */
    private BigDecimal deAdvanceApply;

    /**
	 * 到付运费代付申请
	 */
    private BigDecimal destAdvanceApply;

    /**
	 * 奖励付款申请
	 */
    private BigDecimal adPayApply;

    /**
	 * 凭证开始日期
	 */
    private Date voucherBeginTime;

    /**
	 * 凭证结束日期
	 */
    private Date voucherEndTime;
    
    /**
     * 培训费收款
     */
    private BigDecimal stTrcRec;
    
    /**
     * 会务费收款
     */
    private BigDecimal stCmcRec;
    
    /**
	 * 还款电汇（H）
	 */
    private BigDecimal stTtReph;
    
    /**
	 * 预收加盟冲应收到付运费已签收(D)
	 */
    private BigDecimal stPdeCsrd;
    
    /**
     * 预收加盟冲应收到付运费已签收（H）
     */
    private BigDecimal stPdeCsrh;
    
    /**
     * 还款到付运费
     */
    private BigDecimal stRtfRtp;
    
    /**
     * 委托派费代付已签收
     */
    private BigDecimal stCphFpc;
    
    /**
     * 委托派费代付反签收
     */
    private BigDecimal stCphPrc;
        
    /**
     * 凭证报表优化新增 2017-02-28
     * 奖励应付付款申请  奖励自动返需求 355019
     */
    private BigDecimal stAdPayPmApply;
    
    /**
     * 凭证报表优化新增 2017-02-28
     * 快递差错应付付款申请 奖励自动返需求 355019
     */
    private BigDecimal stEePayPmApply;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode == null ? null : origOrgCode.trim();
    }

    public String getOrigOrgName() {
        return origOrgName;
    }

    public void setOrigOrgName(String origOrgName) {
        this.origOrgName = origOrgName == null ? null : origOrgName.trim();
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode == null ? null : destOrgCode.trim();
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName == null ? null : destOrgName.trim();
    }

    public String getRecOrgCode() {
        return recOrgCode;
    }

    public void setRecOrgCode(String recOrgCode) {
        this.recOrgCode = recOrgCode == null ? null : recOrgCode.trim();
    }

    public String getRecOrgName() {
        return recOrgName;
    }

    public void setRecOrgName(String recOrgName) {
        this.recOrgName = recOrgName == null ? null : recOrgName.trim();
    }

    public String getPayOrgCode() {
        return payOrgCode;
    }

    public void setPayOrgCode(String payOrgCode) {
        this.payOrgCode = payOrgCode == null ? null : payOrgCode.trim();
    }

    public String getPayOrgName() {
        return payOrgName;
    }

    public void setPayOrgName(String payOrgName) {
        this.payOrgName = payOrgName == null ? null : payOrgName.trim();
    }

    public String getDepOrgCode() {
        return depOrgCode;
    }

    public void setDepOrgCode(String depOrgCode) {
        this.depOrgCode = depOrgCode == null ? null : depOrgCode.trim();
    }

    public String getDepOrgName() {
        return depOrgName;
    }

    public void setDepOrgName(String depOrgName) {
        this.depOrgName = depOrgName == null ? null : depOrgName.trim();
    }

    public BigDecimal getDepPtpAmount() {
        return depPtpAmount;
    }

    public void setDepPtpAmount(BigDecimal depPtpAmount) {
        this.depPtpAmount = depPtpAmount;
    }

    public BigDecimal getDepWoRecPod() {
        return depWoRecPod;
    }

    public void setDepWoRecPod(BigDecimal depWoRecPod) {
        this.depWoRecPod = depWoRecPod;
    }

    public BigDecimal getRdDrPayApply() {
        return rdDrPayApply;
    }

    public void setRdDrPayApply(BigDecimal rdDrPayApply) {
        this.rdDrPayApply = rdDrPayApply;
    }

    public BigDecimal getOrTtPod() {
        return orTtPod;
    }

    public void setOrTtPod(BigDecimal orTtPod) {
        this.orTtPod = orTtPod;
    }

    public BigDecimal getOrTtNpod() {
        return orTtNpod;
    }

    public void setOrTtNpod(BigDecimal orTtNpod) {
        this.orTtNpod = orTtNpod;
    }

    public BigDecimal getClaimOrigtCost() {
        return claimOrigtCost;
    }

    public void setClaimOrigtCost(BigDecimal claimOrigtCost) {
        this.claimOrigtCost = claimOrigtCost;
    }

    public BigDecimal getClaimOrigtIncome() {
        return claimOrigtIncome;
    }

    public void setClaimOrigtIncome(BigDecimal claimOrigtIncome) {
        this.claimOrigtIncome = claimOrigtIncome;
    }

    public BigDecimal getHitWoodenRec() {
        return hitWoodenRec;
    }

    public void setHitWoodenRec(BigDecimal hitWoodenRec) {
        this.hitWoodenRec = hitWoodenRec;
    }

    public BigDecimal getArrivePayApply() {
        return arrivePayApply;
    }

    public void setArrivePayApply(BigDecimal arrivePayApply) {
        this.arrivePayApply = arrivePayApply;
    }

    public BigDecimal getDeAdvanceApply() {
        return deAdvanceApply;
    }

    public void setDeAdvanceApply(BigDecimal deAdvanceApply) {
        this.deAdvanceApply = deAdvanceApply;
    }

    public BigDecimal getDestAdvanceApply() {
        return destAdvanceApply;
    }

    public void setDestAdvanceApply(BigDecimal destAdvanceApply) {
        this.destAdvanceApply = destAdvanceApply;
    }

    public BigDecimal getAdPayApply() {
        return adPayApply;
    }

    public void setAdPayApply(BigDecimal adPayApply) {
        this.adPayApply = adPayApply;
    }

    public Date getVoucherBeginTime() {
        return voucherBeginTime;
    }

    public void setVoucherBeginTime(Date voucherBeginTime) {
        this.voucherBeginTime = voucherBeginTime;
    }

    public Date getVoucherEndTime() {
        return voucherEndTime;
    }

    public void setVoucherEndTime(Date voucherEndTime) {
        this.voucherEndTime = voucherEndTime;
    }

	/**
	 * @return the stTrcRec
	 */
	public BigDecimal getStTrcRec() {
		return stTrcRec;
	}

	/**
	 * @param stTrcRec the stTrcRec to set
	 */
	public void setStTrcRec(BigDecimal stTrcRec) {
		this.stTrcRec = stTrcRec;
	}

	/**
	 * @return the stCmcRec
	 */
	public BigDecimal getStCmcRec() {
		return stCmcRec;
	}

	/**
	 * @param stCmcRec the stCmcRec to set
	 */
	public void setStCmcRec(BigDecimal stCmcRec) {
		this.stCmcRec = stCmcRec;
	}

	/**
	 * @return the stTtReph
	 */
	public BigDecimal getStTtReph() {
		return stTtReph;
	}

	/**
	 * @param stTtReph the stTtReph to set
	 */
	public void setStTtReph(BigDecimal stTtReph) {
		this.stTtReph = stTtReph;
	}

	/**
	 * @return the stPdeCsrd
	 */
	public BigDecimal getStPdeCsrd() {
		return stPdeCsrd;
	}

	/**
	 * @param stPdeCsrd the stPdeCsrd to set
	 */
	public void setStPdeCsrd(BigDecimal stPdeCsrd) {
		this.stPdeCsrd = stPdeCsrd;
	}

	/**
	 * @return the stPdeCsrh
	 */
	public BigDecimal getStPdeCsrh() {
		return stPdeCsrh;
	}

	/**
	 * @param stPdeCsrh the stPdeCsrh to set
	 */
	public void setStPdeCsrh(BigDecimal stPdeCsrh) {
		this.stPdeCsrh = stPdeCsrh;
	}

	/**
	 * @return the stRtfRtp
	 */
	public BigDecimal getStRtfRtp() {
		return stRtfRtp;
	}

	/**
	 * @param stRtfRtp the stRtfRtp to set
	 */
	public void setStRtfRtp(BigDecimal stRtfRtp) {
		this.stRtfRtp = stRtfRtp;
	}

	/**
	 * @return the stCphFpc
	 */
	public BigDecimal getStCphFpc() {
		return stCphFpc;
	}

	/**
	 * @param stCphFpc the stCphFpc to set
	 */
	public void setStCphFpc(BigDecimal stCphFpc) {
		this.stCphFpc = stCphFpc;
	}

	/**
	 * @return the stCphPrc
	 */
	public BigDecimal getStCphPrc() {
		return stCphPrc;
	}

	/**
	 * @param stCphPrc the stCphPrc to set
	 */
	public void setStCphPrc(BigDecimal stCphPrc) {
		this.stCphPrc = stCphPrc;
	}

	/**
	 * @return the stAdPayPmApply
	 */
	public BigDecimal getStAdPayPmApply() {
		return stAdPayPmApply;
	}
	
	/**
	 * @param stAdPayPmApply the stAdPayPmApply to set
	 */
	public void setStAdPayPmApply(BigDecimal stAdPayPmApply) {
		this.stAdPayPmApply = stAdPayPmApply;
	}
	
	/**
	 * @return the stEePayPmApply
	 */
	public BigDecimal getStEePayPmApply() {
		return stEePayPmApply;
	}
	
	/**
	 * @param stEePayPmApply the stEePayPmApply to set
	 */
	public void setStEePayPmApply(BigDecimal stEePayPmApply) {
		this.stEePayPmApply = stEePayPmApply;
	}
    
}