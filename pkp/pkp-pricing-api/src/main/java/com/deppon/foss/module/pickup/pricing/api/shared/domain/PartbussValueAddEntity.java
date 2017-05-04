package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递代理理公司增值服務实体类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-zdp,date:2013-7-18 上午10:22:56
 * </p>
 * 
 * @author 094463-foss-zdp
 * @date 2013-7-18 上午10:22:56
 * @since
 * @version
 */
public class PartbussValueAddEntity extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *快递代理公司.
	 */
	private String expressPartbussCode;

	/**
	 快递代理递代理公司名称（扩展）
	 */
	private String expressPartbussName;

	/**
	 * 配载部门.
	 */
	private String loadOrgCode;

	/**
	 * 配载部门名称
	 */

	private String loadOrgName;

	/**
	 * 省份编码
	 */
	private String proCode;

	/**
	 * 省份名称
	 */
	private String proName;

	/**
	 * 生效日期.
	 */
	private Date beginTime;

	/**
	 * 结束日期.
	 */
	private Date endTime;

	private Date billDate;

	// 最后创建人编码
	private String lastCreatorCode;

	// 最后创建人名称
	private String lastCreatorName;

	// 最后创建时间
	private Date lastCreateTime;

	/**
	 * 数据状态.
	 */
	private String active;

	/**
	 * 备注.
	 */
	private String descption;

	/**
	 * 数据版本.
	 */
	private Long versionNo;

	/**
	 * 创建组织.
	 */
	private String createOrgCode;

	/**
	 * 更新组织.
	 */
	private String modifyOrgCode;

	/**
	 * 币种.
	 */
	private String currencyCode;

	/**
	 * 最低代收货款手续费.
	 */
	private BigDecimal minCodFee;

	/**
	 * 代收货款手续费率.
	 */
	private BigDecimal codRate;

	/**
	 * 保价费最低一票
	 */
	private BigDecimal minInsuranceFee;

	/**
	 * 保费费率.
	 */
	private BigDecimal insuranceFeeRate;
	
	/**
	 * 到付费率.
	 */
	private BigDecimal freightPayFeeRate;
	
	/**
	 * 到付费最低一票
	 */
	private BigDecimal minFreightPayFee;

	/**
	 * 行政區域code
	 */
	private String districtCode;

	/**
	 * 快递代理快递代理公司.
	 * 
	 * @return 快递代理 快递代理公司
	 */
	public String getExpressPartbussCode() {
		return expressPartbussCode;
	}

	/**
	 快递代理置 快递代理公司.
	 * 
	 * @param expressPartbussCode
	 *            th快递代理ew 快递代理公司
	 */
	public void setExpressPartbussCode(String expressPartbussCode) {
		this.expressPartbussCode = expressPartbussCode;
	}

	/**
	 * 获取 配载部门.
	 * 
	 * @return the 配载部门
	 */
	public String getLoadOrgCode() {

		return loadOrgCode;
	}

	/**
	 * 设置 配载部门.
	 * 
	 * @param loadOrgCode
	 *            the new 配载部门
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * 获取 生效日期.
	 * 
	 * @return the 生效日期
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置 生效日期.
	 * 
	 * @param beginTime
	 *            the new 生效日期
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 获取 结束日期.
	 * 
	 * @return the 结束日期
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置 结束日期.
	 * 
	 * @param endTime
	 *            the new 结束日期
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取 数据状态.
	 * 
	 * @return the 数据状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 数据状态.
	 * 
	 * @param active
	 *            the new 数据状态
	 */
	public void setActive(String active) {
		this.active = active;
	}


	/**
	 * 获取 数据版本.
	 * 
	 * @return the 数据版本
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * 设置 数据版本.
	 * 
	 * @param versionNo
	 *            the new 数据版本
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * 获取 创建组织.
	 * 
	 * @return the 创建组织
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 创建组织.
	 * 
	 * @param createOrgCode
	 *            the new 创建组织
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 更新组织.
	 * 
	 * @return the 更新组织
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * 设置 更新组织.
	 * 
	 * @param modifyOrgCode
	 *            the new 更新组织
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * 获取 币种.
	 * 
	 * @return the 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置 币种.
	 * 
	 * @param currencyCode
	 *            the new 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getCodRate() {
		if (codRate == null) {
			codRate = BigDecimal.ZERO;
		}
		return codRate;
	}

	public void setCodRate(BigDecimal codRate) {
		this.codRate = codRate;
	}

	public BigDecimal getInsuranceFeeRate() {
		if (insuranceFeeRate == null) {
			insuranceFeeRate = BigDecimal.ZERO;
		}
		return insuranceFeeRate;
	}

	public void setInsuranceFeeRate(BigDecimal insuranceFeeRate) {
		this.insuranceFeeRate = insuranceFeeRate;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getExpressPartbussName() {
		return expressPartbussName;
	}

	public void setExpressPartbussName(String expressPartbussName) {
		this.expressPartbussName = expressPartbussName;
	}

	public String getLoadOrgName() {
		return loadOrgName;
	}

	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getLastCreatorCode() {
		return lastCreatorCode;
	}

	public void setLastCreatorCode(String lastCreatorCode) {
		this.lastCreatorCode = lastCreatorCode;
	}

	public String getLastCreatorName() {
		return lastCreatorName;
	}

	public void setLastCreatorName(String lastCreatorName) {
		this.lastCreatorName = lastCreatorName;
	}

	public Date getLastCreateTime() {
		return lastCreateTime;
	}

	public void setLastCreateTime(Date lastCreateTime) {
		this.lastCreateTime = lastCreateTime;
	}

	/**
	 * @return the minCodFee
	 */
	public BigDecimal getMinCodFee() {
		return minCodFee;
	}

	/**
	 * @param minCodFee the minCodFee to set
	 */
	public void setMinCodFee(BigDecimal minCodFee) {
		this.minCodFee = minCodFee;
	}

	/**
	 * @return the minInsuranceFee
	 */
	public BigDecimal getMinInsuranceFee() {
		return minInsuranceFee;
	}

	/**
	 * @param minInsuranceFee the minInsuranceFee to set
	 */
	public void setMinInsuranceFee(BigDecimal minInsuranceFee) {
		this.minInsuranceFee = minInsuranceFee;
	}

	/**
	 * @return the freightPayFeeRate
	 */
	public BigDecimal getFreightPayFeeRate() {
		return freightPayFeeRate;
	}

	/**
	 * @param freightPayFeeRate the freightPayFeeRate to set
	 */
	public void setFreightPayFeeRate(BigDecimal freightPayFeeRate) {
		this.freightPayFeeRate = freightPayFeeRate;
	}

	/**
	 * @return the minFreightPayFee
	 */
	public BigDecimal getMinFreightPayFee() {
		return minFreightPayFee;
	}

	/**
	 * @param minFreightPayFee the minFreightPayFee to set
	 */
	public void setMinFreightPayFee(BigDecimal minFreightPayFee) {
		this.minFreightPayFee = minFreightPayFee;
	}

	/**
	 * @return the descption
	 */
	public String getDescption() {
		return descption;
	}

	/**
	 * @param descption the descption to set
	 */
	public void setDescption(String descption) {
		this.descption = descption;
	}

}