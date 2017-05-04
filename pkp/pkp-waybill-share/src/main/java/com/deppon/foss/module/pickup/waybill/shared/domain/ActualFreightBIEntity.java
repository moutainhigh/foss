package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ActualFreightBIEntity extends BaseEntity{

	private static final long serialVersionUID = -8812980922263778477L;

	private String id;

    private String actualFreightId;

    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActualFreightId() {
        return actualFreightId;
    }

    public void setActualFreightId(String actualFreightId) {
        this.actualFreightId = actualFreightId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}