package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryBigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;

public class ExpressDeliverySmallZoneVo implements Serializable{
	/**
	 * 
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4827722654015180626L;

	/**
	 * 快递收派小区实体.
	 */
	private ExpressDeliverySmallZoneEntity expressDeliverySmallZoneEntity;
	
	/**
	 * 快递收派小区实体LIST.
	 */
	private List<ExpressDeliverySmallZoneEntity> smallZoneEntityList;
	
	/**
	 * 快递收派大区实体.
	 */
	private ExpressDeliveryBigZoneEntity expressDeliveryBigZoneEntity;
	
	/**
	 * 快递收派大区实体LIST.
	 */
	private List<ExpressDeliveryBigZoneEntity> bigZoneEntityList;
	
	/**
	 * 定人定区实体.
	 */
	private RegionVehicleEntity regionVehicleEntity;
	
	/**
	 * 定人定区实体LIST.
	 */
	private List<RegionVehicleEntity> regionVehicleEntityList;
	
	/**
	 * 定人定区实体.
	 */
	private RegionVehicleInfoDto regionVehicleDto;
	
	/**
	 * 定人定区实体LIST.
	 */
	private List<RegionVehicleInfoDto> regionVehicleDtoList;
	
	/**
	 * 封装树节点，由于大小区不是同一个类 所以没有泛型.
	 */
	private List treeList;
	
	/**
	 * [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 */
	private String codeStr;
	
	/**
	 * 返回前台的INT类型属性.
	 */
	private int returnInt;

	/**
	 * 快递收派小区实体LIST.
	 */
	private List<String> addSmallZoneList = new ArrayList<String>();
	
	/**
	 * 快递收派小区实体LIST.
	 */
	private List<String> delSmallZoneList = new ArrayList<String>();
	
	/**
	 * 部门编码集合.
	 */
	private List<String> deptCodeList;

	/**
	 * 下面是get,set方法.
	 *
	 * @return the 小区实体
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27  19:40:42
	 */
	
	private String regionCode;
	
	/**
	 * 查询参数
	 */
	private String queryParam;
	
	private String active;
 
	 

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public ExpressDeliverySmallZoneEntity getExpressDeliverySmallZoneEntity() {
		return expressDeliverySmallZoneEntity;
	}

	public void setExpressDeliverySmallZoneEntity(
			ExpressDeliverySmallZoneEntity expressDeliverySmallZoneEntity) {
		this.expressDeliverySmallZoneEntity = expressDeliverySmallZoneEntity;
	}

	 
 
	/**
	 * 获取 [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 *
	 * @return the [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
	 */
	public String getCodeStr() {
		return codeStr;
	}
	
	/**
	 * 设置 [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 *
	 * @param codeStr the new [查询大小区树时的节点id]实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
	 */
	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}
	
	/**
	 * 获取 返回前台的INT类型属性.
	 *
	 * @return the 返回前台的INT类型属性
	 */
	public int getReturnInt() {
		return returnInt;
	}
	
	/**
	 * 设置 返回前台的INT类型属性.
	 *
	 * @param returnInt the new 返回前台的INT类型属性
	 */
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}
	
	 
	
	/**
	 * 获取 定人定区实体.
	 *
	 * @return the 定人定区实体
	 */
	public RegionVehicleEntity getRegionVehicleEntity() {
		return regionVehicleEntity;
	}
	
	/**
	 * 设置 定人定区实体.
	 *
	 * @param regionVehicleEntity the new 定人定区实体
	 */
	public void setRegionVehicleEntity(RegionVehicleEntity regionVehicleEntity) {
		this.regionVehicleEntity = regionVehicleEntity;
	}
	
	/**
	 * 获取 定人定区实体LIST.
	 *
	 * @return the 定人定区实体LIST
	 */
	public List<RegionVehicleEntity> getRegionVehicleEntityList() {
		return regionVehicleEntityList;
	}
	
	/**
	 * 设置 定人定区实体LIST.
	 *
	 * @param regionVehicleEntityList the new 定人定区实体LIST
	 */
	public void setRegionVehicleEntityList(
			List<RegionVehicleEntity> regionVehicleEntityList) {
		this.regionVehicleEntityList = regionVehicleEntityList;
	}
	
	/**
	 * 获取 封装树节点，由于大小区不是同一个类 所以没有泛型.
	 *
	 * @return the 封装树节点，由于大小区不是同一个类 所以没有泛型
	 */
	public List getTreeList() {
		return treeList;
	}
	
	/**
	 * 设置 封装树节点，由于大小区不是同一个类 所以没有泛型.
	 *
	 * @param treeList the new 封装树节点，由于大小区不是同一个类 所以没有泛型
	 */
	public void setTreeList(List treeList) {
		this.treeList = treeList;
	}
	
	/**
	 * 获取 快递收派小区实体LIST.
	 *
	 * @return the addSmallZoneList
	 */
	public List<String> getAddSmallZoneList() {
		return addSmallZoneList;
	}
	
	/**
	 * 设置 快递收派小区实体LIST.
	 *
	 * @param addSmallZoneList the addSmallZoneList to set
	 */
	public void setAddSmallZoneList(List<String> addSmallZoneList) {
		this.addSmallZoneList = addSmallZoneList;
	}
	
	/**
	 * 获取 快递收派小区实体LIST.
	 *
	 * @return the delSmallZoneList
	 */
	public List<String> getDelSmallZoneList() {
		return delSmallZoneList;
	}
	
	/**
	 * 设置 快递收派小区实体LIST.
	 *
	 * @param delSmallZoneList the delSmallZoneList to set
	 */
	public void setDelSmallZoneList(List<String> delSmallZoneList) {
		this.delSmallZoneList = delSmallZoneList;
	}
	
	/**
	 * 获取 定人定区实体.
	 *
	 * @return  the regionVehicleDto
	 */
	public RegionVehicleInfoDto getRegionVehicleDto() {
	    return regionVehicleDto;
	}
	
	/**
	 * 设置 定人定区实体.
	 *
	 * @param regionVehicleDto the regionVehicleDto to set
	 */
	public void setRegionVehicleDto(RegionVehicleInfoDto regionVehicleDto) {
	    this.regionVehicleDto = regionVehicleDto;
	}
	
	/**
	 * 获取 定人定区实体LIST.
	 *
	 * @return  the regionVehicleDtoList
	 */
	public List<RegionVehicleInfoDto> getRegionVehicleDtoList() {
	    return regionVehicleDtoList;
	}
	
	/**
	 * 设置 定人定区实体LIST.
	 *
	 * @param regionVehicleDtoList the regionVehicleDtoList to set
	 */
	public void setRegionVehicleDtoList(
		List<RegionVehicleInfoDto> regionVehicleDtoList) {
	    this.regionVehicleDtoList = regionVehicleDtoList;
	}
	
	/**
	 * 获取 部门编码集合.
	 *
	 * @return  the deptCodeList
	 */
	public List<String> getDeptCodeList() {
	    return deptCodeList;
	}
	
	/**
	 * 设置 部门编码集合.
	 *
	 * @param deptCodeList the deptCodeList to set
	 */
	public void setDeptCodeList(List<String> deptCodeList) {
	    this.deptCodeList = deptCodeList;
	}

	public List<ExpressDeliverySmallZoneEntity> getSmallZoneEntityList() {
		return smallZoneEntityList;
	}

	public void setSmallZoneEntityList(
			List<ExpressDeliverySmallZoneEntity> smallZoneEntityList) {
		this.smallZoneEntityList = smallZoneEntityList;
	}

	public ExpressDeliveryBigZoneEntity getExpressDeliveryBigZoneEntity() {
		return expressDeliveryBigZoneEntity;
	}

	public void setExpressDeliveryBigZoneEntity(
			ExpressDeliveryBigZoneEntity expressDeliveryBigZoneEntity) {
		this.expressDeliveryBigZoneEntity = expressDeliveryBigZoneEntity;
	}

	public List<ExpressDeliveryBigZoneEntity> getBigZoneEntityList() {
		return bigZoneEntityList;
	}

	public void setBigZoneEntityList(
			List<ExpressDeliveryBigZoneEntity> bigZoneEntityList) {
		this.bigZoneEntityList = bigZoneEntityList;
	}
	
	
	
	
	
}
