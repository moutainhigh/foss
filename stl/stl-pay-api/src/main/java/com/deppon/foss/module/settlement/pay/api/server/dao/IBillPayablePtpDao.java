/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-pay-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/api/server/dao/IBillPayablePtpDao.java
 * 
 * FILE NAME        	: IBillPayablePtpDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.pay.api.shared.domain.BillPayInfoEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpAutoPayPFCREntity;

/**
 * 合伙人应付单dao
 * @author 302346-foss-jiang xun
 * @date 2016-01-29 上午09:09:00
 */
public interface IBillPayablePtpDao {
	/**
	 * 根据运单号查询进入对账单的应付单数量
	 * @author 302346-foss-jiang xun
	 * @date 2016-01-29 上午09:09:00
	 */
	int queryStatedBillPayableNumber(String wayBillNo);
	
	/**
	 *  查询合伙人到付运费应付单
	 * @author 302346-foss-jiang xun
	 * @date 2016-05-18 下午04:19:00
	 * @param billType	单据子类型
	 * @param active	是否有效
	 * @param fromDate	开始日期
	 * @param toDate	结束日期
	 * @return 应付单集合
	 */
	public List<PtpAutoPayPFCREntity> queryPFCPBills(String billType,String active,Date fromDate,Date toDate);
	/**
	 *  查询合伙人委托派费应付单
	 * @author yanjunjie
	 * @param billType	单据子类型
	 * @param active	是否有效
	 * @param fromDate	开始日期
	 * @param toDate	结束日期
	 * @return 应付单集合
	 */
	public List<PtpAutoPayPFCREntity> queryPDDFBills(String billType,String active,Date fromDate,Date toDate);
	
	/**
	 *  查询合伙人奖励差错应付单
	 * @author 355019
	 * @param billType	单据子类型
	 * @param active	是否有效
	 * @param fromDate	开始日期
	 * @param toDate	结束日期
	 * @return 应付单集合
	 */
	public List<PtpAutoPayPFCREntity> queryPBPLEBills(String billType,String active,Date fromDate,Date toDate);
	/**
     *  根据应付单号，批量更新合伙人到付运费应付单
     * @author foss-Jiang Xun
     * @date 2016-05-20 下午04:07:00
     * @param PtpAutoPayPFCREntity	
	 * @return 修改的记录数
     */
    public int updateBatchPFCPBills(PtpAutoPayPFCREntity payableEntity);
    
    /**
     *  根据付款单号集合,查询应付单和付款单信息 
     * @author foss-Jiang Xun
     * @date 2016-06-02 上午09:08:00
     * @return 应付单和付款单信息 集合
     */
    public List<BillPayInfoEntity> queryPayInfoByPayableNos(List<String> paymentNos);

    /**
     * ptp监控查询应付单各单据的总记录数和总金额数
     * 
     * @author gpz
     * @date 2016年8月6日
     * @param 
     */
	List<StlBillDetailDto> countPayableBills(BillingQueryRequestDto requestDto);
	
}
