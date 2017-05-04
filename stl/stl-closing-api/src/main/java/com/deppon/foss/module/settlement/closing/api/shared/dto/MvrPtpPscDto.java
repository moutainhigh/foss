package com.deppon.foss.module.settlement.closing.api.shared.dto;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpPscEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by youkun on 2016/3/18.
 */
public class MvrPtpPscDto implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 6128359801326059811L;

    /**
     * 期间
     */
    private String period;

    /**
     * 运输性质
     */
    private String productCode;

    /**
     * 合伙人名称
     */
    private String customerCode;
    /**
     * 始发部门
     */
    private String origOrgCode;
    /**
     * 到达部门
     */
    private String destOrgCode;

    /**
     * 合伙人子公司月报表结合
     */
    private List<MvrPtpPscEntity> mvrPtpPscEntityList;

    /**
     * 用户名
     */
    private String  userCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
    }

    public List<MvrPtpPscEntity> getMvrPtpPscEntityList() {
        return mvrPtpPscEntityList;
    }

    public void setMvrPtpPscEntityList(List<MvrPtpPscEntity> mvrPtpPscEntityList) {
        this.mvrPtpPscEntityList = mvrPtpPscEntityList;
    }
}
