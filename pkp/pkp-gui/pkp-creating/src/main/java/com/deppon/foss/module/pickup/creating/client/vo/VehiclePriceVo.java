package com.deppon.foss.module.pickup.creating.client.vo;

import java.math.BigDecimal;

import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;

/**
 * 
 * @author DP-FOSS-YANGKANG
 * 整车报价vo
 */
public class VehiclePriceVo {

	
	// 运输性质
	private ProductEntityVo vehicleProductCode;
	//发票标记  
	private InvoiceMarkVo vehicleInvoiceMarkType;
	//包装费
	private BigDecimal vehiclePackageFee;
	//货物重量
	private BigDecimal vehicleProductWeight;
	//开单报价
	private BigDecimal vehicleBillFee;
	//约车报价
	private BigDecimal vehicleReserveCarFee;
	//总运费
	private BigDecimal vehicleTotalFee;
	//整车运费冲减
	private BigDecimal vehicleChangeFee;
	//当前登陆部门编码
	private String loginDeptCode;
	//当前登陆部门名称
	private String loginDeptName;
	//验证是否有误
	private boolean isError;
	//装卸费
	private BigDecimal handlingFee;
	
	public ProductEntityVo getVehicleProductCode() {
		return vehicleProductCode;
	}
	public void setVehicleProductCode(ProductEntityVo vehicleProductCode) {
		this.vehicleProductCode = vehicleProductCode;
	}
	public InvoiceMarkVo getVehicleInvoiceMarkType() {
		return vehicleInvoiceMarkType;
	}
	public void setVehicleInvoiceMarkType(InvoiceMarkVo vehicleInvoiceMarkType) {
		this.vehicleInvoiceMarkType = vehicleInvoiceMarkType;
	}
	public BigDecimal getVehiclePackageFee() {
		return vehiclePackageFee;
	}
	public void setVehiclePackageFee(BigDecimal vehiclePackageFee) {
		this.vehiclePackageFee = vehiclePackageFee;
	}
	public BigDecimal getVehicleProductWeight() {
		return vehicleProductWeight;
	}
	public void setVehicleProductWeight(BigDecimal vehicleProductWeight) {
		this.vehicleProductWeight = vehicleProductWeight;
	}
	public BigDecimal getVehicleBillFee() {
		return vehicleBillFee;
	}
	public void setVehicleBillFee(BigDecimal vehicleBillFee) {
		this.vehicleBillFee = vehicleBillFee;
	}
	public BigDecimal getVehicleReserveCarFee() {
		return vehicleReserveCarFee;
	}
	public void setVehicleReserveCarFee(BigDecimal vehicleReserveCarFee) {
		this.vehicleReserveCarFee = vehicleReserveCarFee;
	}
	public BigDecimal getVehicleTotalFee() {
		return vehicleTotalFee;
	}
	public void setVehicleTotalFee(BigDecimal vehicleTotalFee) {
		this.vehicleTotalFee = vehicleTotalFee;
	}
	public BigDecimal getVehicleChangeFee() {
		return vehicleChangeFee;
	}
	public void setVehicleChangeFee(BigDecimal vehicleChangeFee) {
		this.vehicleChangeFee = vehicleChangeFee;
	}
	public String getLoginDeptCode() {
		return loginDeptCode;
	}
	public void setLoginDeptCode(String loginDeptCode) {
		this.loginDeptCode = loginDeptCode;
	}
	public String getLoginDeptName() {
		return loginDeptName;
	}
	public void setLoginDeptName(String loginDeptName) {
		this.loginDeptName = loginDeptName;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public BigDecimal getHandlingFee() {
		return handlingFee;
	}
	public void setHandlingFee(BigDecimal handlingFee) {
		this.handlingFee = handlingFee;
	}
	
	
}
