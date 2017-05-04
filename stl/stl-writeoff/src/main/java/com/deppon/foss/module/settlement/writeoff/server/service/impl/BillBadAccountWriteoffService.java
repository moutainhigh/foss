package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IFreightDiscountService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillBadWriteoffDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillBadWriteoffReceivableQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillBadAccountWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillBadAccountQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 坏账核销实现类
 * 
 * @author foss-wangxuemin
 * @date Dec 4, 2012 3:55:41 PM
 */
public class BillBadAccountWriteoffService implements
		IBillBadAccountWriteoffService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillBadAccountWriteoffService.class);

	/**
	 * 核销服务
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 结算公共服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 坏账应收单查询服务
	 */
	private IBillBadWriteoffReceivableQueryDao billBadWriteoffReceivableQueryDao;

	/**
	 * 注入更改单SERVICE
	 */
	private IWaybillRfcService waybillRfcService;

	/**
	 * 对账单service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;

	/**
	 * 零担折扣
	 */
	private IFreightDiscountService freightDiscountService;
	
	/**
	 * 灰名单服务
	 */
	private IGrayCustomerService grayCustomerService;
	
	/**
	 * 查询待坏账处理的应收单
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:56:09 PM
	 * @see com.deppon.foss.module.settlement.IBillBadAccountService.api.server.service.IBillBadAccountWriteoffService#queryBadAccountReceiableList(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<BillReceivableEntity> queryBadAccountReceivableList(
			BillBadAccountQueryDto queryDto) {
		// 请求参数不能为空
		if (queryDto == null) {
			// 提示参数错误：QueryBadDebtsArInfoRequest为null
			throw new SettlementException(
					"参数错误：QueryBadDebtsArInfoRequest为null！");
		}

		// 查询类型不能为空
		if (StringUtils.isEmpty(queryDto.getQueryType())) {
			// 提示参数错误：查询类型QueryType为null
			throw new SettlementException("参数错误：查询类型QueryType为null！");
		}

		// 查询类型为按客户查询
		if (SettlementConstants.BAD_ACCOUNT__QUERY_TYPE__CUSTOMER
				.equals(queryDto.getQueryType())) {
			// 客户编码不能为空
			if (StringUtils.isEmpty(queryDto.getCustomerCode())) {
				// 提示参数错误：客户编码customerCode为null
				throw new SettlementException("参数错误：客户编码customerCode为null！");
			}
			// 开始时间、结束时间不能为空
			if (null == queryDto.getBeginTime()
					|| null == queryDto.getEndTime()) {
				// 提示参数错误：开始时间beginTime和结束时间endTime都不能为null
				throw new SettlementException(
						"参数错误：开始时间beginTime和结束时间endTime都不能为null！");
			}
		}
		// 查询类型为按运单号查询
		else if (SettlementConstants.BAD_ACCOUNT__QUERY_TYPE__WAYBILL
				.equals(queryDto.getQueryType())) {
			// 运单号不能为空
			if (CollectionUtils.isEmpty(queryDto.getWayBillNum())) {
				// 提示参数错误：运单号wayBillNum为null
				throw new SettlementException("参数错误：运单号wayBillNum为null！");
			}
		}
		// 非法查询类型
		else {
			// 提示参数错误：查询类型QueryType为非法值
			throw new SettlementException("参数错误：查询类型QueryType为非法值！");
		}

		// 生成应收单列表
		List<BillReceivableEntity> billReceivableList = null;

		// 只能查询有效的应收单
		queryDto.setActive(FossConstants.ACTIVE);

		// 按客户查询
		if (SettlementConstants.BAD_ACCOUNT__QUERY_TYPE__CUSTOMER
				.equals(queryDto.getQueryType())) {
			// 查询结束日期加1天
			queryDto.setEndTime(DateUtils.addDays(queryDto.getEndTime(), 1));
			// 查询待坏账申请的应收单列表（按客户）
			billReceivableList = billBadWriteoffReceivableQueryDao
					.queryBillBadReceivableByCustomer(queryDto);
		}
		// 按运单号查询
		else if (SettlementConstants.BAD_ACCOUNT__QUERY_TYPE__WAYBILL
				.equals(queryDto.getQueryType())) {
			// 查询待坏账申请的应收单列表（按运单号）
			billReceivableList = billBadWriteoffReceivableQueryDao
					.queryBillBadReceivableByWayBillNo(queryDto);
		}

		// 结果为空，抛出异常
		if (CollectionUtils.isEmpty(billReceivableList)) {
			// 提示查询待坏账处理的应收记录为空
			throw new SettlementException("查询待坏账处理的应收记录为空;原因：运单未签收或存在未受理更改单或运单本不存在坏账应收");
		}

		//ddw
		for(BillReceivableEntity reEntity : billReceivableList){
			Map<String,Object> contractMap = new HashMap<String,Object>();
			//客户编码
			contractMap.put("customerCode", reEntity.getCustomerCode());
			//业务日期
			contractMap.put("businessDate", reEntity.getBusinessDate());
			//运单号
			contractMap.put("wayblllNo",reEntity.getWaybillNo());
			//查询客户合同
			List<Integer> list = freightDiscountService.queryCustomerContract(contractMap);
			//查询折扣应付
			List<BillPayableEntity> payableList = freightDiscountService.queryDiscountPayableBill(contractMap);
			//获取当前时间月份
			int nowDate = new Date().getMonth();
			//判断客户是否存在合同
			if(CollectionUtils.isNotEmpty(list) && list.get(0) > 0){
				//判断应收单是否为当月应收
				if(nowDate == reEntity.getBusinessDate().getMonth()){
					throw new SettlementException("折扣应收不允许当月上报坏账");
				//判断是否生成折扣应付
				}else if(CollectionUtils.isEmpty(payableList)){
					throw new SettlementException("折扣应收未生成折扣应付不允许上报坏账");
				}
			}
		}
		
		// MODIFIY 095793 LIQIN ISSUE-3357，应收单对账单部分还款后，剩余部分不能申请坏账
		// 取应收单的对账单号
		// List<String> statementNos = new ArrayList<String>();
		// 取应收单的运单号
		List<String> waybillNos = new ArrayList<String>();
		// 如果应收单列表不为空
		if (CollectionUtils.isNotEmpty(billReceivableList)) {
			// 循环处理
			for (BillReceivableEntity receiveEntity : billReceivableList) {
				// 如果运单号存在
				if (StringUtils.isNotEmpty(receiveEntity.getWaybillNo())) {
					// 去运单号
					waybillNos.add(receiveEntity.getWaybillNo());
				}
			}
		}

		// 去除关联的运单存在有未受理的更改单的应收单
		List<BillReceivableEntity> waybillBillReceivableList = new ArrayList<BillReceivableEntity>();
		// 如果运单号不为空
		if (CollectionUtils.isNotEmpty(waybillNos)) {
			// 检测运单是否存在更改单，并返回存在更改单的运单号列表
			List<String> changeWaybillNoList = waybillRfcService
					.isExsitsWayBillRfcs(waybillNos);
			// 存在更改单的运单号列表不为空
			if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
				// 循环去除已经确认对账单的应收单列表
				for (BillReceivableEntity receiveEntity : billReceivableList) {
					// 是否更改状态
					boolean isChange = false;
					for (String changeWaybillNo : changeWaybillNoList) {
						// 如果未受理更改单的运单号和应收单中运单号一致，则去除
						if (changeWaybillNo
								.equals(receiveEntity.getWaybillNo())) {
							// 设置为已更改
							logger.info("应收单" + receiveEntity.getReceivableNo()
									+ "对应的运单" + receiveEntity.getWaybillNo()
									+ "存在未受理的更改单记录");
							isChange = true;
						}
					}
					// 如果未更改
					if (!isChange) {
						// 将应收单加入到去除关联的运单存在有未受理的更改单的应收单
						waybillBillReceivableList.add(receiveEntity);
					}
				}
			} else {
				// 如果不存在更改单，直接 将去除已经确认对账单的应收单列表 付给 去除关联的运单存在有未受理的更改单的应收单
				waybillBillReceivableList = billReceivableList;
			}
		} else {
			// 如果运单号列表为空，直接 将去除已经确认对账单的应收单列表 付给 去除关联的运单存在有未受理的更改单的应收单
			waybillBillReceivableList = billReceivableList;
		}

		// 结果为空，抛出异常
		if (CollectionUtils.isEmpty(waybillBillReceivableList)) {
			// 提示查询待坏账处理的应收记录为空
			throw new SettlementException("查询待坏账处理的应收记录为空;原因：运单未签收或存在未受理更改单或运单本不存在坏账应收");
		}

		// 返回处理后的应收单
		return waybillBillReceivableList;
	}

	/**
	 * 坏账核销
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:55:57 PM
	 * @see com.deppon.foss.module.settlement.IBillBadAccountService.api.server.service.IBillBadAccountWriteoffService#writeoffBadAccount(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillBadWriteoffDto)
	 */
	@Override
	@Transactional
	public void writeoffBadAccount(BillBadWriteoffDto badWriteoffDto) {
		// 工作流号不能为空
		if (StringUtils.isEmpty(badWriteoffDto.getSerialNo())) {
			// 提示工作流号为空，无法核销
			throw new SettlementException("工作流号为空，无法核销");
		}

		// 记录日志
		logger.info("坏账核销工作流号:" + badWriteoffDto.getSerialNo());

		// 核销金额不能为零
		if (null == badWriteoffDto.getWriteoffAmount()
				|| (null != badWriteoffDto.getWriteoffAmount() && badWriteoffDto
						.getWriteoffAmount().compareTo(BigDecimal.ZERO) == 0)) {
			// 提示核销金额为空或者等于零，无法核销
			throw new SettlementException("核销金额为空或者等于零，无法核销");
		}

		// 应收单号列表不能为空
		if (CollectionUtils.isEmpty(badWriteoffDto.getReceivableNos())) {
			// 提示应收单号列表为空，无法核销
			throw new SettlementException("应收单号列表为空，无法核销");
		}

		// 记录日志
		logger.info("坏账核销应收单号列表:" + badWriteoffDto.getReceivableNos());

		// 记录日志
		logger.info("坏账核销冲账方式:" + badWriteoffDto.getBillType());

		// 应收单号无有效记录
		List<BillReceivableEntity> recs = billReceivableService
				.queryByReceivableNOs(badWriteoffDto.getReceivableNos(),
						FossConstants.ACTIVE);
		// 有效应收单列表不能为空
		if (CollectionUtils.isEmpty(recs)) {
			// 提示应收单号找不到对应的有效应收单记录，无法核销
			throw new SettlementException("应收单号找不到对应的有效应收单记录，无法核销");
		}

		// 校验查询出的应收单记录数量是否和传入的应收单号数量一致
		if (recs.size() != badWriteoffDto.getReceivableNos().size()) {
			throw new SettlementException("应收单记录已经发生过变化，请重新查询后核销");
		}

		// 校验查询出的应收单的单号和传入的应收单号是否一致
		for (String recNo : badWriteoffDto.getReceivableNos()) {
			boolean isMaching = false;
			for (BillReceivableEntity rec : recs) {
				if (rec.getReceivableNo().equals(recNo)) {
					isMaching = true;
					break;
				}
			}
			if (!isMaching) {
				throw new SettlementException("应收单记录已经发生过变化，请重新查询后核销");
			}
		}

		// 取应收单的对账单号
		List<String> statementNos = new ArrayList<String>();
		// 应收单集合
		List<BillReceivableEntity> recList = new ArrayList<BillReceivableEntity>();
		// 取客户编码集合
		List<String> customerCodes = new ArrayList<String>();

		// 取应收单的运单号
		List<String> waybillNos = new ArrayList<String>();
		// 如果应收单列表不为空
		if (CollectionUtils.isNotEmpty(recs)) {
			// 循环处理
			for (BillReceivableEntity receiveEntity : recs) {
				// 如果对账单号存在
				if (StringUtils.isNotEmpty(receiveEntity.getStatementBillNo())
						&& !SettlementConstants.DEFAULT_BILL_NO
								.equals(receiveEntity.getStatementBillNo())) {
					// 取对账单号
					statementNos.add(receiveEntity.getStatementBillNo());
					//
					recList.add(receiveEntity);
				}
				// 如果运单号存在
				if (StringUtils.isNotEmpty(receiveEntity.getWaybillNo())) {
					// 去运单号
					waybillNos.add(receiveEntity.getWaybillNo());
				}
				//start 灰名单需求-如果客户编码存在--add by 269044-zhurongrong 2016-05-09 15:10
				if(StringUtils.isNotEmpty(receiveEntity.getCustomerCode())) {
					// 取客户编码
					customerCodes.add(receiveEntity.getCustomerCode());
				}
				//end
			}
		}

		// 如果运单号不为空
		if (CollectionUtils.isNotEmpty(waybillNos)) {
			// 检测运单是否存在更改单，并返回存在更改单的运单号列表
			List<String> changeWaybillNoList = waybillRfcService
					.isExsitsWayBillRfcs(waybillNos);
			// 存在更改单的运单号列表不为空
			if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
				// 循环去除已经确认对账单的应收单列表
				for (BillReceivableEntity receiveEntity : recs) {
					for (String changeWaybillNo : changeWaybillNoList) {
						// 如果未受理更改单的运单号和应收单中运单号一致，则去除
						if (changeWaybillNo
								.equals(receiveEntity.getWaybillNo())) {
							// 返回异常，应收单存在未受理的更改单，无法核销
							throw new SettlementException("应收单"
									+ receiveEntity.getReceivableNo() + "对应的运单"
									+ receiveEntity.getWaybillNo()
									+ "存在未受理的更改单记录，无法核销");
						}
					}
				}
			}
		}

		// 添加互斥锁收集数据
		List<MutexElement> mutexElements = new ArrayList<MutexElement>();

		// 核销金额不能大于应收单的未核销总额
		BigDecimal totalUnverifyAmount = BigDecimal.ZERO;
		// 循环处理
		for (BillReceivableEntity rec : recs) {
			// 累加总未核销金额
			totalUnverifyAmount = totalUnverifyAmount.add(rec
					.getUnverifyAmount());
			// 如果应收单中存在运单号
			if (StringUtils.isNotEmpty(rec.getWaybillNo())) {
				// 业务互锁运单号
				MutexElement mutexElement = new MutexElement(
						rec.getWaybillNo(), "坏账核销操作",
						MutexElementType.WAYBILL_NO);
				// 互斥对象加入到互斥对象集合
				mutexElements.add(mutexElement);
			}
		}
		// 坏账核销金额大于应收单未核销金额不能核销
		if (badWriteoffDto.getWriteoffAmount().compareTo(totalUnverifyAmount) > 0) {
			// 提示核销金额大于应收单未核销金额，无法核销
			throw new SettlementException("核销金额大于应收单未核销金额，无法核销");
		}

		// 添加互斥锁
		if (CollectionUtils.isNotEmpty(mutexElements)) {
			// 批量锁定
			businessLockService.lock(mutexElements,
					SettlementConstants.BUSINESS_LOCK_BATCH);
		}

		// 校验应收单是否网厅锁定、存在未受理的更改单
		validateReceiveable(recs);

		// 设置核销操作Dto
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();
		// 设置核销操作dto应收单列表参数
		billWriteoffOperationDto.setBillReceivableEntitys(recs);
		// 设置核销操作dto生成方式为自动生成
		billWriteoffOperationDto
				.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		// 核销批次号
		String writeoffBatchNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
		// 设置核销操作dto核销批次号参数
		billWriteoffOperationDto.setWriteoffBatchNo(writeoffBatchNo);
		// 设置核销操作dto参数坏账核销金额为
		billWriteoffOperationDto.setBadWriteoffAmount(badWriteoffDto
				.getWriteoffAmount());

		// 调用核销公用服务核销坏账
		billWriteoffService.writeoffBadAccountAndReceivable(
				billWriteoffOperationDto, badWriteoffDto.getBillType(),
				getCurrentInfoForFinance());

		CurrentInfo cInfo = getCurrentInfoForFinance();

		// 如果对账单号不为空
		if (CollectionUtils.isNotEmpty(statementNos)) {
			// 根据对账单号获取对账单列表
			List<StatementOfAccountEntity> statements = statementOfAccountService
					.queryByStatementNos(statementNos);
			// 如果对账单列表不为空
			if (CollectionUtils.isNotEmpty(statements)) {
				// 核销完成，通知修改对账单及对账单明细信息(调用对账信息)
				StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
				// 设置对账单修改dto核销应收单列表参数
				statementOfAccountUpdateDto.setReceivableEntityList(recList);
				// 修改对账单及对账单明细信息
				statementOfAccountService
						.updateBadAccountStatementAndDetailForWriteOff(
								statementOfAccountUpdateDto, cInfo);
			}
		}

		// 去除互斥锁
		if (CollectionUtils.isNotEmpty(mutexElements)) {
			// 解锁
			businessLockService.unlock(mutexElements);
		}
		
		//start 269044-zhurongrong 2016-05-09 15:10
		//存放待处理灰名单信息
		List<GrayCustomerEntity> grayCustomerEntitys = new ArrayList<GrayCustomerEntity>();
		//发生核销时，先判断该客户是否在灰名单中，在的话需判断是否拉出来，不在的话，直接pass
		if(CollectionUtils.isNotEmpty(customerCodes)) {
			//查询灰名单信息
			grayCustomerEntitys = grayCustomerService.queryGrayCustomerListByCustomerCodes(customerCodes);
		}
		if(CollectionUtils.isNotEmpty(grayCustomerEntitys)) {
			try{
				//调用判断时候修改灰名单接口
				grayCustomerService.updateGrayCustomerToECS(customerCodes);
			} catch (Exception e) {
				//打印异常
				logger.info("调用悟空更改灰名单接口异常" + e.getMessage());
			}				
		}
		//end
	}

	/**
	 * 生成财务自助登录信息
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:56:33 PM
	 */
	private CurrentInfo getCurrentInfoForFinance() {
		// 生成用户信息
		UserEntity user = new UserEntity();
		// 生成员工系想你
		EmployeeEntity employee = new EmployeeEntity();
		// 设置员工编码
		employee.setEmpCode(SettlementConstants.FINANCE_CODE);
		// 设置员工名称
		employee.setEmpName(SettlementConstants.FINANCE_NAME);
		// 设置用户员工信息
		user.setEmployee(employee);
		// 设置用户名
		user.setUserName(SettlementConstants.FINANCE_NAME);

		// 生成组织信息
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		// 设置组织信息编码
		dept.setCode(SettlementConstants.FINANCE_CODE);
		// 设置组织信息名称
		dept.setName(SettlementConstants.FINANCE_NAME);

		// 生成当前登录用户信息
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		// 返回当前登录用户信息
		return currentInfo;
	}

	/**
	 * 校验应收单，是否被网厅锁定、是否进入已确定的对账单、是否存在有未受理的更改单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2013-1-11 下午3:48:48
	 */
	private void validateReceiveable(List<BillReceivableEntity> receiveableList) {
		// 应收单中的运单编号
		List<String> waybillNoList = new ArrayList<String>();

		// 应收单编号
		List<String> receiveNos = new ArrayList<String>();

		// 校验应收单是否被网厅锁定
		List<String> lockBillList = new ArrayList<String>();
		// 循环处理
		for (BillReceivableEntity receiveEntity : receiveableList) {
			// 应收单号
			receiveNos.add(receiveEntity.getReceivableNo());

			// 如果应收单中存在网厅锁定
			if (receiveEntity.getUnlockDateTime() != null
					&& receiveEntity.getUnlockDateTime().after(new Date())) {
				// 加入网厅锁定列表
				lockBillList.add(receiveEntity.getReceivableNo());
			}

			// 如果应收单中存在运单号
			if (StringUtils.isNotEmpty(receiveEntity.getWaybillNo())) {
				// 加入运单列表
				waybillNoList.add(receiveEntity.getWaybillNo());
			}
		}
		// 如果有则抛出异常,并抛出对账单号和运单号
		// 如果官网锁定应收单号列表不为空
		if (CollectionUtils.isNotEmpty(lockBillList)) {
			// 生成应收单号StringBuffer
			StringBuffer expReceiveNos = new StringBuffer();
			// 循环处理
			for (int i = 0; i < lockBillList.size(); i++) {
				if (i != 0) {
					expReceiveNos.append(",");
				}
				// 循环将应收单号拼接到应收单StringBuffer
				expReceiveNos.append(lockBillList.get(i));
			}
			// 提示存在被网厅锁定的应收单，不能进行坏账核销操作！应收单号为XXXX,
			throw new SettlementException("存在被网厅锁定的应收单，不能进行坏账核销操作！应收单号为"
					+ expReceiveNos);
		}

		// 校验该对账单明细中的单据所关联的运单是否存在有未受理的更改单
		if (CollectionUtils.isNotEmpty(waybillNoList)) {
			// 检测运单是否存在更改单，并返回存在更改单的运单号列表
			List<String> changeWaybillNoList = waybillRfcService
					.isExsitsWayBillRfcs(waybillNoList);

			// 如果有则抛出异常,并抛出对账单号和运单号
			if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
				// 生成运单号StringBuffer
				StringBuffer waybillNos = new StringBuffer();
				// 循环处理
				for (int i = 0; i < changeWaybillNoList.size(); i++) {
					if (i != 0) {
						waybillNos.append(",");
					}
					// 循环将应收单号拼接到应收单StringBuffer
					waybillNos.append(changeWaybillNoList.get(i));
				}
				// 提示存在有未受理的更改单，不能进行坏账核销操作！存在更改的运单号为XXXXXX
				throw new SettlementException("存在有未受理的更改单，不能进行坏账核销操作！存在更改的运单号为"
						+ waybillNos);
			}
		}

	}

	/**
	 * @get
	 * @return billWriteoffService
	 */
	public IBillWriteoffService getBillWriteoffService() {
		/*
		 * @get
		 * 
		 * @return billWriteoffService
		 */
		return billWriteoffService;
	}

	/**
	 * @set
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		/*
		 * @set
		 * 
		 * @this.billWriteoffService = billWriteoffService
		 */
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * @get
	 * @return billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		/*
		 * @get
		 * 
		 * @return billReceivableService
		 */
		return billReceivableService;
	}

	/**
	 * @set
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		/*
		 * @set
		 * 
		 * @this.billReceivableService = billReceivableService
		 */
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @get
	 * @return settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		/*
		 * @get
		 * 
		 * @return settlementCommonService
		 */
		return settlementCommonService;
	}

	/**
	 * @set
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		/*
		 * @set
		 * 
		 * @this.settlementCommonService = settlementCommonService
		 */
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @get
	 * @return billBadWriteoffReceivableQueryDao
	 */
	public IBillBadWriteoffReceivableQueryDao getBillBadWriteoffReceivableQueryDao() {
		/*
		 * @get
		 * 
		 * @return billBadWriteoffReceivableQueryDao
		 */
		return billBadWriteoffReceivableQueryDao;
	}

	/**
	 * @set
	 * @param billBadWriteoffReceivableQueryDao
	 */
	public void setBillBadWriteoffReceivableQueryDao(
			IBillBadWriteoffReceivableQueryDao billBadWriteoffReceivableQueryDao) {
		/*
		 * @set
		 * 
		 * @this.billBadWriteoffReceivableQueryDao =
		 * billBadWriteoffReceivableQueryDao
		 */
		this.billBadWriteoffReceivableQueryDao = billBadWriteoffReceivableQueryDao;
	}

	/**
	 * @get
	 * @return waybillRfcService
	 */
	public IWaybillRfcService getWaybillRfcService() {
		/*
		 * @get
		 * 
		 * @return waybillRfcService
		 */
		return waybillRfcService;
	}

	/**
	 * @set
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		/*
		 * @set
		 * 
		 * @this.waybillRfcService = waybillRfcService
		 */
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @get
	 * @return statementOfAccountService
	 */
	public IStatementOfAccountService getStatementOfAccountService() {
		/*
		 * @get
		 * 
		 * @return statementOfAccountService
		 */
		return statementOfAccountService;
	}

	/**
	 * @set
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		/*
		 * @set
		 * 
		 * @this.statementOfAccountService = statementOfAccountService
		 */
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @get
	 * @return businessLockService
	 */
	public IBusinessLockService getBusinessLockService() {
		/*
		 * @get
		 * 
		 * @return businessLockService
		 */
		return businessLockService;
	}

	/**
	 * @set
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		/*
		 * @set
		 * 
		 * @this.businessLockService = businessLockService
		 */
		this.businessLockService = businessLockService;
	}

	public void setFreightDiscountService(IFreightDiscountService freightDiscountService) {
		this.freightDiscountService = freightDiscountService;
	}
	
	/**
	 * @SET
	 * @param grayCustomerService
	 */
	public void setGrayCustomerService(IGrayCustomerService grayCustomerService) {
		this.grayCustomerService = grayCustomerService;
	}

}
