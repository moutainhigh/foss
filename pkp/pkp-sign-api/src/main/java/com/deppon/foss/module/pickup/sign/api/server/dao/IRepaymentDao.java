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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IRepaymentDao.java
 * 
 * FILE NAME        	: IRepaymentDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArriveDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;

/**
 * 付款接口
 * @author ibm-lizhiguo
 * @date 2012-11-16 下午3:44:02
 */
public interface IRepaymentDao {
	/**
	 * 
	 * 获得付款LIST--通过运单号
	 * @author ibm-lizhiguo
	 * @date 2012-11-16 下午3:46:38
	 */
	List<RepaymentEntity> searchRepaymentList(RepaymentEntity entity);
	/**
	 * 
	 * 通过ID获得付款详细
	 * @author ibm-lizhiguo
	 * @date 2012-11-16 下午3:46:43
	 */
	RepaymentEntity searchRepaymentById(String id);
	/**
	 * 
	 * 修改付款数据
	 * @author ibm-lizhiguo
	 * @date 2012-11-16 下午3:46:47
	 */
	int updateRepayment(RepaymentEntity record);
	
	/**
	 * 
	 * 返回待处理列表
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-20 下午3:49:50
	 */
	List<AirAgencyQueryDto> queryAirAgencyQueryDtoList(AirAgencyQueryDto airAgencyQueryDto);
	
	/**
	 * 
	 * 返回待处理列表
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-20 下午3:49:50
	 */
	List<AirAgencyQueryDto> queryAirAgencyQueryDtoListByReceiveOrg(AirAgencyQueryDto airAgencyQueryDto); 
	/**
	 * 
	 * 新增付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-26 下午2:57:30
	 */
	String addPaymentInfo(RepaymentEntity repaymentEntity);
	
	/**
	 * 
	 * 退款操作
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-27 下午2:30:42
	 */
	boolean refundPaymentInfo(RepaymentEntity repaymentEntity);
	
	/**
	 * 
	 * 获得未生成财务单据的付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-27 下午8:00:58
	 */
	List<RepaymentEntity> queryRepaymentList(RepaymentEntity entity);
	
    /**
     * 
     * 查询JOB所需付款信息
     * @author 043258-foss-zhaobin
     * @date 2012-11-30 上午10:38:28
     */
	List<RepaymentEntity> queryRepaymentListForJob(RepaymentEntity entity);
	
	/**
	 * 
	 * 更新job所需付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-3 下午7:27:39
	 */
	int  updateRepaymentListForJob(RepaymentEntity entity);
	
	/**
	 * 
	 * 根据运单号查询付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-24 下午2:45:59
	 */
	List<RepaymentArriveDto> queryRepaymentListbyNo(RepaymentEntity repayment);
	
	/**
	 * 
	 * 根据条件返回付款信息for变更所需
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-18 下午4:29:39
	 */
	List<RepaymentArriveDto> queryRepaymentListForSign(String waybillNo , String active , String stlbillGeneratedStatus , String stlbillNotStatus);
	
	/**
	 * 
	 * 是否存在付款信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-28 下午4:11:10
	 */
	boolean isExistRepayment(RepaymentEntity repayment);
	/**
	 * 根据运单号获取最后插入的 一条付款记录（查询付款方式，实付运费）
	 * @author foss-meiying
	 * @date 2013-2-21 下午2:28:26
	 * @param repayment
	 * @return
	 * @see
	 */
	RepaymentEntity queryRepaymentTypebyNo(RepaymentEntity repayment);
	
	/** 
	 * 返回快递待处理列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-12 上午11:45:16
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao#queryAirAgencyQueryDtoList(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	List<AirAgencyQueryDto> queryExpressAirAgencyQueryDtoList(AirAgencyQueryDto airAgencyQueryDto);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.sign.api.server.dao.IAirAgencySignDao.updatestorageCharge
	 * @Description:根据运单号更新运单的保管费
	 *
	 * @param dto
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-28 下午6:15:17
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-28    130376-YANGKANG      v1.0.0         create
	 */
	int updatestorageCharge(WaybillDto waydto,CurrentInfo currentInfo);
	
	/**
	 * 根据司机工号、运单号查询并判是否在派送任务中
	 * @author 309603 yangqiang
	 * @date 2016-3-3
	 * @param vehicleNo 车牌号
	 * 		  waybill 运单号
	 * @return
	 */
	String checkWaybill(Map<String, String> map);
	
	/**
	 * 根据司机工号、运单号查询并判是否在派送任务中
	 * @author 309603 yangqiang
	 * @date 2016-3-3
	 * @param vehicleNo 车牌号
	 * 		  waybill 运单号
	 * @return
	 */
	BigDecimal  getTotalAmount(String waybill);
}