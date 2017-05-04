/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPriceDownLoadService.java
 * 
 * FILE NAME        	: IPriceDownLoadService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.service.IService;

/**
 * 
 * 产品价格数据模型下载接口
 * IPriceDownLoadService
 * @author DP-Foss-YueHongJie
 * 2012-11-29 上午11:29:33
 * 
 * @version 1.0.0
 *
 */
public interface IPriceDownLoadService  extends IService{
	/**
	 * @Description: 折扣优先级(增量下载)
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2012-12-25 下午3:21:11
	 * @param clientInfo
	 * @return
	 * @version V1.0
	 */
    DataBundle downDiscountPriorityServerData(ClientUpdateDataPack clientInfo);
	
	/**
	 * @Description: 产品信息数据下载(增量下载)
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2012-12-25 下午3:21:11
	 * @param clientInfo
	 * @return
	 * @version V1.0
	 */
    DataBundle downProductServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 货物类型信息数据下载(增量下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downGoodsTypeServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 产品条目信息数据下载(增量下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downProductItemServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 时效区域信息下载(按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downEffectiveRegionServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 价格区域信息下载(按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPriceRegionServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 空运价格区域信息下载(按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPriceRegionAirServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 时效主方案信息下载(按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downEffectivePlanServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 时效方案详细下载(按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downEffectivePlanDetailServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 时效区域与部门对应关系信息下载(按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downEffectiveRegionOrgDetailServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 价格主方案信息下载(请按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPricePlanServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 空运价格主方案信息下载(请按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPriceAirPlanServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 价格区域与部门对应关系信息下载(请按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPriceRegionOrgDetailServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 空运价格区域与部门对应关系信息下载(请按照始发区域下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPriceRegionOrgAirDetailServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 价格计算表达式信息下载(增量下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPriceRuleServerData(ClientUpdateDataPack clientInfo);
    
    /**
     * @Description: 计价条目信息下载(增量下载)
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPriceEntity(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 市场活动信息下载（折扣活动），按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downMarketingEvent(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 市场活动渠道下载，按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downMarketingEventChannel(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 打折处理程序下载，按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downDiscountProgram(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 计价规则 运费信息下载，按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPricingValuationPricingServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 计价规则 空运 运费信息下载，按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPricingValuationPricingAirServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 计价规则  基础增值服务信息下载，按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPricingValuationBasicValueAddServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 计价规则  产品增值服务信息下载，按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPricingValuationProductValueAddServerData(ClientUpdateDataPack clientInfo);
    /**
     * @Description: 计价规则  区域增值服务信息下载，按照始发区域下载
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午3:21:28
     * @param clientInfo
     * @return
     * @version V1.0
     */
    DataBundle downPricingValuationRegionValueAddServerData(ClientUpdateDataPack clientInfo);
   
    /**
	 *  增值区域
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
    DataBundle downPriceRegionValueAddData(ClientUpdateDataPack clientInfo);
    
    /**
	 *  最低一票
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
    DataBundle downMinFeePlanServerData(ClientUpdateDataPack clientInfo);
    
    /**
	 *  偏线价格
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
    DataBundle downOuterPriceData(ClientUpdateDataPack clientInfo);
    
    /**
	 *  到达区域
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
    DataBundle downPriceRegionArriveData(ClientUpdateDataPack clientInfo);
    
    /**
	 *  下载到达区域和机构关联数据
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
    DataBundle downPriceRegionOrgArriveData(ClientUpdateDataPack clientInfo);
    
    /**
	 *  下载增值区域与机构关联数据
	  * Description: 下载离线数据 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:47:20
	  * @param clientInfo
	  * @return
	 */
    DataBundle downPriceRegionOrgValueAddData(ClientUpdateDataPack clientInfo);
}