/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class QueryPickupPointDto implements Serializable{

	// 以下两个参数 传值时候需要赋值
	// 提货方式
	private String pickUpType;

	// 运输性质 产品 区分查询自有网点 外部网点
	private String destNetType;

	// 产品编码
	private String transType;

	// 自有营业部类型
	private String salesType;

	// 以下两个参数实际情况只需要选择赋值一个
	// 1.目的站 开单界面如果根据目的站过滤提货网点 要给这个参数赋值
	private String orgSimpleName;

	// 2. 提货网点 开单界面如果根据提货网点名字过滤提货网点 要给这个参数赋值
	private String pickUpPoint;

	// ---------------------------------------------------
	// 以下变量 不需要赋值 ,传输QueryPickupPointDto 不需要赋值 ,在数据查询时候客户端判断使用
	// 定义区域表 T_BAS_DISTRICT degree
	private String cityDegree;

	// 定义区域表 是否启用
	private String cityActive;

	// 是否启用
	private String active;

	// 是否送货上门 派送
	private String pickUpDoor;

	// 是否自提
	private String pickUpSelf;
	
	// 开单营业部编码
	private String receiveOrgCode;
	
	//触发来源，区分是开单查网点，还是更改单中的返货查网点
	private String source;
	
	//当前时间
	private Date curDate;
	
	/**
     * 是否机场
     */
    private String isAirport;  

    /**
     * 是否可到达
     */
    private String arrive;
    
    /**
     * 是否可出发
     */	
    private String leave;
    /**
     * 提货网点
     */
    private String targetOrgCode;
    
    /**
     * 目的站对应的城市编码
     */
    private String targetCityCode;
    
    /**
     * 目的站对应的区县编码
     */
    private String targetCountyCode;
    
    /**
     * 是否快递虚拟营业部
     */
    private String expressSalesDepartment;
    
    /**
     * 派送区域对应营业部编码 
     */
    private List<String> salesDepartmentCodeList;
    
    /**
     * 是否可家装送装
     */
    private String isHome;
    
    
	public String getIsHome() {
		return isHome;
	}
	public void setIsHome(String isHome) {
		this.isHome = isHome;
	}
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public String getIsAirport() {
		return isAirport;
	}

	
	public void setIsAirport(String isAirport) {
		this.isAirport = isAirport;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDestNetType() {
		return destNetType;
	}

	public void setDestNetType(String destNetType) {
		this.destNetType = destNetType;
	}

	public String getPickUpPoint() {
		return pickUpPoint;
	}

	public void setPickUpPoint(String pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}

	public String getPickUpDoor() {
		return pickUpDoor;
	}

	public void setPickUpDoor(String pickUpDoor) {
		this.pickUpDoor = pickUpDoor;
	}

	public String getPickUpSelf() {
		return pickUpSelf;
	}

	public void setPickUpSelf(String pickUpSelf) {
		this.pickUpSelf = pickUpSelf;
	}

	public String getPickUpType() {
		return pickUpType;
	}

	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}

	public String getCityDegree() {
		return cityDegree;
	}

	public void setCityDegree(String cityDegree) {
		this.cityDegree = cityDegree;
	}

	public String getCityActive() {
		return cityActive;
	}

	public void setCityActive(String cityActive) {
		this.cityActive = cityActive;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	/**
	 * @return the orgSimpleName
	 */
	public String getOrgSimpleName() {
		return orgSimpleName;
	}

	/**
	 * @param orgSimpleName
	 *            the orgSimpleName to set
	 */
	public void setOrgSimpleName(String orgSimpleName) {
		this.orgSimpleName = orgSimpleName;
	}

	/**
	  * @return  the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode the receiveOrgCode to set
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	  * @return  the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}


	/**
	  * @return  the curDate
	 */
	public Date getCurDate() {
		return curDate;
	}


	/**
	 * @param curDate the curDate to set
	 */
	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}


	/**
	 * @return the arrive
	 */
	public String getArrive() {
		return arrive;
	}


	/**
	 * @param arrive the arrive to set
	 */
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	
	public String getLeave() {
		return leave;
	}
	
	public void setLeave(String leave) {
		this.leave = leave;
	}
	
	public String getTargetCityCode() {
		return targetCityCode;
	}
	
	public void setTargetCityCode(String targetCityCode) {
		this.targetCityCode = targetCityCode;
	}
	
	public String getTargetCountyCode() {
		return targetCountyCode;
	}
	
	public void setTargetCountyCode(String targetCountyCode) {
		this.targetCountyCode = targetCountyCode;
	}
	public String getExpressSalesDepartment() {
		return expressSalesDepartment;
	}
	public void setExpressSalesDepartment(String expressSalesDepartment) {
		this.expressSalesDepartment = expressSalesDepartment;
	}
	public List<String> getSalesDepartmentCodeList() {
		return salesDepartmentCodeList;
	}
	public void setSalesDepartmentCodeList(List<String> salesDepartmentCodeList) {
		this.salesDepartmentCodeList = salesDepartmentCodeList;
	}
}
