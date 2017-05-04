package com.deppon.foss.module.settlement.closing.api.shared.vo;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpsDto;

import java.io.Serializable;

/**
 * Created by youkun on 2016/3/18.
 */
public class MvrPtpRpsVo implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = -4623302613676100797L;

    //合伙人奖罚特殊月结报表
    private MvrPtpRpsDto mvrPtpRpsDto;

    public MvrPtpRpsDto getMvrPtpRpsDto() {
        return mvrPtpRpsDto;
    }

    public void setMvrPtpRpsDto(MvrPtpRpsDto mvrPtpRpsDto) {
        this.mvrPtpRpsDto = mvrPtpRpsDto;
    }
}
