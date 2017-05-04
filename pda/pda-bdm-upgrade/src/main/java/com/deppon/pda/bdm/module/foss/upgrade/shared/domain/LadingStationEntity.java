package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;



/**
 * 提货站点实体
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-09-23
 */
public class LadingStationEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 网点名称
	 */
	private String name;
	/**
	 * 网点编号
	 */
	private String deptCode;
	/**
	 * 目的站
	 */
	private String destination;
	/**
	 * 网点类型 
	 */
	private String ladingSort;
	/**
	 * 外发代理
	 */
	private String agentOuterCode;
	/**
	 * 目的地编码
	 */
	private String stationNumber;
	/**
	 * 最终外场编码
	 */
	private String finalOutField;
	/**
	 *创建时间
	 */
	private String createTime;
	/**
	 * 最后更新时间
	 */
	private String lastUpdateTime;
	/**
	 * 提货区域
	 */
	private String pickArea;
	/**
	 * 派送区域
	 */
	private String  deliverAre;
	/**
	 * 是否生效
	 */
	private String isActive;
	/**
	 * 操作标记
	 */
	private String operFlag;
	/**
	 * 上一次更新时间
	 */
	private String updTime;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 生效时间
	 */
	private String activeTime;
	
	/**
	 * 是否自提
	 */
	private String isPickup;
	
	/**
	 * 是否派送
	 */
	private String isDelivery;
	
	/**
	 * 是否可货到付款
	 */
	private String canCashOnDelivery;
	
	/**
	 * 是否可代收货款
	 */
	private String canAgentCollected;
	
	/**
	 * 部门电话
	 */
	private String deptTelephone;
	
	/**
	 * 是否快递虚拟营业部
	 */
	private String salesDepartment;
	/**
	 * 可快递返回签单
	 */
	private String signBill;
	/**
	 * 可快递接货
	 */
	private String pickupToDoor;;
	/**
	 * 可快递派送
	 */
	private String delivery;
	/**
	 * 可快递自提
	 */
	private String pickupself;
	/**
	 * 快递派送区域描述
	 */
	private String deliverAreadesc;
	/**
	 * 快递自提区域描述
	 */
	private String pickupAreadesc;
	
	public String getSalesDepartment() {
		return salesDepartment;
	}
	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}
	public String getSignBill() {
		return signBill;
	}
	public void setSignBill(String signBill) {
		this.signBill = signBill;
	}
	public String getPickupToDoor() {
		return pickupToDoor;
	}
	public void setPickupToDoor(String pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public String getPickupself() {
		return pickupself;
	}
	public void setPickupself(String pickupself) {
		this.pickupself = pickupself;
	}
	public String getDeliverAreadesc() {
		return deliverAreadesc;
	}
	public void setDeliverAreadesc(String deliverAreadesc) {
		this.deliverAreadesc = deliverAreadesc;
	}
	public String getPickupAreadesc() {
		return pickupAreadesc;
	}
	public void setPickupAreadesc(String pickupAreadesc) {
		this.pickupAreadesc = pickupAreadesc;
	}
	public String getCanCashOnDelivery() {
		return canCashOnDelivery;
	}
	public void setCanCashOnDelivery(String canCashOnDelivery) {
		this.canCashOnDelivery = canCashOnDelivery;
	}
	public String getCanAgentCollected() {
		return canAgentCollected;
	}
	public void setCanAgentCollected(String canAgentCollected) {
		this.canAgentCollected = canAgentCollected;
	}
	public String getDeptTelephone() {
		return deptTelephone;
	}
	public void setDeptTelephone(String deptTelephone) {
		this.deptTelephone = deptTelephone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getLadingSort() {
		return ladingSort;
	}
	public void setLadingSort(String ladingSort) {
		this.ladingSort = ladingSort;
	}
	public String getAgentOuterCode() {
		return agentOuterCode;
	}
	public void setAgentOuterCode(String agentOuterCode) {
		this.agentOuterCode = agentOuterCode;
	}
	public String getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}
	public String getFinalOutField() {
		return finalOutField;
	}
	public void setFinalOutField(String finalOutField) {
		this.finalOutField = finalOutField;
	}
	
	public String getPickArea() {
		return pickArea;
	}
	public void setPickArea(String pickArea) {
		this.pickArea = pickArea;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDeliverAre() {
		return deliverAre;
	}
	public void setDeliverAre(String deliverAre) {
		this.deliverAre = deliverAre;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getIsPickup() {
		return isPickup;
	}
	public void setIsPickup(String isPickup) {
		this.isPickup = isPickup;
	}
	public String getIsDelivery() {
		return isDelivery;
	}
	public void setIsDelivery(String isDelivery) {
		this.isDelivery = isDelivery;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	
	
	
	
	
	
}
