package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * FOSS查询物流交易单请求实体
 * 
 * @author: 347069
 * @Time: 2016年12月27日 下午8:18:44
 * @version: 1.0
 */
public class FossSearchTradeRequestDO implements Serializable {

	/**
	 * TODO
	 * 
	 * @Time: 2016年12月27日 下午8:19:34
	 */
	private static final long serialVersionUID = -3058324745470803337L;
	/**
	 * 物流交易单集合
	 */
	private List<String> waybillNos;
	/**
	 * 物流交易单子类型 对应 单据子类型
	 */
	private List<String> orderSubType;

	/**
	 * 获取物流交易单集合
	 * 
	 * @return waybillNos 物流交易单集合
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * 设置物流交易单集合
	 * 
	 * @param waybillNos
	 *            物流交易单集合
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * 获取物流交易单子类型对应单据子类型
	 * 
	 * @return orderSubType 物流交易单子类型对应单据子类型
	 */
	public List<String> getOrderSubType() {
		return orderSubType;
	}

	/**
	 * 设置物流交易单子类型对应单据子类型
	 * 
	 * @param orderSubType
	 *            物流交易单子类型对应单据子类型
	 */
	public void setOrderSubType(List<String> orderSubType) {
		this.orderSubType = orderSubType;
	}

}
