package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @ClassName: ScanDataUploadEntity 
 * @Description: TODO(扫描数据上传实体) 
 * @author &268974  wangzhili
 * @date 2016-5-17 下午2:55:21 
 *
 */
public class ScanDataUploadEntity extends ScanMsgEntity implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
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
	//收货部门编码
	private String consigneeDeptCode;
	//运单信息
	private List<WaybillInfoEntitys> waybillInfoEntity;
	//总件数
	private int totalPieces;
	//总票数
	private int totalVotes;
	//总重量 
	private BigDecimal totalWeight;
	//总体积
	private BigDecimal totalVolume;
    
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
	public List<WaybillInfoEntitys> getWaybillInfoEntity() {
		return waybillInfoEntity;
	}
	public void setWaybillInfoEntity(List<WaybillInfoEntitys> waybillInfoEntity) {
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

}
