package com.deppon.foss.module.transfer.load.api.shared.define;


/**
* @description 自动补码常量类
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年5月14日 上午8:52:13
*/
public final class AutoAddCodeConstants {
	
	/**
	 * 补码结果-外发补码正常
	 */
	public final static String ADD_CODE_RESULT_OUTER_SUCCESS = "OUTER_SUCCESS";
	
	/**
	 * 补码结果-外发补码失败
	 */
	public final static String ADD_CODE_RESULT_OUTER_FAILURE = "OUTER_FAILURE";
	
	/**
	 * 补码结果-GIS匹配成功
	 */
	public final static String ADD_CODE_RESULT_GIS_SUCCESS = "GIS_SUCCESS";
	
	/**
	 * 补码结果-GIS匹配失败
	 */
	public final static String ADD_CODE_RESULT_GIS_FAILURE = "GIS_FAILURE";
	
	/**
	 * 补码结果-城市开关关闭
	 */
	public final static String ADD_CODE_RESULT_CITY_CLOSE = "CITY_CLOSE";
	
	
	/**
	 * job层报错 只做日志和重置缓存池数据
	 */
	public final static String ADD_CODE_BY_JOB_FAILURE = "GOB_FAILURE";
	
	
	/**
	 * 补码结果-城市静默开关关闭
	* @fields ADD_CODE_RESULT_CITY_CLOSE_JM
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午3:22:43
	* @version V1.0
	*/
	public final static String ADD_CODE_RESULT_CITY_CLOSE_JM = "CITY_CLOSE_JM";
	
	
	
	/**
	 * 自动补码job开关
	* @fields AUTO_ADD_JOB_OPEN
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午5:26:38
	* @version V1.0
	*/
	public final static int AUTO_ADD_JOB_OPEN =1;
	
	/**
	 * 综合返回值:城市关闭
	* @fields CITY_CLOSE_INT
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午4:10:09
	* @version V1.0
	*/
	public final static int CITY_CLOSE_INT = 1;
	
	/**
	 *  综合返回值:城市静默开启
	* @fields CITY_CLOSE_JM_INT
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午4:10:12
	* @version V1.0
	*/
	public final static int CITY_CLOSE_JM_INT = 2;
	
	/**
	 *  综合返回值:城市开启
	* @fields CITY_OPEN_INT
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午4:10:15
	* @version V1.0
	*/
	public final static int CITY_OPEN_INT = 3;
	
	
	/**
	 * 外发单子
	* @fields OUT_FLAG
	* @author 14022-foss-songjie
	* @update 2015年11月4日 下午2:24:32
	* @version V1.0
	*/
	public final static String OUT_FLAG = "OUT";
	
	
	/**
	 * 补码结果-业务异常
	 */
	public final static String ADD_CODE_RESULT_BIZ_EXCEPTION = "BIZ_EXCEPTION";
	
	/**
	 * 补码结果-其他异常
	 */
	public final static String ADD_CODE_RESULT_OTHER_EXP = "OTHER_EXP";
	/**
	 * 快递到达-被退回
	 */
	public final static String RETURN_ARRIVE = "RETURN_ARRIVE";
	/**
	 * 转寄退回
	 */
	public final static String RETURN_FORWARD = "RETURN_FORWARD";
	/**
	 * gis异常类型
	* @fields ADD_CODE_GIS_ERROR
	* @author 14022-foss-songjie
	* @update 2015年11月4日 下午4:00:37
	* @version V1.0
	*/
	public final static String ADD_CODE_GIS_ERROR = "GIS_ERROR";
}

