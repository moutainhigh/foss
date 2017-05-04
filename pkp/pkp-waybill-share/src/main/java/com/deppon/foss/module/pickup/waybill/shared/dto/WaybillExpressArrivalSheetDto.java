/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 快递 提交派送装车任务后生成派送单和到达联 dto
 * @author ibm-foss-sxw
 *
 */
public class WaybillExpressArrivalSheetDto implements Serializable{

	//序列号
	private static final long serialVersionUID = 2L;

	/**
	 * 每个运单信息列表
	 */
	private List<WaybillExpressArrivalSheetDto> list;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 运单件数
	 */
	private Integer goodQtyTotal;
	
	/**
	 *  创建人工号
	 */
	private String createUserCode;

	/**
	 * 创建人名字
	 */
	private String createUserName;
	
	/**
	 * 创建部门编码
	 */
	private String createOrgCode;
	
	/**
	 * 创建部门
	 */
	private String createOrgName;
	
	/** 车牌号. */
	private String vehicleNo;

	/** 司机工号. */
	private String driverCode;

	/** 司机姓名. */
	private String driverName;
	
	
	/**
	 * 到达联对象  不用传
	 */
	private ArriveSheetEntityDto arriveSheetEntityDto;
	
	/**
	 * 实际承运信息
	 */
	private ActualFreightEntity actualFreightEntity;
	
	/**
	 * 运单实际信息
	 */
	private WaybillEntity waybillEntity ;
	
	/**
	 *  派送单号  返回给外部中转使用
	 */
	private String deliveryNo;
	
	
	
	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public WaybillEntity getWaybillEntity() {
		return waybillEntity;
	}

	public void setWaybillEntity(WaybillEntity waybillEntity) {
		this.waybillEntity = waybillEntity;
	}

	public ActualFreightEntity getActualFreightEntity() {
		return actualFreightEntity;
	}

	public void setActualFreightEntity(ActualFreightEntity actualFreightEntity) {
		this.actualFreightEntity = actualFreightEntity;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public ArriveSheetEntityDto getArriveSheetEntityDto() {
		return arriveSheetEntityDto;
	}

	public void setArriveSheetEntityDto(ArriveSheetEntityDto arriveSheetEntityDto) {
		this.arriveSheetEntityDto = arriveSheetEntityDto;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public List<WaybillExpressArrivalSheetDto> getList() {
		return list;
	}

	public void setList(List<WaybillExpressArrivalSheetDto> list) {
		this.list = list;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Integer getGoodQtyTotal() {
		return goodQtyTotal;
	}

	public void setGoodQtyTotal(Integer goodQtyTotal) {
		this.goodQtyTotal = goodQtyTotal;
	}

	
}
