package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 电子运单预处理实体
 * @author Foss-105888-Zhangxingwang
 * @date 2015-4-24 10:54:13
 */
public class PreHandEWaybillOrderEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//主键ID
    private String id;

    //订单号
    private String orderNo;

    //运单号
    private String waybillNo;

    //任务ID
    private String jobId;

    //创建时间
    private Date createTime;

    //修改时间
    private Date modifyTime;

    //异常信息
    private String exceptionMsg;
    
    //运单类型
    private String waybillType;
    
    //状态
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
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

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg == null ? null : exceptionMsg.trim();
    }

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
