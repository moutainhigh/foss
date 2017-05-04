package com.deppon.foss.module.settlement.closing.api.shared.vo;

import com.deppon.foss.module.settlement.closing.api.shared.dto.UnifiedSettlementDto;

import java.io.Serializable;

/**
 * Created by 073615 on 2014/11/17.
 */
public class UnifiedSettlementVo implements Serializable{
    private UnifiedSettlementDto dto ;

    public UnifiedSettlementDto getDto() {
        return dto;
    }

    public void setDto(UnifiedSettlementDto dto) {
        this.dto = dto;
    }
}
