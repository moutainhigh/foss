/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ShowDeptEntity;

/**
 * (区域VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-10-13 上午10:13:10
 * </p>
 * 
 * @author dp-张斌
 * @date 2012-10-13 上午10:13:10
 * @since
 * @version
 */
public class RegionValueAddVo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -679785621104894678L;
	/** 
	 * 返回区域信息结果集
	 */
	private List<PriceRegionValueAddEntity> regionEntityList;
	/** 
	 * 部门信息
	 */
	private List<ShowDeptEntity> showDeptEntityList;
	/** 
	 * 区域关联部门信息(查询)
	 */
	private List<PriceRegionOrgValueAddEntity> priceRegionOrgValueAddEntityList;
	/** 
	 * 返回的行政趋于结果集
	 */
	private List<AdministrativeRegionsEntity> administrativeRegionsEntityList;
	/** 
	 * 查询区域关联部门条件
	 */
	private PriceRegionOrgValueAddEntity priceRegionOrgValueAddEntityEntity;
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
	private List<PriceRegionOrgValueAddEntity> addPriceRegionOrgValueAddEntityList;
	/** 
	 * 区域关联部门信息(修改)
	 */
	private List<PriceRegionOrgValueAddEntity> updatePriceRegionOrgValueAddEntityList;
	/** 
	 * 查询行政区域的父节点
	 */
	private String tparentRegionCode;
	/** 
	 * 查询条件
	 */
	private PriceRegionValueAddEntity regionEntity;
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
	public List<PriceRegionValueAddEntity> getRegionEntityList() {
		return regionEntityList;
	}
	public void setRegionEntityList(List<PriceRegionValueAddEntity> regionEntityList) {
		this.regionEntityList = regionEntityList;
	}
	public List<ShowDeptEntity> getShowDeptEntityList() {
		return showDeptEntityList;
	}
	public void setShowDeptEntityList(List<ShowDeptEntity> showDeptEntityList) {
		this.showDeptEntityList = showDeptEntityList;
	}
	public List<PriceRegionOrgValueAddEntity> getPriceRegionOrgValueAddEntityList() {
		return priceRegionOrgValueAddEntityList;
	}
	public void setPriceRegionOrgValueAddEntityList(
			List<PriceRegionOrgValueAddEntity> priceRegionOrgValueAddEntityList) {
		this.priceRegionOrgValueAddEntityList = priceRegionOrgValueAddEntityList;
	}
	public List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityList() {
		return administrativeRegionsEntityList;
	}
	public void setAdministrativeRegionsEntityList(
			List<AdministrativeRegionsEntity> administrativeRegionsEntityList) {
		this.administrativeRegionsEntityList = administrativeRegionsEntityList;
	}
	public PriceRegionOrgValueAddEntity getPriceRegionOrgValueAddEntityEntity() {
		return priceRegionOrgValueAddEntityEntity;
	}
	public void setPriceRegionOrgValueAddEntityEntity(
			PriceRegionOrgValueAddEntity priceRegionOrgValueAddEntityEntity) {
		this.priceRegionOrgValueAddEntityEntity = priceRegionOrgValueAddEntityEntity;
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
	public List<PriceRegionOrgValueAddEntity> getAddPriceRegionOrgValueAddEntityList() {
		return addPriceRegionOrgValueAddEntityList;
	}
	public void setAddPriceRegionOrgValueAddEntityList(
			List<PriceRegionOrgValueAddEntity> addPriceRegionOrgValueAddEntityList) {
		this.addPriceRegionOrgValueAddEntityList = addPriceRegionOrgValueAddEntityList;
	}
	public List<PriceRegionOrgValueAddEntity> getUpdatePriceRegionOrgValueAddEntityList() {
		return updatePriceRegionOrgValueAddEntityList;
	}
	public void setUpdatePriceRegionOrgValueAddEntityList(
			List<PriceRegionOrgValueAddEntity> updatePriceRegionOrgValueAddEntityList) {
		this.updatePriceRegionOrgValueAddEntityList = updatePriceRegionOrgValueAddEntityList;
	}
	public String getTparentRegionCode() {
		return tparentRegionCode;
	}
	public void setTparentRegionCode(String tparentRegionCode) {
		this.tparentRegionCode = tparentRegionCode;
	}
	public PriceRegionValueAddEntity getRegionEntity() {
		return regionEntity;
	}
	public void setRegionEntity(PriceRegionValueAddEntity regionEntity) {
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