package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
* @ClassName: SendDistrictMapPiceAreaEntity 
* @Description:派送货派送货区行政区域映射基础资料   
*     件区Entity 
* @author 189284 ZhangXu
* @date 2015-3-17 上午9:52:14 
*
 */
public class SendDistrictItemAreaEntity extends BaseEntity {


	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	private String  scopeStart;  // 范围（起点）
	private String  scopeEnd;  // 范围（终点）
	private String  length;  // 件区长度（米）
	private String  width;  // 件区宽度（米）
	private String  height;  // 件区高度（米）
	private String  abscissa;  // 横坐标
	private String  ordinate;  // 纵坐标
	private String  volume;  // 件区体积
	private String  itemAreaName;  // 件区名称
	private String id;
	private String active;
	private String zoneCode;//分区Code
	private String actionType;//对件区表格的操作类型 add新增 update修改 delete删除
	/** 
	 *获取 范围（起点）
	 * @return  scopeStart  
	 */
	public String getScopeStart() {
		return scopeStart;
	}
	/**
	 *设置 范围（起点）
	 *setScopeStart
	 * @param scopeStart the scopeStart to set
	 * @return the scopeStart
	 */
	public void setScopeStart(String scopeStart) {
		this.scopeStart = scopeStart;
	}
	/** 
	 *获取  范围（终点）
	 * @return  scopeEnd  
	 */
	public String getScopeEnd() {
		return scopeEnd;
	}
	/**
	 *设置 范围（终点）
	 *setScopeEnd
	 * @param scopeEnd the scopeEnd to set
	 * @return the scopeEnd
	 */
	public void setScopeEnd(String scopeEnd) {
		this.scopeEnd = scopeEnd;
	}
	/** 
	 *获取 
	 * @return  length  
	 */
	public String getLength() {
		return length;
	}
	/**
	 *设置
	 *setLength
	 * @param length the length to set
	 * @return the length
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/** 
	 *获取
	 * @return  width  
	 */
	public String getWidth() {
		return width;
	}
	/**
	 *设置
	 *setWidth
	 * @param width the width to set
	 * @return the width
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	/** 
	 *获取
	 * @return  height  
	 */
	public String getHeight() {
		return height;
	}
	/**
	 *设置
	 *setHeight
	 * @param height the height to set
	 * @return the height
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/** 
	 *获取
	 * @return  abscissa  
	 */
	public String getAbscissa() {
		return abscissa;
	}
	/**
	 *设置
	 *setAbscissa
	 * @param abscissa the abscissa to set
	 * @return the abscissa
	 */
	public void setAbscissa(String abscissa) {
		this.abscissa = abscissa;
	}
	/** 
	 *获取
	 * @return  ordinate  
	 */
	public String getOrdinate() {
		return ordinate;
	}
	/**
	 *设置
	 *setOrdinate
	 * @param ordinate the ordinate to set
	 * @return the ordinate
	 */
	public void setOrdinate(String ordinate) {
		this.ordinate = ordinate;
	}
	/** 
	 *获取
	 * @return  volume  
	 */
	public String getVolume() {
		return volume;
	}
	/**
	 *设置
	 *setVolume
	 * @param volume the volume to set
	 * @return the volume
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	/** 
	 *获取 件区Name
	 * @return  itemAreaName  
	 */
	public String getItemAreaName() {
		return itemAreaName;
	}
	/**
	 *设置 件区 Name
	 *setItemAreaName
	 * @param itemAreaName the itemAreaName to set
	 * @return the itemAreaName
	 */
	public void setItemAreaName(String itemAreaName) {
		this.itemAreaName = itemAreaName;
	}
	/** 
	 *获取
	 * @return  id  
	 */
	public String getId() {
		return id;
	}
	/**
	 *设置
	 *setId
	 * @param id the id to set
	 * @return the id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/** 
	 *获取是否有效
	 * @return  active  
	 */
	public String getActive() {
		return active;
	}
	/**
	 *设置 是否有效
	 *setActive
	 * @param active the active to set
	 * @return the active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/** 
	 *获取 分区Code
	 * @return  zoneCode  
	 */
	public String getZoneCode() {
		return zoneCode;
	}
	/**
	 *设置 分区Code
	 *setZoneCode
	 * @param zoneCode the zoneCode to set
	 * @return the zoneCode
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	/** 
	 *获取 对件区表格的操作类型 add新增 update修改 delete删除
	 * @return  actionType  
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 *设置 //对件区表格的操作类型 add新增 update修改 delete删除
	 *setActionType
	 * @param actionType the actionType to set
	 * @return the actionType
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	
	
}
