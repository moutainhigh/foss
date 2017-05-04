package com.deppon.foss.module.settlement.consumer.api.shared.vo;



import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.MergeWaybillDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.MergeWaybillQueryDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 322906 on 2016/6/15.
 */
public class MergeWaybillVo implements Serializable {

    private static final long serialVersionUID = -7882482432600174680L;
    private String mergeWaybillNo;
    /**
     * 页面中的查询参数
     */
    private MergeWaybillQueryDto mergeWaybillQueryDto;
    /**
     * 查询返回给页面的合并运单列表
     */
    private List<MergeWaybillDto> mergeWaybillDtoList;

    private List<WaybillDetailEntity> waybillDetailEntityList;

    public List<WaybillDetailEntity> getWaybillDetailEntityList() {
        return waybillDetailEntityList;
    }

    public void setWaybillDetailEntityList(List<WaybillDetailEntity> waybillDetailEntityList) {
        this.waybillDetailEntityList = waybillDetailEntityList;
    }

    public String getMergeWaybillNo() {
        return mergeWaybillNo;
    }

    public void setMergeWaybillNo(String mergeWaybillNo) {
        this.mergeWaybillNo = mergeWaybillNo;
    }

    public MergeWaybillQueryDto getMergeWaybillQueryDto() {
        return mergeWaybillQueryDto;
    }

    public void setMergeWaybillQueryDto(MergeWaybillQueryDto mergeWaybillQueryDto) {
        this.mergeWaybillQueryDto = mergeWaybillQueryDto;
    }

    public List<MergeWaybillDto> getMergeWaybillDtoList() {
        return mergeWaybillDtoList;
    }

    public void setMergeWaybillDtoList(List<MergeWaybillDto> mergeWaybillDtoList) {
        this.mergeWaybillDtoList = mergeWaybillDtoList;
    }
}
