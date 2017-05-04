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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IRuleVerifyService.java
 * 
 * FILE NAME        	: IRuleVerifyService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * 
 * 运单业务规则效验服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Bobby,date:2012-10-16 上午11:32:07, </p>
 * @author Bobby
 * @date 2012-10-16 上午11:32:07
 * @since
 * @version
 */
public interface IRuleVerifyService extends IService {
    
    /**
     * 
     * <p>判断是否需要大车直送</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午4:28:28
     * @param weight
     * @param volume
     * @return
     * @see
     */
    boolean isVehicleDirect(String weight, String volume);
    
    /**
     * 
     * <p>通过物品的数量，体积，保价金额判断是否属于贵重物品</p> 
     * @author foss-sunrui
     * @date 2012-10-24 下午2:59:06
     * @param goodsNum 货物数量
     * @param goodsVolume 货物总体积（立方米）
     * @param goodsValue 货物总申明价值（元）
     * @return
     * @see
     */
    boolean isValueGoods(int goodsNum, String goodsVolume, String goodsValue);
    
    /**
     * 
     * <p>判断货物的重量体积比值是否在区间内</p> 
     * @author foss-sunrui
     * @date 2012-10-29 上午11:26:33
     * @param goodsWeight 货物总重量（千克）
     * @param goodsVolume 货物总体积（立方米）
     * @return
     * @see
     */
    boolean isWeightByVolume(String goodsWeight, String goodsVolume);
    
    /**
     * 
     * <p>判断单票货物或单件货物体积和重量是否在到达营业部允许的承运范围内</p> 
     * @author foss-sunrui
     * @date 2012-10-29 下午12:43:05
     * @param salesDeptId 营业部编码
     * @param goodsNum 货物数量
     * @param goodsWeight 货物总重量（千克）
     * @param goodsVolume 货物总体积（立方米）
     * @return
     * @see
     */
    ResultDto verifyGoodsByDept(String salesDeptId, int goodsNum, String goodsWeight, String goodsVolume);
    
}