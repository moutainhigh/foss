package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;
import java.util.Date;

/**
 * 校验车牌号返回信息Dto
 * @author 332209
 * @date 2017-03-24 下午 16:36
 */
public class CheckVehicleNoResultDto implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -1450968692126059993L;

	/**
	 * 异常信息
	 */
	private String exceptionMsg;
	
	/**
	 * 线路名称
	 */
	private String lineName;

	/**
	 * 计划发车时间
	 */
	private Date planDepartTime;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}
	
	/**
	 * 发车计划ID
	 */
	private String id;
	
	/**
	 * 用来交互“车辆（公司、外请车）”的数据实体相关联信息的DTO
	 */
	private VehicleAssociationDto vehicleAssciationDto;
	
	/**
	 * 状态
	 */
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the vehicleAssciationDto
	 */
	public VehicleAssociationDto getVehicleAssciationDto() {
		return vehicleAssciationDto;
	}

	/**
	 * @param vehicleAssciationDto the vehicleAssciationDto to set
	 */
	public void setVehicleAssciationDto(VehicleAssociationDto vehicleAssciationDto) {
		this.vehicleAssciationDto = vehicleAssciationDto;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * @return the planDepartTime
	 */
	public Date getPlanDepartTime() {
		return planDepartTime;
	}

	/**
	 * @param planDepartTime the planDepartTime to set
	 */
	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
	}

	/**
	 * @return the exceptionMsg
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	/**
	 * @param exceptionMsg the exceptionMsg to set
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}
