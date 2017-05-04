package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 部门收入记录表
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-2-18 上午11:41:23
 * @since
 * @version
 */
public class OrgMonthIncomeEntity extends BaseEntity{

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 月份格式化到月（2013-02）
	 */
    private Date ctMonth;

    /**
     * 收入金额
     */
    private BigDecimal incomeAmount;

    /**
     * 创建时间
     */
    private Date createTime;

  

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Date getCtMonth() {
        return ctMonth;
    }

    public void setCtMonth(Date ctMonth) {
        this.ctMonth = ctMonth;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
