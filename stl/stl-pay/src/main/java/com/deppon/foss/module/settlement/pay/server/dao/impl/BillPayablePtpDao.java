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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/server/dao/BillPayablePtpDao.java
 * 
 * FILE NAME        	: BillPayablePtpDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillPayablePtpDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.BillPayInfoEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpAutoPayPFCREntity;

/**
 * 合伙人应付单dao
 * @author 302346-foss-jiang xun
 * @date 2016-01-29 上午09:16:00
 */
public class BillPayablePtpDao extends iBatis3DaoImpl implements IBillPayablePtpDao{
	
	private String NAMESPACE = "foss.stl.BillPayablePtpDao.";
	
	/**
	 * 根据运单号查询进入对账单的应付单数量
	 * @author 302346-foss-jiang xun
	 * @date 2016-01-29 上午09:16:00
	 */
	public int queryStatedBillPayableNumber(String wayBillNo){
		Object obj = this.getSqlSession().selectOne(NAMESPACE+"queryStatedBillPayableNumber",wayBillNo);
		if(obj!=null){
			return Integer.valueOf(obj.toString());
		}
		return 0;
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PtpAutoPayPFCREntity> queryPFCPBills(String billType,String active,Date fromDate,Date toDate){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("billType", billType);
		paramMap.put("active", active);
		paramMap.put("fromDate", fromDate);
		paramMap.put("toDate", toDate);
		
		return this.getSqlSession().selectList(NAMESPACE+"queryPFCPBills", paramMap);
	}
	
	/**
     * 根据应付单号，批量更新合伙人到付运费应付单
     * @author foss-Jiang Xun
     * @date 2016-05-20 下午04:20:00
     * @param billType	单据子类型
	 * @param payableNos	应付单号集合
	 * @return 
     */
	public int updateBatchPFCPBills(PtpAutoPayPFCREntity payableEntity){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("paymentNo", payableEntity.getPaymentNo());
		paramMap.put("billType", payableEntity.getBillType());
		paramMap.put("payableNoList", payableEntity.getPayableNoList());
		paramMap.put("modifyUserCode", "SYSTEM");
		paramMap.put("modifyUserName","SYSTEM");
		paramMap.put("paymentNotes",payableEntity.getPaymentNotes());
		
		return this.getSqlSession().update(NAMESPACE+"updateBatchPFCPBills", paramMap);
    }
	
	 /**
     *  根据付款单号集合,查询应付单和付款单信息 
     * @author foss-Jiang Xun
     * @date 2016-06-02 上午09:08:00
     * @return 应付单和付款单信息 集合
     */
    @SuppressWarnings("unchecked")
	public List<BillPayInfoEntity> queryPayInfoByPayableNos(List<String> paymentNos){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("paymentNos", paymentNos);
		
		return this.getSqlSession().selectList(NAMESPACE+"queryPayInfoByPayableNos", paramMap);
    }

    /**
     * ptp监控查询应付单各单据的总记录数和总金额数
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<StlBillDetailDto> countPayableBills(
			BillingQueryRequestDto requestDto) {
		return this.getSqlSession().selectList(NAMESPACE + "countPayableBills", requestDto);
	}
	/**
     * 查询委托派费
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<PtpAutoPayPFCREntity> queryPDDFBills(String billType,
			String active, Date fromDate, Date toDate) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("billType", billType);
		map.put("active", active);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);
		return this.getSqlSession().selectList(NAMESPACE+"queryPDDFBills", map);
	}
	
	/**
     * 查询奖励差错应付单
     * @author 355019
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<PtpAutoPayPFCREntity> queryPBPLEBills(String billType,
			String active, Date fromDate, Date toDate) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("billType", billType);
		map.put("active", active);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);
		return this.getSqlSession().selectList(NAMESPACE+"queryPBPLEBills", map);
	}
	
}
