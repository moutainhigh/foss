package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 点单任务
 * @author 272681-foss-chenlei
 * @date 2012-12-23 
 */
public class OrderTaskEntity extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**ID**/
	private String id;
	//点单任务编号
	private String orderTaskNo;
	//车牌号
	private String vehicleNo;
	//点单人
	private String orderCode;
	
	private String orderName;
	//点单任务状态
	private String orderTaskState;
	//报告处理状态
	private String reportState;
	
	private String departCode;
	//出发部门名称
	private String departName;
	
	private String ArriveCode;
	
	//到达部门名称
	private String ArriveName;
	
	//总票数
	private Long totWaybillQty;
		
	//总件数
	private Long totGoodsQty;
		
	//总重量
	private BigDecimal totWeight;
		
	//总体积
	private BigDecimal totVolume;
	//点单任务开始时间
	private Date orderStartTime;
	//点单任务结束时间
	private Date orderEndTime;
	//创建部门
	private String createOrgCode;
	//创建部门name
	private String createOrgName;
	//创建时间
	private Date createTime;
	//创建人
	private String createUser;
	//修改时间
	private Date modifyTime;
	//修改人
	private String modifyUser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderTaskState() {
		return orderTaskState;
	}
	public void setOrderTaskState(String orderTaskState) {
		this.orderTaskState = orderTaskState;
	}
	public String getReportState() {
		return reportState;
	}
	public void setReportState(String reportState) {
		this.reportState = reportState;
	}
	public String getDepartCode() {
		return departCode;
	}
	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getArriveCode() {
		return ArriveCode;
	}
	public void setArriveCode(String arriveCode) {
		ArriveCode = arriveCode;
	}
	public String getArriveName() {
		return ArriveName;
	}
	public void setArriveName(String arriveName) {
		ArriveName = arriveName;
	}
	public Long getTotWaybillQty() {
		return totWaybillQty;
	}
	public void setTotWaybillQty(Long totWaybillQty) {
		this.totWaybillQty = totWaybillQty;
	}
	public Long getTotGoodsQty() {
		return totGoodsQty;
	}
	public void setTotGoodsQty(Long totGoodsQty) {
		this.totGoodsQty = totGoodsQty;
	}
	public BigDecimal getTotWeight() {
		return totWeight;
	}
	public void setTotWeight(BigDecimal totWeight) {
		this.totWeight = totWeight;
	}
	public BigDecimal getTotVolume() {
		return totVolume;
	}
	public void setTotVolume(BigDecimal totVolume) {
		this.totVolume = totVolume;
	}
	public Date getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(Date orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public Date getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
}
