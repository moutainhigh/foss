package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IEffectTl2ByVTSInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VtsToFossTailDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

public class EffectTl2ByVTSInfo implements IEffectTl2ByVTSInfo{
	/** 应付单公共Service. */
	private IBillPayableService billPayableService;
	
	/**
	 * 应付单Dao
	 */
	private IBillPayableEntityDao billPayableEntityDao;
	
	//打印日志使用
		private static final Logger LOGGER = LoggerFactory
				.getLogger(EffectTl2ByVTSInfo.class);
	
	@Override
	@Transactional
	public void effectTl2ByVTSInfo(List<VtsToFossTailDto> effectDtos) {
		if(effectDtos.isEmpty()){
			throw new SettlementException("vts传入参数为空！");
		}
		//循环生效应付单
		for(int i = 0 ;i<effectDtos.size();i++){
			VtsToFossTailDto dto = effectDtos.get(i);
			//检验是否是尾款
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.equals(dto.getBillType()) &&
					SettlementConstants.LINE_SIGN.equals(dto.getSignType())){
				// 生效开始
				int resultValue=this.effectBillPayable(dto);	
				if(resultValue!=1){
					throw new SettlementException("单号为:"+dto.getWaybillNo()+"的运单,该单号生效尾款应付单失败!");
				}
		}else{
				throw new SettlementException("签收不为专线签收,或运单类型不是整车");
			}
			
		}
		
	}
	public int effectBillPayable(VtsToFossTailDto dto){
		/** 返回值. */
		int RESULT_VALUE_VTS = 1;
		//根据运单号和应付单类型等参数，查询有效应付单信息
		BillPayableConditionDto payableConditionDto = new BillPayableConditionDto();
		payableConditionDto.setWaybillNo(dto.getWaybillNo());
		List<BillPayableEntity> billPayables = billPayableService
				.queryBillPayableByCondition(payableConditionDto);
		if (CollectionUtils.isEmpty(billPayables)) {
			RESULT_VALUE_VTS = 2;
			return RESULT_VALUE_VTS;
		}
		
		// 设置存在结算单据标记
		dto.setStlBillCounts(1);
		// 验证应付单
		List<BillPayableEntity> lists = checkBillPayableEntity(dto, billPayables);
		//查询整车尾款应付单
		Predicate predicate = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE,
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST);
		@SuppressWarnings("unchecked")
		List<BillPayableEntity> truckBillPayables = (List<BillPayableEntity>) CollectionUtils.select(lists,
						predicate);
		// 整车运单生效整车尾款应付单
		if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())
						&& FossConstants.YES.equals(dto.getIsWholeVehicle())// 为整车运单
						&& CollectionUtils.isNotEmpty(truckBillPayables)) {
					
		for (BillPayableEntity truckBillPayable : truckBillPayables) {
						
						// 生效应付单方法
			this.enableBillPayable(truckBillPayable, dto);
						
			}
		}
		
		return RESULT_VALUE_VTS;
	}
	/**
	 * 根据单号 查询需要该单号对应的有效数据
	 */
	public List<BillPayableEntity> queryBillPayableByCondition(
			BillPayableConditionDto dto) {

		// 满足以下(运单号和来源单据类型不能为空)条件才能进入，此查询方法
		if (dto != null 
				&& (StringUtils.isNotEmpty(dto.getPayableNo())||StringUtils.isNotBlank(dto.getPaymentNo())
						|| StringUtils.isNotEmpty(dto.getWaybillNo()) || StringUtils
							.isNotEmpty(dto.getSourceBillNo()))) {
			// 有效单据
			dto.setActive(FossConstants.ACTIVE);

			// 非红单
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

			return billPayableEntityDao.queryBillPayableByCondition(dto);
		} else {
			throw new SettlementException("查询应付单，输入的单据编号不能为空！");
		}

	}
	//验证应付单数据.
	private List<BillPayableEntity> checkBillPayableEntity(VtsToFossTailDto dto,
			List<BillPayableEntity> billPayables){
		if (dto == null) {
			throw new SettlementException("接口参数不能为空");
		}
		if (CollectionUtils.isEmpty(billPayables)) {
			return null;
		}
		List<BillPayableEntity> list = new ArrayList<BillPayableEntity>();
		boolean isTL = true;//判断是否含有整车尾款
		for (int i = 0; i < billPayables.size(); i++) {
			BillPayableEntity payableEntity = billPayables.get(i);
			if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.equals(payableEntity.getBillType())
					&&SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO.equals(payableEntity.getEffectiveStatus())) {
				// 专线存在整车尾款应付单，签收类型不为专线时，抛出异常信息
				if (!SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {
					throw new SettlementException("非专线签收，不能存在整车尾款应付单");
				}
				list.add(payableEntity);
				isTL = false;
			}
			else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.equals(payableEntity.getBillType())
					&&SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES.equals(payableEntity.getEffectiveStatus())
					  &&SettlementConstants.LINE_SIGN.equals(dto.getSignType())){
				throw new SettlementException("尾款确认时,该尾款单已是生效状态!!!");
			}
			
		}
		if(isTL){
			throw new SettlementException("尾款确认时，FOSS应付单表中没有未生效的整车尾款应付单！");
		}
		
		return list;
		
	}
	// 生效应付单
	public void enableBillPayable(BillPayableEntity entity, VtsToFossTailDto dto){


		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("生效应付单参数不为空！");
		}

		LOGGER.info("entering service, id: " + entity.getId());

        //初始化日期格式化到秒
        Date now = Calendar.getInstance().getTime();

        //签收时间
        entity.setSignDate(now);

        //生效状态-设置为有效
        entity.setEffectiveStatus(FossConstants.YES);

        //生效日期
        entity.setEffectiveDate(now);

        //生效人编码
        entity.setEffectiveUserCode(dto.getOperatorCode());

        //生效人名称
        entity.setEffectiveUserName(dto.getOperatorName());

        //修改时间
        entity.setModifyTime(now);

        // 操作者信息
        entity.setModifyUserCode(dto.getOperatorCode());

        //修改人名称
        entity.setModifyUserName(dto.getOperatorName());

        int i = billPayableEntityDao.updateByTakeEffect(entity);

		//dao层返回值不等于1时，提示保存异常信息
		if (i != 1) {
			throw new SettlementException("生效应付单出错");
		}
		
		LOGGER.info("successfully exit service, id: " + entity.getId());
	
		
	}
	
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	public void setBillPayableEntityDao(IBillPayableEntityDao billPayableEntityDao) {
		this.billPayableEntityDao = billPayableEntityDao;
	}
}
