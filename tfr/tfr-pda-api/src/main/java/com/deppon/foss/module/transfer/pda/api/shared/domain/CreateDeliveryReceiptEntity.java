package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: CreateDeliveryReceiptEntity 
 * @Description: TODO(生成交接单实体) 
 * @author &268974  wangzhili
 * @date 2016-5-17 下午2:54:16 
 *
 */
public class CreateDeliveryReceiptEntity implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	
	private static final long serialVersionUID = 1L;
	//修改时间
	private Date modifyTime;
	//交接单号
	private String handOverType;
	//司机工号
	private String driverCode;
	//车牌号
	private String truckCode;
	//任务编码
	private String taskCode;
	//创建时间
	private Date creationTime;
	//任务完成提交时间
	private Date submissionTime;
	// 收货部门编码
	private String consigneeDeptCode;
	//运单信息
	private List<WaybillInfoEntity> waybillInfoEntity;
	//总件数
	private int totalPieces;
	//总票数
	private int totalVotes;
	//总重量 
	private BigDecimal totalWeight;
	//总体积
	private BigDecimal totalVolume;
	
	// 分配类型
	private String assignState;
	
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	// 任务号
	private String handOverNo;

	public String getHandOverType() {
		return handOverType;
	}
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(Date submissionTime) {
		this.submissionTime = submissionTime;
	}
	public String getConsigneeDeptCode() {
		return consigneeDeptCode;
	}
	public void setConsigneeDeptCode(String consigneeDeptCode) {
		this.consigneeDeptCode = consigneeDeptCode;
	}
	public List<WaybillInfoEntity> getWaybillInfoEntity() {
		return waybillInfoEntity;
	}
	public void setWaybillInfoEntity(List<WaybillInfoEntity> waybillInfoEntity) {
		this.waybillInfoEntity = waybillInfoEntity;
	}
	public int getTotalPieces() {
		return totalPieces;
	}
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
	public int getTotalVotes() {
		return totalVotes;
	}
	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}
	public String getAssignState() {
		return assignState;
	}
	public void setAssignState(String assignState) {
		this.assignState = assignState;
	}
	public String getHandOverNo() {
		return handOverNo;
	}
	public void setHandOverNo(String handOverNo) {
		this.handOverNo = handOverNo;
	}
}
