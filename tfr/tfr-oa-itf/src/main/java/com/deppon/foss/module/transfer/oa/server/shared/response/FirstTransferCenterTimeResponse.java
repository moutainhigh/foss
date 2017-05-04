package com.deppon.foss.module.transfer.oa.server.shared.response;

import java.util.Date;

/**
 * 查询货物轨迹返回CUBC实体，
 * 参数问waybillNo
 * @author 310248
 *
 */
public class FirstTransferCenterTimeResponse {
    
	//第一外场交接时间
	private Date firstTranserCenterTime;
	
	//响应信息
	private String errmsg;
	
	public Date getFirstTranserCenterTime() {
		return firstTranserCenterTime;
	}
	public void setFirstTranserCenterTime(Date firstTranserCenterTime) {
		this.firstTranserCenterTime = firstTranserCenterTime;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
