package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.LandBillPaidCODGridDto;

/**
 * 快递代理代收货款审核Vo
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-7 上午8:53:26
 */
public class LandBillPaidCodVo implements Serializable {

	private static final long serialVersionUID = 5974109984376267306L;

	/**
	 * 查询条件
	 */
	private LandBillPaidCODConditionVo queryVo;

	/**
	 * 表单
	 */
	private List<LandBillPaidCODGridDto> grid;

	/**
	 * 代收货款ID
	 */
	private String[] entityIds;
	
	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * @return queryVo
	 */
	public LandBillPaidCODConditionVo getQueryVo() {
		return queryVo;
	}

	/**
	 * @param queryVo
	 */
	public void setQueryVo(LandBillPaidCODConditionVo queryVo) {
		this.queryVo = queryVo;
	}

	/**
	 * @return grid
	 */
	public List<LandBillPaidCODGridDto> getGrid() {
		return grid;
	}

	/**
	 * @param grid
	 */
	public void setGrid(List<LandBillPaidCODGridDto> grid) {
		this.grid = grid;
	}

	/**
	 * @return entityIds
	 */
	public String[] getEntityIds() {
		return entityIds;
	}

	/**
	 * @param entityIds
	 */
	public void setEntityIds(String[] entityIds) {
		this.entityIds = entityIds;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param  totalAmount  
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	
}
