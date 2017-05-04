package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
/**
 * 
 * @ClassName: QueryWaybillResult 
 * @Description: TODO(查询运单是否核销返回值实体) 
 * @author &268974  wangzhili
 * @date 2016-4-1 上午8:24:07 
 *
 */
public class QueryWaybillResult implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//是否核销  Y： 已核销   N ：未核销
	private String IswriteOff;
	public String getIswriteOff() {
		return IswriteOff;
	}
	public void setIswriteOff(String iswriteOff) {
		IswriteOff = iswriteOff;
	}
	

}
