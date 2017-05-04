/**
 * Copyright 2013 STL TEAM
 */

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IAbnormalCargoExamineVtsCheck;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCGrayService;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.AbnormalBillAmountCalculatedDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.AbnormalBillApprovalDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.BillCashCollectionService;
import com.deppon.foss.module.settlement.common.server.service.impl.BillReceivableService;
import com.deppon.foss.module.settlement.common.server.service.impl.CUBCCommonService;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOtherRevenueDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCOtherRevenueRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCOtherRevenueResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteDetailsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRenvenueNoTotalPrtResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCOtherRevenueException;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 小票服务
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-20 上午9:41:59
 */
public class OtherRevenueService extends CUBCCommonService implements IOtherRevenueService {
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.settlement.consumer.server.service.impl.OtherRevenueService";
	
	private ICUBCGrayService cUBCGrayService;
	public void setcUBCGrayService(ICUBCGrayService cUBCGrayService) {
		this.cUBCGrayService = cUBCGrayService;
	}
	private IWaybillDao waybillDao;
    public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	/**
	 * 调用接送货运单Service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 客户信用额度管理服务
	 */
	private ICustomerBargainService customerBargainService;

	/**
	 * 客户Service
	 */
	private ICustomerService customerService;

	/**
	 * 偏线代理服务
	 */
	private IVehicleAgencyCompanyService vehicleAgencyCompanyService;

	/**
	 * 空运代理服务
	 */
	private IAirAgencyCompanyService airAgencyCompanyService;

	/**
	 * 小票单据明细Service
	 */
	private NoteDetailsService noteDetailsService;

	/**
	 * 现金收款单公共服务实现类
	 */
	private BillCashCollectionService billCashCollectionService;

	/**
	 * 应收单服务
	 */
	private BillReceivableService billReceivableService;

	/**
	 * 结算通用服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 小票Dao
	 */
	private IOtherRevenueDao otherRevenueDao;

	/**
	 * 系统配置参数服务
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	
	/**
	 * POS刷卡服务
	 * @author 309603 yangqiang
	 * @date 2016-02-23
	 */
	private IPdaPosManageService pdaPosManageService;
	/**
	 * 调用QMS服务
	 * @author zhang 347069
	 * @date 2016-09-21
	 */
	private IAbnormalCargoExamineVtsCheck abnormalCargoExamineVtsCheck;
	
	public IAbnormalCargoExamineVtsCheck getAbnormalCargoExamineVtsCheck() {
		return abnormalCargoExamineVtsCheck;
	}

	public void setAbnormalCargoExamineVtsCheck(
			IAbnormalCargoExamineVtsCheck abnormalCargoExamineVtsCheck) {
		this.abnormalCargoExamineVtsCheck = abnormalCargoExamineVtsCheck;
	}


	private static final List<String> collectionType = Arrays.asList("A", "G", "D", "DU", "E", "FC", "F", "H", "B", "O", "P", "PD", "POS", "R", "RB", "RFC", "W", "WE", "SB", "DR", "AD", "EC", "RF", "FO", "AC","HC","STORAGE","WS","SU","BU","EH","ZF");
	
	/**
	 * 
	 * 判断小票号是否连续
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-2-27 上午10:11:14
	 */
	public boolean isConsecutiveNum(CurrentInfo currentInfo, String otherRevenueNo) {

		boolean consecutived = true;

		NoteQueryDto noteQueryDto = noteDetailsService.queryNoteApplyInfoByDetailNo(otherRevenueNo, null, null);

		// 校验小票单号是否存在于小票单据明细中
		if (null == noteQueryDto) {
			throw new SettlementException("该小票单号未申请，不能进行新增小票操作!");
		} else {

			// 非本部门申请
			if (!StringUtils.equals(noteQueryDto.getApplyOrgCode(),	currentInfo.getCurrentDeptCode())) {
				throw new SettlementException("该小票单号不是本部门申请，不能进行新增小票操作!");
			}

			// 未入库
			if (!StringUtils.equals(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__IN,	noteQueryDto.getStatus())) {
				throw new SettlementException("小票号已经被使用，或者小票号未入库或者已使用!");
			}

			// 已核销
			if (!StringUtils.equals(SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__NOT_WRITEOFF,noteQueryDto.getWriteoffStatus())) {
				throw new SettlementException("该小票单号处于核销状态，不能进行新增小票操作!");
			}
		}

		// 判断小票单号是否跳号
		long revenueNo = Long.valueOf(otherRevenueNo);
		if (revenueNo > 1) {

			revenueNo = revenueNo - 1;
			String revenueNoStr = String.format("%08d", revenueNo);
			int count = otherRevenueDao.queryOtherRevenueNo(revenueNoStr);
	
			/*
			 * 跳号有两种: 1)同部门跳号 算跳号扔异常提示信息。 2)不同部门跳号 判断与本部门最后一个单号是否连续，不连续扔异常提示信息。
			 */
			if (count == 0) {

				// 查询出是否是申请的小票票据的第一个号
				String endNo = String.valueOf(noteQueryDto.getEndNo());
				String currentDeptCode = currentInfo.getCurrentDeptCode();
				NoteQueryDto checkDto = noteDetailsService.queryMixNoUseDetailsNo(endNo, currentDeptCode);

				// 如果此小票号非小票票据的第一个号码，则判断为非连续的
				if (null != checkDto) {
					if (!StringUtils.equals(otherRevenueNo,	checkDto.getDetailsNo())) {
						consecutived = false;
					}
				}

				// 如果此找不到小票票据，则判断非连续的
				else {
					consecutived = false;
				}
			}
		}

		return consecutived;
	}

	/**
	 * 新加小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午10:03:26
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService#addOtherRevenue(com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto)
	 */
	@Override
	@Transactional
	public String addOtherRevenue(OtherRevenueDto otherRevenueDto, CurrentInfo currentInfo) {
		logger.info("enter addOtherRevenue method ......");
		
		if (null == otherRevenueDto) {
			throw new SettlementException("新增小票失败");
		}
		// 新增一个实体
		OtherRevenueDto dto = otherRevenueDto;
		//获取产品类型
		String productCode  = getProductCode(otherRevenueDto);
		//如果产品类型为快递代理，则不能有临时欠款
		if (SettlementUtil.isPackageProductCode(productCode)
				&& StringUtils.equals(dto.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT)) {

			throw new SettlementException("业务类型为快递代理业务类型或运单号对应运输性质为快递代理类型，则收款方式不能为临时欠款！");
		}
		// 校验保存数据的合法性
		validateInputData(dto, currentInfo);
		
		
		/**
		 * 银行卡检验
		 * @author yangqiang 309603
		 * @date 2016-2-23
		 */
		PosCardEntity    posCardEntity    = null;			//POS实体
		BigDecimal 		 amount		      = null; 			//未使用金额
		BigDecimal       paymentAmount 	  = null;			//收款金额
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(otherRevenueDto.getPaymentType())
				&& (!StringUtil.isBlank(otherRevenueDto.getBatchNo())
						&& otherRevenueDto.getBatchNo().matches("[0-9]+"))
				){
			//查询T+0报表获取未使用金额
			//准备查询数据
			PosCardManageDto posCardManageDto = new PosCardManageDto();
			posCardManageDto.setTradeSerialNo(otherRevenueDto.getBatchNo());
			posCardManageDto.setOrgCode(currentInfo.getCurrentDeptCode());
			//posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_DEPOSIT);
			//查询T+0报表数据
            List<PosCardEntity> posCardEntitys = null;
			posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
			//是否存在
			if (posCardEntitys==null||posCardEntitys.isEmpty()) {
				throw new SettlementException("输入流水号不存在或者输入流水号有误或者所属部门不正确。");
			}else{
				posCardEntity = posCardEntitys.get(0);
			}
			//获取未使用金额
			amount =posCardEntity.getUnUsedAmount();
			//比较
			//收款金额
			paymentAmount = otherRevenueDto.getAmount();
			logger.info("输入的交易流水号:"+posCardEntity.getTradeSerialNo()+"的未使用金额为:"+posCardEntity.getUnUsedAmount());
			logger.info("输入的交易流水号:"+posCardEntity.getTradeSerialNo()+"的实际未使用金额为:"+amount);
			//添加校验T+0报表数据的冻结金额校验
			// 可用金额小于收款金额
			if (amount.compareTo(paymentAmount)<0) {
				throw new SettlementException("可用余额不足。");
			}	
		}
		
		Date date = new Date();
	
		// ID
		String id = UUIDUtils.getUUID();
		dto.setId(id);

		// 金额
		dto.setAmount(BigDecimal.valueOf(dto.getAmount().doubleValue()));
		// 币种
		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		dto.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
		dto.setActive(FossConstants.ACTIVE);// 是否有效
		dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);// 是否红单
		dto.setIsDisable(FossConstants.NO);// 是否作废

		dto.setGeneratingComCode(currentInfo.getDept().getSubsidiaryCode());// 当前子公司编码
		dto.setGeneratingComName(currentInfo.getDept().getSubsidiaryName());// 当前子公司名称
		dto.setGeneratingOrgCode(currentInfo.getCurrentDeptCode());
		dto.setGeneratingOrgName(currentInfo.getCurrentDeptName());

		dto.setCreateOrgCode(currentInfo.getCurrentDeptCode());// 当前部门编码
		dto.setCreateOrgName(currentInfo.getCurrentDeptName());// 当前部门名称
		dto.setCreateUserCode(currentInfo.getEmpCode());// 当前用户编码
		dto.setCreateUserName(currentInfo.getEmpName());// 当前用户名称
		dto.setCreateTime(date);// 创建时间

		dto.setBusinessDate(date);// 业务时间
		dto.setModifyTime(date);// 修改时间
		dto.setModifyUserCode(currentInfo.getEmpCode());// 修改人员编码
		dto.setModifyUserName(currentInfo.getEmpName());// 修改人员名称
		dto.setProductCode(productCode);//设置产品类型
		//当获取不到时默认为02
		if(StringUtils.isEmpty(dto.getInvoiceMark())){
			dto.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		}else{
			dto.setInvoiceMark(otherRevenueDto.getInvoiceMark());
		}
		dto.setNotes(otherRevenueDto.getNotes());

		int retNum = otherRevenueDao.addOtherRevenue(dto);

		if (1 != retNum) {
			throw new SettlementException("新增行数不唯一,新加小票记录失败");
		}
		
		
		// 月结 or 临时欠款
		if (StringUtils.equals(dto.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT)
				|| StringUtils.equals(dto.getPaymentType(),	SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT)) {

			// add 2013-05-30 小票是否可开临欠
			// 判断能否欠款 ；包括超期欠款和信用额度余额欠款
			DebitDto debitDto = customerBargainService.isBeBebt(dto.getCustomerCode(), currentInfo.getCurrentDeptCode(),dto.getPaymentType(), dto.getAmount());

			if (debitDto == null) { // 每个客户和部门都必须有信用额度
				throw new SettlementException("找不到信用额度配置，不能执行此操作");
			} else if (!debitDto.isBeBebt()) { // 客户不能欠款，不能执行操作
				throw new SettlementException(debitDto.getMessage());
			}

			// 欠款费用，扣减客户信用额度
			customerBargainService.updateUsedAmount(dto.getCustomerCode(), currentInfo.getCurrentDeptCode(), dto.getPaymentType(), dto.getAmount(), currentInfo);
		}
		
		// 如果收款方式为现金 则生成现金收款单 否则生成应收单，进入对账单。
		if (StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH, otherRevenueDto.getPaymentType())
				|| StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD, otherRevenueDto.getPaymentType())) {
			// 生成现金收款单实体
			BillCashCollectionEntity billCashCollectionEntity = this.getBillCashCollectoinEntity(otherRevenueDto, currentInfo,date);
			//统一设置产品类型
			billCashCollectionEntity.setProductCode(productCode);
			//如果为包裹，则需要设置出发部门和到达部门对应的快递代理  --默认取当前部门对应的映射
			if(SettlementUtil.isPackageProductCode(productCode)){
				//调用综合接口去查询快递代理点部
				ExpressPartSalesDeptResultDto shareExpressOrg = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(currentInfo.getCurrentDeptCode(),date);
				//判断是否为空
				if(shareExpressOrg==null){
					throw new SettlementException("新增小票的业务类型为快递代理，其当前登录部门对应的快递代理点部为空，不能进行录入操作！");
				}else if(StringUtils.isNotBlank(shareExpressOrg.getUnifiedCode())){
					//设置快递代理点部
					billCashCollectionEntity.setExpressOrigOrgCode(shareExpressOrg.getPartCode());
					billCashCollectionEntity.setExpressOrigOrgName(shareExpressOrg.getPartName());
					billCashCollectionEntity.setExpressDestOrgCode(shareExpressOrg.getPartCode());
					billCashCollectionEntity.setExpressDestOrgName(shareExpressOrg.getPartName());
				}else{
					//设置快递代理点部
					billCashCollectionEntity.setExpressOrigOrgCode(billCashCollectionEntity.getOrigOrgCode());
					billCashCollectionEntity.setExpressOrigOrgName(billCashCollectionEntity.getOrigOrgName());
					billCashCollectionEntity.setExpressDestOrgCode(billCashCollectionEntity.getDestOrgCode());
					billCashCollectionEntity.setExpressDestOrgName(billCashCollectionEntity.getDestOrgName());
				}
				
			}
			billCashCollectionService.addBillCashCollection(billCashCollectionEntity, currentInfo);

		} else {
			// 生成应收单实体
			BillReceivableEntity billReceivableEntity = this.getBillReceivableEntity(otherRevenueDto, currentInfo, date);
			//统一设置产品类型
			billReceivableEntity.setProductCode(productCode);
			//如果为包裹，则需要设置出发部门和到达部门对应的快递代理  --默认取当前部门对应的映射
			if(SettlementUtil.isPackageProductCode(productCode)){
				//调用综合接口去查询快递代理点部
				ExpressPartSalesDeptResultDto shareExpressOrg = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(currentInfo.getCurrentDeptCode(),date);
				//判断是否为空
				if(shareExpressOrg==null){
					throw new SettlementException("新增小票的业务类型为快递代理，其当前登录部门对应的快递代理点部为空，不能进行录入操作！");
				}else if(StringUtils.isNotBlank(shareExpressOrg.getUnifiedCode())){
					//设置快递代理点部
					billReceivableEntity.setExpressOrigOrgCode(shareExpressOrg.getPartCode());
					billReceivableEntity.setExpressOrigOrgName(shareExpressOrg.getPartName());
					billReceivableEntity.setExpressDestOrgCode(shareExpressOrg.getPartCode());
					billReceivableEntity.setExpressDestOrgName(shareExpressOrg.getPartName());
				}else{
					//设置快递代理点部
					billReceivableEntity.setExpressOrigOrgCode(billReceivableEntity.getOrigOrgCode());
					billReceivableEntity.setExpressOrigOrgName(billReceivableEntity.getOrigOrgName());
					billReceivableEntity.setExpressDestOrgCode(billReceivableEntity.getDestOrgCode());
					billReceivableEntity.setExpressDestOrgName(billReceivableEntity.getDestOrgName());
				}
				
			}
			billReceivableService.addBillReceivable(billReceivableEntity, currentInfo);
		}
		

		// 生成小票单据明细实体
		NoteDetailsDto noteDetailsDto = this.updateNoteDetailsInfo(otherRevenueDto.getOtherRevenueNo(), currentInfo, date);
		// 更新小票单据明细信息
		noteDetailsService.updateUserInfoByDetailNo(noteDetailsDto);
		
		/**
		 *更新T+0数据 调用更新数据
		 * @author yangqiang 309603
		 * @date 2016-2-23
		 */
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(otherRevenueDto.getPaymentType())
					&& (!StringUtil.isBlank(otherRevenueDto.getBatchNo())
					&& otherRevenueDto.getBatchNo().matches("[0-9]+"))
					){
				//更新POS刷卡信息
				//准备数据
				posCardEntity.setModifyUser(currentInfo.getEmpName());											//更新人名称
				posCardEntity.setModifyUserCode(currentInfo.getEmpCode());										//更新人编码
				posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(paymentAmount));					//已使用金额
				posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(paymentAmount));   								//未使用金额
				pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
				//插入新的POS刷卡明细
				//准备数据
				PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
				posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
				posCardDetailEntity.setInvoiceType("XP");													//小票
				posCardDetailEntity.setInvoiceNo(otherRevenueDto.getOtherRevenueNo());						//小票号
				posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								//创建人名称
				posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							//创建人编码
				posCardDetailEntity.setAmount(paymentAmount);												//发生金额
				posCardDetailEntity.setOccupateAmount(paymentAmount);										//已占用金额
				posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);										//未核销金额
				pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
			}
		
		return id;
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.settlement.consumer.server.service.impl.OtherRevenueService.addOtherRevenueWayBill
	 * @Description:在结清货款的时候生成保管费小票记录已移到CUBC，现在只做现金收款单和小票明细单据
	 *
	 * @param otherRevenueDto
	 * @param currentInfo
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 下午4:44:26
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-20    130376-YANGKANG      v1.0.0         create
	 */
	@Override
	public String addOtherRevenueByWayBill(OtherRevenueDto otherRevenueDto,	CurrentInfo currentInfo) {
		logger.info("enter addOtherRevenueByWayBill method ......");
		
		if (null == otherRevenueDto) {
			throw new SettlementException("新增小票失败");
		}
		// 新增一个实体
		OtherRevenueDto dto = otherRevenueDto;
		//获取产品类型
		String productCode  = getProductCode(otherRevenueDto);
		// 校验保存数据的合法性
		validateRepaymentInputData(dto, currentInfo);
		
		Date date = new Date();
	
		// ID
		String id = UUIDUtils.getUUID();
		dto.setId(id);

		// 金额
		dto.setAmount(BigDecimal.valueOf(dto.getAmount().doubleValue()));
		// 币种
		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		dto.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
		dto.setActive(FossConstants.ACTIVE);// 是否有效
		dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);// 是否红单
		dto.setIsDisable(FossConstants.NO);// 是否作废

		dto.setGeneratingComCode(currentInfo.getDept().getSubsidiaryCode());// 当前子公司编码
		dto.setGeneratingComName(currentInfo.getDept().getSubsidiaryName());// 当前子公司名称
		dto.setGeneratingOrgCode(currentInfo.getCurrentDeptCode());
		dto.setGeneratingOrgName(currentInfo.getCurrentDeptName());

		dto.setCreateOrgCode(currentInfo.getCurrentDeptCode());// 当前部门编码
		dto.setCreateOrgName(currentInfo.getCurrentDeptName());// 当前部门名称
		dto.setCreateUserCode(currentInfo.getEmpCode());// 当前用户编码
		dto.setCreateUserName(currentInfo.getEmpName());// 当前用户名称
		dto.setCreateTime(date);// 创建时间

		dto.setBusinessDate(date);// 业务时间
		dto.setModifyTime(date);// 修改时间
		dto.setModifyUserCode(currentInfo.getEmpCode());// 修改人员编码
		dto.setModifyUserName(currentInfo.getEmpName());// 修改人员名称
		dto.setProductCode(productCode);//设置产品类型
		//当获取不到时默认为02
		if(StringUtils.isEmpty(dto.getInvoiceMark())){
			dto.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		}else{
			dto.setInvoiceMark(otherRevenueDto.getInvoiceMark());
		}
		dto.setNotes(otherRevenueDto.getNotes());

		int retNum = otherRevenueDao.addOtherRevenue(dto);

		if (1 != retNum) {
			throw new SettlementException("新增行数不唯一,新加小票记录失败");
		}
		
				
		// 如果收款方式为现金 则生成现金收款单 否则生成应收单，进入对账单。
		if (StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH, otherRevenueDto.getPaymentType())
				|| StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD,	otherRevenueDto.getPaymentType())) {
			// 生成现金收款单实体
			BillCashCollectionEntity billCashCollectionEntity = this.getBillCashCollectoinEntity(otherRevenueDto, currentInfo, date);
			//统一设置产品类型
			billCashCollectionEntity.setProductCode(productCode);

			billCashCollectionService.addBillCashCollection(billCashCollectionEntity, currentInfo);

		}
		
		// 生成小票单据明细实体
		NoteDetailsDto noteDetailsDto = this.updateNoteDetailsInfo(otherRevenueDto.getOtherRevenueNo(), currentInfo, date);
		// 更新小票单据明细信息
		noteDetailsService.updateUserInfoByDetailNo(noteDetailsDto);

		return FossConstants.YES;
	}

	/**
	 * 通过小票信息生成应收单实体
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-26 下午5:11:03
	 */
	private BillReceivableEntity getBillReceivableEntity(OtherRevenueDto otherRevenueDto, CurrentInfo currentInfo, Date date) {
		BillReceivableEntity entity = new BillReceivableEntity();

		entity.setId(UUIDUtils.getUUID());
		entity.setSourceBillNo(otherRevenueDto.getOtherRevenueNo()); // 来源单据单号
		entity.setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS7));
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__OTHER_REVENUE);// 来源单据类型
		entity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__OTHER_REVENUE_RECEIVABLE);// 单据子类型
		entity.setWaybillId(null); // 运单ID
		entity.setWaybillNo(otherRevenueDto.getWaybillNo()); // 运单号
		entity.setPaymentType(otherRevenueDto.getPaymentType()); // 付款方式
		entity.setCollectionType(otherRevenueDto.getIncomeCategories()); // 收入类别
		entity.setActive(FossConstants.ACTIVE); // 是否有效
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO); // 是否红单
		entity.setIsInit(FossConstants.NO); // 是否初始化
		entity.setVersionNo(FossConstants.INIT_VERSION_NO); // 初始化版本号
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO); // 系统生成方式

		entity.setAmount(otherRevenueDto.getAmount());// 收款金额
		entity.setUnverifyAmount(otherRevenueDto.getAmount());// 未核销金额
		entity.setVerifyAmount(BigDecimal.ZERO);// 核销金额默认为0
		entity.setCurrencyCode(otherRevenueDto.getCurrencyCode()); // 币种
		entity.setNotes(otherRevenueDto.getNotes()); // 备注

		entity.setCustomerCode(otherRevenueDto.getCustomerCode());// 客户编码
		entity.setCustomerName(otherRevenueDto.getCustomerName());// 客户名称
		entity.setGeneratingOrgCode(currentInfo.getCurrentDeptCode()); // 收入部门编码
		entity.setGeneratingOrgName(currentInfo.getCurrentDeptName()); // 收入部门名称
		//统一结算应收、催款取合同部门
		if(FossConstants.YES.equals(otherRevenueDto.getUnifiedSettlement())){
			entity.setReceivableOrgCode(otherRevenueDto.getContractOrgCode()); // 应收部门编码
			entity.setReceivableOrgName(otherRevenueDto.getContractOrgName()); // 应收部门名称
			entity.setDunningOrgCode(otherRevenueDto.getDunningOrgCode());// 催款部门
			entity.setDunningOrgName(otherRevenueDto.getDunningOrgName());// 催款部门
		}else{
			entity.setReceivableOrgCode(currentInfo.getCurrentDeptCode()); // 应收部门编码
			entity.setReceivableOrgName(currentInfo.getCurrentDeptName()); // 应收部门名称
			entity.setDunningOrgCode(currentInfo.getCurrentDeptCode());// 催款部门
			entity.setDunningOrgName(currentInfo.getCurrentDeptName());// 催款部门
		}

		entity.setGeneratingComCode(currentInfo.getDept().getSubsidiaryCode());// 当前子公司编码
		entity.setGeneratingComName(currentInfo.getDept().getSubsidiaryName());// 当前子公司名称
		entity.setBusinessDate(date); // 业务日期
		entity.setAccountDate(date); // 记账日期
		entity.setConrevenDate(date); // 确认收入日期
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);// 审核状态默认已审核
		
		//始发部门、到达部门
		entity.setOrigOrgCode(currentInfo.getCurrentDeptCode());
		entity.setOrigOrgName(currentInfo.getCurrentDeptName());
		entity.setDestOrgCode(currentInfo.getCurrentDeptCode());
		entity.setDestOrgName(currentInfo.getCurrentDeptName());
		//ddw
		entity.setInvoiceMark(otherRevenueDto.getInvoiceMark());
		entity.setUnifiedSettlement(otherRevenueDto.getUnifiedSettlement());
		entity.setContractOrgCode(otherRevenueDto.getContractOrgCode());
		entity.setContractOrgName(otherRevenueDto.getContractOrgName());
		
		return entity;
	}

	/**
	 * 通过小票信息生成现金收款单实体
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-26 下午3:38:44
	 */
	public BillCashCollectionEntity getBillCashCollectoinEntity(OtherRevenueDto otherRevenueDto, CurrentInfo currentInfo, Date date) {
		BillCashCollectionEntity entity = new BillCashCollectionEntity(); // 构造现金收款单实体
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE); // 是否有效
		entity.setIsInit(FossConstants.NO); // 是否初始化
		entity.setVersionNo(FossConstants.INIT_VERSION_NO); // 初始化版本号
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO); // 是否红冲
		entity.setCreateTime(date); // 创建时间
		entity.setModifyTime(date); // 修改时间
		entity.setAccountDate(date); // 记账日期
		entity.setBusinessDate(date); // 业务日期
		entity.setConrevenDate(date);// 确认收入日期
		entity.setStatus(SettlementDictionaryConstants.BILL_CASH_COLLECTION__STATUS__SUBMIT); // 单据状态
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__OTHER_REVENUE);// 来源单据类型
		entity.setBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE__CASH_COLLECTION); // 单据类型
		entity.setCollectionType(otherRevenueDto.getIncomeCategories()); // 收入类别
		String cashCollectionNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.XS2);// 生成小票现金收款单号
		entity.setCashCollectionNo(cashCollectionNo);
		entity.setSourceBillNo(otherRevenueDto.getOtherRevenueNo());// 小票单号
		entity.setAmount(otherRevenueDto.getAmount());// 收款金额
        entity.setPaymentType(otherRevenueDto.getPaymentType()); // 收款类型
		entity.setWaybillNo(otherRevenueDto.getWaybillNo());// 运单号码
		entity.setCustomerCode(otherRevenueDto.getCustomerCode());// 客户编码
		entity.setCustomerName(otherRevenueDto.getCustomerName());// 客户名称
		entity.setCurrencyCode(otherRevenueDto.getCurrencyCode()); // 币种
        entity.setBatchNo(otherRevenueDto.getBatchNo()); // 银联交易流水号
		entity.setCollectionOrgCode(currentInfo.getCurrentDeptCode()); // 收款部门编码
		entity.setCollectionOrgName(currentInfo.getCurrentDeptName()); // 收款部门名称
		entity.setCollectionCompanyCode(currentInfo.getDept().getSubsidiaryCode());// 收款部门所属子公司编码
		entity.setCollectionCompanyName(currentInfo.getDept().getSubsidiaryName());// 收款部门所属子公司名称
		entity.setGeneratingOrgCode(currentInfo.getCurrentDeptCode()); // 收入部门编码
		entity.setGeneratingOrgName(currentInfo.getCurrentDeptName()); // 收入部门名称
		entity.setGeneratingCompanyCode(currentInfo.getDept().getSubsidiaryCode()); // 收入部门所属子公司编码
		entity.setGeneratingCompanyName(currentInfo.getDept().getSubsidiaryName());// 收入部门所属子公司名称
		entity.setNotes(otherRevenueDto.getNotes());// 备注
		
		//始发部门、到达部门
		entity.setOrigOrgCode(currentInfo.getCurrentDeptCode());
		entity.setOrigOrgName(currentInfo.getCurrentDeptName());
		entity.setDestOrgCode(currentInfo.getCurrentDeptCode());
		entity.setDestOrgName(currentInfo.getCurrentDeptName());
		//ddw
		entity.setInvoiceMark(otherRevenueDto.getInvoiceMark());
		
		return entity;
	}

	/**
	 * 设置小票明细使用人信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-3 下午3:24:41
	 */
	public NoteDetailsDto updateNoteDetailsInfo(String otherRenvenueNo, CurrentInfo currentInfo, Date date) {
		// 实例化小票明细实体
		NoteDetailsDto noteDetailsDto = new NoteDetailsDto();
		noteDetailsDto.setUseTime(date);
		noteDetailsDto.setUseOrgCode(currentInfo.getCurrentDeptCode());// 使用部门编码
		noteDetailsDto.setUseOrgName(currentInfo.getCurrentDeptName());// 使用部门名称
		noteDetailsDto.setUserCode(currentInfo.getEmpCode());// 使用人员编码
		noteDetailsDto.setUserName(currentInfo.getEmpName());// 使用人员名称
		noteDetailsDto.setStatus(SettlementDictionaryConstants.NOTE_DETAILS__STATUS__USED);// 已使用
		noteDetailsDto.setNoteDetailsNo(otherRenvenueNo);// 小票单据号码
		noteDetailsDto.setNotereqType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__OTHER_REVENUE);// 使用小票单据的票据类型

		return noteDetailsDto;
	}

	/**
	 * 校验保存数据的合法性
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-22 上午9:17:14
	 */
	private void validateInputData(OtherRevenueDto dto, CurrentInfo currentInfo) {
		/**
		 * 判断是否有输入参数
		 */
		if (null == dto) {
			throw new SettlementException("保存参数有误");
		}

		/*
		 * 因包装其他应收添加收款类别
		 * 增加对小票的收款类别的限制
		 * 
		 * 105762 Yang Shushuo 2014-6-27 下午6:17:12
		 */
		if(collectionType.indexOf(dto.getIncomeCategories()) == -1){
			throw new SettlementException("收入类别不合法");
		}
		
		
		/**
		 * 判断非月结客户是否是付款方式为月结的小票，如果是，则抛出异常
		 */
		// 客户编码和付款方式
		String customerCode = dto.getCustomerCode();
		String customerType = dto.getCustomerType();
		String paymentType = dto.getPaymentType();

		//收银员利息 付款方式必须为现金
		if(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__INTEREST.equals(dto.getIncomeCategories())){
			if(!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(dto.getPaymentType())){
				throw new SettlementException("收入类别为“收银员卡利息”付款方式必须为“现金”");
			}
		}

		//收入类别为其它 备注必填
		if(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__OTHER.equals(dto.getIncomeCategories())){
			if(StringUtil.isBlank(dto.getNotes())){
				throw new SettlementException("收入类别为“租房违约金”必须填写“备注”");
			}
		}

		//收入类别为 FC（富余仓库转租收入）、RB（还借支），新增
		if(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__FC.equals(dto.getIncomeCategories())||
				SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__REPAY_DEBIT.equals(dto.getIncomeCategories())){
			if(!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(dto.getPaymentType())
					&&!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getPaymentType())){

				throw new SettlementException("收入类别为“富余仓库转租收”或者“还借支”，付款方式必须为“现金”或者“银行卡”");
			}
		}
		
		//快递代理必须填写运单号
		if(SettlementUtil.isPackageProductCode(dto.getProductCode())){
			if(StringUtil.isBlank(dto.getWaybillNo())){
				throw new SettlementException("产品类型为 快递代理必须填写运单号");
			}
		}
        /**
         * POS机需求，付款方式为银行卡需填写银联交易流水号
         *
         * @author 105762
         */
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)) {
            String batchNo = dto.getBatchNo();
            if(batchNo == null || !batchNo.matches("^[0-9]+$")){
                throw new SettlementException("付款方式为银行卡时需填写银联交易流水号,请输入由多为数字组成的银联交易流水号");
            }
        }

            // 如果付款方式为月结
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(paymentType)) {

			//付款方式为月结的小票客户编码不能为空
			if(StringUtils.isEmpty(customerCode)){
				throw new SettlementException("付款方式为月结的小票客户编码不能为空");
			}
			
			if (SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER.equals(customerType)) {

				// 查询客户信息
				CustomerDto customerDto = this.getCustomerInfo(customerCode);
				if (customerDto != null) {

					// 获取客户合同列表
					List<CusBargainEntity> bargainList = customerDto.getBargainList();

					// 是否为月结客户
					boolean isCreditCustomer = false;

					// 月结付款方式
					String monthChargeType = DictionaryValueConstants.CLEARING_TYPE_MONTH;

					// 判断是否有有效的月结合同
					if (CollectionUtils.isNotEmpty(bargainList)) {
						for (CusBargainEntity entity : bargainList) {

							// 合同有效且结算方式为月结
							if (entity != null && FossConstants.ACTIVE.equals(entity.getActive())
									&& monthChargeType.equals(entity.getChargeType())) {

								// 为月结客户
								isCreditCustomer = true;
								break;
							}
						}
					}

					// 如果为非月结客户，则抛出异常
					if (!isCreditCustomer) {
						throw new SettlementException("客户编码为:" + customerCode + "的客户为非月结客户，不能新增付款方式为月结的小票");
					}

				}
			}
		}

		/**
		 * 校验小票单号是否合法
		 */
		String otherRevenueNo = dto.getOtherRevenueNo();
		int count = otherRevenueDao.queryOtherRevenueNo(otherRevenueNo);
		if (count > 0) {
			throw new SettlementException("编号:"+otherRevenueNo+":该小票单号已存在");
		}
		NoteQueryDto noteQueryDto = noteDetailsService.queryNoteApplyInfoByDetailNo(dto.getOtherRevenueNo(), null, null);
		// 校验小票单号是否存在于小票单据明细中
		if (null == noteQueryDto) {
			throw new SettlementException("该小票单号未申请，不能进行新增小票操作!");
		} else {
			if (!StringUtils.equals(noteQueryDto.getApplyOrgCode(),	currentInfo.getCurrentDeptCode())) {
				throw new SettlementException("该小票单号不是本部门申请，不能进行新增小票操作!");
			}
			if (!StringUtils.equals(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__IN,	noteQueryDto.getStatus())) {
				throw new SettlementException("小票单号状态为已入库,才能进行新增小票操作!");
			}
			if (!StringUtils.equals(SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__NOT_WRITEOFF, noteQueryDto.getWriteoffStatus())) {
				throw new SettlementException("该小票单号处于核销状态，不能进行新增小票操作!");
			}
		}
		
		// 查询小票单据明细表
		if (StringUtils.isNotBlank(dto.getWaybillNo())) {
			// 调用接送货接口，判断运单信息是否存在
			boolean bl = waybillManagerService.isWayBillExsits(dto.getWaybillNo());
			if (!bl) {
				throw new SettlementException("运单信息不存在。");
			}
		}

        if (FossConstants.YES.equals(dto.getUnifiedSettlement())) {
            if (StringUtils.isBlank(dto.getContractOrgCode())) {
                throw new SettlementException("统一结算合同部门编码不能为空。");
            }
            if (StringUtils.isBlank(dto.getContractOrgName())) {
                throw new SettlementException("统一结算合同部门名称不能为空。");
            }
            if (StringUtils.isBlank(dto.getDunningOrgCode())) {
                throw new SettlementException("统一结算催款部门编码不能为空。");
            }
            if (StringUtils.isBlank(dto.getDunningOrgName())) {
                throw new SettlementException("统一结算催款部门名称不能为空。");
            }
		}
	}

	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.settlement.consumer.server.service.impl.OtherRevenueService.validateInputDataWayBill
	 * @Description:校验结清货款界面需要新增的小票信息是否符合要求
	 *
	 * @param dto
	 * @param currentInfo
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 下午4:57:30
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-20    130376-YANGKANG      v1.0.0         create
	 */
	private void validateRepaymentInputData(OtherRevenueDto dto, CurrentInfo currentInfo) {
		if (null == dto) {
			throw new SettlementException("保存参数有误");
		}
		
		String otherRevenueNo = dto.getOtherRevenueNo();
		if(otherRevenueNo == null || otherRevenueNo == ""){
			throw new SettlementException("小票单号为空！");
		}
		int count = otherRevenueDao.queryOtherRevenueNo(otherRevenueNo);
		if (count > 0) {
			throw new SettlementException("编号:"+otherRevenueNo+":该小票单号已存在");
		}
		NoteQueryDto noteQueryDto = noteDetailsService.queryNoteApplyInfoByDetailNo(dto.getOtherRevenueNo(), null, null);
		// 校验小票单号是否存在于小票单据明细中
		if (null == noteQueryDto) {
			throw new SettlementException("该小票单号未申请，不能进行新增小票操作!");
		} else {
			if (!StringUtils.equals(noteQueryDto.getApplyOrgCode(), currentInfo.getCurrentDeptCode())) {
				throw new SettlementException("该小票单号不是本部门申请，不能进行新增小票操作!");
			}
			if (!StringUtils.equals(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__IN,	noteQueryDto.getStatus())) {
				throw new SettlementException("小票单号状态为已入库,才能进行新增小票操作!");
			}
			if (!StringUtils.equals(SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__NOT_WRITEOFF, noteQueryDto.getWriteoffStatus())) {
				throw new SettlementException("该小票单号处于核销状态，不能进行新增小票操作!");
			}
		}
		
		// 查询小票单据明细表
		if (StringUtils.isNotBlank(dto.getWaybillNo())) {
			// 调用接送货接口，判断运单信息是否存在
			boolean bl = waybillManagerService.isWayBillExsits(dto.getWaybillNo());
			if (!bl) {
				throw new SettlementException("运单信息不存在。");
			}
		}
	}

	/**
	 * 
	 * 根据客户编码获取客户信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-21 下午5:26:43
	 */
	@Override
	public CustomerDto getCustomerInfo(String customerCode) {
		return customerService.queryCustInfoByCode(customerCode);
	}

	/**
	 * 
	 * 根据客户代理编码获取偏线、空运代理信息
	 * 
	 * @Title: getPartLineAgencyInfo
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-26 下午4:14:12
	 * @param @param customerCode
	 * @param @return 设定文件
	 * @return BusinessPartnerEntity 返回类型
	 * @throws
	 */
	@Override
	public BusinessPartnerEntity getAgencyInfo(String customerCode,	String customerType) {

		// 客户代理编码为空
		if (StringUtils.isEmpty(customerCode)) {
			throw new SettlementException("客户代理编码为空");
		}

		// 客户类型为空
		if (StringUtils.isEmpty(customerType)) {
			throw new SettlementException("客户类型为空");
		}

		// 偏线代理
		if (SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY.equals(customerType)) {
			return vehicleAgencyCompanyService.queryEntityByCode(customerCode);
		}

		// 空运代理
		if (SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY.equals(customerType)) {
			return airAgencyCompanyService.queryEntityByCode(customerCode);
		}

		// 反回空
		return null;
	}

	/**
	 * 
	 * 查询客户信息
	 * 
	 * @Title: queryCustomerInfo
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-26 下午4:39:03
	 * @param @param dto
	 * @param @return 设定文件
	 * @return CustomerInfoDto 返回类型
	 * @throws
	 */
	public CustomerInfoDto queryCustomerInfo(CustomerInfoDto dto) {

		// 查询条件对象为空
		if (dto == null) {
			throw new SettlementException("查询条件对象为空");
		}

		// 创建返回值信息
		CustomerInfoDto result = new CustomerInfoDto();
		BeanUtils.copyProperties(dto, result);

		// 查询客户信息
		if (SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER.equals(dto.getType())) {

			// 查询客户
			CustomerDto customerDto = getCustomerInfo(dto.getCode());
			if (customerDto != null) {
				result.setName(customerDto.getName());

				// 获取客户联系人电话
				if (CollectionUtils.isNotEmpty(customerDto.getContactList())) {

					// 取第一个联系人
					ContactEntity entity = customerDto.getContactList().get(0);
					if (entity != null) {
						/* 09203-foss bochenlong
						 * 
						 * 如果移动电话不为空，取移动电话；否则，取固定电话
						 * 如果固定电话为空，则界面显示空
						 */
						if(entity.getMobilePhone() != null) {
							result.setPhone(entity.getMobilePhone());
						} else {
							result.setPhone(entity.getContactPhone());
						}
						
					}
				}
			}
		}

		// 偏线代理、空运代理
		else if (SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY.equals(dto.getType())
				|| SettlementDictionaryConstants.SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY.equals(dto.getType())) {

			// 查询代理信息
			BusinessPartnerEntity agency = this.getAgencyInfo(dto.getCode(), dto.getType());

			if (agency != null) {
				result.setName(agency.getAgentCompanyName());
				result.setPhone(agency.getMobilePhone());
			}
		}

		return result;
	}
	
	
	/**针对于财务经理才可以作废还借支的小票
	 * @author 310970 
	 * BILL_RECEIVABLE__COLLECTION_TYPE__REPAY_DEBIT   还借支的小票
	 * @param  otherRevenueDto,currentInfo
	 */
	@Override
	@Transactional
	public void cancelOtherRevenueOfRB(OtherRevenueDto otherRevenueDto,
			CurrentInfo currentInfo) {
		
		Date date = new Date();
		// 输入参数
		if (null == otherRevenueDto) {
			throw new SettlementException("作废小票参数有误，作废失败!");
		}
		if (!StringUtils.equals(FossConstants.ACTIVE, otherRevenueDto.getActive())) {
			throw new SettlementException("小票无效不能进行作废操作");
		}
		logger.info("开始小票作废,otherRenvenueNo:"	+ otherRevenueDto.getOtherRevenueNo());
		// 查询最新小票单据
		OtherRevenueEntity ore = getOtherRevenueEntityByNo(otherRevenueDto.getOtherRevenueNo(), otherRevenueDto.getActive());
		if(!SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__REPAY_DEBIT.equals(ore.getIncomeCategories())){
			throw new SettlementException("此类小票,您无权限作废");
		}
		OtherRevenueEntity entity = this.addCancelOtherRevenue(ore, date, currentInfo);
		/**
		 * 作废小票操作
		 */
		cancelOtherRevenueOperator(entity, currentInfo, date);
		/**
		 * 小票应收单 和 现金收款单红冲操作
		 */
		// 红冲小票 应收单或现金收款单
		writeBackOtherRevenueNo(entity, currentInfo);
		
		if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(ore.getPaymentType())){
			// 释放NCI项目中T+0报表和作废明细
			PosCardDetailEntity pd = new PosCardDetailEntity();
			// 设置单据号
			pd.setInvoiceNo(otherRevenueDto.getOtherRevenueNo());
			
			pd.setInvoiceType("XP");
			/**
			 * 更新人
			 */
			pd.setModifyUser(currentInfo.getEmpName());
			pd.setModifyUserCode(currentInfo.getEmpCode());
			
			//判断单号是否来自于NCI项目
			List<PosCardDetailEntity> list = pdaPosManageService.queryPosDetailsByNo(pd);
			if(CollectionUtils.isNotEmpty(list)){
				// 释放T+0报表的金额和删除明细数据
				pdaPosManageService.updatePosAndDeleteDetail(pd);
			}
		}

		logger.info("结束作废运单：" + otherRevenueDto.getOtherRevenueNo());
		
	}
	
	
	/**
	 * 作废小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午10:03:33
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService#cancelOtherRevenue(com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto)
	 */
	@Override
	@Transactional   
	public void cancelOtherRevenue(OtherRevenueDto otherRevenueDto,	CurrentInfo currentInfo) {
		// 输入参数
		if (null == otherRevenueDto) {
			throw new SettlementException("作废小票参数有误，作废失败!");
		}
		if (!StringUtils.equals(FossConstants.ACTIVE, otherRevenueDto.getActive())) {
			throw new SettlementException("小票无效不能进行作废操作");
		}
		logger.info("开始小票作废,otherRenvenueNo:"	+ otherRevenueDto.getOtherRevenueNo());

		Date date = new Date();

		// 查询最新小票单据
		OtherRevenueEntity ore = getOtherRevenueEntityByNo(otherRevenueDto.getOtherRevenueNo(), otherRevenueDto.getActive());
		/**
		 * 如果作废的小票记录是结清货款时产生的保管费小票记录，则不需要以下判断
		 * author yangkang
		 */
		
		if(!SettlementDictionaryConstants.SETTLEMENT_INVOICE_CATEGORY.equals(ore.getInvoiceCategory())){
			// 判断是否超过了作废期限,如果时间差超过期限,抛出异常提示
			String span = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_INVOICE_CANCELABLE_DAY);
			
			//如果配置参数为空，抛出异常
			if(StringUtils.isEmpty(span)){
				throw new SettlementException("配置参数【小票作废期限】未配置，请配置后再操作");
			}
			
			                          
			Date d = DateUtils.addDays(ore.getCreateTime(), Integer.valueOf(span));
			//针对于还借支小票作废，已有的作废情况不变，过期作废 提醒当前作废用户找财务经理
			if(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__REPAY_DEBIT.equals(ore.getIncomeCategories())){
				throw new SettlementException("超出业务范围,不允许进行作废此类小票操作，请联系财务经理");
		}
			if (date.after(d)) {
				throw new SettlementException("超出结算业务红冲的最大时间范围，不允许进行作废小票操作");
			}	
		}
		//如果为上门接货费用或者自提改派送，需要去中转判断下是否存在临时租车绑定的数据
		if(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__PICKUP_DELIVERY.equals(ore.getIncomeCategories()) 
			||SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__FUEL.equals(ore.getIncomeCategories())){
			//判断是否存在绑定临时租车小票
			List<OtherRevenueDto> list = this.otherRevenueDao.isExistRentCarOtherNos(ore.getOtherRevenueNo());
			if(CollectionUtils.isNotEmpty(list)){
				OtherRevenueDto dto = list.get(0);
				throw new SettlementException("小票单号已经绑定临时租车记录"+dto.getRentCarNo()+"，不能作废。请先取消临时租车标记！");
			}
		}
		//如果作废的小票类型是异常货处置,则需要进行一下判断
		//No.347069
		if(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__EXCEPTIONAL_HANDLING.equals(ore.getIncomeCategories())){
			logger.debug("查询小票号:" + ore.getOtherRevenueNo() + "的QMS异常货审批工作流");
			//调用QMS接口判断工作流审批结果
			AbnormalBillApprovalDto dto = new AbnormalBillApprovalDto();
			try {
					dto = abnormalCargoExamineVtsCheck.sendVtsPayableCheck(ore.getOtherRevenueNo());
				} catch (IOException e) {
					throw new SettlementException("网络异常,查询小票异常审批失败");
				}
		
			if(dto != null){
				//处置状态
				//审批同意
				if(dto.getHandleStatus().equalsIgnoreCase("2")){
					//提示不能作废小票
					throw new SettlementException("该小票处置审批同意,不能作废小票");
				//待审批/
				}else if(dto.getHandleStatus().equalsIgnoreCase("1")){
					//先退回QMS异常货处置工作流，再在FOSS系统作废小票。
					throw new SettlementException("先退回QMS异常货处置工作流，再在FOSS系统作废小票");
				}
			}
		}
		/**
		 * 增加一条小票红冲记录
		 */
		// 新增加一条小票红冲记录
		OtherRevenueEntity entity = this.addCancelOtherRevenue(ore, date, currentInfo);
		/**
		 * 作废小票操作
		 */
		cancelOtherRevenueOperator(entity, currentInfo, date);
		/**
		 * 小票应收单 和 现金收款单红冲操作
		 */
		// 红冲小票 应收单或现金收款单
		writeBackOtherRevenueNo(entity, currentInfo);
		
		/**
		 * NCI项目，假如作废预收单成功之后，释放T+0报表的已使用金额，删除明细数据
		 * @author 269052 zhouyuan008@deppon.com
		 */
		/**
		 * 1、判断收款方式是否为银行卡
		 */
		if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(ore.getPaymentType())){
			// 释放NCI项目中T+0报表和作废明细
			PosCardDetailEntity pd = new PosCardDetailEntity();
			// 设置单据号
			pd.setInvoiceNo(otherRevenueDto.getOtherRevenueNo());
			
			pd.setInvoiceType("XP");
			/**
			 * 更新人
			 */
			pd.setModifyUser(currentInfo.getEmpName());
			pd.setModifyUserCode(currentInfo.getEmpCode());
			
			//判断单号是否来自于NCI项目
			List<PosCardDetailEntity> list = pdaPosManageService.queryPosDetailsByNo(pd);
			if(CollectionUtils.isNotEmpty(list)){
				// 释放T+0报表的金额和删除明细数据
				pdaPosManageService.updatePosAndDeleteDetail(pd);
			}
		}
		logger.info("结束作废运单：" + otherRevenueDto.getOtherRevenueNo());
	}

	/**
	 * 
	 * 作废小票操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-27 下午2:12:14
	 */
	private void cancelOtherRevenueOperator(OtherRevenueEntity entity, CurrentInfo currentInfo, Date date) {
		// 设置作废信息
		OtherRevenueDto otherDto = new OtherRevenueDto();
		otherDto.setId(entity.getId());// 更新Id标识
		otherDto.setActive(FossConstants.INACTIVE);// 无效
		otherDto.setVersionNo(entity.getVersionNo());// 最新版本号
		otherDto.setModifyTime(date);// 修改时间
		otherDto.setModifyUserCode(currentInfo.getEmpCode());// 修改人编码
		otherDto.setModifyUserName(currentInfo.getEmpName());// 修改人姓名
		otherDto.setIsDisable(FossConstants.ACTIVE);// 是否作废
		otherDto.setDisableUserCode(currentInfo.getEmpCode());// 作废人编码
		otherDto.setDisableUserName(currentInfo.getEmpName());// 作废人名称
		otherDto.setDisableTime(date);// 作废时间
		// 执行更新作废操作
		int retNum = otherRevenueDao.cancelOtherRevenue(otherDto);
		if (1 != retNum) {
			throw new SettlementException("作废小票记录失败");
		}
	}

	private OtherRevenueEntity getOtherRevenueEntityByNo(String revenueNo, String active) {
		// 构造小票单号集合
		List<String> cancelOtherRevenueNos = new ArrayList<String>();
		cancelOtherRevenueNos.add(revenueNo);
		// 根据小票号查询小票最新信息
		List<OtherRevenueEntity> otherRevenueLst = otherRevenueDao.queryOtherRevenueByOtherRevenueNos(cancelOtherRevenueNos, active);
		if (CollectionUtils.isEmpty(otherRevenueLst)) {
			throw new SettlementException("小票单号无效,不能进行作废操作");
		}
		OtherRevenueEntity entity = otherRevenueLst.get(0);
		return entity;
	}

	/**
	 * 增加小票作废记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-27 下午1:46:28
	 */
	private OtherRevenueEntity addCancelOtherRevenue(OtherRevenueEntity entity,	Date date, CurrentInfo currentInfo) {

		OtherRevenueDto dto = new OtherRevenueDto();
		BeanUtils.copyProperties(entity, dto);

		dto.setId(UUIDUtils.getUUID());
		dto.setActive(FossConstants.INACTIVE);// 置为无效状态
		dto.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
		dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES); // 红单
		dto.setAmount(NumberUtils.multiply(dto.getAmount(), -1));// 设置小票金额
		// 操作者信息
		dto.setModifyTime(date);// 修改时间
		dto.setModifyUserCode(currentInfo.getEmpCode());// 修改人编码
		dto.setModifyUserName(currentInfo.getEmpName());// 修改人姓名
		dto.setIsDisable(FossConstants.ACTIVE);// 是否作废
		dto.setDisableUserCode(currentInfo.getEmpCode());// 作废人编码
		dto.setDisableUserName(currentInfo.getEmpName());// 作废人名称
		dto.setDisableTime(date);// 作废时间
		dto.setInvoiceMark(entity.getInvoiceMark());

		int i = otherRevenueDao.addOtherRevenue(dto);
		if (1 != i) {
			throw new SettlementException("作废失败");
		} else {
			return entity;
		}

	}

	/**
	 * 红冲小票 应收单和现金收款单
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-27 上午10:52:44
	 */
	private void writeBackOtherRevenueNo(OtherRevenueEntity otherRevenueEntity,	CurrentInfo currentInfo) {
		// 查询小票应收单
		BillReceivableConditionDto dto = new BillReceivableConditionDto();
		// 设置源单号
		dto.setSourceBillNo(otherRevenueEntity.getOtherRevenueNo());
		// 设置源单类型
		dto.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__OTHER_REVENUE);
		// 构造源单号集合
		List<String> otherRevenueNos = new ArrayList<String>();
		otherRevenueNos.add(dto.getSourceBillNo());

		// 如果收款方式为现金 则红冲现金收款单 否则红种应收单
		if (StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH, otherRevenueEntity.getPaymentType())
				|| StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD, otherRevenueEntity.getPaymentType())) {
			/**
			 * 红冲现金收款单
			 */
			List<BillCashCollectionEntity> billCashes = billCashCollectionService.queryBySourceBillNOs(otherRevenueNos, dto.getSourceBillType(), dto.getActive());

			// 红冲现金收款单
			if (CollectionUtils.isNotEmpty(billCashes)) {
				BillCashCollectionEntity entity = billCashes.get(0);
				// 调用公共红冲接口
				billCashCollectionService.writeBackBillCashCollection(entity, currentInfo);
			} else {
				throw new SettlementException("现金收款单号不存在,无法作废!");
			}

		} else {
			/**
			 * 红冲应收单
			 */
			// 查询应收单
			List<BillReceivableEntity> billReceives = billReceivableService.queryBySourceBillNOs(otherRevenueNos, dto.getSourceBillType(), dto.getActive());
			// 红冲应收单
			if (CollectionUtils.isNotEmpty(billReceives)) {
				BillReceivableEntity entity = billReceives.get(0);
				// 调用公共红冲接口
				billReceivableService.writeBackBillReceivable(entity, currentInfo);
			} else {
				throw new SettlementException("应收单号不存在,无法作废!");
			}

		}
	}

	/**
	 * 查询小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午10:03:42
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService#queryOtherRevenue(com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto,
	 *      int, int)
	 */
	@Override
	public List<OtherRevenueEntity> queryOtherRevenue(OtherRevenueDto otherRevenueDto, int start, int limit) {
		if (null == otherRevenueDto) {
			throw new SettlementException("参数有误，查询失败!");
		}
		if (StringUtils.equals(SettlementConstants.TAB_QUERY_BY_DATE, otherRevenueDto.getQueryPageTab())) {
			otherRevenueDto.setActive(FossConstants.ACTIVE);
			return otherRevenueDao.queryOtherRevenueByDate(otherRevenueDto,	start, limit);
		} else if (StringUtils.equals(SettlementConstants.TAB_QUERY_BY_OTHERREVENUE_NO,	otherRevenueDto.getQueryPageTab())) {
			return otherRevenueDao.queryOtherRevenueByOtherRevenueNos(otherRevenueDto, start, limit);
		} else {
			throw new SettlementException("查询小票失败");
		}
	}

	/**
	 * 查询打印小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	public OtherRenvenueNoTotalPrtResultDto queryOtherRevenue(OtherRevenueDto otherRevenueDto) {
		if (null == otherRevenueDto) {
			throw new SettlementException("参数有误，查询失败!");
		}
		List<OtherRevenueEntity> list = null;
		if (StringUtils.equals(SettlementConstants.TAB_QUERY_BY_DATE, otherRevenueDto.getQueryPageTab())) {
			otherRevenueDto.setActive(FossConstants.ACTIVE);
			list = otherRevenueDao.queryOtherRevenueByDate(otherRevenueDto);
		} else if (StringUtils.equals(SettlementConstants.TAB_QUERY_BY_OTHERREVENUE_NO, otherRevenueDto.getQueryPageTab())) {
			list = otherRevenueDao.queryOtherRevenueByOtherRevenueNos(otherRevenueDto.getOtherRevenueNos(), null);
		} else {
			throw new SettlementException("打印小票失败");
		}
		return sumTotalAmount(list);
	}

	/**
	 * 计算汇总金额
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-30 下午3:36:44
	 */
	private OtherRenvenueNoTotalPrtResultDto sumTotalAmount(List<OtherRevenueEntity> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			OtherRenvenueNoTotalPrtResultDto dto = new OtherRenvenueNoTotalPrtResultDto();
			dto.setList(list);
			for (OtherRevenueEntity entity : list) {
				if (entity.getAmount() != null) {
					// 设置总金额
					dto.setTotalAmount(NumberUtils.add(dto.getTotalAmount(), entity.getAmount()));
					// 现金总金额
					if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(entity.getPaymentType())) {
						// 设置现金总金额
						dto.setChTotalAmount(NumberUtils.add(dto.getChTotalAmount(), entity.getAmount()));
					} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(entity.getPaymentType())) {// 银行卡
						// 设置银行卡总金额
						dto.setCdTotalAmount(NumberUtils.add(dto.getCdTotalAmount(), entity.getAmount()));
					} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(entity.getPaymentType())) {// 临时欠款
						// 设置临时欠款总金额
						dto.setDtTotalAmount(NumberUtils.add(dto.getDtTotalAmount(), entity.getAmount()));
					} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(entity.getPaymentType())) {// 月结
						dto.setCtTotalAmount(NumberUtils.add(dto.getCtTotalAmount(), entity.getAmount()));
					}
				}
			}
			return dto;
		}
		return null;
	}

	/**
	 * 查询小票记录总记录条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午10:03:49
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService#countQueryOtherRevenue(com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto)
	 */
	@Override
	public OtherRevenueDto countQueryOtherRevenue(OtherRevenueDto otherRevenueDto) {
		if (null == otherRevenueDto) {
			throw new SettlementException("参数有误，查询失败!");
		}
		if (StringUtils.equals(SettlementConstants.TAB_QUERY_BY_DATE, otherRevenueDto.getQueryPageTab())) {
			otherRevenueDto.setActive(FossConstants.ACTIVE);
			return otherRevenueDao.countQueryOtherRevenueByDate(otherRevenueDto);
		} else if (StringUtils.equals(SettlementConstants.TAB_QUERY_BY_OTHERREVENUE_NO, otherRevenueDto.getQueryPageTab())) {
			return otherRevenueDao.countQueryOtherRevenueByOtherRevenueNos(otherRevenueDto);
		} else {
			throw new SettlementException("查询失败");
		}
	}

	/**
	 * 按小票号查询小票记录
	 * 
	 * @otherRevenueNos 小票单号集合
	 * @active 是否有效
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	@Override
	public List<OtherRevenueEntity> queryOtherRevenueByOtherRevenueNos(List<String> otherRevenueNos, String active) {
		if (CollectionUtils.isEmpty(otherRevenueNos)) {
			throw new SettlementException("内部错误，小票记录为空!");
		}

		return otherRevenueDao.queryOtherRevenueByOtherRevenueNos(otherRevenueNos, active);
	}

	/**
	 * 按小票ID查询小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	@Override
	public OtherRevenueEntity queryOtherRevenueById(String id) {
		if (StringUtils.isBlank(id)) {
			throw new SettlementException("ID不能为空");
		}
		return otherRevenueDao.queryOtherRevenueById(id);
	}

	/**
	 * 获取产品类型
	 * @author 045738-foss-maojianqiang
	 * @date 2013-7-23 下午4:08:04
	 * @param otherRevenueDto
	 * @return
	 */
	private String getProductCode(OtherRevenueDto otherRevenueDto){
		String productCode = "";
		//如果运单号不为空，则默认从运单获取产品类型
		if(StringUtils.isNotBlank(otherRevenueDto.getWaybillNo())){
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(otherRevenueDto.getWaybillNo().trim());
			
			/* 092036-bochenlong 
			 * 
			 * 如果填写单号不存在，提抛出异常
			 */
			if(waybill == null) {
			 throw new SettlementException("运单号不存在，请核实！");	
			}
			
			productCode = waybill.getProductCode();
		}else {
			//如果产品类型为空，则抛出异常
			if(StringUtils.isBlank(otherRevenueDto.getBusinessCase())){
				throw new SettlementException("运单号为空时，业务类型不能为空！");
			}
			//如果为零担，则默认为精准卡航，反之为包裹
			if(SettlementConstants.BREAKBULK.equals(otherRevenueDto.getBusinessCase())){
				productCode=ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT;
			}else{
				productCode=ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE;
			}
		}
		return productCode;
	}
	
	public AbnormalBillAmountCalculatedDto queryOtherRevenueByRevenueNo(
			AbnormalBillAmountCalculatedDto dto) {
		return this.otherRevenueDao.queryOtherRevenueByRevenueNo(dto);
	}
	
	/**
	 * 根据运单号查询小票记录
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-10-10 上午8:43:01 
	 * @param waybillNO
	 * @return List<OtherRevenueEntity>
	 */
	public List<OtherRevenueEntity> queryOtherRevenueByWayBillNO(String waybillNO) {
		
		return otherRevenueDao.queryOtherRevenueByWayBillNO(waybillNO);
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService.queryOtherRevenueByWayBillNO
	 * @Description:根据运单号和发票产生类别 发票产生部门查询小票记录
	 *
	 * @param waybillNO
	 * @return
	 *
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 上午9:29:30
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-5-20    130376-YANGKANG      v1.0.0         create
	 */
	public List<OtherRevenueEntity> queryOtherRevenueByWayBillNOAndInvoiceCategory(String waybillNO,CurrentInfo currentInfo) {
		
		return otherRevenueDao.queryOtherRevenueByWayBillNOAndInvoiceCategory(waybillNO,currentInfo);
	}
	/**
	 * 查询运单
	 * @author ddw
	 * @date 2013-11-05 
	 * @param waybillNO
	 * @return WaybillEntity
	 */
	@Override
	public WaybillDto queryWaybillByNo(String waybillNo) {
		WaybillDto waybill = waybillManagerService.queryWaybillByNo(waybillNo);
		return waybill;
	}
	
	/**
	 * @return customerService
	 */
	public ICustomerService getCustomerService() {
		return customerService;
	}

	/**
	 * @param customerService
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @return noteDetailsService
	 */
	public NoteDetailsService getNoteDetailsService() {
		return noteDetailsService;
	}

	/**
	 * @param noteDetailsService
	 */
	public void setNoteDetailsService(NoteDetailsService noteDetailsService) {
		this.noteDetailsService = noteDetailsService;
	}

	/**
	 * @return billCashCollectionService
	 */
	public BillCashCollectionService getBillCashCollectionService() {
		return billCashCollectionService;
	}

	/**
	 * @param billCashCollectionService
	 */
	public void setBillCashCollectionService(BillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}

	/**
	 * @return billReceivableService
	 */
	public BillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(BillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @return settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @return otherRevenueDao
	 */
	public IOtherRevenueDao getOtherRevenueDao() {
		return otherRevenueDao;
	}

	/**
	 * @param otherRevenueDao
	 */
	public void setOtherRevenueDao(IOtherRevenueDao otherRevenueDao) {
		this.otherRevenueDao = otherRevenueDao;
	}

	public void setCustomerBargainService(ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param vehicleAgencyCompanyService
	 */
	public void setVehicleAgencyCompanyService(IVehicleAgencyCompanyService vehicleAgencyCompanyService) {
		this.vehicleAgencyCompanyService = vehicleAgencyCompanyService;
	}

	/**
	 * @param airAgencyCompanyService
	 */
	public void setAirAgencyCompanyService(IAirAgencyCompanyService airAgencyCompanyService) {
		this.airAgencyCompanyService = airAgencyCompanyService;
	}

	/**
	 * @SET
	 * @param expressPartSalesDeptService
	 */
	public void setExpressPartSalesDeptService(IExpressPartSalesDeptService expressPartSalesDeptService) {
		/*
		 *@set
		 *@this.expressPartSalesDeptService = expressPartSalesDeptService
		 */
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

	

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}
	
	/**
	 * add by 353654
	 * 调用CUBC小票新增接口FOSS客户端接口实现
	 */
	@Override
	public CUBCOtherRevenueResultDto addOtherRevenueToCUBC(CUBCOtherRevenueRequestDto requestDto) {
		if(requestDto == null){
			logger.error("调用CUBC小票新增接口参数不合法");
			throw new CUBCOtherRevenueException("调用CUBC小票新增接口参数不合法");
		}
		PostMethod post = null;
		logger.info("CUBC新增小票服务地址："+esbAddress);
		try {
			String request = JSONObject.toJSONString(requestDto);
			logger.info("调用CUBC新增小票服务请求JSON信息："+request);
			RequestEntity reqEntity = new StringRequestEntity(request, APPLICATION_JSON, CODE_FORMAT);
			post = new PostMethod(esbAddress);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader(CONTENT_TYPE, REQUEST_HEADER);
			HttpClient httpClient = new HttpClient();
			HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(NumberConstants.NUMBER_15000);
			params.setSoTimeout(NumberConstants.NUMBER_2999);
			httpClient.executeMethod(post);
//			String resultJson = post.getResponseBodyAsString();
			InputStream inputStream = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			try {
				while ((line = br.readLine()) != null){
					sbf.append(line);
				}
			} catch (Exception e) {
				throw new IOException("读取响应数据失败！");
			} finally{
				if(br != null){
					br.close();
				}
			}
			String resultJson = sbf.toString();
			logger.info("调用CUBC新增小票服务响应JSON信息："+resultJson);
			CUBCOtherRevenueResultDto result = JSONObject.parseObject(resultJson, CUBCOtherRevenueResultDto.class);
			return result;
		} catch (Exception e) {
			logger.error("调用CUBC新增小票服务失败"+e.getMessage());
			return null;
		} finally{
			if(post != null){
				post.releaseConnection();
			}
		}
	}
	
}
