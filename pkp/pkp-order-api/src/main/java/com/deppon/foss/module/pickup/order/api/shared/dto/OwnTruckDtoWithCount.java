package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.List;


/**
 * 自有车结果（带count）
 * @author 038590-foss-wanghui
 * @date 2013-3-27 上午11:55:58
 */
public class OwnTruckDtoWithCount implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 数量
	 */
	private Long count;
	/**
	 * 自有车集合
	 */
	private List<OwnTruckDto> ownTruckList;
	
	/**
	 * Gets the count.
	 * 
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}
	
	/**
	 * Sets the count.
	 * 
	 * @param count the count to see
	 */
	public void setCount(Long count) {
		this.count = count;
	}
	
	/**
	 * Gets the ownTruckList.
	 * 
	 * @return the ownTruckList
	 */
	public List<OwnTruckDto> getOwnTruckList() {
		return ownTruckList;
	}
	
	/**
	 * Sets the ownTruckList.
	 * 
	 * @param ownTruckList the ownTruckList to see
	 */
	public void setOwnTruckList(List<OwnTruckDto> ownTruckList) {
		this.ownTruckList = ownTruckList;
	}
	
	
}
