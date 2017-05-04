/**   
* @Title: QuantityStaConstants.java 
* @Package com.deppon.foss.module.transfer.platform.api.shared.define 
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年8月25日 下午3:07:19 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.shared.define;

/** 
 * @ClassName: QuantityStaConstants 
 * @Description: 货量统计模块常量类
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年8月25日 下午3:07:19 
 *  
 */
public class QuantityStaConstants {

	public static final String DIP = "DIP";
	
	/***************************************************************************
	 *                                                  三种出发货量                                         *
	 ***************************************************************************/
	
	/**
	 * 货量统计类型-出发-全部
	 */
	public static final String STA_TYPE_DEPARTURE = "DEPARTURE";
	
	/**
	 * 货量统计类型-出发-长途出发
	 */
	public static final String STA_TYPE_DEPARTURE_LOCAL_DEPARTURE = "DEPARTURE_LONG_DISTANCE";
	
	/**
	 * 货量统计类型-出发-短途出发
	 */
	public static final String STA_TYPE_DEPARTURE_ARRIVAL_TRANSFER = "DEPARTURE_SHORT_DISTANCE";
	
	/**
	 * 货量统计类型-出发-派送货量
	 */
	public static final String STA_TYPE_DEPARTURE_DELIVER = "DEPARTURE_DELIVER";
	
	/***************************************************************************
	 *                                                  三种到达货量                                         *
	 ***************************************************************************/
	
	/**
	 * 货量统计类型-到达-全部
	 */
	public static final String STA_TYPE_ARRIVAL = "ARRIVAL";
	
	/**
	 * 货量统计类型-到达-长途到达
	 */
	public static final String STA_TYPE_ARRIVAL_LONG_DISTANCE = "ARRIVAL_LONG_DISTANCE";
	
	/**
	 * 货量统计类型-到达-短途到达
	 */
	public static final String STA_TYPE_ARRIVAL_SHORT_DISTANCE = "ARRIVAL_SHORT_DISTANCE";
	
	/**
	 * 货量统计类型-到达-集中接货
	 */
	public static final String STA_TYPE_ARRIVAL_FOCUS_RECIEVE = "ARRIVAL_CENTRALIZE_PICKUP";
	

}
