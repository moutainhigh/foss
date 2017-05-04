package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.util.define.FossConstants;

public class LabelPushProcessEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String orderNo;

    private String waybillNo;

    private Date createTime;
    //初始时，modifyTime=createTime
    private Date modifyTime;

    /**
     * WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_UNPUSHED
     * WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_PUSHING
     * WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_PUSHED
     * WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_SUCCESS
     * WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL
     */
    private String operateResult;
    
    private String message;

    private String jobId;
    //默认为0
    private Integer processCount = 0;
    //是否有效
    private String active = FossConstants.ACTIVE;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Integer getProcessCount() {
		return processCount;
	}

	public void setProcessCount(Integer processCount) {
		this.processCount = processCount;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getActive() {
		return active;
	}
}
