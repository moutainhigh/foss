package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 快递派件交接 派送单信息 dto
 * @author 243921-foss-zhangtingting
 * @date 2015-05-11 下午6:49:20
 */
public class DeliverbillDataDto implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    /**
     * 派送单ID

     */
    private String deliverbillId;
    /**
     * 派送单号
     */
    private String deliverbillNo;
    /**
     * 总票数
     */
    private Integer waybillQtyTotal;
    /**
     * 总件数
     */
    private Integer goodsQtyTotal;
    /**
     * 总到付金额
     */
    private BigDecimal payAmountTotal;
    /**
     * 总重量
     */
    private BigDecimal weightTotal;
    /**
     * 总体积
     */
    private BigDecimal volumeTotal;

    public String getDeliverbillNo() {
        return deliverbillNo;
    }

    public void setDeliverbillNo(String deliverbillNo) {
        this.deliverbillNo = deliverbillNo;
    }

    public Integer getWaybillQtyTotal() {
        return waybillQtyTotal;
    }

    public void setWaybillQtyTotal(Integer waybillQtyTotal) {
        this.waybillQtyTotal = waybillQtyTotal;
    }

    public Integer getGoodsQtyTotal() {
        return goodsQtyTotal;
    }

    public void setGoodsQtyTotal(Integer goodsQtyTotal) {
        this.goodsQtyTotal = goodsQtyTotal;
    }

    public BigDecimal getPayAmountTotal() {
        return payAmountTotal;
    }

    public void setPayAmountTotal(BigDecimal payAmountTotal) {
        this.payAmountTotal = payAmountTotal;
    }

    public BigDecimal getWeightTotal() {
        return weightTotal;
    }

    public void setWeightTotal(BigDecimal weightTotal) {
        this.weightTotal = weightTotal;
    }

    public BigDecimal getVolumeTotal() {
        return volumeTotal;
    }

    public void setVolumeTotal(BigDecimal volumeTotal) {
        this.volumeTotal = volumeTotal;
    }

    public String getDeliverbillId() {
        return deliverbillId;
    }

    public void setDeliverbillId(String deliverbillId) {
        this.deliverbillId = deliverbillId;
    }

}