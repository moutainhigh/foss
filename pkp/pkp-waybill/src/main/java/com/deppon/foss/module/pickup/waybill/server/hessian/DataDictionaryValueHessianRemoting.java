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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/AddressService.java
 * 
 * FILE NAME        	: AddressService.java
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
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IDataDictionaryValueHessianRemoting;

/**
 * 数据字典在线查询
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-3-7 下午2:35:13,content:TODO </p>
 * @author 094463-foss-xieyantao
 * @date 2014-3-7 下午2:35:13
 * @since
 * @version
 */
@Remote
public class DataDictionaryValueHessianRemoting implements IDataDictionaryValueHessianRemoting {
	
	@Resource
	private IDataDictionaryValueDao pkpdataDictionaryValueDao;
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addDataDictionaryValue(
			DataDictionaryValueEntity dataDictionaryValue) {	 
		pkpdataDictionaryValueDao.addDataDictionaryValue(dataDictionaryValue);
	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updateDataDictionaryValue(
			DataDictionaryValueEntity dataDictionaryValue) {
		pkpdataDictionaryValueDao.updateDataDictionaryValue(dataDictionaryValue);
	}
	
	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(DataDictionaryValueEntity dataDictionaryValue) {
		if(!pkpdataDictionaryValueDao.addDataDictionaryValue(dataDictionaryValue)){
			pkpdataDictionaryValueDao.updateDataDictionaryValue(dataDictionaryValue);
		}
	}

	/**
     * <p>通过词条代码获取数据集合</p> 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:13:45
     * @param termsCode
     * @return
     * @see com.deppon.foss.module.pickup.creating.client.service.IDataDictionaryService#queryByTermsCode()
     */
    @Override
    public List<DataDictionaryValueEntity> queryByTermsCode(String termsCode) {
    	return pkpdataDictionaryValueDao.queryByTermsCode(termsCode);
    }
    
    /**
     * <p>查询对外备注</p> 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see com.deppon.foss.module.pickup.creating.client.service.IDataDictionaryService#queryOuterRemark()
     */
    @Override
    public List<DataDictionaryValueEntity> queryOuterRemark() {
	String termsCode = WaybillConstants.OUTER_REMARK;
	return queryByTermsCode(termsCode);
    }
    
    /**
     * <p>查询付款方式</p> 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see com.deppon.foss.module.pickup.creating.client.service.IDataDictionaryService#queryPaymentMode()
     */
    @Override
    public List<DataDictionaryValueEntity> queryPaymentMode() {
    	String termsCode = WaybillConstants.PAYMENT_MODE;
		return queryByTermsCode(termsCode);
    }
    
    /**
     * <p>查询退款类型</p> 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see com.deppon.foss.module.pickup.creating.client.service.IDataDictionaryService#queryRefundType()
     */
    @Override
    public List<DataDictionaryValueEntity> queryRefundType() {
    	String termsCode = WaybillConstants.REFUND_TYPE;
    	return queryByTermsCode(termsCode);
    }
    
    /**
     * <p>查询返单类型</p> 
     * @author foss-sunrui
     * @date 2012-10-15 上午11:07:33
     * @return
     * @see com.deppon.foss.module.pickup.creating.client.service.IDataDictionaryService#queryReturnBillType()
     */
    @Override
    public List<DataDictionaryValueEntity> queryReturnBillType() {
    	String termsCode = WaybillConstants.RETURN_BILL_TYPE;
    	return queryByTermsCode(termsCode);
    }
    
    /**
     * 
     * <p>查询计费方式</p> 
     * @author foss-sunrui
     * @date 2012-10-18 下午5:15:09
     * @return 
     * @see com.deppon.foss.module.pickup.creating.client.service.IDataDictionaryService#queryBillingWay()
     */
    @Override
    public List<DataDictionaryValueEntity> queryBillingWay() {
    	String termsCode = WaybillConstants.BILLING_WAY;
    	return queryByTermsCode(termsCode);
    }
    
    /**
     * 
     * 查询空运货物类型
     * @author 025000-FOSS-helong
     * @date 2013-1-4 下午04:25:54
     * @return
     */
    @Override
    public List<DataDictionaryValueEntity> queryAirGoodsType() {
    	String termsCode = WaybillConstants.AIR_GOODS_TYPE;
    	return queryByTermsCode(termsCode);
    }


    /**
     * 
     * （查询提货方式-空运）
     * @author 025000-FOSS-helong
     * @date 2012-10-22 下午04:10:06
     * @see com.deppon.foss.module.pickup.common.client.service.IDataDictionaryService#queryPickUpGoodsAir()
     */
    @Override
    public List<DataDictionaryValueEntity> queryPickUpGoodsAir() {
    	String termsCode = WaybillConstants.PICKUP_GOODS_AIR;
    	return queryByTermsCode(termsCode);
    }


    /**
     * 
     * 查询提货方式-汽运
     * @author 025000-FOSS-helong
     * @date 2012-10-22 下午04:10:15
     * @see com.deppon.foss.module.pickup.common.client.service.IDataDictionaryService#queryPickUpGoodsHighWays()
     */
    @Override
    public List<DataDictionaryValueEntity> queryPickUpGoodsHighWays() {
    	String termsCode = WaybillConstants.PICKUP_GOODS_HIGHWAYS;
    	return queryByTermsCode(termsCode);
    }

    /**
     * 
     * 查询预配航班
     * @author 025000-FOSS-helong
     * @date 2012-12-19 下午04:03:35
     */
	@Override
	public List<DataDictionaryValueEntity> queryPredictFlight() {
		String termsCode = WaybillConstants.AIR_FLIGHT_TYPE;
		return queryByTermsCode(termsCode);
	}
	
	/**
	 * 
	 * 查询合票方式
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午04:26:08
	 */
	public List<DataDictionaryValueEntity> queryFreightMethod() {
		String termsCode = WaybillConstants.MAKE_WAYBILL_WAY;
		return queryByTermsCode(termsCode);
	}

	/**
	 * 
	 * 根据code查询数据字典项
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午04:03:45
	 */
	@Override
	public DataDictionaryValueEntity queryDataDictoryValueByCode(String termsCode, String valueCode) {
		if("NIGHT_FLIGHT".equals(valueCode)){
			return pkpdataDictionaryValueDao.queryDataDictoryValueByValueCode(termsCode,valueCode);
		}else{			
			return pkpdataDictionaryValueDao.queryDataDictoryValueByCode(termsCode, valueCode);
		}
	}

	/**
	 * 
	 * 内部变更类型
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午04:03:55
	 */
	@Override
	public List<DataDictionaryValueEntity> queryInsideChangeType() {
		String termsCode = WaybillConstants.INSIDE_CHANGE;
		return queryByTermsCode(termsCode);
	}

	@Override
	public List<DataDictionaryValueEntity> queryCustomerChangeType() {
		String termsCode = WaybillConstants.CUSTOMER_CHANGE;
		return queryByTermsCode(termsCode);
	}

	@Override
	public List<DataDictionaryValueEntity> queryOrderType() {
		String termsCode = WaybillConstants.ORDER_TYPE;
		return queryByTermsCode(termsCode);
	}

	@Override
	public List<DataDictionaryValueEntity> queryOrderStatus() {
		String termsCode = WaybillConstants.ORDER_STATUS;
		return queryByTermsCode(termsCode);
	}
	
	/**
     * 
     * 运单状态PENDING
     * 
     * @author 105089-foss-yangtong
     * @date 2012-11-13 上午10:58:45
     */
	@Override
	public List<DataDictionaryValueEntity> queryPendingType() {
		String termsCode = WaybillConstants.PENDING_TYPE;
		return queryByTermsCode(termsCode);
	}
	
	/**
     * 
     * 运单状态OFFLINE
     * 
     * @author 105089-foss-yangtong
     * @date 2012-11-13 上午10:58:45
     */
	@Override
	public List<DataDictionaryValueEntity> queryOfflineActive() {
		String termsCode = WaybillConstants.OFFLINE_ACTIVE;
		return queryByTermsCode(termsCode);
	}

	@Override
	public List<DataDictionaryValueEntity> queryGoodsStatus() {
		String termsCode = WaybillRfcConstants.STOCK_STATUS;
		return queryByTermsCode(termsCode);
	}

	/**
	 * @param dataDictionaryValue
	 */
	public void delete(DataDictionaryValueEntity dataDictionaryValue) {
		pkpdataDictionaryValueDao.delete(dataDictionaryValue);
	}
	/**
	 * 查询内部发货类型
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	public List<DataDictionaryValueEntity> queryDeliveryMode() {
		String termsCode = WaybillConstants.INTERNAL_DELIVERY_TYPE;
		return queryByTermsCode(termsCode);
	}
	/**
	 * 查询客户变更原因类型
	 * dp-foss-dongjialing
	 * 225131
	 */
	@Override
	public List<DataDictionaryValueEntity> queryRfcCusReason() {
		String termsCode = WaybillConstants.RFC_REASON;
		return queryByTermsCode(termsCode);
	}
}