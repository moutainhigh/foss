package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @派送信息查询条件DTO
 * @author zyr
 * @2015-05-21
 */
public class SendInfoSearchDto implements Serializable {
	private static final long serialVersionUID = 9210832937462854328L;
	
	//车牌号
	private String vehicleNo;
	//交接单号
	private String handoverNo;
	//车次号
	private String vehicleassembleNo;
	//出发部门
	private String departDeptCode;
	//预计到达时间(起)
	private Date preArriveTimeBegin;
	//预计到达时间(止)
	private Date preArriveTimeEnd;
	//到达时间(起)
	private Date arriveTimeBegin;
	//到达时间(止)
	private Date arriveTimeEnd;
	//出发时间(起)
	private Date leaveTimeBegin;
	//出发时间(止)
	private Date leaveTimeEnd;
	//入库时间(起)
	private Date inStockTimeBegin;
	//入库时间(止)
	private Date inStockTimeEnd;
	// 当前登录部门
	private String departmentCode;
	//对应外场
	private String transferCenterCode;
	//清单类型
	private String billType;
	//派送（总票数）
	private Integer deliverQtyTotal;
	//派送（总重量）
	private BigDecimal deliverWeightTotal;
	//派送（总重量）
	private BigDecimal deliverVolumeTotal;
	
	
	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}

	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public Integer getDeliverQtyTotal() {
		return deliverQtyTotal;
	}

	public void setDeliverQtyTotal(Integer deliverQtyTotal) {
		this.deliverQtyTotal = deliverQtyTotal;
	}

	public BigDecimal getDeliverWeightTotal() {
		return deliverWeightTotal;
	}

	public void setDeliverWeightTotal(BigDecimal deliverWeightTotal) {
		this.deliverWeightTotal = deliverWeightTotal;
	}

	public BigDecimal getDeliverVolumeTotal() {
		return deliverVolumeTotal;
	}

	public void setDeliverVolumeTotal(BigDecimal deliverVolumeTotal) {
		this.deliverVolumeTotal = deliverVolumeTotal;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getDepartDeptCode() {
		return departDeptCode;
	}

	public void setDepartDeptCode(String departDeptCode) {
		this.departDeptCode = departDeptCode;
	}

	public Date getPreArriveTimeBegin() {
		return preArriveTimeBegin;
	}

	public void setPreArriveTimeBegin(Date preArriveTimeBegin) {
		this.preArriveTimeBegin = preArriveTimeBegin;
	}

	public Date getPreArriveTimeEnd() {
		return preArriveTimeEnd;
	}

	public void setPreArriveTimeEnd(Date preArriveTimeEnd) {
		this.preArriveTimeEnd = preArriveTimeEnd;
	}

	public Date getArriveTimeBegin() {
		return arriveTimeBegin;
	}

	public void setArriveTimeBegin(Date arriveTimeBegin) {
		this.arriveTimeBegin = arriveTimeBegin;
	}

	public Date getArriveTimeEnd() {
		return arriveTimeEnd;
	}

	public void setArriveTimeEnd(Date arriveTimeEnd) {
		this.arriveTimeEnd = arriveTimeEnd;
	}

	public Date getLeaveTimeBegin() {
		return leaveTimeBegin;
	}

	public void setLeaveTimeBegin(Date leaveTimeBegin) {
		this.leaveTimeBegin = leaveTimeBegin;
	}

	public Date getLeaveTimeEnd() {
		return leaveTimeEnd;
	}

	public void setLeaveTimeEnd(Date leaveTimeEnd) {
		this.leaveTimeEnd = leaveTimeEnd;
	}

	public Date getInStockTimeBegin() {
		return inStockTimeBegin;
	}

	public void setInStockTimeBegin(Date inStockTimeBegin) {
		this.inStockTimeBegin = inStockTimeBegin;
	}

	public Date getInStockTimeEnd() {
		return inStockTimeEnd;
	}

	public void setInStockTimeEnd(Date inStockTimeEnd) {
		this.inStockTimeEnd = inStockTimeEnd;
	}
	
	
}
