package com.deppon.foss.module.transfer.stock.api.shared.dto;

import java.util.Date;

public class ChangeGoodsAreaQueryDto implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 *状态 
	 */
	private String state;
	/**
	 * 开始时间
	 */
	private Date beginInStockTime;
	/**
	 * 结束时间
	 */
	private Date endInStockTime;
	
	/**
	* @description 获取状态
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午3:53:48
	*/
	public String getState() {
		return state;
	}
	
	/**
	* @description 设置状态
	* @param state
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午3:53:58
	*/
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	* @description 获取开始时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午3:54:08
	*/
	public Date getBeginInStockTime() {
		return beginInStockTime;
	}
	
	/**
	* @description 设置开始时间
	* @param beginInStockTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午3:54:18
	*/
	public void setBeginInStockTime(Date beginInStockTime) {
		this.beginInStockTime = beginInStockTime;
	}
	
	/**
	* @description 获取结束时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午3:54:28
	*/
	public Date getEndInStockTime() {
		return endInStockTime;
	}
	
	/**
	* @description 设置结束时间
	* @param endInStockTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午3:54:39
	*/
	public void setEndInStockTime(Date endInStockTime) {
		this.endInStockTime = endInStockTime;
	}
	
}
