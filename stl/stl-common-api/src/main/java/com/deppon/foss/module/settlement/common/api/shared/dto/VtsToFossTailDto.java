package com.deppon.foss.module.settlement.common.api.shared.dto;
import java.io.Serializable;
import java.util.Date;


/** 
 * 签收确认收入服务使用DTO
 * 
 * @author caopeng 310970
 * @date 2016-5-28
 * @since
 * @version
 */
public class VtsToFossTailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1705023777829424395L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运输性质
	 */
	private String productType;

	/**
	 * 签收部门编码
	 */
	private String signOrgCode;

	/**
	 * 签收部门名称
	 */
	private String signOrgName;

	/**
	 * 业务日期（存放：签收/反签收日期）
	 * 注意：签收记录生成的系统时间（非业务人员输入时间）
	 */
	/*private Date signDate;*/

	/**
	 * 是否整车运单
	 */
	private String isWholeVehicle;

	/**
	 * 操作人编码
	 */
	private String operatorCode;

	/**
	 * 操作人名称
	 */
	private String operatorName;

	/**
	 * 签收类型 专线正常签收、偏线/空运正常签收
	 */
	private String signType;

	/**
	 * 记录结算单据条数（结算内部使用：用于判断签收时，是否存在结算的单据）
	 */
	private int stlBillCounts;
	
	/**
	 * 返货新开单所用,true
	 */
	private Boolean isReturnNewWaybill;
	
	/**
	 * 运单类型  默认:整车尾款
	 */
	private String billType;
	
	/**
	 *  以下字段不需要传
	 */
	/**
	 * 外发流水号
	 */
	private String serialNo;
	
	/**
	 * 外发单号
	 */
	private String externalWaybillNo;
	
	

	public String getExternalWaybillNo() {
		return externalWaybillNo;
	}

	public void setExternalWaybillNo(String externalWaybillNo) {
		this.externalWaybillNo = externalWaybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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
	 * @return productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return signOrgCode
	 */
	public String getSignOrgCode() {
		return signOrgCode;
	}

	/**
	 * @param signOrgCode
	 */
	public void setSignOrgCode(String signOrgCode) {
		this.signOrgCode = signOrgCode;
	}

	/**
	 * @return signOrgName
	 */
	public String getSignOrgName() {
		return signOrgName;
	}

	/**
	 * @param signOrgName
	 */
	public void setSignOrgName(String signOrgName) {
		this.signOrgName = signOrgName;
	}

	/**
	 * @return signDate
	 */
	/*public Date getSignDate() {
		return signDate;
	}*/

	/**
	 * @param signDate
	 */
	/*public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}*/

	/**
	 * @return isWholeVehicle
	 */
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * @param isWholeVehicle
	 */
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * @return operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param operatorName
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * @return signType
	 */
	public String getSignType() {
		return signType;
	}

	/**
	 * @param signType
	 */
	public void setSignType(String signType) {
		this.signType = signType;
	}

	/**
	 * @return stlBillCounts
	 */
	public int getStlBillCounts() {
		return stlBillCounts;
	}

	/**
	 * @param stlBillCounts
	 */
	public void setStlBillCounts(int stlBillCounts) {
		this.stlBillCounts = stlBillCounts;
	}

	public Boolean getIsReturnNewWaybill() {
		return isReturnNewWaybill;
	}

	public void setIsReturnNewWaybill(Boolean isReturnNewWaybill) {
		this.isReturnNewWaybill = isReturnNewWaybill;
	}
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

}
