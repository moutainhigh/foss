package com.deppon.foss.module.settlement.pay.api.shared.vo;

import com.deppon.foss.module.settlement.pay.api.shared.domain.DebitWillOverEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DebitWillOverDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 073615 on 2014/12/26.
 */
public class DebitWillOverVo implements Serializable {
    /**
     * 查询条件
     */
    private DebitWillOverDto dto;

    /**
     * 返回结果集
     */
    private List<DebitWillOverEntity> list;

    public DebitWillOverDto getDto() {
        return dto;
    }

    public void setDto(DebitWillOverDto dto) {
        this.dto = dto;
    }

    public List<DebitWillOverEntity> getList() {
        return list;
    }

    public void setList(List<DebitWillOverEntity> list) {
        this.list = list;
    }
}
