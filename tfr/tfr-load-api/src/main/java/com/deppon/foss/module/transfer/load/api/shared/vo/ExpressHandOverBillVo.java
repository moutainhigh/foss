package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto;

/** 
 * @className: ExpressHandOverBillVo
 * @author: ShiWei shiwei@outlook.com
 * @description: 包交接单Vo
 * @date: 2013-7-26 下午2:14:03
 * 
 */
public class ExpressHandOverBillVo {
	
	/**
	 * 包号
	 */
	private String packageNo;
	
	/**
	 * 交接单基本信息
	 */
	private HandOverBillEntity handOverBillEntity;
	
	/**
	 * 运单信息list
	 */
	private List<HandOverBillDetailEntity> waybillList;
	
	/**
	 * 新增交接单时传递参数
	 */
	private NewHandOverBillDto newHandOverBillDto;
	
	/**
	 * 快递转换体积参数
	 * */
	private BigDecimal expressConverParameter;
	

	public BigDecimal getExpressConverParameter() {
		return expressConverParameter;
	}

	public void setExpressConverParameter(BigDecimal expressConverParameter) {
		this.expressConverParameter = expressConverParameter;
	}

	public NewHandOverBillDto getNewHandOverBillDto() {
		return newHandOverBillDto;
	}

	public void setNewHandOverBillDto(NewHandOverBillDto newHandOverBillDto) {
		this.newHandOverBillDto = newHandOverBillDto;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public HandOverBillEntity getHandOverBillEntity() {
		return handOverBillEntity;
	}

	public void setHandOverBillEntity(HandOverBillEntity handOverBillEntity) {
		this.handOverBillEntity = handOverBillEntity;
	}

	public List<HandOverBillDetailEntity> getWaybillList() {
		return waybillList;
	}

	public void setWaybillList(List<HandOverBillDetailEntity> waybillList) {
		this.waybillList = waybillList;
	}
	
}
