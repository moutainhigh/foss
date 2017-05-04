package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PDFPriceTableEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartnerPriceTableEntity;

/**
 * 
 * TODO合伙人汽运价格表VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-352676-YUANHB,date:2016-9-27 下午6:17:15,content:TODO </p>
 * @author Foss-352676-YUANHB 
 * @date 2016-9-27 下午6:17:15
 * @since
 * @version
 */
public class PartnerPriceTableVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3067739939752633512L;

    /**
     * 查询部门.
     */
    private String startDeptCode;
    
    /**
     * 产品类型.
     */
    private String productType;
    
    /**
     * 有效时间.
     */
    private Date effectiveDate;
    
	/**
	 * 导出PDF结果.
	 */
	private List<PDFPriceTableEntity> pdfPriceTableEntityList;
	
	/**
	 * 界面查询结果.
	 */
	private List<PartnerPriceTableEntity> partnerPriceTableEntityList;

	/**
	 * [POP]界面查询条件-分段数
	 */
	private int sectionID;
	
	/**
	 * [POP]重量体积临界值转换后的分段范围信息
	 */
	private Map<String,String> sectionScopeMap;
	
	/**
	 * 获取 查询部门.
	 *
	 * @return the 查询部门
	 */
	public String getStartDeptCode() {
		return startDeptCode;
	}

	/**
	 * 设置 查询部门.
	 *
	 * @param startDeptCode the new 查询部门
	 */
	public void setStartDeptCode(String startDeptCode) {
		this.startDeptCode = startDeptCode;
	}

	/**
	 * 获取 产品类型.
	 *
	 * @return the 产品类型
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * 设置 产品类型.
	 *
	 * @param productType the new 产品类型
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * 获取 有效时间.
	 *
	 * @return the 有效时间
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * 设置 有效时间.
	 *
	 * @param effectiveDate the new 有效时间
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * 获取 导出PDF结果.
	 *
	 * @return the 导出PDF结果
	 */
	public List<PDFPriceTableEntity> getPdfPriceTableEntityList() {
		return pdfPriceTableEntityList;
	}

	/**
	 * 设置 导出PDF结果.
	 *
	 * @param pdfPriceTableEntityList the new 导出PDF结果
	 */
	public void setPdfPriceTableEntityList(
			List<PDFPriceTableEntity> pdfPriceTableEntityList) {
		this.pdfPriceTableEntityList = pdfPriceTableEntityList;
	}

	

	/**
	 * [POP]获取 界面查询条件-分段数
	 * @return  the sectionID
	 */
	public int getSectionID() {
		return sectionID;
	}

	/**
	 * [POP]设置 界面查询条件-分段数
	 * @param sectionID the sectionID to set
	 */
	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}

	public Map<String, String> getSectionScopeMap() {
		return sectionScopeMap;
	}

	public void setSectionScopeMap(Map<String, String> sectionScopeMap) {
		this.sectionScopeMap = sectionScopeMap;
	}

	public List<PartnerPriceTableEntity> getPartnerPriceTableEntityList() {
		return partnerPriceTableEntityList;
	}

	public void setPartnerPriceTableEntityList(
			List<PartnerPriceTableEntity> partnerPriceTableEntityList) {
		this.partnerPriceTableEntityList = partnerPriceTableEntityList;
	}



}