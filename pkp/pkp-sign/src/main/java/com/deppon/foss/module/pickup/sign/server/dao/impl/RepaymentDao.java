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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/RepaymentDao.java
 * 
 * FILE NAME        	: RepaymentDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArriveDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 *
 * 付款操作
 * @author 043258-
 *				 foss-zhaobin
 * @date 2013-3-12 
 *				 上午10:25:14
 * @since
 * @version
 */
public class RepaymentDao extends iBatis3DaoImpl implements IRepaymentDao{
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity.";
	/**
	 * 获得付款LIST
	 */
	private static final String REPAYMENTLIST = "selectRepaymentList";
	/**
	 * 获得未生成财务单据的付款信息
	 */
	private static final String QUERYREPAYMENTLIST = "queryRepaymentList";
	/**
	 * 获得付款详细
	 */
	private static final String REPAYMENT = "selectRepaymentById";
	/**
	 * 更新付款
	 */
	private static final String UPDATEREPAYMENT = "updateRepaymentById";
	/**
	 * 根据dtop获得待处理列表
	 */
	private static final String QUERYPENDINGLIST = "queryPendingList";
	/**
	 * 根据dtop获得待处理列表
	 */
	private static final String QUERYPENDINGLISTBYRECEIVEORG = "queryPendingListByReceiveOrg";
	/**
	 * 新增付款信息
	 */
	private static final String ADDREPAYMENTINFO = "addRepaymentInfo";
	/**
	 * 退款业务
	 */
	private static final String REFUNDREPAYMENTINFO = "refundRepaymentInfo";
	/**
	 * 查询job所需付款信息
	 */
	private static final String QUERYREPAYMENTLISTFORJOB = "queryRepaymentListForJob";
	/**
	 * 更新job所需付款信息
	 */
	private static final String UPDATEREPAYMENTLISTFORJOB = "updateRepaymentListForJob";
	/**
	 * 根据运单号获取付款信息
	 */
	private static final String QUERYREPAYMENTLISTBYNO = "queryRepaymentListbyNo";
	/**
	 * 根据条件获取付款信息
	 */
	private static final String QUERYREPAYMENTLISTFORSIGN = "queryRepaymentListForSign";
	/**
	 * 根据运单信息更新运单保管费
	 */
	private static final String UPDATESTORAGECHARGE ="updatestorageCharge";
	/**
	 * 插入运单保管费更新的记录
	 */
	private static final String INSERTUPDATESTORAGECHARGEHISTORY ="insertUpdateStoregeChargeHistory";
	/**
	 * 
	 * 查询付款记录
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-12-4
	 * 		 下午2:36:43
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#searchRepaymentList
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentEntity> searchRepaymentList(RepaymentEntity entity) {
		return getSqlSession().selectList(NAMESPACE + REPAYMENTLIST, entity);
	}
	
	/**
	 * 
	 * 根据id查询付款信息 
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2013-3-12 
	 * 		上午10:26:05
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#searchRepaymentById
	 * (java.lang.String)
	 */
	@Override
	public RepaymentEntity searchRepaymentById(String id) {
		return  (RepaymentEntity)getSqlSession().selectOne(NAMESPACE+REPAYMENT, id);
	}
	
	/**
	 * 
	 * 根据id修改付款信息
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-30
	 * 		 上午9:54:36
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#updateRepayment
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@Override
	public int updateRepayment(RepaymentEntity record) {
		return getSqlSession().update(NAMESPACE+UPDATEREPAYMENT, record);
	}

	/**
	 * 
	 * 返回待处理列表
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-20
	 * 		 下午3:49:50
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirAgencyQueryDto> queryAirAgencyQueryDtoList(AirAgencyQueryDto airAgencyQueryDto) 
	{
		return getSqlSession().selectList(NAMESPACE + QUERYPENDINGLIST, airAgencyQueryDto);
	}
	
	/** 
	 * 返回快递待处理列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-12 上午11:45:16
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#queryAirAgencyQueryDtoList(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirAgencyQueryDto> queryExpressAirAgencyQueryDtoList(AirAgencyQueryDto airAgencyQueryDto) 
	{
		return getSqlSession().selectList(NAMESPACE + "queryExpressPendingList", airAgencyQueryDto);
	}
	
	/**
	 * 
	 * 返回待处理列表
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-20
	 * 		 下午3:49:50
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirAgencyQueryDto> queryAirAgencyQueryDtoListByReceiveOrg(AirAgencyQueryDto airAgencyQueryDto) 
	{
		return getSqlSession().selectList(NAMESPACE + QUERYPENDINGLISTBYRECEIVEORG, airAgencyQueryDto);
	}
	
	/**
	 * 
	 * 新增付款信息
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-26
	 * 		 下午2:59:03
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#addPaymentInfo
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@Override
	public String addPaymentInfo(RepaymentEntity repaymentEntity) 
	{
		String uuid = UUIDUtils.getUUID();
		repaymentEntity.setId(uuid) ;
		getSqlSession().insert(NAMESPACE + ADDREPAYMENTINFO, repaymentEntity);
		return uuid;

	}

	/**
	 * 
	 * 退款操作
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-27
	 * 		 下午2:30:42
	 */
	@Override
	public boolean refundPaymentInfo(RepaymentEntity repaymentEntity) 
	{
		return this.getSqlSession().update(
				NAMESPACE + REFUNDREPAYMENTINFO, repaymentEntity) > 0 ? true : false;
	}

	/**
	 * 
	 * 获得未生成财务单据的付款信息
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-27
	 * 		 下午8:00:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentEntity> queryRepaymentList(RepaymentEntity entity)
	{
		return getSqlSession().selectList(NAMESPACE + QUERYREPAYMENTLIST, entity);
	}

	/**
	 * 
	 * 查询JOB所需付款信息
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-11-30 
	 * 		上午10:39:26
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#queryRepaymentListForJob()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentEntity> queryRepaymentListForJob(RepaymentEntity entity) 
	{
		return getSqlSession().selectList(NAMESPACE + QUERYREPAYMENTLISTFORJOB,entity);
	}

	/**
	 * 
	 * 更新job所需付款信息
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-12-3 
	 * 		下午7:27:39
	 */
	@Override
	public int updateRepaymentListForJob(RepaymentEntity entity) 
	{
		return this.getSqlSession().update(NAMESPACE + UPDATEREPAYMENTLISTFORJOB,entity);
	}
	
	/**
	 * 
	 * 根据运单号查询付款信息
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2012-12-24
	 * 		 下午2:45:59
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentArriveDto> queryRepaymentListbyNo(RepaymentEntity repayment)
	{
		return getSqlSession().selectList(NAMESPACE + QUERYREPAYMENTLISTBYNO,repayment);
	}

	/**
	 * 
	 * 根据查询条件返回付款信息
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2013-1-18 
	 * 		下午4:31:25
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#queryRepaymentListForSign
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentArriveDto> queryRepaymentListForSign(String waybillNo, String active, String stlbillGeneratedStatus, String stlbillNotStatus)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.ACTIVE);
		map.put("stlbillGeneratedStatus", stlbillGeneratedStatus);
		map.put("stlbillNotStatus", stlbillNotStatus);
		return getSqlSession().selectList(NAMESPACE + QUERYREPAYMENTLISTFORSIGN,map);
	}
	
	/**
	 * 
	 * 是否存在付款信息
	 * @author 043258-
	 * 		foss-zhaobin
	 * @date 2013-1-28 
	 * 		下午4:11:10
	 */
	@Override
	public boolean isExistRepayment(RepaymentEntity repayment)
	{
		return getSqlSession().selectList(NAMESPACE + QUERYREPAYMENTLISTBYNO,repayment).size()>0 ? true :false;
	}
	/**
	 * 根据运单号获取最后插入的 一条付款记录（查询付款方式，实付运费）
	 * @author foss-
	 * 		meiying
	 * @date 2013-2-21
	 * 		 下午2:29:56
	 * @param repayment
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#queryRepaymentTypebyNo
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity)
	 */
	@Override
	public RepaymentEntity queryRepaymentTypebyNo(RepaymentEntity repayment) {
		return (RepaymentEntity)this.getSqlSession().selectOne(NAMESPACE+"queryRepaymentTypebyNo",repayment);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.server.service.impl.RepaymentService.updatestorageCharge
	 * @Description:根据运单号更新运单的保管费
	 *
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-28 下午6:09:36
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-28    130376-YANGKANG      v1.0.0         create
	 */
	@Override
	public int updatestorageCharge(WaybillDto waydto,CurrentInfo currentInfo) {
		RepaymentEntity  repayment =new RepaymentEntity();
		repayment.setWaybillNo(waydto.getWaybillNo());
		//修改后保管费
		if(null != waydto.getStorageCharge()){
			repayment.setStorageFee(BigDecimal.valueOf(SettlementReportNumber.ONE_HUNDRED).multiply(waydto.getStorageCharge()));
		}else{
			repayment.setStorageFee(BigDecimal.ZERO);
		}
		//修改前保管费
		if(null != waydto.getStorageChargeOld()){
			repayment.setStorageFeeOld(BigDecimal.valueOf(SettlementReportNumber.ONE_HUNDRED).multiply(waydto.getStorageChargeOld()));
		}else{
			repayment.setStorageFeeOld(BigDecimal.ZERO);
		}		
		//更新运单保管费的同时插入保管费更改历史记录
		repayment.setId(UUIDUtils.getUUID());
		repayment.setOperator(currentInfo.getEmpName());
		repayment.setOperatorCode(currentInfo.getEmpCode());
		repayment.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		repayment.setOperateOrgName(currentInfo.getCurrentDeptName());
		repayment.setModifyTime(new Date());
		repayment.setUpdateStorageChargeReason(waydto.getUpdateStorageChargeReason());
		this.getSqlSession().insert(NAMESPACE+INSERTUPDATESTORAGECHARGEHISTORY, repayment);
	  return  this.getSqlSession().update(NAMESPACE+UPDATESTORAGECHARGE, repayment);
		 
		
		
	}
	/**
	 * 根据司机编号，车牌号,验证运单是否已下拉
	 * @author 309603 yangqiang
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String checkWaybill(Map<String, String> map) {
		List<String> list = this.getSqlSession().selectList(NAMESPACE + "checkWaybill", map);
		return list.isEmpty()? null:list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getTotalAmount(String waybill) {
		List<BigDecimal> list = this.getSqlSession().selectList(NAMESPACE+"getTotalAmount", waybill);
		return list.isEmpty()? null:list.get(0);
	}
	
}