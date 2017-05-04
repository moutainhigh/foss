package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;

/**
 * 
* @description 返回发车计划信息实体
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:42:49
 */
public class DepartPlanInfoDetailDto {
	
	/**
	 * 司机code
	 */
	private String driverCode;
	
	/**
	 * 司机姓名
	 */
	private String driverName;
	
	/**
	 * 司机电话
	 */
	private String driverPhone;
	
	/**
	 * 班次
	 */
	private String shift;
	
	/**
	 * 车型
	 */
	private String vechileModel;
	
	/**
	 * 货柜号
	 */
	private String containerNo;
	
	/**
	 * 运行时数
	 */
	private BigDecimal runHours;
	
	/**
	 * 线路虚拟编码
	 */
	private String lineVirtualNo;
	
	/**
	 * 挂牌号
	 * */
	private String brandNo;

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getVechileModel() {
		return vechileModel;
	}

	public void setVechileModel(String vechileModel) {
		this.vechileModel = vechileModel;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public BigDecimal getRunHours() {
		return runHours;
	}

	public void setRunHours(BigDecimal runHours) {
		this.runHours = runHours;
	}

	public String getLineVirtualNo() {
		return lineVirtualNo;
	}

	public void setLineVirtualNo(String lineVirtualNo) {
		this.lineVirtualNo = lineVirtualNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
}