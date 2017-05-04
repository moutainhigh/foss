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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/ScanDetailDto.java
 *  
 *  FILE NAME          :ScanDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.Date;

/**
 * 扫描明细
 * @author foss-wuyingjie
 * @date 2012-11-14 下午7:01:12
 */
public class ScanDetailDto {
	/**流水号*/
	private String serialNo;		
	/**扫描状态*/
	private String scanStatus;		
	/**扫描状态描述*/
	private String scanStatusDesc;	
	/**货物状态*/
	private String goodsStatus;		
	/**货物状态描述*/
	private String goodsStatusDesc;	
	/**操作时间*/
	private Date uploadTime;
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
	public String getScanStatusDesc() {
		return scanStatusDesc;
	}
	public void setScanStatusDesc(String scanStatusDesc) {
		this.scanStatusDesc = scanStatusDesc;
	}
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public String getGoodsStatusDesc() {
		return goodsStatusDesc;
	}
	public void setGoodsStatusDesc(String goodsStatusDesc) {
		this.goodsStatusDesc = goodsStatusDesc;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}