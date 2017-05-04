package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDciEntity;

/**
 * 折扣调整往来月报表
 * 
 * @author 105762
 * @date 2015-02-04
 */
public class MvrDciResultDto {

    private String userCode;

    private List<MvrDciEntity> entityList;

    private Long count;

    private BigDecimal dciDestApplyWoIncomeNus;
    private BigDecimal dciDestApplyWoIncomeUs;
    private BigDecimal ldciDestIncomeoNus   ;
    private BigDecimal ldciDestIncomeoUs    ;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<MvrDciEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<MvrDciEntity> entityList) {
        this.entityList = entityList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getDciDestApplyWoIncomeNus() {
        return dciDestApplyWoIncomeNus;
    }

    public void setDciDestApplyWoIncomeNus(BigDecimal dciDestApplyWoIncomeNus) {
        this.dciDestApplyWoIncomeNus = dciDestApplyWoIncomeNus;
    }

    public BigDecimal getDciDestApplyWoIncomeUs() {
        return dciDestApplyWoIncomeUs;
    }

    public void setDciDestApplyWoIncomeUs(BigDecimal dciDestApplyWoIncomeUs) {
        this.dciDestApplyWoIncomeUs = dciDestApplyWoIncomeUs;
    }

	public BigDecimal getLdciDestIncomeoNus() {
		return ldciDestIncomeoNus;
	}

	public void setLdciDestIncomeoNus(BigDecimal ldciDestIncomeoNus) {
		this.ldciDestIncomeoNus = ldciDestIncomeoNus;
	}

	public BigDecimal getLdciDestIncomeoUs() {
		return ldciDestIncomeoUs;
	}

	public void setLdciDestIncomeoUs(BigDecimal ldciDestIncomeoUs) {
		this.ldciDestIncomeoUs = ldciDestIncomeoUs;
	}
}
