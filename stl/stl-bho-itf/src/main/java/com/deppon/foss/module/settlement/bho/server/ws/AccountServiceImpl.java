package com.deppon.foss.module.settlement.bho.server.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.accounting.AccountStatement;
import com.deppon.esb.inteface.domain.accounting.AccountStatementDetail;
import com.deppon.esb.inteface.domain.accounting.ApplyChangeOrderRequest;
import com.deppon.esb.inteface.domain.accounting.ApplyChangeOrderResponse;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementDetailRequest;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementDetailResponse;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementRequest;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementResponse;
import com.deppon.esb.inteface.domain.accounting.GetRefundRequest;
import com.deppon.esb.inteface.domain.accounting.GetRefundResponse;
import com.deppon.esb.inteface.domain.accounting.GetUnWriteoffReceivableBillRequest;
import com.deppon.esb.inteface.domain.accounting.GetUnWriteoffReceivableBillResponse;
import com.deppon.esb.inteface.domain.accounting.LockReceivableBillRequest;
import com.deppon.esb.inteface.domain.accounting.LockReceivableBillResponse;
import com.deppon.esb.inteface.domain.accounting.LockStatementOfAccountRequest;
import com.deppon.esb.inteface.domain.accounting.LockStatementOfAccountResponse;
import com.deppon.esb.inteface.domain.accounting.QueryAppWaybillInfosRequest;
import com.deppon.esb.inteface.domain.accounting.QueryAppWaybillInfosResponse;
import com.deppon.esb.inteface.domain.accounting.QueryEWaybillInfosRequest;
import com.deppon.esb.inteface.domain.accounting.QueryEWaybillInfosResponse;
import com.deppon.esb.inteface.domain.accounting.QueryExistWaybillNoInfoRequest;
import com.deppon.esb.inteface.domain.accounting.QueryExistWaybillNoInfoResponse;
import com.deppon.esb.inteface.domain.accounting.QueryFreightRouteInfoRequest;
import com.deppon.esb.inteface.domain.accounting.QueryFreightRouteInfoResponse;
import com.deppon.esb.inteface.domain.accounting.QueryWaybillInfosRequest;
import com.deppon.esb.inteface.domain.accounting.QueryWaybillInfosResponse;
import com.deppon.esb.inteface.domain.accounting.RefundInfo;
import com.deppon.esb.inteface.domain.accounting.RepaymentByReceivableBillRequest;
import com.deppon.esb.inteface.domain.accounting.RepaymentByReceivableBillResponse;
import com.deppon.esb.inteface.domain.accounting.RepaymentByStatementOfAccountRequest;
import com.deppon.esb.inteface.domain.accounting.RepaymentByStatementOfAccountResponse;
import com.deppon.esb.inteface.domain.basemessage.SimpleResponse;
import com.deppon.foss.accountservice.AccountService;
import com.deppon.foss.accountservice.CommonException;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailCountDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentReceiveBillService;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentStatementsService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOADOnlineResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultListDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.StatementOnlineQueryDto;

/**
 * 
 * 网上支付service
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-12-24 下午2:16:56
 */
public class AccountServiceImpl implements AccountService {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(AccountServiceImpl.class);

	/**
	 * 代收货款综合服务
	 */
	private ICodCommonService codCommonService;

	/**
	 * 网上支付：查询未核销的应收单服务
	 */
	private IOnlinePaymentReceiveBillService onlinePaymentReceiveBillService;

	/**
	 * 部门信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 对账单明细service
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 网上支付对账单service
	 */
	private IOnlinePaymentStatementsService onlinePaymentStatementsService;

	/**
	 * 根据对账单号查询对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-20 下午5:57:43
	 * @see com.deppon.foss.accountservice.AccountService#getAccountDetailInfo(com.deppon.esb.inteface.domain.accounting.GetAccountStatementDetailRequest)
	 */
	@Override
	public GetAccountStatementDetailResponse getAccountDetailInfo(Holder<ESBHeader> esbHeader,GetAccountStatementDetailRequest payload) throws CommonException {
		//发送esb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化返回实体
		GetAccountStatementDetailResponse response = new GetAccountStatementDetailResponse();
		try {
			// 判断输入参数是否为空
			if (payload != null) {
				//判空
				if (StringUtil.isNotEmpty(payload.getAccountStatementNo())) {
					//记录日志
					logger.info("entering web service, "+ payload.getAccountStatementNo());
				}
				// 设置查询查询对象
				StatementOnlineQueryDto queryDto = new StatementOnlineQueryDto();
				// 对账单号
				queryDto.setStatementBillNo(payload.getAccountStatementNo());
				// 起始页
				queryDto.setPageNo(payload.getPageNo());
				// 每页最大条数
				queryDto.setPageSize(payload.getPageSize());
				// 求明细总条数
				StatementOfAccountDetailCountDto countDto = statementOfAccountDService.queryCountByStatementBillNo(payload.getAccountStatementNo());
				//声明对账单明细列表
				List<StatementOfAccountDEntity> entitylist = new ArrayList<StatementOfAccountDEntity>();
				// 如果总条数大于0，则执行分页查询
				if (countDto.getCountNum() > 0) {
					// 分页查询对账单明细信息
					entitylist = statementOfAccountDService.queryByStatementBillNo(payload.getAccountStatementNo(),payload.getPageNo(), payload.getPageSize());
				}
				//声明实体
				AccountStatementDetail detailEntity = null;
				//判空
				if (CollectionUtils.isNotEmpty(entitylist)) {
					//循环处理
					for (StatementOfAccountDEntity entity : entitylist) {
						//获取明细实体
						detailEntity = new AccountStatementDetail();
						// 运单号、支付方式、收货人名称
						detailEntity.setWaybillNum(entity.getWaybillNo());
						//封装信息
						detailEntity.setPayType(entity.getPaymentType());
						//封装信息
						detailEntity.setConsignee(entity.getReceiveCustomerName());
						// 来源单号、记账日期、总金额、未核销金额、已核销金额
						detailEntity.setAccountStatementDetailNo(entity.getSourceBillNo());
						try {
							//封装记账日期
							detailEntity.setAccountDate(SettlementUtil.dateToXmlDate(entity.getAccountDate()));
						//异常处理
						} catch (Exception e) {
							//记录日志
							logger.error(e.getMessage(), e);
							//抛出异常
							throw new SettlementException("日期转换错误！", "");
						}
						//封装信息
						detailEntity.setTotalAmount(entity.getAmount());
						//原始未核销金额，如果为应收和预付，则返回正的金额，如果为应付和预收，则返回负的金额，方便官网显示
						if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(entity.getBillType())
								|| SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT.equals(entity.getBillType())) {
							detailEntity.setUnWriteoffAmount(entity.getUnverifyAmount());
						} else {
							detailEntity.setUnWriteoffAmount(entity.getUnverifyAmount().negate());
						}
						//封装信息
						detailEntity.setWriteoffAmount(entity.getVerifyAmount());
						// 根据部门编码查询部门信息
						OrgAdministrativeInfoEntity orgReceiveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getOrgCode());
						//判空
						if (orgReceiveEntity != null) {
							// 部门标杆编码
							detailEntity.setCollectionDeptId(orgReceiveEntity.getUnifiedCode());
						}
						// 部门名称
						detailEntity.setCollectionDeptName(entity.getOrgName());
						// 客户名称、客户编码、到达城市、货物名称
						detailEntity.setCustomerName(entity.getCustomerName());
						// 客户编码
						detailEntity.setCustomerId(entity.getCustomerCode());
						// 获取到达网点信息
						OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getDestOrgCode());
						//判空
						if (orgEntity != null) {
							// 到达城市
							detailEntity.setArriveCity(orgEntity.getCityName());
						}
						// 货物名称
						detailEntity.setCargoName(entity.getGoodsName());
						//添加明细
						response.getAccountStatementDetail().add(detailEntity);
					}
					// 设置总条数
					response.setCount(countDto.getCountNum());
				}
				//判空
				if (StringUtil.isNotEmpty(payload.getAccountStatementNo())) {
					//记录日志
					logger.info("entering web service, "	+ payload.getAccountStatementNo());
				}
			}
		//异常处理	
		} catch (SettlementException e) {
			throw new CommonException(e.getErrorCode(), e);
		}
		//返回结果
		return response;
	}

	/**
	 * 
	 * 根据运单号查询代收货款
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-12 下午7:50:41
	 * @see com.deppon.foss.accountservice.AccountService#getRefundInfo(com.deppon.esb.inteface.domain.accounting.GetRefundRequest)
	 */
	@Override
	public GetRefundResponse getRefundInfo(Holder<ESBHeader> esbHeader,GetRefundRequest payload) throws CommonException {
		//记录日志
		logger.info("Service getRefundInfo start ...");
		//返回esb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化返回实体
		GetRefundResponse response = new GetRefundResponse();
		try {
			//判空
			if (payload != null) {
				// 获取运单号集合、分页号、分页大小
				List<String> waybillNos = payload.getWayBillNo();
				//分页参数
				int pageNo = payload.getPageNo();
				//分页参数
				int limit = payload.getPageSize();
				// 如果运单号不为空
				if (CollectionUtils.isNotEmpty(waybillNos)) {
					// 如果分页号小于1，则查第一页
					if (pageNo < 1) {
						//设置分页为第一页
						pageNo = 1;
					}
					// 查询代收货款信息
					List<CODDto> queryResult = codCommonService.queryCODByWaybillNums(waybillNos, (pageNo - 1)* limit, limit);
					// 查询代收货款信息大小
					int size = codCommonService.queryCODByWaybillNumsSize(waybillNos);
					validaExtracted(response, queryResult);
					// 代收货款信息大小
					response.setCount(size);
				}
			}
		//异常处理
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			throw new CommonException(e.getMessage(), e);
		}
		//记录日志
		logger.info("Service getRefundInfo end ...");
		//返回信息
		return response;
	}

	private void validaExtracted(GetRefundResponse response,
			List<CODDto> queryResult) {
		//判空
		if (CollectionUtils.isNotEmpty(queryResult)) {
			//声明代收货款实体
			RefundInfo refund = null;
			//循环处理
			for (CODDto dto : queryResult) {
				// 构建返回值对象并设置值
				refund = new RefundInfo();
				// 运单号、发货人、收款人、银行账号、银行
				refund.setWayBillNo(dto.getWaybillNo());
				//设置值
				refund.setSender(dto.getPayableCustomerName());
				//设置值
				refund.setPayee(dto.getPayeeName());
				//设置值
				refund.setAccountNo(dto.getPayeeAccount());
				//设置值
				refund.setBankNo(dto.getBank());
				// 代收货款类型、代收货款金额、冲应收金额、应退金额、代收货款状态
				String codType = dto.getCodType();
				//类型转换
				int type = transferRefundType(codType);
				//判断类型
				if (type > 0) {
					//判断类型
					refund.setRefundType(String.valueOf(type));
				}
				//设置值
				refund.setColletionAmount(dto.getAmount());
				//设置值
				refund.setDeductionAmount(dto.getVerReceivableAmount());
				//设置值
				refund.setRefundAmount(dto.getReturnAmount());
				//获取转台
				String status = dto.getStatus();
				//类型转换
				int refundStatus = transferRefundStatus(status);
				//判断值
				if (refundStatus > 0) {
					//类型转换
					refund.setRefundStatus(String.valueOf(refundStatus));
				}
				// 加入结果集
				response.getRefundInfo().add(refund);
			}
		}
	}

	/**
	 * 
	 * 代收货款类型转换
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-13 上午9:48:45
	 */
	private int transferRefundType(String codType) {
		//公共未知类型
		int type = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_UNKNOWN;
		//判断即日退
		if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY.equals(codType)) {
			//即日退
			type = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_1_DAY;
		//判断3日退
		} else if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY.equals(codType)) {
			//3日退
			type = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_3_DAY;
		//判断审核退
		} else if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE.equals(codType)) {
			//审核退
			type = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_APPROVE;
		}
		//返回类型
		return type;
	}

	/**
	 * 
	 * 转换代收货款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-13 上午10:03:02
	 */
	private int transferRefundStatus(String status) {
		//代收货款状态
		int refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_TYPE__RETURN_UNKNOWN;
		// 未退款、待审核、营业部冻结、收银员审核、资金部冻结
		if (SettlementDictionaryConstants.COD__STATUS__NOT_RETURN.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__APPROVING.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__SHIPPER_FREEZE.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__CASHIER_APPROVE.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE.equals(status)) {
			//未退款
			refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_STATUS__NOT_RETURN;
		}

		// 退款中、退款失败申请、反汇款成功
		else if (SettlementDictionaryConstants.COD__STATUS__RETURNING.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION.equals(status)
				|| SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS.equals(status)) {
			// 退款中
			refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_STATUS__RETURNING;
		}
		// 已退款
		else if (SettlementDictionaryConstants.COD__STATUS__RETURNED.equals(status)) {
			// 已退款
			refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_STATUS__RETURNED;
		}

		// 退款失败
		else if (SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE.equals(status)) {
			// 退款失败
			refundStatus = SettlementESBDictionaryConstants.BHO__REFUND_STATUS__RETURN_FAILURE;
		}
		//返回
		return refundStatus;
	}

	/**
	 * 锁定应收单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 上午11:57:29
	 * @see com.deppon.foss.accountservice.AccountService#lockReceivableBill(com.deppon.esb.inteface.domain.accounting.LockReceivableBillRequest)
	 */
	@Override
	public LockReceivableBillResponse lockReceivableBill(Holder<ESBHeader> esbHeader, LockReceivableBillRequest payload)throws CommonException {
		logger.info("锁定应收单信息  begin....");
		//发送esb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化返回实体
		LockReceivableBillResponse response = new LockReceivableBillResponse();
		//实例化返回实体
		SimpleResponse simpleResponse = new SimpleResponse();
		try {
			//判空
			if (payload != null) {
				BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
				// 设置应收单单号
				queryDto.setReceivableNo(payload.getReceivableBillNo());
				// 客户编码、记账日期
				queryDto.setCustomerCode(payload.getCustNumber());
				//判空
				if (payload.getAccountDate() != null) {
					// 记账日期
					queryDto.setAccountDate(payload.getAccountDate().toGregorianCalendar().getTime());
				}
				// 锁定应收单
				onlinePaymentReceiveBillService.lockReceiveBillInfo(queryDto);
				//设置返回结果
				simpleResponse.setResult(true);
				//设置返回结果
				response.setResult(simpleResponse);
			}
		} catch (SettlementException e) {
			logger.info("锁定应收单信息异常：" + e.getErrorCode(), e);
			// 执行结果
			simpleResponse.setResult(false);
			// 失败原因
			simpleResponse.setReason(e.getErrorCode());
			//设置返回结果
			response.setResult(simpleResponse);
		} catch (Exception e){
			logger.info("锁定应收单信息异常：" + e.getMessage(), e);
			throw new CommonException("锁定应收单信息异常：" + e.getMessage(), e);
		}
		//返回信息
		logger.info("锁定应收单信息  end....");
		return response;
	}

	/**
	 * 查询未结清的对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 上午11:56:11
	 * @see com.deppon.foss.accountservice.AccountService#getAccountInfo(com.deppon.esb.inteface.domain.accounting.GetAccountStatementRequest)
	 */
	@Override
	public GetAccountStatementResponse getAccountInfo(Holder<ESBHeader> esbHeader, GetAccountStatementRequest payload)throws CommonException {
		//记录日志
		logger.info("查询未结清的对账单信息  begin....", "");
		//返回esb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//返回信息
		GetAccountStatementResponse response = new GetAccountStatementResponse();
		try {
			//判空
			if (payload != null) {
				//实例dto
				StatementOnlineQueryDto queryDto = new StatementOnlineQueryDto();
				// 设置客户编码
				queryDto.setCustomerCode(payload.getCustomerId());
				// 起始页
				queryDto.setPageNo(payload.getPageNo());
				// 每页最大条数
				queryDto.setPageSize(payload.getPageSize());
				//判空
				if (payload.getBeginDate() != null&& payload.getEndDate() != null) {
					// 开始日期
					queryDto.setBeginDate(payload.getBeginDate().toGregorianCalendar().getTime());
					// 结束日期
					queryDto.setEndDate(payload.getEndDate().toGregorianCalendar().getTime());
				}
				// 对账单号
				queryDto.setStatementBillNo(payload.getAccountStatementNo());
				//调用接口查询
				BillSOAOnlineResultListDto resultDto = onlinePaymentStatementsService.queryStatementOnline(queryDto);
				//获取结果集
				List<BillSOAOnlineResultDto> list = resultDto.getBillSOAOnlineResultDtoList();
				//判空
				if (CollectionUtils.isNotEmpty(list)) {
					//循环判断
					validaBillOnline(response, resultDto, list);
					//设置总条数
					response.setCount(resultDto.getCountNum());
				} else {
					//设置总条数
					resultDto.setCountNum(0);
				}
			}
			//异常处理
		} catch (SettlementException e) {
			throw new CommonException(e.getErrorCode(), e);
		}
		//记录日志
		logger.info("查询未结清的对账单信息  end....", "");
		//返回信息
		return response;
	}

	private void validaBillOnline(GetAccountStatementResponse response,
			BillSOAOnlineResultListDto resultDto,
			List<BillSOAOnlineResultDto> list) {
		AccountStatement accountStatement;
		for (BillSOAOnlineResultDto entity : list) {
			//实例化对账单实体
			accountStatement = new AccountStatement();
			// 设置对账单号
			if (entity != null&& StringUtil.isNotEmpty(entity.getStatementBillNo())) {
				//设置对账单号
				accountStatement.setAccountStatementNo(entity.getStatementBillNo());
				// 设置账期开始日期、结束日期、总金额、已还款金额、未还款金额
				if (entity.getPeriodBeginDate() != null&& entity.getPeriodEndDate() != null) {
					try {
						//设置开始时间
						accountStatement.setBeginDate(SettlementUtil.dateToXmlDate(entity.getPeriodBeginDate()));
						//设置结束时间
						accountStatement.setEndDate(SettlementUtil.dateToXmlDate(entity.getPeriodEndDate()));
					} catch (Exception e) {
						//异常出来记录日志
						logger.error(e.getMessage(), e);
						throw new SettlementException("日期转换错误", "");
					}
				}
				// 总金额
				accountStatement.setTotalAmount(entity.getPeriodAmount());
				// 已还款金额
				accountStatement.setUncollectedAmount(entity.getUnpaidAmount());
				// 未还款金额
				accountStatement.setCollectedAmount(entity.getPeriodAmount().subtract(entity.getUnpaidAmount()));
				// 根据部门编码查询部门信息
				OrgAdministrativeInfoEntity orgReceiveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getCreateOrgCode());
				//判空
				if (orgReceiveEntity != null) {
					// 设置部门标杆编码
					accountStatement.setCollectionDeptId(orgReceiveEntity.getUnifiedCode());
				}
				// 部门名称
				accountStatement.setCollectionDeptName(entity.getCreateOrgName());
				// 客户编码
				accountStatement.setCustomerId(entity.getCustomerCode());
				// 客户名称
				accountStatement.setCustomerName(entity.getCustomerName());
				// 包含运单条数
				accountStatement.setWayBillCount(entity.getCountDetailNum());
				//封装返回明细
				response.getAccountStatement().add(accountStatement);
			} else {
				//设置总条数
				resultDto.setCountNum(0);
			}
		}
	}

	/**
	 * 网上支付按对账单还款
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 上午11:57:58
	 * @see com.deppon.foss.accountservice.AccountService#repaymentByAccountInfo(com.deppon.esb.inteface.domain.accounting.RepaymentByStatementOfAccountRequest)
	 */
	@SuppressWarnings("finally")
	@Override
	public RepaymentByStatementOfAccountResponse repaymentByAccountInfo(Holder<ESBHeader> esbHeader,RepaymentByStatementOfAccountRequest payload)
			throws CommonException {
		//记录日志
		logger.info("网上支付按对账单还款  begin....", "");
		//返回esb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化返回实体
		RepaymentByStatementOfAccountResponse response = new RepaymentByStatementOfAccountResponse();
		//实例化返回实体
		SimpleResponse simpleResponse = new SimpleResponse();
		try {
			//判空
			if (payload != null) {
				//实例化对账单dto
				StatementOnlineQueryDto queryDto = new StatementOnlineQueryDto();
				// 设置对账单号
				queryDto.setStatementBillNo(payload.getStatementOfAccountNo());
				// 还款金额
				queryDto.setAmount(payload.getCollectionAmount());
				// 在线支付编号
				queryDto.setOnlineNo(payload.getOnlinePaymentNo());
				//调用接口查询
				onlinePaymentStatementsService.paymentStatementOnline(queryDto);
				//封装返回信息
				simpleResponse.setResult(true);
				//设置结果
				response.setResult(simpleResponse);
			}
		} catch (SettlementException e) {
			// 执行结果
			simpleResponse.setResult(false);
			// 失败原因
			simpleResponse.setReason(e.getErrorCode());
			//设置返回结果
			response.setResult(simpleResponse);

            logger.info("网上支付按对账单还款 异常"+e.getErrorCode());

        } catch(Exception e){
            // 执行结果
            simpleResponse.setResult(false);
            // 失败原因
            simpleResponse.setReason(e.getMessage());
            //设置返回结果
            response.setResult(simpleResponse);

            logger.error("网上支付按对账单还款 异常"+e.getMessage()+e.getStackTrace(),e);
        } finally {
			//记录日志
			//返回信息
            logger.info("网上支付按对账单还款 end");
            return response;
        }
    }

	/**
	 * 网上支付按应收单还款
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 上午11:58:59
	 * @see com.deppon.foss.accountservice.AccountService#repaymentByReceivableBill(com.deppon.esb.inteface.domain.accounting.RepaymentByReceivableBillRequest)
	 */
	@SuppressWarnings("finally")
	@Override
	public RepaymentByReceivableBillResponse repaymentByReceivableBill(Holder<ESBHeader> esbHeader,RepaymentByReceivableBillRequest payload) throws CommonException {
		//记录日志
		logger.info("网上支付按应收单还款  begin....", "");
		//返回esb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化返回实体
		RepaymentByReceivableBillResponse response = new RepaymentByReceivableBillResponse();
		//实例化返回实体
		SimpleResponse simpleResponse = new SimpleResponse();
		try {
			//判空
			if (payload != null) {
				//实例化实体
				BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
				// 设置应收单单号
				queryDto.setReceivableNo(payload.getReceivableBillNo());
				// 在线支付编号
				queryDto.setOnlineNo(payload.getOnlinePaymentNo());
				//判空
				if (payload.getAccountingDate() != null) {
					// 记账日期
					queryDto.setAccountDate(payload.getAccountingDate().toGregorianCalendar().getTime());
				} else {
					//封装返回信息
					simpleResponse.setResult(false);
					//封装返回信息
					simpleResponse.setReason("记账日期为空！");
					//封装返回信息
					response.setResult(simpleResponse);
				}
				// 金额
				queryDto.setAmount(payload.getCollectionAmount());
				//调用接口处理
				onlinePaymentReceiveBillService.paymentReceiveBillInfo(queryDto);
				//封装返回信息
				simpleResponse.setResult(true);
				//封装返回信息
				response.setResult(simpleResponse);
			}
		} catch (SettlementException e) {
			// 执行结果
			simpleResponse.setResult(false);
			// 失败原因
			simpleResponse.setReason(e.getErrorCode());
			//记录日志
			logger.error(e.getErrorCode(), e);
			//封装返回信息
			response.setResult(simpleResponse);
		} finally {
			//记录日志
			logger.info("网上支付按应收单还款  end....");
			return response;
		}
	}

	/**
	 * 锁定对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 上午11:56:48
	 * @see com.deppon.foss.accountservice.AccountService#lockAccountInfo(com.deppon.esb.inteface.domain.accounting.LockStatementOfAccountRequest)
	 */
	@Override
	public LockStatementOfAccountResponse lockAccountInfo(Holder<ESBHeader> esbHeader, LockStatementOfAccountRequest payload)
			throws CommonException {
		////记录日志
		logger.info("锁定对账单信息  begin....", "");
		//返回头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化返回信息实体
		LockStatementOfAccountResponse response = new LockStatementOfAccountResponse();
		//实例化返回信息实体
		SimpleResponse simpleResponse = new SimpleResponse();
		try {
			//判空
			if (payload != null) {
				//实例化dto实体
				StatementOnlineQueryDto queryDto = new StatementOnlineQueryDto();
				// 设置对账单号
				queryDto.setStatementBillNo(payload.getStatementOfAccountNo());
				// 调用锁定服务
				onlinePaymentStatementsService.lockStatementOnline(queryDto);
				//封装返回信息
				simpleResponse.setResult(true);
				//封装返回信息
				response.setResult(simpleResponse);
			}
		} catch (SettlementException e) {
			logger.error("锁定对账单信息异常：" + e.getErrorCode(),e);
			// 执行结果
			simpleResponse.setResult(false);
			// 失败原因
			simpleResponse.setReason(e.getErrorCode());
			//封装返回信息
			response.setResult(simpleResponse);
		} catch (Exception e){
			logger.error("锁定对账单信息异常：" + e.getMessage(), e);
			throw new CommonException("锁定对账单信息异常：" + e.getMessage(), e);
		}
		//记录日志
		logger.info("锁定对账单信息  end....", "");
		return response;
	}

	/**
	 * 官网查询未核销的应收单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 上午11:38:55
	 * @see com.deppon.foss.accountservice.AccountService#getUnWriteoffReceivableBillInfo(com.deppon.esb.inteface.domain.accounting.GetUnWriteoffReceivableBillRequest)
	 */
	@Override
	public GetUnWriteoffReceivableBillResponse getUnWriteoffReceivableBillInfo(
			Holder<ESBHeader> esbHeader,
			GetUnWriteoffReceivableBillRequest payload) throws CommonException {
		//记录日志
		logger.info("官网查询未核销的应收单信息  begin....", "");
		//返回esb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化返回实体
		GetUnWriteoffReceivableBillResponse response = new GetUnWriteoffReceivableBillResponse();
		try {
			//判空
			if (payload != null) {
				//实例化实体
				BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
				// 查询方式
				queryDto.setQueryType(payload.getSearchMethod());
				// 开始记录
				queryDto.setPageNo(payload.getPageNo());
				// 最大条数
				queryDto.setPageSize(payload.getPageSize());
				// 客户编码
				queryDto.setCustomerCode(payload.getCustomerId());
				// 运单号集合
				queryDto.setWayBillNos(payload.getWayBillNo());
				// 手机号码集合
				queryDto.setTelphoneNos(payload.getCellPhoneNo());
				if (payload.getBeginDate() != null&& payload.getEndDate() != null) {
					// 业务开始日期
					queryDto.setBeginDate(payload.getBeginDate().toGregorianCalendar().getTime());
					// 业务结束日期
					queryDto.setEndDate(payload.getEndDate().toGregorianCalendar().getTime());
				}
				// 货物名称
				queryDto.setCargoName(payload.getCargoName());
				// 收货人名称
				queryDto.setConsigneeName(payload.getConsigneeName());
				// 转换支付方式的值
				String payWay = transferPayWay(payload.getPayWay());
				// 付款方式
				queryDto.setPayWay(payWay);

				// 查询应收单信息
				BillSOADOnlineResultDto resultDto = onlinePaymentReceiveBillService.queryReceiveBillInfo(queryDto);
				//获取应收单列表
				List<BillReceivableEntity> receivableEntityList = resultDto.getBillReceivableEntityList();
				// 循环获取的应收单信息
				validaReceivable(response, receivableEntityList);
				// 设置总条数
				response.setCount(resultDto.getCountNum());
			}
		} catch (SettlementException e) {
			//异常处理			
			throw new CommonException(e.getErrorCode(), e);
		}
		//记录日志
		logger.info("官网查询未核销的应收单信息  end....", "");
		//返回信息
		return response;
	}

	private void validaReceivable(GetUnWriteoffReceivableBillResponse response,
			List<BillReceivableEntity> receivableEntityList) {
		if (CollectionUtils.isNotEmpty(receivableEntityList)) {
			// 结果集实体对象
			AccountStatementDetail detailEntity = null;
			//循环处理
			for (BillReceivableEntity entity : receivableEntityList) {
				//实例化明细实体
				detailEntity = new AccountStatementDetail();
				// 设置应收单号
				detailEntity.setAccountStatementDetailNo(entity.getReceivableNo());
				// 设置运单号
				detailEntity.setWaybillNum(entity.getWaybillNo());
				// 设置支付方式
				detailEntity.setPayType(entity.getPaymentType());
				try {
					// 业务日期
					detailEntity.setBusinessDate(SettlementUtil.dateToXmlDate(entity.getBusinessDate()));
					// 记账日期
					detailEntity.setAccountDate(SettlementUtil.dateToXmlDate(entity.getAccountDate()));
				} catch (Exception e) {
					//返回日志
					logger.error(e.getMessage(), e);
					throw new SettlementException("日期转换错误！", "");
				}
				// 总金额
				detailEntity.setTotalAmount(entity.getAmount());
				// 已核销金额
				detailEntity.setWriteoffAmount(entity.getVerifyAmount());
				// 未核销金额
				detailEntity.setUnWriteoffAmount(entity.getUnverifyAmount());
				// 根据部门编码查询部门信息
				OrgAdministrativeInfoEntity orgReceiveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getReceivableOrgCode());
				//收款部门
				if (orgReceiveEntity != null) {
					// 设置收款部门标杆编码
					detailEntity.setCollectionDeptId(orgReceiveEntity.getUnifiedCode());
				}
				// 收款部门名称
				detailEntity.setCollectionDeptName(entity.getReceivableOrgName());
				// 客户编码
				detailEntity.setCustomerId(entity.getCustomerCode());
				// 客户名称
				detailEntity.setCustomerName(entity.getCustomerName());
				// 获取到达网点信息
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getDestOrgCode());
				if (orgEntity != null) {
					// 到达部门城市
					detailEntity.setArriveCity(orgEntity.getCityName());
				}
				// 货物名称
				detailEntity.setCargoName(entity.getGoodsName());
				// 运单收货人
				detailEntity.setConsignee(entity.getReceiveCustomerName());
				//封装返回数据
				response.getUnWriteoffReceivableBill().add(detailEntity);
			}
		}
	}

	/**
	 * 
	 * 查询应收单信息时转换支付方式
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-1 上午9:56:08
	 */
	private String transferPayWay(String payWay) {
		//声明付款类型变量
		String payType = null;
		//如果为 到付，处理
		if (SettlementESBDictionaryConstants.BHO__PAYMENT_TYPE__FREIGHT_COLLECT.equals(payWay)) {
			// 到付
			payType = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT;
			//如果为 月结，处理
		} else if (SettlementESBDictionaryConstants.BHO__PAYMENT_TYPE__CREDIT.equals(payWay)) {
			// 月结
			payType = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT;
		//如果为  临欠，处理
		} else if (SettlementESBDictionaryConstants.BHO__PAYMENT_TYPE__DEBT.equals(payWay)) {
			// 临欠
			payType = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT;
		//如果为 网上支付，处理
		} else if (SettlementESBDictionaryConstants.BHO__PAYMENT_TYPE__ONLINE.equals(payWay)) {
			// 网上支付
			payType = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE;
		}
		//返回结果
		return payType;
	}

	/**
	 * 
	 * 查询运单信息（非结算接口）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-24 下午8:30:15
	 * @see com.deppon.foss.accountservice.AccountService#queryWaybillInfos(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.accounting.QueryWaybillInfosRequest)
	 */
	@Override
	public QueryWaybillInfosResponse queryWaybillInfos(Holder<ESBHeader> esbHeader, QueryWaybillInfosRequest request)
			throws CommonException {
		//返回esb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//返回结果
		return null;
	}

	/**
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * @param onlinePaymentReceiveBillService
	 */
	public void setOnlinePaymentReceiveBillService(
			IOnlinePaymentReceiveBillService onlinePaymentReceiveBillService) {
		this.onlinePaymentReceiveBillService = onlinePaymentReceiveBillService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param statementOfAccountDService
	 */
	public void setStatementOfAccountDService(
			IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}

	/**
	 * @param onlinePaymentStatementsService
	 */
	public void setOnlinePaymentStatementsService(
			IOnlinePaymentStatementsService onlinePaymentStatementsService) {
		this.onlinePaymentStatementsService = onlinePaymentStatementsService;
	}

	/**
	 * 申请变更订单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-14 上午11:09:00
	 * @param esbHeader
	 * @param request
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.accountservice.AccountService#applyChangeOrder(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.accounting.ApplyChangeOrderRequest)
	 */
	public ApplyChangeOrderResponse applyChangeOrder(
			Holder<ESBHeader> esbHeader, ApplyChangeOrderRequest request)
			throws CommonException {
		//
		
		// 待处理
		return null;
	}

	@Override
	public QueryFreightRouteInfoResponse queryFreightRouteInfo(
			Holder<ESBHeader> esbHeader, QueryFreightRouteInfoRequest request)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryExistWaybillNoInfoResponse queryExistWaybillNoInfo(
			Holder<ESBHeader> esbHeader, QueryExistWaybillNoInfoRequest request)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @author WangQianJin
	 * @date 2014-07-19
	 * APP查询我的发货单/收货单信息
	 * @param esbHeader
	 * @param request
	 * @return
	 * @throws CommonException
	 */
	@Override
	public QueryAppWaybillInfosResponse queryAppWaybillInfos(Holder<ESBHeader> esbHeader, QueryAppWaybillInfosRequest request)
			throws CommonException {
		return null;
	}

	/**
	 * @author BaiLei
	 * @date 2014-09-14
	 * 官网查询电子运单
	 * @param esbHeader
	 * @param request
	 * @return
	 * @throws CommonException
	 */
	@Override
	public QueryEWaybillInfosResponse queryEWaybillInfos(
			Holder<ESBHeader> esbHeader, QueryEWaybillInfosRequest request)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}


}
