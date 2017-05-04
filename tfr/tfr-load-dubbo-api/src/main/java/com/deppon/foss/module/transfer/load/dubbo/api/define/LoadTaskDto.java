package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * PDA传给foss的装车任务参数
 * @author 332209-FOSS-ruilibao
 * @date 2017年3月29日
 */
public class LoadTaskDto implements Serializable {
    
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2807190209224336184L;

	/**任务类型**/
    private String loadTaskType;
    
    /**货物类型*/
    private String goodsType;
    
    /**派送单号*/
    private String deliverBillNo;
    
    /**目的站编码*/
    private List<String> destOrgCodes;
    
    /**快递员工号**/
    private String tallyerCode;
    
    /** 装车类型 快递 1, 零担0, 合单2 **/
    private Integer sendType;
    
    /** 校验异常信息 **/
    private String exceptionMsg;
    
    /** 线路名称 **/
    private String lineName;
    
    /** 计划发车时间 **/
    private Date planDepartTime;
    
    /** 发车计划ID **/
    private String id;
    
    /** 理货员 **/
    private List<LoaderDto> loaderCodes;
    
    /**月台号*/
    private String platformNo;
    
    /** 车牌号 **/
    private String vehicleNo;
    
    /**建立任务部门*/
    private String createOrgCode;
    
    /**建立任务时间*/
    private Date createTime;
    
    /**操作人编号*/
    private String operatorCode;

    /**PDA设备号*/
    private String deviceNo;
    
    /**任务编号*/
    private String taskNo;
    
    /**转货类型*/
    private String transitGoodsType;
    
    /** 同步给悟空系统的操作人员信息 **/
    private LoadTaskCreateDto loadTaskCreateDto;
    
    /**
     * 用来交互“车辆（公司、外请车）”的数据实体相关联信息的DTO
     */
    private VehicleAssociationDto vehicleAssciationDto;
    
    private String submitStatus;
    
    private String loadTaskUuid;
    
	/**
	 * @return the loadTaskUuid
	 */
	public String getLoadTaskUuid() {
		return loadTaskUuid;
	}

	/**
	 * @param loadTaskUuid the loadTaskUuid to set
	 */
	public void setLoadTaskUuid(String loadTaskUuid) {
		this.loadTaskUuid = loadTaskUuid;
	}

	/**
	 * @return the loadTaskType
	 */
	public String getLoadTaskType() {
		return loadTaskType;
	}

	/**
	 * @param loadTaskType the loadTaskType to set
	 */
	public void setLoadTaskType(String loadTaskType) {
		this.loadTaskType = loadTaskType;
	}

	/**
	 * @return the goodsType
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * @return the deliverBillNo
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}

	/**
	 * @param deliverBillNo the deliverBillNo to set
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}

	/**
	 * @return the destOrgCodes
	 */
	public List<String> getDestOrgCodes() {
		return destOrgCodes;
	}

	/**
	 * @param destOrgCodes the destOrgCodes to set
	 */
	public void setDestOrgCodes(List<String> destOrgCodes) {
		this.destOrgCodes = destOrgCodes;
	}

	/**
	 * @return the tallyerCode
	 */
	public String getTallyerCode() {
		return tallyerCode;
	}

	/**
	 * @param tallyerCode the tallyerCode to set
	 */
	public void setTallyerCode(String tallyerCode) {
		this.tallyerCode = tallyerCode;
	}

	/**
	 * @return the sendType
	 */
	public Integer getSendType() {
		return sendType;
	}

	/**
	 * @param sendType the sendType to set
	 */
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
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

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
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
	 * @return the loaderCodes
	 */
	public List<LoaderDto> getLoaderCodes() {
		return loaderCodes;
	}

	/**
	 * @param loaderCodes the loaderCodes to set
	 */
	public void setLoaderCodes(List<LoaderDto> loaderCodes) {
		this.loaderCodes = loaderCodes;
	}

	/**
	 * @return the platformNo
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * @param platformNo the platformNo to set
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * @param taskNo the taskNo to set
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * @return the transitGoodsType
	 */
	public String getTransitGoodsType() {
		return transitGoodsType;
	}

	/**
	 * @param transitGoodsType the transitGoodsType to set
	 */
	public void setTransitGoodsType(String transitGoodsType) {
		this.transitGoodsType = transitGoodsType;
	}

	/**
	 * @return the loadTaskCreateDto
	 */
	public LoadTaskCreateDto getLoadTaskCreateDto() {
		return loadTaskCreateDto;
	}

	/**
	 * @param loadTaskCreateDto the loadTaskCreateDto to set
	 */
	public void setLoadTaskCreateDto(LoadTaskCreateDto loadTaskCreateDto) {
		this.loadTaskCreateDto = loadTaskCreateDto;
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
	 * @return the submitStatus
	 */
	public String getSubmitStatus() {
		return submitStatus;
	}

	/**
	 * @param submitStatus the submitStatus to set
	 */
	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}
	
}
