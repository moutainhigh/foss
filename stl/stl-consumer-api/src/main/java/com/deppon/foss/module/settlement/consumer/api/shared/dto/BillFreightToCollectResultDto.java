package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 *  到付清查查询数据Dto
 *  查询的大部分数据，可以从
 *  
 * @author 099995-foss-wujiangtao
 * @date 2013-3-25 下午2:56:46
 * @since
 * @version
 */
public class BillFreightToCollectResultDto extends BillReceivableEntity{

	/**
	 * 序列化标记
	 */
	private static final long serialVersionUID = -2164232663707688914L;
	
	/**
	 * 库存状态
	 */
	private String stockStatus;

	
	/**
	 * @return  the stockStatus
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	
	/**
	 * @param stockStatus the stockStatus to set
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

}
