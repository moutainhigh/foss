package com.deppon.foss.module.transfer.stock.server.message.define;

import com.deppon.foss.framework.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 335284 on 2017/3/24.
 */
public class ToPFDStockActionEntity implements Serializable{
    private ActionType type; // 增删
    private StockKind kind; // 外场 营业部 合车
    private List<? extends BaseEntity> data;
    private Date sendTime = new Date();

    public ToPFDStockActionEntity() {
    }

    /**
     *
     * @param type 增删
     * @param kind center对应stockentity, sale对应StockSaleEntity, together对应TogetherTruckStockEntity
     * @param data
     */
    public ToPFDStockActionEntity(ActionType type, StockKind kind, List<? extends BaseEntity> data) {
        this.type = type;
        this.kind = kind;
        this.data = data;
    }

    public ActionType getType() {
        return type;
    }

    public StockKind getKind() {
        return kind;
    }

    public void setKind(StockKind kind) {
        this.kind = kind;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public List<? extends BaseEntity> getData() {
        return data;
    }

    public void setData(List<? extends BaseEntity> data) {
        this.data = data;
    }
}
