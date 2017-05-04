package com.deppon.foss.module.settlement.financeitf.server.ws;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.ws.Holder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.finance.BillInfo;
import com.deppon.esb.inteface.domain.finance.BillInvoiceType;
import com.deppon.esb.inteface.domain.finance.QueryBadDebtsArInfoRequest;
import com.deppon.esb.inteface.domain.finance.QueryBadDebtsArInfoResponse;
import com.deppon.esb.inteface.domain.finance.QueryDebtMoneyRequest;
import com.deppon.esb.inteface.domain.finance.QueryDebtMoneyResponse;
import com.deppon.esb.inteface.domain.finance.QueryExpressDeptRequest;
import com.deppon.esb.inteface.domain.finance.QueryExpressDeptResponse;
import com.deppon.esb.inteface.domain.finance.QueryInvoiceAmountRequestType;
import com.deppon.esb.inteface.domain.finance.QueryInvoiceAmountResponseType;
import com.deppon.esb.inteface.domain.finance.QueryReceiptMoneyRequest;
import com.deppon.esb.inteface.domain.finance.QueryReceiptMoneyResponse;
import com.deppon.esb.inteface.domain.finance.QueryStatementRequest;
import com.deppon.esb.inteface.domain.finance.QueryStatementResponse;
import com.deppon.esb.inteface.domain.finance.ReceiptMoneyInfo;
import com.deppon.esb.inteface.domain.finance.StatementInfo;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.inteface.finmanager.CommonException;
import com.deppon.foss.inteface.finmanager.FossFinmanagerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.settlement.common.api.server.dao.IProductEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IInvoiceService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceDto;
import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInResultDto;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillBadAccountWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillBadAccountQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;
import com.deppon.foss.util.DateUtils;


/**
 * 财务自助WEB SERVICE服务端.
 * 
 * @author ibm-guxinhua
 * @date 2012-11-20 下午4:43:50
 */
public class FossFinmanagerServiceImpl implements FossFinmanagerService {

	/** 
	 * 日志. 
	 * */
	private static final Logger LOGGER = LoggerFactory.getLogger(FossFinmanagerService.class);

	/** 
	 * 注入开发发票服务.
	 */
	private IInvoiceService invoiceService;

	/** 
	 * 注入坏账核销服务. 
	 * */
	private IBillBadAccountWriteoffService billBadAccountWriteoffService;

	/** 
	 * 注入组织管理服务.
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/** 现金收入缴款报表管理service. */
	private IReportCashRecPayInService reportCashRecPayInService;
	
	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 制作对账单服务类接口
	 */
	private IStatementMakeService statementMakeService;
	
    /**
     * 产品类型Dao
     */
    private IProductEntityDao productEntityDao;

    /**
     * 开始时间和结束时间最大相差31天
     */
    private static final int MAX_DAYS = 31;

	/**
	 * 根据运单号获取应收单信息.
	 * FOSS_QUERY_BADDEBTSARINFO
	 * 查询坏账应收单信息
	 * @param esbHeader ：the esb header
	 * @param request：the request
	 * @return the query bad debts ar info response
	 * @throws CommonException：the common exception
	 * @author foss-wangxuemin
	 * @date 2012-12-25 下午4:48:57
	 */
	@Override
	public QueryBadDebtsArInfoResponse queryBadDebtsArInfo(
			Holder<ESBHeader> esbHeader, QueryBadDebtsArInfoRequest request)
			throws CommonException {
		//记录日志
		LOGGER.info("Web Service begin");
		// 请求参数不能为空
		if (request == null) {
			throw new CommonException("参数错误：QueryBadDebtsArInfoRequest为null！");
		}
		//记录日志
		LOGGER.info("QueryType:" + request.getQueryType());
		//记录日志
		LOGGER.info("BeginTime:" + request.getBeginTime());
		//记录日志
		LOGGER.info("EndTime:" + request.getEndTime());
		//记录日志
		LOGGER.info("CustomerCode:" + request.getCustomerCode());
		//记录日志
		if(CollectionUtils.isNotEmpty(request.getWayBillNum())){
			LOGGER.info("WayBillNum:" + request.getWayBillNum().toString());
		}

		// 设置查询Dto
		BillBadAccountQueryDto queryDto = new BillBadAccountQueryDto();
		//设置查询信息
		queryDto.setQueryType(request.getQueryType());
		//设置查询信息
		queryDto.setBeginTime(request.getBeginTime());
		//设置查询信息
		queryDto.setEndTime(request.getEndTime());
		//设置查询信息
		queryDto.setCustomerCode(request.getCustomerCode());
		//设置查询信息
		queryDto.setWayBillNum(request.getWayBillNum());
		//调用esb接口，返回esbHeader头
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化
		QueryBadDebtsArInfoResponse response = new QueryBadDebtsArInfoResponse();

		try {
			// 查询坏账待处理的应收单记录
			List<BillReceivableEntity> recs = billBadAccountWriteoffService
					.queryBadAccountReceivableList(queryDto);

			// 结果为空，抛出异常
			if (CollectionUtils.isEmpty(recs)) {
				throw new SettlementException("查询待坏账处理的应收记录为空;原因：运单未签收或存在未受理更改单或运单本不存在坏账应收");
			}
			//打印条数
			LOGGER.info("BadAccount Receivable records:" + recs.size());
			//循环处理
			for (BillReceivableEntity rec : recs) {
				BillInfo bill = new BillInfo();
				// 应收单号
				bill.setBillNumber(rec.getReceivableNo());
				// 运单号
				bill.setWayBillNumber(rec.getWaybillNo());
				// 获取发货部门标杆编码
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(rec.getReceivableOrgCode());
				//判空
				if (null == orgEntity) {
					//记录错误信息
					LOGGER.error("应收单" + rec.getReceivableNo() + "的部门"
							+ rec.getReceivableOrgCode() + "对应的标杆编码不存在");
				} else {
					//将部门编码设置给实体
					bill.setDeptNum(orgEntity.getUnifiedCode());
				}
				// 发货部门名称
				bill.setDeptName(rec.getReceivableOrgName());
				// 发货人:财务自助要求这个字段传客户名称
				bill.setSenderName(rec.getCustomerName());
				// 客户标杆编码
				bill.setCustDeptNumber(rec.getCustomerCode());
				// 总金额
				bill.setTotalMoney(rec.getAmount());
				// 已核销金额
				bill.setAppliedAmount(rec.getVerifyAmount());
				// 未核销金额
				bill.setUnappliedAmount(rec.getUnverifyAmount());
				// 业务日期
				bill.setBizDate(rec.getBusinessDate());

//				// 运输类型
//				if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
//						.equals(rec.getProductCode())) {
//					// 空运
//					bill.setTranType(SettlementESBDictionaryConstants.FINANCE_QUERYBADDEBT_TRANTYP_AIR);
//				} else {
//					// 汽运
//					bill.setTranType(SettlementESBDictionaryConstants.FINANCE_QUERYBADDEBT_TRANTYP_MOTOR);
//				}
				
				bill.setTranType(changeProductCodeToName(rec.getProductCode()));

				// 结算类型（单据子类型）
				bill.setSettleType(DictUtil.rendererSubmitToDisplay(
						rec.getBillType(),
						DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE));
				// 付款方式
				bill.setPaymentType(DictUtil.rendererSubmitToDisplay(
						rec.getPaymentType(),
						DictionaryConstants.SETTLEMENT__PAYMENT_TYPE));
				//将明细添加到明细列表中
				response.getBillInfo().add(bill);
			}
		} catch (BusinessException e) {
			//错误处理
			LOGGER.error(e.getErrorCode(), e);
			throw new CommonException(e.getErrorCode());
		}
		//记录结束日志
		LOGGER.info("Web Service end");
		//返回处理结果
		return response;
	}

	/**
	 * 获取运单可开发票金额信息.
	 * FOSS_QUERY_INVOICE_AMOUNT：获取运单可开发票金额信息 
	 *	接口名称	 获取运单可开发票金额信息
	 * @author ibm-guxinhua
	 * @date 2012-11-20 下午4:44:15
	 */
	@Override
	public QueryInvoiceAmountResponseType queryInvoiceAmount(
			QueryInvoiceAmountRequestType request, Holder<ESBHeader> esbHeader)
			throws CommonException {
		//调用esb接口，返回esbHeader头
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//记录日志
		LOGGER.info("Web Service start,request.getWaybillNO():"
				+ (request == null ? null : request.getWaybillNO()));
		//判空操作
		if (request == null) {
			throw new CommonException(
					"参数错误：QueryInvoiceAmountRequestType为null！");
		}
		//获取运单号集合
		List<String> waybillNoList = request.getWaybillNO();

		// 判断运单号是否为空
		if (CollectionUtils.isEmpty(waybillNoList)) {
			throw new CommonException("运单号为空，无法查询运单信息");
		}
		//申请发票列表
		List<InvoiceDto> invoiceDtoList = null;

		try {
			// 查询运单的可开票金额
			invoiceDtoList = invoiceService.queryWaybillAmount(waybillNoList);
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getErrorCode(), e);
			throw new CommonException(e.getErrorCode());
		}

		// 设置response
		QueryInvoiceAmountResponseType response = new QueryInvoiceAmountResponseType();
		//判空
		if (CollectionUtils.isNotEmpty(invoiceDtoList)) {
			//声明发票实体
			BillInvoiceType invoice = null;
			//循环发票列表
			for (InvoiceDto dto : invoiceDtoList) {
				//封装信息
				invoice = new BillInvoiceType();
				//封装信息
				invoice.setWaybillNO(dto.getWaybillNo());
				//封装信息
				invoice.setBillAmount(dto.getTotalAmount());
				//封装运输性质
				invoice.setTransportProperty(changeProductCodeToName(dto.getProductCode()));
				//封装发票标记和收货部门
				if(dto.getInvoiceMark().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE)) {
					invoice.setBillFlag("01");
				} else if (dto.getInvoiceMark().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO)) {
					invoice.setBillFlag("02");
				} else {
					throw new CommonException("未识别的发票标记！");
				}
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(dto.getReceviceOrgCode());
				if(orgEntity == null) {
					throw new CommonException("未找到次部门");
				}
				invoice.setBillDept(orgEntity.getUnifiedCode());
				//将发票结果放到返回明细中
				response.getBillInvoices().add(invoice);
			}
		}
		//记录结束日志
		LOGGER.info("Web Service end");
		//返回
		return response;
	}


	/**
	 * 获取小票金额信息.
	 * 接口名称：
	 * 	FOSS_QUERY_LITTLETICKETAMOUNT：获取小票金额
	 * 接口名称	获取小票金额
	 * @author ibm-guxinhua
	 * @date 2012-11-28 下午5:29:38
	 */
	@Override
	public QueryReceiptMoneyResponse queryReceiptMoney(
			Holder<ESBHeader> esbHeader, QueryReceiptMoneyRequest request)
			throws CommonException {
		//调用esb接口，返回esbHeader头
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//记录日志
		LOGGER.info("Web Service start,request.getReceiptNo():"
				+ (request == null ? null : request.getReceiptNo()));
		//判空
		if (request == null) {
			throw new CommonException("参数错误：QueryReceiptMoneyRequest为null！");
		}
		//获取编号
		List<String> receiptNoList = request.getReceiptNo();
		// 判断小票号是否为空
		if (CollectionUtils.isEmpty(receiptNoList)) {
			throw new CommonException("小票号为空，无法查询小票信息");
		}
		//声明发票类表
		List<InvoiceDto> invoiceDtoList = null;
		try {
			// 查询小票开票金额
			invoiceDtoList = invoiceService
					.queryOtherRevenueAmount(receiptNoList);
		//异常处理
		} catch (BusinessException e) {
			//异常记录
			LOGGER.error(e.getErrorCode(), e);
		}

		// 设置response
		QueryReceiptMoneyResponse response = new QueryReceiptMoneyResponse();
		//判空
		if (CollectionUtils.isNotEmpty(invoiceDtoList)) {
			//声明实体
			ReceiptMoneyInfo receipt = null;
			//循环处理
			for (InvoiceDto dto : invoiceDtoList) {
				//封装明细实体
				receipt = new ReceiptMoneyInfo();
				//设置属性
				receipt.setReceiptNo(dto.getOtherRevenueNo());
				//设置属性
				receipt.setReceiptMoney(dto.getTotalAmount());
				//设置产品类型
				receipt.setTransportProperty(changeProductCodeToName(dto.getProductCode()));
				//发票标记
				if(dto.getInvoiceMark().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE)) {
					receipt.setBillFlag("01");
				} else if (dto.getInvoiceMark().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO)) {
					receipt.setBillFlag("02");
				} else {
					throw new CommonException("未识别的发票标记！");
				}
				
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(dto.getReceviceOrgCode());
				if(orgEntity == null) {
					throw new CommonException("未找到次部门");
				}
				receipt.setBillDept(orgEntity.getUnifiedCode());
				
				//将明细放置到返回结果中去
				response.getReceiptMoneyInfos().add(receipt);
			}
		}
		//记录处理结束日志
		LOGGER.info("Web Service end");
		//返回
		return response;
	}

	/**
	 * 查询部门累计未交帐(营业款金额、预收款金额).
	 * @author 095793-foss-LiQin
	 * @date 2012-12-19 上午11:29:04
	 */
	@Override
	public QueryDebtMoneyResponse queryDebtMoney(Holder<ESBHeader> esbHeader,
			QueryDebtMoneyRequest parameters) throws CommonException {

		/**
		 * 1.查询部门累计未交账金额前，先重新按部门生成现金收入（缴款）报表 2.现金收入（缴款）报表生成完后，查询部门累计未交帐营业款、预收款
		 */
		LOGGER.info(" 查询部门累计未交帐(营业款金额、预收款金额). into");
		
		//调用esb接口，返回esbHeader头
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		// 部门标杆编码
		String unifiedCode = parameters.getDeptBenchmarkCode();
		//判空
		if (StringUtil.isEmpty(unifiedCode)) {
			throw new CommonException("部门编码不能为空!");
		}
		//记录日志
		LOGGER.info("部门标杆编号:" + unifiedCode);
		//声明变量
		QueryDebtMoneyResponse bebtDebtMoneyResponse = null;
		try {
			// 根据财务自助标杆部门编码综合的部门编码编码查询
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(unifiedCode);
			//判空
			if (null == orgEntity) {
				//异常处理
				LOGGER.error("根据标杆编码："+unifiedCode + "查找Foss中的部门编码未找到！");
				throw new CommonException("根据标杆编码："+unifiedCode + "查找Foss中的部门编码未找到！");
			}
			// 根据标杆部门获取当前部门
			String deptNo =orgEntity.getCode();
			//记录日志
			LOGGER.info("根据标杆编码："+unifiedCode + "查找Foss中的部门编码:"+deptNo);
			/**
			 * 1、根据单个网点，调用存储过程生成现金收入（缴款）报表
			 */
			// 当前日期
			Date now = new Date();
			// 获取当前时间精确时间
			Date currentTime = getNowTime();
			
			
			
			//添加互斥锁收集数据
			List<MutexElement> mutexElements = new ArrayList<MutexElement>();
			// 业务互锁运单号
			MutexElement mutexElement = new MutexElement(deptNo, "按部门生成缴款报表时,锁定部门", MutexElementType.CASH_IN_COME_LOCK_DEPT_CODE);
			//加入互斥对象集合
			mutexElements.add(mutexElement);
			//添加互斥锁
			if(CollectionUtils.isNotEmpty(mutexElements)){
				//锁定
				if(!businessLockService.lock(mutexElements, SettlementConstants.BUSINESS_LOCK_BATCH)) {
					throw new SettlementException("资源已被占用，请稍后再操作！");
				}
			}
			
			
			//记录日志
			LOGGER.info("调用存储过程，按部门生成现金收入（缴款）金额");
			//调用接口处理
			reportCashRecPayInService.createOneReportCashRecPayIn(
					SettlementConstants.CASH_BEGIN_DATE, now, deptNo,
				    currentTime);
			//记录日志
			LOGGER.info("结束调用");
			
			//去除互斥锁
			if(CollectionUtils.isNotEmpty(mutexElements)){
				//解锁
				businessLockService.unlock(mutexElements);
			}
			
			
			/**
			 * 2、查询部门累计未交款营业款预收款
			 */
			CashCollectionRptEntity entity = new CashCollectionRptEntity();
			// 设置查询编码为组织编码编号
			entity.setOrgCode(deptNo);
			//调用接口查询
			BillCashRecPayInResultDto dto = reportCashRecPayInService.queryCashinComerptDebtAmount(entity);
			//记录日志
			LOGGER.info("累计现金营业款金额:" + dto.getTotalClerksAmount() + "累计预收款总金额:"+ dto.getTotalPrecollectedAmount());

			// 累计现金营业款金额、累计预收款总金额
			bebtDebtMoneyResponse = new QueryDebtMoneyResponse();
			//设置属性
			bebtDebtMoneyResponse.setAdvanceMoney(dto.getTotalPrecollectedAmount());
			//设置属性
			bebtDebtMoneyResponse.setBusinessMoney(dto.getTotalClerksAmount());
			//记录日志
			LOGGER.info("queryDebtMoneyRequest success exit...");
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error("查询部门累计未交帐(营业款金额、预收款金额)" + e.getErrorCode(), e);
			throw new CommonException("查询部门累计未交帐(营业款金额、预收款金额)"+e.getErrorCode(),e);
		}
		// 接口返回金额
		return bebtDebtMoneyResponse;
	}

	/**
	 * 获取当前时间.
	 * @return the now time
	 * @author 095793-foss-LiQin
	 * @date 2012-11-30 下午3:51:16
	 */
	private Date getNowTime() {
		// 获取当前精确时间
		return org.apache.commons.lang.time.DateUtils.truncate(Calendar
				.getInstance().getTime(), Calendar.SECOND);
	}

	/**
	 * 运输类型编码转化.
	 *
	 * @param productCode the product code
	 * @return the string
	 * @author huanglewei
	 * @date 2015-09-09 下午7:31:42
	 */
	private String changeProductCodeToName(String productCode) {
		//根据产品类型查询
		String productName = productEntityDao.queryProductNameByCode(productCode);
		if (StringUtils.isNotBlank(productName)) { //如果productName不为null或空
			return productName;
		}else if(StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD)){
			return "单独开单";
		}else if(StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP)){
			return "合大票";
		}
		//都不符合则返回未知
		else {
			return "未知运输类型";
		}
	}
	
	/**
	 * 财务自助查询对账单
	 * @author ddw
	 * @date 2014-08-19
	 */
	@Override
	public QueryStatementResponse queryStatementInfo(Holder<ESBHeader> esbHeader, QueryStatementRequest parameters) throws CommonException {
		//记录日志
		LOGGER.info("查询对账单信息开始");
		//判断参数是否为空
		if(null == parameters){
			throw new CommonException("财务自助传输参数异常!");
		}
		//记录日志
		LOGGER.info("CreateDeptNo:" + parameters.getCreateDeptNo());
		//记录日志
		LOGGER.info("CreateBeginTime:" + parameters.getCreateBeginTime());
		//记录日志
		LOGGER.info("CreateEndTime:" + parameters.getCreateEndTime());
		//记录日志
		LOGGER.info("CustomerCode:" + parameters.getCustomerCode());
		//记录日志
		LOGGER.info("CustomerName:" + parameters.getCustomerName());
		//记录日志
		LOGGER.info("StatementNo:" + parameters.getStatementNo());
		StatementOfAccountMakeQueryDto statementQueryDto = new StatementOfAccountMakeQueryDto();
		if(StringUtils.isEmpty(parameters.getStatementNo())){
			//判断部门编码是否为空
			if(StringUtils.isEmpty(parameters.getCreateDeptNo())){
				throw new CommonException("财务自助传输参数异常,部门标杆编码为空!");
			}
			if(null != parameters.getCreateBeginTime() && null != parameters.getCreateEndTime()){
				if(DateUtils.getTimeDiff(parameters.getCreateBeginTime(), parameters.getCreateEndTime()) > MAX_DAYS){
					throw new CommonException("财务自助传输参数异常,开始时间和结束时间不能相差大于31天!");
				}
			}else{
				throw new CommonException("财务自助传输参数异常,开始时间或结束时间不能为空!");
			}
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(parameters.getCreateDeptNo());
			statementQueryDto.setPeriodBeginDate(parameters.getCreateBeginTime());
			statementQueryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(parameters.getCreateEndTime(), 1)));
			statementQueryDto.setOrgCode(orgEntity.getCode());
			statementQueryDto.setCustomerCode(parameters.getCustomerCode());
			statementQueryDto.setCustomerName(parameters.getCustomerName());
			statementQueryDto.setStatementBillNo(null);
			statementQueryDto.setQueryPage(SettlementConstants.TAB_QUERY_BY_DATE);
		}else{
			statementQueryDto.setPeriodBeginDate(null);
			statementQueryDto.setPeriodEndDate(null);
			statementQueryDto.setOrgCode(null);
			statementQueryDto.setCustomerCode(null);
			statementQueryDto.setCustomerName(null);
			statementQueryDto.setStatementBillNo(parameters.getStatementNo());
		}
		//调用esb接口，返回esbHeader头
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		//实例化
		QueryStatementResponse response = new QueryStatementResponse();
		List<StatementOfAccountEntity> statementBillList = statementMakeService.queryStatementBIllEntity(statementQueryDto);
		// 结果为空，抛出异常
		if (CollectionUtils.isEmpty(statementBillList)) {
			throw new CommonException("没有查询到对账单信息，请确定查询条件是否正确！");
		}
		try{
			//循环处理
			for (StatementOfAccountEntity statement : statementBillList) {
				StatementInfo bill = new StatementInfo();
				bill.setCreateTime(statement.getCreateTime());
				bill.setCustomerCode(statement.getCustomerCode());
				bill.setCustomerName(statement.getCustomerName());
				bill.setStatementNo(statement.getStatementBillNo());
				//将明细添加到明细列表中
				response.getStatementList().add(bill);
			}
		} catch (BusinessException e) {
			//错误处理
			LOGGER.error(e.getErrorCode(), e);
			throw new CommonException(e.getErrorCode());
		}
		//记录结束日志
		LOGGER.info("查询对账单信息结束");
		return response;
	}
	
	/**
	 * Sets the report cash rec pay in service.
	 * 
	 * @param reportCashRecPayInService
	 *            the new report cash rec pay in service
	 */
	public void setReportCashRecPayInService(
			IReportCashRecPayInService reportCashRecPayInService) {
		this.reportCashRecPayInService = reportCashRecPayInService;
	}

	/**
	 * Sets the invoice service.
	 * 
	 * @param invoiceService
	 *            the new invoice service
	 */
	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	/**
	 * Sets the bill bad account writeoff service.
	 * 
	 * @param billBadAccountWriteoffService
	 *            the new bill bad account writeoff service
	 */
	public void setBillBadAccountWriteoffService(
			IBillBadAccountWriteoffService billBadAccountWriteoffService) {
		this.billBadAccountWriteoffService = billBadAccountWriteoffService;
	}

	/**
	 * Sets the org administrative info service.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setStatementMakeService(IStatementMakeService statementMakeService) {
		this.statementMakeService = statementMakeService;
	}

	public IProductEntityDao getProductEntityDao() {
		return productEntityDao;
	}

	public void setProductEntityDao(IProductEntityDao productEntityDao) {
		this.productEntityDao = productEntityDao;
	}
	
	@Override
	public QueryExpressDeptResponse queryExpressDept(Holder<ESBHeader> arg0,
			QueryExpressDeptRequest arg1) throws CommonException {
		return null;
	}

}
