package com.deppon.foss.module.settlement.pay.api.shared.vo;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountAddDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 073615 on 2014/12/26.
 */
public class DiscountAddVo implements Serializable {
    /**
     * 查询条件
     */
    private DiscountAddDto dto;

    /**
     * 应收单结果集
     */
    private List<BillReceivableEntity> receivableList;
    /**
     * 代收货款总费用
     */
    private BigDecimal codAmount;

    /**
     * 保价总费用
     *
     */
    private BigDecimal insuranceAmount;

    /**
     *运费总费用
     */
    private BigDecimal transportAmount;


    public DiscountAddDto getDto() {
        return dto;
    }

    public void setDto(DiscountAddDto dto) {
        this.dto = dto;
    }

    public List<BillReceivableEntity> getReceivableList() {
        return receivableList;
    }

    public void setReceivableList(List<BillReceivableEntity> receivableList) {
        this.receivableList = receivableList;
    }

    public BigDecimal getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(BigDecimal codAmount) {
        this.codAmount = codAmount;
    }

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public BigDecimal getTransportAmount() {
        return transportAmount;
    }

    public void setTransportAmount(BigDecimal transportAmount) {
        this.transportAmount = transportAmount;
    }
}
