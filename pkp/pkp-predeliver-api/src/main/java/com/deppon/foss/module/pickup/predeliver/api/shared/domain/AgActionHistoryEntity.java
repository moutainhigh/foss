package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class AgActionHistoryEntity extends BaseEntity{

	private static final long serialVersionUID = -2209775267055134606L;

    private String tSrvAbandonApplicationId;

    private String status;

    private String operator;

    private String operatorCode;

    private String operateOrgName;

    private String operateOrgCode;
    private String operateContent;

    private Date operateTime;

    public String gettSrvAbandonApplicationId() {
        return tSrvAbandonApplicationId;
    }

    public void settSrvAbandonApplicationId(String tSrvAbandonApplicationId) {
        this.tSrvAbandonApplicationId = tSrvAbandonApplicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperateOrgName() {
        return operateOrgName;
    }

    public void setOperateOrgName(String operateOrgName) {
        this.operateOrgName = operateOrgName;
    }

    public String getOperateOrgCode() {
        return operateOrgCode;
    }

    public void setOperateOrgCode(String operateOrgCode) {
        this.operateOrgCode = operateOrgCode;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}