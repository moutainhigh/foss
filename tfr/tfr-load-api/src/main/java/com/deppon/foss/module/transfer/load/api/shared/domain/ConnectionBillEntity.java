package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 *接驳交接单基本信息实体
 * @author 205109-foss-zenghaibin
 * @date 2015-04-09 下午10:41:40
 */
public class ConnectionBillEntity extends BaseEntity {
	
	private static final long serialVersionUID = -1528734821764517108L;
	
	private String connectionBillNo;//交接单号
	
	private String handOverType;//交接类型
	
	private int statu;//状态
	
	private Date handOverTime;//交接日期
	
	private String vehicleNo;//车牌号
	
	private String departDeptName; //出发部门名称
	
	private String departDeptCode; //出发部门编码
	
	private String arriveDeptName; //到达接驳点名称
	
	private String arriveDeptCode; //到达接驳点编码
	
	private Date arriveTime;//到达时间
	
	private String assignState;//分配状态
	
	private BigDecimal waybillQtyTotal;//总票数
	
	private BigDecimal goodsQtyTotal;//总件数
	
	private BigDecimal volumeTotal;//总体积
	
	private BigDecimal weightTotal;//总重量

	private String createUserName;//制单人名称
	
	private String createUserCode;//制单人工号
	
	private String driverName;//司机姓名
	
	private String driverCode;//司机工号

	private Date loadEndTime;//装车完成时间
	
	private String driverTel;//司机电话 
	
	private String modifyUserName;//修改姓名
	
	private String modifyUserCode;//修改人编码
	
	private String  isPda;//是否pda生成
	
	private String notes;//备注
	
	private String loadTaskNo;//装车任务编号
	
	private Date departTime;//出发时间
	
	private String arrivalDept;//到达部门（外场）
	
	public String getConnectionBillNo() {
		return connectionBillNo;
	}

	public void setConnectionBillNo(String connectionBillNo) {
		this.connectionBillNo = connectionBillNo;
	}

	public int getStatu() {
		return statu;
	}

	public void setStatu(int statu) {
		this.statu = statu;
	}

	public Date getHandOverTime() {
		return handOverTime;
	}

	public void setHandOverTime(Date handOverTime) {
		this.handOverTime = handOverTime;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDepartDeptName() {
		return departDeptName;
	}

	public void setDepartDeptName(String departDeptName) {
		this.departDeptName = departDeptName;
	}

	public String getDepartDeptCode() {
		return departDeptCode;
	}

	public void setDepartDeptCode(String departDeptCode) {
		this.departDeptCode = departDeptCode;
	}

	public String getArriveDeptName() {
		return arriveDeptName;
	}

	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}


	public BigDecimal getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	public void setWaybillQtyTotal(BigDecimal waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}

	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public Date getLoadEndTime() {
		return loadEndTime;
	}

	public void setLoadEndTime(Date loadEndTime) {
		this.loadEndTime = loadEndTime;
	}

	public String getDriverTel() {
		return driverTel;
	}

	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getHandOverType() {
		return handOverType;
	}

	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}

	public String getIsPda() {
		return isPda;
	}

	public void setIsPda(String isPda) {
		this.isPda = isPda;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAssignState() {
		return assignState;
	}

	public void setAssignState(String assignState) {
		this.assignState = assignState;
	}

	public String getLoadTaskNo() {
		return loadTaskNo;
	}

	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}
	public Date getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	public String getArrivalDept() {
		return arrivalDept;
	}

	public void setArrivalDept(String arrivalDept) {
		this.arrivalDept = arrivalDept;
	}
    
	
	
}