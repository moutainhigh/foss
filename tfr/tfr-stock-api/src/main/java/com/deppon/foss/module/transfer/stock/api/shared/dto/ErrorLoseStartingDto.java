package com.deppon.foss.module.transfer.stock.api.shared.dto;

import java.util.Date;


/**
* @description 出发丢货上报的实体
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年1月4日 上午10:06:20
*/
public class ErrorLoseStartingDto implements java.io.Serializable {

	
	/**
	 * 出发丢货的实体类
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午8:25:48
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 运单号
	* @fields waybillNO
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:04:24
	* @version V1.0
	*/
	private String waybillNO;
	/**
	 * 库存件数
	* @fields stockGoodsCount
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:04:57
	* @version V1.0
	*/
	private Integer stockGoodsCount;
	/**
	 * 入库时间
	* @fields inStockTime
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:05:28
	* @version V1.0
	*/
	private Date inStockTime;
	
	
	/**
	 * 库存部门Code
	* @fields org_code
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午8:33:09
	* @version V1.0
	*/
	private String orgCode;
	
	
	/**
	 * 库存部门Name
	* @fields orgCodeName
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午8:34:59
	* @version V1.0
	*/
	private String orgName;
	/**
	 * 是否上门取货
	* @fields pickupToDoor
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午9:03:01
	* @version V1.0
	*/
	private String pickupToDoor;
	
	
	/**
	 * 是否集中接货
	* @fields pickupCentralized
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午9:27:59
	* @version V1.0
	*/
	private String pickupCentralized;
	
	
	/**
	 * 是否驻地
	* @fields station
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午9:29:00
	* @version V1.0
	*/
	private String station;
	
	
	/**
	 * 业务渠道
	* @fields channelBusiness
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午9:30:39
	* @version V1.0
	*/
	private String channelBusiness;


	
	/**
	* @description 运单号
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:04:39
	*/
	public String getWaybillNO() {
		return waybillNO;
	}


	
	/**
	* @description 运单号
	* @param waybillNO
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:04:46
	*/
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}


	
	/**
	* @description 库存件数
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:05:12
	*/
	public Integer getStockGoodsCount() {
		return stockGoodsCount;
	}


	
	/**
	* @description 库存件数
	* @param stockGoodsCount
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:05:14
	*/
	public void setStockGoodsCount(Integer stockGoodsCount) {
		this.stockGoodsCount = stockGoodsCount;
	}


	
	/**
	* @description 入库时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:05:45
	*/
	public Date getInStockTime() {
		return inStockTime;
	}


	
	/**
	* @description 入库时间
	* @param inStockTime
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月4日 上午10:05:48
	*/
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}


	public String getOrgCode() {
		return orgCode;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getPickupToDoor() {
		return pickupToDoor;
	}


	public void setPickupToDoor(String pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}


	public String getPickupCentralized() {
		return pickupCentralized;
	}


	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}


	public String getStation() {
		return station;
	}


	public void setStation(String station) {
		this.station = station;
	}


	public String getChannelBusiness() {
		return channelBusiness;
	}


	public void setChannelBusiness(String channelBusiness) {
		this.channelBusiness = channelBusiness;
	}
	
}
