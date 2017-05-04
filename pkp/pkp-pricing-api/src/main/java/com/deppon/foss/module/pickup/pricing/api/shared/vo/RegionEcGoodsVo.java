/**
 * Project Name:pkp-pricing-api
 * File Name:RegionEcGoodsVo.java
 * Package Name:com.deppon.foss.module.pickup.pricing.api.shared.vo
 * Date:2016.06.29
 *
*/

package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ShowDeptEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName:RegionEcGoodsVo <br/>
 * Function: 精准电商区域VO. <br/>
 * Date:     2616.06.29<br/>
 * @author   311417 wangfeng
 * @version
 * @since    JDK 1.6
 * @see
 */
public class RegionEcGoodsVo implements Serializable{

	private static final long serialVersionUID = 8513258730913662707L;
	/**
	 *  返回区域信息结果集
	 */
	private List<PriceRegionEcGoodsEntity> regionEntityList;

	/**
	 *  部门信息
	 */
	private List<ShowDeptEntity> showDeptEntityList;

	/**
	 *  区域关联部门信息(查询)
	 */
	private List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntityList;
	/**
	 * 返回的行政趋于结果集
	 */
	private List<AdministrativeRegionsEntity> administrativeRegionsEntityList;
	/**
	 * 查询区域关联部门条件
	 */
	private PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity;
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
	private List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList;
	/**
	 * 区域关联部门信息(修改)
	 */
	private List<PriceRegioOrgnEcGoodsEntity> updatePriceRegioOrgnEntityList;
	/**
	 * 查询行政区域的父节点
	 */
	private String tparentRegionCode;
	/**
	 * 查询条件
	 */
	private PriceRegionEcGoodsEntity regionEntity;
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

	/**
	 * 获取 起始时间.
	 *
	 * @return the 起始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置 起始时间.
	 *
	 * @param beginTime the new 起始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}



	/**
	 * 获取 部门信息.
	 *
	 * @return the 部门信息
	 */
	public List<ShowDeptEntity> getShowDeptEntityList() {
		return showDeptEntityList;
	}

	/**
	 * 设置 部门信息.
	 *
	 * @param showDeptEntityList the new 部门信息
	 */
	public void setShowDeptEntityList(List<ShowDeptEntity> showDeptEntityList) {
		this.showDeptEntityList = showDeptEntityList;
	}


	/**
	 * 获取 返回的行政趋于结果集.
	 *
	 * @return the 返回的行政趋于结果集
	 */
	public List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityList() {
		return administrativeRegionsEntityList;
	}

	/**
	 * 设置 返回的行政趋于结果集.
	 *
	 * @param administrativeRegionsEntityList the new 返回的行政趋于结果集
	 */
	public void setAdministrativeRegionsEntityList(
			List<AdministrativeRegionsEntity> administrativeRegionsEntityList) {
		this.administrativeRegionsEntityList = administrativeRegionsEntityList;
	}

	/**
	 * 获取 激活区域.
	 *
	 * @return the 激活区域
	 */
	public List<String> getRegionIds() {
		return regionIds;
	}

	/**
	 * 设置 激活区域.
	 *
	 * @param regionIds the new 激活区域
	 */
	public void setRegionIds(List<String> regionIds) {
		this.regionIds = regionIds;
	}

	/**
	 * 获取 查询部门条件(区县CODE).
	 *
	 * @return the 查询部门条件(区县CODE)
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * 设置 查询部门条件(区县CODE).
	 *
	 * @param countyCode the new 查询部门条件(区县CODE)
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * 获取 排除的部门ID.
	 *
	 * @return the 排除的部门ID
	 */
	public List<String> getDeptIds() {
		return deptIds;
	}

	/**
	 * 设置 排除的部门ID.
	 *
	 * @param deptIds the new 排除的部门ID
	 */
	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

	/**
	 * 获取 查询行政区域的父节点.
	 *
	 * @return the 查询行政区域的父节点
	 */
	public String getTparentRegionCode() {
		return tparentRegionCode;
	}

	/**
	 * 设置 查询行政区域的父节点.
	 *
	 * @param tparentRegionCode the new 查询行政区域的父节点
	 */
	public void setTparentRegionCode(String tparentRegionCode) {
		this.tparentRegionCode = tparentRegionCode;
	}

	/**
	 * 获取 区域性质.
	 *
	 * @return the 区域性质
	 */
	public String getRegionNature() {
		return regionNature;
	}
	/**
	 * 设置 区域性质.
	 *
	 * @param regionNature the new 区域性质
	 */
	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}
	/**
	 * 获取 结束时间.
	 *
	 * @return the 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置 结束时间.
	 *
	 * @param endTime the new 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<PriceRegionEcGoodsEntity> getRegionEntityList() {
		return regionEntityList;
	}

	public void setRegionEntityList(List<PriceRegionEcGoodsEntity> regionEntityList) {
		this.regionEntityList = regionEntityList;
	}

	public PriceRegionEcGoodsEntity getRegionEntity() {
		return regionEntity;
	}

	public void setRegionEntity(PriceRegionEcGoodsEntity regionEntity) {
		this.regionEntity = regionEntity;
	}

	public List<PriceRegioOrgnEcGoodsEntity> getPriceRegioOrgnEntityList() {
		return priceRegioOrgnEntityList;
	}

	public void setPriceRegioOrgnEntityList(
			List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntityList) {
		this.priceRegioOrgnEntityList = priceRegioOrgnEntityList;
	}

	public PriceRegioOrgnEcGoodsEntity getPriceRegioOrgnEntity() {
		return priceRegioOrgnEntity;
	}

	public void setPriceRegioOrgnEntity(
			PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity) {
		this.priceRegioOrgnEntity = priceRegioOrgnEntity;
	}

	public List<PriceRegioOrgnEcGoodsEntity> getAddPriceRegioOrgnEntityList() {
		return addPriceRegioOrgnEntityList;
	}

	public void setAddPriceRegioOrgnEntityList(
			List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList) {
		this.addPriceRegioOrgnEntityList = addPriceRegioOrgnEntityList;
	}

	public List<PriceRegioOrgnEcGoodsEntity> getUpdatePriceRegioOrgnEntityList() {
		return updatePriceRegioOrgnEntityList;
	}

	public void setUpdatePriceRegioOrgnEntityList(
			List<PriceRegioOrgnEcGoodsEntity> updatePriceRegioOrgnEntityList) {
		this.updatePriceRegioOrgnEntityList = updatePriceRegioOrgnEntityList;
	}

}

