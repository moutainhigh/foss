package com.deppon.foss.module.settlement.closing.api.shared.dto;

import com.deppon.foss.module.settlement.closing.api.shared.domain.UnifiedSettlementEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 073615 on 2014/11/17.
 */
public class UnifiedSettlementDto implements Serializable {
    /**
     * 期间id
     */
    private String periodId;
    /**
     * 客户编码
     */
    private String costomerCode;
    /**
     * 部门编码
     */
    private String orgCode;
    /**
     * 部门类型
     */
    private String orgType;
    /**
     * 返回查询结果集
     */
    private List<UnifiedSettlementEntity> list;

    public List<UnifiedSettlementEntity> getList() {
        return list;
    }

    public void setList(List<UnifiedSettlementEntity> list) {
        this.list = list;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getCostomerCode() {
        return costomerCode;
    }

    public void setCostomerCode(String costomerCode) {
        this.costomerCode = costomerCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}
