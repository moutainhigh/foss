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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/StTaskWaybillNoListDto.java
 *  
 *  FILE NAME          :StTaskWaybillNoListDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于确认清仓任务中，按运单号分类的清仓任务货物清单
 * @author foss-wuyingjie
 * @date 2012-11-7 下午2:02:06
 */
public class StTaskWaybillNoListDto {
	/**运单号*/
	private String waybillNo;		
	/**运单号描述信息*/
	private String wayBillNoDesc;	
	/**某运单号下的件数*/
	private int serialNoNum;		
	/**某运单号下的件数描述信息*/
	private String serialNoNumDesc;
	/**流水号列表*/
	List<String> serialNoList = new ArrayList<String>();
	
	public StTaskWaybillNoListDto() {
	}
	
	/**
	 * 
	 *
	 * @param waybillNo 
	 */
	public StTaskWaybillNoListDto(String waybillNo) {
		this.waybillNo = waybillNo;
		this.wayBillNoDesc = waybillNo;
	}
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 运单号描述信息.
	 *
	 * @return the 运单号描述信息
	 */
	public String getWayBillNoDesc() {
		return wayBillNoDesc;
	}
	
	/**
	 * 设置 运单号描述信息.
	 *
	 * @param wayBillNoDesc the new 运单号描述信息
	 */
	public void setWayBillNoDesc(String wayBillNoDesc) {
		this.wayBillNoDesc = wayBillNoDesc;
	}
	
	/**
	 * 获取 某运单号下的件数.
	 *
	 * @return the 某运单号下的件数
	 */
	public int getSerialNoNum() {
		return serialNoNum;
	}
	
	/**
	 * 设置 某运单号下的件数.
	 *
	 * @param serialNoNum the new 某运单号下的件数
	 */
	public void setSerialNoNum(int serialNoNum) {
		this.serialNoNum = serialNoNum;
		this.serialNoNumDesc = "件数:" + serialNoNum;
	}
	
	/**
	 * 获取 某运单号下的件数描述信息.
	 *
	 * @return the 某运单号下的件数描述信息
	 */
	public String getSerialNoNumDesc() {
		return serialNoNumDesc;
	}
	
	/**
	 * 设置 某运单号下的件数描述信息.
	 *
	 * @param serialNoNumDesc the new 某运单号下的件数描述信息
	 */
	public void setSerialNoNumDesc(String serialNoNumDesc) {
		this.serialNoNumDesc = serialNoNumDesc;
	}
	
	/**
	 * 获取 流水号列表.
	 *
	 * @return the 流水号列表
	 */
	public List<String> getSerialNoList() {
		return serialNoList;
	}
	
	/**
	 * 设置 流水号列表.
	 *
	 * @param serialNoList the new 流水号列表
	 */
	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}
}