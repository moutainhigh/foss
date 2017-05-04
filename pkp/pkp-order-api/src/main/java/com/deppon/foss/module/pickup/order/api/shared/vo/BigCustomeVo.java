package com.deppon.foss.module.pickup.order.api.shared.vo;

import com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomerSummaryEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.BigCustomeDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.BigCustomerSummaryDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * caohuibin
 * Created by 268217 on 2015/9/11.
 */
public class BigCustomeVo implements Serializable {

    /**
     * 起始时间
     */
    private Date billTimeFrom;

    /**
     * 截止时间
     */
    private Date billTimeTo;

    /**
     *实体类集合
     */
    private List<BigCustomeEntity> bigCustomeEntitylist;

    /**
     *页面显示
     */
    private BigCustomeDto bigCustomeDto;

    /**
     *页面显示
     */
    private List<BigCustomeDto> bigCustomeDtoList;

    /**
     *页面导出
     * BigCustomerSummaryEntity
     */
    private List<BigCustomerSummaryEntity> bigCustomerSummaryEntityList;

    private BigCustomerSummaryDto bigCustomerSummaryDto;


    public BigCustomeDto getBigCustomeDto() {
        return bigCustomeDto;
    }

    public void setBigCustomeDto(BigCustomeDto bigCustomeDto) {
        this.bigCustomeDto = bigCustomeDto;
    }

    public List<BigCustomeEntity> getBigCustomeEntitylist() {
        return bigCustomeEntitylist;
    }

    public void setBigCustomeEntitylist(List<BigCustomeEntity> bigCustomeEntitylist) {
        this.bigCustomeEntitylist = bigCustomeEntitylist;
    }

    public Date getBillTimeFrom() {
        return billTimeFrom;
    }

    public void setBillTimeFrom(Date billTimeFrom) {
        this.billTimeFrom = billTimeFrom;
    }

    public Date getBillTimeTo() {
        return billTimeTo;
    }

    public void setBillTimeTo(Date billTimeTo) {
        this.billTimeTo = billTimeTo;
    }

    public List<BigCustomeDto> getBigCustomeDtoList() {
        return bigCustomeDtoList;
    }

    public void setBigCustomeDtoList(List<BigCustomeDto> bigCustomeDtoList) {
        this.bigCustomeDtoList = bigCustomeDtoList;
    }

    public BigCustomerSummaryDto getBigCustomerSummaryDto() {
        return bigCustomerSummaryDto;
    }

    public void setBigCustomerSummaryDto(BigCustomerSummaryDto bigCustomerSummaryDto) {
        this.bigCustomerSummaryDto = bigCustomerSummaryDto;
    }

	public List<BigCustomerSummaryEntity> getBigCustomerSummaryEntityList() {
		return bigCustomerSummaryEntityList;
	}

	public void setBigCustomerSummaryEntityList(
			List<BigCustomerSummaryEntity> bigCustomerSummaryEntityList) {
		this.bigCustomerSummaryEntityList = bigCustomerSummaryEntityList;
	}
    
    
}
