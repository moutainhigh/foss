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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/CrmOrderHessianRemoting.java
 * 
 * FILE NAME        	: CrmOrderHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ICrmOrderHessianRemoting;

/**
 * 
 * CRM系统订单服务(Hessian)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-13 下午5:04:20,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-11-13 下午5:04:20
 * @since
 * @version
 */
@Remote
public class CrmOrderHessianRemoting implements ICrmOrderHessianRemoting {
    
    @Resource
    private ICrmOrderService crmOrderService;

    /**
     * 
     * <p>查询订单</p> 
     * @author foss-sunrui
     * @date 2012-11-13 下午5:04:56
     * @param query
     * @return 
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.ICrmOrderHessianRemoting#searchOrder(com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto)
     */
    @Override
    public CrmOrderInfoDto searchOrder(CrmOrderQueryDto query) {
    	return crmOrderService.searchOrder(query);
    }
    
    /**
     * 查询快递网上订单
     * @author 026123-foss-lifengteng
     * @date 2013-10-9 下午2:35:36
     */
    @Override
    public CrmOrderInfoDto searchExpressOrder(CrmOrderQueryDto query) {
    	return crmOrderService.searchExpressOrder(query);
    }

    /**
     * 
     * <p>订单导入</p> 
     * @author foss-sunrui
     * @date 2012-11-13 下午5:05:02
     * @param orderNumber
     * @return 
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.ICrmOrderHessianRemoting#importOrder(java.lang.String)
     */
    @Override
    public CrmOrderDetailDto importOrder(String orderNumber) {
	// TODO Auto-generated method stub
	return crmOrderService.importOrder(orderNumber);
    }
    
    /**
     * 
     * <p>优惠劵验证</p> 
     * @author foss-sunrui
     * @date 2012-11-16 下午1:47:14
     * @param couponInfo
     * @return 
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.ICrmOrderHessianRemoting#validateCoupon(com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto)
     */
    @Override
    public CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo) {
	// TODO Auto-generated method stub
	return crmOrderService.validateCoupon(couponInfo);
    }
    
    /**
     * 
     * <p>优惠券使用状态退回</p> 
     * @author foss-sunrui
     * @date 2012-11-16 下午1:47:17
     * @param couponNumber
     * @return 
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.ICrmOrderHessianRemoting#effectCouponState(java.lang.String)
     */
    @Override
    public boolean effectCouponState(String couponNumber) {
    	// TODO Auto-generated method stub
    	return crmOrderService.effectCouponState(couponNumber);
    }

    /**
     * 依据订单号查询订单类型
     */
	@Override
	public String queryServiceType(String orderNo) {
		return crmOrderService.searchServiceType(orderNo);
	}
    
}