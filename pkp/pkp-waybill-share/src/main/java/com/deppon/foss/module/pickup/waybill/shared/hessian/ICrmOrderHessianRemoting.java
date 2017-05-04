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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/ICrmOrderHessianRemoting.java
 * 
 * FILE NAME        	: ICrmOrderHessianRemoting.java
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
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto;

/**
 * 
 * CRM系统订单服务接口(Hessian)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-13 下午5:01:41, </p>
 * @author foss-sunrui
 * @date 2012-11-13 下午5:01:41
 * @since
 * @version
 */
public interface ICrmOrderHessianRemoting extends IHessianService {

    /**
     * 
     * <p>查询订单</p> 
     * @author foss-sunrui
     * @date 2012-11-13 下午5:02:26
     * @param query
     * @return
     * @see
     */
    CrmOrderInfoDto searchOrder(CrmOrderQueryDto query);
    
    /**
     * 
     * <p>订单导入</p> 
     * @author foss-sunrui
     * @date 2012-11-13 下午5:02:41
     * @param orderNumber
     * @return
     * @see
     */
   CrmOrderDetailDto importOrder(String orderNumber);
    
    /**
     * 
     * <p>优惠劵验证</p> 
     * @author foss-sunrui
     * @date 2012-11-15 下午4:47:50
     * @param couponInfo
     * @return
     * @see
     */
    CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo);
    
    /**
     * 
     * <p>优惠券使用状态退回</p> 
     * @author foss-sunrui
     * @date 2012-11-15 下午5:52:54
     * @param couponNumber
     * @return
     * @see
     */
    boolean effectCouponState(String couponNumber);

    /**
     * 查询快递网上订单
     * @author 026123-foss-lifengteng
     * @date 2013-10-9 下午2:35:36
     */
	CrmOrderInfoDto searchExpressOrder(CrmOrderQueryDto query);

	/**
	 * 依据订单号查询订单类型
	 * @param orderNo
	 * @return
	 */
	String queryServiceType(String orderNo);
    
}