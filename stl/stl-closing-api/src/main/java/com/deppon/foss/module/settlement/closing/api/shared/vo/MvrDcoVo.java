package com.deppon.foss.module.settlement.closing.api.shared.vo;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoResultDto;

import java.io.Serializable;

/**
 * 始发偏线往来月报表VO.
 *
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrDcoVo implements Serializable {

    /*serialVersionUID*/
    private static final long serialVersionUID = 2975726969096682391L;

    private MvrDcoQueryDto queryDto;
    private MvrDcoResultDto resultDto;

    public MvrDcoQueryDto getQueryDto() {
        return queryDto;
    }

    public void setQueryDto(MvrDcoQueryDto queryDto) {
        this.queryDto = queryDto;
    }

    public MvrDcoResultDto getResultDto() {
        return resultDto;
    }

    public void setResultDto(MvrDcoResultDto resultDto) {
        this.resultDto = resultDto;
    }

}
