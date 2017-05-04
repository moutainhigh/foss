package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
/**
 * 重分类收入基础信息
 * @author 307196
 *
 */
public class ClassifiedIncomeQueryDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6878655640828967244L;
	 /**
     * 产品类型名称
     */
    private String productTypeName;
    /**
     * 产品类型编码
     */
    private String productTypeCode;
    /**
     * 所属子公司名称
     */
    private String owendSubsidiaryName;
    /**
     * 所属子公司编码
     */
    private String owendSubsidiaryCode;
    /**
     * 是否有效
     */
    private String active;
    /**
     * 是否有效
     */
    private String sixPercent;
    /**
     * 是否有效
     */
    private String elevenPercent;
	/**
	 * 业务时间
	 */
	private Date businessTime;
	/**
	 * 选择ID集合List
	 */
	private List<String> selectedIdList;
	/**
	 * 已选ID集合
	 */
	private String selectedIds;
	
	
	public String getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	public List<String> getSelectedIdList() {
		return selectedIdList;
	}
	public void setSelectedIdList(List<String> selectedIdList) {
		this.selectedIdList = selectedIdList;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public String getOwendSubsidiaryName() {
		return owendSubsidiaryName;
	}
	public void setOwendSubsidiaryName(String owendSubsidiaryName) {
		this.owendSubsidiaryName = owendSubsidiaryName;
	}
	public String getOwendSubsidiaryCode() {
		return owendSubsidiaryCode;
	}
	public void setOwendSubsidiaryCode(String owendSubsidiaryCode) {
		this.owendSubsidiaryCode = owendSubsidiaryCode;
	}
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}
	public String getSixPercent() {
		return sixPercent;
	}
	public void setSixPercent(String sixPercent) {
		this.sixPercent = sixPercent;
	}
	public String getElevenPercent() {
		return elevenPercent;
	}
	public void setElevenPercent(String elevenPercent) {
		this.elevenPercent = elevenPercent;
	}
	
	
}
