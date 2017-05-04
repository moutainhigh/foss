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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillRfcDao.java
 * 
 * FILE NAME        	: IWaybillRfcDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeChargeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyWriteOffStatus;
import com.deppon.foss.module.pickup.waybill.shared.dto.TransportRecordDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNosDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto;
/**
 * 
 * 运单更改单数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-31 下午3:39:09, </p>
 * @author foss-sunrui
 * @date 2012-10-31 下午3:39:09
 * @since
 * @version
 */
public interface IWaybillRfcDao {

	/**
	 * 
	 * 查询运单签收结果
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:23:29
	 */
	String queryWaybillSignSituation(String waybillNo);

	/**
	 * 
	 * 通过原始运单id查询更改单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:21:56
	 */
	WaybillRfcEntity queryRfcEntityByWaybillId(String id, List<String> waybillRfcStatus);

	/**
	 * 
	 * 查询运单更改记录
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-30 下午2:27:03
	 */
	List<TransportRecordDto> queryWaybillRfcRecord(String waybillNo,List<String> rfcTypes);

	/**
	 * 通过运单号集合拿到待处理的更改单
	 * 
	 * @param WaybillFRcQueryByWaybillNosDto
	 * @return
	 */
	List<String> queryWaybillRfcByWaybillNos(WaybillFRcQueryByWaybillNosDto waybillFRcQueryByWaybillNosDto);

	/**
	 * 通过运单号来查询未受理更改单
	 * 
	 * @param waybillFRcQueryByWaybillNoDto
	 * @return
	 */
	String queryWaybillRfcByWaybillNo(WaybillFRcQueryByWaybillNoDto waybillFRcQueryByWaybillNoDto);

	/**
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 
	 * @param modifyBillWriteoffDto
	 * @return
	 */
	List<ModifyBillWriteoffResultDto> queryModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit);

	/**
	 * 
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号、产品集合）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * @author 025000-foss-helong
	 * @date 2013-7-26
	 */
	List<ModifyBillWriteoffResultDto> queryExpressModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit);

	
	
	/**
	 * 
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号、产品集合）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 备注：查询记录总条数
	 * @author 025000-foss-helong
	 * @date 2013-7-30
	 */
	long queryExpressModifyBillWriteoffResultCount(
			ModifyBillWriteoffDto modifyBillWriteoffDto,int start, int limit);
	
	
	/**
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 
	 * @param modifyBillWriteoffDto
	 * @return
	 */
	List<ModifyBillWriteoffResultDto> queryModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto);
	
	/**
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * @author 025000-foss-helong
	 * @date 2013-7-27
	 */
	List<ModifyBillWriteoffResultDto> queryExpressModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto);
	
	/**
	 * 更新核销状态
	 * 
	 * @param modifyWriteOffStatus
	 */
	void updateWriteOffStatus(ModifyWriteOffStatus modifyWriteOffStatus);

	/**
	 * 查询已经处理的更改单
	 * 
	 * @param WaybillRFcNos
	 * @return
	 */
	List<String> queryAlreadyHandleWaybillRfcNos(ModifyWriteOffStatus modifyWriteOffStatus);

	/**
	 * 查询以及处理的更改单
	 * 
	 * @param WaybillRFcNos
	 * @return
	 */
	List<String> queryNoHandleWaybillRfcNos(ModifyWriteOffStatus modifyWriteOffStatus);

	/**
	 * 更新在线更改单打印次数 +1
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int updateWaybillPrintTimes(String waybillID);

	/**
	 * 
	 * 新增更改单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:17:50
	 */
	void addWaybillRfcEntity(WaybillRfcEntity rfcEntity);

	/**
	 * 
	 * 新增更改明细
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:18:01
	 */
	void addRfcChangeDetailEntity(WaybillRfcChangeDetailEntity rfcChangeDetailEntitie);

	/**
	 * 
	 * 新增更改金额明细
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:18:18
	 */
	void addRfcChangeChargeEntity(WaybillRfcChangeChargeEntity rfcChangeChargeEntitie);

	/**
	 * 
	 * <p>
	 * 根据id查询更改单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param id
	 * @return WaybillRfcEntity
	 */
	WaybillRfcEntity selectByPrimaryKey(String id);

	/**
	 * 
	 * <p>
	 * 更新更改单实体<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcEntity
	 *            void
	 */
	void updateByPrimaryKeySelective(WaybillRfcEntity waybillRfcEntity);

	/**
	 * 
	 * 查询审核总条数
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-7 上午8:51:57
	 */
	long queryModifyBillWriteoffResultTotalNumber(ModifyBillWriteoffDto modifyBillWriteoffDto);

	/**
	 * 
	 * 查询其他费用明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-19 下午8:47:42
	 */
	List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo);
	
	/**
	 * 
	 * <p>是否自动受理</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-20 上午11:08:57
	 * @param parms
	 * @see
	 */
	 void updateIsLabourHandle(Map<String, Object> parms);
	
	/**
	 * 根据运单号查询运单出发更改单信息
	 * @param waybillNo
	 * @return
	 */
	List<LeaveChangeByWaybillNoResultDto> queryLeaveChangeByWaybillNo(String waybillNo);
	
	/**
	 * 
	 * 查询更改明细
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	List<WaybillRfcChangeDetailEntity> queryRfcChangeDetail(String waybillRfcId);
	
	/**
     * 
     * 根据Code查询价格DTO
     * @author 102246-foss-shaohongliang
     * @date 2013-1-22 下午2:27:16
     * @see com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcRemotingService#queryValueAddPriceByCode(java.lang.String)
     */
	ValueAddDto queryValueAddPriceByCode(String pricingCodeZz);
	
	/**
	 * 
	 * 官网更改单申请
	 * @author foss-gengzhe
	 * @date 2013-1-23 下午3:51:01
	 * @param waybillRfcForAccountServiceDto
	 * @return
	 * @see
	 */
	void applyChangeOrder(WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity);
	
	/**
	 * 
	 * 官网更改单查询
	 * @author foss-gengzhe
	 * @date 2013-1-23 下午3:51:07
	 * @param waybillRfcForAccountServiceCondition
	 * @return
	 * @see
	 */
	List<WaybillRfcForAccountServiceDto> queryChangeOrder(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition);

	/**
	 * 
	 * 分页查询更改单
	 * 
	 * @param waybillRfcForAccountServiceCondition
	 * @param start
	 * @param limit
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:11:21 AM
	 */
	List<WaybillRfcForAccountServiceDto> queryChangeOrderList(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition, int start, int limit);

	/**
	 * 
	 * 查询更改单的记录总数
	 * 
	 * @param waybillRfcForAccountServiceCondition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:11:55 AM
	 */
	Long queryChangeOrderCount(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition);

	/**
	 * 
	 * 更新更改单处理状态
	 * 
	 * @param waybillRfcForAccountServiceEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:12:17 AM
	 */
	int updateWaybillRfcIds(WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity);
	
	/**
     * 
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 102246-foss-shaohongliang
     * @date 2013-4-12 下午4:52:38
     */
	OuterBranchEntity queryAgencyBranchCompanyInfo(String agencyBranchCode);
	
	/**
	 * 
	 *  通过 标识编码查询
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 下午4:56:44
	 */
	SaleDepartmentEntity querySaleDepartmentByCode(String customerPickupOrgCode);
	
	
	
	/**
	 * 
	 * 查询运单第一条更改记录
	 * 
	 * @author 102246-foss-linwensheng
	 * @date 2012-11-30 下午2:27:03
	 */
	List<TransportRecordDto> queryFirstRecord(String waybillNo);

	/**
	 * @param oldVersionWaybillId
	 * @return
	 */
	List<WaybillRfcEntity> queryWaybillRfcEntityByNewVersionId(
			String oldVersionWaybillId);
	
	/**
	 * 通过营业部查询所属产品
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:57:58
	 */
	List<ProductEntity> queryBySalesDept(String salesDeptId,String productLevel);
	
	/**
	 * 根据到达部门编码查询所属产品
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:29:20
	 */
	List<ProductEntity> queryByArriveDeptProduct(String arriveDept, String productLevel);
	/**
	 * 通过单号查询所有更改信息
	 * @date 2013-11-19 8:47:07
	 * @author Foss-105888-Zhangxingwang
	 */
	List<WaybillRfcEntity> queryWaybillRfcAcceptByWaybillNo(String waybillNo, String status);

	/**
	 * 通过单号查询手动添加超重费
	 * @date 2014-01-09 8:47:07
	 * @author pgy
	 */
	List<WaybillOtherChargeDto> queryCZHCZFWFSDTJByNo(String waybillNo);

	/**
	 * 查询到达部门产品
	 * 
	 * @author 076234-FOSS-pgy
	 * @date 2014-3-5
	 */
	List<ProductEntity> searchByArriveDeptProduct(String salesDeptId,
			String productLevel);
	
	/**
	 * 根据卫星点部门编码获取所属营业部的负责人CODE
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-3-27 上午10:59:46
	 */
	String queryPrincipalNoBySatellite(String sateCode);

	void addWaybillRfcInfoNoId(WaybillRfcEntity waybillRfcEntity);
	
	
	/**
	 * 根据运单号List 查询这些运单号集合里面那些更改单申请未受理
	 * @param waybillNoList
	 * @return 未受理更改单对应的运单号集合
	 */
	List<String> queryUnActiveRfcWaybillNo(List<String> waybillNoList);

	/**
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	List<ExpBatchChangeWeightDto> queryExpBatchChangeWeightResultByCondition(
			ExpBatchChangeWeightQueryDto dto);
	

	/**
	 * 插入转运或返货目的站记录
	 * 
	 * @author foss-206860
	 */
	void addWaybillRfcTransferEntity(WaybillRfcTranferEntity rfcTranferEntity);

	List<WaybillRfcTranferEntity> queryWaybillRfcTransferEntity(
			WaybillRfcTranferEntity waybillRfcTranferEntity);

	int updateRfcTranferEntity(WaybillRfcTranferEntity rfcTranfer);

	List<WaybillRfcTranferEntity> queryRfcTransferHistory(
			WaybillRfcTranferEntity waybillRfcTranferEntity);

	int updateRfcTranferRfcId(WaybillRfcTranferEntity rfcTranfer);
	/**
	 * @项目：家装项目
	 * @功能：根据单号查询交货确认状态
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-10-17下午16:18
	 */
	String queryWayBillState(String waybillNo);
	/**
	 * 批量导入更改的结果
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-29 14:23:04
	 */
	int insertWaybillRfcChangeChargeBatch(List<WaybillRfcChangeChargeEntity> rfcChangeChargeList);
	
	List<WaybillRfcEntity> queryRecentRfc(String waybillNo);
	
	/**
     * 根据运单更改单id,查询更改单变更信息
     * @param waybillRfcId
     * @return
     * @author 311417 wangfeng
     * @date 2016-4-22
     */
	String queryRfcChangeItemsByWaybillRfcId(String waybillRfcId);
	
	/**
     * 查询是否可开装卸费
     * @author 354805
     * @date 2016-12-6 16:22:19
     * @param code 装卸费开单部门
     * @param billTime 开单时间
     * @return 是否可开装卸费
     */
    String queryCanPayServiceFeeByCodeAndTime(String code, Date billTime );
}