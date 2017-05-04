/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/define/GisConstants.java
 * 
 * FILE NAME        	: GisConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.define
 * FILE    NAME: GisConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.define;

import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;


/**
 * GIS相关的词条信息
 * @author 026123-foss-lifengteng
 * @date 2012-12-27 下午4:27:11
 */
public class GisConstants {
	
	public static final String PARA = "&";
    
    //GIS 需特殊处理城市
    public static final String BEIJING = "北京";
    public static final String TIANJIN = "天津";
    public static final String CHONGQING = "重庆";
    public static final String SHANGHAI = "上海";
    
	/*********** GIS网点类型  **********/ 
	/**
	 * 自提
	 */
	public final static String GIS_MATCH_PICKUP = "pickup";
	
	/**
	 * 快递自提
	 */
	public final static String GIS_MATCH_PICKUP_EXPRESS = "expressPickup";
	
	/**
	 * 送货
	 */
	public final static String GIS_MATCH_DELIVER = "deliver";
	
	/**
	 * 快递送货
	 */
	public final static String GIS_MATCH_DELIVER_EXPRESS = "expressDeliver";
	
	/**
	 * 发货
	 */
	public final static String GIS_MATCH_LEAVE = "leave";
	
//	/**
//	 * 快递发货
//	 */
//	public final static String GIS_MATCH_LEAVE_EXPRESS = "expressLeave";
	
	/*********** GIS运输类型 begin *************/ 
	/**
	 * 运输类型 GIS 空运
	 */
	public final static String GIS_TRANS_AIR = "air";
	
	/**
	 *  GIS 汽运专线，用GIS_TRANS_SELF替代
	 */
	public final static String GIS_TRANS_HIGHWAYS = "motor_self";
	
	/**
	 *  GIS 偏线  
	 */
	public final static String GIS_TRANS_AGENCY = "motor_agency";		//zxy 20131212 DEFECT-682 新增：偏线类型
	
	/*********** GIS特殊地址备注类型 begin **********/ 
	/**
	 * 禁行区域
	 */
	public final static String SPECIAL_ADDRESS_FORBID = "JX";
	/**
	 * 进仓区域
	 */
	public final static String SPECIAL_ADDRESS_ENTER = "JC";
	
	/*********** GIS服务类型 begin **********/ 
	/**
	 * 特殊地址备注查询
	 */
	public final static String ADDRESS_REMARK_SEARCH = PropertiesUtil.getKeyValue("gis.service.remark");

	/**
	 * 区域综合匹配
	 */
	public final static String AREA_COMPRE_MATCH = PropertiesUtil.getKeyValue("gis.service.areasearch");

	/**
	 * 到达网点匹配
	 */
	public final static String ARRIVE_DEPT_MATCH = PropertiesUtil.getKeyValue("gis.serivce.match");
	
	/**
	 * 提货网点查询
	 */
	public final static String SALES_DEPT_SEARCH = PropertiesUtil.getKeyValue("gis.page.arrival.query");
	
	/**
	 * 目的网站信息查询
	 */
	public final static String SALES_STATION_INFO_QUERY = PropertiesUtil.getKeyValue("gis.service.queryStationInfo");

	/**
	 * 部门历史经验匹配
	 */
	public final static String DEPT_HISEXPERIENCE_MATCH = "deptHisExperienceMatch";

	/**
	 * 部门经纬度匹配
	 */
	public final static String DEPT_LATLNG_MATCH = "deptLatLngMacth";

	/**
	 * 部门行政区域匹配
	 */
	public final static String DEPT_ADMIN_MATCH = "deptAdminMatch";

	/**
	 * 部门关键字匹配
	 */
	public final static String DEPT_KEYWORD_MATCH = "deptKeywordMatch";
	
	/**
	 * 汽运
	 */
	public final static String GIS_FORTUNE = "汽运";
	/**
	 * 送货
	 */
	public final static String GIS_DELIVER = "送货";
	/**
	 * 自提
	 */
	public final static String GIS_PICKUP = "自提";
	/**
	 * 进仓
	 */
	public final static  String DELIVER_INGA = "进仓"; 
	
}