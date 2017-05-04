/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LoadTaskDto.java
 *  
 *  FILE NAME          :LoadTaskDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;

/**
 * 装车任务
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:35:22
 */
public class LoadTaskDto extends LoadTaskEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8388659926340942766L;
	
	/**理货员**/
	private String loaderName;		

	/**开始时间**/
	private String beginDate;		

	/**结束时间**/
	private String endDate;			

	/**交接单编号**/
	private String handoverNo;		
	
	/**差异报告编号**/
	private String gaprepNo;		

	/**规定发车时间**/
	private Date planDepartTime;
	
	/** 装车来源(0-foss,1-悟空同步过来) */
	private String source;
	
	private String destOrgCode;
	
	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 理货员*.
	 *
	 * @return the 理货员*
	 */
	public String getLoaderName() {
		return loaderName;
	}
	
	/**
	 * 设置 理货员*.
	 *
	 * @param loaderName the new 理货员*
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	
	/**
	 * 获取 开始时间*.
	 *
	 * @return the 开始时间*
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 设置 开始时间*.
	 *
	 * @param beginDate the new 开始时间*
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 获取 结束时间*.
	 *
	 * @return the 结束时间*
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * 设置 结束时间*.
	 *
	 * @param endDate the new 结束时间*
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 获取 差异报告编号*.
	 *
	 * @return the 差异报告编号*
	 */
	public String getGaprepNo() {
		return gaprepNo;
	}
	
	/**
	 * 设置 差异报告编号*.
	 *
	 * @param gaprepNo the new 差异报告编号*
	 */
	public void setGaprepNo(String gaprepNo) {
		this.gaprepNo = gaprepNo;
	}
	
	/**
	 * 获取 规定发车时间*.
	 *
	 * @return the 规定发车时间*
	 */
	public Date getPlanDepartTime() {
		return planDepartTime;
	}
	
	/**
	 * 设置 规定发车时间*.
	 *
	 * @param planDepartTime the new 规定发车时间*
	 */
	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
	}
	
	/**
	 * 获取 交接单编号*.
	 *
	 * @return the 交接单编号*
	 */
	public String getHandoverNo() {
		return handoverNo;
	}
	
	/**
	 * 设置 交接单编号*.
	 *
	 * @param handoverNo the new 交接单编号*
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	

	
	
}