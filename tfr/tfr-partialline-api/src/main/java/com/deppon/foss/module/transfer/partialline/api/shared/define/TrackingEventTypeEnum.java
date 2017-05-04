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
 *  311396-foss-Wang Wenbo - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/define/TrackingEventTypeEnum.java
 *  
 *  FILE NAME          :TrackingEventTypeEnum.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.api.shared.define;
/**
 * 
 * @author 311396-foss-wwb
 * 用来轨迹编码对应
 *
 */
public enum TrackingEventTypeEnum {
	TRACKING_10("10", "进口清关完成，货物正在转运中", "INTERNATION_CUSTOMS_ARRIVAL" , "ARRIVAL_CLEARANCE"),
	TRACKING_11("11", "已抵达目的地转运场，货物正在转运中", "INTERNATION_TRANSFER_ARRIVAL" , "ARRIVETRANFIELD"),
	TRACKING_12("12", "抵达派送营业部，安排派送中", "INTERNATION_DEST_ARRIVAL" , "ARRIVE_DESTINATION"),
	TRACKING_13("13", "派送中", "INTERNATION_DELIVER" , "LDP_TRACK_DELIVER"),
	TRACKING_14("14", "正常签收", "INTERNATION_SIGN" , "SIGNED"),
	TRACKING_20("20", "派送异常", "INTERNATION_EXCEPTION_DELIVER" , "LDPDELIVERERROR"),
	TRACKING_21("21", "已退回", "INTERNATION_RETURN" , "RETURN"),
	TRACKING_22("22", "派送拉回-地址更改", "INTERNATION_ADDRESS_CHANGE" , "LDPDELIVERERROR"),
	TRACKING_23("23", "派送拉回-派送未完成", "INTERNATION_UNDELIVER" , "LDPDELIVERERROR"),
	TRACKING_24("24", "派送拉回-地址错误", "INTERNATION_ADDRESS_ERROR" , "LDPDELIVERERROR"),
	TRACKING_25("25", "已抵达口岸，出口清关中", "EXPORT_CLEARANCE" , "EXPORT_CLEARANCE"),
	TRACKING_26("26", "海关查验中", "CUSTOMS_INSPECTION" , "CUSTOMS_INSPECTION"),
	TRACKING_27("27", "出口清关完成，货物正在配载中", "COMPLETEEXPORTCLEAR" , "COMPLETEEXPORTCLEAR"),
	TRACKING_28("28", "跨境运输中", "CROSSBORDERDELIVER" , "CROSSBORDERDELIVER"),
	TRACKING_29("29", "已抵达口岸，进口清关中", "IMPORT_CLEARANCE" , "IMPORT_CLEARANCE");

	//事件编码
	private String bizcode;
	//事件信息
	private String bizName;
	//foss综合查询编码
	private String fossTrackCode;		
	//发给WQS编码
	private String wqsTrackCode;	

	private TrackingEventTypeEnum(String bizcode, String bizName, String fossTrackCode, String wqsTrackCode) {
		this.bizcode = bizcode;
		this.bizName = bizName;
		this.fossTrackCode = fossTrackCode;
		this.wqsTrackCode = wqsTrackCode;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getFossTrackCode() {
		return fossTrackCode;
	}

	public void setFossTrackCode(String fossTrackCode) {
		this.fossTrackCode = fossTrackCode;
	}

	public String getWqsTrackCode() {
		return wqsTrackCode;
	}

	public void setWqsTrackCode(String wqsTrackCode) {
		this.wqsTrackCode = wqsTrackCode;
	}
	
}