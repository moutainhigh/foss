package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
/**
 * 
 * @ClassName: QueryWaybillEntity 
 * @Description: TODO(查询运单是否核销参数实体) 
 * @author &268974  wangzhili
 * @date 2016-4-1 上午8:22:48 
 *
 */
public class QueryWaybillEntity implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	

}
