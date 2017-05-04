package com.deppon.foss.module.settlement.writeoff.server.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IWriteoffBillDepositReceivedQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IWriteoffBillReceivableQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepWriteoffBillRecDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 待核销预收单、应收单进行查询、核销等操作service实现
 * 
 * @author ibm-qiaolifeng
 * @date 2012-10-15 上午11:16:03
 */
public class BillDepWriteoffBillRecService implements IBillDepWriteoffBillRecService {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(BillDepWriteoffBillRecService.class);

	/**
	 * 预收Dao
	 */
	private IWriteoffBillDepositReceivedQueryDao writeoffBillDepositReceivedQueryDao;

	/**
	 * 应收Dao
	 */
	private IWriteoffBillReceivableQueryDao writeoffBillReceivableQueryDao;

	/**
	 * 核销公共service
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 对账单公共service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 结束通用服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 更改单服务接口
	 */
	private IWaybillRfcService waybillRfcService;

	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 灰名单服务
	 */
	private IGrayCustomerService grayCustomerService;

	/**
	 * 查询预收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-22 上午11:51:09
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService#queryBillDepWriteoffBillRec(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto,
	 *      com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto)
	 */
	@Override
	public BillDepWriteoffBillRecDto queryBillDep(BillDepositReceivedDto billDepositReceivedDto) {

		//查询参数dto不能为空
		if (billDepositReceivedDto == null|| StringUtils.isEmpty(billDepositReceivedDto.getQueryType())) {
			//提示输入参数为空,查询失败
			throw new SettlementException("输入参数为空,查询失败");
		}

		//生成预收冲应收dto
		BillDepWriteoffBillRecDto rtnDto = null;

		// 按日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billDepositReceivedDto.getQueryType())) {
			//记录日志
			LOGGER.debug("按参数查询预收单开始...");
			//按日期查询预收单
			rtnDto = queryBillDepByParams(billDepositReceivedDto);
		// 按单号查询	
		} else if (SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO.equals(billDepositReceivedDto.getQueryType())) {
			//记录日志
			LOGGER.debug("按单号查询预收单开始...");
			//按单号查询预收单
			rtnDto = queryBillDepByNOs(billDepositReceivedDto);
		}

		//返回预收冲应收dto
		return rtnDto;
	}

	/**
	 * 查询应收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-22 上午11:51:09
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService#queryBillDepWriteoffBillRec(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto,
	 *      com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto)
	 */
	@Override
	public BillDepWriteoffBillRecDto queryBillRec(BillReceivableDto billReceivableDto) {

		//查询参数dto不能为空
		if (billReceivableDto == null) {
			//提示输入参数为空,查询失败
			throw new SettlementException("内部错误，参数为空");
		}
		//生成预收冲应收dto
		BillDepWriteoffBillRecDto rtnDto = null;

		// 按日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(billReceivableDto.getQueryType())) {
			//记录日志
			LOGGER.debug("按参数查询应收单开始...");
			// 按日期查询应收单
			rtnDto = queryBillRecByParams(billReceivableDto);
		// 按单号查询	
		} else if (SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO.equals(billReceivableDto.getQueryType())) {
			//记录日志
			LOGGER.debug("按单号查询应收单开始...");
			// 按单号查询应收单
			rtnDto = queryBillRecByNOs(billReceivableDto);
		}

		//返回预收冲应收dto
		return rtnDto;
	}

	/**
	 * 根据传入预收单号获取一到多条预收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-16 下午3:43:20
	 * @param billDepositReceivedDto
	 *            参数集合
	 * @return
	 * @see
	 */
	private BillDepWriteoffBillRecDto queryBillDepByNOs(BillDepositReceivedDto billDepositReceivedDto) {

		// 查询预收单数据
		List<BillDepositReceivedEntity> billDepositReceivedEntityListAll = queryByDepositReceivedNOs(billDepositReceivedDto);

		// 设置预收单列表的总金额
		BigDecimal totalAmountDep = BigDecimal.ZERO;
		// 设置预收单列表的未核销总金额
		BigDecimal unverifyTotalAmountDep = BigDecimal.ZERO;
		// 设置预收单列表的已核销总金额
		BigDecimal verifyTotalAmountDep = BigDecimal.ZERO;

		// 计算应收、预收单列表的总条数、总金额、未核销总金额、已核销总金额
		//循环处理预收单
		for (BillDepositReceivedEntity entity : billDepositReceivedEntityListAll) {
			//总金额
			totalAmountDep = totalAmountDep.add(entity.getAmount());
			//未核销总金额
			unverifyTotalAmountDep = unverifyTotalAmountDep.add(entity.getUnverifyAmount());
			//已核销总金额
			verifyTotalAmountDep = verifyTotalAmountDep.add(entity.getVerifyAmount());
		}

		// 生成预收冲应收返回dto
		BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = new BillDepWriteoffBillRecDto();

		//预收冲应收返回dto预收单列表
		billDepWriteoffBillRecDto.setBillDepositreceivedEntityList(billDepositReceivedEntityListAll);
		//预收冲应收返回dto总条数
		billDepWriteoffBillRecDto.setTotalNumDep(billDepositReceivedEntityListAll.size());
		//预收冲应收返回dto总金额
		billDepWriteoffBillRecDto.setTotalAmountDep(totalAmountDep);
		//预收冲应收返回dto未核销金额
		billDepWriteoffBillRecDto.setUnverifyTotalAmountDep(unverifyTotalAmountDep);
		//预收冲应收返回dto已核销金额
		billDepWriteoffBillRecDto.setVerifyTotalAmountDep(verifyTotalAmountDep);

		//返回预收冲应收返回dto
		return billDepWriteoffBillRecDto;
	}

	/**
	 * 根据传入应收单号获取一到多条应收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-16 下午3:43:20
	 * @param billDepositReceivedDto
	 *            参数集合
	 * @return
	 * @see
	 */
	private BillDepWriteoffBillRecDto queryBillRecByNOs(BillReceivableDto billReceivableDto) {

		// 查询应收单数据
		List<BillReceivableEntity> billReceivableEntityListAll = queryByReceivableNOs(billReceivableDto);

		// 设置应收单列表的总金额
		BigDecimal totalAmountRec = BigDecimal.ZERO;
		// 设置应收单列表的未核销总金额
		BigDecimal unverifyTotalAmountRec = BigDecimal.ZERO;
		// 设置应收单列表的已核销总金额
		BigDecimal verifyTotalAmountRec = BigDecimal.ZERO;

		// 计算应收列表的总条数、总金额、未核销总金额、已核销总金额
		//循环处理应收单
		for (BillReceivableEntity entity : billReceivableEntityListAll) {
			//总金额
			totalAmountRec = totalAmountRec.add(entity.getAmount());
			//未核销总金额
			unverifyTotalAmountRec = unverifyTotalAmountRec.add(entity.getUnverifyAmount());
			//已核销总金额
			verifyTotalAmountRec = verifyTotalAmountRec.add(entity.getVerifyAmount());
		}

		// 生成预收冲应收返回dto
		BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = new BillDepWriteoffBillRecDto();
		//设置预收冲应收返回dto应收单列表
		billDepWriteoffBillRecDto.setBillReceivableEntityList(billReceivableEntityListAll);
		//设置预收冲应收返回dto总条数
		billDepWriteoffBillRecDto.setTotalNumRec(billReceivableEntityListAll.size());
		//设置预收冲应收返回dto总金额
		billDepWriteoffBillRecDto.setTotalAmountRec(totalAmountRec);
		//设置预收冲应收返回dto未核销总金额
		billDepWriteoffBillRecDto.setUnverifyTotalAmountRec(unverifyTotalAmountRec);
		//设置预收冲应收返回dto已核销总金额
		billDepWriteoffBillRecDto.setVerifyTotalAmountRec(verifyTotalAmountRec);

		//返回预收冲应收dto
		return billDepWriteoffBillRecDto;
	}

	/**
	 * 根据传入参数获取一到多条预收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-16 下午3:43:20
	 * @param billDepositReceivedDto
	 *            参数集合
	 * @return
	 * @see
	 */
	private BillDepWriteoffBillRecDto queryBillDepByParams(BillDepositReceivedDto billDepositReceivedDto) {

		// 查询预收单数据
		List<BillDepositReceivedEntity> billDepositReceivedEntityListAll = queryByDepositReceivedParams(billDepositReceivedDto);

		// 设置预收单列表的总金额
		BigDecimal totalAmountDep = BigDecimal.ZERO;
		// 设置预收单列表的未核销总金额
		BigDecimal unverifyTotalAmountDep = BigDecimal.ZERO;
		// 设置预收单列表的已核销总金额
		BigDecimal verifyTotalAmountDep = BigDecimal.ZERO;

		// 计算应收、预收单列表的总条数、总金额、未核销总金额、已核销总金额
		//循环处理预收单
		for (BillDepositReceivedEntity entity : billDepositReceivedEntityListAll) {
		//总金额
			totalAmountDep = totalAmountDep.add(entity.getAmount());
		//未核销总金额
			unverifyTotalAmountDep = unverifyTotalAmountDep.add(entity.getUnverifyAmount());
			//已核销总金额
			verifyTotalAmountDep = verifyTotalAmountDep.add(entity.getVerifyAmount());
		}

		// 生成预收冲应收返回dto
		BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = new BillDepWriteoffBillRecDto();

		//预收冲应收返回dto预收单列表
		billDepWriteoffBillRecDto.setBillDepositreceivedEntityList(billDepositReceivedEntityListAll);
		//预收冲应收返回dto总条数
		billDepWriteoffBillRecDto.setTotalNumDep(billDepositReceivedEntityListAll.size());
		//预收冲应收返回dto总金额
		billDepWriteoffBillRecDto.setTotalAmountDep(totalAmountDep);
		//预收冲应收返回dto未核销金额
		billDepWriteoffBillRecDto.setUnverifyTotalAmountDep(unverifyTotalAmountDep);
		//预收冲应收返回dto已核销金额
		billDepWriteoffBillRecDto.setVerifyTotalAmountDep(verifyTotalAmountDep);
		
		//返回预收冲应收返回dto
		return billDepWriteoffBillRecDto;
	}

	/**
	 * 根据传入参数获取一到多条应收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-16 下午3:43:20
	 * @param billDepositReceivedDto
	 *            参数集合
	 * @return
	 * @see
	 */
	private BillDepWriteoffBillRecDto queryBillRecByParams(BillReceivableDto billReceivableDto) {

		// 查询应收单数据

		List<BillReceivableEntity> billReceivableEntityListAll = queryByReceivableParams(billReceivableDto);

		// 设置应收单列表的总金额
		BigDecimal totalAmountRec = BigDecimal.ZERO;
		// 设置应收单列表的未核销总金额
		BigDecimal unverifyTotalAmountRec = BigDecimal.ZERO;
		// 设置应收单列表的已核销总金额
		BigDecimal verifyTotalAmountRec = BigDecimal.ZERO;

		// 计算应收列表的总条数、总金额、未核销总金额、已核销总金额
		//循环处理应收单
		for (BillReceivableEntity entity : billReceivableEntityListAll) {
			//总金额
			totalAmountRec = totalAmountRec.add(entity.getAmount());
			//未核销总金额
			unverifyTotalAmountRec = unverifyTotalAmountRec.add(entity.getUnverifyAmount());
			//已核销总金额
			verifyTotalAmountRec = verifyTotalAmountRec.add(entity.getVerifyAmount());
		}

		// 生成预收冲应收返回dto
		BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = new BillDepWriteoffBillRecDto();
		//设置预收冲应收返回dto应收单列表
		billDepWriteoffBillRecDto.setBillReceivableEntityList(billReceivableEntityListAll);
		//设置预收冲应收返回dto总条数
		billDepWriteoffBillRecDto.setTotalNumRec(billReceivableEntityListAll.size());
		//设置预收冲应收返回dto总金额
		billDepWriteoffBillRecDto.setTotalAmountRec(totalAmountRec);
		//设置预收冲应收返回dto未核销总金额
		billDepWriteoffBillRecDto.setUnverifyTotalAmountRec(unverifyTotalAmountRec);
		//设置预收冲应收返回dto已核销总金额
		billDepWriteoffBillRecDto.setVerifyTotalAmountRec(verifyTotalAmountRec);

		//返回预收冲应收dto
		return billDepWriteoffBillRecDto;
	}

	/**
	 * 根据传入的一到多个预收单号，获取一到多条预收单信息
	 * 
	 * @author ibm-qiaolifeng
	 * @date 2012-10-15 上午9:50:50
	 * @param billDepositReceivedDto
	 *            单号集合
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService#queryByDepositReceivedNOs(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto)
	 */
	@Override
	public List<BillDepositReceivedEntity> queryByDepositReceivedNOs(BillDepositReceivedDto billDepositReceivedDto) {
		// 如果参数为空，抛出异常
		if (billDepositReceivedDto == null) {
			//提示内部错误，参数为空
			throw new SettlementException("内部错误，参数为空!");
		}

		// 设置可用于核销的预收单的状态：有效
		billDepositReceivedDto.setActive(FossConstants.YES);
		// 设置可用于核销的预收单的状态：非红单
		billDepositReceivedDto.setIsRedBack(FossConstants.NO);
		// 付款单号为默认单号
		billDepositReceivedDto.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);
		//付款单付款状态：已付款
		billDepositReceivedDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);

		// 查询预收单列表
		List<BillDepositReceivedEntity> billDepositReceivedEntityList = writeoffBillDepositReceivedQueryDao.queryByDepositReceivedNOs(billDepositReceivedDto);

		// 1、调用对账单接口过滤预收单，根据单号过滤掉已经生成对账单的应收单
		// 1.1 、获取对账单号列表
		//对账单号列表
		List<String> statementBillNoList = null;
		//待处理预收单列表
		List<BillDepositReceivedEntity> toDelList = new ArrayList<BillDepositReceivedEntity>();
		//如果预收单列表不为空
		if (CollectionUtils.isNotEmpty(billDepositReceivedEntityList)) {
			//生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			//循环预收单列表
			for (BillDepositReceivedEntity entity : billDepositReceivedEntityList) {
				//如果预收单上对账单号存在
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					//加入到对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//检测对账单是否已确认，并返回已确认的对账单号
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}

		// 1.3、从预收单列表中除去已经确认对账单的数据
		//如果已确认的对账单号不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//循环已确认的对账单号列表
			for (String statementBillNo : statementBillNoList) {
				//循环预收单列表
				for (BillDepositReceivedEntity entity : billDepositReceivedEntityList) {
					//如果对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						//将预收单加入到待处理预收单列表
						toDelList.add(entity);
					}
				}
			}
		}
		//如果待处理预收单列表不为空
		if(CollectionUtils.isNotEmpty(toDelList)){
			//从预收单列表中删除待处理预收单列表中的相同数据
			billDepositReceivedEntityList.removeAll(toDelList);
			//情况待处理预收单列表
			toDelList.clear();
		}
		//返回处理后的预收单列表
		return billDepositReceivedEntityList;
	}

	/**
	 * 
	 * 根据传入的一到多个应收单号或者应收单对应运单号，获取一到多条应收单信息
	 * 
	 * @author ibm-qiaolifeng
	 * @date 2012-10-12 上午11:58:36
	 * @param billReceivableDto
	 *            单号集合
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService#queryByReceivableNOs(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto)
	 */
	@Override
	public List<BillReceivableEntity> queryByReceivableNOs(BillReceivableDto billReceivableDto) {

		//查询dto不能为空
		if (billReceivableDto == null) {
			//提示内部错误，参数为空
			throw new SettlementException("内部错误，参数为空");
		}

		/*
		 *  设置可用于核销应收单的状态: 
		 *  1、有效
		 *  2、非红单、
		 *  3、未进入制作对账单,
		 *  4、类型为：
		 *	       始发应收、到达应收、小票应收、空运其他应收、偏线代理应收单、空运到达代理应收
		 * 
		 * */
		// 设置可用于核销应收单的状态: 有效
		billReceivableDto.setActive(FossConstants.YES);
		// 设置可用于核销应收单的状态: 非红单
		billReceivableDto.setIsRedBack(FossConstants.NO);
		//生成可用于核销应收单的单据类型列表
		List<String> billTypeList = new ArrayList<String>();
		//设置可用于核销应收单单据类型为始发应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		//设置可用于核销应收单单据类型为到达应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		//设置可用于核销应收单单据类型为小票应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__OTHER_REVENUE_RECEIVABLE);
		//设置可用于核销应收单单据类型为空运其他应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE);
		//设置可用于核销应收单单据类型为到达偏线代理应收单
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE);
		//设置可用于核销应收单单据类型为空运到达代理应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
		//设置可用于核销应收单单据类型为空运代理代收货款应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
		//设置可用于核销应收单单据类型为空运到达代理应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD);
		//设置可用于核销应收单单据类型为空运代理代收货款应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
		//设置可用于核销应收单单据类型为空运代理代收货款应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE);
		//设置可用于核销应收单单据类型为偏线其他应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTIAL_LINE_RECEIVABLE);
		// TODO 新增合伙人应收单类型（允许查出来）
		//始发提成应收单（H）
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE);
		//委托派费应收单(H)
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE );
		//到付运费应收单（H）
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE);
		//代收货款应收单（H））
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		//设置可用于核销应收单单据类型为以上列表
		billReceivableDto.setBillTypeList(billTypeList);

		//已审核
		billReceivableDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);
		
		// 查询应收单列表
		List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();
		
		/* 按运单号查询应收单
		 * 
		 * 杨书硕 2013-7-8 上午11:36:58
		 */
		
		//存放按应收单号查询出的应收单临时list
		List<BillReceivableEntity> tmpReceivableEntityListByRec = new ArrayList<BillReceivableEntity>();
		
		//存放按运单号查询出的应收单临时list
		List<BillReceivableEntity> tmpReceivableEntityListByWaybill = new ArrayList<BillReceivableEntity>();

		// 应收单号不为空则按照应收单号查询
		if( CollectionUtils.isNotEmpty(billReceivableDto.getReceivableNos())){
			
			//按照应收单号查询
			tmpReceivableEntityListByRec.addAll(writeoffBillReceivableQueryDao.queryByReceivableNOs(billReceivableDto));
		}
		
		// 应收单对应运单号不为空则按照运单号查询
		if( CollectionUtils.isNotEmpty(billReceivableDto.getWaybillNoList())){
			
			//按照运单号查询
			tmpReceivableEntityListByWaybill.addAll(writeoffBillReceivableQueryDao.queryByWayBillNOs(billReceivableDto));
		}
		
		//去除按运单查询可能出现的重复应收单
		if(CollectionUtils.isNotEmpty(tmpReceivableEntityListByRec)&&CollectionUtils.isNotEmpty(tmpReceivableEntityListByWaybill)){
			for(BillReceivableEntity enTemp:tmpReceivableEntityListByWaybill){
				
				//循环比较按应收单号查出的list
				for(BillReceivableEntity en:tmpReceivableEntityListByRec){
					
					//对比应收单号
					if(enTemp.getReceivableNo().equals(en.getReceivableNo())){
						tmpReceivableEntityListByWaybill.remove(enTemp);
						break;
					}
				}
			}
		}
		
		//按应收单号查询出的数据存放进billReceivableEntityList
		billReceivableEntityList.addAll(tmpReceivableEntityListByRec);
		
		//添加进按运单查出的应收单
		billReceivableEntityList.addAll(tmpReceivableEntityListByWaybill);
		
		// 1、调用对账单接口过滤应收单，根据单号过滤掉已经生成对账单的应收单
		// 2、调用中转货接口，根据单号过滤掉存在未受理更改单的应收单
		// 1.1 、获取对账单号列表
		// 2.1、获取运单号列表
		//生成对账单号列表
		List<String> statementBillNoList = null;
		//生成运单号列表
		List<String> waybillNoList = null;
		//生成待处理应收单列表
		List<BillReceivableEntity> toDelList = new ArrayList<BillReceivableEntity>();

		//如果应收单列表不为空
		if (CollectionUtils.isNotEmpty(billReceivableEntityList)) {
			//生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			//生成运单号列表
			waybillNoList = new ArrayList<String>();
			//循环处理应收单列表
			for (BillReceivableEntity entity : billReceivableEntityList) {
				// 如果对账单号不为空
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					//获取对账单号加入到待验证对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
				// 如果运单号不为空
				if (StringUtils.isNotEmpty(entity.getWaybillNo())) {
					//获取运单号加入到待验证运单号列表
					waybillNoList.add(entity.getWaybillNo());
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		//如果对账单号列表
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//检测对账单号是否已确认，并返回已确认的对账单号列表
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}

		// 2.2、 调用运单更改单服务接口，返回存在未受理更改单的运单号
		//如果运单号列表不为空
		if (CollectionUtils.isNotEmpty(waybillNoList)) {
			//检测运单是否存在更改单，并返回存在更改单的运单号列表
			waybillNoList = waybillRfcService.isExsitsWayBillRfcs(waybillNoList);
		}

		// 1.3、从应收单列表中除去已经确认对账单的数据 
		//如果已确认的对账单号列表不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//循环处理已确认对账单号列表
			for (String statementBillNo : statementBillNoList) {
				//循环处理应收单列表
				for (BillReceivableEntity entity : billReceivableEntityList) {
					//如果对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						//将应收单加入到待处理应收单列表
						toDelList.add(entity);
					}
				}
			}
		}
		//如果待处理应收单列表不为空
		if(CollectionUtils.isNotEmpty(toDelList)){
			//从应收单列表中删除待处理应收单列表中相同的数据
			billReceivableEntityList.removeAll(toDelList);
			//清空待处理应收单列表
			toDelList.clear();
		}
		
		// 2.3、从应收单列表中除去存在未受理更改单的数据
		//如果存在更改单的运单号列表不为空
		if (CollectionUtils.isNotEmpty(waybillNoList)) {
			//循环处理存在更改单的运单号列表
			for (String waybillNo : waybillNoList) {
				//循环应收单列表
				for (BillReceivableEntity entity : billReceivableEntityList) {
					//如果运单号相等
					if (waybillNo.equals(entity.getWaybillNo())) {
						//将应收单加入到待处理应收单列表
						toDelList.add(entity);
					}
				}
			}
		}
		//如果待处理应收单列表不为空
		if(CollectionUtils.isNotEmpty(toDelList)){
			//从应收单列表中删除待处理应收单列表中相同的数据
			billReceivableEntityList.removeAll(toDelList);
			//清空待处理应收单列表
			toDelList.clear();
		}

		//返回处理后的应收单列表
		return billReceivableEntityList;
	}

	/**
	 * 根据传入参数获取一到多条预收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-16 下午3:43:20
	 * @param billDepositReceivedDto
	 *            参数集合
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService#queryByDepositReceivedParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto)
	 */
	@Override
	public List<BillDepositReceivedEntity> queryByDepositReceivedParams(BillDepositReceivedDto billDepositReceivedDto) {

		//查询dto不能为空
		if (billDepositReceivedDto == null) {
			//提示内部错误，参数为空
			throw new SettlementException("内部错误，参数为空");
		}

		// 设置可用于核销的预收单的状态：有效
		billDepositReceivedDto.setActive(FossConstants.YES);
		// 设置可用于核销的预收单的状态：非红单
		billDepositReceivedDto.setIsRedBack(FossConstants.NO);
		// 付款单号为默认单号
		billDepositReceivedDto.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);
		//付款单付款状态：已付款
		billDepositReceivedDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);

		// 查询预收单列表
		List<BillDepositReceivedEntity> billDepositReceivedEntityList = writeoffBillDepositReceivedQueryDao.queryByDepositReceivedParams(billDepositReceivedDto);

		// 1、调用对账单接口过滤预收单，根据单号过滤掉已经生成对账单的应收单
		// 1.1 、获取对账单号列表
		//对账单号列表
		List<String> statementBillNoList = null;
		//待处理预收单列表
		List<BillDepositReceivedEntity> toDelList = new ArrayList<BillDepositReceivedEntity>();
		//如果预收单列表不为空
		if (CollectionUtils.isNotEmpty(billDepositReceivedEntityList)) {
			//生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			//循环预收单列表
			for (BillDepositReceivedEntity entity : billDepositReceivedEntityList) {
				//如果预收单上对账单号存在
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					//加入到对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//检测对账单是否已确认，并返回已确认的对账单号
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}

		// 1.3、从预收单列表中除去已经确认对账单的数据
		//如果已确认的对账单号不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//循环已确认的对账单号列表
			for (String statementBillNo : statementBillNoList) {
				//循环预收单列表
				for (BillDepositReceivedEntity entity : billDepositReceivedEntityList) {
					//如果对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						//将预收单加入到待处理预收单列表
						toDelList.add(entity);
					}
				}
			}
		}
		//如果待处理预收单列表不为空
		if(CollectionUtils.isNotEmpty(toDelList)){
			//从预收单列表中删除待处理预收单列表中的相同数据
			billDepositReceivedEntityList.removeAll(toDelList);
			//情况待处理预收单列表
			toDelList.clear();
		}
		//返回处理后的预收单列表
		return billDepositReceivedEntityList;

	}

	/**
	 * 根据传入的参数获取一到多条应收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-16 下午4:44:23
	 * @param billDepositReceivedDto
	 *            参数集合
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService#queryByReceivableParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto)
	 */
	@Override
	public List<BillReceivableEntity> queryByReceivableParams(BillReceivableDto billReceivableDto) {

		//查询dto不能为空
		if (billReceivableDto == null) {
			//提示内部错误，参数为空
			throw new SettlementException("内部错误，参数为空");
		}

		/*
		 *  设置可用于核销应收单的状态: 
		 *  1、有效
		 *  2、非红单、
		 *  3、未进入制作对账单,
		 *  4、类型为：
		 *	       始发应收、到达应收、小票应收、空运其他应收、偏线代理应收单、空运到达代理应收
		 * 
		 * */
		// 设置可用于核销应收单的状态: 有效
		billReceivableDto.setActive(FossConstants.YES);
		// 设置可用于核销应收单的状态: 非红单
		billReceivableDto.setIsRedBack(FossConstants.NO);
		//生成可用于核销应收单的单据类型列表
		List<String> billTypeList = new ArrayList<String>();
		//设置可用于核销应收单单据类型为始发应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		//设置可用于核销应收单单据类型为到达应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		//设置可用于核销应收单单据类型为小票应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__OTHER_REVENUE_RECEIVABLE);
		//设置可用于核销应收单单据类型为空运其他应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE);
		//设置可用于核销应收单单据类型为到达偏线代理应收单
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE);
		//设置可用于核销应收单单据类型为空运到达代理应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
		//设置可用于核销应收单单据类型为空运代理代收货款应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
		//设置可用于核销应收单单据类型为快递代理其它应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD);
		//设置可用于核销应收单单据类型为快递代理应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
		//设置可用于核销应收单单据类型为快递代理应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE);
		//设置可用于核销应收单单据类型为偏线其他应收
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTIAL_LINE_RECEIVABLE);
		// TODO 新增合伙人应收单类型（允许查出来）
		//始发提成应收单（H）
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE);
		//委托派费应收单(H)
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE );
		//到付运费应收单（H）
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE);
		//代收货款应收单（H））
		billTypeList.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		//设置可用于核销应收单单据类型为以上列表
		billReceivableDto.setBillTypeList(billTypeList);
		
		//已审核
		billReceivableDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);

		// 查询应收单列表
		List<BillReceivableEntity> billReceivableEntityList = writeoffBillReceivableQueryDao.queryByReceivableParams(billReceivableDto);

		// 1、调用对账单接口过滤应收单，根据单号过滤掉已经生成对账单的应收单
		// 2、调用中转货接口，根据单号过滤掉存在未受理更改单的应收单
		// 1.1 、获取对账单号列表
		// 2.1、获取运单号列表
		//生成对账单号列表
		List<String> statementBillNoList = null;
		//生成运单号列表
		List<String> waybillNoList = null;
		//生成待处理应收单列表
		List<BillReceivableEntity> toDelList = new ArrayList<BillReceivableEntity>();

		//如果应收单列表不为空
		if (CollectionUtils.isNotEmpty(billReceivableEntityList)) {
			//生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			//生成运单号列表
			waybillNoList = new ArrayList<String>();
			//循环处理应收单列表
			for (BillReceivableEntity entity : billReceivableEntityList) {
				// 如果对账单号不为空
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					//获取对账单号加入到待验证对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
				// 如果运单号不为空
				if (StringUtils.isNotEmpty(entity.getWaybillNo())) {
					//获取运单号加入到待验证运单号列表
					waybillNoList.add(entity.getWaybillNo());
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		//如果对账单号列表
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//检测对账单号是否已确认，并返回已确认的对账单号列表
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}

		// 2.2、 调用运单更改单服务接口，返回存在未受理更改单的运单号
		//如果运单号列表不为空
		if (CollectionUtils.isNotEmpty(waybillNoList)) {
			//检测运单是否存在更改单，并返回存在更改单的运单号列表
			waybillNoList = waybillRfcService.isExsitsWayBillRfcs(waybillNoList);
		}

		// 1.3、从应收单列表中除去已经确认对账单的数据 
		//如果已确认的对账单号列表不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//循环处理已确认对账单号列表
			for (String statementBillNo : statementBillNoList) {
				//循环处理应收单列表
				for (BillReceivableEntity entity : billReceivableEntityList) {
					//如果对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						//将应收单加入到待处理应收单列表
						toDelList.add(entity);
					}
				}
			}
		}
		//如果待处理应收单列表不为空
		if(CollectionUtils.isNotEmpty(toDelList)){
			//从应收单列表中删除待处理应收单列表中相同的数据
			billReceivableEntityList.removeAll(toDelList);
			//清空待处理应收单列表
			toDelList.clear();
		}
				
		// 2.3、从应收单列表中除去存在未受理更改单的数据
		//如果存在更改单的运单号列表不为空
		if (CollectionUtils.isNotEmpty(waybillNoList)) {
			//循环处理存在更改单的运单号列表
			for (String waybillNo : waybillNoList) {
				//循环应收单列表
				for (BillReceivableEntity entity : billReceivableEntityList) {
					//如果运单号相等
					if (waybillNo.equals(entity.getWaybillNo())) {
						//将应收单加入到待处理应收单列表
						toDelList.add(entity);
					}
				}
			}
		}
		//如果待处理应收单列表不为空
		if(CollectionUtils.isNotEmpty(toDelList)){
			//从应收单列表中删除待处理应收单列表中相同的数据
			billReceivableEntityList.removeAll(toDelList);
			//清空待处理应收单列表
			toDelList.clear();
		}

		//返回处理后的应收单列表
		return billReceivableEntityList;
	}

	/**
	 * 预收冲应收核销
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-18 下午4:31:46
	 * @param billDepositReceivedEntityList
	 *            ,billReceivableEntityList预收单集合,应收单集合
	 */
	@Override
	@Transactional
	public BillDepWriteoffBillRecDto writeoffBillDepAndBillRec(List<BillDepositReceivedEntity> billDepositReceivedEntityListFrom,List<BillReceivableEntity> billReceivableEntityListFrom,List<BillDepositReceivedEntity> billDepositReceivedEntityListAll,List<BillReceivableEntity> billReceivableEntityListAll,CurrentInfo cInfo) throws SettlementException {

		//来源单号不能为空
		if (CollectionUtils.isEmpty(billDepositReceivedEntityListFrom)) {
			//提示内部错误，来源预收单集合为空
			throw new SettlementException("内部错误，来源预收单集合为空!");
		}
		//来源单号不能为空
		if (CollectionUtils.isEmpty(billReceivableEntityListFrom)) {
			//提示内部错误，来源应收单集合为空
			throw new SettlementException("内部错误，来源应收单集合为空!");
		}
		//来源单号不能为空
		if (CollectionUtils.isEmpty(billDepositReceivedEntityListAll)) {
			//提示内部错误，预收单集合为空
			throw new SettlementException("内部错误，预收单集合为空!");
		}
		//来源单号不能为空
		if (CollectionUtils.isEmpty(billReceivableEntityListAll)) {
			//提示内部错误，应收单集合为空
			throw new SettlementException("内部错误，应收单集合为空!");
		}

		// 获取所选应收、预收单用客户编码
		String customerNo = billDepositReceivedEntityListFrom.get(0).getCustomerCode();

		/*
		 * 校验预收单是否可用于核销，
		 * billDepositReceivedEntityListFrom为界面所选择的待核销预收单集合,
		 * billDepositReceivedEntityListAll为数据库最新的待核销预收单集合
		 */
		validateBillDepositReceivedForWriteoff(customerNo,billDepositReceivedEntityListFrom,billDepositReceivedEntityListAll);

		/*
		 * 校验应收单是否可用于核销，
		 * billReceivableEntityListFrom为界面所选择的待核销应收单集合,
		 * billReceivableEntityListAll为数据库最新的待核销应收单集合
		 */
		validateBillReceivableForWirteoff(customerNo,billReceivableEntityListFrom, billReceivableEntityListAll);

		// 手动锁定应收单对应的运单
		List<MutexElement> mutexes = new ArrayList<MutexElement>();
		// 循环选中的应收单
		for (BillReceivableEntity entity : billReceivableEntityListFrom) {
			// 如果应收单的运单号不为空
			if (StringUtils.isNotEmpty(entity.getWaybillNo())&&!SettlementConstants.DEFAULT_BILL_NO.equals(entity.getWaybillNo())) {
				//生成业务互斥对象
				MutexElement mutexe = new MutexElement(entity.getWaybillNo(),"预收冲应收", MutexElementType.WAYBILL_NO);
				//并加入到对象列表
				mutexes.add(mutexe);
			}
		}
		// 批量锁定运单
		if(CollectionUtils.isNotEmpty(mutexes)){
			//锁定
			businessLockService.lock(mutexes,SettlementConstants.BUSINESS_LOCK_BATCH);
		}
		
		// 根据选中的预收单、应收单集合，调用公用核销接口，生成核销单
		BillWriteoffOperationDto billWriteoffOperationDtoRtn = addWriteoffBill(billDepositReceivedEntityListFrom,billReceivableEntityListFrom, cInfo);

		// 手动解锁应收单对应的运单
		if(CollectionUtils.isNotEmpty(mutexes)){
			 //解锁
			businessLockService.unlock(mutexes);
		}
		
		// 预收单核销结果同步到最新查询的数据库数据列表中
		billDepositReceivedEntityListAll.addAll(billWriteoffOperationDtoRtn.getBillDepositReceivedEntitys());
		// 应收单核销结果同步到最新查询的数据库数据列表中
		billReceivableEntityListAll.addAll(billWriteoffOperationDtoRtn.getBillReceivableEntitys());

		// 计算并设置应收、预收单列表的总金额
		BigDecimal totalAmountRec = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的未核销总金额
		BigDecimal unverifyTotalAmountRec = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的已核销总金额
		BigDecimal verifyTotalAmountRec = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的总金额
		BigDecimal totalAmountDep = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的未核销总金额
		BigDecimal unverifyTotalAmountDep = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的已核销总金额
		BigDecimal verifyTotalAmountDep = BigDecimal.ZERO;

		// 计算预收单列表的总条数、总金额、未核销总金额、已核销总金额
		for (BillDepositReceivedEntity entity : billDepositReceivedEntityListAll) {
			//总金额
			totalAmountDep = totalAmountDep.add(entity.getAmount());
			//未核销总金额
			unverifyTotalAmountDep = unverifyTotalAmountDep.add(entity.getUnverifyAmount());
			//已核销总金额
			verifyTotalAmountDep = verifyTotalAmountDep.add(entity.getVerifyAmount());
		}

		// 计算应收单列表的总条数、总金额、未核销总金额、已核销总金额
		for (BillReceivableEntity entity : billReceivableEntityListAll) {
			//总金额
			totalAmountRec = totalAmountRec.add(entity.getAmount());
			//未核销总金额
			unverifyTotalAmountRec = unverifyTotalAmountRec.add(entity.getUnverifyAmount());
			//已核销总金额
			verifyTotalAmountRec = verifyTotalAmountRec.add(entity.getVerifyAmount());
		}

		// 生成预收冲应收返回dto
		BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = new BillDepWriteoffBillRecDto();

		//设置预收冲应收返回dto预收单列表
		billDepWriteoffBillRecDto.setBillDepositreceivedEntityList(billDepositReceivedEntityListAll);
		//设置预收冲应收返回dto应收单列表
		billDepWriteoffBillRecDto.setBillReceivableEntityList(billReceivableEntityListAll);
		//设置预收冲应收返回dt应收单总条数
		billDepWriteoffBillRecDto.setTotalNumRec(billReceivableEntityListAll.size());
		//设置预收冲应收返回dto应收单总金额
		billDepWriteoffBillRecDto.setTotalAmountRec(totalAmountRec);
		//设置预收冲应收返回dto应收单未核销总金额
		billDepWriteoffBillRecDto.setUnverifyTotalAmountRec(unverifyTotalAmountRec);
		//设置预收冲应收返回dto应收单已核销总金额
		billDepWriteoffBillRecDto.setVerifyTotalAmountRec(verifyTotalAmountRec);
		//设置预收冲应收返回dto预收单总条数
		billDepWriteoffBillRecDto.setTotalNumDep(billDepositReceivedEntityListAll.size());
		//设置预收冲应收返回dto预收单总金额
		billDepWriteoffBillRecDto.setTotalAmountDep(totalAmountDep);
		//设置预收冲应收返回dto预收单未核销总金额
		billDepWriteoffBillRecDto.setUnverifyTotalAmountDep(unverifyTotalAmountDep);
		//设置预收冲应收返回dto预收单已核销总金额
		billDepWriteoffBillRecDto.setVerifyTotalAmountDep(verifyTotalAmountDep);
		
		//start add by ecs-269044-zhurongrong  2016-05-09 15:07
		//发生核销时，先判断该客户是否在灰名单中，在的话需判断是否拉出来，不在的话，直接pass
		GrayCustomerEntity entity = grayCustomerService.queryGrayCustomerByCustomerCode(customerNo);
		//存放待处理客户编码集合
		List<String> customerCodeList = new ArrayList<String>();
		//将客户编码添加到集合中
		customerCodeList.add(customerNo);
		if(entity!=null) {
			try{
				//调用判断时候修改灰名单接口
				grayCustomerService.updateGrayCustomerToECS(customerCodeList);
			} catch (Exception e) {
				//打印异常
				LOGGER.info("调用悟空更改灰名单接口异常" + e.getMessage());
			}
		}
		//end
		//返回预收冲应收返回dto
		return billDepWriteoffBillRecDto;
	}

	/**
	 * 刷新界面数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-29 上午10:15:57
	 */
	@Override
	public BillDepWriteoffBillRecDto reQueryBillDepAndBillRec(BillReceivableDto billReceivableDto,BillDepositReceivedDto billDepositReceivedDto) {
 
		//查询dto不能为空
		if (billReceivableDto == null) {
			//提示内部错误，应收单Dto集合为空
			throw new SettlementException("内部错误，应收单Dto集合为空！");
		}
		//查询dto不能为空
		if (billDepositReceivedDto == null) {
			//提示内部错误，预收单Dto集合为空
			throw new SettlementException("内部错误，预收单Dto集合为空！");
		}

		// 根据条件重新Load预收单的数据
		List<BillDepositReceivedEntity> billDepositReceivedEntityListAll = null;
		// 根据条件重新Load应收单的数据
		List<BillReceivableEntity> billReceivableEntityListAll = null;

		// 如果预收、应收单不为空，按单号查询;
		if (CollectionUtils.isNotEmpty(billReceivableDto.getReceivableNos())&& CollectionUtils.isNotEmpty(billDepositReceivedDto.getPrecollectedNos())) {
			//查询预收单
			billDepositReceivedEntityListAll = queryByDepositReceivedNOs(billDepositReceivedDto);
			//查询应收单
			billReceivableEntityListAll = queryByReceivableNOs(billReceivableDto);
		//否者按参数查询
		} else {
			//查询预收单
			billDepositReceivedEntityListAll = queryByDepositReceivedParams(billDepositReceivedDto);
			//查询应收单
			billReceivableEntityListAll = queryByReceivableParams(billReceivableDto);
		}

		// 计算并设置应收、预收单列表的总金额
		BigDecimal totalAmountRec = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的未核销总金额
		BigDecimal unverifyTotalAmountRec = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的已核销总金额
		BigDecimal verifyTotalAmountRec = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的总金额
		BigDecimal totalAmountDep = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的未核销总金额
		BigDecimal unverifyTotalAmountDep = BigDecimal.ZERO;
		// 计算并设置应收、预收单列表的已核销总金额
		BigDecimal verifyTotalAmountDep = BigDecimal.ZERO;

		// 计算预收单列表的总条数、总金额、未核销总金额、已核销总金额
		for (BillDepositReceivedEntity entity : billDepositReceivedEntityListAll) {
			//总金额
			totalAmountDep = totalAmountDep.add(entity.getAmount());
			//未核销总金额
			unverifyTotalAmountDep = unverifyTotalAmountDep.add(entity.getUnverifyAmount());
			//已核销总金额
			verifyTotalAmountDep = verifyTotalAmountDep.add(entity.getVerifyAmount());
		}

		// 计算应收单列表的总条数、总金额、未核销总金额、已核销总金额
		for (BillReceivableEntity entity : billReceivableEntityListAll) {
			//总金额
			totalAmountRec = totalAmountRec.add(entity.getAmount());
			//未核销总金额
			unverifyTotalAmountRec = unverifyTotalAmountRec.add(entity.getUnverifyAmount());
			//已核销总金额
			verifyTotalAmountRec = verifyTotalAmountRec.add(entity.getVerifyAmount());
		}

		// 生成预收冲应收返回dto
		BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = new BillDepWriteoffBillRecDto();

		//设置预收冲应收返回dto预收单列表
		billDepWriteoffBillRecDto.setBillDepositreceivedEntityList(billDepositReceivedEntityListAll);
		//设置预收冲应收返回dto应收单列表
		billDepWriteoffBillRecDto.setBillReceivableEntityList(billReceivableEntityListAll);
		//设置预收冲应收返回dt应收单总条数
		billDepWriteoffBillRecDto.setTotalNumRec(billReceivableEntityListAll.size());
		//设置预收冲应收返回dto应收单总金额
		billDepWriteoffBillRecDto.setTotalAmountRec(totalAmountRec);
		//设置预收冲应收返回dto应收单未核销总金额
		billDepWriteoffBillRecDto.setUnverifyTotalAmountRec(unverifyTotalAmountRec);
		//设置预收冲应收返回dto应收单已核销总金额
		billDepWriteoffBillRecDto.setVerifyTotalAmountRec(verifyTotalAmountRec);
		//设置预收冲应收返回dto预收单总条数
		billDepWriteoffBillRecDto.setTotalNumDep(billDepositReceivedEntityListAll.size());
		//设置预收冲应收返回dto预收单总金额
		billDepWriteoffBillRecDto.setTotalAmountDep(totalAmountDep);
		//设置预收冲应收返回dto预收单未核销总金额
		billDepWriteoffBillRecDto.setUnverifyTotalAmountDep(unverifyTotalAmountDep);
		//设置预收冲应收返回dto预收单已核销总金额
		billDepWriteoffBillRecDto.setVerifyTotalAmountDep(verifyTotalAmountDep);
		//返回预收冲应收返回dto
		return billDepWriteoffBillRecDto;
	}

	/**
	 * 根据预收单、应收单列表，调用接口生产核销单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-1 上午11:57:37
	 */
	private BillWriteoffOperationDto addWriteoffBill(List<BillDepositReceivedEntity> billDepositReceivedEntityListFrom,List<BillReceivableEntity> billReceivableEntityListFrom,CurrentInfo cInfo) {

		// 获取核销批次号
		String writeoffBillBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

		// 调用common统一的预收冲应收service
		//生成核销操作dto
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();
		//设置核销操作dto预收单列表
		billWriteoffOperationDto.setBillDepositReceivedEntitys(billDepositReceivedEntityListFrom);
		//设置核销操作dto应收单列表
		billWriteoffOperationDto.setBillReceivableEntitys(billReceivableEntityListFrom);
		//设置核销操作dto核销批次号
		billWriteoffOperationDto.setWriteoffBatchNo(writeoffBillBatchNo);
		//设置核销操作dto生产方式
		billWriteoffOperationDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		//生成核销操作返回dto
		BillWriteoffOperationDto rtnDto = new BillWriteoffOperationDto();

		// 调用通用的预收冲应收核销接口
		rtnDto = billWriteoffService.writeoffDepositAndReceivable(billWriteoffOperationDto, cInfo);

		// 核销完成，通知修改对账单及对账单明细信息
		StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
		//设置核销操作返回dto预收单列表参数
		statementOfAccountUpdateDto.setDepositReceivedEntityList(rtnDto.getBillDepositReceivedEntitys());
		//设置核销操作返回dto应收单列表参数
		statementOfAccountUpdateDto.setReceivableEntityList(rtnDto.getBillReceivableEntitys());
		//修改对账单及对账单明细信息
		statementOfAccountService.updateStatementAndDetailForWriteOff(statementOfAccountUpdateDto, cInfo);

		//返回核销操作返回dto
		return rtnDto;

	}

	/**
	 * 循环校验预收单的列表是否可以核销
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-1 上午11:42:50
	 */
	private void validateBillDepositReceivedForWriteoff(String customerNo,List<BillDepositReceivedEntity> billDepositReceivedEntityListFrom,List<BillDepositReceivedEntity> billDepositReceivedEntityListAll) {
		/*
		 * 循环预收单的列表，并进行验证：
		 *  1、选中预收数据的客户编码不能为空 
		 *  2、选中预收数据的客户编码必须一致 
		 *  3、选中预收单为有效时才能核销
		 *  4、选中预收单为非红单时才能核销 
		 *  5、选中预收单为未核销金额等于0时不能核销 
		 *  6、选中预收单为被别人部分核销的须重新核销
		 *  7、选择预收单已经确认对账单的不能进行核销（重新查询数据时，该步骤应过滤掉）
		 */
		//循环处理选择预收单列表
		for (BillDepositReceivedEntity entityFrom : billDepositReceivedEntityListFrom) {

			// 设置选中预收单是否在最新数据库查询结果只中的标准
			boolean existsFlag = false;
			//循环处理load预收单列表
			for (BillDepositReceivedEntity entity : billDepositReceivedEntityListAll) {
				//如果是同一条数据
				if (entityFrom.getId().equals(entity.getId())) {

					// 1、验证客户编码是否为空
					if (StringUtils.isEmpty(entityFrom.getCustomerCode())|| StringUtils.isEmpty(entity.getCustomerCode())) {
						//提示待核销预收单：XXX 客户编码为空
						throw new SettlementException("待核销预收单："+ entity.getDepositReceivedNo() + " 客户编码为空!");

						// 2、验证客户编码是否一致
					} else if (!entityFrom.getCustomerCode().equals(customerNo)) {
						//提示只有同一个客户的单据才能进行核销
						throw new SettlementException("只有同一个客户的单据才能进行核销!");
					}

					// 3、选中预收单有效时才能核销
					if (!entityFrom.getActive().equals(FossConstants.YES)|| !entity.getActive().equals(FossConstants.YES)) {
						//提示无效的预收单：XXXX 不能进行核销操作
						throw new SettlementException("无效的预收单："+ entity.getDepositReceivedNo() + " 不能进行核销操作!");
					}

					// 4、选中预收单为非红单时才能核销
					if (!entityFrom.getIsRedBack().equals(FossConstants.NO)|| !entity.getIsRedBack().equals(FossConstants.NO)) {
						//提示红单预收单：XXXX 不能进行核销操作
						throw new SettlementException("红单预收单："+ entity.getDepositReceivedNo() + " 不能进行核销操作!");
					}

					// 5、选中预收单为未核销金额等于0时不能核销
					if (entityFrom.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0|| entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
						//提示已核销的预收单：XXXX 不能进行核销操作
						throw new SettlementException("已核销的预收单："+ entity.getDepositReceivedNo() + " 不能进行核销操作!");
					}

					// 6、选中预收单已被被别人部分核销的须重新选择核销
					if (entityFrom.getUnverifyAmount().compareTo(entity.getUnverifyAmount()) != 0) {
						//提示已部分核销的预收单：XXXX 不能进行核销操作
						throw new SettlementException("已部分核销的预收单："+ entity.getDepositReceivedNo() + " 请重新选择核销!");
					}

					// 判断均通过，将最新的数据赋值给待核销的实体
					BeanUtils.copyProperties(entity, entityFrom);

					// 验证完成，去除本条待核销数据数据
					billDepositReceivedEntityListAll.remove(entity);
					
					//设置存在状态
					existsFlag = true;
					break;
				}
			}

			// 如果选中预收单是否在最新数据库查询结果只中，返回界面最新结果
			if (!existsFlag) {
				//提示已核销或确认对账的预收单不能进行核销操作,请重新选择核销
				throw new SettlementException("已核销或确认对账的预收单不能进行核销操作,请重新选择核销!");
			}
		}
	}

	/**
	 * 循环校验应收单的列表是否可以核销
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-1 上午11:42:50
	 */
	private void validateBillReceivableForWirteoff(String customerNo,
			List<BillReceivableEntity> billReceivableEntityListFrom,
			List<BillReceivableEntity> billReceivableEntityListAll) {
		/*
		 * 循环有应收单的列表，并进行验证： 
		 *  1、选中应收数据的客户编码不能为空
		 *  2、选中应收数据的客户编码必须一致
		 *  3、选中应收单为有效时才能核销
		 *  4、选中应收单为非红单时才能核销 
		 *  5、选中应收单为未核销金额等于0时不能核销
		 *  6、选中应收单为被别人部分核销的须重新核销
		 *  7、选择应收单已经确认对账单的不能进行核销(重新查询数据时，该步骤应过滤掉) 
		 *  8、选中应收单被网上支付锁定的不能进行核销
		 *  9、选中应收单存在未受理的更改单不能直接核销
		 */

		// 7.1、设置应收单号列表
		List<String> statementBillNoList = new ArrayList<String>();

		//循环处理选择的应收单
		for (BillReceivableEntity entityFrom : billReceivableEntityListFrom) {
			// 设置选中应收单是否在最新数据库查询结果只中的标准
			boolean existsFlag = false;
			//循环处理load的应收单列表
			for (BillReceivableEntity entity : billReceivableEntityListAll) {
				//如果是同一条数据
				if (entityFrom.getId().equals(entity.getId())) {
					// 1、验证客户编码是否为空
					if (StringUtils.isEmpty(entityFrom.getCustomerCode())|| StringUtils.isEmpty(entity.getCustomerCode())) {
						//提示待核销应收单: XXX 客户编码为空
						throw new SettlementException("待核销应收单: "+ entity.getReceivableNo() + " 客户编码为空!");

						// 2、验证客户编码是否一致
					} else if (!entityFrom.getCustomerCode().equals(customerNo)) {
						//提示只有同一个客户的单据才能进行核销
						throw new SettlementException("只有同一个客户的单据才能进行核销!");
					}

					// 3、选中应收单有效时才能核销
					if (!FossConstants.YES.equals(entityFrom.getActive())|| !FossConstants.YES.equals(entity.getActive())) {
						//提示无效的应收单: XXX 不能进行核销核销
						throw new SettlementException("无效的应收单: "+ entity.getReceivableNo() + " 不能进行核销操作!");
					}

					// 4、选中应收单为非红单时才能核销
					if (!FossConstants.NO.equals(entityFrom.getIsRedBack())|| !FossConstants.NO.equals(entity.getIsRedBack())) {
						//提示红单应收单: XXX 不能进行核销核销
						throw new SettlementException("红单应收单: "+ entity.getReceivableNo() + " 不能进行核销操作!");
					}

					// 5、选中应收单为未核销金额等于0时不能核销
					if (entityFrom.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0|| entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
						//提示已核销应收单: XXX 不能进行核销核销
						throw new SettlementException("已核销的应收单: "+ entity.getReceivableNo() + " 不能进行核销操作!");
					}

					// 6、选中应收单已被被别人部分核销的须重新选择核销
					if (entityFrom.getUnverifyAmount().compareTo(entity.getUnverifyAmount()) != 0) {
						//提示已部分核销应收单: XXX 不能进行核销核销
						throw new SettlementException("已部分核销的应收单: "+ entity.getReceivableNo() + " 请重新选择核销!");
					}

					// 8、选中应收单被网上支付锁定的不能进行核销
					if (entity.getUnlockDateTime() != null&& entity.getUnlockDateTime().after(new Date())) {
						//提示被网上支付锁定的应收单: XXX 不能进行核销核销
						throw new SettlementException("被网上支付锁定的应收单: "+ entity.getReceivableNo() + " 不能进行核销操作!");
					}

					// 9、选中应收单存在未受理的更改单不能直接核销
					if (StringUtils.isNotEmpty(entity.getWaybillNo())) {
						//检测是否存在未受理的更改单
						boolean isExsitsWayBillRfc = waybillRfcService.isExsitsWayBillRfc(entity.getWaybillNo());

						// 存在的话返回异常提示
						if (isExsitsWayBillRfc) {
							//提示存在未受理更改单的应收单: XXX 不能进行核销核销
							throw new SettlementException("存在未受理更改单的应收单: "+ entity.getReceivableNo() + " 不能进行核销核销!");
						}
					}

					// 7.2将验证通过的应收单的对账单号提取到statementBillNoList
					if (StringUtils.isNotEmpty(entity.getStatementBillNo())&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
						//将对账单号加入的对账单号列表
						statementBillNoList.add(entity.getStatementBillNo());
					}

					// 判断均通过，将最新的数据赋值给待核销的实体
					BeanUtils.copyProperties(entity, entityFrom);

					// 验证完成，去除本条待核销数据数据
					billReceivableEntityListAll.remove(entity);

					//修改存在状态
					existsFlag = true;
					break;
				}
			}

			// 如果选中预收单是否在最新数据库查询结果只中，返回界面最新结果
			if (!existsFlag) {
				//提示已核销或确认对账的应收单不能进行核销操作,请重新选择并核销
				throw new SettlementException("已核销、已确认对账单或存在更改单的应收单不能进行核销操作,请重新选择并核销!");
			}
		}

		// 7.3、调用对账单验证接口，返回已经做过确认对账单的单号
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//检测对账单号是否已确认，并返回未确认的对账单号
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}
		// 7.4、返回结果集数量大于0，提示确认对账单的应收单不能核销
		if (statementBillNoList.size() > 0) {
			//提示已确认对账单的应收单不能进行核销操作,请重新选择核销
			throw new SettlementException("已确认对账单的应收单不能进行核销操作,请重新选择核销!");
		}
	}

	/**
	 * @get
	 * @return writeoffBillDepositReceivedQueryDao
	 */
	public IWriteoffBillDepositReceivedQueryDao getWriteoffBillDepositReceivedQueryDao() {
		/*
		 * @get
		 * 
		 * @return writeoffBillDepositReceivedQueryDao
		 */
		return writeoffBillDepositReceivedQueryDao;
	}

	/**
	 * @set
	 * @param writeoffBillDepositReceivedQueryDao
	 */
	public void setWriteoffBillDepositReceivedQueryDao(
			IWriteoffBillDepositReceivedQueryDao writeoffBillDepositReceivedQueryDao) {
		/*
		 * @set
		 * 
		 * @this.writeoffBillDepositReceivedQueryDao =
		 * writeoffBillDepositReceivedQueryDao
		 */
		this.writeoffBillDepositReceivedQueryDao = writeoffBillDepositReceivedQueryDao;
	}

	/**
	 * @get
	 * @return writeoffBillReceivableQueryDao
	 */
	public IWriteoffBillReceivableQueryDao getWriteoffBillReceivableQueryDao() {
		/*
		 * @get
		 * 
		 * @return writeoffBillReceivableQueryDao
		 */
		return writeoffBillReceivableQueryDao;
	}

	/**
	 * @set
	 * @param writeoffBillReceivableQueryDao
	 */
	public void setWriteoffBillReceivableQueryDao(
			IWriteoffBillReceivableQueryDao writeoffBillReceivableQueryDao) {
		/*
		 * @set
		 * 
		 * @this.writeoffBillReceivableQueryDao = writeoffBillReceivableQueryDao
		 */
		this.writeoffBillReceivableQueryDao = writeoffBillReceivableQueryDao;
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
	
	/**
	 * @SET
	 * @param grayCustomerService
	 */
	public void setGrayCustomerService(IGrayCustomerService grayCustomerService) {
		this.grayCustomerService = grayCustomerService;
	}

}
