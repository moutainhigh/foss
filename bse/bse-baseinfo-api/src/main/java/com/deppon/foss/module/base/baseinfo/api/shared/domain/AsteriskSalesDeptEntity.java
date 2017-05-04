package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 加星标营业部信息
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-5-4 上午8:53:12
 * @since
 * @version
 */
public class AsteriskSalesDeptEntity extends BaseEntity{

	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 4915535940776129400L;
	
	/**
     * 虚拟编码
     */
    private String virtualCode;
    
    /**
     * 营业部部门编码
     */
    private String salesDeptCode;
    
    /**
     * 营业部部门名称
     */
    private String salesDeptName;
    
    /**
     * 星标编码(对应数据字典)
     */
    private String asteriskCode;
    
    /**
     * 是否有效
     */
    private String active;
    
    /**
     * 备注
     */
    private String notes;
     
    /**
     * 版本号
     */
    private Long versionNo;
    
    /**
     * 获取虚拟编码
     * @return
     */
	public String getVirtualCode() {
		return virtualCode;
	}
	
	/**
     * 设置虚拟编码
     * @return
     */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}
	
	/**
     * 获取营业部部门编码
     * @return
     */
	public String getSalesDeptCode() {
		return salesDeptCode;
	}
	
	/**
     * 设置营业部部门编码
     * @return
     */
	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
	}
	
	/**
     * 获取营业部部门名称
     * @return
     */
	public String getSalesDeptName() {
		return salesDeptName;
	}
	
	/**
     * 设置营业部部门名称
     * @return
     */
	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}
	
	/**
     * 获取星标编码
     * @return
     */
	public String getAsteriskCode() {
		return asteriskCode;
	}
	
	/**
     * 设置星标编码
     * @return
     */
	public void setAsteriskCode(String asteriskCode) {
		this.asteriskCode = asteriskCode;
	}
	
	/**
     * 获取 是否有效 Y：有效 N:无效
     * @return
     */
	public String getActive() {
		return active;
	}
	
	/**
     * 设置 是否有效 Y：有效 N:无效
     * @return
     */
	public void setActive(String active) {
		this.active = active;
	}
	
	/**
     * 获取备注
     * @return
     */
	public String getNotes() {
		return notes;
	}
	
	/**
     * 设置备注
     * @return
     */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
     * 获取版本号
     * @return
     */
	public Long getVersionNo() {
		return versionNo;
	}
	
	/**
     * 获取版本号
     * @return
     */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	
}
