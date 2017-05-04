package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;

public class ToFossTaskDetailVo implements Serializable {

	private static final long serialVersionUID = -977232777224779228L;

	private String receiveOrgName; // 出发部门
	private String reachOrgName; // 到达部门
	private String waybillNo; // 运单号

	private String transportType; // 产品类型

	private Integer stock = 1; // 库存
	private Integer scanCount; // 已扫件数
	private Integer loadCount; // 装车件数
	private BigDecimal weight; // 重量
	private BigDecimal volume; // 体积
	private String goodsName; // 货物名称
	private String packing; // 包装
	private String beLoaded; // 是否装车
	private String scanState; // 扫描状态
	private String cargoNo;
	private String cargoType;
	private Date operationTime;
	
	
	
	
	public LoadWayBillDetailDto getLoadWayBillDetailDto() {
		LoadWayBillDetailDto dto = new LoadWayBillDetailDto();
		
		dto.setOrigOrgName(receiveOrgName);
		dto.setReachOrgName(reachOrgName);
		dto.setWaybillNo(cargoNo);
		dto.setTransportType(transportType);
		dto.setStockQty(stock);
		dto.setScanQty(scanCount);
		dto.setLoadQty(loadCount);
		dto.setStockWeight(weight);
		dto.setStockVolume(volume);
		dto.setGoodsName(goodsName);
		dto.setPack(packing);
		dto.setCargoNo(cargoNo);
		dto.setCargoType(cargoType);
		dto.setOptTime(operationTime);
		
		return dto;
		
	}
	

	
	
	/**
	 * 
	* @description 返回出发部门
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:39:28
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	/**
	 * 
	* @description 设置出发部门
	* @param receiveOrgName
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:39:38
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	/**
	 * 
	* @description 返回到达部门
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:39:46
	 */
	public String getReachOrgName() {
		return reachOrgName;
	}
	/**
	 * 
	* @description 设置到达部门
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:39:52
	 */
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	/**
	 * 
	* @description 返回运单号
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:40:01
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * 
	* @description 设置运单号
	* @param waybillNo
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:40:13
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * 
	* @description 返回产品类型
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:40:27
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * 
	* @description 设置产品类型
	* @param transportType
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:40:33
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/**
	 * 
	* @description 返回库存
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:40:51
	 */
	public Integer getStock() {
		return stock;
	}
	/**
	 * 
	* @description 设置库存
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:40:56
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	/**
	 * 
	* @description 返回已扫件数
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:41:08
	 */
	public Integer getScanCount() {
		if(scanCount == null) {
			scanCount = 0;
		}
		return scanCount;
	}
	/**
	 * 
	* @description 设置已扫件数
	* @param scanCount
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:41:16
	 */
	public void setScanCount(Integer scanCount) {
		this.scanCount = scanCount;
	}
	/**
	 * 
	* @description 返回装车件数
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:41:29
	 */
	public Integer getLoadCount() {
		if(loadCount == null) {
			loadCount = 0;
		}
		return loadCount;
	}
	/**
	 * 
	* @description 设置装车件数
	* @param loadCount
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:41:39
	 */
	public void setLoadCount(Integer loadCount) {
		this.loadCount = loadCount;
	}
	/**
	 * 
	* @description 返回重量
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:41:50
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * 
	* @description 设置重量
	* @param weight
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:41:56
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * 
	* @description 返回体积
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:42:02
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * 
	* @description 设置体积
	* @param volume
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:42:08
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**
	 * 
	* @description 返回货物名称
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:42:31
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 
	* @description 设置货物名称
	* @param goddsName
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:42:38
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 
	* @description 返回包装
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:42:51
	 */
	public String getPacking() {
		return packing;
	}
	/**
	 * 
	* @description 设置包装
	* @param packing
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:42:58
	 */
	public void setPacking(String packing) {
		this.packing = packing;
	}
	/**
	 * 
	* @description 返回是否装车
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:43:55
	 */
	public String getBeLoaded() {
		return beLoaded;
	}
	/**
	 * 
	* @description 设置是否装车
	* @param beLoaded
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:44:12
	 */
	public void setBeLoaded(String beLoaded) {
		this.beLoaded = beLoaded;
	}
	/**
	 * 
	* @description 返回扫描状态
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:44:19
	 */
	public String getScanState() {
		return scanState;
	}
	/**
	 * 
	* @description 设置扫描状态
	* @param scanState
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月20日 下午6:44:29
	 */
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}


	public String getCargoNo() {
		return cargoNo;
	}


	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}


	public String getCargoType() {
		return cargoType;
	}


	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}


	
	

}
