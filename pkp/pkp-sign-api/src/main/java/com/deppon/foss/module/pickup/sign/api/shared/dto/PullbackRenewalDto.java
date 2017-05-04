package com.deppon.foss.module.pickup.sign.api.shared.dto;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;

/**
 * WaybillSignResultService接口 updatePullBackStatus方法的参数类型dto FOSS回传OMS派送拉回原因
 * 
 * @author 353654 DN201608260005
 * 
 */
public class PullbackRenewalDto {

    // 订单号
    private String orderNo;

    // 当前用户信息
    private CurrentInfo currentInfo;

    // 到达联实体
    private ArriveSheetEntity entity;

    public PullbackRenewalDto(String waybillNo, String orderNo, CurrentInfo currentInfo,
            ArriveSheetEntity entity) {
        super();
        this.orderNo = orderNo;
        this.currentInfo = currentInfo;
        this.entity = entity;
    }

    public PullbackRenewalDto() {
        super();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public CurrentInfo getCurrentInfo() {
        return currentInfo;
    }

    public void setCurrentInfo(CurrentInfo currentInfo) {
        this.currentInfo = currentInfo;
    }

    public ArriveSheetEntity getEntity() {
        return entity;
    }

    public void setEntity(ArriveSheetEntity entity) {
        this.entity = entity;
    }

}
