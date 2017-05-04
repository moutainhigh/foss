package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 展馆区域规划Entity实体
 * @author 187862-dujunhui
 * @date 2015-7-6 下午2:51:24
 */
public class ExhibitionAreaPlanEntity extends BaseEntity {
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = -5208993055226384101L;
	/**
     * 展馆编码
     */
    private String exhibitionCode;
    /**
     * 展馆名称
     */
    private String exhibitionName;
    /**
     * 管理部门编码
     */
    private String managementCode;
    /**
     * 管理部门名称
     */
    private String managementName;
    /**
     * 是否启用
     */
    private String active;
    /**
     * 版本号
     */
    private Long versionNo;
    /**
     * 备注
     */
    private String notes;
    /**
     * 虚拟编码
     */
    private String virtualCode;
    /**
     * GIS系统小区范围ID
     */
    private String gisid;
    /**
     * GIS系统小区范围 面积
     */
    private String gisArea;
    /**
     * 所在省编码
     */
    private String provCode;
    /**
     * 所在市编码
     */
    private String cityCode;
    /**
     * 所在区县
     */
    private String countyCode;
    /**
     * 所在省名称（扩展）
     */
    private String provName;
    /**
     * 所在市名称（扩展）
     */
    private String cityName;
    /**
     * 所在区县名称（扩展）
     */
    private String countyName;
    /**
     * 前台操作类型(ADD、UPDATE、VIEW)
     * @return
     */
    private String viewState;
    
	public String getExhibitionCode() {
		return exhibitionCode;
	}

	public void setExhibitionCode(String exhibitionCode) {
		this.exhibitionCode = exhibitionCode;
	}

	public String getExhibitionName() {
		return exhibitionName;
	}

	public void setExhibitionName(String exhibitionName) {
		this.exhibitionName = exhibitionName;
	}

	public String getManagementCode() {
		return managementCode;
	}

	public void setManagementCode(String managementCode) {
		this.managementCode = managementCode;
	}

	public String getManagementName() {
		return managementName;
	}

	public void setManagementName(String managementName) {
		this.managementName = managementName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getVirtualCode() {
		return virtualCode;
	}

	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}
	
	public String getGisid() {
		return gisid;
	}

	public void setGisid(String gisid) {
		this.gisid = gisid;
	}

	public String getGisArea() {
		return gisArea;
	}

	public void setGisArea(String gisArea) {
		this.gisArea = gisArea;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getViewState() {
		return viewState;
	}

	public void setViewState(String viewState) {
		this.viewState = viewState;
	}
	
}
