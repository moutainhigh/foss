package com.deppon.foss.module.settlement.closing.api.shared.vo;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpPscDto;

import java.io.Serializable;

/**
 * Created by youkun on 2016/3/18.
 */
public class MvrPtpPscVo implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = -4623302613676100797L;

    /**
     * 合伙人子公司月结报表
     */
    private MvrPtpPscDto mvrPtpPscDto;


    public MvrPtpPscDto getMvrPtpPscDto() {
        return mvrPtpPscDto;
    }

    public void setMvrPtpPscDto(MvrPtpPscDto mvrPtpPscDto) {
        this.mvrPtpPscDto = mvrPtpPscDto;
    }
}
