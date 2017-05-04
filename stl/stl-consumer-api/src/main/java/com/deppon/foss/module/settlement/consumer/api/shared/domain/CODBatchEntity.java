package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 代收货款批次号
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-10 下午4:35:53
 * @since
 * @version
 */
public class CODBatchEntity extends BaseEntity {

	private static final long serialVersionUID = -7385023181095745403L;

	/**
	 * 操作时间
	 */
	private Date operateTime;

	/**
	 * 操作人编码
	 */
	private String operatorCode;

	/**
	 * 操作人名称
	 */
	private String operatorName;

	/**
	 * 批次号
	 */
	private String batchNo;

	/**
	 * 已发送、银企审核通过、银企审核不通过
	 */
	private String status;

	/**
	 * 银企返回失败原因
	 */
	private String failNotes;

	/**
	 * @return operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param  operateTime  
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param  operatorCode  
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param  operatorName  
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * @return batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param  batchNo  
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param  status  
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return failNotes
	 */
	public String getFailNotes() {
		return failNotes;
	}

	/**
	 * @param  failNotes  
	 */
	public void setFailNotes(String failNotes) {
		this.failNotes = failNotes;
	}



}
