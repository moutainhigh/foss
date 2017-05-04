package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;



/**
* @description 异常货实体类
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年6月1日 上午8:50:48
*/
public class QmsYchEntity extends BaseEntity{
	
	/**
	* @fields serialVersionUID
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午9:07:39
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	//是否驻地部门
	private String isStation;
	//目的部门所属外场
	private String transferCenter;
	
	//开单日期
	private Date billTime;
	//开单金额
	private int totalFee;
	//代收货款金额
	private int codAmount;
	//预付金额
	private int prePayAmount;
	//到付金额
	private int toPayAmount;
	//开单品名
	private String goodsName;
	//收货部门code
	private String receiveOrgCode;
	//收货部门
	private String receiveOrgName;
	//到达部门code
	private String targetOrgCode;
	//到达部门
	private String targetOrgName;
	//库存部门code
	private String orgCode;
	//库存部门
	private String orgName;
	//到达大区code
	private String bigdeptcode;
	//到达大区
	private String bigdept;
	//到达事业部code
	private String divisioncode;
	//到达事业部
	private String division;
	//开单件数
	private int goodsQtyTotal;
	//开单重量
	private Double goodsWeightTotal;
	//开单体积
	private Double goodsVolumeTotal;
	//保险价值
	private int insuranceAmount;
	//运输性质code
	private String productCode;
	//运输性质name
	private String productName;
	//签收结果code
	private String signSituationCode;
	//签收结果
	private String signSituation;
	
	public String getIsStation() {
		return isStation;
	}
	public void setIsStation(String isStation) {
		this.isStation = isStation;
	}
	public String getTransferCenter() {
		return transferCenter;
	}
	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getBigdeptcode() {
		return bigdeptcode;
	}
	public void setBigdeptcode(String bigdeptcode) {
		this.bigdeptcode = bigdeptcode;
	}
	public String getDivisioncode() {
		return divisioncode;
	}
	public void setDivisioncode(String divisioncode) {
		this.divisioncode = divisioncode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSignSituationCode() {
		return signSituationCode;
	}
	public void setSignSituationCode(String signSituationCode) {
		this.signSituationCode = signSituationCode;
	}
	public String getBigdept() {
		return bigdept;
	}
	public void setBigdept(String bigdept) {
		this.bigdept = bigdept;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getSignSituation() {
		return signSituation;
	}
	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	public int getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(int codAmount) {
		this.codAmount = codAmount;
	}
	public int getPrePayAmount() {
		return prePayAmount;
	}
	public void setPrePayAmount(int prePayAmount) {
		this.prePayAmount = prePayAmount;
	}
	public int getToPayAmount() {
		return toPayAmount;
	}
	public void setToPayAmount(int toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getTargetOrgName() {
		return targetOrgName;
	}
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public Double getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(Double goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	public Double getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	public void setGoodsVolumeTotal(Double goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	public int getInsuranceAmount() {
		return insuranceAmount;
	}
	public void setInsuranceAmount(int insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
}
