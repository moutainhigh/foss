/**
 * Copyright 2013 STL TEAM
 */
package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCODStateService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayableQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 冻结代收货款服务.
 * 
 * @author dp-zengbinwen
 * @date 2012-10-17 下午6:40:24
 */
public class BillCODStateService implements IBillCODStateService {

	/** 日志服务. */
	private static final Logger LOGGER = LogManager
			.getLogger(BillCODStateService.class);

	/** 代收货款服务. */
	private ICodCommonService codCommonService;

	/** 应付单服务. */
	private IBillPayableService billPayableService;

	/** 接送货更改单Service. */
	// private IWaybillRfcService waybillRfcService;

	/** 查询签收变更结果Service. */
	private ISignChangeService signChangeService;
	
	/**
	 * @author 268217
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 检验codEntityList实体的状态，把通过的运单添加到codWayBillNoList,并返回
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 上午9:48:57
	 * @param codEntityList
	 * @return
	 */
	private List<String> validCODEntityList(List<CODEntity> codEntityList)
			throws SettlementException {

		List<String> codWayBillNoList = new ArrayList<String>(
				codEntityList.size()); // 业务参数运单号

		String status = null; // 代收货款状态
		// 1.循环对代收货款数据处理,验证错误
		for (CODEntity entity : codEntityList) {

			// entity = codCommonService.queryById(entityId);
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			status = entity.getStatus();

			// 已经冻结的单据再次冻结，则抛出异常
			if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)) {
				throw new SettlementException("运单号[" + entity.getWaybillNo()
						+ "]的代收货款已经冻结的单据不能再次冻结");
			}

			// 代收货款状态不为“未退款”，则抛出异常
			if (!SettlementDictionaryConstants.COD__STATUS__NOT_RETURN
					.equals(status)) {
				throw new SettlementException("运单号[" + entity.getWaybillNo()
						+ "]的代收货款状态不是“未退款”,不能资金部冻结");
			}

			codWayBillNoList.add(entity.getWaybillNo());
		}

		return codWayBillNoList;
	}

	/**
	 * 验证代收货款应付单，并判断是否有更改单，把存在更改单的运单号添加到waybillChangeFrozenErrorList，
	 * 把符合业务的代收货款添加到codEntityBussList，把符合退代收货款的应付单添加到payableEntityApproveList
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 上午10:03:36
	 * @param codEntityList
	 * @param waybillChangeFrozenErrorList
	 * @param codEntityBussList
	 * @param payableEntityApproveList
	 */
	private void vaildChangeWaybillAndBillPlayableByCodEntity(
			List<CODEntity> codEntityList,
			List<String> waybillChangeFrozenErrorList,
			List<CODPayableDto> codEntityBussList,
			List<BillPayableEntity> payableEntityApproveList,
			List<String> waybillNoErrorList)
			throws SettlementException {

		List<String> codWayBillNoList = this.validCODEntityList(codEntityList);

		List<BillPayableEntity> billPayableList = billPayableService
				.queryByWaybillNosAndByBillTypes(
						codWayBillNoList,
						Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD));// 获得代收货款应付单集合
		codWayBillNoList = null; // 回收

		String codType = null; // 代收货款类型
		BillPayableEntity billPayable = null; // 代收货款应付单
		String[] receiveBillTypes = null ;
		// 2.循环对代收货款数据处理-验证应付单
		for (final CODEntity entity : codEntityList) {

			// 查询应付单
			billPayable = (BillPayableEntity) CollectionUtils.find(
					billPayableList, new Predicate() {
						@Override
						public boolean evaluate(Object object) {
							if (StringUtils.equals(
									((BillPayableEntity) object).getWaybillNo(),
									entity.getWaybillNo()))
								return true;
							return false;
						}
					});

			if (null == billPayable) {
				throw new SettlementException("找不到运单[" + entity.getWaybillNo()
						+ "]代收货款应付单");
			}
			// 应付单未生效，不能冻结
			if (!StringUtils
					.equals(billPayable.getEffectiveStatus(),
							SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES)) {
				throw new SettlementException("运单号[" + entity.getWaybillNo()
						+ "]的代收货款应付单未生效,不能资金部冻结");
			}

			// 存在未受理的始发跟改单和到达更改单，则抛出异常--接送货判断是否存在未受理的更改单【接送货接口 】 ---修改为下面的方式
			// 存在未受理的始发跟改单
			/*
			 * boolean startBool =
			 * waybillRfcService.isExsitsWayBillRfc(entity.getWaybillNo()); if(
			 * startBool ){ // 存在未受理的更改单 throw new
			 * SettlementException("代收货款存在未受理的始发跟改单,不能资金部冻结"); }
			 */

			if (null == billPayable.getSignDate()
					|| StringUtils
							.isBlank(billPayable.getSignDate().toString())) {
				// throw new SettlementException("代收货款存在未受理的始发跟改单,不能资金部冻结");
				LOGGER.error("运单号[" + entity.getWaybillNo()
						+ "]的代收货款存在未受理的始发更改单,不能资金部冻结");
				waybillChangeFrozenErrorList.add(entity.getWaybillNo());
				continue; // 存在更改单 跳过
			}

			// 存在未受理的到达更改单
			boolean endBool = signChangeService.checkWayBillSignStatus(entity
					.getWaybillNo());
			if (!endBool) {
				// 存在未受理的更改单
				// throw new SettlementException("代收货款存在未受理的到达更改单,不能资金部冻结");
				LOGGER.error("运单号[" + entity.getWaybillNo()
						+ "]代收货款存在未受理的到达更改单,不能资金部冻结");
				waybillChangeFrozenErrorList.add(entity.getWaybillNo());
				continue; // 存在更改单 跳过
			}
			
			//268217  新增校验  ‘无代收应收、代收应收金额与代收应付金额不相等，无法冻结’
			receiveBillTypes = new String[3];
			//单据类型
			receiveBillTypes[0] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD;
        	receiveBillTypes[1] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE;
        	receiveBillTypes[2] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD;
        	
			BillReceivableConditionDto receiConDto = new BillReceivableConditionDto(entity.getWaybillNo());
			receiConDto.setBillTypes(receiveBillTypes);

			List<BillReceivableEntity> recList=billReceivableService.queryBillReceivableByCondition(receiConDto);
			if(CollectionUtils.isEmpty(recList)){
				LOGGER.error("运单号["
						+ entity.getWaybillNo() + "]的代收货款应收单不存在，无法冻结");
				waybillNoErrorList.add(entity.getWaybillNo());
				continue; // 代收货款应收单不存在 跳过
			}
			BillReceivableEntity receivableEntity= recList.get(0);
			if(receivableEntity.getAmount().compareTo(billPayable.getAmount()) >0 ||
					receivableEntity.getAmount().compareTo(billPayable.getAmount()) <0){
				LOGGER.error("运单号["
						+ entity.getWaybillNo() + "]的代收货款应收单与代收应付单的金额不一致，无法冻结");
				waybillNoErrorList.add(entity.getWaybillNo());
				continue; // 代收货款应收单与代收应付单的金额不一致 跳过
			}
			// 268217  结束校验
			
			CODPayableDto codPayableDto = new CODPayableDto();
			BeanUtils.copyProperties(entity, codPayableDto);
			// 符合冻结操作的代收货款集合
			codEntityBussList.add(codPayableDto);

			// 如果为审核退，更新应付单的状态
			codType = entity.getCodType();
			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
					.equals(codType)) {
				payableEntityApproveList.add(billPayable);
			}

		}
	}

	/**
	 * 验证代收货款应付单，并判断是否有更改单，把存在更改单的运单号添加到waybillChangeFrozenErrorList，
	 * 把符合业务的代收货款添加到codEntityBussList，把符合退代收货款的应付单添加到payableEntityApproveList
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 上午10:03:36
	 * @param codEntityList
	 * @param waybillChangeFrozenErrorList
	 * @param codEntityBussList
	 * @param payableEntityApproveList
	 */
	private void vaildChangeWaybillAndBillPlayableByCODPayableDto(
			List<CODPayableDto> codPayableDtoList,
			List<String> waybillChangeFrozenErrorList,
			List<CODPayableDto> codEntityBussList,
			List<BillPayableEntity> payableEntityApproveList,
			List<String> waybillNoErrorList)
			throws SettlementException {

		String codType = null; // 代收货款类型
		String status = null; // 代收货款状态
		BillPayableEntity billPayable = null;
		//应收单单据子类型
        String[] receiveBillTypes = null;

		for (final CODPayableDto codPayableDto : codPayableDtoList) {

			if (codPayableDto == null) {
				throw new SettlementException("代收货款实体为空");
			}

			status = codPayableDto.getStatus();

			// 已经冻结的单据再次冻结，则抛出异常
			if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)) {
				throw new SettlementException("运单号["
						+ codPayableDto.getWaybillNo() + "]的代收货款已经冻结的单据不能再次冻结");
			}

			// 代收货款状态不为“未退款”，则抛出异常
			if (!SettlementDictionaryConstants.COD__STATUS__NOT_RETURN
					.equals(status)) {
				throw new SettlementException("运单号["
						+ codPayableDto.getWaybillNo()
						+ "]的代收货款状态不是“未退款”,不能资金部冻结");
			}

			// 应付单未生效，不能冻结
			if (!StringUtils
					.equals(codPayableDto.getEffectiveStatus(),
							SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES)) {
				throw new SettlementException("运单号["
						+ codPayableDto.getWaybillNo() + "]的代收货款应付单未生效,不能资金部冻结");
			}

			if (null == codPayableDto.getSignDate()
					|| StringUtils.isBlank(codPayableDto.getSignDate()
							.toString())) {
				// throw new SettlementException("代收货款存在未受理的始发跟改单,不能资金部冻结");
				LOGGER.error("运单号[" + codPayableDto.getWaybillNo()
						+ "]的代收货款存在未受理的始发更改单,不能资金部冻结");
				waybillChangeFrozenErrorList.add(codPayableDto.getWaybillNo());
				continue; // 存在更改单 跳过
			}

			// 存在未受理的到达更改单
			boolean endBool = signChangeService
					.checkWayBillSignStatus(codPayableDto.getWaybillNo());
			if (!endBool) {
				// 存在未受理的更改单
				// throw new SettlementException("代收货款存在未受理的到达更改单,不能资金部冻结");
				LOGGER.error("运单号[" + codPayableDto.getWaybillNo()
						+ "]代收货款存在未受理的到达更改单,不能资金部冻结");
				waybillChangeFrozenErrorList.add(codPayableDto.getWaybillNo());
				continue; // 存在更改单 跳过
			}
			//268217  新增校验  ‘无代收应收、代收应收金额与代收应付金额不相等，无法冻结’
			receiveBillTypes = new String[3];
			//单据类型
			receiveBillTypes[0] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD;
        	receiveBillTypes[1] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE;
        	receiveBillTypes[2] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD;
        	
			BillReceivableConditionDto receiConDto = new BillReceivableConditionDto(codPayableDto.getWaybillNo());
			receiConDto.setBillTypes(receiveBillTypes);

			List<BillReceivableEntity> recList=billReceivableService.queryBillReceivableByCondition(receiConDto);
			if(CollectionUtils.isEmpty(recList)){
				LOGGER.error("运单号["
						+ codPayableDto.getWaybillNo() + "]的代收货款应收单不存在，无法冻结");
				waybillNoErrorList.add(codPayableDto.getWaybillNo());
				continue; // 代收货款应收单不存在 跳过
			}
			BillReceivableEntity receivableEntity= recList.get(0);
			if(receivableEntity.getAmount().compareTo(codPayableDto.getAmount()) >0 ||
					receivableEntity.getAmount().compareTo(codPayableDto.getAmount()) <0){
				LOGGER.error("运单号["
						+ codPayableDto.getWaybillNo() + "]的代收货款应收单与代收应付单的金额不一致，无法冻结");
				waybillNoErrorList.add(codPayableDto.getWaybillNo());
				continue; // 代收货款应收单与代收应付单的金额不一致 跳过
			}
			// 268217  结束校验
			
			// 符合冻结操作的代收货款集合
			codEntityBussList.add(codPayableDto);

			// 如果为审核退，更新应付单的状态
			codType = codPayableDto.getCodType();
			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
					.equals(codType)) {
				// 把codPayableDto中的应付单数据构建成应付单实体
				billPayable = new BillPayableEntity();
				billPayable.setId(codPayableDto.getPayableId());
				billPayable.setVersionNo(codPayableDto.getPayableVersionNo());
				billPayable.setAccountDate(codPayableDto
						.getPayableAccountDate());
				billPayable.setEffectiveStatus(codPayableDto
						.getEffectiveStatus());
				billPayable.setSignDate(codPayableDto.getSignDate());

				payableEntityApproveList.add(billPayable);
			}

		}
	}

	/**
	 * 批量冻结代收货款，应付单
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-7 下午2:19:51
	 * @param codEntityBussList
	 * @param payableEntityApproveList
	 * @param currentInfo
	 */
	private void doFrozenCodPayable(List<CODPayableDto> codEntityBussList,
			List<BillPayableEntity> payableEntityApproveList,
			CurrentInfo currentInfo) throws SettlementException {
		if (codEntityBussList.size() > 0) {
			// 批量更新，大于1000条，拆分多组执行
			// 冻结付款条件的代收货款
			List<List<CODPayableDto>> splitCodList = com.deppon.foss.util.CollectionUtils
					.splitListBySize(codEntityBussList,
							FossConstants.ORACLE_MAX_IN_SIZE);
			CODDto dto = new CODDto();
			// 更新代收货款状态为资金部冻结
			dto.setStatus(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
			dto.setTusyorgFreezeTime(new Date());
			dto.setTusyorgFreezeUserCode(currentInfo.getEmpCode());
			dto.setTusyorgFreezeUserName(currentInfo.getEmpName());
			for (List<CODPayableDto> list : splitCodList) {
				dto.setCodList(list); // 批量更新
				// 更新代收货款数据
				codCommonService.updateTusyorgFreezeStatusByBatch(dto,
						currentInfo);
			}
		}

		if (payableEntityApproveList.size() > 0) {
			// 批量更新，大于1000条，拆分多组执行
			// 冻结审核退代收货款的应付单
			// 冻结状态、冻结时间、冻结人
			List<List<BillPayableEntity>> splitPayableList = com.deppon.foss.util.CollectionUtils
					.splitListBySize(payableEntityApproveList,
							FossConstants.ORACLE_MAX_IN_SIZE);
			Date nowDate = new Date();
			BillPayableDto payableDto = new BillPayableDto();
			payableDto
					.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN);
			payableDto.setFrozenTime(nowDate);
			payableDto.setFrozenUserCode(currentInfo.getEmpCode());
			payableDto.setFrozenUserName(currentInfo.getEmpName());
			payableDto.setModifyTime(nowDate);
			payableDto.setModifyUserCode(currentInfo.getEmpCode());
			payableDto.setModifyUserName(currentInfo.getEmpName());
			for (List<BillPayableEntity> list : splitPayableList) {
				payableDto.setBillPayables(list); // 批量更新
				// 冻结代收货款应付单
				billPayableService.frozenBillPayableByBatch(payableDto,
						currentInfo);
			}
		}
	}

	/**
	 * 批量取消冻结代收货款，应付单
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-7 下午2:19:51
	 * @param codEntityBussList
	 * @param payableEntityApproveList
	 * @param currentInfo
	 */
	private void doFreezeCodPayable(List<CODPayableDto> codEntityBussList,
			List<BillPayableEntity> payableEntityApproveList,
			CurrentInfo currentInfo) throws SettlementException {

		if (codEntityBussList.size() > 0) {
			// 批量更新，大于1000条，拆分多组执行
			// 冻结付款条件的代收货款
			List<List<CODPayableDto>> splitCodList = com.deppon.foss.util.CollectionUtils
					.splitListBySize(codEntityBussList,
							FossConstants.ORACLE_MAX_IN_SIZE);
			// 批量更新
			CODDto codDto = new CODDto();
			// 更新代收货款状态为未退款
			codDto.setStatus(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
			codDto.setTusyorgClfreezeTime(new Date());
			codDto.setTusyorgClfreezeUserCode(currentInfo.getEmpCode());
			codDto.setTusyorgFreezeUserName(currentInfo.getEmpName());
			for (List<CODPayableDto> list : splitCodList) {
				codDto.setCodList(list); // 批量更新
				// 更新代收货款数据
				codCommonService.updateTusyorgClfreezeStatusByBatch(codDto,
						currentInfo);
			}
		}

		if (payableEntityApproveList.size() > 0) {
			// 批量更新，大于1000条，拆分多组执行
			// 冻结付款条件的代收货款
			List<List<BillPayableEntity>> splitCodList = com.deppon.foss.util.CollectionUtils
					.splitListBySize(payableEntityApproveList,
							FossConstants.ORACLE_MAX_IN_SIZE);
			// 批量更新
			BillPayableDto payableDto = new BillPayableDto();
			// 冻结状态、冻结时间、冻结人
			payableDto.setFrozenTime(null);
			payableDto.setFrozenUserCode(null);
			payableDto.setFrozenUserName(null);
			// 设置冻结状态为-未冻结
			payableDto
					.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
			// 修改操作
			payableDto.setModifyTime(new Date());
			// 修改人编码
			payableDto.setModifyUserCode(currentInfo.getEmpCode());
			// 修改人名称
			payableDto.setModifyUserName(currentInfo.getEmpName());
			// 设定支付状态为未支付
			payableDto
					.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
			// 设置付款金额为空
			payableDto.setPaymentAmount(null);
			// 付款备注为空
			payableDto.setPaymentNotes(null);
			// 设置付款单号为N/A
			payableDto.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);
			for (List<BillPayableEntity> list : splitCodList) {
				payableDto.setBillPayables(list); // 批量更新
				// 取消冻结代收货款
				billPayableService.cancelFrozenBillPayableByBatch(payableDto,
						currentInfo);
			}

		}
	}
	
	/**
	 * 符合条件的全部代收货款资金部冻结
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-7 下午1:40:41
	 * @param endSignDate
	 * @param codTypes
	 * @param banks
	 * @param publicPrivateFlag
	 * @return
	 * @throws SettlementException
	 */
	@Override
	@Transactional
	public CODVo fundFreezeCODAll(Date endSignDate, List<String> codTypes,
			List<String> banks, String publicPrivateFlag,
			CurrentInfo currentInfo) throws SettlementException {

		LOGGER.trace("符合条件的全部代收货款资金部冻结Service start,codTypes:"
				+ (codTypes == null ? null : codTypes.toString()));

		// 代收货款类型不能为空
		if (CollectionUtils.isEmpty(codTypes)) {
			throw new SettlementException("代收货款类型为空，不能进行代收货款汇款导出查询");
		}

		// 构造查询参数
		BillCODPayableQueryDto queryDto = new BillCODPayableQueryDto();
		
		/**
		 * @author 218392  zhangyongxue 2015-08-29 16:48:39
		 * 这里加上个判断给isPackage根据是否打包退设置值
		 */
		queryDto.setIsPackage("");//默认不含有打包退,默认是空
		for(String codType : codTypes){
			if(StringUtils.equals("PACK", codType)){
				queryDto.setIsPackage("Y");//如果含有打包退的时候，就将 N 变成 Y
			}
		}

		// 截止签收日期、代收货款类型、银行、对公对私标志
		queryDto.setEndSignDate(endSignDate);
		queryDto.setCodTypes(codTypes);
		queryDto.setBanks(banks);
		queryDto.setPublicPrivateFlag(publicPrivateFlag);

		// 代收货款状态:未退款
		List<String> statuses = new ArrayList<String>();
		statuses.add(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		queryDto.setStatuses(statuses);

		// 是否有效、应付单是否有效、应付单生效状态、应付单单据类型
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		
		// 查询结果
		List<CODPayableDto> queryResult = codCommonService
				.queryAllCODAndBillPayable(queryDto);

		List<CODPayableDto> codEntityBussList = new ArrayList<CODPayableDto>(
				queryResult.size());// 符合业务的代收货款集合
		List<BillPayableEntity> payableEntityApproveList = new ArrayList<BillPayableEntity>(
				queryResult.size());// 符合业务的审核退代收应付单集合
		List<String> waybillChangeFrozenErrorList = new ArrayList<String>(
				queryResult.size()); // 发生更改单的冻结失败的运单号
		List<String> waybillNoErrorList = new ArrayList<String>(
				queryResult.size()); // 无应收的冻结失败的运单号

		this.vaildChangeWaybillAndBillPlayableByCODPayableDto(queryResult,
				waybillChangeFrozenErrorList, codEntityBussList,
				payableEntityApproveList,waybillNoErrorList);

		// 冻结
		this.doFrozenCodPayable(codEntityBussList, payableEntityApproveList,
				currentInfo);

		// 冻结成功后，把冻结后的运单号添加到list上
		@SuppressWarnings("unchecked")
		List<String> waybillNoFreezeList = (List<String>) CollectionUtils
				.collect(codEntityBussList, new Transformer() {
					@Override
					public Object transform(Object input) {
						return ((CODEntity) input).getWaybillNo();
					}
				}); // 冻结成功的运单号

		CODVo vo = new CODVo(); // 返回结果
		if (CollectionUtils.isNotEmpty(waybillNoFreezeList)) {
			// 冻结成功后，把冻结后的代收货款最新数据查询出保存到vo上
			// List<CODDto> codDtoList =
			// codCommonService.queryBillCODPayableByWaybillNos(waybillNoFreezeList);
			vo.setWaybillNoFreezeSuccessList(waybillNoFreezeList); // 把冻结后的代收货款dto设置到vo上，用于界面更新数据
		}
		if (waybillChangeFrozenErrorList.size() > 0) {

			LOGGER.error("运单号["
					+ Arrays.toString(waybillChangeFrozenErrorList.toArray())
					+ "]代收货款存在未受理的始发更改单或到达更改单,不能资金部冻结");
			vo.setWaybillNoFreezeErrorList(waybillChangeFrozenErrorList); // 把冻结失败的运单号设置到vo上，用于界面提示
		}
		
		//268217
		if(waybillNoErrorList.size()>0){
			LOGGER.error("运单号["
					+ Arrays.toString(waybillChangeFrozenErrorList.toArray())
					+ "]不存在代收应收单或代收应收与代收应付金额不一致,不能资金部冻结");
			vo.setWaybillNoErrorList(waybillNoErrorList); // 把冻结失败的运单号设置到vo上，用于界面提示
		}

		LOGGER.trace("符合条件的全部代收货款资金部冻结Service end");

		// 查询并返回结果
		return vo;
	}

	/**
	 * 资金部冻结代收货款.
	 * 
	 * @param entityIds
	 *            the entity ids
	 * @param currentInfo
	 *            the current info
	 * @throws SettlementException
	 *             the settlement exception
	 * @author dp-zengbinwen
	 * @return 存在更改单，冻结失败的运单号List<String>,冻结成功的List<String>
	 * @date 2012-10-17 下午6:42:15
	 */
	@Override
	@Transactional
	public CODVo fundFreezeCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("资金部冻结代收货款Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		List<CODEntity> codEntityList = codCommonService.queryByIds(entityIds);// 获得CODEntity集合

		List<CODPayableDto> codEntityBussList = new ArrayList<CODPayableDto>(
				codEntityList.size());// 符合业务的代收货款集合
		List<BillPayableEntity> payableEntityApproveList = new ArrayList<BillPayableEntity>(
				codEntityList.size());// 符合业务的审核退代收应付单集合
		List<String> waybillChangeFrozenErrorList = new ArrayList<String>(
				codEntityList.size()); // 发生更改单的冻结失败的运单号
		List<String> waybillNoErrorList = new ArrayList<String>(
				codEntityList.size()); // 无应收的冻结失败的运单号

		// 验证
		this.vaildChangeWaybillAndBillPlayableByCodEntity(codEntityList,
				waybillChangeFrozenErrorList, codEntityBussList,
				payableEntityApproveList,waybillNoErrorList);

		// 冻结
		this.doFrozenCodPayable(codEntityBussList, payableEntityApproveList,
				currentInfo);

		// 冻结成功后，把冻结后的运单号添加到list上
		@SuppressWarnings("unchecked")
		List<String> waybillNoFreezeList = (List<String>) CollectionUtils
				.collect(codEntityBussList, new Transformer() {
					@Override
					public Object transform(Object input) {
						return ((CODEntity) input).getWaybillNo();
					}
				}); // 冻结成功的运单号

		CODVo vo = new CODVo(); // 返回结果
		if (CollectionUtils.isNotEmpty(waybillNoFreezeList)) {
			// 冻结成功后，把冻结后的代收货款最新数据查询出保存到vo上
			// List<CODDto> codDtoList =
			// codCommonService.queryBillCODPayableByWaybillNos(waybillNoFreezeList);
			vo.setWaybillNoFreezeSuccessList(waybillNoFreezeList); // 把冻结后的代收货款dto设置到vo上，用于界面更新数据
		}
		if (waybillChangeFrozenErrorList.size() > 0) {

			LOGGER.error("运单号["
					+ Arrays.toString(waybillChangeFrozenErrorList.toArray())
					+ "]代收货款存在未受理的始发更改单或到达更改单,不能资金部冻结");
			vo.setWaybillNoFreezeErrorList(waybillChangeFrozenErrorList); // 把冻结失败的运单号设置到vo上，用于界面提示
		}
		//
		if(waybillNoErrorList.size()>0){
			LOGGER.error("运单号["
					+ Arrays.toString(waybillChangeFrozenErrorList.toArray())
					+ "]代收货款存在未受理的始发更改单或到达更改单,不能资金部冻结");
			vo.setWaybillNoErrorList(waybillNoErrorList); // 把冻结失败的运单号设置到vo上，用于界面提示
		}
		LOGGER.info("资金部冻结代收货款Service end.");
		return vo;

	}

	/**
	 * 符合条件的全部代收货款释放冻结.
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-10 上午9:39:28
	 * @param endSignDate
	 * @param codTypes
	 * @param banks
	 * @param publicPrivateFlag
	 * @param currentInfo
	 * @throws SettlementException
	 */
	@Override
	@Transactional
	public void fundReleaseCODAll(Date endSignDate, List<String> codTypes,
			List<String> banks, String publicPrivateFlag,
			CurrentInfo currentInfo) throws SettlementException {

		LOGGER.info("符合条件的全部代收货款释放冻结Service start");

		// 代收货款类型不能为空
		if (CollectionUtils.isEmpty(codTypes)) {
			throw new SettlementException("代收货款类型为空，不能进行代收货款汇款导出查询");
		}

		// 构造查询参数
		BillCODPayableQueryDto queryDto = new BillCODPayableQueryDto();
		
		/**
		 * @author 218392  zhangyongxue 2015-08-29 16:48:39
		 * 这里加上个判断给isPackage根据是否打包退设置值
		 */
		queryDto.setIsPackage("");//默认不含有打包退,默认是空
		for(String codType : codTypes){
			if(StringUtils.equals("PACK", codType)){
				queryDto.setIsPackage("Y");//如果含有打包退的时候，就将 “” 变成 Y
			}
		}

		// 截止签收日期、代收货款类型、银行、对公对私标志
		queryDto.setEndSignDate(endSignDate);
		queryDto.setCodTypes(codTypes);
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

		// 查询结果
		List<CODPayableDto> queryResult = codCommonService
				.queryAllCODAndBillPayable(queryDto);

		String status = null;
		String codType = null;
		List<BillPayableEntity> payableEntityApproveList = new ArrayList<BillPayableEntity>(); // 符合条件的审核退类型的代收货款应付单
		// 循环对代收货款数据处理
		for (CODPayableDto entity : queryResult) {

			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			status = entity.getStatus();
			// 如果不是资金部冻结，则抛出异常
			if (!SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)) {
				throw new SettlementException("代收货款非资金部冻结");
			}

			// 如果为审核退，更新应付单的状态
			codType = entity.getCodType();
			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
					.equals(codType)) {
				// 把codPayableDto中的应付单数据构建成应付单实体
				BillPayableEntity billPayable = new BillPayableEntity();
				billPayable.setId(entity.getPayableId());
				billPayable.setVersionNo(entity.getPayableVersionNo());
				billPayable.setAccountDate(entity.getPayableAccountDate());
				billPayable.setEffectiveStatus(entity.getEffectiveStatus());
				billPayable.setSignDate(entity.getSignDate());

				payableEntityApproveList.add(billPayable);
			}

		}

		// 取消冻结
		this.doFreezeCodPayable(queryResult, payableEntityApproveList,
				currentInfo);

		LOGGER.info("符合条件的全部代收货款释放冻结Service end.");
	}

	/**
	 * 释放冻结代收货款.
	 * 
	 * @param entityIds
	 *            the entity ids
	 * @param currentInfo
	 *            the current info
	 * @throws SettlementException
	 *             the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-17 下午6:42:47
	 */
	@Override
	@Transactional
	public void fundReleaseCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("释放冻结代收货款Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		List<CODEntity> codEntityList = codCommonService.queryByIds(entityIds);// 获得CODEntity集合

		if (CollectionUtils.isEmpty(codEntityList)) {
			throw new SettlementException("代收货款集合为空");
		}

		@SuppressWarnings("unchecked")
		List<BillPayableEntity> billPayableList = billPayableService
				.queryByWaybillNosAndByBillTypes(
						(List<String>) CollectionUtils.collect(codEntityList,
								new Transformer() {
									@Override
									public Object transform(Object input) {
										return ((CODEntity) input)
												.getWaybillNo();
									}
								}),
						Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD));// 获得代收货款应付单集合

		List<CODPayableDto> codEntityBussList = new ArrayList<CODPayableDto>(
				codEntityList.size());// 符合业务的代收货款集合

		List<BillPayableEntity> payableEntityApproveList = new ArrayList<BillPayableEntity>(); // 符合条件的审核退类型的代收货款应付单

		String status = null;
		String codType = null;
		for (final CODEntity entity : codEntityList) {
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			status = entity.getStatus();
			codType = entity.getCodType();

			// 如果不是资金部冻结，则抛出异常
			if (!SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)) {
				throw new SettlementException("代收货款非资金部冻结");
			}

			CODPayableDto codPayableDto = new CODPayableDto();
			BeanUtils.copyProperties(entity, codPayableDto);
			codEntityBussList.add(codPayableDto);

			// 如果为审核退，更新应付单的状态
			codType = entity.getCodType();
			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
					.equals(codType)) {
				// 查询集合中的代收货款的应付单
				BillPayableEntity billPayable = (BillPayableEntity) CollectionUtils
						.find(billPayableList, new Predicate() {
							@Override
							public boolean evaluate(Object object) {
								if (StringUtils.equals(
										((BillPayableEntity) object)
												.getWaybillNo(), entity
												.getWaybillNo()))
									return true;
								return false;
							}
						});
				payableEntityApproveList.add(billPayable);
			}
		}

		this.doFreezeCodPayable(codEntityBussList, payableEntityApproveList,
				currentInfo);

		LOGGER.info("释放冻结代收货款Service end.");
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
	 * Sets the bill payable service.
	 * 
	 * @param billPayableService
	 *            the new bill payable service
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * Sets the waybill rfc service.
	 * 
	 * @param waybillRfcService
	 *            the new waybill rfc service
	 */
	/*
	 * public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
	 * this.waybillRfcService = waybillRfcService; }
	 */

	/**
	 * Sets the sign change service.
	 * 
	 * @param signChangeService
	 *            the new sign change service
	 */
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	
	/**
	 * @author 268217 
	 * @date  2016-11-29
	 * @param billReceivableService
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

}
