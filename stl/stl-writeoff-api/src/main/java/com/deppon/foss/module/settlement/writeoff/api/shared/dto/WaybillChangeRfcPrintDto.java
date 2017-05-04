package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WaybillChangeDetailPrintDto;



/**
 * 更改单打印DTO
 * @author 095793-foss-LiQin
 * @date 2013-4-17 下午12:44:59
 */
public class WaybillChangeRfcPrintDto implements Serializable{

	/**
	 * 更改单打印DTO
	 */
	private static final long serialVersionUID = 1L;
	
	//打印次数
	private Integer printTimes;
	
	//运单号
	private String waybillNo;
	
	//更改原因
	private String rfcReason;
	
	//申请部门
	private String applyDept;
	
	//申请人
	private String applyPerson;
	
	//申请时间
	private String applyTime;
	
	
	//货物基本信息
	//货物名称
	private String goodsName;
	
	//包装
	private String pack;
	
	//件数
	private Integer pieces;
	
	//重量
	private BigDecimal weight;
	
	//体积
	private BigDecimal volume;
	
	//尺寸
	private String dimension;
	
	//更改单ID
	private String rfcId;
	
	//更改单类型
	private String rfcType;

	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 目的站
	 */
	private String targetOrgCode;
	
	//变更项
	private List<WaybillChangeDetailPrintDto> changeList;
	/**
	 * @return printTimes
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}
	/**
	 * @param printTimes
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}
	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return rfcReason
	 */
	public String getRfcReason() {
		return rfcReason;
	}
	/**
	 * @param rfcReason
	 */
	public void setRfcReason(String rfcReason) {
		this.rfcReason = rfcReason;
	}
	/**
	 * @return applyDept
	 */
	public String getApplyDept() {
		return applyDept;
	}
	/**
	 * @param applyDept
	 */
	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}
	/**
	 * @return applyPerson
	 */
	public String getApplyPerson() {
		return applyPerson;
	}
	/**
	 * @param applyPerson
	 */
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}
	/**
	 * @return applyTime
	 */
	public String getApplyTime() {
		return applyTime;
	}
	/**
	 * @param applyTime
	 */
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * @return goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * @return pack
	 */
	public String getPack() {
		return pack;
	}
	/**
	 * @param pack
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}
	/**
	 * @return pieces
	 */
	public Integer getPieces() {
		return pieces;
	}
	/**
	 * @param pieces
	 */
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}
	/**
	 * @return weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * @param weight
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * @return volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * @param volume
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**
	 * @return dimension
	 */
	public String getDimension() {
		return dimension;
	}
	/**
	 * @param dimension
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	/**
	 * @return rfcId
	 */
	public String getRfcId() {
		return rfcId;
	}
	/**
	 * @param rfcId
	 */
	public void setRfcId(String rfcId) {
		this.rfcId = rfcId;
	}
	/**
	 * @return rfcType
	 */
	public String getRfcType() {
		return rfcType;
	}
	/**
	 * @param rfcType
	 */
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}
	/**
	 * @return changeList
	 */
	public List<WaybillChangeDetailPrintDto> getChangeList() {
		return changeList;
	}
	/**
	 * @param changeList
	 */
	public void setChangeList(List<WaybillChangeDetailPrintDto> changeList) {
		this.changeList = changeList;
	}
	
	/**
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}
	/**
	 * @param customerPickupOrgCode
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	/**
	 * @return targetOrgCode
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	/**
	 * @param targetOrgCode
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
}
