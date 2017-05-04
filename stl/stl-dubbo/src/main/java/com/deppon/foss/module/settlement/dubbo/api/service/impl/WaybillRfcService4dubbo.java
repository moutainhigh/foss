package com.deppon.foss.module.settlement.dubbo.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.dubbo.uip.api.define.ReceivableEntity;
import com.deppon.foss.dubbo.uip.api.define.exception.SettlementException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
//import com.deppon.foss.module.base.baseinfo.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.settlement.dubbo.api.dao.IWaybillRfcDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.ActualFreightEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.ArriveSheetConstants;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.dubbo.api.define.LabeledGoodEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.SignConstants;
import com.deppon.foss.module.settlement.dubbo.api.define.StockDto;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillChargeDtlEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillDisDtlEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillDto;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillFRcQueryByWaybillNosDto;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillPaymentEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillRfcConstants;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillRfcExceptionType;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WoodenRequirementsEntity;
import com.deppon.foss.module.settlement.dubbo.api.service.IWaybillRfcService4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.service.WaybillRfcException;
import com.deppon.foss.module.settlement.dubbo.api.util.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.settlement.dubbo.api.util.SettlementConstants;
import com.deppon.foss.module.settlement.dubbo.api.util.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.dubbo.api.util.SettlementUtil;
import com.deppon.foss.util.define.FossConstants;

public class WaybillRfcService4dubbo implements IWaybillRfcService4dubbo {

	/**
	 * 更改单DAO 提供更改单持久化接口
	 */
	@Resource
	private IWaybillRfcDao4dubbo waybillRfcDao4dubbo;

	/**
	 * 客户合同信息DAO接口.
	 */
	@Resource
	private ICusBargainDao cusBargainDao;

	@Resource
	private ICustomerDao customerDao;

	/**
	 * 注入合伙人部门service
	 */
	@Resource
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 注入部门service
	 */
	@Resource
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * @author 327090
	 */
	@Override
	public CusBargainEntity queryCusBargainByCustCode(String custCode) {
		if (StringUtil.isBlank(custCode)) {
			return null;
		} else {
			Date date = new Date();

			CusBargainEntity entity = cusBargainDao.queryCusBargainByCustCode(
					custCode, date, FossConstants.ACTIVE);
			if (entity != null) {
				BigDecimal crmId = entity.getCrmId();
				long count = customerDao.queryIscpackBybargainCrmId(crmId);
				if (count > 0) {
					entity.setIsAccuratePackage("Y");
				}
			}
			return entity;
		}
	}

	/**
	 * @author 327090
	 * @date
	 */
	@Override
	public List<String> isExsitsWayBillRfcs(List<String> waybillNoList)
			throws WaybillRfcException {
		// 如果单号列表大于1000个，返回查询量太大异常
		if (waybillNoList != null && waybillNoList.size() > 1000) {
			throw new WaybillRfcException(
					WaybillRfcExceptionType.QUERY_NUMBER_TOO_GARGE_ERROR_CODE);
		}

		// 如果运单列表大小为空，返回查询运单号为空异常
		if (waybillNoList == null || waybillNoList.size() == 0) {
			throw new WaybillRfcException(
					WaybillRfcExceptionType.QUERY_NUMBER_NULL_ERROR_CODE);

		}

		WaybillFRcQueryByWaybillNosDto waybillFRcQueryByWaybillNosDto = new WaybillFRcQueryByWaybillNosDto();// 创建对象
		waybillFRcQueryByWaybillNosDto.setWaybillNos(waybillNoList);
		waybillFRcQueryByWaybillNosDto
				.setPreAccecpt(WaybillRfcConstants.PRE_ACCECPT);
		waybillFRcQueryByWaybillNosDto
				.setPreAudit(WaybillRfcConstants.PRE_AUDIT);

		return waybillRfcDao4dubbo
				.queryWaybillRfcByWaybillNos(waybillFRcQueryByWaybillNosDto);
	}

	/**
	 * @author 327090
	 */
	@Override
	public void noPayNotAddCode(List<String> waybillNos) {
		// 循环校验每一个运单
		for (int i = 0; i < waybillNos.size(); i++) {
			// 调用接口进行查询运单实体
			WaybillDto waybillDto = queryWaybillByNo(waybillNos.get(i));
			// 判断运单实体DTO是否为空
			if (waybillDto == null) {
				throw new SettlementException("运单 " + waybillNos.get(i)
						+ "没有对应的运单实体信息！");
			}
			// 判断运单基础是否为空
			if (waybillDto.getWaybillEntity() == null) {
				throw new SettlementException("运单 " + waybillNos.get(i)
						+ "没有对应的运单基础信息!");
			}
			// 如果是快递
			if (SettlementUtil.isPackageProductCode(waybillDto
					.getWaybillEntity().getProductCode())) {
				// 到付
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
						.equals(waybillDto.getWaybillEntity().getPaidMethod())) {
					// 判断该运单是否有对应的快递信息
					if (waybillDto.getWaybillExpressEntity() == null) {
						throw new SettlementException("运单 " + waybillNos.get(i)
								+ "没有对应的快递信息!");
					}
					// 未补码
					if (!FossConstants.ACTIVE.equals(waybillDto
							.getWaybillExpressEntity().getIsAddCode())) {
						throw new SettlementException("请在运单 "
								+ waybillNos.get(i) + "补码后再进行支付!");
					}
				}
			}
		}
	}

	/**
	 * @author 327090
	 * @return waybill
	 */
	@Override
	public WaybillDto queryWaybillByNo(String waybillNo) {
		// 定义运单DTO对象
		WaybillDto waybill = new WaybillDto();
		// 设置运单基本信息 editor:306486
		WaybillEntity waybillEntity = waybillRfcDao4dubbo
				.queryWaybillBasicInfoByNo(waybillNo);
		// 获取发货客户精准包裹权限
		if (waybillEntity != null
				&& StringUtil.isNotEmpty(waybillEntity
						.getDeliveryCustomerCode())) {
			CusBargainEntity cusBargainEntity = queryCusBargainByCustCode(waybillEntity
					.getDeliveryCustomerCode());
			waybillEntity
					.setDeliveryCustomerIsAccuratePackage(cusBargainEntity == null ? FossConstants.NO
							: cusBargainEntity.getIsAccuratePackage());
		}
		waybill.setWaybillEntity(waybillEntity);
		// 设置运单费用明细信息
		waybill.setWaybillChargeDtlEntity(queryChargeDtlEntityByNo(waybillNo));
		// 设置运单折扣明细信息
		waybill.setWaybillDisDtlEntity(queryDisDtlEntityByNo(waybillNo));
		// 设置运单付款明细信息
		waybill.setWaybillPaymentEntity(queryWaybillPayment(waybillNo));
		// 设置打木架信息
		waybill.setWoodenRequirementsEntity(queryWoodenRequirements(waybillNo));
		// 设置运单冗余信息
		waybill.setActualFreightEntity(queryActualFreightByNo(waybillNo));

		// TODO
		// 小件添加---------------------------
		if (waybill.getWaybillEntity() != null
				&& (onlineDetermineIsExpressByProductCode(waybill
						.getWaybillEntity().getProductCode(), waybill
						.getWaybillEntity().getBillTime()))) {
			waybill.setWaybillExpressEntity(getWabillExpressEntityByWaybillId(waybill
					.getWaybillEntity().getId()));

			// TODO 返单 返货 都需要判断签收状态的 顶
			List<LabeledGoodEntity> labeledGoodList = queryAllSerialByWaybillNo(waybillNo);
			boolean allSigned = true;

			if (labeledGoodList != null && labeledGoodList.size() > 0) {
				for (int i = 0; i < labeledGoodList.size(); i++) {
					LabeledGoodEntity labeledGood = labeledGoodList.get(i);
					if (labeledGood == null
							|| !FossConstants.ACTIVE.equals(labeledGood.getActive())) {
						continue;
					}

					String signResult = FossConstants.NO;

					// 结算这里没有引入jar所以可能会为null没有注入
					signResult = querySerialNoIsSign(waybillNo,labeledGood.getSerialNo());

					if (!FossConstants.YES.equals(signResult)) {
						allSigned = false;
					}

				}
			}
			// 在判断是否可以返货的时候有用
			waybill.setAllSigned(allSigned);
		}
		// 返回运单DTO
		return waybill;
	}

	@Override
	public List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(
			String waybillNo) {
		return waybillRfcDao4dubbo.queryChargeDtlEntityByNo(waybillNo);
	}

	@Override
	public List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo) {
		return waybillRfcDao4dubbo.queryDisDtlEntityByNo(waybillNo);
	}

	@Override
	public List<WaybillPaymentEntity> queryWaybillPayment(String waybillNo) {
		return waybillRfcDao4dubbo.queryPaymentEntityByNo(waybillNo);
	}

	@Override
	public WoodenRequirementsEntity queryWoodenRequirements(String waybillNo) {
		return waybillRfcDao4dubbo.queryWoodenByNo(waybillNo);
	}

	/**
	 * @author 327090
	 */
	@Override
	public ActualFreightEntity queryActualFreightByNo(String waybillNo) {
		return waybillRfcDao4dubbo.queryByWaybillNo(waybillNo);
	}

	@Override
	public boolean onlineDetermineIsExpressByProductCode(String productCode,
			Date billTime) {
		if (StringUtils.isEmpty(productCode)) {
			return false;
		}
		return waybillRfcDao4dubbo.onlineDetermineIsExpressByProductCode(
				productCode, billTime);
	}

	@Override
	public WaybillExpressEntity getWabillExpressEntityByWaybillId(
			String waybillId) {
		return waybillRfcDao4dubbo.queryWaybillExpressByWaybillId(waybillId);
	}

	@Override
	public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
		return waybillRfcDao4dubbo.queryAllSerialByWaybillNo(waybillNo);
	}

	@Override
	public String querySerialNoIsSign(String waybillNo, String serialNo) {
		if (StringUtils.isBlank(waybillNo)) {
			return FossConstants.NO;
		}
		WaybillEntity waybill = queryPartWaybillByNo(waybillNo);
		if (waybill != null) {
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
					.equals(waybill.getProductCode())
					|| ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
							.equals(waybill.getProductCode())) {
				WaybillSignResultEntity entity = new WaybillSignResultEntity(
						waybillNo, FossConstants.YES);
				if (queryWaybillSignResultByWaybillNo(entity) != null) {
					return FossConstants.YES;
				} else {
					return FossConstants.NO;
				}
			} else {
				WaybillSignResultEntity entity = new WaybillSignResultEntity(
						waybillNo, FossConstants.YES);
				WaybillSignResultEntity result = queryWaybillSignResultByWaybillNo(entity);
				if (result != null
						&& SignConstants.SIGN_STATUS_ALL.equals(result.getSignStatus())) {
					return FossConstants.YES;
				}
				StockDto dto = new StockDto();
				dto.setWaybillNo(waybillNo);
				dto.setSerialNo(serialNo);
				dto.setActive(FossConstants.YES);
				dto.setDestroyed(FossConstants.NO);
				dto.setStatus(ArriveSheetConstants.STATUS_SIGN);
				return waybillRfcDao4dubbo.querySerialNoIsSign(dto);
			}

		} else {
			return FossConstants.NO;
		}
	}

	@Override
	public WaybillEntity queryPartWaybillByNo(String waybillNo) {
		// 查询运单部分数据
		return waybillRfcDao4dubbo.queryPartWaybillByNo(waybillNo);
	}

	@Override
	public WaybillSignResultEntity queryWaybillSignResultByWaybillNo(
			WaybillSignResultEntity entity) {
		return waybillRfcDao4dubbo.queryWaybillSignResult(entity);
	}

	@Override
	public List<ReceivableEntity> queryByWaybillNOs(List<String> waybillNos,
			List<String> waybillList) {
		// 返回DOP应收集合
		List<ReceivableEntity> receivableEntityList = new ArrayList<ReceivableEntity>();
		// 查询应收单
		List<BillReceivableEntity> billReceivablelist = waybillRfcDao4dubbo.queryByWaybillNOs(waybillNos);
		String billTypeDR = "";
		String billTypeOR = "";
		// 判断查询应收单是否为空
		if (CollectionUtils.isEmpty(billReceivablelist)) {
			throw new SettlementException("没有查询到数据。");
		} else {
			// 判断是否存在未受理的更改单
			if (CollectionUtils.isNotEmpty(waybillList)) {
				throw new SettlementException("存在更改单未受理，请联系发货部门。");
			} else {
				// 循环处理应收单
				for (int i = 0; i < billReceivablelist.size(); i++) {
					BillReceivableEntity billReceivableEntity = billReceivablelist.get(i);
					SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
							.querySaleDepartmentInfoByCode(billReceivableEntity
									.getOrigOrgCode());
					// 判断是否存在多条应收单
					if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
							.equals(billReceivableEntity.getBillType())) {
						billTypeDR = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
					}
					if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
							.equals(billReceivableEntity.getBillType())) {
						billTypeOR = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
					}
					if (StringUtil.isNotBlank(billTypeDR)
							&& StringUtil.isNotBlank(billTypeOR)) {
						throw new SettlementException(
								"运单同时存在到达和始发付款金额，请联系提货或发货部门。");
						// 判断应收单是否做了对账单
					} else if (StringUtil.isNotBlank(billReceivableEntity
							.getStatementBillNo())
							&& !SettlementConstants.DEFAULT_BILL_NO
									.equals(billReceivableEntity
											.getStatementBillNo())) {
						throw new SettlementException("已被制作对账单，请联系发货部门。");
						// 判断应收单是否已经全部核销
					} else if (billReceivableEntity.getUnverifyAmount()
							.compareTo(BigDecimal.valueOf(0)) == 0) {
						throw new SettlementException("运单已结清。");
					} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
							.equals(billReceivableEntity.getBillType())
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
									.equals(billReceivableEntity.getBillType())
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
									.equals(billReceivableEntity.getBillType())) {
						throw new SettlementException("运单存在代收货款。");
						// 判断是否合伙人开单
					} else if (null != saleDepartmentEntity
							&& FossConstants.ACTIVE.equals(saleDepartmentEntity
									.getIsLeagueSaleDept())) {
						throw new SettlementException("始发部门为合伙人不能网上支付。");
					} else if (SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_FAILED
							.equals(billReceivableEntity.getWithholdStatus())
							|| SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS
									.equals(billReceivableEntity
											.getWithholdStatus())) {
						throw new SettlementException(
								"应收单扣款状态为扣款成功或失败不能进行网上支付。");
					} else {
						ReceivableEntity receivableEntity = new ReceivableEntity();
						// 应收单单号
						receivableEntity.setReceivableNo(billReceivableEntity
								.getReceivableNo());
						// 未核销金额
						receivableEntity.setUnverifyAmount(billReceivableEntity
								.getUnverifyAmount());
						// 运单号
						receivableEntity.setWaybillNo(billReceivableEntity
								.getWaybillNo());
						// 客户编码
						receivableEntity.setCustomerCode(billReceivableEntity
								.getCustomerCode());
						// 应收部门名称
						receivableEntity.setReceivableOrgName(billReceivableEntity
										.getReceivableOrgName());
						// 是否错误
						receivableEntity.setIsError(FossConstants.NO);
						// 错误消息
						receivableEntity.setErrorMsg(null);
						// 根据部门编码查询部门信息
						OrgAdministrativeInfoEntity orgReceiveEntity = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCode(billReceivableEntity
										.getReceivableOrgCode());
						// 判断部门实体是否为空
						if (orgReceiveEntity != null) {
							// 标杆编码
							receivableEntity.setUnifiedCode(orgReceiveEntity.getUnifiedCode());
						}
						// 将应收实体添加到返回给DOP集合中
						receivableEntityList.add(receivableEntity);
					}
				}
			}
		}
		return receivableEntityList;
	}

	@Override
	public int updateReceiveBillInfoForLock(
			BillReceivableOnlineQueryDto queryDto) {
		return waybillRfcDao4dubbo.updateReceiveBillInfoForLock(queryDto);
	}

	@Override
	public String queryConfValueByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		ConfigurationParamsEntity entityCondition = this.queryConfigurationParamsOneByCode(code);
		if (entityCondition != null) {
			return entityCondition.getConfValue();
		}
		return null;
	}

	@Override
	public ConfigurationParamsEntity queryConfigurationParamsOneByCode(
			String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		ConfigurationParamsEntity entityCondition = new ConfigurationParamsEntity();
		entityCondition.setCode(code);
		List<ConfigurationParamsEntity> entityResults = this.queryConfigurationParamsExactByEntity(entityCondition,
						NumberConstants.ZERO, Integer.MAX_VALUE);
		if (CollectionUtils.isNotEmpty(entityResults)) {
			// 如果返回的有多个，只取第一个
			ConfigurationParamsEntity entityResult = entityResults.get(NumberConstants.ZERO);
			entityResult = this.attachOrg(entityResult);
			return entityResult;
		}
		return null;
	}

	/**
	 * @author 327090
	 * @date 2016-12-2 下午7:43:53
	 */
	@Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
			ConfigurationParamsEntity entity, int start, int limit) {
		// 保证entity对象不为空
		ConfigurationParamsEntity entityCondition = entity == null ? new ConfigurationParamsEntity()
				: entity;
		// 执行查询
		List<ConfigurationParamsEntity> entityResults = waybillRfcDao4dubbo
				.queryConfigurationParamsExactByEntity(entityCondition, start,limit);
		// 返回结果
		return this.attachOrg(entityResults);
	}

	/**
	 * 将部门名称添加进去
	 * 
	 * @author 327090
	 * @date 2016-12-2 下午7:43:53
	 */
	@Override
	public List<ConfigurationParamsEntity> attachOrg(
			List<ConfigurationParamsEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return entitys;
		}

		for (ConfigurationParamsEntity entity : entitys) {
			this.attachOrg(entity);
		}

		return entitys;
	}

	/**
	 * 将部门名称添加进
	 * 
	 * @author 327090
	 * @date 2016-12-2 下午7:43:53
	 */
	@Override
	public ConfigurationParamsEntity attachOrg(ConfigurationParamsEntity entity) {
		if (entity == null || StringUtils.isBlank(entity.getCode())) {
			return entity;
		}

		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(entity.getOrgCode());
		if (org != null) {
			entity.setOrgName(org.getName());
		}

		return entity;
	}

}
