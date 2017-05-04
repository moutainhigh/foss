package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.List;

/**
 * Created by 322906 on 2016/6/13.
 */
public class MergeWaybillResponse {
    /**
     * 返回给发票系统的合并运单信息
     */
    List<MergeWaybill4FimsDto> mergeWaybillInfo;
    /**
     * 返回结果 1:成功，0:失败
     */
    private String result;

    /**
     * 处理消息，失败时的提示信息
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<MergeWaybill4FimsDto> getMergeWaybillInfo() {
        return mergeWaybillInfo;
    }

    public void setMergeWaybillInfo(List<MergeWaybill4FimsDto> mergeWaybillInfo) {
        this.mergeWaybillInfo = mergeWaybillInfo;
    }
}
