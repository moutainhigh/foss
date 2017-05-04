package com.deppon.foss.module.settlement.closing.api.shared.vo;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciResultDto;

import java.io.Serializable;

/**
 * 始发偏线往来月报表VO.
 *
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrDciVo implements Serializable {

    /*serialVersionUID*/
    private static final long serialVersionUID = 2975726969096682391L;

    private MvrDciQueryDto queryDto;
    private MvrDciResultDto resultDto;

    public MvrDciQueryDto getQueryDto() {
        return queryDto;
    }

    public void setQueryDto(MvrDciQueryDto queryDto) {
        this.queryDto = queryDto;
    }

    public MvrDciResultDto getResultDto() {
        return resultDto;
    }

    public void setResultDto(MvrDciResultDto resultDto) {
        this.resultDto = resultDto;
    }

}
