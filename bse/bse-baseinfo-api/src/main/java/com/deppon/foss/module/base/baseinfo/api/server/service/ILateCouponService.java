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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPriceDiscountService.java
 * 
 * FILE NAME        	: IPriceDiscountService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;


import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity;

/**
 *  晚到补差价的service接口类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-7-20 下午7:32:39,content:TODO </p>
 * @author 232607 
 * @date 2015-7-20 下午7:32:39
 * @since
 * @version
 */
public interface ILateCouponService extends IService{

	
	
	
	/**
	 *  给接送货pkp的接口，返回当前唯一激活有效的方案信息，并且经过处理，
	 *  封装发货人大区和收货人大区下属的所有营业部及集中开单组存入实体
	 * @author 232607 
	 * @date 2015-7-20 下午7:33:55
	 * @return
	 * @see
	 */
	LateCouponEntity returnSchemeToPKP();

	/**
	 * 分页查询晚到补差价方案
	 * @author 232607 
	 * @date 2015-8-4 下午5:15:21
	 * @param lateCouponEntity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<LateCouponEntity> queryLateCouponByCodition(
			LateCouponEntity lateCouponEntity, int start, int limit);

	/**
	 * 计数——分页查询晚到补差价方案  
	 * @author 232607 
	 * @date 2015-8-4 下午5:15:44
	 * @param lateCouponEntity
	 * @return
	 * @see
	 */
	Long countLateCouponByCodition(LateCouponEntity lateCouponEntity);

	/**
	 * 新增晚到补差价方案 
	 * @author 232607 
	 * @date 2015-8-4 下午5:16:27
	 * @param lateCouponEntity
	 * @return
	 * @see
	 */
	LateCouponEntity addLateCoupon(LateCouponEntity lateCouponEntity);

	/**
	 * 作废晚到补差价方案 
	 * @author 232607 
	 * @date 2015-8-4 下午5:17:14
	 * @param lateCouponIds
	 * @see
	 */
	void deleteLateCoupon(List<String> lateCouponIds);

	/**
	 * 更新晚到补差价方案 
	 * @author 232607 
	 * @date 2015-8-4 下午5:17:31
	 * @param lateCouponEntity
	 * @see
	 */
	void updateLateCoupon(LateCouponEntity lateCouponEntity);

	/**
	 * <p>查重，只要存在相同的名字就算重复，重复则抛出异常</p> 
	 * @author 232607 
	 * @date 2015-8-4 下午5:17:53
	 * @param entity
	 * @see
	 */
	void queryRepeat(LateCouponEntity entity);

	/**
	 * <p>升级版本，实际为复制一个未激活方案，除ID外什么都不变，编码和名称也不变</p> 
	 * @author 232607 
	 * @date 2015-8-4 下午5:19:48
	 * @param id
	 * @see
	 */
	void copyLateCoupon(String id);

	/**
	 * 激活方案 
	 * @author 232607 
	 * @date 2015-8-4 下午5:19:59
	 * @param id
	 * @see
	 */
	void activateLateCoupon(String id);

	/**
	 * <p>中止方案</p> 
	 * @author 232607 
	 * @date 2015-8-4 下午5:20:11
	 * @param id
	 * @see
	 */
	void stopLateCoupon(String id);

	/**
	 * <p>给接送货pkp的接口，传入出发、到达营业部、产品编码，营业部向上查询事业部进行判断，
	 * 		  判断包含成功再返回其他判断条件给接送货进行判断，判断失败或无方案均返回null</p> 
	 * @author 232607 
	 * @date 2016-4-11 下午2:39:58
	 * @param startOrgcode
	 * @param destOrgcode
	 * @return
	 * @see
	 */
	LateCouponEntity returnSchemeToPKPByOrg(String startOrgcode,String destOrgcode,String productCode);

	LateCouponEntity findById(String id);
	
}