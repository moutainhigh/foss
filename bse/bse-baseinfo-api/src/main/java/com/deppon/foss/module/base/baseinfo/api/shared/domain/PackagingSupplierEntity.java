package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 包装供应商基础资料
 * @author 130346
 *
 */
public class PackagingSupplierEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4204930148770262525L;

	/**
	 * 外场code(包装部门)
	 */
	private String orgCode;
	
	/**
	 * 外场code(包装部门)(冗余)
	 */
	private String orgCodeCode;
	
	/**
	 * 供应商编码
	 */
	private String packagingSupplierCode;
	
	/**
	 * 包装供应商
	 * @return
	 */
	private String packagingSupplier;
	
	/**
	 * 包装供应商电弧
	 */
	private String packagingSupplierPhone;
	
	/**
	 * 打木架单价（方）
	 */
	private String woodenFrame;
	
	/**
	 * 打木托单价（个）
	 */
	private String woodPallet;
	
	/**
	 * 打包带单价（跟）
	 */
	private String bagLine;
	
	/**
	 * 木条单价（米）
	 */
	private String wood;
	
	/**
	 * 气泡膜单价（方）
	 */
	private String bubblefilm;
	
	/**
	 * 缠绕膜单价（方）
	 */
	private String wrappingFilm;
	
	/**
	 *打木箱（个） 
	 */
	private String woodBox;
	
	/**
	 * 破损率参数
	 */
	private String breakageRate;
	
	/**
	 * 打木架起步体积
	 */
	private String woodenFrameStartVolume;
	
	/**
	 * 打木箱最低一票
	 */
	private String woodenFrameMin;
	/**
	 * 打木箱起步体积
	 */
	private String woodBoxStartVolume;
	
	/**
	 * 打木箱最低一票
	 */
	private String woodBoxMin;
	
	/**
	 * 有效时间
	 */
	private String effectiveDate;
	
	/**
	 * 版本号
	 */
	private Long versionNo;
	
	
	/**
	 * active启用
	 */
	private String active;
	
	/**
	 * 是否保理
	 */
	private String factoring;
	
	/**
	 * 显示是否保理
	 */
	private String factoringDisplay;
	
	/**
	 * 保理开始日期
	 */
	private Date factorBeginTime;//格式为"yyyy-MM-dd"
	
	/**
	 * 保理结束日期
	 */
	private Date factorEndTime;//格式为"yyyy-MM-dd"
	
	/**
	 * 贷款客户编码
	 */
	private String cusCode;
	
	/**
	 * 保理回款账号
	 */
	private String account;
	
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPackagingSupplierCode() {
		return packagingSupplierCode;
	}

	public void setPackagingSupplierCode(String packagingSupplierCode) {
		this.packagingSupplierCode = packagingSupplierCode;
	}

	public String getPackagingSupplier() {
		return packagingSupplier;
	}

	public void setPackagingSupplier(String packagingSupplier) {
		this.packagingSupplier = packagingSupplier;
	}

	public String getPackagingSupplierPhone() {
		return packagingSupplierPhone;
	}

	public void setPackagingSupplierPhone(String packagingSupplierPhone) {
		this.packagingSupplierPhone = packagingSupplierPhone;
	}

	public String getWoodenFrame() {
		return woodenFrame;
	}

	public void setWoodenFrame(String woodenFrame) {
		this.woodenFrame = woodenFrame;
	}

	public String getWoodPallet() {
		return woodPallet;
	}

	public void setWoodPallet(String woodPallet) {
		this.woodPallet = woodPallet;
	}

	public String getBagLine() {
		return bagLine;
	}

	public void setBagLine(String bagLine) {
		this.bagLine = bagLine;
	}

	public String getWood() {
		return wood;
	}

	public void setWood(String wood) {
		this.wood = wood;
	}

	public String getBubblefilm() {
		return bubblefilm;
	}

	public void setBubblefilm(String bubblefilm) {
		this.bubblefilm = bubblefilm;
	}

	public String getWrappingFilm() {
		return wrappingFilm;
	}

	public void setWrappingFilm(String wrappingFilm) {
		this.wrappingFilm = wrappingFilm;
	}

	public String getWoodBox() {
		return woodBox;
	}

	public void setWoodBox(String woodBox) {
		this.woodBox = woodBox;
	}

	public String getBreakageRate() {
		return breakageRate;
	}

	public void setBreakageRate(String breakageRate) {
		this.breakageRate = breakageRate;
	}

	public String getWoodenFrameStartVolume() {
		return woodenFrameStartVolume;
	}

	public void setWoodenFrameStartVolume(String woodenFrameStartVolume) {
		this.woodenFrameStartVolume = woodenFrameStartVolume;
	}

	public String getWoodenFrameMin() {
		return woodenFrameMin;
	}

	public void setWoodenFrameMin(String woodenFrameMin) {
		this.woodenFrameMin = woodenFrameMin;
	}

	public String getWoodBoxStartVolume() {
		return woodBoxStartVolume;
	}

	public void setWoodBoxStartVolume(String woodBoxStartVolume) {
		this.woodBoxStartVolume = woodBoxStartVolume;
	}

	public String getWoodBoxMin() {
		return woodBoxMin;
	}

	public void setWoodBoxMin(String woodBoxMin) {
		this.woodBoxMin = woodBoxMin;
	}

	/**
	 * @return  the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return  the versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return  the orgCodeCode
	 */
	public String getOrgCodeCode() {
		return orgCodeCode;
	}

	/**
	 * @param orgCodeCode the orgCodeCode to set
	 */
	public void setOrgCodeCode(String orgCodeCode) {
		this.orgCodeCode = orgCodeCode;
	}

	public String getFactoring() {
		return factoring;
	}

	public void setFactoring(String factoring) {
		if("Y".equals(factoring)){
			this.setFactoringDisplay("是");
		} else {
			this.setFactoringDisplay("否");
		}
		this.factoring = factoring;
	}

	public String getFactoringDisplay() {
		if("Y".equals(factoring)){
			factoringDisplay = "是";
		} else {
			factoringDisplay = "否";
		}
		return factoringDisplay;
	}

	public void setFactoringDisplay(String factoringDisplay) {
		this.factoringDisplay = factoringDisplay;
	}

	public Date getFactorBeginTime() {
		return factorBeginTime;
	}

	public void setFactorBeginTime(Date factorBeginTime) {
		this.factorBeginTime = factorBeginTime;
	}

	public Date getFactorEndTime() {
		return factorEndTime;
	}

	public void setFactorEndTime(Date factorEndTime) {
		this.factorEndTime = factorEndTime;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}
