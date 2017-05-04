package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 外场与所属运输财务公司关系信息
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-11-25 下午5:40:12
 * @since
 * @version
 */

public class OutfieldTFCompanyEntity extends BaseEntity{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 是否有效 Y：有效 N:无效.
     */
    private String active;
    
    /**
     * 外场名称
     */
    private String outfieldName;
    
    /**
     * 外场编码
     */
    private String outfieldCode;
    
    /**
     * 外场所属运输财务公司编码
     */
    private String companyCode;
    
    /**
     * 外场所属运输财务公司名称
     */
    private String companyName;
    
    /**
     * 备注
     */
    private String notes;
    
    /**
     * 获取备注
     * @return
     */
    public String getNotes() {
		return notes;
	}
    
    /**
     * 设置备注
     * @param notes
     */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
     * 获取状态信息
     */
    public String getActive() {
		return active;
	}
    
    /**
     * 设置状态信息
     */
	public void setActive(String active) {
		this.active = active;
	}
	
	/**
     * 获取外场名称
     */
	public String getOutfieldName() {
		return outfieldName;
	}
	
	/**
     * 设置外场名称
     */
	public void setOutfieldName(String outfieldName) {
		this.outfieldName = outfieldName;
	}
	
	/**
     * 获取外场编码
     */
	public String getOutfieldCode() {
		return outfieldCode;
	}
	
	/**
     * 设置外场编码
     */
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}
	
	/**
     * 获取外场所属运输公司编码
     */
	public String getCompanyCode() {
		return companyCode;
	}
	
	/**
     * 设置外场所属运输公司编码
     */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
     * 获取外场所属运输公司名称
     */
	public String getCompanyName() {
		return companyName;
	}
	
	/**
     * 设置外场所属运输公司名称
     */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
