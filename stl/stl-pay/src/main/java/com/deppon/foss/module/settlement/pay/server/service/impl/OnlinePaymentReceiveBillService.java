package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISendStatusToPtpService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WithholdStatusDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IOnlinePaymentReceiveBillDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentReceiveBillService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOADOnlineResultDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 网上支付应收单、对账单公共SERVICE接口实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-19 下午2:29:17
 */
public class OnlinePaymentReceiveBillService implements
		IOnlinePaymentReceiveBillService {

	/**
	 * 注入IOnlinePaymentReceiveBillDao
	 */
	private IOnlinePaymentReceiveBillDao onlinePaymentReceiveBillDao;

	/**
	 * 注入运单IWaybillManagerService
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 注入对账单IStatementOfAccountService
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 注入代收货款ICodCommonService
	 */
	private ICodCommonService codCommonService;

	/**
	 * 注入更改单SERVICE
	 */
	private IWaybillRfcService waybillRfcService;

	/**
	 * 注入应收单IBillReceivableService
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入还款单IBillRepaymentService
	 */
	private IBillRepaymentService billRepaymentService;

	/**
	 * 注入生成序列Service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入子公司SERVICE
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 注入核销单IBillWriteoffService
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 快递代理网点服务
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	/**
	 * 注入合伙人部门service
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 更新PTP扣款状态
	 */
	 private ISendStatusToPtpService sendStatusToPtpService;
	
	
	//日志
	private static final Logger logger = org.apache.log4j.LogManager.getLogger(OnlinePaymentReceiveBillService.class);

	/**
	 * 网上支付查询未核销的应收单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	@Override
	public BillSOADOnlineResultDto queryReceiveBillInfo(BillReceivableOnlineQueryDto queryDto) {
		queryDto.setActive(FossConstants.ACTIVE);// 是否有效
		queryDto.setIsRedBack(FossConstants.NO);// 是否红单
		// 来源单据类型:运单
		queryDto.setSourceType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		// 应收单类型集合(始发应收、专线到付)
		List<String> receivableTypeList = new ArrayList<String>();
		//添加应收单类型
		receivableTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		//添加应收单类型
		receivableTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		queryDto.setReceivableTypeList(receivableTypeList);
		// 应收单类型
		queryDto.setReceivableBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		// 默认单据编号
		queryDto.setDefaultBillNo(SettlementConstants.DEFAULT_BILL_NO);
		// 对账单确认状态
		queryDto.setStatementConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM);
		// 更改单受理状态
		List<String> waybillRFCStatusList = new ArrayList<String>();
		waybillRFCStatusList.add(WaybillRfcConstants.PRE_AUDIT); // 待审核
		waybillRFCStatusList.add(WaybillRfcConstants.PRE_ACCECPT); // 待审核
		//封装属性
		queryDto.setWaybillRFCStatus(waybillRFCStatusList);
		// 财务类更改单标记
		queryDto.setIsFinanceChange(FossConstants.YES);
		// 校验查询类型是否为空
		if (StringUtil.isEmpty(queryDto.getQueryType())) {
			throw new SettlementException("查询类型不存在，不能进行查询！", "");
		}
		//判断分页条件
		if (queryDto.getPageNo() == 0 && queryDto.getPageSize() == 0) {
			throw new SettlementException("分页条件不正确，页最大条数为0，不能进行查询！", "");
		}
		// 分页查询结果
		BillSOADOnlineResultDto resultDto = new BillSOADOnlineResultDto();
		// 求总条数查询结果
		BillSOADOnlineResultDto resultCountDto = new BillSOADOnlineResultDto();
		// 只按客户编码查询
		if (SettlementConstants.BHO_QUERY_TYPE_BY_CUSTOMER.equals(queryDto.getQueryType())) {
			// 校验客户编码是否为空
			if (StringUtil.isEmpty(queryDto.getCustomerCode())) {
				throw new SettlementException("客户编码为空，不能进行查询！", "");
			}
			// 求总条数信息
			if (queryDto.getPageNo() == 0) {
				//调用接口进行查询
				resultCountDto = onlinePaymentReceiveBillDao.queryCountReceiveBillInfoByCustomer(queryDto);
				//设置查询总条数
				resultDto.setCountNum(resultCountDto.getCountNum());
			}
			// 如果总条数大于0，则执行分页查询
			if (resultCountDto.getCountNum() > 0 || queryDto.getPageNo() != 0) {
				//调用接口进行查询
				List<BillReceivableEntity> list = onlinePaymentReceiveBillDao.queryReceiveBillInfoByCustomer(queryDto,queryDto.getPageNo(), queryDto.getPageSize());
				//设置查询结果
				resultDto.setBillReceivableEntityList(list);
			}

		}
		// 只按运单号+手机号码查询
		else if (SettlementConstants.BHO_QUERY_TYPE_BY_WAYBILL.equals(queryDto
				.getQueryType())) {
			// 校验运单号和手机号码集合是否为空
			if (CollectionUtils.isEmpty(queryDto.getWayBillNos())|| CollectionUtils.isEmpty(queryDto.getTelphoneNos())) {
				throw new SettlementException("运单号或手机号码集合为空，不能进行查询！", "");
			}
			// 校验运单号和手机号码集合长度是否相等
			if (queryDto.getWayBillNos().size() != queryDto.getTelphoneNos().size()) {
				throw new SettlementException("运单号和手机号码集合不相等，不能进行查询！", "");
			}
			// 校验运单号和手机号集合的长度是否超过最大数
			if (queryDto.getWayBillNos().size() > SettlementConstants.BHO_MAX_LIST_SIZE|| queryDto.getTelphoneNos().size() > SettlementConstants.BHO_MAX_LIST_SIZE) {
				throw new SettlementException("运单号和手机号码集合长度超过最大个数，不能进行查询！", "");
			}
			
			//运单号集合
			Map<String,String> map = new HashMap<String,String>();
			// 调用运单查询方法校验运单号和手机号码是否匹配
			for (int i = 0; i < queryDto.getWayBillNos().size(); i++) {
				//调用接口进行查询
				WaybillDto waybillEntity = waybillManagerService.queryWaybillByNo(queryDto.getWayBillNos().get(i));
				//封装手机号码
				String telPhone = queryDto.getTelphoneNos().get(i);
				// 校验手机号码是否和运单的发货人手机号码相同或和运单的收货人手机号码相同
				if (!(telPhone.equals(waybillEntity.getWaybillEntity().getDeliveryCustomerMobilephone()) 
						|| telPhone.equals(waybillEntity.getWaybillEntity().getReceiveCustomerMobilephone()))) {
					throw new SettlementException("运单号:"+ queryDto.getWayBillNos().get(i) + "和手机号码："+ telPhone + "不匹配，不能进行查询！", "");
				}
				//如果非快递代理，则可以查询出来，反之不能查询出来
				if(isLandStowage(waybillEntity)){
					map.put(queryDto.getWayBillNos().get(i),queryDto.getWayBillNos().get(i));
				}
			}
			//查询
			List<BillReceivableEntity> list = onlinePaymentReceiveBillDao.queryReceiveBillInfoByWaybillNo(queryDto);
			//判空
			if (CollectionUtils.isNotEmpty(list)) {
				//声明目标targelist
				List<BillReceivableEntity> targetList = new ArrayList<BillReceivableEntity>();
				for(BillReceivableEntity entity:list){
					//如果为始发应收，则默认都可以查出来
					if(entity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE)){
						targetList.add(entity);
					//如果为到付，则需要判断是否在不合法的map中，如果在，则
					}else{
						if(map.get(entity.getWaybillNo())==null){
							targetList.add(entity);
						}
					}
				}
				
				//设置属性值
				resultDto.setBillReceivableEntityList(targetList);
				//设置属性值
				resultDto.setCountNum(targetList.size());
			}
		}
		// 按业务日期+客户编码+收货人名称+货物名称+付款方式
		else if (SettlementConstants.BHO_QUERY_TYPE_BY_DATE.equals(queryDto.getQueryType())) {
			// 校验业务日期是否为空
			if (queryDto.getBeginDate() == null|| queryDto.getEndDate() == null) {
				throw new SettlementException("业务日期为空，不能进行查询！", "");
			}
			// 校验业务日期是否超过6个月
			if (DateUtils.getTimeDiff(queryDto.getBeginDate(),queryDto.getEndDate()) > SettlementConstants.DATE_LIMIT_SEX_MONTH) {
				throw new SettlementException("开始日期和结束日期不能超过6个月！", "");
			}
			// 校验客户编码是否为空
			if (StringUtil.isEmpty(queryDto.getCustomerCode())) {
				throw new SettlementException("客户编码为空，不能进行查询！", "");
			}
			// 求总条数信息
			if (queryDto.getPageNo() == 0) {
				//调用接口进行查询
				resultCountDto = onlinePaymentReceiveBillDao.queryCountReceiveBillInfoByDate(queryDto);
				//设置属性值
				resultDto.setCountNum(resultCountDto.getCountNum());
			}
			// 如果总条数大于0，则执行分页查询
			if (resultCountDto.getCountNum() > 0 || queryDto.getPageNo() != 0) {
				//调用接口查询
				List<BillReceivableEntity> list = onlinePaymentReceiveBillDao.queryReceiveBillInfoByDate(queryDto,queryDto.getPageNo(), queryDto.getPageSize());
				//判空
				if (CollectionUtils.isNotEmpty(list)) {
					////设置属性值
					resultDto.setBillReceivableEntityList(list);
				}
			}
		}
		// 按客户编码+运单号
		else if (SettlementConstants.BHO_QUERY_TYPE_BY_WAYBILL_CUSTOMER
				.equals(queryDto.getQueryType())) {
			// 校验客户编码是否为空
			if (StringUtil.isEmpty(queryDto.getCustomerCode())) {
				throw new SettlementException("客户编码为空，不能进行查询！", "");
			}
			// 校验运单号是否为空
			if (StringUtil.isEmpty(queryDto.getWayBillNos().get(0))) {
				throw new SettlementException("运单号为空，不能进行查询！", "");
			}
			//设置属性值
			queryDto.setWaybillNo(queryDto.getWayBillNos().get(0));
			//调用接口进行查询
			List<BillReceivableEntity> list = onlinePaymentReceiveBillDao.queryReceiveBillInfoByWaybillAndCus(queryDto);
			//设置总条数
			resultDto.setCountNum(list.size());
			//判空
			if (CollectionUtils.isNotEmpty(list)) {
				//设置属性值
				resultDto.setBillReceivableEntityList(list);
			}
		} else {
			//抛出异常
			throw new SettlementException("查询类型不存在，不能进行查询！", "");
		}
		//网上支付限制始发合伙人不能支付
		if(null != resultDto && CollectionUtils.isNotEmpty(resultDto.getBillReceivableEntityList())){
			for(int i = 0;i < resultDto.getBillReceivableEntityList().size(); i++){
				BillReceivableEntity billReceivableEntity = resultDto.getBillReceivableEntityList().get(i);
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentInfoByCode(billReceivableEntity.getOrigOrgCode());
				if(null != saleDepartmentEntity && FossConstants.ACTIVE.equals(saleDepartmentEntity.getIsLeagueSaleDept())){
					resultDto.getBillReceivableEntityList().remove(i);
				}else if(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_FAILED.equals(billReceivableEntity.getWithholdStatus())
						|| SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS.equals(billReceivableEntity.getWithholdStatus())){
					resultDto.getBillReceivableEntityList().remove(i);
				}
				/*
				 * modify by 269044-zhurongrong 2016-10-28
				 * 新增校验快递到付应收单据挂在非虚拟部门才可第三方支付,补码后且到达部门非虚拟部门才可进行支付。
				 */
				if(StringUtils.isNotEmpty(billReceivableEntity.getDestOrgCode())) {
					//根据到达部门编码查询部门信息
					OrgAdministrativeInfoEntity destOrgEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCodeClean(billReceivableEntity.getDestOrgCode());
					//到达部门为空或者到达部门为虚拟部门，则remove
					if(destOrgEntity == null || FossConstants.YES.equals(destOrgEntity.getExpressSalesDepartment())) {
						//去除改实体
						resultDto.getBillReceivableEntityList().remove(i);
					}
				}//end
			}
		}
		//返回结果
		return resultDto;
	}

	/**
	 * 网上支付锁定应收单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-22 上午11:56:18
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentReceiveBillService#lockReceiveBillInfo(com.deppon.foss.module.settlement.pay.api.shared.dto.BillReceivableOnlineQueryDto)
	 */
	@Transactional
	@Override
	public void lockReceiveBillInfo(BillReceivableOnlineQueryDto queryDto) {
		// 校验查询参数是否合法
		if (StringUtil.isEmpty(queryDto.getReceivableNo())) {
			throw new SettlementException("应收单号为空！", "");
		}
		//记录日志
		logger.info("网上支付锁定应收单entering service,应收单号："+ queryDto.getReceivableNo());
		//判空
		if (StringUtil.isEmpty(queryDto.getCustomerCode())) {
			throw new SettlementException("客户编码为空！", "");
		}
		//判空
		if (queryDto.getAccountDate() == null) {
			throw new SettlementException("记账日期为空！", "");
		} else {
			//日期格式化
			Date acountDate = org.apache.commons.lang.time.DateUtils.truncate(queryDto.getAccountDate(), Calendar.SECOND);
			//设置日期
			queryDto.setAccountDate(acountDate);
		}
		// 网厅最大锁定时间（分）
		String sysLockTimeStr = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_RECEIVABLE_LOCK_MINUTE);
		//设置锁定时间
		queryDto.setLockTime(Integer.parseInt(sysLockTimeStr));
		//校验还款实体
		BillReceivableEntity receivableEntity = validateReceivableEntity(queryDto);
		// 同一客户可以对一条记录进行重复锁定，锁定时间为最新锁定时间；正在被锁定的记录不能被其他客户重复锁定
		if (receivableEntity.getUnlockDateTime() != null && receivableEntity.getUnlockDateTime().before(new Date())) {
			//判空
			if (StringUtil.isNotEmpty(receivableEntity.getLockCustomerCode())
					&& !receivableEntity.getLockCustomerCode().equals(queryDto.getCustomerCode())) {
				throw new SettlementException("该应收单已经被其他客户锁定！", "");
			}
		}
		// 锁定应收单信息
		int row = billReceivableService.updateReceiveBillInfoForLock(queryDto);
		//判断更新条数
		if (row != 1) {
			//记录日志
			logger.error("数据库更新数量异常！不能锁定");
			throw new SettlementException("数据库更新数量异常！不能锁定", "");
		}
		//记录日志
		logger.info("网上支付锁定应收单successfully exit service, 应收单号: "+ queryDto.getReceivableNo());
	}

	/**
	 * 网上支付按应收单还款
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-22 下午4:39:48
	 */
	@Transactional
	@Override
	public void paymentReceiveBillInfo(BillReceivableOnlineQueryDto queryDto) {
		// 校验查询参数是否合法
		// 判断应收单号、在线支付编号、还款金额、记账日期是否为空
		if (StringUtil.isEmpty(queryDto.getReceivableNo())) {
			logger.error("应收单号为空");
			throw new SettlementException("应收单号为空！", "");
		}
		//记录日志
		logger.info("网上支付还款entering service,应收单号：" + queryDto.getReceivableNo());
		//判空
		if (StringUtil.isEmpty(queryDto.getOnlineNo())) {
			//记录日志
			logger.error("在线支付编码为空");
			throw new SettlementException("在线支付编码为空！", "");
		}
		//判空
		if (queryDto.getAccountDate() == null) {
			//记录日志
			logger.error("记账日期为空");
			throw new SettlementException("记账日期为空！", "");
		}
		//判空
		if (queryDto.getAmount() == null) {
			//记录日志
			logger.error("还款金额为空");
			throw new SettlementException("还款金额为空！", "");
		}
		//判空
		if (queryDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			//记录日志
			logger.error("还款金额小于等于0");
			throw new SettlementException("还款金额小于等于0！", "");
		}
		// 判断在线支付编号是否已经使用
		BillRepaymentConditionDto dto = new BillRepaymentConditionDto();
		//设置属性值
		dto.setOnlinePaymentNo(queryDto.getOnlineNo());
		//调用接口进行查询
		List<BillRepaymentEntity> list = billRepaymentService.queryBillRepaymentByCondition(dto);
		//判断列表集合
		if (list.size() > 0) {
			//记录日志
			logger.error("在线支付编号已被使用过，请确认是否重复付款");
			throw new SettlementException("在线支付编号已被使用过，请确认是否重复付款！", "");
		}
		// 根据应收单能否被操作
		BillReceivableEntity receivableEntity = validateReceivableEntity(queryDto);
		// 判断还款金额和应收单未核销金额
		if (queryDto.getAmount().compareTo(receivableEntity.getUnverifyAmount()) != 0) {
			//记录日志
			logger.error("还款金额与应收单的未核销金额不等");
			throw new SettlementException("还款金额与应收单的未核销金额不等！", "");
		}

		// 如果应收单存在运单号，生成互斥对象
		MutexElement mutex = null;
		//判空
		if (StringUtils.isNotEmpty(receivableEntity.getWaybillNo())&&!SettlementConstants.DEFAULT_BILL_NO.equals(receivableEntity.getWaybillNo())) {
			//互斥锁
			mutex = new MutexElement(receivableEntity.getWaybillNo(),"网上支付按应收单还款", MutexElementType.WAYBILL_NO);
		}
		// 如果互斥对象不为空,锁定该应收单对应的运单
		if (mutex != null) {
			// 锁定运单
			businessLockService.lock(mutex,SettlementConstants.BUSINESS_LOCK_BATCH);
		}

		// 生成还款单信息
		BillRepaymentEntity repaymentEntity = new BillRepaymentEntity();
		//设置属性值
		repaymentEntity.setOnlinePaymentNo(queryDto.getOnlineNo());
		//设置属性值
		repaymentEntity.setId(UUIDUtils.getUUID());
		// 还款单号
		repaymentEntity.setRepaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1));
		//设置属性值
		repaymentEntity.setCustomerCode(receivableEntity.getCustomerCode());
		//设置运单号属性值
		repaymentEntity.setWaybillNo(receivableEntity.getWaybillNo());
		//设置属性值
		repaymentEntity.setCustomerName(receivableEntity.getCustomerName());
		// 还款金额、实际还款金额、反核销金额
		repaymentEntity.setAmount(receivableEntity.getUnverifyAmount());
		//设置属性值
		repaymentEntity.setTrueAmount(receivableEntity.getUnverifyAmount());
		//设置属性值
		repaymentEntity.setBverifyAmount(BigDecimal.ZERO);
		// 审核状态、生成方式
		repaymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		//设置属性值
		repaymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		// 收款部门编码、名称
		repaymentEntity.setCollectionOrgCode(receivableEntity.getReceivableOrgCode());
		repaymentEntity.setCollectionOrgName(receivableEntity.getReceivableOrgName());
		// 根据部门编码获取子公司信息
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(receivableEntity.getReceivableOrgCode());
		//判空
		if (orgEntity != null) {
			// 收款部门公司编码、名称
			repaymentEntity.setCollectionCompanyCode(orgEntity.getSubsidiaryCode());
			//设置属性值
			repaymentEntity.setCollectionCompanyName(orgEntity.getSubsidiaryName());
		}
		//BUG-20628 还款单不需要收入部门及收入部门公司
		// 收入部门编码、名称
		//repaymentEntity.setGeneratingOrgCode(receivableEntity.getGeneratingOrgCode());
		//设置属性值
		//repaymentEntity.setGeneratingOrgName(receivableEntity.getGeneratingOrgName());
		// 收入部门公司编码、名称
		//repaymentEntity.setGeneratingCompanyCode(receivableEntity.getGeneratingComCode());
		//设置属性值
		//repaymentEntity.setGeneratingCompanyName(receivableEntity.getGeneratingComName());
		// 单据类型、是否有效、是否红单、支付方式
		repaymentEntity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);
		//设置属性值
		repaymentEntity.setActive(FossConstants.ACTIVE);
		//设置属性值
		repaymentEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		//设置属性值
		repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		// 业务日期、记账日期、是否初始化、版本号
		repaymentEntity.setBusinessDate(new Date());
		//设置属性值
		repaymentEntity.setAccountDate(new Date());
		//设置属性值
		repaymentEntity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
		//设置属性值
		repaymentEntity.setIsInit(FossConstants.NO);
		//设置属性值
		repaymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 创建当前登录信息
		EmployeeEntity employee = new EmployeeEntity();
		//设置属性值
		employee.setEmpCode(SettlementConstants.BHO_CODE);
		//设置属性值
		employee.setEmpName(SettlementConstants.BHO_NAME);
		//实例化
		UserEntity user = new UserEntity();
		//设置属性值
		user.setEmployee(employee);
		//设置属性值
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		//设置属性值
		dept.setCode(receivableEntity.getReceivableOrgCode());
		//设置属性值
		dept.setName(receivableEntity.getReceivableOrgName());
		//获取当前登录人
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		// 新增还款单信息
		repaymentEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
		repaymentEntity.setCreateOrgName(currentInfo.getCurrentDeptName());
		billRepaymentService.addBillRepayment(repaymentEntity, currentInfo);
		// 调用还款冲应收服务
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();
		//设置属性值
		billWriteoffOperationDto.setBillRepaymentEntity(repaymentEntity);
		//声明
		List<BillReceivableEntity> billReceivableEntitys = new ArrayList<BillReceivableEntity>();
		//设置属性值
		billReceivableEntitys.add(receivableEntity);
		//设置属性值
		billWriteoffOperationDto.setBillReceivableEntitys(billReceivableEntitys);
		// 核销批次号
		billWriteoffOperationDto.setWriteoffBatchNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN));
		// 核销單生成方式（手工輸入）
		billWriteoffOperationDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		// 核销网点编码和名称
		BillWriteoffOperationDto resultDto = billWriteoffService.writeoffRepaymentAndReceibable(billWriteoffOperationDto,currentInfo);
		// 核销完毕调用通知对账单接口
		StatementOfAccountUpdateDto updateDto = new StatementOfAccountUpdateDto();
		//设置属性值
		updateDto.setReceivableEntityList(resultDto.getBillReceivableEntitys());
		//调用接口更更新
		statementOfAccountService.updateStatementAndDetailForWriteOff(updateDto, currentInfo);

		// 如果互斥对象不为空,解锁该应收单对应的运单
		if (mutex != null) {
			// 解锁运单
			businessLockService.unlock(mutex);
		}
		
		/**
		 * SPBP-用户需求说明书-FOSS-D到H客户网上支付的需求优化
		 * 
		 * @author 269044-zhurongrong
		 * @date 2016-12-22
	     */
		//更新PTP扣款状态DTO集合
		List<WithholdStatusDto> withholdStatusDtoList = new ArrayList<WithholdStatusDto>();
		//更新PTP扣款状态DTO
		WithholdStatusDto withholdStatusDto = new WithholdStatusDto();
		//设置运单号
		withholdStatusDto.setWaybillNo(receivableEntity.getWaybillNo());
		//设置单据子类型
		withholdStatusDto.setBillType(receivableEntity.getBillType());
		//设置到达部门编码
		withholdStatusDto.setDestOrgCode(receivableEntity.getDestOrgCode());
		//设置到达部门名称
		withholdStatusDto.setDestOrgName(receivableEntity.getDestOrgName());
		//设置场景
		withholdStatusDto.setScene(SettlementDictionaryConstants.FOSS_PTP_SEND_WITHHOLD_STATUS_ONLINE);
		//添加到集合中
		withholdStatusDtoList.add(withholdStatusDto);
		//发送到ptp
		sendStatusToPtpService.sendStatusToPtp(withholdStatusDtoList);
		
		//记录日志
		logger.info("网上支付还款sucessful service,应收单号："+ queryDto.getReceivableNo());
	}

	/**
	 * 校验应收单信息能否被网厅操作
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-23 上午9:29:06
	 */
	private BillReceivableEntity validateReceivableEntity(BillReceivableOnlineQueryDto queryDto) {
		// 按应收单号和记账日期查询有效应收单信息
		BillReceivableDto dtos = new BillReceivableDto();
		//实例化
		List<BillReceivableEntity> billReceivables = new ArrayList<BillReceivableEntity>();
		//声明应收单
		BillReceivableEntity queryEntity = new BillReceivableEntity();
		//设置属性值
		queryEntity.setReceivableNo(queryDto.getReceivableNo());
		//设置属性值
		queryEntity.setAccountDate(queryDto.getAccountDate());
		//添加应收单
		billReceivables.add(queryEntity);
		//设置属性值
		dtos.setBillReceivables(billReceivables);
		//设置属性值
		dtos.setActive(FossConstants.YES);
		//查询
		List<BillReceivableEntity> receivableEntityList = billReceivableService.queryPartitionsByConditions(dtos);
		//判空
		if (CollectionUtils.isEmpty(receivableEntityList)) {
			logger.error("该应收单不存在或已经处于无效状态！不能操作");
			throw new SettlementException("该应收单不存在或已经处于无效状态！不能操作", "");
		}
		BillReceivableEntity receivableEntity = receivableEntityList.get(0);
		
		// 校验产品类型为空运、偏线的到付应收单不能进行网上支付
				if ((SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(receivableEntity.getBillType()) 
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE.equals(receivableEntity.getBillType())
								|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY.equals(receivableEntity.getBillType()))
								&&(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(receivableEntity.getProductCode())
										||PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(receivableEntity.getProductCode())
										)) {
					//记录日志
					logger.error("运输性质为空运、偏线的到付应收单不能进行网上支付！");
					throw new SettlementException("运输性质为空运、偏线的到付应收单不能进行网上支付！","");
				}
				
		
		
		// 校验应收单信息是否合法
		// 校验应收单的未核销金额是否大于0；
		if (receivableEntity.getUnverifyAmount().compareTo(BigDecimal.ZERO) <= 0) {
			//记录日志
			logger.error("该应收单未核销金额为0！不能操作");
			throw new SettlementException("该应收单未核销金额为0！不能操作", "");
		}
		// 校验应收单的类型只能始发应收、专线到付运费
		if (!(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.equals(receivableEntity.getBillType()) 
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(receivableEntity.getBillType()))) {
			//记录日志
			logger.error("该应收单类型只能为始发应收、到付应收，其余类型不能进行网上支付！");
			throw new SettlementException("该应收单类型只能为始发应收、到付应收，其余类型不能进行网上支付！","");
		}
		// 不能操作有代收货款的专线到付运费应收单
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(receivableEntity.getBillType())) {
			//调用接口查询
			CODEntity codEntity = codCommonService.queryByWaybill(receivableEntity.getWaybillNo());
			//判空
			if (codEntity != null) {
				//记录日志
				logger.error("该专线到付应收单存在代收货款记录！不能操作");
				throw new SettlementException("该专线到付应收单存在代收货款记录！不能操作", "");
			}
		}
		// 只能操作没有进入对账单或者进入对账单但对账单未确认
		// 对账单号集合
		List<String> statementNos = new ArrayList<String>();
		//校验是否存在对账单号
		if (SettlementConstants.DEFAULT_BILL_NO.equals(receivableEntity.getStatementBillNo())) {
			//添加对账单号
			statementNos.add(receivableEntity.getStatementBillNo());
		}
		//查询
		List<String> confirmList = statementOfAccountService.queryConfirmStatmentOfAccount(statementNos);
		//判空
		if (CollectionUtils.isNotEmpty(confirmList)) {
			//记录日志
			logger.error("该应收单已经入确认的对账单！不能操作");
			throw new SettlementException("该应收单已经入确认的对账单！不能操作", "");
		}
		// 与应收单相关联的运单不存在有未受理的更改单信息
		// 运单号集合
		if (StringUtil.isNotEmpty(receivableEntity.getWaybillNo())) {
			//实例化实体
			List<String> waybillNos = new ArrayList<String>();
			//添加运单号
			waybillNos.add(receivableEntity.getWaybillNo());
			//判断是否存在更改单
			List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
			//判空
			if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
				//记录日志
				logger.error("该应收单存在未受理的更改单！不能操作");
				throw new SettlementException("该应收单存在未受理的更改单！不能操作", "");
			}
		}
		return receivableEntity;
	}
	
	/**
	 * 是否快递代理
	 * @author 045738-foss-maojianqiang
	 * @date 2013-8-16 下午4:20:06
	 * @return
	 */
	private boolean isLandStowage(WaybillDto dto){
		//声明标志位
		boolean flag = false;
		//提货方式
		String receiveMethod = dto.getWaybillEntity().getReceiveMethod();
		//如果为自提，则默认不是快递代理
		if(!WaybillConstants.SELF_PICKUP.equals(receiveMethod) &&  SettlementUtil.isPackageProductCode(dto.getWaybillEntity().getProductCode())){
			if(null != dto.getWaybillExpressEntity()){
				//是否补码
				String isAddCode = dto.getWaybillExpressEntity().getIsAddCode();
				String orgCode =dto.getWaybillEntity().getCustomerPickupOrgCode();
				//补码后
				if(FossConstants.ACTIVE.equals(isAddCode)){
					OuterBranchExpressEntity entity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(orgCode,FossConstants.ACTIVE);
					if(entity!=null){
						flag = true;
					}
				//补码前	
				}else{
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
					//判空
					if (orgEntity != null && orgEntity.getExpressSalesDepartment()!=null) {
						if(FossConstants.ACTIVE.equals(orgEntity.getExpressSalesDepartment())){
							flag = true;
						}
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * @return onlinePaymentReceiveBillDao
	 */
	public IOnlinePaymentReceiveBillDao getOnlinePaymentReceiveBillDao() {
		return onlinePaymentReceiveBillDao;
	}

	/**
	 * @param onlinePaymentReceiveBillDao
	 */
	public void setOnlinePaymentReceiveBillDao(
			IOnlinePaymentReceiveBillDao onlinePaymentReceiveBillDao) {
		this.onlinePaymentReceiveBillDao = onlinePaymentReceiveBillDao;
	}

	/**
	 * @return waybillManagerService
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	/**
	 * @param waybillManagerService
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @return statementOfAccountService
	 */
	public IStatementOfAccountService getStatementOfAccountService() {
		return statementOfAccountService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @return codCommonService
	 */
	public ICodCommonService getCodCommonService() {
		return codCommonService;
	}

	/**
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * @return waybillRfcService
	 */
	public IWaybillRfcService getWaybillRfcService() {
		return waybillRfcService;
	}

	/**
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @return billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @return billRepaymentService
	 */
	public IBillRepaymentService getBillRepaymentService() {
		return billRepaymentService;
	}

	/**
	 * @param billRepaymentService
	 */
	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
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
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @return orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @return billWriteoffService
	 */
	public IBillWriteoffService getBillWriteoffService() {
		return billWriteoffService;
	}

	/**
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * @return configurationParamsService
	 */
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	/**
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @return businessLockService
	 */
	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	/**
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * @SET
	 * @param ldpAgencyDeptService
	 */
	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		/*
		 *@set
		 *@this.ldpAgencyDeptService = ldpAgencyDeptService
		 */
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setSendStatusToPtpService(
			ISendStatusToPtpService sendStatusToPtpService) {
		this.sendStatusToPtpService = sendStatusToPtpService;
	}
}
