package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 合伙人对账单DTO
 * @author foss-youkun
 * @date 2016/1/22
 */
public class PartnerStatementDto extends BaseEntity {

    /**
     * 合伙人部门编码
     */
    private String contractOrgCode;

    /**
     * 业务开始日期
     */
    private Date businessStartDate;

    /**
     * 业务结束日期
     */
    private Date businessEndDate;

    /**
     * 单号集合
     */
    private List<String> waybillNoList;

    public String getContractOrgCode() {
        return contractOrgCode;
    }

    public void setContractOrgCode(String contractOrgCode) {
        this.contractOrgCode = contractOrgCode;
    }

    public Date getBusinessStartDate() {
        return businessStartDate;
    }

    public void setBusinessStartDate(Date businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    public Date getBusinessEndDate() {
        return businessEndDate;
    }

    public void setBusinessEndDate(Date businessEndDate) {
        this.businessEndDate = businessEndDate;
    }

    public List<String> getWaybillNoList() {
        return waybillNoList;
    }

    public void setWaybillNoList(List<String> waybillNoList) {
        this.waybillNoList = waybillNoList;
    }
}
