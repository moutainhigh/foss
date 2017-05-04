/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/MessageType.java
 * 
 * FILE NAME        	: MessageType.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:操作提示类型类</small></b> </br> 
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> 
 * <b style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%">
 * 1 2012-11-05 张斌    新增
 * </div>
 ******************************************** 
 */
public class MessageType {

	public static final String SAVE_CHONGFU = "foss.baseinfo.saveChongfu";
	/**
	 * 保存成功
	 */
	public static final String SAVE_SUCCESS = "foss.baseinfo.saveSuccess";
	
	/**
	 * 生效成功
	 */
	public static final String VALID_SUCCESS = "foss.baseinfo.validSuccess";
	
	/**
	 * 失效成功
	 */
	public static final String INVALID_SUCCESS = "foss.baseinfo.invalidSuccess";
	
	/**
	 * 导入成功
	 */
	public static final String IMPORT_SUCCESS = "foss.baseinfo.importSuccess";
	
	/**
	 * 作废失败
	 */
	public static final String DELETE_FAILURE = "foss.baseinfo.deleteFailure";
	/**
	 * 新增线路成功
	 */
	public static final String SAVE_LINE_SUCCESS = "foss.baseinfo.saveLineSuccess";
	
	/**
	 * 新增线段成功
	 */
	public static final String SAVE_LINEITEM_SUCCESS = "foss.baseinfo.saveLineItemSuccess";
	
	/**
	 * 新增发车标准成功
	 */
	public static final String SAVE_DEPARTURESTANDAR_SUCCESS = "foss.baseinfo.saveDepartureStandarSuccess";
	
	/**
	 * 新增站点成功
	 */
	public static final String SAVE_SITEGROUPSITE_SUCCESS = "foss.baseinfo.saveSiteGroupSiteSuccess";
	
	/**
	 * 新增月台成功
	 */
	public static final String SAVE_PLATFORM_SUCCESS = "foss.baseinfo.savePlatformSuccess";
	/**
	 * 新增库位成功
	 */
	public static final String SAVE_STORAGE_SUCCESS = "foss.baseinfo.saveStorageSuccess";
	/**
	 * 新增库区成功
	 */
	public static final String SAVE_GOODSAREA_SUCCESS = "foss.baseinfo.saveGoodsAreaSuccess";
	/**
	 * 新增航空公司成功
	 */
	public static final String SAVE_AIRLINES_SUCCESS = "foss.baseinfo.saveAirlinesSuccess";
	/**
	 * 新增航空公司账户成功
	 */
	public static final String SAVE_AIRLINESACCOUNT_SUCCESS= "foss.baseinfo.saveAirlinesAccountSuccess";
	/**
	 * 新增航空公司代理人成功
	 */
	public static final String SAVE_AIRLINESAGENT_SUCCESS = "foss.baseinfo.saveAirlinesAgentSuccess";
	/**
	 * 修改航空公司成功
	 */
	public static final String UPDATE_AIRLINES_SUCCESS = "foss.baseinfo.updateAirlinesSuccess";
	/**
	 * 修改航空公司账户成功
	 */
	public static final String UPDATE_AIRLINESACCOUNT_SUCCESS= "foss.baseinfo.updateAirlinesAccountSuccess";
	/**
	 * 修改航空公司代理人成功
	 */
	public static final String UPDATE_AIRLINESAGENT_SUCCESS = "foss.baseinfo.updateAirlinesAgentSuccess";
	/**
	 * 删除航空公司成功
	 */
	public static final String DELETE_AIRLINES_SUCCESS = "foss.baseinfo.deleteAirlinesSuccess";
	/**
	 * 删除航空公司账户成功
	 */
	public static final String DELETE_AIRLINESACCOUNT_SUCCESS= "foss.baseinfo.deleteAirlinesAccountSuccess";
	/**
	 * 删除航空公司代理人成功
	 */
	public static final String DELETE_AIRLINESAGENT_SUCCESS = "foss.baseinfo.seleteAirlinesAgentSuccess";
	/**
	 * 新增站点组成功
	 */
	public static final String SAVE_SITEGROUP_SUCCESS = "foss.baseinfo.saveSiteGroupSuccess";
	
	/**
	 * 激活成功
	 */
	public static final String ACTIVE_SUCCESS = "foss.baseinfo.activeSuccess";


	/**
	 * 作废成功
	 */
	public static final String DELETE_SUCCESS = "foss.baseinfo.deleteSuccess";

	/**
	 * 作废线路成功
	 */
	public static final String DELETE_LINE_SUCCESS = "foss.baseinfo.deleteLineSuccess";
	/**
	 * 作废月台成功
	 */
	public static final String DELETE_PLATFORM_SUCCESS = "foss.baseinfo.deletePlatformSuccess";
	/**
	 * 作废库位成功
	 */
	public static final String DELETE_STORAGE_SUCCESS = "foss.baseinfo.deleteStorageSuccess";
	/**
	 * 作废库区成功
	 */
	public static final String DELETE_GOODSAREA_SUCCESS = "foss.baseinfo.deleteGoodsAreaSuccess";
	
	/**
	 * 作废线段成功
	 */
	public static final String DELETE_LINEITEM_SUCCESS = "foss.baseinfo.deleteLineItemSuccess";
	
	/**
	 * 作废发车标准成功
	 */
	public static final String DELETE_DEPARTURESTANDAR_SUCCESS = "foss.baseinfo.deleteDepartureStandarSuccess";

	/**
	 * 更新成功
	 */
	public static final String UPDATE_SUCCESS = "foss.baseinfo.updateSuccess";
	
	/**
	 * 更新线段成功
	 */
	public static final String UPDATE_LINEITEM_SUCCESS = "foss.baseinfo.updateLineItemSuccess";
	
	/**
	 * 更新线路成功
	 */
	public static final String UPDATE_LINE_SUCCESS = "foss.baseinfo.updateLineSuccess";
	
	/**
	 * 更新发车标准成功
	 */
	public static final String UPDATE_DEPARTURESTANDAR_SUCCESS = "foss.baseinfo.updateDepartureStandarSuccess";
	
	/**
	 * 作废站点组成功
	 */
	public static final String DELETE_SITEGROUP_SUCCESS = "foss.baseinfo.deleteSiteGroupSuccess";
	
	/**
	 * 作废站点成功
	 */
	public static final String DELETE_SITEGROUPSITE_SUCCESS = "foss.baseinfo.deleteSiteGroupSiteSuccess";
	
	/**
	 * 修改站点组成功
	 */
	public static final String UPDATE_SITEGROUP_SUCCESS = "foss.baseinfo.updateSiteGroupSuccess";
	
	/**
	 * 修改站点成功
	 */
	public static final String UPDATE_SITEGROUPSITE_SUCCESS = "foss.baseinfo.updateSiteGroupSiteSuccess";
	/**
	 * 修改月台成功
	 */
	public static final String UPDATE_PLATFORM_SUCCESS = "foss.baseinfo.updatePlatformSuccess";
	/**
	 * 修改库位成功
	 */
	public static final String UPDATE_STORAGE_SUCCESS = "foss.baseinfo.updateStorageSuccess";
	/**
	 * 修改库区成功
	 */
	public static final String UPDAE_GOODSAREA_SUCCESS = "foss.baseinfo.updateGoodsAreaSuccess";
	/**
	 * 保存用户数据权限成功
	 */
	public static final String SAVE_USERDEPTS_SUCCESS = "foss.baseinfo.saveUserDeptsSuccess";
	/**
	 * 保存用户数据权限失败
	 */
	public static final String SAVE_USERDEPTS_FAIL = "foss.baseinfo.saveUserDeptsFail";
	
	/**
	 * 新增快递打印星标成功
	 */
	public static final String SAVE_EXPRESSPRINTSTAR_SUCCESS = "foss.baseinfo.saveExpressPrintStarSuccess";
	
	/**
	 * 作废快递打印星标成功
	 */
	public static final String DELETE_EXPRESSPRINTSTAR_SUCCESS = "foss.baseinfo.deleteExpressPrintStarSuccess";
	
	/**
	 * 修改快递打印星标成功
	 */
	public static final String UPDATE_EXPRESSPRINTSTAR_SUCCESS = "foss.baseinfo.updateExpressPrintStarSuccess";
	
	/**
	 * 升级版本成功
	 */
	public static final String COPY_PRICECOUPON_SUCCESS = "foss.baseinfo.copyPriceCouponSuccess";
	
	/**
	 * 中止成功
	 */
	public static final String STOP_SUCCESS = "foss.baseinfo.stopSuccess";
		/**
	 * 快递派送区域电子地图审核成功
	 */
	public static final String VERIFY_EXPRESS_DELIVERY_MAP_MANAGE_SUCCESS = "foss.baseinfo.verifyExpressDeliveryMapManageSuccess";
	
	/**
	 * 快递派送区域电子地图退回成功
	 */
	public static final String TURNBACK_EXPRESS_DELIVERY_MAP_MANAGE_SUCCESS = "foss.baseinfo.turnBackExpressDeliveryMapManageSuccess";
	
	/**
	 * 开启成功
	 * @author 218392 zhangyongxue
	 * @date 2015-05-15 16:50:50
	 */
	public static final String OPEN_SUCCESS = "foss.baseinfo.openSuccess";
	
	/**
	 * 关闭成功
	 * @author 218392 zhangyongxue
	 * @date 2015-05-15 16:50:50
	 */
	public static final String CLOSE_SUCCESS = "foss.baseinfo.closeSuccess";	
	
	/**
	 * 静默成功
	 */
	public static final String SILENT_SUCCESS = "foss.baseinfo.silentSuccess";
	
	/**
	 * 密码应包含字母和数字且长度为6-16位！
	 */
	public static final String PW_SAMECHAR_ERROR = "foss.login.passwordSamecharError";
	
	/**
	 * 新密码不能和原来密码一样
	 */
	public static final String PW_NOT_SAME = "foss.login.passwordNotSame";
	

}
