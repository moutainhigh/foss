package com.deppon.foss.module.settlement.dubbo.api.define;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 家装安装查询实体
 * @author WangQianJin
 *
 */
public class InstallationEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2857757970083193295L;
	/**
	 * uuid
	 */
	private String uuid;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 特殊增值服务方式
	 */
	private String specialValueAddedServiceType;
	/**
	 * 安装名称
	 */
	private String installationName;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 安装件数
	 */
	private String installPackages;
	/**
	 * 搬运体积
	 */
	private String handlingVolume;
	/**
	 * 超标楼层数
	 */
	private String exceedNumberFloors;
	/**
	 * 是否有效
	 */
    private String active;
    /**
     * 人工搬楼费 
     */
	private String installationCode;
	/**
     * 最大值金额
     */
	private BigDecimal upperLimit;
	/**
     * 最小值金额
     */
	private BigDecimal lowerLimit;
    
	public BigDecimal getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(BigDecimal upperLimit) {
		this.upperLimit = upperLimit;
	}
	public BigDecimal getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public String getInstallationName() {
		return installationName;
	}
	public void setInstallationName(String installationName) {
		this.installationName = installationName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getInstallPackages() {
		return installPackages;
	}
	public void setInstallPackages(String installPackages) {
		this.installPackages = installPackages;
	}
	public String getHandlingVolume() {
		return handlingVolume;
	}
	public void setHandlingVolume(String handlingVolume) {
		this.handlingVolume = handlingVolume;
	}	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSpecialValueAddedServiceType() {
		return specialValueAddedServiceType;
	}
	public void setSpecialValueAddedServiceType(String specialValueAddedServiceType) {
		this.specialValueAddedServiceType = specialValueAddedServiceType;
	}
	public String getExceedNumberFloors() {
		return exceedNumberFloors;
	}
	public void setExceedNumberFloors(String exceedNumberFloors) {
		this.exceedNumberFloors = exceedNumberFloors;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getInstallationCode() {
		return installationCode;
	}
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}
	
}
