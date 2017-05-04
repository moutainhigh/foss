package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillQueryDto;

import java.util.List;
import java.util.Map;

/**
 * Created by 322906 on 2016/6/22.
 */
public class WaybillVo {
    /**
     * 运单号集合
     */
    private String[] waybillNos;
    /**
     * 纳税人信息
     */
    private Map<String,String> taxPayer;

    /**
     * 页面中的查询参数
     */
    private WaybillQueryDto waybillQueryDto;
    /**
     * 运单查询返回给前端的列表
     */
    private List<WaybillDetailEntity> waybillDetailEntityList;

    public String[] getWaybillNos() {
        return waybillNos;
    }

    public void setWaybillNos(String[] waybillNos) {
        this.waybillNos = waybillNos;
    }

    public Map<String, String> getTaxPayer() {
        return taxPayer;
    }

    public void setTaxPayer(Map<String, String> taxPayer) {
        this.taxPayer = taxPayer;
    }

    public WaybillQueryDto getWaybillQueryDto() {
        return waybillQueryDto;
    }

    public void setWaybillQueryDto(WaybillQueryDto waybillQueryDto) {
        this.waybillQueryDto = waybillQueryDto;
    }

    public List<WaybillDetailEntity> getWaybillDetailEntityList() {
        return waybillDetailEntityList;
    }

    public void setWaybillDetailEntityList(List<WaybillDetailEntity> waybillDetailEntityList) {
        this.waybillDetailEntityList = waybillDetailEntityList;
    }
}
