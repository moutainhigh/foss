/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-consumer-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.consumer.api.shared.dto
 * FILE    NAME: WaybillPickupInfoDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 运单开单信息DTO
 * 
 * @author ibm-zhuwei
 * @date 2012-12-28 下午2:19:26
 */
public class WaybillPickupInfoDto extends WaybillEntity {

	private static final long serialVersionUID = -6060975106573313129L;

	/**
	 * 出发部门名称
	 */
	private String receiveOrgName;

	
	/**
	 * 出发子公司编码
	 */
	private String receiveSubsidiaryCode;
	
	/**
	 * 出发子公司名称
	 */
	private String receiveSubsidiaryName;

	/**
	 * 到达部门名称
	 */
	private String lastLoadOrgName;

	/**
	 * 到达子公司编码
	 */
	private String lastLoadSubsidiaryCode;
	
	/**
	 * 到达子公司名称
	 */
	private String lastLoadSubsidiaryName;

	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;
	
	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;
	
	/**
	 * 收款部门子公司编码
	 */
	private String collectionCompanyCode;
	
	/**
	 * 收款部门子公司名称
	 */
	private String collectionCompanyName;
	
	/**
	 * 是否集中接货
	 */
	private String pickupCentralized;

	// ---------------- 以下代收货款银企账号信息 ------------------

	/**
	 * 开户行编码
	 */
	private String bankHQCode;

	/**
	 * 开户行名称
	 */
	private String bankHQName;

	/**
	 * 支行编码（行号）
	 */
	private String bankBranchCode;

	/**
	 * 支行名称
	 */
	private String bankBranchName;

	/**
	 * 省份编码
	 */
	private String provinceCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 对公对私标志
	 */
	private String publicPrivateFlag;

	/**
	 * 收款人与发货人关系
	 */
	private String payeeRelationship;

	/**
	 * 收款人手机号码
	 */
	private String payeePhone;
	
	/**
	 * POS串号
	 */
	private String posSerialNum;
	
	/**
	 * 银行交易流水号
	 */
	private String batchNo;
	
	/**
	 * 结算专用：是否需要查询快递点部（自有的、自有的非外场）
	 */
	private String isSelfStation;
	/**
	 * 发票标记
	 */
	private String invoiceMark;

    /**
     *始发统一结算
     */
    private String origUnifiedSettlement;
    /**
     *到达统一结算
     */
	private String destUnifiedSettlement;
    /**
     * 出发统一结算合同部门标杆编码
     */
    private String origContractUnifiedCode;
    /**
     * 到达统一结算合同部门标杆编码
     */
    private String destContractUnifiedCode;
    /**
     *出发催款部门标杆编码
     */
    private String origUnifiedDuningCode;

    /**
     *到达催款部门标杆编码
     *
     */
    private String destUnifiedDuningCode;
    
    /**
     * 合同部门编码 @218392 张永雪
     * @date 2016-05-23 09:04:15
     */
    private String contractOrgCode;
    /**
     * 合同部门名称 @218392 张永雪
     * @date 2016-05-23 09:04:15
     */
    private String contractOrgName;
    
    /**
     * 应收部门编码 @218392 张永雪
     * @date 2016-05-23 09:04:15
     */
    private String receivableOrgCode;
    
    /**
     * 应收部门名称 @218392 张永雪
     * @date 2016-05-23 09:04:15
     */
    private String receivableOrgName;
    
    /**
     * 催款部门编码 @218392 张永雪
     * @date 2016-05-23 09:04:15
     */
    private String dunningOrgCode;
    
    /**
     * 催款部门名称 @218392 张永雪
     * @date 2016-05-23 09:04:15
     */
    private String dunningOrgName;
    
    /**
     * 业务ID-VTS传过来的唯一标识 @author 218392 张永雪
     * @date 2016-05-28 13:00:05
     */
    private String businessId;
    
    /**
     * 原运单号：oldWaybillNo 运单多次发更改，取自距离此次更改时间最近的运单单号
     * @author 218392 张永雪
     * @date 2016-05-31 22:22:45
     */
    private String oldWaybillNo;
    
    /**
     * 操作类型 openBillType vts整车项目
     * @author 218392 张永雪
     * @date 2016-06-01 11:12:25
     * 运单操作类型：1.新增：add 2.更改：update 3.作废：disable 4.中止：stop
     */
    private String openBillType;
        
    /**
     * 快递员工号
     */
    private String expressEmpCode;
    
    /**
     * 快递员姓名
     */
    private String expressEmpName;
    
    /**
     * PDA开单时间
     */
    private Date pdaBillTime;
    
    /**
     * 是否转寄
     */
    private String isRedirect;
    
    /**
     * 中转费（转寄费）
     */
    private BigDecimal isRedirectFee;
    
    /**
     * 预付现金
     */
    private BigDecimal prePayAmountCH;
    
    /**
     * 预付月结
     */
    private BigDecimal prePayAmountCT;
    
    /**
     * 来源系统（系统来源于悟空的，不需要校验客户额度）
     */
    private String sourceSystem;
    
    /**
     * 转寄新单付款方式
     */
    private String redirectPaidMethod;
    
    /**
     * 是否裹裹运单<br/>
     * 结算根据标记判断是否是裹裹运单，如果isWrap 字段值是“Y”，
     * 则需校验暂存表是否存在支付信息，
     * 存在支付信息，就自动核销始发应收单，
     * 不存在支付信息，就不做自动核销操作；
     * 如果isWrap字段值为“N”,则不校验暂存表中的支付信息；
     * @author 326181
     * @date 2016-10-03 9:50:45
     * 
     */
    private String isWrap;
    
    
    /**
     * 始发营业部对应点部编码
     * @author 231434
     * @date 2016-11-2
     */
    private String expressOrigOrgCode;
    
    /**
     * 始发营业部对应点部名称
     * @author 231434
     * @date 2016-11-2
     */
    private String expressOrigOrgName;
    
    /**
     * 到达营业部对应点部编码
     * @author 231434
     * @date 2016-11-2
     */
    private String expressDestOrgCode;
    
    /**
     * 到达营业部对应点部名称
     * @author 231434
     * @date 2016-11-2
     */
    private String expressDestOrgName;
    
    /**
     * 到达部门是否合伙人
     * @return
     */
    private String isPtp;
    
    public String getOrigContractUnifiedCode() {
        return origContractUnifiedCode;
    }

    public void setOrigContractUnifiedCode(String origContractUnifiedCode) {
        this.origContractUnifiedCode = origContractUnifiedCode;
    }

    public String getDestContractUnifiedCode() {
        return destContractUnifiedCode;
    }

    public void setDestContractUnifiedCode(String destContractUnifiedCode) {
        this.destContractUnifiedCode = destContractUnifiedCode;
    }

    public String getOrigUnifiedDuningCode() {
        return origUnifiedDuningCode;
    }

    public void setOrigUnifiedDuningCode(String origUnifiedDuningCode) {
        this.origUnifiedDuningCode = origUnifiedDuningCode;
    }

    public String getDestUnifiedDuningCode() {
        return destUnifiedDuningCode;
    }

    public void setDestUnifiedDuningCode(String destUnifiedDuningCode) {
        this.destUnifiedDuningCode = destUnifiedDuningCode;
    }

    public String getOrigUnifiedSettlement() {
        return origUnifiedSettlement;
    }

    public void setOrigUnifiedSettlement(String origUnifiedSettlement) {
        this.origUnifiedSettlement = origUnifiedSettlement;
    }

    public String getDestUnifiedSettlement() {
        return destUnifiedSettlement;
    }

    public void setDestUnifiedSettlement(String destUnifiedSettlement) {
        this.destUnifiedSettlement = destUnifiedSettlement;
    }


    public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	/**
	 * @return receiveOrgName
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * @param receiveOrgName
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * @return lastLoadOrgName
	 */
	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	/**
	 * @param lastLoadOrgName
	 */
	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}

	/**
	 * @return bankHQCode
	 */
	public String getBankHQCode() {
		return bankHQCode;
	}

	/**
	 * @param bankHQCode
	 */
	public void setBankHQCode(String bankHQCode) {
		this.bankHQCode = bankHQCode;
	}

	/**
	 * @return bankHQName
	 */
	public String getBankHQName() {
		return bankHQName;
	}

	/**
	 * @param bankHQName
	 */
	public void setBankHQName(String bankHQName) {
		this.bankHQName = bankHQName;
	}

	/**
	 * @return bankBranchCode
	 */
	public String getBankBranchCode() {
		return bankBranchCode;
	}

	/**
	 * @param bankBranchCode
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	/**
	 * @return bankBranchName
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}

	/**
	 * @param bankBranchName
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	/**
	 * @return provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param provinceCode
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return publicPrivateFlag
	 */
	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}

	/**
	 * @param publicPrivateFlag
	 */
	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}

	/**
	 * @return payeeRelationship
	 */
	public String getPayeeRelationship() {
		return payeeRelationship;
	}

	/**
	 * @param payeeRelationship
	 */
	public void setPayeeRelationship(String payeeRelationship) {
		this.payeeRelationship = payeeRelationship;
	}

	
	/**
	 * @return payeePhone
	 */
	public String getPayeePhone() {
		return payeePhone;
	}

	
	/**
	 * @param payeePhone
	 */
	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}

	
	/**
	 * @return receiveSubsidiaryCode
	 */
	public String getReceiveSubsidiaryCode() {
		return receiveSubsidiaryCode;
	}

	
	/**
	 * @param receiveSubsidiaryCode
	 */
	public void setReceiveSubsidiaryCode(String receiveSubsidiaryCode) {
		this.receiveSubsidiaryCode = receiveSubsidiaryCode;
	}

	
	/**
	 * @return receiveSubsidiaryName
	 */
	public String getReceiveSubsidiaryName() {
		return receiveSubsidiaryName;
	}

	
	/**
	 * @param receiveSubsidiaryName
	 */
	public void setReceiveSubsidiaryName(String receiveSubsidiaryName) {
		this.receiveSubsidiaryName = receiveSubsidiaryName;
	}

	
	/**
	 * @return lastLoadSubsidiaryCode
	 */
	public String getLastLoadSubsidiaryCode() {
		return lastLoadSubsidiaryCode;
	}

	
	/**
	 * @param lastLoadSubsidiaryCode
	 */
	public void setLastLoadSubsidiaryCode(String lastLoadSubsidiaryCode) {
		this.lastLoadSubsidiaryCode = lastLoadSubsidiaryCode;
	}

	
	/**
	 * @return lastLoadSubsidiaryName
	 */
	public String getLastLoadSubsidiaryName() {
		return lastLoadSubsidiaryName;
	}

	
	/**
	 * @param lastLoadSubsidiaryName
	 */
	public void setLastLoadSubsidiaryName(String lastLoadSubsidiaryName) {
		this.lastLoadSubsidiaryName = lastLoadSubsidiaryName;
	}

	
	/**
	 * @return collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	
	/**
	 * @param collectionOrgName
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
	}

	
	/**
	 * @return collectionCompanyCode
	 */
	public String getCollectionCompanyCode() {
		return collectionCompanyCode;
	}

	
	/**
	 * @param collectionCompanyCode
	 */
	public void setCollectionCompanyCode(String collectionCompanyCode) {
		this.collectionCompanyCode = collectionCompanyCode;
	}

	
	/**
	 * @return collectionCompanyName
	 */
	public String getCollectionCompanyName() {
		return collectionCompanyName;
	}

	
	/**
	 * @param collectionCompanyName
	 */
	public void setCollectionCompanyName(String collectionCompanyName) {
		this.collectionCompanyName = collectionCompanyName;
	}

	
	/**
	 * @return collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	
	/**
	 * @param collectionOrgCode
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	
	/**
	 * @return pickupCentralized
	 */
	public String getPickupCentralized() {
		return pickupCentralized;
	}

	
	/**
	 * @param  pickupCentralized  
	 */
	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	/**
	 * @GET
	 * @return posSerialNum
	 */
	public String getPosSerialNum() {
		/*
		 *@get
		 *@ return posSerialNum
		 */
		return posSerialNum;
	}

	/**
	 * @SET
	 * @param posSerialNum
	 */
	public void setPosSerialNum(String posSerialNum) {
		/*
		 *@set
		 *@this.posSerialNum = posSerialNum
		 */
		this.posSerialNum = posSerialNum;
	}

	/**
	 * @GET
	 * @return batchNo
	 */
	public String getBatchNo() {
		/*
		 *@get
		 *@ return batchNo
		 */
		return batchNo;
	}

	/**
	 * @SET
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		/*
		 *@set
		 *@this.batchNo = batchNo
		 */
		this.batchNo = batchNo;
	}

	/**
	 * @GET
	 * @return isSelfStation
	 */
	public String getIsSelfStation() {
		/*
		 *@get
		 *@ return isSelfStation
		 */
		return isSelfStation;
	}

	/**
	 * @SET
	 * @param isSelfStation
	 */
	public void setIsSelfStation(String isSelfStation) {
		/*
		 *@set
		 *@this.isSelfStation = isSelfStation
		 */
		this.isSelfStation = isSelfStation;
	}

	public String getContractOrgCode() {
		return contractOrgCode;
	}

	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}

	public String getContractOrgName() {
		return contractOrgName;
	}

	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}

	public String getReceivableOrgCode() {
		return receivableOrgCode;
	}

	public void setReceivableOrgCode(String receivableOrgCode) {
		this.receivableOrgCode = receivableOrgCode;
	}

	public String getReceivableOrgName() {
		return receivableOrgName;
	}

	public void setReceivableOrgName(String receivableOrgName) {
		this.receivableOrgName = receivableOrgName;
	}

	public String getDunningOrgCode() {
		return dunningOrgCode;
	}

	public void setDunningOrgCode(String dunningOrgCode) {
		this.dunningOrgCode = dunningOrgCode;
	}

	public String getDunningOrgName() {
		return dunningOrgName;
	}

	public void setDunningOrgName(String dunningOrgName) {
		this.dunningOrgName = dunningOrgName;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getOldWaybillNo() {
		return oldWaybillNo;
	}

	public void setOldWaybillNo(String oldWaybillNo) {
		this.oldWaybillNo = oldWaybillNo;
	}

	public String getOpenBillType() {
		return openBillType;
	}

	public void setOpenBillType(String openBillType) {
		this.openBillType = openBillType;
	}
		public String getExpressEmpCode() {
		return expressEmpCode;
	}

	public void setExpressEmpCode(String expressEmpCode) {
		this.expressEmpCode = expressEmpCode;
	}

	public String getExpressEmpName() {
		return expressEmpName;
	}

	public void setExpressEmpName(String expressEmpName) {
		this.expressEmpName = expressEmpName;
	}

	public Date getPdaBillTime() {
		return pdaBillTime;
	}

	public void setPdaBillTime(Date pdaBillTime) {
		this.pdaBillTime = pdaBillTime;
	}

	public String getIsRedirect() {
		return isRedirect;
	}

	public void setIsRedirect(String isRedirect) {
		this.isRedirect = isRedirect;
	}

	public BigDecimal getIsRedirectFee() {
		return isRedirectFee;
	}

	public void setIsRedirectFee(BigDecimal isRedirectFee) {
		this.isRedirectFee = isRedirectFee;
	}

	public BigDecimal getPrePayAmountCH() {
		return prePayAmountCH;
	}

	public void setPrePayAmountCH(BigDecimal prePayAmountCH) {
		this.prePayAmountCH = prePayAmountCH;
	}

	public BigDecimal getPrePayAmountCT() {
		return prePayAmountCT;
	}

	public void setPrePayAmountCT(BigDecimal prePayAmountCT) {
		this.prePayAmountCT = prePayAmountCT;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getRedirectPaidMethod() {
		return redirectPaidMethod;
	}

	public void setRedirectPaidMethod(String redirectPaidMethod) {
		this.redirectPaidMethod = redirectPaidMethod;
	}

	public String getIsWrap() {
		return isWrap;
	}

	public void setIsWrap(String isWrap) {
		this.isWrap = isWrap;
	}

	public String getExpressOrigOrgCode() {
		return expressOrigOrgCode;
	}

	public void setExpressOrigOrgCode(String expressOrigOrgCode) {
		this.expressOrigOrgCode = expressOrigOrgCode;
	}

	public String getExpressOrigOrgName() {
		return expressOrigOrgName;
	}

	public void setExpressOrigOrgName(String expressOrigOrgName) {
		this.expressOrigOrgName = expressOrigOrgName;
	}

	public String getExpressDestOrgCode() {
		return expressDestOrgCode;
	}

	public void setExpressDestOrgCode(String expressDestOrgCode) {
		this.expressDestOrgCode = expressDestOrgCode;
	}

	public String getExpressDestOrgName() {
		return expressDestOrgName;
	}

	public void setExpressDestOrgName(String expressDestOrgName) {
		this.expressDestOrgName = expressDestOrgName;
	}

	public String getIsPtp() {
		return isPtp;
	}

	public void setIsPtp(String isPtp) {
		this.isPtp = isPtp;
	}

}
