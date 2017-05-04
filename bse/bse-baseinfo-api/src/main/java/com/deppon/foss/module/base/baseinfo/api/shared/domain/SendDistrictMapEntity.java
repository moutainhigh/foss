package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
* @ClassName: SendDistrictMapEntity 
* @Description: 增加派送货区行政区域映射基础资料 
* @author 189284--张许 
* @date 2014-10-20 上午10:34:47 
*
 */
public class SendDistrictMapEntity extends BaseEntity  {
	
	/** 
	* 
	*/ 
	private static final long serialVersionUID = 484806584252989143L;
	
	/**
	 * 外场编码 CODE （对应表字段 t_BasTransferCenter.CODE）
	 */
	private String  transferCenterCode;
	/**
	 * 外场名称 （对应表字段 t_BasTransferCenter.NAME）
	 */
	private String  transferCenterName;
	/**
	 * 库区编码 CODE ：（对应关了表字段T_BAS_GOODS_AREA.GOODS_AREA_CODE）
	 */
	private String  goodsAreaCode;
	/**
	 * 库区名称 ：（对应关了表字段T_BAS_GOODS_AREA.GOODS_AREA_NAME）
	 */
	private String  goodsAreaName;
	/**
	 * 行政区域编码 （对应关了表字段 T_BAS_DISTRICT.Code）
	 */
	private String  districtCode;
	/**
	 * 行政区域名称 （对应关了表字段 T_BAS_DISTRICT.NAME）
	 */
	private String  districtName;
	/**
	 * 分区名称
	 */
	private String  zoneName;
	/**
	 * 分区编码CODE
	 */
	private String  zoneCode;
	/**
	 * 是否启用
	 */
	private String  active;
	/**
	 * 货区类型 goodsType send：派送区，personally:自提区
	 */
	private String goodsType;
	/**
	 * 件区list
	 */
	private List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys;

	/**  
	 * 获取外场编码 CODE （对应表字段 t_BasTransferCenter.CODE）  
	 * @return  transferCenterCode  
	 */
	public String getTransferCenterCode() {
		return transferCenterCode;
	}
	/**  
	 * 设置外场编码 CODE （对应表字段 t_BasTransferCenter.CODE）  
	 * @param  transferCenterCode  
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}
	/**  
	 * 获取外场名称 （对应表字段 t_BasTransferCenter.NAME）  
	 * @return  transferCenterName  
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}
	/**  
	 * 设置外场名称 （对应表字段 t_BasTransferCenter.NAME）  
	 * @param  transferCenterName  
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}
	/**  
	 * 获取  库区编码 CODE ：（对应关了表字段T_BAS_GOODS_AREA.GOODS_AREA_CODE）  
	 * @return  goodsAreaCode  
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	/**  
	 * 设置  库区编码 CODE ：（对应关了表字段T_BAS_GOODS_AREA.GOODS_AREA_CODE）  
	 * @param  goodsAreaCode  
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	/**  
	 * 获取  库区名称 ：（对应关了表字段T_BAS_GOODS_AREA.GOODS_AREA_NAME）  
	 * @return  goodsAreaName  
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	/**  
	 * 设置  库区名称 ：（对应关了表字段T_BAS_GOODS_AREA.GOODS_AREA_NAME）  
	 * @param  goodsAreaName  
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	/**  
	 * 获取  行政区域编码 （对应关了表字段 T_BAS_DISTRICT.Code）  
	 * @return  districtCode  
	 */
	public String getDistrictCode() {
		return districtCode;
	}
	/**  
	 * 设置  行政区域编码 （对应关了表字段 T_BAS_DISTRICT.Code）  
	 * @param  districtCode  
	 */
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	/**  
	 * 获取  行政区域名称 （对应关了表字段 T_BAS_DISTRICT.NAME  
	 * @return  districtName  
	 */
	public String getDistrictName() {
		return districtName;
	}
	/**  
	 * 设置  行政区域名称 （对应关了表字段 T_BAS_DISTRICT.NAME  
	 * @param  districtName  
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	/**  
	 * 获取 分区名称  
	 * @return zoneName   
	 */
	public String getZoneName() {
		return zoneName;
	}
	/**  
	 * 设置 分区名称  
	 * @param  zoneName  
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	/**  
	 * 获取  分区 Code  
	 * @return  zoneCode  
	 */
	public String getZoneCode() {
		return zoneCode;
	}
	/**  
	 * 设置 分区 Code  
	 * @param  zoneCode  
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	/**  
	 * 获取  是否启用
	 * @return active active  
	 */
	public String getActive() {
		return active;
	}
	/**  
	 * 设置是否启用  
	 * @param active active  
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/** 
	 *获取 货区类型 goodsType send：派送区，personally:自提区
	 * @return  goodsType  
	 */
	public String getGoodsType() {
		return goodsType;
	}
	/**
	 *设置
	 *货区类型 goodsType send：派送区，personally:自提区
	 * @return the goodsType
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	/** 
	 *获取 件区List
	 * @return  sendDistrictItemAreaEntitys  
	 */
	public List<SendDistrictItemAreaEntity> getSendDistrictItemAreaEntitys() {
		return sendDistrictItemAreaEntitys;
	}
	/**
	 *设置 件区List
	 *setSendDistrictItemAreaEntitys
	 * @param sendDistrictItemAreaEntitys the sendDistrictItemAreaEntitys to set
	 * @return the sendDistrictItemAreaEntitys
	 */
	public void setSendDistrictItemAreaEntitys(
			List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys) {
		this.sendDistrictItemAreaEntitys = sendDistrictItemAreaEntitys;
	}
	
}
