package com.deppon.foss.module.settlement.closing.api.shared.vo;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdResultDto;

import java.io.Serializable;

/**
 * 始发偏线往来月报表VO.
 *
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrDcdVo implements Serializable {

    /*serialVersionUID*/
    private static final long serialVersionUID = 2975726969096682391L;

    private MvrDcdQueryDto queryDto;
    private MvrDcdResultDto resultDto;

    public MvrDcdQueryDto getQueryDto() {
        return queryDto;
    }

    public void setQueryDto(MvrDcdQueryDto queryDto) {
        this.queryDto = queryDto;
    }

    public MvrDcdResultDto getResultDto() {
        return resultDto;
    }

    public void setResultDto(MvrDcdResultDto resultDto) {
        this.resultDto = resultDto;
    }

}
