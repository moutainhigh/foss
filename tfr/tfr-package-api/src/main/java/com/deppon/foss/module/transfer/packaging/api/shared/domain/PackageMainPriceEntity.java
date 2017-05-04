package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
/*
 * @desc 主要包装金额实体
 * @author foss-105795-wqh
 * @date   2014-04-29
 * */
public class PackageMainPriceEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5285246659601362410L;

	// id
	private String id;
	//运单号
	private String waybillNo;
	//开单部门code
	private String billOrgCode;
	//开单部门名称
	private String billOrgName;
	//包装部门code
	private String packageOrgCode;
	//包装部门名称
	private String packageOrgName;
	//理论打木架体积
	private BigDecimal theoryFrameVolume;
	//实际打木架体积
	private BigDecimal actualFrameVolume;
	//打木架体积
	private BigDecimal packageFrameVolume;
	//理论打木箱体积
	private BigDecimal theoryWoodenVolume;
	//实际打木箱体积
	private BigDecimal actualWoodenVolume;
	//打木箱体积
	private BigDecimal packageWoodenVolume;
	//理论打木托个数
	private BigDecimal theoryMaskNumber;
	//实际打木托个数
	private BigDecimal actualMaskNumber;
	//打木托个数
	private BigDecimal packageMaskNumber;
	//应付金额
	private BigDecimal packagePayableMoney;
	//包装供应商code
	private String packageSupplierCode;
	//包装供应商
	private String packageSupplierName;
	//包装材料
	private String  packageMaterial;
	//创建部门code
	private String createOrgCode;
	//创建部门
	private String createOrgName;
	//创建人code
	private String createUserCode;
	//创建人
	private String createUserName;
	//创建时间
	private Date createTime;
	//修改人code
	private String modifyUserCode;
	//修改人
	private String modifyUserName;
	//精确到毫秒的修改时间
	private String modifyTimeMs;
	//修改时间
	private Date modifyTime;
	//是否当月单据
	private String isNowMonth;
	//审核状态
	private String auditStatus;
	//set and get
		
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getBillOrgCode() {
		return billOrgCode;
	}
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}
	public String getBillOrgName() {
		return billOrgName;
	}
	public void setBillOrgName(String billOrgName) {
		this.billOrgName = billOrgName;
	}
	public String getPackageOrgCode() {
		return packageOrgCode;
	}
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}
	public String getPackageOrgName() {
		return packageOrgName;
	}
	public void setPackageOrgName(String packageOrgName) {
		this.packageOrgName = packageOrgName;
	}
	public BigDecimal getTheoryFrameVolume() {
		return theoryFrameVolume;
	}
	public void setTheoryFrameVolume(BigDecimal theoryFrameVolume) {
		this.theoryFrameVolume = theoryFrameVolume;
	}
	public BigDecimal getActualFrameVolume() {
		return actualFrameVolume;
	}
	public void setActualFrameVolume(BigDecimal actualFrameVolume) {
		this.actualFrameVolume = actualFrameVolume;
	}
	public BigDecimal getPackageFrameVolume() {
		return packageFrameVolume;
	}
	public void setPackageFrameVolume(BigDecimal packageFrameVolume) {
		this.packageFrameVolume = packageFrameVolume;
	}
	public BigDecimal getTheoryWoodenVolume() {
		return theoryWoodenVolume;
	}
	public void setTheoryWoodenVolume(BigDecimal theoryWoodenVolume) {
		this.theoryWoodenVolume = theoryWoodenVolume;
	}
	public BigDecimal getActualWoodenVolume() {
		return actualWoodenVolume;
	}
	public void setActualWoodenVolume(BigDecimal actualWoodenVolume) {
		this.actualWoodenVolume = actualWoodenVolume;
	}
	public BigDecimal getPackageWoodenVolume() {
		return packageWoodenVolume;
	}
	public void setPackageWoodenVolume(BigDecimal packageWoodenVolume) {
		this.packageWoodenVolume = packageWoodenVolume;
	}
	public BigDecimal getTheoryMaskNumber() {
		return theoryMaskNumber;
	}
	public void setTheoryMaskNumber(BigDecimal theoryMaskNumber) {
		this.theoryMaskNumber = theoryMaskNumber;
	}
	public BigDecimal getActualMaskNumber() {
		return actualMaskNumber;
	}
	public void setActualMaskNumber(BigDecimal actualMaskNumber) {
		this.actualMaskNumber = actualMaskNumber;
	}
	public BigDecimal getPackageMaskNumber() {
		return packageMaskNumber;
	}
	public void setPackageMaskNumber(BigDecimal packageMaskNumber) {
		this.packageMaskNumber = packageMaskNumber;
	}
	public BigDecimal getPackagePayableMoney() {
		return packagePayableMoney;
	}
	public void setPackagePayableMoney(BigDecimal packagePayableMoney) {
		this.packagePayableMoney = packagePayableMoney;
	}
	public String getPackageSupplierCode() {
		return packageSupplierCode;
	}
	public void setPackageSupplierCode(String packageSupplierCode) {
		this.packageSupplierCode = packageSupplierCode;
	}
	public String getPackageSupplierName() {
		return packageSupplierName;
	}
	public void setPackageSupplierName(String packageSupplierName) {
		this.packageSupplierName = packageSupplierName;
	}
	public String getPackageMaterial() {
		return packageMaterial;
	}
	public void setPackageMaterial(String packageMaterial) {
		this.packageMaterial = packageMaterial;
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
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getModifyTimeMs() {
		return modifyTimeMs;
	}
	public void setModifyTimeMs(String modifyTimeMs) {
		this.modifyTimeMs = modifyTimeMs;
		if(StringUtils.isNotBlank(this.modifyTimeMs)){
			this.setModifyTime(new Date(Long.parseLong(this.modifyTimeMs)));
		}
	}
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss SSS")
	public Date getModifyTime() {
		return modifyTime;
	}
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss SSS")
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getIsNowMonth() {
		return isNowMonth;
	}
	public void setIsNowMonth(String isNowMonth) {
		this.isNowMonth = isNowMonth;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
