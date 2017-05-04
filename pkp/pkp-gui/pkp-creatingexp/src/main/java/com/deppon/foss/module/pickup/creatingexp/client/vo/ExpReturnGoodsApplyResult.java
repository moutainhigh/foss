/**  
 * Project Name:pkp-creatingexp  
 * File Name:ExpReturnGoodsApplyResult.java  
 * Package Name:com.deppon.foss.module.pickup.creatingexp.client.vo  
 * Date:2015-2-7下午4:58:58  
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.  
 *  
*/  
  
package com.deppon.foss.module.pickup.creatingexp.client.vo;  

import java.util.Date;

/**  
 * ClassName:ExpReturnGoodsApplyResult <br/>  
 * Function: 提交返货申请之后的返回的实体. <br/>  
 * Date:     2015-2-7 下午4:58:58 <br/>  
 * @author   146831  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public class ExpReturnGoodsApplyResult {
	/**
	 * 访问时间
	 */
	private Date entryTime;
	/**
	 * 返回时间
	 */
	private Date returnTime;
	/**
	 * 状态 
	 * 0 处理失败  1成功  2其他异常
	 */
	private int statusCode;
	
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
  
