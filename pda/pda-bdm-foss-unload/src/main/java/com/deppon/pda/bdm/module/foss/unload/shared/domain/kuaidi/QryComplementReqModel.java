package com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi;

import java.util.Date;



/**
 * 补码请求实体类
 * @author wenwuneng
 * @date 2013-07-22
 * @version 1.0
 * @since
 */
public class QryComplementReqModel {
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 *查询开始时间
	 */
	private Date queryStartTime;
	/**
	 *同城或非同城
	 */
	private boolean queryType;
	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public Date getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(Date queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public boolean isQueryType() {
		return queryType;
	}

	public void setQueryType(boolean queryType) {
		this.queryType = queryType;
	}

	
	
	
}
