
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/** 
 * @author: zwd-200968
 * @description: 返回运单集合中,处于运输中和丢货的票数,供接送货调用 
 * @date: 2015-07-24 
 * 
 */
public class CalWaybillQtyDto implements Serializable {
	 
	
	
	private static final long serialVersionUID = 1712564752243457319L;

	/**
	 * 运输中票数
	 * 
	 */
	private Integer OnTheWayQTYTotal;
	/**
	 * 遗失票数
	 * 
	 */
	private Integer LoseGoodsQTYTotal;
	
	//存放运单号+运单状态
	Map<String, String> map = new HashMap<String, String>();
	
	
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public Integer getOnTheWayQTYTotal() {
		return OnTheWayQTYTotal;
	}
	public void setOnTheWayQTYTotal(Integer onTheWayQTYTotal) {
		OnTheWayQTYTotal = onTheWayQTYTotal;
	}
	public Integer getLoseGoodsQTYTotal() {
		return LoseGoodsQTYTotal;
	}
	public void setLoseGoodsQTYTotal(Integer loseGoodsQTYTotal) {
		LoseGoodsQTYTotal = loseGoodsQTYTotal;
	}
	
	
	
}
