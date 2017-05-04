/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deppon.foss.module.settlement.common.api.server.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillAdvancedPaymentEntityDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillDepositReceivedEntityDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWritebackOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IFossToFinanceRemittanceService;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardMService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IReverseBillWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IWoodenStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 反核消查询Service实现类
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-24 上午11:48:22
 */
public class ReverseBillWriteoffService implements IReverseBillWriteoffService {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(ReverseBillWriteoffService.class);
	
	/**
	 * 反核销Dao
	 */
	private IReverseBillWriteoffQueryDao reverseBillWriteoffQueryDao;

	/**
	 * 公共核销服务service
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 对账单公共service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 对账单明细公共service
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 制作对账单服务servcie
	 */
	private IStatementMakeService statementMakeService;

	/**
	 * 还款单服务service
	 */
	private IBillRepaymentService billRepaymentService;

	/**
	 * 结算通用服务service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 代收货款记录管理服务
	 */
	private ICodCommonService codCommonService;

	/**
	 * 汇款服务接口service
	 */
	private IFossToFinanceRemittanceService fossToFinanceRemittanceService;

	/**
	 * 公共预收单service
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 公共应付单service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 公共应收单service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 公共预付单service
	 */
	private IBillAdvancedPaymentService billAdvancedPaymentService;
	/**
	 * 代打木架对账单service
	 */
	private IWoodenStatementService woodenStatementService;

    /**
     * 财务自助网上支付接口代理
     */
    private IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;

    private IPartnerStatementOfAwardMService partnerStatementOfAwardMService;
    
    /**
	 * 灰名单服务
	 */
	private IGrayCustomerService grayCustomerService;

    /**
     * NCI项目接口T+0
     */
    private IPdaPosManageService pdaPosManageService;
    
    /**
	 * 根据参数集合，分页查询核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 上午11:33:15
	 */
	@Override
	public ReverseBillWriteoffDto queryBillWriteoffEntityList(
			ReverseBillWriteoffDto reverseBillWriteoffDto, int start,
			int limit, CurrentInfo cInfo) {

		// 参数dto不能为空
		if (reverseBillWriteoffDto == null) {
			// 提示内部错误，待反核销的核销单为空
			throw new SettlementException("内部错误，待反核销的核销单为空！");
		}

		// 设置查询条件（员工编号）
		reverseBillWriteoffDto.setEmpCode(cInfo.getEmpCode());

		// 用于返回的Dto
		ReverseBillWriteoffDto reverseBillWriteoffDtoRtn = new ReverseBillWriteoffDto();

		// 分页符合条件的核销单
		List<BillWriteoffEntity> billWriteoffEntityListByPage = null;

		// 如果核销单号为不为空，按核销单号查询
		if (StringUtils.isNotEmpty(reverseBillWriteoffDto.getWriteoffBillNo())) {
			// 记录日志
			LOGGER.debug("按核销单号查询核销单,核销单号:"
					+ reverseBillWriteoffDto.getWriteoffBillNo());

			// 根据核销单号查询核销单
			reverseBillWriteoffDtoRtn = reverseBillWriteoffQueryDao
					.queryBillWriteoffTotalByWriteoffBillNo(reverseBillWriteoffDto);

			// 如果查询结果不为空
			if (reverseBillWriteoffDtoRtn != null
					&& reverseBillWriteoffDtoRtn.getWriteoffTotalRows() > 0) {
				// 设置查询结果
				billWriteoffEntityListByPage = reverseBillWriteoffQueryDao
						.queryBillWriteoffEntityAllByWriteoffBillNo(reverseBillWriteoffDto);
			}

			// 如果核销单号为为空，运单号不为空，按运单号查询
		} else if (StringUtils
				.isNotEmpty(reverseBillWriteoffDto.getWaybillNo())) {
			// 记录日志
			LOGGER.debug("按运单号查询核销单,运单号:"
					+ reverseBillWriteoffDto.getWaybillNo());

			// 根据运单号查询核销单
			reverseBillWriteoffDtoRtn = reverseBillWriteoffQueryDao
					.queryBillWriteoffTotalByWaybillNo(reverseBillWriteoffDto);
			// 如果查询结果不为空
			if (reverseBillWriteoffDtoRtn != null
					&& reverseBillWriteoffDtoRtn.getWriteoffTotalRows() > 0) {
				// 设置查询结果
				billWriteoffEntityListByPage = reverseBillWriteoffQueryDao
						.queryBillWriteoffEntityAllByWaybillNo(reverseBillWriteoffDto);
			}
			// 否则按预付单号查询核销单
		} else if (StringUtils.isNotEmpty(reverseBillWriteoffDto
				.getAdvancePayBillNo())) {
			// 记录日志
			LOGGER.debug("按预付单号查询核销单,预付单号:"
					+ reverseBillWriteoffDto.getAdvancePayBillNo());

			// 根据预付单号查询核销单
			reverseBillWriteoffDtoRtn = reverseBillWriteoffQueryDao
					.queryBillWriteoffTotalByAdvPayillNo(reverseBillWriteoffDto);
			// 如果查询结果不为空
			if (reverseBillWriteoffDtoRtn != null
					&& reverseBillWriteoffDtoRtn.getWriteoffTotalRows() > 0) {
				// 设置查询结果
				billWriteoffEntityListByPage = reverseBillWriteoffQueryDao
						.queryBillWriteoffEntityByAdvPayillNo(reverseBillWriteoffDto);
			}
			// 否则应付单号查询核销单
		} else if (StringUtils.isNotEmpty(reverseBillWriteoffDto
				.getPayableBillNo())) {
			// 记录日志
			LOGGER.debug("按应付单号查询核销单,应付单号:"
					+ reverseBillWriteoffDto.getPayableBillNo());

			// 根据应付单号查询核销单
			reverseBillWriteoffDtoRtn = reverseBillWriteoffQueryDao
					.queryBillWriteoffTotalByPayableBillNo(reverseBillWriteoffDto);
			// 如果查询结果不为空
			if (reverseBillWriteoffDtoRtn != null
					&& reverseBillWriteoffDtoRtn.getWriteoffTotalRows() > 0) {
				// 设置查询结果
				billWriteoffEntityListByPage = reverseBillWriteoffQueryDao
						.queryBillWriteoffEntityByPayableBillNo(reverseBillWriteoffDto);
			}
			// 否则预收单号查询核销单
		} else if (StringUtils
				.isNotEmpty(reverseBillWriteoffDto.getDepositNo())) {
			// 根据应付单号查询核销单
			reverseBillWriteoffDtoRtn = reverseBillWriteoffQueryDao
					.queryBillWriteoffTotalByDepositNo(reverseBillWriteoffDto);
			// 如果查询结果不为空
			if (reverseBillWriteoffDtoRtn != null
					&& reverseBillWriteoffDtoRtn.getWriteoffTotalRows() > 0) {
				// 设置查询结果
				billWriteoffEntityListByPage = reverseBillWriteoffQueryDao
						.queryBillWriteoffEntityByDepositNo(reverseBillWriteoffDto);
			}
			// 否则参数查询
		} else {
			// 记录日志
			LOGGER.debug("按参数查询核销单...");
			// 根据日期查询核销单
			reverseBillWriteoffDtoRtn = reverseBillWriteoffQueryDao
					.queryBillWriteoffTotalByParams(reverseBillWriteoffDto);
			// 如果查询结果不为空
			if (reverseBillWriteoffDtoRtn != null
					&& reverseBillWriteoffDtoRtn.getWriteoffTotalRows() > 0) {
				// 设置查询结果
				billWriteoffEntityListByPage = reverseBillWriteoffQueryDao
						.queryBillWriteoffEntityListByParams(
								reverseBillWriteoffDto, start, limit);
			}
		}
		// 如果查询结果不为空
		if (CollectionUtils.isNotEmpty(billWriteoffEntityListByPage)) {

			// 将分页的结果集，设置到返回对象中
			reverseBillWriteoffDtoRtn
					.setBillWriteoffEntityList(billWriteoffEntityListByPage);
		}

		// 返回查询结果
		return reverseBillWriteoffDtoRtn;
	}

	/**
	 * 根据参数集合，查询全部核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-20 上午11:27:13
	 */
	@Override
	public ReverseBillWriteoffDto queryBillWriteoffEntityAll(
			ReverseBillWriteoffDto reverseBillWriteoffDto, CurrentInfo cInfo) {

		// 参数dot不能为空
		if (reverseBillWriteoffDto == null) {
			// 提示内部错误，查询条件为空
			throw new SettlementException("内部错误，查询条件为空！");
		}

		// 设置查询条件（员工编号）
		reverseBillWriteoffDto.setEmpCode(cInfo.getEmpCode());

		// 全部符合条件的核销单
		List<BillWriteoffEntity> billWriteoffEntityList = null;

		// 如果核销单号为不为空，按核销单号查询
		if (StringUtils.isNotEmpty(reverseBillWriteoffDto.getWriteoffBillNo())) {
			// 记录日志
			LOGGER.debug("按核销单号查询核销单,核销单号:"
					+ reverseBillWriteoffDto.getWriteoffBillNo());
			// 设置查询结果
			billWriteoffEntityList = reverseBillWriteoffQueryDao
					.queryBillWriteoffEntityAllByWriteoffBillNo(reverseBillWriteoffDto);

			// 如果核销单号为为空，运单号不为空，按运单号查询，且不分页
		} else if (StringUtils
				.isNotEmpty(reverseBillWriteoffDto.getWaybillNo())) {
			// 记录日志
			LOGGER.debug("按运单号查询核销单,运单号:"
					+ reverseBillWriteoffDto.getWaybillNo());
			// 设置查询结果
			billWriteoffEntityList = reverseBillWriteoffQueryDao
					.queryBillWriteoffEntityAllByWaybillNo(reverseBillWriteoffDto);
			// 否则按预付单号查询核销单
		} else if (StringUtils.isNotEmpty(reverseBillWriteoffDto
				.getAdvancePayBillNo())) {
			// 记录日志
			LOGGER.debug("按预付单号查询核销单,预付单号:"
					+ reverseBillWriteoffDto.getAdvancePayBillNo());

			// 根据预付单号查询核销单
			billWriteoffEntityList = reverseBillWriteoffQueryDao
					.queryBillWriteoffEntityByAdvPayillNo(reverseBillWriteoffDto);
			// 否则应付单号查询核销单
		} else if (StringUtils.isNotEmpty(reverseBillWriteoffDto
				.getPayableBillNo())) {
			// 记录日志
			LOGGER.debug("按应付单号查询核销单,应付单号:"
					+ reverseBillWriteoffDto.getPayableBillNo());

			// 根据应付单号查询核销单
			billWriteoffEntityList = reverseBillWriteoffQueryDao
					.queryBillWriteoffEntityByPayableBillNo(reverseBillWriteoffDto);
			// 否则参数查询
			// 否则预收单号查询核销单
		} else if (StringUtils
				.isNotEmpty(reverseBillWriteoffDto.getDepositNo())) {
			// 记录日志
			LOGGER.debug("按应付单号查询核销单,应付单号:"
					+ reverseBillWriteoffDto.getPayableBillNo());
			// 设置查询结果
			billWriteoffEntityList = reverseBillWriteoffQueryDao
					.queryBillWriteoffEntityByDepositNo(reverseBillWriteoffDto);
			// 否则参数查询
		} else {
			// 记录日志
			LOGGER.debug("按参数查询核销单...");
			// 设置查询结果
			billWriteoffEntityList = reverseBillWriteoffQueryDao
					.queryBillWriteoffEntityALLByParams(reverseBillWriteoffDto);
		}

		// 用于返回的Dto
		ReverseBillWriteoffDto reverseBillWriteoffDtoRtn = new ReverseBillWriteoffDto();

		// 封装数据到Dto
		if (CollectionUtils.isNotEmpty(billWriteoffEntityList)) {

			// 将分页的结果集，设置到返回对象中
			reverseBillWriteoffDtoRtn
					.setBillWriteoffEntityList(billWriteoffEntityList);
		}
		// 返回查询结果
		return reverseBillWriteoffDtoRtn;
	}

	/**
	 * 根据核销单号，查询待核销的数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-31 上午9:35:53
	 */
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityByNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto, CurrentInfo cInfo) {

		// 参数dto不能为空
		if (reverseBillWriteoffDto == null) {
			// 提示内部错误，核销单对象为空!
			throw new SettlementException("内部错误，核销单对象为空!");
		}

		// 设置查询条件（员工编号）
		reverseBillWriteoffDto.setEmpCode(cInfo.getEmpCode());

		// 设置可核销数据查询隐含条件条件：有效
		reverseBillWriteoffDto.setActive(FossConstants.ACTIVE);
		// 设置可核销数据查询隐含条件条件：非红单
		reverseBillWriteoffDto.setIsRedBack(FossConstants.NO);

		// 返回查询结果
		return reverseBillWriteoffQueryDao
				.queryBillWriteoffEntityByNo(reverseBillWriteoffDto);
	}

	/**
	 * 根据核销单号，反核销数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-31 上午11:33:15
	 */
	@Override
	@Transactional
	public void reverseBillWriteOff(
			ReverseBillWriteoffDto reverseBillWriteoffDto, CurrentInfo cInfo) {
		// 1、验证是否可以进行反核消：
		// 参数dto不能为空
		if (reverseBillWriteoffDto == null) {
			// 提示内部错误，核销对象为空
			throw new SettlementException("内部错误，核销对象为空！");
		}

		// 根据选中的核销单号查询核销单
		List<BillWriteoffEntity> billWriteoffEntitylist = queryBillWriteoffEntityByNo(
				reverseBillWriteoffDto, cInfo);

		// 1.1判断选中选中的核销单是否发生变化
		if (CollectionUtils.isEmpty(billWriteoffEntitylist)) {
			// 提示选择的核销单已经反核销过
			throw new SettlementException("选择的核销单已经反核销过!");
		}
		// 1.1判断选中选中的核销单是否发生变化
		if (billWriteoffEntitylist.size() < reverseBillWriteoffDto
				.getWriteoffBillNoList().size()) {
			// 选择的核销单已部分被反核销过,请重新选择
			throw new SettlementException("选择的核销单已部分被反核销过,请重新选择!");
		}
		// 代打木架核销单
		List<BillWriteoffEntity> woodenWriteoff = new ArrayList<BillWriteoffEntity>();
		// 客户核销单
		List<BillWriteoffEntity> normalWriteoff = new ArrayList<BillWriteoffEntity>();
		// 奖罚核销单
		List<BillWriteoffEntity> awardWriteoff = new ArrayList<BillWriteoffEntity>();
		if (CollectionUtils.isNotEmpty(billWriteoffEntitylist)) {
			// 对核销单分类
			for (BillWriteoffEntity entity : billWriteoffEntitylist) {
				// 普通核销单
				if (SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())
						|| StringUtils.isEmpty(entity.getStatementBillNo())) {
					normalWriteoff.add(entity);
				} else {
					//ddw
					// 查看是否为
					WoodenStatementEntity woodenStatement = woodenStatementService.queryByStatementNo(entity.getStatementBillNo());
					PartnerStatementOfAwardEntity partnerStatementOfAward = partnerStatementOfAwardMService.queryPStatementsByStaNoNPage(entity.getStatementBillNo());
					StatementOfAccountEntity statementOfAccountEntity = statementOfAccountService.queryByStatementNo(entity.getStatementBillNo());
					// 查看是何种对账单类型
					if (null != woodenStatement) {
						woodenWriteoff.add(entity);
					} else if(null != partnerStatementOfAward) {
						//奖罚对账核销
						awardWriteoff.add(entity);
					} else if(null != statementOfAccountEntity) {
						//普通对账核销
						normalWriteoff.add(entity);
					}
				}
			}
		}
		// 分别调用普通反核销接口和代打木架反核销接口
		if (woodenWriteoff.size() > 0) {
			this.reverseWoodenStatement(woodenWriteoff, cInfo);
		}

		if (normalWriteoff.size() > 0) {
			this.reverseNormalStatement(normalWriteoff, cInfo);
		}
		
		if (awardWriteoff.size() > 0) {
			throw new SettlementException("奖罚对账单不可以反核销");
		}
		
		//start 269044-zhurongrong--灰名单需求 2016-05-09 15:12
		//发生反核销时，先判断该客户是否在灰名单中，不在的话需判断是否添加进去，在的话，需要更新最久欠款日期
		//存放客户编码集合
		List<String> list = new ArrayList<String>();
		//获取所有的客户编码集合
		if(normalWriteoff.size() > 0) {
			for(BillWriteoffEntity writeoffEntity : normalWriteoff) {
				//如果核销单的客户编码不为空
				if(StringUtils.isNotEmpty(writeoffEntity.getCustomerCode())) {
					//添加到集合中
					list.add(writeoffEntity.getCustomerCode());
				} else {
					continue;
				}
			}
		}
		//此时已经去掉重复的数据保存在hashset中
		Set<String> hs = new HashSet<String>(list); 
		//将hashSet数据转化为ArrayList中
		List<String> newList = new ArrayList<String>(hs);	
		try{
			//调用判断时候修改灰名单接口
			grayCustomerService.updateGrayCustomerToECS(newList);
		} catch (Exception e) {
			//打印异常
			LOGGER.info("调用悟空更改灰名单接口异常" + e.getMessage());
		}		
		//end
	}

	/**
	 * 
	 * @Title: reverseWoodenSatement
	 * @Description: 反核销代打木架 2014-6-11上午11:37:02
	 * @param @param reverseBillWriteoffDto
	 * @param @param cInfo
	 * @throws
	 */
	@Override
	public void reverseWoodenStatement(List<BillWriteoffEntity> writeoffEntities, CurrentInfo cInfo) {
		
		// 生成调用接口的Dto参数及其应收、应付、预收、预付对象的list
		List<BillReceivableEntity> receivableEntityList = null;// 应收单
		List<BillPayableEntity> payableEntityList = null;// 应付单
		List<BillDepositReceivedEntity> depositReceivedEntityList = null;// 预收单
		List<BillAdvancedPaymentEntity> advancePaymentEntityList = null;// 预付单
		// 初始化待释放汇款的还款单列表
		List<BillRepaymentEntity> oldEntityList = new ArrayList<BillRepaymentEntity>();
		// 当前时间
		Date now = Calendar.getInstance().getTime();
		// ***************************循环对核销单进行反核销******************
		// 循环调用
		for (BillWriteoffEntity entity : writeoffEntities) {

			// 2、反核消接口进行反核消，并获取反核消成功后的明细结果
			BillWritebackOperationDto writebackDto = billWriteoffService
					.writeBackBillWriteoffByEntity(entity, cInfo);
			// 记录日志
			LOGGER.info("根据核销单号,反核销核销单，核销单号:" + entity.getWriteoffBillNo());

			// 3、如果反核消还款冲应收类型的核销单，进行还款单处理
			// 如果是还款冲应收，需要调用还款单服务，生成新的还款单，金额为核销单的负值
			if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
					.equals(entity.getWriteoffType())) {
				// 生成新的还款单
				BillRepaymentEntity newEntity = new BillRepaymentEntity();
				// 根据还款单号查询还款单
				BillRepaymentEntity oldEntity = billRepaymentService
						.queryByRepaymentNO(entity.getBeginNo(),
								FossConstants.ACTIVE);
				// 加入待释放汇款的还款单
				oldEntityList.add(oldEntity);
				
				// 根据原还款单生成新的还款单，并设置相关属性
				BeanUtils.copyProperties(oldEntity, newEntity);// 拷贝老还款单数据到新还款单
				newEntity.setId(UUIDUtils.getUUID());// 新ID
				newEntity.setVersionNo(FossConstants.INIT_VERSION_NO);// 初始版本号
				newEntity.setAccountDate(now);// 当前日期
				newEntity
						.setAmount(NumberUtils.multiply(entity.getAmount(), -1));// 还款单金额
				newEntity.setTrueAmount(NumberUtils.multiply(
						entity.getAmount(), -1));// 实际还款金额
				newEntity.setBverifyAmount(BigDecimal.ZERO);// 反核消金额
				// String repaymentNo =
				// settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1);//
				// 获取还款单号
				// newEntity.setRepaymentNo(repaymentNo);// 新还款单号
				newEntity.setRepaymentNo(oldEntity.getRepaymentNo());// 2013-04-24，修改为使用相同还款单号
				newEntity
						.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);// 状态默认为提交
				// 2013-05-27 反核消生成还款单设置为无效、红单
				newEntity.setActive(FossConstants.INACTIVE);
				newEntity
						.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
				billRepaymentService.addBillRepayment(newEntity, cInfo);// 保存新还款单

				// 如果还款金额金额等于反核消金额，将该还款单设置为无效
				if (oldEntity.getAmount().compareTo(
						oldEntity.getBverifyAmount()) == 0) {
					// 更新还款单的效状态
					billRepaymentService.revereWriteoffBillRepayment(oldEntity,
							cInfo);
				}
			}

			// 4、依次获取核销完成后的应收、应付、预收、预付单据
			// 如果反核销后应收单实体存在，加入到应收单list集合参数中
			if (writebackDto != null
					&& writebackDto.getBillReceivableEntity() != null) {
				// 如果应收单不为空
				if (CollectionUtils.isEmpty(receivableEntityList)) {
					// 新建应收单列表
					receivableEntityList = new ArrayList<BillReceivableEntity>();
				}
				// 获取核销返回应收单
				receivableEntityList
						.add(writebackDto.getBillReceivableEntity());
			}

			// 如果反核销后应付单实体存在，加入到应付单list集合参数中
			if (writebackDto != null
					&& writebackDto.getBillPayableEntity() != null) {
				// 如果应付单不为空
				if (CollectionUtils.isEmpty(payableEntityList)) {
					// 新建应付单列表
					payableEntityList = new ArrayList<BillPayableEntity>();
				}
				// 获取核销返回应付单
				payableEntityList.add(writebackDto.getBillPayableEntity());
			}

			// 如果反核销后预收单实体存在，加入到预收单list集合参数中
			if (writebackDto != null
					&& writebackDto.getBillDepositReceivedEntity() != null) {
				// 如果预收单不为空
				if (CollectionUtils.isEmpty(depositReceivedEntityList)) {
					// 新建预收单列表
					depositReceivedEntityList = new ArrayList<BillDepositReceivedEntity>();
				}
				// 获取核销返回预收单
				depositReceivedEntityList.add(writebackDto
						.getBillDepositReceivedEntity());
			}

			// 如果反核销后预付单实体存在，加入到预付单list集合参数中
			if (writebackDto != null
					&& writebackDto.getBillAdvancedPaymentEntity() != null) {
				// 如果预付单不为空
				if (CollectionUtils.isEmpty(advancePaymentEntityList)) {
					// 新建预付单列表
					advancePaymentEntityList = new ArrayList<BillAdvancedPaymentEntity>();
				}
				// 获取核销返预付单
				advancePaymentEntityList.add(writebackDto
						.getBillAdvancedPaymentEntity());
			}
		}

		// **************************************释放对账单*****************************
		for (BillWriteoffEntity entity : writeoffEntities) {

			// 存放各种待释放的明细单据集合(应收、应付、预收、预付)
			List<BillPayableEntity> toReleasePayableEntityList = new ArrayList<BillPayableEntity>();
			List<BillDepositReceivedEntity> toReleaseDepositReceivedEntityList = new ArrayList<BillDepositReceivedEntity>();
			List<BillAdvancedPaymentEntity> toReleaseAdvancedPaymentEntityList = new ArrayList<BillAdvancedPaymentEntity>();
			List<BillReceivableEntity> toReleaseReceivableEntityList = new ArrayList<BillReceivableEntity>();

			// 5.1、针对对账单核销：
			if (StringUtils.isNotEmpty(entity.getStatementBillNo())
					&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity
							.getStatementBillNo())) {
				// 根据对账单号查询对账单
				WoodenStatementEntity stateEntity = woodenStatementService
						.queryByStatementNo(entity.getStatementBillNo());

				/*
				 * 6、释放对账单明细： 1、如果该对账单明细不存在有效的核销单信息（按明细单号、对账单号共同查询），释放该对账单明细
				 * 2、释放方式： 1、删除对账单明细 2、将对账单明细的本体单据（应收、应付等）去掉对账单号
				 * 3、重新计算对账单本期的各种金额
				 */
				// 针对对账单核销，释放明细
				if (StringUtils.isNotEmpty(entity.getStatementBillNo())
						&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity
								.getStatementBillNo())) {

					// 1、如果是还款冲应收类型核销单,只需要根据对账单号和目的单号检查是否存在有效的核销单
					if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
							.equals(entity.getWriteoffType())) {
						// 根据应收单号查询应收单
						BillReceivableEntity billReceivableEntity = billReceivableService
								.queryByReceivableNO(entity.getEndNo(),
										FossConstants.ACTIVE);
						// 如果应收单不为空，且应收单的对账单号等于对账单应收明细的对账单号
						if (billReceivableEntity != null) {

							// 将应收单加入到待释放应收单明细集合
							boolean flagIsExist = false;
							if (!CollectionUtils
									.isEmpty(toReleaseReceivableEntityList)) {
								for (BillReceivableEntity temp : toReleaseReceivableEntityList) {
									if (temp.getReceivableNo().equals(
											billReceivableEntity
													.getReceivableNo())) {
										flagIsExist = true;
									}
								}
							}
							if (!flagIsExist) {
								toReleaseReceivableEntityList
										.add(billReceivableEntity);
							}

						}

						// 2、如果其他类型核销单，需要分别以开始单号、目的单号和对账单号组合检查是否存在有效的核销单
					} else {

						validaBillWriteoffEntity(entity,
								toReleaseReceivableEntityList);
						// 根据应付单号查询应付单
						if (SettlementUtil.isPayable(entity.getBeginNo())) {
							BillPayableEntity billPayableEntity = billPayableService
									.queryByPayableNO(entity.getBeginNo(),
											FossConstants.ACTIVE);
							// 如果应付单不为空，且 应付单的对账单号等于对账单应付明细的对账单号
							if (billPayableEntity != null) {
								// 将应付单加入到待释放应付单明细集合
								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleasePayableEntityList)) {
									for (BillPayableEntity temp : toReleasePayableEntityList) {
										if (temp.getPayableNo().equals(
												billPayableEntity
														.getPayableNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleasePayableEntityList
											.add(billPayableEntity);
								}

							}
						}
						// 根据预收单号查询
						if (SettlementUtil.isDepositReceived(entity
								.getBeginNo())) {
							BillDepositReceivedEntity billDepositReceivedEntity = billDepositReceivedService
									.queryByDepositReceivedNo(
											entity.getBeginNo(),
											FossConstants.ACTIVE);
							// 如果预收单不为空，且 预收单的对账单号等于对账单预收明细的对账单号
							if (billDepositReceivedEntity != null) {
								// 将预收单加入到待释放预收单明细集合

								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleaseDepositReceivedEntityList)) {
									for (BillDepositReceivedEntity temp : toReleaseDepositReceivedEntityList) {
										if (temp.getDepositReceivedNo()
												.equals(billDepositReceivedEntity
														.getDepositReceivedNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleaseDepositReceivedEntityList
											.add(billDepositReceivedEntity);
								}

							}
						}
						// 根据预付单号查询
						if (SettlementUtil.isAdvancedPayment(entity
								.getBeginNo())) {
							BillAdvancedPaymentEntity billAdvancedPaymentEntity = billAdvancedPaymentService
									.queryBillAdvancedPaymentNo(
											entity.getBeginNo(),
											FossConstants.ACTIVE);
							validaPaymentEntity(
									toReleaseAdvancedPaymentEntityList,
									billAdvancedPaymentEntity);
						}

						// 根据应收单号查询应收单
						if (SettlementUtil.isReceiveable(entity.getEndNo())) {
							BillReceivableEntity billReceivableEntity = billReceivableService
									.queryByReceivableNO(entity.getEndNo(),
											FossConstants.ACTIVE);
							// 如果应收单不为空，且应收单的对账单号等于对账单应收明细的对账单号
							if (billReceivableEntity != null) {
								// 将应收单加入到待释放应收单明细集合

								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleaseReceivableEntityList)) {
									for (BillReceivableEntity temp : toReleaseReceivableEntityList) {
										if (temp.getReceivableNo().equals(
												billReceivableEntity
														.getReceivableNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleaseReceivableEntityList
											.add(billReceivableEntity);
								}

							}
						}
						// 根据应付单号查询应付单
						if (SettlementUtil.isPayable(entity.getEndNo())) {
							BillPayableEntity billPayableEntity = billPayableService
									.queryByPayableNO(entity.getEndNo(),
											FossConstants.ACTIVE);
							// 如果应付单不为空，且 应付单的对账单号等于对账单应付明细的对账单号
							if (billPayableEntity != null) {
								// 将应付单加入到待释放应付单明细集合
								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleasePayableEntityList)) {
									for (BillPayableEntity temp : toReleasePayableEntityList) {
										if (temp.getPayableNo().equals(
												billPayableEntity
														.getPayableNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleasePayableEntityList
											.add(billPayableEntity);
								}

							}
						}
						// 根据预收单号查询
						if (SettlementUtil.isDepositReceived(entity.getEndNo())) {
							BillDepositReceivedEntity billDepositReceivedEntity = billDepositReceivedService
									.queryByDepositReceivedNo(
											entity.getEndNo(),
											FossConstants.ACTIVE);
							// 如果预收单不为空，且 预收单的对账单号等于对账单预收明细的对账单号
							if (billDepositReceivedEntity != null) {
								// 将预收单加入到待释放预收单明细集合

								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleaseDepositReceivedEntityList)) {
									for (BillDepositReceivedEntity temp : toReleaseDepositReceivedEntityList) {
										if (temp.getDepositReceivedNo()
												.equals(billDepositReceivedEntity
														.getDepositReceivedNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleaseDepositReceivedEntityList
											.add(billDepositReceivedEntity);
								}

							}
						}
						// 根据预付单号查询
						if (SettlementUtil.isAdvancedPayment(entity.getEndNo())) {
							BillAdvancedPaymentEntity billAdvancedPaymentEntity = billAdvancedPaymentService
									.queryBillAdvancedPaymentNo(
											entity.getEndNo(),
											FossConstants.ACTIVE);
							validaPaymentEntity(
									toReleaseAdvancedPaymentEntityList,
									billAdvancedPaymentEntity);
						}

					}
				}

				// 修改对账单明细的本体单据的对账单号为默认单号
				// 如果待释放的应收单列表不为空
				if (!CollectionUtils.isEmpty(toReleaseReceivableEntityList)) {
					// 生成批量修改应收单对账单号dto
					BillReceivableDto dto = new BillReceivableDto();
					// 设置默认对账单号
					dto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
					// 设置待释放的应收单列表
					dto.setBillReceivables(toReleaseReceivableEntityList);
					// 批量修改
					billReceivableService
							.batchUpdateByMakeStatement(dto, cInfo);
				}

				// 如果待释放的应付单列表不为空
				if (!CollectionUtils.isEmpty(toReleasePayableEntityList)) {
					// 生成批量修改应收付单对账单号dto
					BillPayableDto dto = new BillPayableDto();
					// 设置默认对账单号
					dto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
					// 设置待释放的应付单列表
					dto.setBillPayables(toReleasePayableEntityList);
					// 批量修改
					billPayableService.batchUpdateByMakeStatement(dto, cInfo);
				}

				// 如果待释放的预收单列表不为空
				if (!CollectionUtils
						.isEmpty(toReleaseDepositReceivedEntityList)) {
					// 生成批量修改预收单对账单号dto
					BillDepositReceivedEntityDto dto = new BillDepositReceivedEntityDto();
					// 设置默认对账单号
					dto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
					// 设置待释放的预收单列表
					dto.setBillDepositReceivedEntityList(toReleaseDepositReceivedEntityList);
					// 批量修改
					billDepositReceivedService
							.batchUpdateBillDepositReceivedByMakeStatement(dto,
									cInfo);
				}

				// 如果待释放的预付单列表不为空
				if (!CollectionUtils
						.isEmpty(toReleaseAdvancedPaymentEntityList)) {
					// 生成批量修改预付单对账单号dto
					BillAdvancedPaymentEntityDto dto = new BillAdvancedPaymentEntityDto();
					// 设置默认对账单号
					dto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
					// 设置待释放的预付单列表
					dto.setBillAdvancedPayment(toReleaseAdvancedPaymentEntityList);
					// 批量修改
					billAdvancedPaymentService.batchUpdateByMakeStatement(dto,
							cInfo);
				}

				// 5.2、非对账单核销：即通过预收冲应收、应收冲应付等界面核销，但却属于对账单明细中
			} else {

				// 生成未确认对账单的应收单列表
				List<BillReceivableEntity> noConfirmReceivableEntityList = new ArrayList<BillReceivableEntity>();
				// 生成未确认对账单的应付单列表
				List<BillPayableEntity> noConfirmPayableEntityList = new ArrayList<BillPayableEntity>();
				// 生成未确认对账单的预收单列表
				List<BillDepositReceivedEntity> noConfirmDepositReceivedEntityList = new ArrayList<BillDepositReceivedEntity>();
				// 生成未确认对账单的预付单列表
				List<BillAdvancedPaymentEntity> noConfirmAdvancedPaymentEntityList = new ArrayList<BillAdvancedPaymentEntity>();

				// 循环核销后的应收单，把未确认的应收单加入到未确认对账单应收单列表
				if (!CollectionUtils.isEmpty(receivableEntityList)) {
					// 循环处理应收单
					for (BillReceivableEntity entityTemp : receivableEntityList) {
						// 根据对账单号查询对账单
						StatementOfAccountEntity stateEntity = statementOfAccountService
								.queryByStatementNo(entityTemp
										.getStatementBillNo());
						// 如果查询不到对账单，抛出业务异常
						if (stateEntity != null) {
							// 如果对账单是未确认状态
							if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
									.equals(stateEntity.getConfirmStatus())) {
								// 加入到未确认对账单应收单列表
								noConfirmReceivableEntityList.add(entityTemp);
							}
						}
					}
				}

				// 循环核销后的应付单，把未确认的应付单加入到未确认对账单应付单列表
				if (!CollectionUtils.isEmpty(payableEntityList)) {
					// 循环处理应付单
					for (BillPayableEntity entityTemp : payableEntityList) {
						// 根据对账单号查询对账单
						StatementOfAccountEntity stateEntity = statementOfAccountService
								.queryByStatementNo(entityTemp
										.getStatementBillNo());
						// 如果查询不到对账单，抛出业务异常
						if (stateEntity != null) {
							// 如果对账单是未确认状态
							if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
									.equals(stateEntity.getConfirmStatus())) {
								// 加入到未确认对账单的应付单列表
								noConfirmPayableEntityList.add(entityTemp);
							}
						}
					}
				}

				validaDeposit(depositReceivedEntityList,
						noConfirmDepositReceivedEntityList);

				validaBillAdvanced(advancePaymentEntityList,
						noConfirmAdvancedPaymentEntityList);

				// 反核销完成，对于未确认的子单据，通知修改对账单及对账单明细信息
				StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
				statementOfAccountUpdateDto
						.setReceivableEntityList(noConfirmReceivableEntityList);
				statementOfAccountUpdateDto
						.setPayableEntityList(noConfirmPayableEntityList);
				statementOfAccountUpdateDto
						.setDepositReceivedEntityList(noConfirmDepositReceivedEntityList);
				statementOfAccountUpdateDto
						.setAdvancePaymentEntityList(noConfirmAdvancedPaymentEntityList);
				statementOfAccountService
						.updateStatementAndDetailForBackWriteOff(
								statementOfAccountUpdateDto, cInfo);
			}

		}
		// 7、释放汇款：
		for (BillWriteoffEntity entity : writeoffEntities) {
			for (BillRepaymentEntity oldEntity : oldEntityList) {
				if (entity.getBeginNo().equals(oldEntity.getRepaymentNo())) {
                     // 调用费控接口，去释放汇款
                     if (StringUtils.isNotEmpty(oldEntity.getOaPaymentNo()) && FossConstants.NO.equals(oldEntity.getIsInit())
                             && (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                             .endsWith(oldEntity.getPaymentType()) || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE
                             .endsWith(oldEntity.getPaymentType()))) {
                          LOGGER.info("反核消还款单时调用费控接口释放汇款,汇款单号号："
                                  + oldEntity.getOaPaymentNo());
                          fossToFinanceRemittanceService.releaseClaim(
                                  entity.getAmount(), oldEntity.getOaPaymentNo(),
                                  oldEntity.getCollectionOrgCode(),
                                  oldEntity.getRepaymentNo());
                     }
                        /*
                         * 调用费控接口，去释放网上支付
                         *
                         * DMANA-6876 FOSS-网上支付发更改重新还款
                         * Author 105762
                         */

                     if (StringUtils.isNotEmpty(oldEntity.getOnlinePaymentNo()) && FossConstants.NO.equals(oldEntity.getIsInit())
                             && (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.endsWith(oldEntity.getPaymentType()))) {
                          LOGGER.info("反核消还款单时调用费控接口释放网上支付款,网上支付编号：" + oldEntity.getOnlinePaymentNo());
                          financeOnlinePayWSProxy.release(oldEntity.getOnlinePaymentNo(), entity.getAmount());
                     }
                }
			}
		}
	}

	/**
	 * 
	 * @Title: reverseNormalStatement
	 * @Description: 普通对账的反核销记录
	 * @param billWriteoffEntitylist
	 * @param cInfo
	 *            参数
	 * @return void 返回值
	 * @throws
	 */
	private void reverseNormalStatement(
			List<BillWriteoffEntity> billWriteoffEntitylist, CurrentInfo cInfo) {

		/*
		 * 反核消步骤： 1、验证是否可以进行反核消： 1、界面传入的核销单数据和数据库数据变更过不能核销，界面重新刷新选择
		 * 2、选择的核销单为红单或无效单据,不能反核消，请重新选择 3、自动生成的核销单不能反核消
		 * 4、核销时间超过了系统配置的反核消限制时间的核销单不能反核消（若系统时间未设置，不做校验）
		 * 5、涉及到应付单的，如应收冲应付、预付冲应付、付款冲应付的核销单，检查其应付单信息 5.1、如果不是代收货款应付单类型的，可以反核消
		 * 5.2、如果是代收货款应付单类型的，要进一步检查其代收货款信息 5.1.1、代收货款为 资金部冻结 不可以反核销 5.1.2、代收货款为
		 * 退款中 不可以反核消 5.1.3、代收货款为 退款失败申请 不可以反核销 5.1.4、代收货款为 反汇款成功 不可以反核消
		 * 5.1.5、代收货款为 已退款 不可以反核销 6、选择的核销单明细存在于已确认的对账单中，不可以反核消
		 * 7、反核消时检查应收单对应的运单是否存在劳务费应付单 7.1 不存在，直接反核消 7.2 存在，判断劳务费应付单是否已支付 7.2.1
		 * 已支付，提示不允许反核消 7.2.1 未支付，时效劳务费应付单，进而反核消
		 * 
		 * 2、循环调用反核消接口进行反核消，并获取反核消成功后的明细结果
		 * 
		 * 3、如果反核消还款冲应收类型的核销单，进行还款单处理： 1、生成一个和原还款单金额相同、还款单号不同的还款单
		 * 
		 * 4、依次获取核销完成后的应收、应付、预收、预付单据
		 * 
		 * 5、反核消成功后处理对账单及其明细 1、针对对账单核销： 1、未确认：该情况不存在，按对账单核销必须先确认 2、已确认：
		 * 1、还款冲应收且对账单发生金额大于0，修改对账单的未还款金额信息 2、其他类型核销，不修改对账单及明细
		 * 2、非对账单核销：即通过预收冲应收、应收冲应付等界面核销，但却属于对账单明细中 1、未确认：修改对账单及其明细 2、已确认：不能执行反核消
		 * 
		 * 6、释放对账单明细： 1、如果该对账单明细不存在有效的核销单信息（按明细单号、对账单号共同查询），释放该对账单明细 2、释放方式：
		 * 1、删除对账单明细 2、将对账单明细的本体单据（应收、应付等）去掉对账单号 3、重新计算对账单本期的各种金额
		 * 
		 * 7、释放汇款： 1、如果为还款冲应收、预收冲应收类型，检查还款单、预收单的支付类型 如果支付类型为电汇，则调用费控进行释放汇款
		 */

		// 获取当前时间
		Date now = Calendar.getInstance().getTime();

		// 循环处理核销单
		for (BillWriteoffEntity entity : billWriteoffEntitylist) {
			// 1.2 红单或者无效核销单无法进行反核销
			if (FossConstants.INACTIVE.equals(entity.getActive())
					|| SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES
							.equals(entity.getIsRedBack())) {
				// 选择的核销单为红单或无效单据,请重新选择
				throw new SettlementException("选择的核销单为红单或无效单据,请重新选择!");
			}

			// 1.3 自动核销的无法前台人工反核销
			if (SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO
					.equals(entity.getCreateType())) {
				// 选择的核销单为红单或无效单据,请重新选择
				throw new SettlementException(
						"选择的核销单为后台自动生成的核销单,不能通过前台人工反核销,请重新选择!");
			}

			// 1.4 核销单明细的核销时间必须小于等于当前时间30天，天数从基础资料读取
			// 当前系统时间、反核消限制时间
			String sysLimitDateStr = configurationParamsService
					.queryConfValueByCode(ConfigurationParamsConstants.STL_WRITEOFF_REVERSE_WRITEOFF_DAY);
			// 如果系统配置时间存在，进行时间反核销限制
			if (StringUtils.isNotEmpty(sysLimitDateStr)) {
				// 系统限制时间=当前时间-系统限制天数
				Date sysLimitDate = DateUtils.addDays(now,
						-Integer.parseInt(sysLimitDateStr));
				// 如果核销单核销时间早于系统限制世间安
				if (entity.getWriteoffTime().before(sysLimitDate)) {
					// 选择的核销单:XXXX核销时间超过了反核消限制时间,请重新选择
					throw new SettlementException("选择的核销单:"
							+ entity.getWriteoffBillNo()
							+ "核销时间超过了反核消限制时间,请重新选择!");
				}
			}

			// 1.6针对非对账单核销，根据核销单的明细单号查询对账单明细数据
			if (StringUtils.isEmpty(entity.getStatementBillNo())
					|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
							.getStatementBillNo())) {
				List<String> tempSourceBillNoList = new ArrayList<String>();
				tempSourceBillNoList.add(entity.getBeginNo());
				tempSourceBillNoList.add(entity.getEndNo());
				// 根据明细单号查询对账单明细对应的应收、应付、预收、预付等单据
				Map<Object, Object> map = statementOfAccountDService
						.queryBySourceBillNos(tempSourceBillNoList);
				// 存放各种明细单据集合(应收)
				@SuppressWarnings("unchecked")
				List<BillReceivableEntity> receivableEntityList =  (List<BillReceivableEntity>) map.get("receivableEntityList");
				// 存放各种明细单据集合(应付)
				@SuppressWarnings("unchecked")
				
				List<BillPayableEntity> payableEntityList = (List<BillPayableEntity>) map.get("payableEntityList");
				// 存放各种明细单据集合(预收)
				@SuppressWarnings("unchecked")
				List<BillDepositReceivedEntity> depositReceivedEntityList = (List<BillDepositReceivedEntity>) map.get("depositReceivedEntityList");
				// 存放各种明细单据集合(预付)
				@SuppressWarnings("unchecked")
				List<BillAdvancedPaymentEntity> advancedPaymentEntityList = (List<BillAdvancedPaymentEntity>) map.get("advancedPaymentEntityList");
				// 循环对账单明细获取对账单号加入toCheckStatementNoList
				List<String> toCheckStatementNoList = new ArrayList<String>();
				// 如果应收单不为空，循环应收单将应收单号加入toCheckStatementNoList
				validaEntityList(receivableEntityList, toCheckStatementNoList);
				// 如果应付单不为空，循环应收单将应付单号加入toCheckStatementNoList
				if (!CollectionUtils.isEmpty(payableEntityList)) {
					for (BillPayableEntity dEntityTemp : payableEntityList) {
						if (StringUtils.isNotEmpty(dEntityTemp
								.getStatementBillNo())
								&& !SettlementConstants.DEFAULT_BILL_NO
										.equals(dEntityTemp
												.getStatementBillNo())) {
							toCheckStatementNoList.add(dEntityTemp
									.getStatementBillNo());
						}
					}
				}
				// 如果预收单不为空，循环预收单将应收单号加入toCheckStatementNoList
				validaDepositReceived(depositReceivedEntityList,
						advancedPaymentEntityList, toCheckStatementNoList);
				// 如果toCheckStatementNoList不为空
				if (!CollectionUtils.isEmpty(toCheckStatementNoList)) {
					List<String> confirmStatementNolist = statementOfAccountService
							.queryConfirmStatmentOfAccount(toCheckStatementNoList);
					// 如果返回的已确认对账单号不为空，该核销单不允许反核消
					if (!CollectionUtils.isEmpty(confirmStatementNolist)) {
						throw new SettlementException("选择的核销单:"
								+ entity.getWriteoffBillNo()
								+ "明细存在于已确认的对账单中不能反核消,请重新选择!");
					}
				}
			}

			// 1.5 验证核销单对应的应付单是否可以反核消
			// 如果核销单的核销类型为:应收冲应付、预付冲应付、付款冲应付的，查询应付单信息
			if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE
					.equals(entity.getWriteoffType())
					|| SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__ADVANCED_PAYABLE
							.equals(entity.getWriteoffType())
					|| SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYMENT_PAYABLE
							.equals(entity.getWriteoffType())) {

				// 查询应付单信息
				BillPayableEntity billPayableEntity = billPayableService
						.queryByPayableNO(entity.getEndNo(),
								FossConstants.ACTIVE);

				// 如果应付单类型为:应付代收货款 类型，判断代收货款是否冻结
				if (billPayableEntity != null) {
					if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
							.equals(billPayableEntity.getBillType())) {

						// 如果代收货款应付单已冻结或已付款，不允许反核消
						// 根据应付单的运单号获取代收货款信息
						// 如果核销单的目的来源运单号不为空
						if (StringUtils.isNotEmpty(entity.getEndWaybillNo())) {
							// 根据核销单目的来源运单号查询代收货款信息
							CODEntity codEntity = codCommonService
									.queryByWaybill(entity.getEndWaybillNo());
							// 如果代收货款不为空
							if (codEntity != null) {
								// 判断代收货款是否支持核销：
								// 1、代收货款为 资金部冻结 不可以反核销
								// 2、代收货款为 退款中 不可以反核消
								// 3、代收货款为 退款失败申请 不可以反核销
								// 4、代收货款为 反汇款成功 不可以反核消
								// 5、代收货款为 已退款 不可以反核销

								// 1、代收货款为资金部冻结中不可以反核销
								if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
										.equals(codEntity.getStatus())) {
									// 提示：选择的核销单:XXXX相关代收货款为资金部冻结中,不可反核消,请重新选择
									throw new SettlementException(
											"选择的核销单:"
													+ entity.getWriteoffBillNo()
													+ "相关代收货款为资金部冻结中,不可反核消,请重新选择! 或联系资金部取消退款或者等代收货款退款完成后再反核销。");
								}

								// 2、 代收货款为退款中不可以反核销
								if (SettlementDictionaryConstants.COD__STATUS__RETURNING
										.equals(codEntity.getStatus())) {
									// 提示：选择的核销单:XXXX相关代收货款为退款中,不可反核销,请重新选择
									throw new SettlementException(
											"选择的核销单:"
													+ entity.getWriteoffBillNo()
													+ "相关代收货款为退款中,不可反核销,请重新选择! 或联系资金部取消退款或者等代收货款退款完成后再反核销。");
								}

								// 3、代收货款为退款失败申请中不可以反核销
								if (SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION
										.equals(codEntity.getStatus())) {
									// 提示：选择的核销单:XXXX相关代收货款为退款失败申请中,不可反核销,请重新选择
									throw new SettlementException(
											"选择的核销单:"
													+ entity.getWriteoffBillNo()
													+ "相关代收货款为退款失败申请中,不可反核销,请重新选择! 或联系资金部取消退款或者等代收货款退款完成后再反核销。");
								}

								// 4、代收货款为反汇款成功不可以反核销
								if (SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS
										.equals(codEntity.getStatus())) {
									// 提示：选择的核销单:XXXX相关代收货款为反汇款成功,不可反核销,请重新选择
									throw new SettlementException(
											"选择的核销单:"
													+ entity.getWriteoffBillNo()
													+ "相关代收货款为反汇款成功,不可反核销,请重新选择! 或联系资金部取消退款或者等代收货款退款完成后再反核销。");
								}

								/*
								 * ISSUE-3426 代收货款部分冲应收部分退款的情况，冲应收部分允许反核销
								 * 去掉已退款后不能反核销的限制
								 * 
								 * 杨书硕 2013-7-26 上午10:02:04
								 */
								// 5、代收货款为已退款不可以反核消
								// if
								// (SettlementDictionaryConstants.COD__STATUS__RETURNED.equals(codEntity.getStatus()))
								// {
								// 提示：选择的核销单:XXXX相关代收货款已退款,不可反核消,请重新选择
								// throw new SettlementException("选择的核销单:"+
								// entity.getWriteoffBillNo()+
								// "相关代收货款为已退款,不可反核消,请重新选择!");
								// }
							}
						}
					}
				}
			}

			/*
			 * 7、反核消时检查应收单对应的运单是否存在劳务费应付单 7.1 不存在，直接反核消 7.2 存在，判断劳务费应付单是否已支付
			 * 7.2.1 已支付，提示不允许反核消 7.2.1 未支付，失效劳务费应付单，进而反核消
			 */
			// 如果核销单开始或结束单号为应收单,获取其对应的运单号
			BillPayableConditionDto billPayableConditionDto = new BillPayableConditionDto();
			if (SettlementUtil.isReceiveable(entity.getBeginNo())) {
				billPayableConditionDto
						.setWaybillNo(entity.getBeginWaybillNo());
			} else if (SettlementUtil.isReceiveable(entity.getEndNo())) {
				billPayableConditionDto.setWaybillNo(entity.getEndWaybillNo());
			}
			// 如果运单号存在
			if (StringUtils.isNotEmpty(billPayableConditionDto.getWaybillNo())) {
				// 根据运单号，查询该运单对应的应付单
				List<BillPayableEntity> payableList = billPayableService
						.queryBillPayableByCondition(billPayableConditionDto);
				// 如果应付单存在
				if (!CollectionUtils.isEmpty(payableList)) {
					// 循环应付单列表
					for (BillPayableEntity pay : payableList) {
						// 如果应付单为装卸费费类型，且为已支付状态，不允许反核消
						if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE
								.equals(pay.getBillType())
								&& SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
										.equals(pay.getPayStatus())) {
							// 提示：选择的核销单:XXX其相关运单XX的装卸费应付单已支付,不可反核消,请重新选择
							throw new SettlementException("选择的核销单:"
									+ entity.getWriteoffBillNo() + "其相关运单:"
									+ billPayableConditionDto.getWaybillNo()
									+ "的装卸费应付单已支付,不可反核消,请重新选择!");
						}
						// 如果应付单为装卸费类型，且为未支付状态，将装卸费应付单失效
						if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE
								.equals(pay.getBillType())
								&& SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO
										.equals(pay.getPayStatus())) {
							// 失效装卸费应付单
							billPayableService.disableBillPayable(pay, cInfo);
						}
					}
				}
			}
		}

		// 生成调用接口的Dto参数及其应收、应付、预收、预付对象的list
		List<BillReceivableEntity> receivableEntityList = null;// 应收单
		List<BillPayableEntity> payableEntityList = null;// 应付单
		List<BillDepositReceivedEntity> depositReceivedEntityList = null;// 预收单
		List<BillAdvancedPaymentEntity> advancePaymentEntityList = null;// 预付单

		// 初始化待释放汇款的还款单列表
		List<BillRepaymentEntity> oldEntityList = new ArrayList<BillRepaymentEntity>();

		// 循环调用
		for (BillWriteoffEntity entity : billWriteoffEntitylist) {
			
			// 2、反核消接口进行反核消，并获取反核消成功后的明细结果
			BillWritebackOperationDto writebackDto = billWriteoffService
					.writeBackBillWriteoffByEntity(entity, cInfo);
			// 记录日志
			LOGGER.info("根据核销单号,反核销核销单，核销单号:" + entity.getWriteoffBillNo());

			// 3、如果反核消还款冲应收类型的核销单，进行还款单处理
			// 如果是还款冲应收，需要调用还款单服务，生成新的还款单，金额为核销单的负值
			if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
					.equals(entity.getWriteoffType())) {
				// 生成新的还款单
				BillRepaymentEntity newEntity = new BillRepaymentEntity();
				// 根据还款单号查询还款单
				BillRepaymentEntity oldEntity = billRepaymentService
						.queryByRepaymentNO(entity.getBeginNo(),
								FossConstants.ACTIVE);
				// 加入待释放汇款的还款单
				oldEntityList.add(oldEntity);
				
				// 根据原还款单生成新的还款单，并设置相关属性
				BeanUtils.copyProperties(oldEntity, newEntity);// 拷贝老还款单数据到新还款单
				newEntity.setId(UUIDUtils.getUUID());// 新ID
				newEntity.setVersionNo(FossConstants.INIT_VERSION_NO);// 初始版本号
				newEntity.setAccountDate(now);// 当前日期
				newEntity
						.setAmount(NumberUtils.multiply(entity.getAmount(), -1));// 还款单金额
				newEntity.setTrueAmount(NumberUtils.multiply(
						entity.getAmount(), -1));// 实际还款金额
				newEntity.setBverifyAmount(BigDecimal.ZERO);// 反核消金额
				// String repaymentNo =
				// settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1);//
				// 获取还款单号
				// newEntity.setRepaymentNo(repaymentNo);// 新还款单号
				newEntity.setRepaymentNo(oldEntity.getRepaymentNo());// 2013-04-24，修改为使用相同还款单号
				newEntity
						.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);// 状态默认为提交
				// 2013-05-27 反核消生成还款单设置为无效、红单
				newEntity.setActive(FossConstants.INACTIVE);
				newEntity
						.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
				billRepaymentService.addBillRepayment(newEntity, cInfo);// 保存新还款单

				// 如果还款金额金额等于反核消金额，将该还款单设置为无效
				if (oldEntity.getAmount().compareTo(
						oldEntity.getBverifyAmount()) == 0) {
					// 更新还款单的效状态
					billRepaymentService.revereWriteoffBillRepayment(oldEntity,
							cInfo);
				}
			}

			// 4、依次获取核销完成后的应收、应付、预收、预付单据
			// 如果反核销后应收单实体存在，加入到应收单list集合参数中
			if (writebackDto != null
					&& writebackDto.getBillReceivableEntity() != null) {
				// 如果应收单不为空
				if (CollectionUtils.isEmpty(receivableEntityList)) {
					// 新建应收单列表
					receivableEntityList = new ArrayList<BillReceivableEntity>();
				}
				// 获取核销返回应收单
				receivableEntityList
						.add(writebackDto.getBillReceivableEntity());
			}

			// 如果反核销后应付单实体存在，加入到应付单list集合参数中
			if (writebackDto != null
					&& writebackDto.getBillPayableEntity() != null) {
				// 如果应付单不为空
				if (CollectionUtils.isEmpty(payableEntityList)) {
					// 新建应付单列表
					payableEntityList = new ArrayList<BillPayableEntity>();
				}
				// 获取核销返回应付单
				payableEntityList.add(writebackDto.getBillPayableEntity());
			}

			// 如果反核销后预收单实体存在，加入到预收单list集合参数中
			if (writebackDto != null
					&& writebackDto.getBillDepositReceivedEntity() != null) {
				// 如果预收单不为空
				if (CollectionUtils.isEmpty(depositReceivedEntityList)) {
					// 新建预收单列表
					depositReceivedEntityList = new ArrayList<BillDepositReceivedEntity>();
				}
				// 获取核销返回预收单
				depositReceivedEntityList.add(writebackDto
						.getBillDepositReceivedEntity());
			}

			// 如果反核销后预付单实体存在，加入到预付单list集合参数中
			if (writebackDto != null
					&& writebackDto.getBillAdvancedPaymentEntity() != null) {
				// 如果预付单不为空
				if (CollectionUtils.isEmpty(advancePaymentEntityList)) {
					// 新建预付单列表
					advancePaymentEntityList = new ArrayList<BillAdvancedPaymentEntity>();
				}
				// 获取核销返预付单
				advancePaymentEntityList.add(writebackDto
						.getBillAdvancedPaymentEntity());
			}
		}

		/*
		 * 5、反核消成功后处理对账单及其明细 1、针对对账单核销： 1、未确认：该情况不存在，按对账单核销必须先确认 2、已确认：
		 * 1、还款冲应收且对账单发生金额大于0，修改对账单的未还款金额信息(如果采用释放的话，不走该步骤) 2、其他类型核销，不修改对账单及明细
		 * 2、非对账单核销：即通过预收冲应收、应收冲应付等界面核销，但却属于对账单明细中 1、未确认：修改对账单及其明细 2、已确认：不能执行反核消
		 */
		for (BillWriteoffEntity entity : billWriteoffEntitylist) {
			/**
			 * @author 269052 zhouyuan008@deppon.com
			 */
			// 根据还款单号查询还款单,查询无效的数据
			BillRepaymentEntity oldEntity = billRepaymentService
					.queryByRepaymentNO(entity.getBeginNo(),
							FossConstants.INACTIVE); 
			// 获取还款方式
			String paymentType  = null ;
			if(oldEntity != null){ 
				 paymentType  = oldEntity.getPaymentType();
			}
			
			// 存放各种待释放的明细单据集合(应收、应付、预收、预付)
			List<BillPayableEntity> toReleasePayableEntityList = new ArrayList<BillPayableEntity>();
			List<BillDepositReceivedEntity> toReleaseDepositReceivedEntityList = new ArrayList<BillDepositReceivedEntity>();
			List<BillAdvancedPaymentEntity> toReleaseAdvancedPaymentEntityList = new ArrayList<BillAdvancedPaymentEntity>();
			List<BillReceivableEntity> toReleaseReceivableEntityList = new ArrayList<BillReceivableEntity>();

			// 5.1、针对对账单核销：
			if (StringUtils.isNotEmpty(entity.getStatementBillNo())
					&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity
							.getStatementBillNo())) {
				// 根据对账单号查询对账单
				StatementOfAccountEntity stateEntity = statementOfAccountService
						.queryByStatementNo(entity.getStatementBillNo());

				/*
				 * 6、释放对账单明细： 1、如果该对账单明细不存在有效的核销单信息（按明细单号、对账单号共同查询），释放该对账单明细
				 * 2、释放方式： 1、删除对账单明细 2、将对账单明细的本体单据（应收、应付等）去掉对账单号
				 * 3、重新计算对账单本期的各种金额
				 */
				// 针对对账单核销，释放明细
				if (StringUtils.isNotEmpty(entity.getStatementBillNo())
						&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity
								.getStatementBillNo())) {

					// 1、如果是还款冲应收类型核销单,只需要根据对账单号和目的单号检查是否存在有效的核销单
					if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
							.equals(entity.getWriteoffType())) {

						// 根据对账单号和目的单号查询核销单（目的单号要作为开始、结束单号两种查询）
						List<BillWriteoffEntity> beginList = billWriteoffService
								.queryByStatementBillNo(
										entity.getStatementBillNo(),
										entity.getEndNo(), null, null,
										FossConstants.ACTIVE);
						List<BillWriteoffEntity> endList = billWriteoffService
								.queryByStatementBillNo(
										entity.getStatementBillNo(), null,
										entity.getEndNo(), null,
										FossConstants.ACTIVE);
						
						// 如果list为空，释放对账单上的该明细信息
						if (CollectionUtils.isEmpty(beginList)
								&& CollectionUtils.isEmpty(endList)) {
							// 根据开始单号、对账单号查询有效的明细单据，该明细单据为应收单
							StatementOfAccountDEntity dEntity = statementOfAccountDService
									.queryByResourceAndStatementNo(
											entity.getEndNo(),
											entity.getStatementBillNo(),
											FossConstants.NO);
							// 如果对账单明细不为空
							if (dEntity != null) {
								// 删除对账单明细，逻辑删除，修改删除标志为删除
								dEntity.setIsDelete(FossConstants.YES);
								statementOfAccountDService
										.updateStatementDetailForDeleteDetail(dEntity);
								// 重新计算对账单本期金额信息
								// 修改对账单的本期应收发生金额
								stateEntity.setPeriodRecAmount(stateEntity
										.getPeriodRecAmount().subtract(
												dEntity.getUnverifyAmount()));
								// 修改对账单的本期发生金额
								stateEntity.setPeriodAmount(stateEntity
										.getPeriodAmount().subtract(
												dEntity.getUnverifyAmount()));
								// 修改本期未还金额

								// 本期应收+本期预付
								BigDecimal amountTemp = stateEntity
										.getPeriodRecAmount().add(
												stateEntity
														.getPeriodAdvAmount());
								// -本期应付
								amountTemp = amountTemp.subtract(stateEntity
										.getPeriodPayAmount());
								// -本期预收
								amountTemp = amountTemp.subtract(stateEntity
										.getPeriodPreAmount());

								// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
								BigDecimal writeoffedAmount = BigDecimal.ZERO;
								List<BillWriteoffEntity> listTemp = billWriteoffService
										.queryByStatementBillNo(
												entity.getStatementBillNo(),
												null,
												null,
												SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
												FossConstants.ACTIVE);
								validaBigDecimal(stateEntity, amountTemp,
										writeoffedAmount, listTemp);

								// 根据对账单的本期发生金额更新对账单的本期剩余发生金额信息
								stateEntity = this
										.updatePeriodUnverifyAmount(stateEntity);
								// 修改对账单信息
								statementOfAccountService
										.updateStatementForAddDetail(
												stateEntity, cInfo);
								
								if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)){
										/**
										 * NCI项目 修改对账单总金额
										 * @author 269052 zhouyuan008@deppon.com
										 * @date 2016-04-15
										 */
										// 判断单号是否来自于NCI项目
										PosCardDetailEntity detail = new PosCardDetailEntity();
										detail.setInvoiceNo(entity.getStatementBillNo());
										
										/**
										 * 设置修改人信息
										 */
										detail.setModifyUser(cInfo.getEmpName());
										detail.setModifyUserCode(cInfo.getEmpCode());
										/**
										 * 反核销的时候，释放应收单，对账单的总金额改变，
										 * 则需要释放T+0报表明细的已使用流水号金额
										 */
										List<PosCardDetailEntity> list = pdaPosManageService.queryPosDetailsByNo(detail);
										if(CollectionUtils.isNotEmpty(list)){
											/**
											 * 获取反核销了多少钱
											 */
											/*BigDecimal bdAmount = entity.getAmount();*/
											
											//本期发生额
											detail.setAmount(stateEntity.getPeriodAmount());
											
											/**
											 * 反核销的钱，就是已使用流水号金额
											 */
											detail.setOccupateAmount(entity.getAmount());
											
											/**
											 * 假如对账单的本期发生额大于0，则要依次释放明细的已使用流水号金额，
											 * 假如等于0,则直接修改流水号金额，删除明细
											 */
											if(stateEntity.getPeriodAmount().compareTo(BigDecimal.ZERO)> 0){
												detail.setIsflag("true");
											}
											// 修改对账单总金额
											pdaPosManageService.updateStatementAmout(detail); 
										}
								}
							}
			 				// 如果应收单不需要释放，修改对账单的本期未还款金额
						} else {
							validaCurrentInfo(cInfo, entity, paymentType,
									stateEntity);
						}

						// 根据应收单号查询应收单
						BillReceivableEntity billReceivableEntity = billReceivableService
								.queryByReceivableNO(entity.getEndNo(),
										FossConstants.ACTIVE);
						// 如果应收单不为空，且应收单的对账单号等于对账单应收明细的对账单号
						if (billReceivableEntity != null) {

							// 将应收单加入到待释放应收单明细集合
							boolean flagIsExist = false;
							if (!CollectionUtils
									.isEmpty(toReleaseReceivableEntityList)) {
								for (BillReceivableEntity temp : toReleaseReceivableEntityList) {
									if (temp.getReceivableNo().equals(
											billReceivableEntity
													.getReceivableNo())) {
										flagIsExist = true;
									}
								}
							}
							if (!flagIsExist) {
								toReleaseReceivableEntityList
										.add(billReceivableEntity);
							}

						}

						// 2、如果其他类型核销单，需要分别以开始单号、目的单号和对账单号组合检查是否存在有效的核销单
					} else {
						/**
						 * 其他冲应收的:预收冲应收
						 */
						// 根据对账单和开始单号查询核销单（开始单号要作为开始、结束单号两种查询）
						List<BillWriteoffEntity> beginListB = billWriteoffService
								.queryByStatementBillNo(
										entity.getStatementBillNo(),
										entity.getBeginNo(), null, null,
										FossConstants.ACTIVE);
						List<BillWriteoffEntity> endListB = billWriteoffService
								.queryByStatementBillNo(
										entity.getStatementBillNo(), null,
										entity.getBeginNo(), null,
										FossConstants.ACTIVE);

						// 如果list为空，释放对账单上的该明细信息
						if (CollectionUtils.isEmpty(beginListB)
								&& CollectionUtils.isEmpty(endListB)) {
							// 根据开始单号、对账单号查询有效的明细单据
							StatementOfAccountDEntity dEntity = statementOfAccountDService
									.queryByResourceAndStatementNo(
											entity.getBeginNo(),
											entity.getStatementBillNo(),
											FossConstants.NO);
							// 如果对账单明细不为空
							if (dEntity != null) {
								// 删除对账单明细，逻辑删除，修改删除标志为删除
								dEntity.setIsDelete(FossConstants.YES);
								statementOfAccountDService
										.updateStatementDetailForDeleteDetail(dEntity);

								// 应收单
								if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE
										.equals(dEntity.getBillParentType())) {

									// 重新计算对账单本期金额信息
									// 修改对账单的本期应收发生金额
									stateEntity
											.setPeriodRecAmount(stateEntity
													.getPeriodRecAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单的本期发生金额
									stateEntity
											.setPeriodAmount(stateEntity
													.getPeriodAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改本期未还金额
									// 本期应收+本期预付
									BigDecimal amountTemp = stateEntity
											.getPeriodRecAmount()
											.add(stateEntity
													.getPeriodAdvAmount());
									// -本期应付
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPayAmount());
									// -本期预收
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPreAmount());

									// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
									BigDecimal writeoffedAmount = BigDecimal.ZERO;
									List<BillWriteoffEntity> listTemp = billWriteoffService
											.queryByStatementBillNo(
													entity.getStatementBillNo(),
													null,
													null,
													SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
													FossConstants.ACTIVE);
									validaBigDecimal(stateEntity, amountTemp,
											writeoffedAmount, listTemp);
									
									// 应付单
								} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE
										.equals(dEntity.getBillParentType())) {

									// 重新计算对账单本期金额信息
									// 修改对账单的本期应付发生金额
									stateEntity
											.setPeriodPayAmount(stateEntity
													.getPeriodPayAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单的本期发生金额
									stateEntity.setPeriodAmount(stateEntity
											.getPeriodAmount()
											.add(dEntity.getUnverifyAmount()));
									// 修改对账单本期未还金额
									// 本期应收+本期预付
									BigDecimal amountTemp = stateEntity
											.getPeriodRecAmount()
											.add(stateEntity
													.getPeriodAdvAmount());
									// -本期应付
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPayAmount());
									// -本期预收
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPreAmount());

									// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
									BigDecimal writeoffedAmount = BigDecimal.ZERO;
									List<BillWriteoffEntity> listTemp = billWriteoffService
											.queryByStatementBillNo(
													entity.getStatementBillNo(),
													null,
													null,
													SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
													FossConstants.ACTIVE);
									validaBigDecimal(stateEntity, amountTemp,
											writeoffedAmount, listTemp);
 
									// 预收单
								} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED
										.equals(dEntity.getBillParentType())) {

									// 重新计算对账单本期金额信息
									// 修改对账单的本期预收金额
									stateEntity
											.setPeriodPreAmount(stateEntity
													.getPeriodPreAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单的本期发生金额
									stateEntity.setPeriodAmount(stateEntity
											.getPeriodAmount()
											.add(dEntity.getUnverifyAmount()));
									// 修改对账单本期未还金额
									// 本期应收+本期预付
									BigDecimal amountTemp = stateEntity
											.getPeriodRecAmount()
											.add(stateEntity
													.getPeriodAdvAmount());
									// -本期应付
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPayAmount());
									// -本期预收
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPreAmount());

									// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
									BigDecimal writeoffedAmount = BigDecimal.ZERO;
									List<BillWriteoffEntity> listTemp = billWriteoffService
											.queryByStatementBillNo(
													entity.getStatementBillNo(),
													null,
													null,
													SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
													FossConstants.ACTIVE);
									validaBigDecimal(stateEntity, amountTemp,
											writeoffedAmount, listTemp);
									
									// 预付单
								} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT
										.equals(dEntity.getBillParentType())) {

									// 重新计算对账单本期金额信息
									// 修改对账单的本期应付发生金额
									stateEntity
											.setPeriodAdvAmount(stateEntity
													.getPeriodAdvAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单的本期发生金额
									stateEntity
											.setPeriodAmount(stateEntity
													.getPeriodAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单本期未还金额
									// 修改本期未还金额
									// 本期应收+本期预付
									BigDecimal amountTemp = stateEntity
											.getPeriodRecAmount()
											.add(stateEntity
													.getPeriodAdvAmount());
									// -本期应付
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPayAmount());
									// -本期预收
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPreAmount());

									// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
									BigDecimal writeoffedAmount = BigDecimal.ZERO;
									List<BillWriteoffEntity> listTemp = billWriteoffService
											.queryByStatementBillNo(
													entity.getStatementBillNo(),
													null,
													null,
													SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
													FossConstants.ACTIVE);
									validaBigDecimal(stateEntity, amountTemp,
											writeoffedAmount, listTemp);
									
								}
							}
						}

						// 根据应收单号查询应收单
						validaBillWriteoffEntity(entity,
								toReleaseReceivableEntityList);
						// 根据应付单号查询应付单
						if (SettlementUtil.isPayable(entity.getBeginNo())) {
							BillPayableEntity billPayableEntity = billPayableService
									.queryByPayableNO(entity.getBeginNo(),
											FossConstants.ACTIVE);
							// 如果应付单不为空，且 应付单的对账单号等于对账单应付明细的对账单号
							if (billPayableEntity != null) {
								// 将应付单加入到待释放应付单明细集合
								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleasePayableEntityList)) {
									for (BillPayableEntity temp : toReleasePayableEntityList) {
										if (temp.getPayableNo().equals(
												billPayableEntity
														.getPayableNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleasePayableEntityList
											.add(billPayableEntity);
								}

							}
						}
						// 根据预收单号查询
						if (SettlementUtil.isDepositReceived(entity
								.getBeginNo())) {
							BillDepositReceivedEntity billDepositReceivedEntity = billDepositReceivedService
									.queryByDepositReceivedNo(
											entity.getBeginNo(),
											FossConstants.ACTIVE);
							// 如果预收单不为空，且 预收单的对账单号等于对账单预收明细的对账单号
							if (billDepositReceivedEntity != null) {
								// 将预收单加入到待释放预收单明细集合

								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleaseDepositReceivedEntityList)) {
									for (BillDepositReceivedEntity temp : toReleaseDepositReceivedEntityList) {
										if (temp.getDepositReceivedNo()
												.equals(billDepositReceivedEntity
														.getDepositReceivedNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleaseDepositReceivedEntityList
											.add(billDepositReceivedEntity);
								}

							}
						}
						// 根据预付单号查询
						if (SettlementUtil.isAdvancedPayment(entity
								.getBeginNo())) {
							BillAdvancedPaymentEntity billAdvancedPaymentEntity = billAdvancedPaymentService
									.queryBillAdvancedPaymentNo(
											entity.getBeginNo(),
											FossConstants.ACTIVE);
							validaPaymentEntity(
									toReleaseAdvancedPaymentEntityList,
									billAdvancedPaymentEntity);
						}

						// 根据对账单和目的单号查询核销单（目的单号要作为开始、结束单号两种查询）
						List<BillWriteoffEntity> beginListE = billWriteoffService
								.queryByStatementBillNo(
										entity.getStatementBillNo(),
										entity.getEndNo(), null, null,
										FossConstants.ACTIVE);
						// 根据对账单和目的单号查询核销单
						List<BillWriteoffEntity> endListE = billWriteoffService
								.queryByStatementBillNo(
										entity.getStatementBillNo(), null,
										entity.getEndNo(), null,
										FossConstants.ACTIVE);
						// 如果list为空，释放对账单上的该明细信息
						if (CollectionUtils.isEmpty(beginListE)
								&& CollectionUtils.isEmpty(endListE)) {
							// 根据开始单号、对账单号查询有效的明细单据
							StatementOfAccountDEntity dEntity = statementOfAccountDService
									.queryByResourceAndStatementNo(
											entity.getEndNo(),
											entity.getStatementBillNo(),
											FossConstants.NO);
							// 如果对账单明细不为空
							if (dEntity != null) {
								// 删除对账单明细，逻辑删除，修改删除标志为删除
								dEntity.setIsDelete(FossConstants.YES);
								statementOfAccountDService
										.updateStatementDetailForDeleteDetail(dEntity);

								// 应收单
								if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE
										.equals(dEntity.getBillParentType())) {

									// 重新计算对账单本期金额信息
									// 修改对账单的本期应收发生金额
									stateEntity
											.setPeriodRecAmount(stateEntity
													.getPeriodRecAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单的本期发生金额
									stateEntity
											.setPeriodAmount(stateEntity
													.getPeriodAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改本期未还金额
									// 本期应收+本期预付
									BigDecimal amountTemp = stateEntity
											.getPeriodRecAmount()
											.add(stateEntity
													.getPeriodAdvAmount());
									// -本期应付
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPayAmount());
									// -本期预收
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPreAmount());

									// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
									BigDecimal writeoffedAmount = BigDecimal.ZERO;
									List<BillWriteoffEntity> listTemp = billWriteoffService
											.queryByStatementBillNo(
													entity.getStatementBillNo(),
													null,
													null,
													SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
													FossConstants.ACTIVE);
									validaBigDecimal(stateEntity, amountTemp,
											writeoffedAmount, listTemp);

									// 应付单
								} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE
										.equals(dEntity.getBillParentType())) {

									// 重新计算对账单本期金额信息
									// 修改对账单的本期应付发生金额
									stateEntity
											.setPeriodPayAmount(stateEntity
													.getPeriodPayAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单的本期发生金额
									stateEntity.setPeriodAmount(stateEntity
											.getPeriodAmount()
											.add(dEntity.getUnverifyAmount()));
									// 修改对账单本期未还金额
									// 本期应收+本期预付
									BigDecimal amountTemp = stateEntity
											.getPeriodRecAmount()
											.add(stateEntity
													.getPeriodAdvAmount());
									// -本期应付
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPayAmount());
									// -本期预收
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPreAmount());

									// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
									BigDecimal writeoffedAmount = BigDecimal.ZERO;
									List<BillWriteoffEntity> listTemp = billWriteoffService
											.queryByStatementBillNo(
													entity.getStatementBillNo(),
													null,
													null,
													SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
													FossConstants.ACTIVE);
									validaBigDecimal(stateEntity, amountTemp,
											writeoffedAmount, listTemp);

									// 预收单
								} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED
										.equals(dEntity.getBillParentType())) {

									// 重新计算对账单本期金额信息
									// 修改对账单的本期应付发生金额
									stateEntity
											.setPeriodPreAmount(stateEntity
													.getPeriodPreAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单的本期发生金额
									stateEntity.setPeriodAmount(stateEntity
											.getPeriodAmount()
											.add(dEntity.getUnverifyAmount()));
									// 修改对账单本期未还金额
									// 本期应收+本期预付
									BigDecimal amountTemp = stateEntity
											.getPeriodRecAmount()
											.add(stateEntity
													.getPeriodAdvAmount());
									// -本期应付
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPayAmount());
									// -本期预收
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPreAmount());

									// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
									BigDecimal writeoffedAmount = BigDecimal.ZERO;
									List<BillWriteoffEntity> listTemp = billWriteoffService
											.queryByStatementBillNo(
													entity.getStatementBillNo(),
													null,
													null,
													SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
													FossConstants.ACTIVE);
									validaBigDecimal(stateEntity, amountTemp,
											writeoffedAmount, listTemp);

									// 预付单
								} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT
										.equals(dEntity.getBillParentType())) {

									// 重新计算对账单本期金额信息
									// 修改对账单的本期应付发生金额
									stateEntity
											.setPeriodAdvAmount(stateEntity
													.getPeriodAdvAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单的本期发生金额
									stateEntity
											.setPeriodAmount(stateEntity
													.getPeriodAmount()
													.subtract(
															dEntity.getUnverifyAmount()));
									// 修改对账单本期未还金额
									// 修改本期未还金额
									// 本期应收+本期预付
									BigDecimal amountTemp = stateEntity
											.getPeriodRecAmount()
											.add(stateEntity
													.getPeriodAdvAmount());
									// -本期应付
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPayAmount());
									// -本期预收
									amountTemp = amountTemp
											.subtract(stateEntity
													.getPeriodPreAmount());

									// 根据对账单号和还款单号查询所有的有效核销单，并将核销单的核销金额相加
									BigDecimal writeoffedAmount = BigDecimal.ZERO;
									List<BillWriteoffEntity> listTemp = billWriteoffService
											.queryByStatementBillNo(
													entity.getStatementBillNo(),
													null,
													null,
													SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
													FossConstants.ACTIVE);
									validaBigDecimal(stateEntity, amountTemp,
											writeoffedAmount, listTemp);
								}
							}
						}

						// 根据应收单号查询应收单
						if (SettlementUtil.isReceiveable(entity.getEndNo())) {
							BillReceivableEntity billReceivableEntity = billReceivableService
									.queryByReceivableNO(entity.getEndNo(),
											FossConstants.ACTIVE);
							// 如果应收单不为空，且应收单的对账单号等于对账单应收明细的对账单号
							if (billReceivableEntity != null) {
								// 将应收单加入到待释放应收单明细集合

								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleaseReceivableEntityList)) {
									for (BillReceivableEntity temp : toReleaseReceivableEntityList) {
										if (temp.getReceivableNo().equals(
												billReceivableEntity
														.getReceivableNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleaseReceivableEntityList
											.add(billReceivableEntity);
								}

							}
						}
						// 根据应付单号查询应付单
						if (SettlementUtil.isPayable(entity.getEndNo())) {
							BillPayableEntity billPayableEntity = billPayableService
									.queryByPayableNO(entity.getEndNo(),
											FossConstants.ACTIVE);
							// 如果应付单不为空，且 应付单的对账单号等于对账单应付明细的对账单号
							if (billPayableEntity != null) {
								// 将应付单加入到待释放应付单明细集合
								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleasePayableEntityList)) {
									for (BillPayableEntity temp : toReleasePayableEntityList) {
										if (temp.getPayableNo().equals(
												billPayableEntity
														.getPayableNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleasePayableEntityList
											.add(billPayableEntity);
								}

							}
						}
						// 根据预收单号查询
						if (SettlementUtil.isDepositReceived(entity.getEndNo())) {
							BillDepositReceivedEntity billDepositReceivedEntity = billDepositReceivedService
									.queryByDepositReceivedNo(
											entity.getEndNo(),
											FossConstants.ACTIVE);
							// 如果预收单不为空，且 预收单的对账单号等于对账单预收明细的对账单号
							if (billDepositReceivedEntity != null) {
								// 将预收单加入到待释放预收单明细集合

								boolean flagIsExist = false;
								if (!CollectionUtils
										.isEmpty(toReleaseDepositReceivedEntityList)) {
									for (BillDepositReceivedEntity temp : toReleaseDepositReceivedEntityList) {
										if (temp.getDepositReceivedNo()
												.equals(billDepositReceivedEntity
														.getDepositReceivedNo())) {
											flagIsExist = true;
										}
									}
								}
								if (!flagIsExist) {
									toReleaseDepositReceivedEntityList
											.add(billDepositReceivedEntity);
								}

							}
						}
						// 根据预付单号查询
						if (SettlementUtil.isAdvancedPayment(entity.getEndNo())) {
							BillAdvancedPaymentEntity billAdvancedPaymentEntity = billAdvancedPaymentService
									.queryBillAdvancedPaymentNo(
											entity.getEndNo(),
											FossConstants.ACTIVE);
							// 如果预付单不为空，且 预付单的对账单号等于对账单预付明细的对账单号
							validaPaymentEntity(
									toReleaseAdvancedPaymentEntityList,
									billAdvancedPaymentEntity);
						}

						// 根据对账单的本期发生金额更新对账单的本期剩余发生金额信息
						stateEntity = this
								.updatePeriodUnverifyAmount(stateEntity);

						// 修改对账单信息
						statementOfAccountService.updateStatementForAddDetail(
								stateEntity, cInfo);
						
						if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)){
								/**
								 * NCI项目 修改对账单总金额
								 * 释放预收单，预付单，应付单，对账单总金额增加，
								 * @author 269052 zhouyuan008@deppon.com
								 * @date 2016-04-15
								 */
								PosCardDetailEntity detail = new PosCardDetailEntity();
								detail.setInvoiceNo(entity.getStatementBillNo());
								/**
								 * 设置修改人信息
								 */
								detail.setModifyUser(cInfo.getEmpName());
								detail.setModifyUserCode(cInfo.getEmpCode());
								// 判断单号是否来自于NCI项目
								List<PosCardDetailEntity> list = pdaPosManageService.queryPosDetailsByNo(detail);
								if(CollectionUtils.isNotEmpty(list)){
									// 本期发生额
									detail.setAmount(stateEntity.getPeriodAmount());
									// 修改对账单总金额
									pdaPosManageService.updateStatementAmout(detail); 
								}
						}
					}
				}

				// 修改对账单明细的本体单据的对账单号为默认单号
				// 如果待释放的应收单列表不为空
				if (!CollectionUtils.isEmpty(toReleaseReceivableEntityList)) {
					// 生成批量修改应收单对账单号dto
					BillReceivableDto dto = new BillReceivableDto();
					// 设置默认对账单号
					dto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
					// 设置待释放的应收单列表
					dto.setBillReceivables(toReleaseReceivableEntityList);
					// 批量修改
					billReceivableService
							.batchUpdateByMakeStatement(dto, cInfo);
				}

				// 如果待释放的应付单列表不为空
				if (!CollectionUtils.isEmpty(toReleasePayableEntityList)) {
					// 生成批量修改应收付单对账单号dto
					BillPayableDto dto = new BillPayableDto();
					// 设置默认对账单号
					dto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
					// 设置待释放的应付单列表
					dto.setBillPayables(toReleasePayableEntityList);
					// 批量修改
					billPayableService.batchUpdateByMakeStatement(dto, cInfo);
				}

				// 如果待释放的预收单列表不为空
				if (!CollectionUtils
						.isEmpty(toReleaseDepositReceivedEntityList)) {
					// 生成批量修改预收单对账单号dto
					BillDepositReceivedEntityDto dto = new BillDepositReceivedEntityDto();
					// 设置默认对账单号
					dto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
					// 设置待释放的预收单列表
					dto.setBillDepositReceivedEntityList(toReleaseDepositReceivedEntityList);
					// 批量修改
					billDepositReceivedService
							.batchUpdateBillDepositReceivedByMakeStatement(dto,
									cInfo);
				}

				// 如果待释放的预付单列表不为空
				if (!CollectionUtils
						.isEmpty(toReleaseAdvancedPaymentEntityList)) {
					// 生成批量修改预付单对账单号dto
					BillAdvancedPaymentEntityDto dto = new BillAdvancedPaymentEntityDto();
					// 设置默认对账单号
					dto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
					// 设置待释放的预付单列表
					dto.setBillAdvancedPayment(toReleaseAdvancedPaymentEntityList);
					// 批量修改
					billAdvancedPaymentService.batchUpdateByMakeStatement(dto,
							cInfo);
				}

				// 5.2、非对账单核销：即通过预收冲应收、应收冲应付等界面核销，但却属于对账单明细中
			} else {

				// 生成未确认对账单的应收单列表
				List<BillReceivableEntity> noConfirmReceivableEntityList = new ArrayList<BillReceivableEntity>();
				// 生成未确认对账单的应付单列表
				List<BillPayableEntity> noConfirmPayableEntityList = new ArrayList<BillPayableEntity>();
				// 生成未确认对账单的预收单列表
				List<BillDepositReceivedEntity> noConfirmDepositReceivedEntityList = new ArrayList<BillDepositReceivedEntity>();
				// 生成未确认对账单的预付单列表
				List<BillAdvancedPaymentEntity> noConfirmAdvancedPaymentEntityList = new ArrayList<BillAdvancedPaymentEntity>();

				// 循环核销后的应收单，把未确认的应收单加入到未确认对账单应收单列表
				if (!CollectionUtils.isEmpty(receivableEntityList)) {
					// 循环处理应收单
					for (BillReceivableEntity entityTemp : receivableEntityList) {
						// 根据对账单号查询对账单
						StatementOfAccountEntity stateEntity = statementOfAccountService
								.queryByStatementNo(entityTemp
										.getStatementBillNo());
						// 如果查询不到对账单，抛出业务异常
						if (stateEntity != null) {
							// 如果对账单是未确认状态
							if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
									.equals(stateEntity.getConfirmStatus())) {
								// 加入到未确认对账单应收单列表
								noConfirmReceivableEntityList.add(entityTemp);
							}
						}
					}
				}

				// 循环核销后的应付单，把未确认的应付单加入到未确认对账单应付单列表
				if (!CollectionUtils.isEmpty(payableEntityList)) {
					// 循环处理应付单
					for (BillPayableEntity entityTemp : payableEntityList) {
						// 根据对账单号查询对账单
						StatementOfAccountEntity stateEntity = statementOfAccountService
								.queryByStatementNo(entityTemp
										.getStatementBillNo());
						// 如果查询不到对账单，抛出业务异常
						if (stateEntity != null) {
							// 如果对账单是未确认状态
							if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
									.equals(stateEntity.getConfirmStatus())) {
								// 加入到未确认对账单的应付单列表
								noConfirmPayableEntityList.add(entityTemp);
							}
						}
					}
				}

				// 循环核销后的预收单，把未确认的预收单加入到未确认对账单预收单列表
				validaDeposit(depositReceivedEntityList,
						noConfirmDepositReceivedEntityList);

				// 循环核销后的预付单，把未确认的预付单加入到未确认对账单预付单列表
				validaBillAdvanced(advancePaymentEntityList,
						noConfirmAdvancedPaymentEntityList);

				// 反核销完成，对于未确认的子单据，通知修改对账单及对账单明细信息
				StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
				statementOfAccountUpdateDto
						.setReceivableEntityList(noConfirmReceivableEntityList);
				statementOfAccountUpdateDto
						.setPayableEntityList(noConfirmPayableEntityList);
				statementOfAccountUpdateDto
						.setDepositReceivedEntityList(noConfirmDepositReceivedEntityList);
				statementOfAccountUpdateDto
						.setAdvancePaymentEntityList(noConfirmAdvancedPaymentEntityList);
				statementOfAccountService
						.updateStatementAndDetailForBackWriteOff(
								statementOfAccountUpdateDto, cInfo);
			}
		}

		// 7、释放汇款：
		for (BillWriteoffEntity entity : billWriteoffEntitylist) {
			for (BillRepaymentEntity oldEntity : oldEntityList) {
				if (entity.getBeginNo().equals(oldEntity.getRepaymentNo())) {
					// 调用费控接口，去释放汇款
					if (StringUtils.isNotEmpty(oldEntity.getOaPaymentNo())
							&& FossConstants.NO.equals(oldEntity.getIsInit())
							&& (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
									.endsWith(oldEntity.getPaymentType()) || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE
									.endsWith(oldEntity.getPaymentType()))) {
						LOGGER.info("反核消还款单时调用费控接口释放汇款,汇款单号号："
								+ oldEntity.getOaPaymentNo());
						fossToFinanceRemittanceService.releaseClaim(
								entity.getAmount(), oldEntity.getOaPaymentNo(),
								oldEntity.getCollectionOrgCode(),
								oldEntity.getRepaymentNo());
					}

                    //FOSS20141016 网上支付安全收款
                    // Author 105762

                    // 如果作废的是网上支付还款单，且校验通过，且是非初始化数据（Foss生成非迁移），则调用OA反占用汇款接口(开关为true时，调用费控)
                    if (StringUtils.isNotEmpty(oldEntity.getOnlinePaymentNo()) && FossConstants.NO.equals(oldEntity.getIsInit())
                            && SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(oldEntity.getPaymentType())) {
                        // 调用财务自助释放网上支付金额
                        financeOnlinePayWSProxy.release(oldEntity.getOnlinePaymentNo(),entity.getAmount());
                    }
					break;
				}
			}
		}
	}

	private void validaDepositReceived(
			List<BillDepositReceivedEntity> depositReceivedEntityList,
			List<BillAdvancedPaymentEntity> advancedPaymentEntityList,
			List<String> toCheckStatementNoList) {
		if (!CollectionUtils.isEmpty(depositReceivedEntityList)) {
			for (BillDepositReceivedEntity dEntityTemp : depositReceivedEntityList) {
				if (StringUtils.isNotEmpty(dEntityTemp
						.getStatementBillNo())
						&& !SettlementConstants.DEFAULT_BILL_NO
								.equals(dEntityTemp
										.getStatementBillNo())) {
					toCheckStatementNoList.add(dEntityTemp
							.getStatementBillNo());
				}
			}
		}
		// 如果预付单不为空，循环应收单将预付单号加入toCheckStatementNoList
		if (!CollectionUtils.isEmpty(advancedPaymentEntityList)) {
			for (BillAdvancedPaymentEntity dEntityTemp : advancedPaymentEntityList) {
				if (StringUtils.isNotEmpty(dEntityTemp
						.getStatementBillNo())
						&& !SettlementConstants.DEFAULT_BILL_NO
								.equals(dEntityTemp
										.getStatementBillNo())) {
					toCheckStatementNoList.add(dEntityTemp
							.getStatementBillNo());
				}
			}
		}
	}

	private void validaEntityList(
			List<BillReceivableEntity> receivableEntityList,
			List<String> toCheckStatementNoList) {
		if (!CollectionUtils.isEmpty(receivableEntityList)) {
			for (BillReceivableEntity dEntityTemp : receivableEntityList) {
				if (StringUtils.isNotEmpty(dEntityTemp
						.getStatementBillNo())
						&& !SettlementConstants.DEFAULT_BILL_NO
								.equals(dEntityTemp
										.getStatementBillNo())) {
					toCheckStatementNoList.add(dEntityTemp
							.getStatementBillNo());
				}
			}
		}
	}

	private void validaCurrentInfo(CurrentInfo cInfo,
			BillWriteoffEntity entity, String paymentType,
			StatementOfAccountEntity stateEntity) {
		if (stateEntity.getPeriodAmount().compareTo(
				BigDecimal.ZERO) > 0
				&& SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
						.equals(entity.getWriteoffType())) {
			// 修改对账单未还款金额，对账单未还款金额=对账单未还款金额+核销单核销金额
			stateEntity.setUnpaidAmount(stateEntity
					.getUnpaidAmount().add(
							entity.getAmount()));
			// 修改对账单信息
			statementOfAccountService
					.updateStatementForWriteOff(
							stateEntity, cInfo);
			
			/**
			 * 判断是否为银行卡刷卡
			 */
			if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)){
				/**
				 * 还款冲应收
				 */
				/**
				 * NCI项目  释放了应收单的的金额，对账单金额减少，释放T+0已使用金额
				 * @author 269052 zhouyuan008@deppon.com
				 * @date 2016-04-14
				 */
				PosCardDetailEntity detail = new PosCardDetailEntity();
				detail.setInvoiceNo(entity.getStatementBillNo());
				/**
				 * 设置修改人信息
				 */
				detail.setModifyUser(cInfo.getEmpName());
				detail.setModifyUserCode(cInfo.getEmpCode());
				// 判断单号是否来自于NCI项目
				List<PosCardDetailEntity> list = pdaPosManageService.queryPosDetailsByNo(detail);
				if(CollectionUtils.isNotEmpty(list)){
					// 释放NCI项目中T+0报表中未使用流水号金额
					/*PosCardDetailEntity pd = new PosCardDetailEntity();
					// 设置单据号
					pd.setInvoiceNo(entity.getStatementBillNo());*/
					detail.setOccupateAmount(entity.getAmount());//核销掉的金额，就是还的钱
					// 释放T+0报表的金额和修改明细中对账单总金额
					pdaPosManageService.handleReverseBillWriterOff(detail);
				}
			}
		}
	}

	private void validaBillWriteoffEntity(BillWriteoffEntity entity,
			List<BillReceivableEntity> toReleaseReceivableEntityList) {
		if (SettlementUtil.isReceiveable(entity.getBeginNo())) {
			BillReceivableEntity billReceivableEntity = billReceivableService
					.queryByReceivableNO(entity.getBeginNo(),
							FossConstants.ACTIVE);
			// 如果应收单不为空，且应收单的对账单号等于对账单应收明细的对账单号
			if (billReceivableEntity != null) {
				// 将应收单加入到待释放应收单明细集合

				boolean flagIsExist = false;
				if (!CollectionUtils
						.isEmpty(toReleaseReceivableEntityList)) {
					for (BillReceivableEntity temp : toReleaseReceivableEntityList) {
						if (temp.getReceivableNo().equals(
								billReceivableEntity
										.getReceivableNo())) {
							flagIsExist = true;
						}
					}
				}
				if (!flagIsExist) {
					toReleaseReceivableEntityList
							.add(billReceivableEntity);
				}

			}
		}
	}

	private void validaBigDecimal(StatementOfAccountEntity stateEntity,
			BigDecimal amountTemp, BigDecimal writeoffedAmount,
			List<BillWriteoffEntity> listTemp) {
		if (!CollectionUtils.isEmpty(listTemp)) {
			for (BillWriteoffEntity entityTemop : listTemp) {
				writeoffedAmount = writeoffedAmount
						.add(entityTemop
								.getAmount());
			}
		}
		// -当前已还款金额
		amountTemp = amountTemp
				.subtract(writeoffedAmount);

		if (amountTemp.compareTo(BigDecimal.ZERO) < 0) {
			stateEntity
					.setUnpaidAmount(BigDecimal.ZERO);
		} else {
			stateEntity.setUnpaidAmount(amountTemp);
		}
	}

	private void validaPaymentEntity(
			List<BillAdvancedPaymentEntity> toReleaseAdvancedPaymentEntityList,
			BillAdvancedPaymentEntity billAdvancedPaymentEntity) {
		if (billAdvancedPaymentEntity != null) {
			// 将预付单加入到待释放预付单明细集合

			boolean flagIsExist = false;
			if (!CollectionUtils
					.isEmpty(toReleaseAdvancedPaymentEntityList)) {
				for (BillAdvancedPaymentEntity temp : toReleaseAdvancedPaymentEntityList) {
					if (temp.getAdvancesNo().equals(
							billAdvancedPaymentEntity
									.getAdvancesNo())) {
						flagIsExist = true;
					}
				}
			}
			if (!flagIsExist) {
				toReleaseAdvancedPaymentEntityList
						.add(billAdvancedPaymentEntity);
			}
		}
	}

	private void validaDeposit(
			List<BillDepositReceivedEntity> depositReceivedEntityList,
			List<BillDepositReceivedEntity> noConfirmDepositReceivedEntityList) {
		if (!CollectionUtils.isEmpty(depositReceivedEntityList)) {
			// 循环处理预收单
			for (BillDepositReceivedEntity entityTemp : depositReceivedEntityList) {
				// 根据对账单号查询对账单
				StatementOfAccountEntity stateEntity = statementOfAccountService
						.queryByStatementNo(entityTemp
								.getStatementBillNo());
				// 如果查询不到对账单，抛出业务异常
				if (stateEntity != null) {
					// 如果对账单是未确认状态
					if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
							.equals(stateEntity.getConfirmStatus())) {
						// 加入到未确认对账单预收单列表
						noConfirmDepositReceivedEntityList
								.add(entityTemp);
					}
				}
			}
		}
	}

	private void validaBillAdvanced(
			List<BillAdvancedPaymentEntity> advancePaymentEntityList,
			List<BillAdvancedPaymentEntity> noConfirmAdvancedPaymentEntityList) {
		if (!CollectionUtils.isEmpty(advancePaymentEntityList)) {
			// 循环处理应收单
			for (BillAdvancedPaymentEntity entityTemp : advancePaymentEntityList) {
				// 根据对账单号查询对账单
				StatementOfAccountEntity stateEntity = statementOfAccountService
						.queryByStatementNo(entityTemp
								.getStatementBillNo());
				// 如果查询不到对账单，抛出业务异常
				if (stateEntity != null) {
					// 如果对账单是未确认状态
					if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
							.equals(stateEntity.getConfirmStatus())) {
						// 加入到未确认对账单应收单列表
						noConfirmAdvancedPaymentEntityList
								.add(entityTemp);
					}
				}
			}
		}
	}

	/**
	 * 根据对账单的本期发生金额更新对账单的本期剩余发生金额信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-4-27 下午3:11:52
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	private StatementOfAccountEntity updatePeriodUnverifyAmount(
			StatementOfAccountEntity soaEntity) {
		/*
		 * 计算本期剩余发生金额明细信息，由于下面代码逻辑比较负责，所以将部分代码逻辑分析过程作为注释进行了添加
		 * 1、对账单明细的核销顺序为预收冲应收、应收冲应付、还款冲应收、预付冲应付 2、本期发生金额=本期应收+本期预付-本期应付-本期预收
		 * 3、如果本期发生金额大于0，且应收单的核销优先于预付单，所以结果可能由本期剩余应收金额+本期剩余预付金额 或者完全由本期剩余预付金额组成；
		 * 当本期发生金额大于本期预付金额，则本期发生金额=本期剩余应收金额+本期剩余预付金额，且本期剩余应收金额=
		 * 本期发生金额-本期预付金额，本期剩余预付金额=本期预付金额； 当本期发生金额小于等于本期预付金额，则本期发生金额=本期剩余预付金额；
		 * 4、如果本期发生金额小于0，且本期预收单的核销优先于应付单，所以结果可能由预收单+应付单或者完全由应付单组成
		 * 当本期发生金额的绝对值大于本期应付金额，则本期发生金额=本期剩余应付金额=本期剩余预收金额，且本期剩余应
		 * 付金额=本期应付金额，本期剩余预收金额=本期发生金额绝对值-本期应付金额；
		 * 当本期发生金额绝对值小于本期应付金额，则本期发生金额绝对值=本期剩余应付金额
		 */
		// 本期剩余应收金额
		BigDecimal periodUnverifyRecAmount = BigDecimal.ZERO;
		// 本期剩余应付金额
		BigDecimal periodUnverifyPayAmount = BigDecimal.ZERO;
		// 本期剩余预收金额
		BigDecimal periodUnverifyPreAmount = BigDecimal.ZERO;
		// 本期剩余预付金额
		BigDecimal periodUnverifyAdvAmount = BigDecimal.ZERO;
		// 如果本期发生金额大于0
		if (soaEntity.getPeriodAmount().compareTo(BigDecimal.ZERO) > 0) {
			// 如果本期发生金额大于本期预付金额
			if (soaEntity.getPeriodAmount().compareTo(
					soaEntity.getPeriodAdvAmount()) > 0) {
				// 本期未核销应收金额=本期发生金额减去本期预付金额
				periodUnverifyRecAmount = soaEntity.getPeriodAmount().subtract(
						soaEntity.getPeriodAdvAmount());
				// 本期未核销预付金额=本期发生金额减去奔去未核销应收金额
				periodUnverifyAdvAmount = soaEntity.getPeriodAmount().subtract(
						periodUnverifyRecAmount);

			} else {
				// 本期未核销预付金额=本期发生金额
				periodUnverifyAdvAmount = soaEntity.getPeriodAmount();
			}
			// 如果本期发生金额小于0
		} else if (soaEntity.getPeriodAmount().compareTo(BigDecimal.ZERO) < 0) {
			// 如果本期发生金额的绝对值大于本期应付金额
			if (soaEntity.getPeriodAmount().negate()
					.compareTo(soaEntity.getPeriodPayAmount()) > 0) {
				// 本期未核销应付金额=本期应付金额
				periodUnverifyPayAmount = soaEntity.getPeriodPayAmount();
				// 本期未核销预收金额=本期发生金额的绝对值加上本期应付金额
				periodUnverifyPreAmount = soaEntity.getPeriodAmount().negate()
						.subtract(soaEntity.getPeriodPayAmount());
			} else {
				// 本期未核销应付金额=本期发生金额绝对值
				periodUnverifyPayAmount = soaEntity.getPeriodAmount().negate();
			}
		}
		// 设置本期未核销应收金额
		soaEntity.setPeriodUnverifyRecAmount(periodUnverifyRecAmount);
		// 应付金额
		soaEntity.setPeriodUnverifyPayAmount(periodUnverifyPayAmount);
		// 预收金额
		soaEntity.setPeriodUnverifyPreAmount(periodUnverifyPreAmount);
		// 预付金额
		soaEntity.setPeriodUnverifyAdvAmount(periodUnverifyAdvAmount);
		return soaEntity;
	}

	/**
	 * @get
	 * @return reverseBillWriteoffQueryDao
	 */
	public IReverseBillWriteoffQueryDao getReverseBillWriteoffQueryDao() {
		/*
		 * @get
		 * 
		 * @return reverseBillWriteoffQueryDao
		 */
		return reverseBillWriteoffQueryDao;
	}

	/**
	 * @set
	 * @param reverseBillWriteoffQueryDao
	 */
	public void setReverseBillWriteoffQueryDao(
			IReverseBillWriteoffQueryDao reverseBillWriteoffQueryDao) {
		/*
		 * @set
		 * 
		 * @this.reverseBillWriteoffQueryDao = reverseBillWriteoffQueryDao
		 */
		this.reverseBillWriteoffQueryDao = reverseBillWriteoffQueryDao;
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
	 * @return billRepaymentService
	 */
	public IBillRepaymentService getBillRepaymentService() {
		/*
		 * @get
		 * 
		 * @return billRepaymentService
		 */
		return billRepaymentService;
	}

	/**
	 * @set
	 * @param billRepaymentService
	 */
	public void setBillRepaymentService(
			IBillRepaymentService billRepaymentService) {
		/*
		 * @set
		 * 
		 * @this.billRepaymentService = billRepaymentService
		 */
		this.billRepaymentService = billRepaymentService;
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
	 * @return configurationParamsService
	 */
	public IConfigurationParamsService getConfigurationParamsService() {
		/*
		 * @get
		 * 
		 * @return configurationParamsService
		 */
		return configurationParamsService;
	}

	/**
	 * @set
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		/*
		 * @set
		 * 
		 * @this.configurationParamsService = configurationParamsService
		 */
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @get
	 * @return codCommonService
	 */
	public ICodCommonService getCodCommonService() {
		/*
		 * @get
		 * 
		 * @return codCommonService
		 */
		return codCommonService;
	}

	/**
	 * @set
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		/*
		 * @set
		 * 
		 * @this.codCommonService = codCommonService
		 */
		this.codCommonService = codCommonService;
	}

	/**
	 * @set
	 * @param fossToFinanceRemittanceService
	 */
	public void setFossToFinanceRemittanceService(
			IFossToFinanceRemittanceService fossToFinanceRemittanceService) {
		/*
		 * @set
		 * 
		 * @this.fossToFinanceRemittanceService = fossToFinanceRemittanceService
		 */
		this.fossToFinanceRemittanceService = fossToFinanceRemittanceService;
	}

	/**
	 * @set
	 * @param billDepositReceivedService
	 */
	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		/*
		 * @set
		 * 
		 * @this.billDepositReceivedService = billDepositReceivedService
		 */
		this.billDepositReceivedService = billDepositReceivedService;
	}

	/**
	 * @set
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		/*
		 * @set
		 * 
		 * @this.billPayableService = billPayableService
		 */
		this.billPayableService = billPayableService;
	}

	/**
	 * @get
	 * @return statementOfAccountDService
	 */
	public IStatementOfAccountDService getStatementOfAccountDService() {
		/*
		 * @get
		 * 
		 * @return statementOfAccountDService
		 */
		return statementOfAccountDService;
	}

	/**
	 * @set
	 * @param statementOfAccountDService
	 */
	public void setStatementOfAccountDService(
			IStatementOfAccountDService statementOfAccountDService) {
		/*
		 * @set
		 * 
		 * @this.statementOfAccountDService = statementOfAccountDService
		 */
		this.statementOfAccountDService = statementOfAccountDService;
	}

	/**
	 * @get
	 * @return statementMakeService
	 */
	public IStatementMakeService getStatementMakeService() {
		/*
		 * @get
		 * 
		 * @return statementMakeService
		 */
		return statementMakeService;
	}

	/**
	 * @set
	 * @param statementMakeService
	 */
	public void setStatementMakeService(
			IStatementMakeService statementMakeService) {
		/*
		 * @set
		 * 
		 * @this.statementMakeService = statementMakeService
		 */
		this.statementMakeService = statementMakeService;
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
	 * @set
	 * @param billAdvancedPaymentService
	 */
	public void setBillAdvancedPaymentService(
			IBillAdvancedPaymentService billAdvancedPaymentService) {
		/*
		 * @set
		 * 
		 * @this.billAdvancedPaymentService = billAdvancedPaymentService
		 */
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}

	public void setWoodenStatementService(
			IWoodenStatementService woodenStatementService) {
		this.woodenStatementService = woodenStatementService;
	}

    public void setFinanceOnlinePayWSProxy(IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
        this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
    }

	public void setPartnerStatementOfAwardMService(IPartnerStatementOfAwardMService partnerStatementOfAwardMService) {
		this.partnerStatementOfAwardMService = partnerStatementOfAwardMService;
	}
    
    /**
	 * @SET
	 * @param grayCustomerService
	 */
	public void setGrayCustomerService(IGrayCustomerService grayCustomerService) {
		this.grayCustomerService = grayCustomerService;
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}
    
}
