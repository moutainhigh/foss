package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * FOSS查询物流交易单响应实体
 * 
 * @author: 347069
 * @Time: 2016年12月27日 下午8:18:44
 * @version: 1.0
 */
public class FossSearchTradeResponseDO implements Serializable {

	/**
	 * TODO
	 * 
	 * @Time: 2016年12月27日 下午8:19:34
	 */
	private static final long serialVersionUID = -3058324745470803337L;

	/**
	 * 查出的物流交易单列表
	 */
	private Map<String, List<TradeDO>> dataMap;

	/**
	 * 消息
	 */
	private String msg;

	/**
	 * 是否成功
	 */
	private String active = "N";


	/**
	 * 获取查出的物流交易单列表
	 * @return dataMap 查出的物流交易单列表
	 */
	public Map<String, List<TradeDO>> getDataMap() {
		return dataMap;
	}
	

	/**
	 * 设置查出的物流交易单列表
	 * @param dataMap 查出的物流交易单列表
	 */
	public void setDataMap(Map<String, List<TradeDO>> dataMap) {
		this.dataMap = dataMap;
	}
	

	/**
	 * 获取消息
	 * 
	 * @return msg 消息
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置消息
	 * 
	 * @param msg
	 *            消息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 获取是否成功
	 * 
	 * @return active 是否成功
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置是否成功
	 * 
	 * @param active
	 *            是否成功
	 */
	public void setActive(String active) {
		this.active = active;
	}
}
