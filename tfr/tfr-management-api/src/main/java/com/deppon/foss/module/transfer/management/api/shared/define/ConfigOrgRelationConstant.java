/**   
 * File Name：ConfigOrgRelationConstant.java   
 *   
 * Version:1.0
 * ：2013-4-7   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.shared.define;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Description：
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-7 下午8:13:54
 */

public class ConfigOrgRelationConstant {
	// 配置项是否有效：有效
	public static final String CONFIGORGRELATION_ACTIVE_YES = "Y";
	// 配置项是否有效：无效
	public static final String CONFIGORGRELATION_ACTIVE_NO = "N";

	// 新增时：组织配置参数已经存在
	public static final String ORG_CONFIG_EXIST_EXCEPTION = "组织配置参数项信息已经存在!";

	// 配置参数为空
	public static final String CONFIGORGRELATIONENTITY_NULL = "配置参数为空!";
	// 配置参数为空
	public static final String CONFIGORGRELATIONENTITY_CONFCODE_NULL = "配置参数为空!";
	// 配置参数类型为空
	public static final String CONFIGORGRELATIONENTITY_CONFTYPE_NULL = "配置参数类型为空!";
	// 组织为空
	public static final String CONFIGORGRELATIONENTITY_ORGCODE_NULL = "组织部门为空!";

	// 配置参数类型:车柜证件
	public static final String CONF_TYPE_CODE_CONTAINERCARD = "CONTAINER_CARD_TYPE";
	// 配置参数类型: 车头证件
	public static final String CONF_TYPE_CODE_VEHICLEHEADCARD = "VEHICLEHEAD_CARD_TYPE";

	// 配置参数类型:车辆证件
	public static final String CONF_TYPE_CODE_VEHICLE = "VEHICLE_CARD_TYPE";

	// 常量冒号
	public static final String COLON_CONSTANT = ":";
	// 查询不到配置参数信息
	public static final String CONFIGORGRELATIONENTITY_CANNOT_FOUND = "查询不到配置参数信息!";

	// 配置项类型已经存在
	public static final String CONF_TYPE_EXSIT = "配置项类型已经存在!";
	// 配置项已经存在
	public static final String CONF_ITEM_EXSIT = "配置项已经存在";

	// 配置项类型是否有效：有效
	public static final String CONFIG_TYPE_ACTIVE_YES = "Y";
	// 配置项类型是否有效：无效
	public static final String CONFIG_TYPE_ACTIVE_NO = "N";

	// 配置项是否有效：有效
	public static final String CONFIG_ITEM_ACTIVE_YES = "Y";
	// 配置项是否有效：无效
	public static final String CONFIG_ITEM_ACTIVE_NO = "N";

	// 查询时项目信息丢失
	public static final String CONFIG_INFO_NULL = "您所选择的项目信息丢失！";

	public static final String CONFIG_TYPE_NOT_INIT = "配置项类型信息还未初始化,请点击<新增>按钮,添加配置项类型信息！";
	//配置项类型为空
	public static final String CONFIGORGRELATIONENTITY_CONFCODE_NULL_AND_CONFTYPE= "内容为空,请配置对应信息";
	
	//定义对应证件包证据件类型map
	public static Map<String,String> certificateBagCardTypeMap=null;
	
	public static Map<String,String> getCertificateBagCardTypeMap(){
		if(null==certificateBagCardTypeMap){
			certificateBagCardTypeMap=new HashMap<String,String>();
			certificateBagCardTypeMap.put("VEHICLE_CARD_TYPE", "车辆证件");
			certificateBagCardTypeMap.put("VEHICLEHEAD_CARD_TYPE", "车头证件");
			certificateBagCardTypeMap.put("CONTAINER_CARD_TYPE", "车柜证件");
		}
		return certificateBagCardTypeMap;
	}

}
