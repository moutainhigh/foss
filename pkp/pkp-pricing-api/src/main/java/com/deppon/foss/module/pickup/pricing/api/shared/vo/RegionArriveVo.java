/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ShowDeptEntity;

public class RegionArriveVo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -679785621104894678L;
	/** 
	 * 返回区域信息结果集
	 */
	private List<PriceRegionArriveEntity> regionEntityList;
	/** 
	 * 部门信息
	 */
	private List<ShowDeptEntity> showDeptEntityList;
	/** 
	 * 区域关联部门信息(查询)
	 */
	private List<PriceRegionOrgArriveEntity> priceRegionOrgArriveEntityList;
	/** 
	 * 返回的行政趋于结果集
	 */
	private List<AdministrativeRegionsEntity> administrativeRegionsEntityList;
	/** 
	 * 查询区域关联部门条件
	 */
	private PriceRegionOrgArriveEntity priceRegionOrgArriveEntity;
	/** 
	 * 激活区域
	 */
	private List<String> regionIds;
	/** 
	 * 查询部门条件(区县CODE)
	 */
	private String countyCode;
	/** 
	 * 排除的部门ID
	 */
	private List<String> deptIds;
	/** 
	 * 区域关联部门信息(新增)
	 */
	private List<PriceRegionOrgArriveEntity> addPriceRegionOrgArriveEntityList;
	/** 
	 * 区域关联部门信息(修改)
	 */
	private List<PriceRegionOrgArriveEntity> updatePriceRegionOrgArriveEntityList;
	/** 
	 * 查询行政区域的父节点
	 */
	private String tparentRegionCode;
	/** 
	 * 查询条件
	 */
	private PriceRegionArriveEntity regionEntity;
	/** 
	 * 区域性质
	 */
	private String regionNature;
	/**
	 * 起始时间
	 */
	private Date beginTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	public List<PriceRegionArriveEntity> getRegionEntityList() {
		return regionEntityList;
	}
	public void setRegionEntityList(List<PriceRegionArriveEntity> regionEntityList) {
		this.regionEntityList = regionEntityList;
	}
	public List<ShowDeptEntity> getShowDeptEntityList() {
		return showDeptEntityList;
	}
	public void setShowDeptEntityList(List<ShowDeptEntity> showDeptEntityList) {
		this.showDeptEntityList = showDeptEntityList;
	}
	public List<PriceRegionOrgArriveEntity> getPriceRegionOrgArriveEntityList() {
		return priceRegionOrgArriveEntityList;
	}
	public void setPriceRegionOrgArriveEntityList(
			List<PriceRegionOrgArriveEntity> priceRegionOrgArriveEntityList) {
		this.priceRegionOrgArriveEntityList = priceRegionOrgArriveEntityList;
	}
	public List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityList() {
		return administrativeRegionsEntityList;
	}
	public void setAdministrativeRegionsEntityList(
			List<AdministrativeRegionsEntity> administrativeRegionsEntityList) {
		this.administrativeRegionsEntityList = administrativeRegionsEntityList;
	}
	public PriceRegionOrgArriveEntity getPriceRegionOrgArriveEntity() {
		return priceRegionOrgArriveEntity;
	}
	public void setPriceRegionOrgArriveEntity(
			PriceRegionOrgArriveEntity priceRegionOrgArriveEntity) {
		this.priceRegionOrgArriveEntity = priceRegionOrgArriveEntity;
	}
	public List<String> getRegionIds() {
		return regionIds;
	}
	public void setRegionIds(List<String> regionIds) {
		this.regionIds = regionIds;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public List<String> getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}
	public List<PriceRegionOrgArriveEntity> getAddPriceRegionOrgArriveEntityList() {
		return addPriceRegionOrgArriveEntityList;
	}
	public void setAddPriceRegionOrgArriveEntityList(
			List<PriceRegionOrgArriveEntity> addPriceRegionOrgArriveEntityList) {
		this.addPriceRegionOrgArriveEntityList = addPriceRegionOrgArriveEntityList;
	}
	public List<PriceRegionOrgArriveEntity> getUpdatePriceRegionOrgArriveEntityList() {
		return updatePriceRegionOrgArriveEntityList;
	}
	public void setUpdatePriceRegionOrgArriveEntityList(
			List<PriceRegionOrgArriveEntity> updatePriceRegionOrgArriveEntityList) {
		this.updatePriceRegionOrgArriveEntityList = updatePriceRegionOrgArriveEntityList;
	}
	public String getTparentRegionCode() {
		return tparentRegionCode;
	}
	public void setTparentRegionCode(String tparentRegionCode) {
		this.tparentRegionCode = tparentRegionCode;
	}
	public PriceRegionArriveEntity getRegionEntity() {
		return regionEntity;
	}
	public void setRegionEntity(PriceRegionArriveEntity regionEntity) {
		this.regionEntity = regionEntity;
	}
	public String getRegionNature() {
		return regionNature;
	}
	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}