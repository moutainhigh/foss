package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * vts签收结果表entity
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-23 下午4:11:48    
 */
public class WaybillSignResultEntity extends BaseEntity {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 8554181972428281435L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 签收情况（正常签收、异常签收）--详细信息:1.NORMAL_SIGN-正常签收
	 */
	private String signSituation;
	
	/**
	 * 提货人名称
	 */
	private String deliverymanName;
	
	/**
	 * 签收件数
	 */
	private int signGoodsQty;
	
	/**
	 * 签收备注
	 */
	private String signNote;
	
	/**
	 * 签收时间
	 */
	private Date signTime;
	
	/**
	 * 生效时间
	 */
	private Date createTime;
	
	/**
	 * 失效时间
	 */
	private Date modifyTime;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 证件类型--详细信息:1.ID_CARD-身份证
	 */
	private String identifyType;
	
	/**
	 * 证件号码
	 */
	private String identifyCode;
	
	/**
	 * 是否PDA签收
	 */
	private String isPdaSign;
	
	/**
	 * 签收状态（部分签收、全部签收）--详细信息:1.SIGN_STATUS_ALL-全部签收
	 */
	private String signStatus;
	
	/**
	 * 代理编码
	 */
	private String agentCode;
	
	/**
	 * 是否审批中
	 */
	private String isRfcing;
	/**
	 * 送货时间
	 */
	private Date deliverDate;
	
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 是否初始化
	 */
	private String isInit;
	
	/**
	 * 签收部门编码
	 */
	private String createOrgCode;
	
	/**
	 * 签收部门名称
	 */
	private String createOrgName;
	
	/**
	 * 操作人
	 */
	private String creator;
	
	/**
	 * 操作人编码
	 */
	private String creatorCode;
	
	/**
	 * 合并金额付款方式--快递新
	 */
	private String totalPaymentType;
	
	/**
	 * 派送快递点部名称
	 */
	private String expressOrgCode;
	
	/**
	 * 快递点部NAME
	 */
	private String expressOrgName;
	
	/**
	 * 派送快递员code
	 */
	private String expressEmpCode;
	
	/**
	 * 派送快递员名称
	 */
	private String expressEmpName;
	
	/**
	 * 到付金额--快递新增
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 代收货款--快递新增
	 */
	private BigDecimal codAmount;
	
	/**
	 * 代收货款付款方式--快递新
	 */
	private String codPaymentType;
	
	/**
	 * 代收货款交易流水号--快递新
	 */
	private String codBankTradeSerail;
	
	/**
	 * 合并金额--快递新增
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 是否伙伴派送(是(Y)、否(N).默认为否(N))
	 */
	private String isPartner;
	
	/**
	 * 伙伴姓名
	 */
	private String partnerName;
	
	/**
	 * 伙伴电话(合作伙伴的联系方式，11位数字，不可输入座机号码)
	 */
	private String partnerPhone;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	public String getDeliverymanName() {
		return deliverymanName;
	}

	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	public int getSignGoodsQty() {
		return signGoodsQty;
	}

	public void setSignGoodsQty(int signGoodsQty) {
		this.signGoodsQty = signGoodsQty;
	}

	public String getSignNote() {
		return signNote;
	}

	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public String getIsPdaSign() {
		return isPdaSign;
	}

	public void setIsPdaSign(String isPdaSign) {
		this.isPdaSign = isPdaSign;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getIsRfcing() {
		return isRfcing;
	}

	public void setIsRfcing(String isRfcing) {
		this.isRfcing = isRfcing;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getIsInit() {
		return isInit;
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	public String getTotalPaymentType() {
		return totalPaymentType;
	}

	public void setTotalPaymentType(String totalPaymentType) {
		this.totalPaymentType = totalPaymentType;
	}

	public String getExpressOrgCode() {
		return expressOrgCode;
	}

	public void setExpressOrgCode(String expressOrgCode) {
		this.expressOrgCode = expressOrgCode;
	}

	public String getExpressOrgName() {
		return expressOrgName;
	}

	public void setExpressOrgName(String expressOrgName) {
		this.expressOrgName = expressOrgName;
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

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public String getCodPaymentType() {
		return codPaymentType;
	}

	public void setCodPaymentType(String codPaymentType) {
		this.codPaymentType = codPaymentType;
	}

	public String getCodBankTradeSerail() {
		return codBankTradeSerail;
	}

	public void setCodBankTradeSerail(String codBankTradeSerail) {
		this.codBankTradeSerail = codBankTradeSerail;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}
	
	
	
}
