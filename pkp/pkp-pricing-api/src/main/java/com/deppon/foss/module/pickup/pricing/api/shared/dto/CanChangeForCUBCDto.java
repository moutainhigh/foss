package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;

/**
 * Created by 343617 on 2016/11/3.
 * 与CUBC是否可更改接口交互后生成类
 */
public class CanChangeForCUBCDto implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 49998556534921231L;

    //是否可发更改
    private boolean result;
    //返回消息
    private String message;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
