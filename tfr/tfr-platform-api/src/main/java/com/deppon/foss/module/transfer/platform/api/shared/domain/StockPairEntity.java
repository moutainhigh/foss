package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 库存副表的实体类
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:30:02
*/
public class StockPairEntity extends BaseEntity{

	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:30:26
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	/**
	* 主键ID
	*/
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNO;
	/**
	 * 流水号
	 */
	private String serialNO;
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 货区名称
	 */
	private String goodsAreaName;
	/**
	 * 操作人编号
	 */
	private String operatorCode;
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	/**
	 * 出入库类型
	 */
	private String inOutStockType;
	/**
	 * 出入库单据号
	 */
	private String inOutStockBillNO;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 出入库时间
	 */
	private Date inOutStockTime;
	
	
	/**
	* @fields preHandOverState
	* @author 14022-foss-songjie
	* @update 2014年3月10日 上午8:36:35
	* @version V1.0
	*/
	private String preHandOverState;
	
	
	/**
	 * 入库时间
	* @fields inStockTime
	* @author 14022-foss-songjie
	* @update 2014年3月10日 上午8:34:47
	* @version V1.0
	*/
	private Date inStockTime;
	/**
	 * PDA扫描时间
	 */
	private Date scanTime;
	/**
	 * 部门编号
	 */
	private String orgCode;
	/**
	 * 动作是否有效
	 */
	private String isValid;
	/**
	 * PDA设备号
	 */
	private String pdaDeviceNO;
	
	/**
	 * 计划出发时间
	 */
	private Date planStartTime;
	/** 下一部门编号*/
	private String nextOrgCode;
	/**
	* 库位
	*/
	private String position;
	
	
	/**
	 * isSendRate 派送率是否已统计
	* @fields 
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:32:56
	* @version V1.0
	*/
	private String isSendRate;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getWaybillNO() {
		return waybillNO;
	}


	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}


	public String getSerialNO() {
		return serialNO;
	}


	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}


	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}


	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}


	public String getGoodsAreaName() {
		return goodsAreaName;
	}


	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}


	public String getOperatorCode() {
		return operatorCode;
	}


	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}


	public String getOperatorName() {
		return operatorName;
	}


	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}


	public String getInOutStockType() {
		return inOutStockType;
	}


	public void setInOutStockType(String inOutStockType) {
		this.inOutStockType = inOutStockType;
	}


	public String getInOutStockBillNO() {
		return inOutStockBillNO;
	}


	public void setInOutStockBillNO(String inOutStockBillNO) {
		this.inOutStockBillNO = inOutStockBillNO;
	}


	public String getDeviceType() {
		return deviceType;
	}


	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}


	public Date getInOutStockTime() {
		return inOutStockTime;
	}


	public void setInOutStockTime(Date inOutStockTime) {
		this.inOutStockTime = inOutStockTime;
	}


	public Date getScanTime() {
		return scanTime;
	}


	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}


	public String getOrgCode() {
		return orgCode;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	public String getIsValid() {
		return isValid;
	}


	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}


	public String getPdaDeviceNO() {
		return pdaDeviceNO;
	}


	public void setPdaDeviceNO(String pdaDeviceNO) {
		this.pdaDeviceNO = pdaDeviceNO;
	}


	public Date getPlanStartTime() {
		return planStartTime;
	}


	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}


	public String getNextOrgCode() {
		return nextOrgCode;
	}


	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getIsSendRate() {
		return isSendRate;
	}


	public void setIsSendRate(String isSendRate) {
		this.isSendRate = isSendRate;
	}


	public Date getInStockTime() {
		return inStockTime;
	}


	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}


	public String getPreHandOverState() {
		return preHandOverState;
	}


	public void setPreHandOverState(String preHandOverState) {
		this.preHandOverState = preHandOverState;
	}
	
	
	

}
