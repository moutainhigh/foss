package com.deppon.foss.module.settlement.closing.api.shared.dto;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDcdEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 折扣调整到达月报表
 *
 * @author 105762
 * @date 2015-02-04
 */
public class MvrDcdResultDto {

    private String userCode;

    private List<MvrDcdEntity> entityList;

    private Long count;

    private BigDecimal dcdDestApplyWoIncomet;
    private BigDecimal dcdDestApplyWoCostt;
    private BigDecimal ldcDestIncomeo     ;
    private BigDecimal ldcDestCosto       ;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<MvrDcdEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<MvrDcdEntity> entityList) {
        this.entityList = entityList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getDcdDestApplyWoIncomet() {
        return dcdDestApplyWoIncomet;
    }

    public void setDcdDestApplyWoIncomet(BigDecimal dcdDestApplyWoIncomet) {
        this.dcdDestApplyWoIncomet = dcdDestApplyWoIncomet;
    }

    public BigDecimal getDcdDestApplyWoCostt() {
        return dcdDestApplyWoCostt;
    }

    public void setDcdDestApplyWoCostt(BigDecimal dcdDestApplyWoCostt) {
        this.dcdDestApplyWoCostt = dcdDestApplyWoCostt;
    }

	public BigDecimal getLdcDestIncomeo() {
		return ldcDestIncomeo;
	}

	public void setLdcDestIncomeo(BigDecimal ldcDestIncomeo) {
		this.ldcDestIncomeo = ldcDestIncomeo;
	}

	public BigDecimal getLdcDestCosto() {
		return ldcDestCosto;
	}

	public void setLdcDestCosto(BigDecimal ldcDestCosto) {
		this.ldcDestCosto = ldcDestCosto;
	}
}
