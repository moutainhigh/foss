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
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillPayCODOnlineService.java
 * 
 * FILE NAME        	: BillPayCODOnlineService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillPayCODOnlineDao;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODMergeEntityDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineSendService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICODBatchService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODMergeEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayableQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODBatchDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODMergeDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 线上汇代收货款给客户服务.
 * 
 * @author dp-zengbinwen
 * @date 2012-10-22 上午11:33:49
 */
public class BillPayCODOnlineService implements IBillPayCODOnlineService {

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(BillPayCODOnlineService.class);

	/** 代收货款服务. */
	private ICodCommonService codCommonService;

	/** 付款单服务. */
	private IBillPaymentService billPaymentService;

	/** 结算公共服务. */
	private ISettlementCommonService settlementCommonService;

	/** 将代收货款数据发送给银企. */
	private IBillPayCODOnlineSendService billPayCODOnlineSendService;

	/** 代收货款批次号服务. */
	private ICODBatchService codBatchService;

	/** 应付单服务. */
	private IBillPayableService billPayableService;
	
	/** 线上汇代收货款给客户服务. */
	private IBillPayCODOnlineService billPayCODOnlineService;
	
	/** 代收合并DAO */
	private ICODMergeEntityDao codMergeEntityDao;

	/** 代收货款批量处理DAO */
	private IBillPayCODOnlineDao billPayCODOnlineDao;

	/**
	 * 准备符合条件的全部代收货款做线上汇代收货款服务前，更改代收货款状态
	 * 
	 * 把codPayableDtoList修改退款中，及返回billPayableEntityList
	 * @param codBatchNumberStr
	 * @param codPayableDtoList
	 * @param mergeCodPayableDtoList
	 * @param currentInfo
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW) 
	@Override
	public void readySendBillPaybleToBankAll(String codBatchNumberStr,
											final List<CODPayableDto> codPayableDtoList,
											List<CODMergeDto> mergeCodPayableDtoList,
											List<String> codTypes,
											CurrentInfo currentInfo) {
		// 创建批次号实体
		codBatchService.createCODBatchEntity(currentInfo, codBatchNumberStr,SettlementDictionaryConstants.COD_BATCH__STATUS__SENDING);
		
		/**
		 * @author 218392  zhangyongxue 2015-08-29 16:26:39
		 * 这里加上个判断给isPackage根据是否打包退设置值
		 */
		String isPackage = "";//默认不含有打包退,默认是空
		if(codTypes != null){
			for(String codType : codTypes){
				if(StringUtils.equals("PACK", codType)){
					isPackage = "Y";//如果含有打包退的时候，就将 N 变成 Y
				}
			}
		}

		if(CollectionUtils.isNotEmpty(codPayableDtoList)){
			
			// 循环验证代收集合是否是经营部冻结
			this.checkCodPayableDtoListIsFreeze(codPayableDtoList);
			
			// 批量更新代收退款中状态
			CODDto codDto = new CODDto(); 
			Date now = new Date();
			this.initializaApplyRefundCOD(codBatchNumberStr, currentInfo,codDto,now);
			this.updateCodReturningByBatch(codDto, codPayableDtoList, currentInfo);
			
			for (CODPayableDto codPayableDto : codPayableDtoList) {
				//上面批量更新后，把更新后信息设置到CODPayableDto上。
				// copy 'Status BatchNumber TusyorgRfdApptime TusyorgRfdAppUserCode TusyorgRfdAppUserName RefundPath' to codPayableDto
				this.initializaApplyRefundCOD(codBatchNumberStr, currentInfo,codPayableDto,now);
				
				BillPayableEntity billPayable = this.createCheckedBillPayableBycodPayableDto(codPayableDto);

				// 代收货款线上退款申请数据验证
				this.validBillPayableCODEntity(codPayableDto, billPayable);

			}
			
			if(StringUtils.equals("Y", isPackage)){
				// 根据可合并代收明细排序，把可合并的反写合并编码，并生成合并的代收集合，并插入合并集合中。
				this.buildMergeCodDto(mergeCodPayableDtoList ,currentInfo);

			}else{
				// 把不可合并代收货款汇集起来 
				List<CODMergeDto> noMegeCODMergeDtoList = this.collectCodPayableListToNoMegeCODMergeList(
															codPayableDtoList, 
															this.collectObjectListToStringList(mergeCodPayableDtoList));
				// 把不可合并代收货款集合 插入 待退款集合
				mergeCodPayableDtoList.addAll(noMegeCODMergeDtoList);
			}
			
		}
	}
	
	/**
	 * 符合条件的全部代收货款做线上汇代收货款服务.
	 * 
	 * @param entityIds
	 *            the entity ids
	 * @param currentInfo
	 *            the current info
	 * @return the string
	 * @throws SettlementException
	 *             the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-22 上午11:35:50
	 */
	@Transactional
	@Override
	public String doWithSendBillPaybleToBankAll(Date endSignDate,
											List<String> codTypes, List<String> banks,
											String publicPrivateFlag, CurrentInfo currentInfo) {
		LOGGER.info("批量处理线上汇代收货款服务Service start");
		
		// 代收货款类型不能为空
		if (CollectionUtils.isEmpty(codTypes)) {
			throw new SettlementException("代收货款类型为空，不能进行代收货款汇款导出查询");
		}

		// 查询代收货款
		BillCODPayableQueryDto queryDto = this.createQueryCODConditions( endSignDate,codTypes,banks,publicPrivateFlag);
		/**
		 * @author 218392  zhangyongxue 2015-09-01 14:26:39
		 * 这里加上个判断给isPackage根据是否打包退设置值
		 */
		queryDto.setIsPackage("");//默认不含有打包退,默认是空
		if(codTypes != null){
			for(String codType : codTypes){
				if(StringUtils.equals("PACK", codType)){
					queryDto.setIsPackage("Y");//如果含有打包退的时候，就将 N 变成 Y
				}
			}
		}	
		List<CODPayableDto> codPayableDtoList = codCommonService.queryAllCODAndBillPayable(queryDto);
		
		/**
		 * @author 218392 zhangyongxue 2015-09-02 10:59:58
		 * 当前台页面查询时打包退的时候，走if条件里的；如果不是打包退的时候，就为空，方便后面业务逻辑里面，将不是打包退的设置到该空集合中
		 */
		List<CODMergeDto> mergeCodPayableDtoList = new ArrayList<CODMergeDto>();
		if(StringUtils.equals("Y", queryDto.getIsPackage())){
			// 根据合并客户查询可合并的代收货款明细
			// 该mergeCodPayableDtoList 被readySendBillPaybleToBankAll调用，插入合并汇总及不可合并明细
			mergeCodPayableDtoList = codCommonService.queryAllMergeCODAndBillPayable(queryDto);
		}

		
		// 判断codEntity集合属于什么类型(R3,RA;R1)
		// 生成代收货款批次号
		String codBatchNumberStr = codBatchService.generateCODBatchNo(codCommonService.codEntityListInstanceofType(codPayableDtoList));
		// 准备符合条件的全部代收货款做线上汇代收货款服务前，更改代收货款状态
		billPayCODOnlineService.readySendBillPaybleToBankAll(codBatchNumberStr,codPayableDtoList, mergeCodPayableDtoList,codTypes,currentInfo);
		
		if (SettlementConstants.EXTEND_SYSTEM_BANK_SWITCH) {
			// 发送付款请求至费控系统
			try {
				billPayCODOnlineSendService.billPayCODOnlineSend(codBatchNumberStr,mergeCodPayableDtoList);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				billPayCODOnlineService.errorDoSendBillPaybleToBank(codBatchNumberStr, e.getMessage(), currentInfo);
				throw new SettlementException("发送代收货款到银企时异常,数据量:"+mergeCodPayableDtoList.size()+"；"+e.getMessage());
			}
		}
		
		LOGGER.info("数据量:"+mergeCodPayableDtoList.size()+"批量处理线上汇代收货款服务Service end");
		
		return codBatchNumberStr;
	}
	
	/**
	 * 准备处理线上汇代收货款服务前，更改代收货款状态.
	 * 
	 * 把codEntityList修改退款中，并转化为codPayableList，及返回billPayableEntityList
	 * @param codBatchNumberStr
	 * @param codEntityList
	 * @param mergeCodMergeDtoList
	 * @param currentInfo
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW) 
	@Override
	public void readySendBillPaybleToBank(String codBatchNumberStr,
										List<CODEntity> codEntityList,
										List<CODMergeDto> mergeCodMergeDtoList,
										List<String> codTypes,
										CurrentInfo currentInfo){
		
		// 创建批次号实体
		codBatchService.createCODBatchEntity(currentInfo, codBatchNumberStr,SettlementDictionaryConstants.COD_BATCH__STATUS__SENDING);

		// 把未签定客户合并合同，即不可合并代收货款汇集起来
		List<CODMergeDto> noMegeCODMergeDtoList = new ArrayList<CODMergeDto>();
		
		//List<String> mergeWaybillList = this.collectObjectListToStringList(mergeCodMergeDtoList);
		String isPackage = "";
		/**
	 	* @author 218392 zhangyongxue
	 	* 之所以加上一个非空判断，是因为代收货款支付的时候，有个页签是按单号支付，里面就没有代后货款类型，不然会报错
	 	*/
		if(codTypes != null){
			for(String codType : codTypes){
				if(StringUtils.equals("PACK", codType)){
					isPackage = "Y";//如果含有打包退的时候，就将 "" 变成 Y
				}
			}
		}
		Date now = new Date();
		// 循环对代收货款数据处理
		for (CODEntity entity : codEntityList) {

			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			String status = entity.getStatus();
			// 如果不是经营部冻结，则抛出异常
			if (!SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)) {
				throw new SettlementException("代收货款非资金部冻结");
			}

			// 代收货款状态、批次号、申请时间、申请人、退款路径
			this.initializaApplyRefundCOD(codBatchNumberStr, currentInfo, entity, now);

			BillPayableEntity billPayable = codCommonService.getBillPayableEntity(entity);
			
			// 代收货款线上退款申请数据验证
			this.validBillPayableCODEntity(entity, billPayable);

			// 更新代收货款状态
			codCommonService.updatePayCODOnlineStatus(entity, currentInfo);
			
			// 把不可合并代收货款汇集
			/**
			 * @author 218392 zhangyongxue 2015-08-29 17:27:30
			 */
			if(StringUtils.equals("", isPackage)){
				CODMergeDto mergeDto = this.createMergeDtoByCODAndPayable(entity, billPayable);
				noMegeCODMergeDtoList.add(mergeDto);
			}
//			if(CollectionUtils.isEmpty(mergeWaybillList) || !mergeWaybillList.contains(entity.getWaybillNo())){
//				CODMergeDto mergeDto = this.createMergeDtoByCODAndPayable(entity, billPayable);
//				noMegeCODMergeDtoList.add(mergeDto);
//			}

		}
		
		if(StringUtils.equals("Y", isPackage)){
			// 根据可合并代收明细排序，把可合并的反写合并编码，并生成合并的代收集合，并插入合并集合中。
			this.buildMergeCodDto(mergeCodMergeDtoList ,currentInfo);
		}

		if(StringUtils.equals("", isPackage)){
			// 把不可合并代收货款集合 插入 待退款集合
			mergeCodMergeDtoList.addAll(noMegeCODMergeDtoList);
		}
		
//		// 根据可合并代收明细排序，把可合并的反写合并编码，并生成合并的代收集合，并插入合并集合中。
//		this.buildMergeCodDto(mergeCodMergeDtoList ,currentInfo);
//
//		// 把不可合并代收货款集合 插入 待退款集合
//		mergeCodMergeDtoList.addAll(noMegeCODMergeDtoList);

	}
	
	/**
	 * 处理线上汇代收货款服务,勾选多条退款.
	 * 
	 * @param entityIds
	 *            the entity ids
	 * @param currentInfo
	 *            the current info
	 * @return the string
	 * @throws SettlementException
	 *             the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-22 上午11:35:50
	 */
	@Transactional
	@Override
	public String doWithSendBillPaybleToBank(List<String> entityIds,List<String> codTypes,CurrentInfo currentInfo) throws SettlementException {

		LOGGER.info("处理线上汇代收货款服务Service start,entityIds:" + (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		// 获得CODEntity集合
		List<CODEntity> codEntityList = codCommonService.queryByIds(entityIds);
		// 获得可合并代收明细
		List<CODMergeDto> mergeCodMergeDtoList = codCommonService.queryMergeCodByIds(entityIds);
		
		// 判断codEntity集合属于什么类型(R3,RA;R1)
		// 生成代收货款批次号
		String codBatchNumberStr = codBatchService.generateCODBatchNo(codCommonService.codEntityListInstanceofType(codEntityList));
		
		// 准备处理线上汇代收货款服务前，更改代收货款状态
		billPayCODOnlineService.readySendBillPaybleToBank(codBatchNumberStr, codEntityList, mergeCodMergeDtoList, codTypes,currentInfo);

		if (SettlementConstants.EXTEND_SYSTEM_BANK_SWITCH) {
			// 发送付款请求至费控系统
			try {
				billPayCODOnlineSendService.billPayCODOnlineSend(codBatchNumberStr, mergeCodMergeDtoList);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				billPayCODOnlineService.errorDoSendBillPaybleToBank(codBatchNumberStr, e.getMessage(), currentInfo);
				throw new SettlementException("发送代收货款到银企时异常,数据量:"+mergeCodMergeDtoList.size()+"；"+e.getMessage());
			}
		}

		LOGGER.info("数据量:"+mergeCodMergeDtoList.size()+"处理线上汇代收货款服务Service end");
		return codBatchNumberStr;
	}
	
	/**
	 * 根据可合并代收明细排序，把可合并的反写合并编码，并生成合并的代收集合，并插入合并集合中。
	 * @param mergeCodPayableDtoList
	 * @return
	 */
	private void buildMergeCodDto(List<CODMergeDto> mergeCodPayableDtoList , CurrentInfo currentInfo){
		
		// 如果没有可合并代收明细，则新建CODMergeDto集合
		if(null == mergeCodPayableDtoList){
			mergeCodPayableDtoList = new ArrayList<CODMergeDto>();
		}
		
		// 如果可合并代收集合大于1条，则排序合并。
		if(CollectionUtils.isNotEmpty(mergeCodPayableDtoList) && mergeCodPayableDtoList.size() > 1){
			
			List<CODMergeDto> mergeTotalDtoList = this.writeMergeCodeBuildTotalMergeCod(mergeCodPayableDtoList);
			
			// 把合并代收货款插入代收合并表
			this.insertMergeCodByBatch(mergeTotalDtoList, currentInfo);
			
			// 并把可合并代收明细表反写合并编码 
			this.updateCodMergeCodeByMergeDtoList(mergeCodPayableDtoList, currentInfo);
			
			// 把合并代收插入待退款集合
			mergeCodPayableDtoList.addAll(mergeTotalDtoList);
		}
		
	}
	
	/**
	 * 发送银企，线上汇代收货款异常处理
	 * @param codBatchNumberStr
	 * @param failNotes
	 * @param currentInfo
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void errorDoSendBillPaybleToBank(String codBatchNumberStr,String failNotes,CurrentInfo currentInfo){
		// 按批次号查询代收货款
		List<CODEntity> codList = codCommonService.queryByBatchNumber(codBatchNumberStr);

		LOGGER.error("批次号:" + codBatchNumberStr + "，发送失败退回异常:" + failNotes);

		// 循环处理
		for (CODEntity entity : codList) {
			// 代收货款状态、批次号、申请时间、申请人、退款路径
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
			// 设置属性值
			// entity.setBatchNumber(null);
			// 设置属性值
			entity.setTusyorgRfdApptime(null);
			// 设置属性值
			entity.setTusyorgRfdAppUserCode(null);
			// 设置属性值
			entity.setTusyorgRfdAppUserName(null);
			// 设置属性值
			entity.setRefundPath(null);
			// 设置属性值
			entity.setRefundPath(null);
			// 申请退款失败，合并编码设置为空
			entity.setMergeCode(null);
			
			// TODO 缺陷  为了添加发送异常到cod日志显示
			entity.setPayeeRelationship("批次号:" + codBatchNumberStr + "，发送失败退回异常:" + failNotes);
			// 更新代收货款状态
			codCommonService.updateCODBankBatchBackStatus(entity, currentInfo);
		}
		// 更新代收货款批次状态
		CODBatchDto entity = new CODBatchDto();
		// 批次号、状态、失败原因
		entity.setBatchNo(codBatchNumberStr);
		// 设置属性值
		entity.setStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_FAIL);
		// 设置属性值
		entity.setOldStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SENDING);
		// 设置属性值
		entity.setFailNotes(failNotes);
		// 更新批次状态
		codBatchService.updateCODBatchStatus(entity);
	}

	/**
	 * 生成代收货款付款单,当线上代收货款审核通过时，调用此接口.
	 * 
	 * @param entity
	 *            the entity
	 * @param currentInfo
	 *            the current info
	 * @throws SettlementException
	 *             the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午9:39:30
	 */
	public void generateCODPaymentBill(CODEntity entity, CurrentInfo currentInfo)
			throws SettlementException {

		if (entity == null) {
			throw new SettlementException("代收货款实体为空");
		}

		BillPayableEntity billPayable = codCommonService
				.getBillPayableEntity(entity);

		// 新增付款单
		BillPaymentEntity billPayment = buildBillPaymentEntity(entity,
				billPayable, currentInfo.getEmpName(), currentInfo.getEmpCode());

		// 创建付款单明细记录
		BillPaymentDEntity paymentDetail = new BillPaymentDEntity();
		paymentDetail.setSourceBillNo(billPayable.getPayableNo()); // 来源单号
		paymentDetail.setWaybillNo(billPayable.getWaybillNo()); // 运单号
		paymentDetail.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE); // 来源单据类型
		paymentDetail.setSrcSourceBillNo(billPayable.getSourceBillNo()); // 应付单来源单据号
		paymentDetail.setSourceAccountDate(billPayable.getAccountDate()); // 来源单据的记账日期
		paymentDetail.setPayAmount(billPayable.getUnverifyAmount());//支付金额
		
		// 保存付款单和付款单明细记录
		billPaymentService.addBillPaymentAndDetails(billPayment,Arrays.asList(paymentDetail), currentInfo);
		
		// 将付款单单号更新到应付单
		BillPayableDto dto = new BillPayableDto();
		dto.setId(billPayable.getId());
		dto.setAccountDate(billPayable.getAccountDate());
		dto.setVersionNo(billPayable.getVersionNo());
		dto.setPaymentNo(billPayment.getPaymentNo());
		dto.setPaymentAmount(billPayment.getAmount());
		billPayableService.payForBillPayable(dto, currentInfo);

	}
	
	/**
	 * 生成代收货款付款单,当线上代收货款审核通过时，调用此接口.
	 * 
	 * @param entity
	 *            the entity
	 * @param currentInfo
	 *            the current info
	 * @throws SettlementException
	 *             the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午9:39:30
	 */
	public void generateCODPaymentBill(List<CODEntity> entityList,List<BillPayableEntity> billPayableEntityList, CurrentInfo currentInfo)
			throws SettlementException {

		if(CollectionUtils.isEmpty(entityList)){
			throw new SettlementException("批次对应的代收货款集合为空");
		}
		if(CollectionUtils.isEmpty(billPayableEntityList)){
			throw new SettlementException("批次对应的代收货款应付单集合为空");
		}
		List<String> payableNos = new ArrayList<String>();
		Map<String,Object> codMap = new HashMap<String,Object>();
		int rows = 0;
		// TODO
		for(int i = 0; i < billPayableEntityList.size(); i++){
			rows++;
			payableNos.add(billPayableEntityList.get(i).getPayableNo());
			if(payableNos.size() == SettlementReportNumber.ONE_THOUSAND){
				codMap.put("payableNos", payableNos);
				codMap.put("empCode", currentInfo.getEmpCode());
				codMap.put("empName", currentInfo.getEmpName());
//				int addPaymentDRow = billPayCODOnlineDao.addCodPaymentD(codMap);
//				int addPaymentRow = billPayCODOnlineDao.addCodPayment(codMap);
//				int updatePayableRow = billPayCODOnlineDao.updateCodPayable(codMap);
				
				int updatePayableRow = billPayCODOnlineDao.updateCodPayable(codMap);
				int addPaymentRow = billPayCODOnlineDao.addCodPayment(codMap);
				int addPaymentDRow = billPayCODOnlineDao.addCodPaymentD(codMap);
				
				if(addPaymentDRow != addPaymentRow){
					throw new SettlementException("插入付款单明细条数和付款单表头条数不一致，明细条数" + addPaymentDRow + "表头条数" + addPaymentRow);
				}else if(addPaymentDRow != updatePayableRow){
					throw new SettlementException("插入付款单明细条数和更新应付单条数不一致，明细条数" + addPaymentDRow + "更新条数" + updatePayableRow);
				}
				payableNos.clear();
			}
		}
		if(payableNos.size()> 0 && payableNos.size() < SettlementReportNumber.ONE_THOUSAND){
			codMap.put("payableNos", payableNos);
			codMap.put("empCode", currentInfo.getEmpCode());
			codMap.put("empName", currentInfo.getEmpName());
//			int addPaymentDRow = billPayCODOnlineDao.addCodPaymentD(codMap);
//			int addPaymentRow = billPayCODOnlineDao.addCodPayment(codMap);
//			int updatePayableRow = billPayCODOnlineDao.updateCodPayable(codMap);
			
			int updatePayableRow = billPayCODOnlineDao.updateCodPayable(codMap);
			int addPaymentRow = billPayCODOnlineDao.addCodPayment(codMap);
			int addPaymentDRow = billPayCODOnlineDao.addCodPaymentD(codMap);
			
			if(addPaymentDRow != addPaymentRow){
				throw new SettlementException("插入付款单明细和付款单表头条数不一致！");
			}else if(addPaymentDRow != updatePayableRow){
				throw new SettlementException("插入付款单明细和更新应付单条数不一致！");
			}
			payableNos.clear();
		}
		if(rows != billPayableEntityList.size()){
			throw new SettlementException("操作条数和查询条数不一致，操作条数" + rows + "查询条数" + billPayableEntityList.size());
		}
		// 对代收货款循环处理
//		for (final CODEntity codEntity : entityList) {
//			// 找到对应的应付单
//			BillPayableEntity billPayableEntity = (BillPayableEntity) CollectionUtils.find(billPayableEntityList, new Predicate() {
//				@Override
//				public boolean evaluate(Object object) {
//					if(StringUtils.equals(codEntity.getWaybillNo(), ((BillPayableEntity)object).getWaybillNo())){
//						return true;
//					}
//					return false;
//				}
//			});
//			
//			if (codEntity == null) {
//				throw new SettlementException("代收货款实体为空");
//			}
//			if (billPayableEntity == null) {
//				throw new SettlementException("应付单实体为空");
//			}
//
//			// 新增付款单
//			BillPaymentEntity billPayment = buildBillPaymentEntity(codEntity,
//					billPayableEntity, currentInfo.getEmpName(), currentInfo.getEmpCode());
//
//			// 创建付款单明细记录
//			BillPaymentDEntity paymentDetail = new BillPaymentDEntity();
//			paymentDetail.setSourceBillNo(billPayableEntity.getPayableNo()); // 来源单号
//			paymentDetail.setWaybillNo(billPayableEntity.getWaybillNo()); // 运单号
//			paymentDetail.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE); // 来源单据类型
//			paymentDetail.setSrcSourceBillNo(billPayableEntity.getSourceBillNo()); // 应付单来源单据号
//			paymentDetail.setSourceAccountDate(billPayableEntity.getAccountDate()); // 来源单据的记账日期
//			paymentDetail.setPayAmount(billPayableEntity.getUnverifyAmount());//支付金额
//			
//			// 保存付款单和付款单明细记录
//			billPaymentService.addBillPaymentAndDetails(billPayment,Arrays.asList(paymentDetail), currentInfo);
//			
//			// 将付款单单号更新到应付单
//			BillPayableDto dto = new BillPayableDto();
//			dto.setId(billPayableEntity.getId());
//			dto.setAccountDate(billPayableEntity.getAccountDate());
//			dto.setVersionNo(billPayableEntity.getVersionNo());
//			dto.setPaymentNo(billPayment.getPaymentNo());
//			dto.setPaymentAmount(billPayment.getAmount());
//			billPayableService.payForBillPayable(dto, currentInfo);
//			
//		}
		
	}

	/**
	 * 构建付款单实体.
	 * 
	 * @param entity
	 *            the entity
	 * @param billPayable
	 *            the bill payable
	 * @param applyUserName
	 *            the apply user name
	 * @param applyUserCode
	 *            the apply user code
	 * @return the bill payment entity
	 * @author dp-zengbinwen
	 * @date 2012-10-20 下午4:57:00
	 */
	private BillPaymentEntity buildBillPaymentEntity(CODEntity entity,
			BillPayableEntity billPayable, String applyUserName,
			String applyUserCode) {

		BillPaymentEntity bill = new BillPaymentEntity();

		// ID,付款单编号，币种，付款金额，客户
		bill.setId(UUIDUtils.getUUID());
		bill.setPaymentNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.FK2));
		bill.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 付款金额取代收货款应付单未核销金额
		bill.setAmount(billPayable.getUnverifyAmount());
		bill.setCustomerCode(billPayable.getCustomerCode());
		bill.setCustomerName(billPayable.getCustomerName());

		// 汇款状态，审核状态，来源单据类型，来源单据编号
		bill.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);
		bill.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		bill.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);
		bill.setSourceBillNo(billPayable.getPayableNo());

		// 付款部门
		bill.setPaymentOrgCode(billPayable.getPayableOrgCode());
		bill.setPaymentOrgName(billPayable.getPayableOrgName());
		// 付款子公司
		bill.setPaymentCompanyCode(billPayable.getPayableComCode());
		bill.setPaymentCompanyName(billPayable.getPayableComName());

		// 是否有效、是否红单、创建人、业务日期、会计日期
		bill.setActive(FossConstants.YES);
		bill.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		bill.setCreateUserCode(applyUserName);
		bill.setCreateUserName(applyUserCode);
		bill.setBusinessDate(billPayable.getBusinessDate());
		bill.setAccountDate(new Date());

		// 创建时间、是否初始化、付款方式、收款人电话、开户名
		bill.setCreateTime(new Date());
		bill.setIsInit(FossConstants.NO);
		bill.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
		bill.setMobilePhone(entity.getPayeePhone());
		bill.setPayeeName(entity.getPayeeName());

		// 银行账号、对公对私标志、省、市、银行、支行、行号、版本号
		bill.setAccountNo(entity.getAccountNo());
		bill.setAccountType(entity.getPublicPrivateFlag());
		bill.setProvinceCode(entity.getProvinceCode());
		bill.setProvinceName(entity.getProvinceName());
		bill.setCityCode(entity.getCityCode());
		bill.setCityName(entity.getCityName());
		bill.setBankHqName(entity.getBankHQName());
		bill.setBankBranchName(entity.getBankBranchName());
		bill.setBankBranchCode(entity.getBankBranchCode());
		bill.setVersionNo(FossConstants.INIT_VERSION_NO);
		bill.setBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);
		// 生成方式，自动生成
		bill.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

		return bill;
	}

	/**
	 * 代收货款线上退款申请数据验证.
	 * 
	 * @param entity
	 *            the entity
	 * @param billPayable
	 *            the bill payable
	 * @author guxinhua
	 * @date 2013-1-14 下午2:12:41
	 */
	private void validBillPayableCODEntity(CODEntity entity,
			BillPayableEntity billPayable) {
		String waybillNo = entity.getWaybillNo();
		if (StringUtils.isBlank(waybillNo)) {
			throw new SettlementException("代收货款运单号不能为空");
		}
		String headMsg = "运单：" + waybillNo + "，";

		if (StringUtils.isBlank(entity.getCodType())) {
			throw new SettlementException(headMsg + "代收货款类别不能为空");
		}
		if (StringUtils.isBlank(billPayable.getPayableComCode())) {
			throw new SettlementException(headMsg + "代收货款所属子公司编码不能为空");
		}
		if (StringUtils.isBlank(billPayable.getPayableComName())) {
			throw new SettlementException(headMsg + "代收货款所属子公司不能为空");
		}
		if (StringUtils.isBlank(billPayable.getPayableOrgName())) {
			throw new SettlementException(headMsg + "代收货款出发部门不能为空");
		}
		if (StringUtils.isBlank(entity.getPayeeName())) {
			throw new SettlementException(headMsg + "代收货款收款人不能为空");
		}
		if (null == billPayable.getAmount()) {
			throw new SettlementException(headMsg + "代收货款金额不能为空");
		}
		if (StringUtils.isBlank(entity.getAccountNo())) {
			throw new SettlementException(headMsg + "账号不能为空");
		}
		if (StringUtils.isBlank(entity.getBankHQCode())) {
			throw new SettlementException(headMsg + "开户行编码不能为空");
		}
		if (StringUtils.isBlank(entity.getBankHQName())) {
			throw new SettlementException(headMsg + "开户行不能为空");
		}
		if (StringUtils.isBlank(entity.getProvinceName())) {
			throw new SettlementException(headMsg + "省不能为空");
		}
		if (StringUtils.isBlank(entity.getCityName())) {
			throw new SettlementException(headMsg + "市不能为空");
		}
		if (StringUtils.isBlank(entity.getBankBranchName())) {
			throw new SettlementException(headMsg + "支行不能为空");
		}
		if (StringUtils.isBlank(entity.getPublicPrivateFlag())) {
			throw new SettlementException(headMsg + "对公对私标志不能为空");
		}
		/*
		 * if(StringUtils.isBlank(entity.getPayeePhone())){ throw new
		 * SettlementException(headMsg + "手机号码不能为空"); }
		 */
		if (null == billPayable.getSignDate()) {
			throw new SettlementException(headMsg + "签收日期不能为空");
		}
		if (StringUtils.isBlank(entity.getBankBranchCode())) {
			throw new SettlementException(headMsg + "银行行号不能为空");
		}
		if (null == entity.getTusyorgRfdApptime()) {
			throw new SettlementException(headMsg + "申请时间时间不能为空");
		}
		if (StringUtils.isBlank(entity.getTusyorgRfdAppUserName())) {
			throw new SettlementException(headMsg + "申请人不能为空");
		}
		if (StringUtils.isBlank(entity.getBatchNumber())) {
			throw new SettlementException(headMsg + "批次号不能为空");
		}

	}
	
	/**
	 * 处理审核结果失败.
	 * 
	 * @param batchNumber
	 *            the batch number
	 * @param remark
	 *            the remark
	 * @throws SettlementException
	 *             the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午9:20:08
	 */
	@Transactional
	@Override
	public void processAuditFailed(String batchNumber, String remark)
			throws SettlementException {
		// 获取银企默认用户信息
		CurrentInfo currentInfo = SettlementUtil.getYQCurrentInfo();
		// 按批次号查询代收货款
		List<CODEntity> codList = codCommonService.queryByBatchNumber(batchNumber);
		
		// TODO 银企返回消息中可能存在重复消息，截取处理
		int index = StringUtils.indexOf(remark, "！");
		if(index != -1){
			remark = remark.substring(0,index + 1);
		}
					
		LOGGER.error("批次号:"+batchNumber + "，审核失败退回异常:" + remark);
		
		//循环处理
		for (CODEntity entity : codList) {
			// 代收货款状态、批次号、申请时间、申请人、退款路径
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
			//属性设置
			//entity.setBatchNumber(null);
			//属性设置
			entity.setTusyorgRfdApptime(null);
			//属性设置
			entity.setTusyorgRfdAppUserCode(null);
			//属性设置
			entity.setTusyorgRfdAppUserName(null);
			//属性设置
			entity.setRefundPath(null);
			//为了添加发送异常到cod日志显示
			entity.setPayeeRelationship("批次号:"+batchNumber + "，审核失败退回异常:" + remark);
			// 更新代收货款状态
			codCommonService.updateCODBankBatchBackStatus(entity, currentInfo);
		}

		// 更新代收货款批次状态
		CODBatchDto entity = new CODBatchDto();
		// 批次号、状态、失败原因
		entity.setBatchNo(batchNumber);
		//属性设置
		entity.setStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__BANK_AUDIT_FAIL);
		//属性设置
		//entity.setOldStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_SUCCESS);
		//属性设置
		entity.setFailNotes(remark);
		// 更新批次状态
		codBatchService.updateCODBatchStatus(entity);
	}

	/**
	 * 处理审核成功.
	 * 
	 * @param batchNumber
	 *            the batch number
	 * @throws SettlementException
	 *             the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午9:32:58
	 */
	@Transactional
	@Override
	public void processAuditSuccess(String batchNumber)throws SettlementException {
		// 获取银企默认用户信息
		CurrentInfo currentInfo = SettlementUtil.getYQCurrentInfo();
		// 按批次号查询代收货款
		List<CODEntity> codList = codCommonService.queryByBatchNumber(batchNumber);
		
		List<BillPayableEntity> payableList = codCommonService.queryBillPayableByCodBatchNumber(batchNumber);
		
		this.generateCODPaymentBill(codList,payableList, currentInfo);
		
		// 更新代收货款批次状态
		CODBatchDto entity = new CODBatchDto();
		// 批次号、状态、失败原因
		entity.setBatchNo(batchNumber);
		//属性设置
		entity.setStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__BANK_AUDIT_PASS);
		//属性设置
		entity.setOldStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_SUCCESS);
		// 更新批次状态
		codBatchService.updateCODBatchStatus(entity);
	}

	/**
	 * 创建查询条件DTO
	 * @param endSignDate
	 * @param codTypes
	 * @param banks
	 * @param publicPrivateFlag
	 * @return
	 */
	private BillCODPayableQueryDto createQueryCODConditions(Date endSignDate,
			List<String> codTypes, List<String> banks,
			String publicPrivateFlag){
		// 构造查询参数
		BillCODPayableQueryDto queryDto = new BillCODPayableQueryDto();
		// 截止签收日期、代收货款类型、银行、对公对私标志
		queryDto.setEndSignDate(endSignDate);
		if(codTypes != null){
			queryDto.setCodTypes(codTypes);
		}
		queryDto.setBanks(banks);
		queryDto.setPublicPrivateFlag(publicPrivateFlag);
		// 代收货款状态:冻结
		List<String> statuses = new ArrayList<String>();
		statuses.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
		queryDto.setStatuses(statuses);
		// 是否有效、应付单是否有效、应付单生效状态、应付单单据类型
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		return queryDto;
	}
	
	/**
	 * 根据代收实体应付单生成合并代收DTO
	 * @param entity
	 * @param billPayable
	 * @return
	 */
	private CODMergeDto createMergeDtoByCODAndPayable(CODEntity entity, BillPayableEntity billPayable){
		CODMergeDto mergeDto = new CODMergeDto();
		mergeDto.setAccountNo(entity.getAccountNo());
		mergeDto.setBankBranchCode(entity.getBankBranchCode());
		mergeDto.setBankHqCode(entity.getBankHQCode());
		mergeDto.setCityCode(entity.getCityCode());
		mergeDto.setCodAmount(billPayable.getUnverifyAmount());
		mergeDto.setCodType(entity.getCodType());
		mergeDto.setPayableComCode(billPayable.getPayableComCode());
		mergeDto.setPayableOrgCode(billPayable.getPayableOrgCode());
		mergeDto.setPayeeName(entity.getPayeeName());
		mergeDto.setProvinceCode(entity.getProvinceCode());
		mergeDto.setPublicPrivateFlag(entity.getPublicPrivateFlag());
		mergeDto.setSignDate(billPayable.getSignDate());
		mergeDto.setWaybillNo(entity.getWaybillNo());
		mergeDto.setPayeePhone(entity.getPayeePhone());
		return mergeDto;
	}
	
	/**
	 * 根据codPayableDto创建需校验的应付实体
	 * @param codPayableDto
	 * @return
	 */
	private BillPayableEntity createCheckedBillPayableBycodPayableDto(CODPayableDto codPayableDto){
		BillPayableEntity billPayable = new BillPayableEntity();//代收应付校验字段
		billPayable.setId(codPayableDto.getPayableId());
		billPayable.setAccountDate(codPayableDto.getPayableAccountDate());
		billPayable.setVersionNo(codPayableDto.getPayableVersionNo());
		billPayable.setEffectiveStatus(codPayableDto.getEffectiveStatus());
		billPayable.setSignDate(codPayableDto.getSignDate());
		billPayable.setPayableComCode(codPayableDto.getPayableComCode());
		billPayable.setPayableComName(codPayableDto.getPayableComName());
		billPayable.setWaybillNo(codPayableDto.getPayableWaybillNo());
		billPayable.setUnverifyAmount(codPayableDto.getUnverifyAmount());
		billPayable.setPayableOrgName(codPayableDto.getPayablePayableOrgName());
		billPayable.setAmount(codPayableDto.getAmount());
		
		return billPayable;
		
	}
	
	/**
	 * 根据对象集合获取某字段集合
	 * 获得需合并的运单号集合，用于收集不可合并代收集合
	 * @param mergeCodPayableDtoList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> collectObjectListToStringList(List<CODMergeDto> mergeCodPayableDtoList){
		if(CollectionUtils.isNotEmpty(mergeCodPayableDtoList)){ 
		
			return (List<String>) CollectionUtils.collect(mergeCodPayableDtoList, new Transformer() {
				@Override
				public Object transform(Object input) { return ((CODMergeDto)input).getWaybillNo(); }
			});
		
		}
		return null;
	}
	
	/**
	 * 根据代收需合并的运单号集合，过滤不可合并的代收集合
	 * @param codPayableDtoList
	 * @param mergeWaybillList
	 * @return
	 */
	private List<CODMergeDto> collectCodPayableListToNoMegeCODMergeList(List<CODPayableDto> codPayableDtoList,
																		List<String> mergeWaybillList){
		
		List<CODMergeDto> noMegeCODMergeDtoList = new ArrayList<CODMergeDto>();
		
		// 如果不存在需合并的运单号集合，则转化所有集合
		boolean haveMergeWaybillList = CollectionUtils.isEmpty(mergeWaybillList);
		
		// 循环对代收货款数据处理
		for (CODPayableDto codPayableDto : codPayableDtoList) {
			// 把不可合并代收货款汇集
			if(haveMergeWaybillList || !mergeWaybillList.contains(codPayableDto.getWaybillNo())){
				CODMergeDto mergeDto = new CODMergeDto();
				mergeDto.setAccountNo(codPayableDto.getAccountNo());
				mergeDto.setBankBranchCode(codPayableDto.getBankBranchCode());
				mergeDto.setBankHqCode(codPayableDto.getBankHQCode());
				mergeDto.setCityCode(codPayableDto.getCityCode());
				mergeDto.setCodAmount(codPayableDto.getUnverifyAmount());
				mergeDto.setCodType(codPayableDto.getCodType());
				mergeDto.setPayableComCode(codPayableDto.getPayableComCode());
				mergeDto.setPayableOrgCode(codPayableDto.getPayablePayableOrgCode());
				mergeDto.setPayeeName(codPayableDto.getPayeeName());
				mergeDto.setProvinceCode(codPayableDto.getProvinceCode());
				mergeDto.setPublicPrivateFlag(codPayableDto.getPublicPrivateFlag());
				mergeDto.setSignDate(codPayableDto.getSignDate());
				mergeDto.setWaybillNo(codPayableDto.getWaybillNo());
				mergeDto.setPayeePhone(codPayableDto.getPayeePhone());
				noMegeCODMergeDtoList.add(mergeDto);
			}
		}
		
		return noMegeCODMergeDtoList;
	}
	
	/**
	 * 批量更新代收退款中状态
	 * @param codBatchNumberStr
	 * @param codPayableDtoList
	 * @param currentInfo
	 */
	private void updateCodReturningByBatch(CODDto codDto,
			List<CODPayableDto> codPayableDtoList,
			CurrentInfo currentInfo){
		// 批量更新，大于1000条，拆分多组执行
		List<List<CODPayableDto>> splitPayableList = com.deppon.foss.util.CollectionUtils.splitListBySize(codPayableDtoList, FossConstants.ORACLE_MAX_IN_SIZE);
		for (List<CODPayableDto> list : splitPayableList) {
			codDto.setCodList(list); // 批量更新
			// 更新代收货款状态
			codCommonService.updatePayCODOnlineStatusByBatch(codDto, currentInfo);
		}
	}
	
	/**
	 * 初始化申请退款需要的代收.
	 * 代收货款状态、批次号、申请时间、申请人、退款路径
	 * @param codBatchNumberStr
	 * @param currentInfo
	 * @return
	 */
	private void initializaApplyRefundCOD(String codBatchNumberStr,CurrentInfo currentInfo,CODEntity dto,Date now){
		// 代收货款状态、批次号、申请时间、申请人、退款路径
		dto.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURNING);
		dto.setBatchNumber(codBatchNumberStr);
		dto.setTusyorgRfdApptime(now);
		dto.setTusyorgRfdAppUserCode(currentInfo.getEmpCode());
		dto.setTusyorgRfdAppUserName(currentInfo.getEmpName());
		dto.setRefundPath(SettlementDictionaryConstants.COD__COD_REFUND_PATH__ONLINE);
		
	}
	
	/**
	 * 循环验证代收集合是否是经营部冻结
	 * @param codPayableDtoList
	 */
	private void checkCodPayableDtoListIsFreeze(final List<CODPayableDto> codPayableDtoList){
		// 循环对代收货款数据处理
		for (CODPayableDto codPayableDto : codPayableDtoList) {

			if (codPayableDto == null) {
				throw new SettlementException("代收货款实体为空");
			}
			String status = codPayableDto.getStatus();
			// 如果不是经营部冻结，则抛出异常
			if (!SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)) {
				throw new SettlementException("代收货款非资金部冻结");
			}
		}
	}
	
	/**
	 * 排序mergeCodPayableDtoList，可合并明细生成合并编号设置到实体，生成合并代收集合，
	 * @param mergeCodPayableDtoList
	 * @return
	 */
	private List<CODMergeDto> writeMergeCodeBuildTotalMergeCod(List<CODMergeDto> mergeCodPayableDtoList){
		
		// 按照该顺序比较
		// 同一部门、收款人、账号、银行、支行、账户类别、所属子公司、省、市、手机号
		Collections.sort(mergeCodPayableDtoList);
		
		// 合并编码 // 新合并编码是否使用
		String mergeCode = null;
		boolean useMergeCode = false;
		// 该合并总金额
		BigDecimal mergeTotalAmount = BigDecimal.ZERO;
		// 合并的代收集合
		List<CODMergeDto> mergeTotalDtoList = new ArrayList<CODMergeDto>();
		for (int i = 0; i < mergeCodPayableDtoList.size(); i++) {
			if(i == 0){ // 第一次
				//生成新合并编号
				mergeCode = codMergeEntityDao.createMergeCode(SettlementNoRuleEnum.HB);
				useMergeCode = false;
			}else{
				CODMergeDto currentDto = mergeCodPayableDtoList.get(i);
				CODMergeDto previousDto = mergeCodPayableDtoList.get(i-1);
				if(currentDto.compareTo(previousDto) == 0){
					useMergeCode = true;
					currentDto.setMergeCode(mergeCode);
					if ( StringUtils.isBlank(previousDto.getMergeCode()) ){
						mergeTotalAmount = NumberUtils.add(previousDto.getCodAmount(), currentDto.getCodAmount());
						previousDto.setMergeCode(mergeCode);
					}else{
						mergeTotalAmount = NumberUtils.add( mergeTotalAmount, currentDto.getCodAmount());
					}
					
					if(mergeCodPayableDtoList.size()-1 == i){ //最后一次循环需要合并的则直接汇总添加
						this.mergeTotalDtoListAdd(previousDto, mergeTotalDtoList, mergeTotalAmount);
					}
				}else{
					if(useMergeCode){ 
						this.mergeTotalDtoListAdd(previousDto, mergeTotalDtoList, mergeTotalAmount);
					}
					// 合并编码没用使用过，重新生成新编码，反之不生成。
					if(useMergeCode){
						mergeCode = codMergeEntityDao.createMergeCode(SettlementNoRuleEnum.HB);
						useMergeCode = false;
					}
				}
				
			}
		}
		
		
		return mergeTotalDtoList;
	}
	
	/**
	 * 往mergeTotalDtoList追加合并DTO
	 * @param previousDto
	 * @param mergeTotalDtoList
	 * @param mergeTotalAmount
	 */
	private void mergeTotalDtoListAdd(final CODMergeDto previousDto,final List<CODMergeDto> mergeTotalDtoList,BigDecimal mergeTotalAmount){
		if(mergeTotalAmount.compareTo(BigDecimal.ZERO) > 0){
			// 合并数据,剔除运单号信息，带上合并编号
			CODMergeDto mergeNewDto = new  CODMergeDto();
			// copy 同一部门、收款人、账号、银行、支行、账户类别、所属子公司、省、市、手机号 合并编号，（因为合并数据没有运单号，不包括运单号，ID）
			BeanUtils.copyProperties(previousDto, mergeNewDto,  new String[]{"id","waybillNo"});
			mergeNewDto.setCodAmount(mergeTotalAmount);
			mergeTotalDtoList.add(mergeNewDto);
			mergeTotalAmount = BigDecimal.ZERO;
		}
	}
	
	/**
	 * 批量插入代收合并数据
	 * @param mergeTotalDtoList
	 * @param currentInfo
	 */
	private void insertMergeCodByBatch(List<CODMergeDto> mergeTotalDtoList,CurrentInfo currentInfo){
		Date now = new Date();
		// 把合并代收货款插入代收合并表，
		for (CODMergeDto codMergeDto : mergeTotalDtoList) {
			CODMergeEntity entity = new CODMergeEntity();
			// copy 同一部门、收款人、账号、银行、支行、账户类别、所属子公司、省、市、手机号 合并编号，（因为合并数据没有运单号，不包括运单号，ID）
			BeanUtils.copyProperties(codMergeDto, entity);
			entity.setId(UUIDUtils.getUUID());
			entity.setActive(FossConstants.ACTIVE);
			entity.setCreateTime(now);
			entity.setCreateUserCode(currentInfo.getEmpCode());
			entity.setCreateUserName(currentInfo.getEmpName());
			entity.setModifyTime(now);
			entity.setModifyUserCode(currentInfo.getEmpCode());
			entity.setModifyUserName(currentInfo.getEmpName());
			codMergeEntityDao.insert(entity);
		}
	}
	
	/**
	 * 把可合并代收明细表反写合并编码 
	 * @param mergeCodPayableDtoList
	 * @param currentInfo
	 */
	private void updateCodMergeCodeByMergeDtoList(List<CODMergeDto> mergeCodPayableDtoList,CurrentInfo currentInfo){
		Date now = new Date();
		// 把可合并明细过滤出 需合并代收明细
		@SuppressWarnings("unchecked")
		List<CODMergeDto> canMegeCODMergeDtoList = (List<CODMergeDto>) CollectionUtils.select(mergeCodPayableDtoList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CODMergeDto dto = (CODMergeDto) object;
				// 明细集合中，有运单号及有合并编号的，即可合并明细
				return StringUtils.isNotBlank(dto.getWaybillNo()) && StringUtils.isNotBlank(dto.getMergeCode());
			}
		});
		
		// 更新合并编码
		for (CODMergeDto mergeDto : canMegeCODMergeDtoList) {
			mergeDto.setModifyTime(now);
			mergeDto.setModifyUserCode(currentInfo.getEmpCode());
			mergeDto.setModifyUserName(currentInfo.getEmpName());
			codCommonService.updateCodMergeCode(mergeDto);
		}
	}
	
	/**
	 * Sets the cod common service.
	 * 
	 * @param codCommonService
	 *            the new cod common service
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Sets the bill payment service.
	 * 
	 * @param billPaymentService
	 *            the new bill payment service
	 */
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	/**
	 * Sets the settlement common service.
	 * 
	 * @param settlementCommonService
	 *            the new settlement common service
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * Sets the bill pay cod online send service.
	 * 
	 * @param billPayCODOnlineSendService
	 *            the new bill pay cod online send service
	 */
	public void setBillPayCODOnlineSendService(
			IBillPayCODOnlineSendService billPayCODOnlineSendService) {
		this.billPayCODOnlineSendService = billPayCODOnlineSendService;
	}

	/**
	 * Sets the cod batch service.
	 * 
	 * @param codBatchService
	 *            the new cod batch service
	 */
	public void setCodBatchService(ICODBatchService codBatchService) {
		this.codBatchService = codBatchService;
	}

	/**
	 * Sets the bill payable service.
	 * 
	 * @param billPayableService
	 *            the new bill payable service
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	/**
	 * @param billPayCODOnlineService the billPayCODOnlineService to set
	 */
	public void setBillPayCODOnlineService(
			IBillPayCODOnlineService billPayCODOnlineService) {
		this.billPayCODOnlineService = billPayCODOnlineService;
	}
	/**
	 * @param codMergeEntityDao the codMergeEntityDao to set
	 */
	public void setCodMergeEntityDao(ICODMergeEntityDao codMergeEntityDao) {
		this.codMergeEntityDao = codMergeEntityDao;
	}
	
	
	/*public static void main(String[] args) {
		// 按照该顺序比较
		// 同一部门、收款人、账号、银行、支行、账户类别、所属子公司、省、市、手机号
		// PayableOrgCode PayeeName AccountNo BankHqCode BankBranchCode PublicPrivateFlag
		// PayableComCode ProvinceCode CityCode PayeePhone
		//Collections.sort(mergeCodPayableDtoList);
		
		List<CODMergeDto> mergeCodPayableDtoList = new ArrayList<CODMergeDto>();
		CODMergeDto dto = new CODMergeDto();
		dto.setPayableOrgCode("org111");
		dto.setPayeeName("payee111");
		dto.setAccountNo("acc111");
		dto.setBankHqCode("bank111");
		dto.setBankBranchCode("subbank111");
		dto.setPublicPrivateFlag("C");
		dto.setPayableComCode("com111");
		dto.setProvinceCode("provt111");
		dto.setCityCode("city111");
		dto.setPayeePhone("135");
		
		CODMergeDto dto1 = new CODMergeDto();
		dto1.setPayableOrgCode("org111");
		dto1.setPayeeName("payee111");
		dto1.setAccountNo("acc111");
		dto1.setBankHqCode("bank111");
		dto1.setBankBranchCode("subbank111");
		dto1.setPublicPrivateFlag("C");
		dto1.setPayableComCode("com111");
		dto1.setProvinceCode("provt111");
		dto1.setCityCode("city111");
		
		CODMergeDto dto2 = new CODMergeDto();
		dto2.setPayableOrgCode("org1");
		dto2.setPayeeName("payee111");
		dto2.setAccountNo("acc111");
		dto2.setBankHqCode("bank111");
		dto2.setBankBranchCode("subbank111");
		dto2.setPublicPrivateFlag("C");
		dto2.setPayableComCode("com111");
		dto2.setProvinceCode("provt111");
		dto2.setCityCode("city111");
		dto2.setPayeePhone("135");
		
		
		mergeCodPayableDtoList.add(dto);
		mergeCodPayableDtoList.add(dto1);
		mergeCodPayableDtoList.add(dto2);
		
		System.out.println(mergeCodPayableDtoList);
		
		Collections.sort(mergeCodPayableDtoList);
		
		System.out.println(mergeCodPayableDtoList);
	}*/

	public void setBillPayCODOnlineDao(IBillPayCODOnlineDao billPayCODOnlineDao) {
		this.billPayCODOnlineDao = billPayCODOnlineDao;
	}

}
