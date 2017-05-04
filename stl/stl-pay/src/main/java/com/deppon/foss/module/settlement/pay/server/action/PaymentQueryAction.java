package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillPaymentVo;
import com.deppon.foss.util.DateUtils;

/**
 * 付款单action
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-19 上午10:23:46
 */
public class PaymentQueryAction extends AbstractAction {

	/**
	 * Logger
	 */
	private static Logger logger = LogManager
			.getLogger(PaymentQueryAction.class);

	/**
	 * 付款单action序列号
	 */
	private static final long serialVersionUID = 592011897815912961L;

	/**
	 * 付款单Vo
	 */
	private BillPaymentVo billPaymentVo = new BillPaymentVo();

	/**
	 * 查询付款单service
	 */
	private IPaymentQueryService paymentQueryService;

	/**
	 * 导出输出流
	 */
	private ByteArrayInputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	private ISettlementCommonService settlementCommonService;
	/**
	 * 注入FOSS综合包装供应商service
	 */
	private IPackagingSupplierService packagingSupplierService;


	/**
	 * 查询付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午10:46:28
	 */
	@JSON
	public String queryPaymentOrderBill() {
		logger.debug("查询付款单开始...");
		// 设置查询条件
		setQueryConditionForParam();
		// 执行付款单查询
		try {

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 创建返回dto
			BillPaymentResultDto rtnDto = paymentQueryService.queryPaymentBill(
					billPaymentVo.getBillPaymentQueryDto(), getStart(),
					getLimit(), cInfo);
			// 设置返回vo和分页总条数
			billPaymentVo.setBillPaymentResultDto(rtnDto);
			// 设置付款单的总条数
			this.setTotalCount(rtnDto.getPaymentTotalRows());
			// 返回至界面
			return returnSuccess();
		} catch (BusinessException be) {
			// 异常处理
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 查询付款单明细
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-4 下午2:30:08
	 */
	@JSON
	public String detailPayment() {

		try {
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 调用service,查询付款单及其应付、预收单明细信息
			BillPaymentResultDto billPaymentResultDto = paymentQueryService
					.queryPaymentBillDetial(
							billPaymentVo.getBillPaymentQueryDto(), start,
							limit, cInfo);
			billPaymentVo.setBillPaymentResultDto(billPaymentResultDto);
			return returnSuccess();
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 申请备用金工作流
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午10:46:28
	 */
	@JSON
	public String applySpareMoney() {

		try {
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用service,根据输入的银行帐号获取登录用户的银行帐号详细信息
			BillPaymentResultDto billPaymentResultDto = paymentQueryService
					.applySpareMoney(billPaymentVo.getBillPaymentQueryDto(),
							currentInfo);
			// 设置返回值
			billPaymentVo.setBillPaymentResultDto(billPaymentResultDto);
			return returnSuccess();
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 保存申请备用金工作流
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午10:46:28
	 */
	@JSON
	public String saveApplySpareMoney() {

		try {
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 调用service,进行申请备用金工作流处理
			paymentQueryService.saveApplySpareMoney(
					billPaymentVo.getBillPaymentQueryDto(), currentInfo);

			return returnSuccess();
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 审核付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午3:53:09
	 */
	@JSON
	public String aduitPayment() {

		try {
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 调用审核接口
			paymentQueryService.aduitPayment(
					billPaymentVo.getBillPaymentQueryDto(), currentInfo);

			return returnSuccess();
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 反审核付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午3:53:09
	 */
	@JSON
	public String revAduitPayment() {

		try {
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 调用反审核接口
			paymentQueryService.revAduitPayment(
					billPaymentVo.getBillPaymentQueryDto(), currentInfo);

			return returnSuccess();
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 作废付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午3:53:09
	 */
	@JSON
	public String disabledPayment() {

		try {
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用作废接口
			paymentQueryService.disabledPayment(
					billPaymentVo.getBillPaymentQueryDto(), currentInfo);
			return returnSuccess();
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 导出付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午3:53:09
	 */
	public String exportPaymentBillList() {
		try {
			logger.debug("导出付款单开始...");

			// 设置查询条件
			setQueryConditionForParam();

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 查询付款单
			BillPaymentResultDto dto = paymentQueryService
					.queryPaymentBillNotPage(
							billPaymentVo.getBillPaymentQueryDto(), cInfo);

			// 生成Excel代码
			try {
				// 设置Excel名称
				this.setExeclName(new String(("付款单报表")
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getMessage());
			}

			// 查询到的结果集不为空时，执行导出操作
			if (dto != null
					&& CollectionUtils.isNotEmpty(dto
							.getBillPaymentEntityList())) {

				if (dto.getBillPaymentEntityList().size() > SettlementConstants.EXPORT_MAX_COUNT) {
					throw new SettlementException("每次最多只能导出"
							+ SettlementConstants.EXPORT_MAX_COUNT
							+ "条数据,请重新查询并导出");
				}

				ExcelExport export = new ExcelExport();
				// 设置每次最多导出6万条数据
				HSSFWorkbook work = export.exportExcel(
						fillSheetDataByPayment(dto), "sheet",
						SettlementConstants.EXPORT_MAX_COUNT);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					work.write(baos);
					inputStream = new ByteArrayInputStream(baos.toByteArray());
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally {
					if (baos != null) {
						try {
							baos.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					}
				}
			} else {
				return returnError("导出数据为空！");
			}

			return returnSuccess();
		} catch (BusinessException be) {
			logger.error(be.getMessage(), be);
			return returnError(be);
		}
	}

	/**
	 * 生成付款单的excel
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-6 上午11:13:38
	 */
	private SheetData fillSheetDataByPayment(BillPaymentResultDto dto) {
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "单据编号", "工作流号", "审核状态", "版本号",
				"是否有效", "是否红单", "来源单据", "付款部门", "客户名称", "客户编码", "记账日期", "汇款状态",
				"付款金额", "生成方式", "创建时间", "创建人", "付款方式", "备注", "银行帐号", "账户类型",
				"开户行省份", "开户行城市", "开户银行", "开户行支行", "行号", "开户名" });

		List<BillPaymentEntity> lists = dto.getBillPaymentEntityList();

		// 因原来在循环里面调用速度比较慢，一次性把所有的缓存获取到
		// 生成待获取转换编码
		List<String> types = new ArrayList<String>();
		types.add(DictionaryConstants.BILL_PAYMENT__AUDIT_STATUS);// 审核状态
		types.add(DictionaryConstants.COD__PUBLIC_PRIVATE_FLAG);// 银行账户对公对私类型
		types.add(DictionaryConstants.FIN_ACCOUNT_TYPE);// 银行账户FIN类型
		types.add(DictionaryConstants.CRM_ACCOUNT_NATURE);// 银行账户CRM类型
		types.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);// 付款方式
		types.add(DictionaryConstants.SETTLEMENT__CREATE_TYPE);// 生成方式
		types.add(DictionaryConstants.BILL_PAYMENT__REMIT_STATUS);// 汇款状态
		types.add(DictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE);// 来源单据类型
		types.add(DictionaryConstants.SETTLEMENT__IS_RED_BACK);// 是否红单
		types.add(DictionaryConstants.FOSS_ACTIVE);// 是否有效
		// 获取全部待转换缓存
		Map<String, Map<String, Object>> map = SettlementUtil
				.getDataDictionaryByTermsCodes(types);

		if (CollectionUtils.isNotEmpty(lists)) {
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (BillPaymentEntity entity : lists) {
				List<String> cellList = new ArrayList<String>();
				cellList.add(entity.getPaymentNo());// 单据编号
				if (StringUtils.isNotEmpty(entity.getWorkflowNo())) {
					cellList.add(entity.getWorkflowNo());// 工作流号
				} else if (StringUtils.isEmpty(entity.getWorkflowNo())
						&& StringUtils.isNotEmpty(entity.getApplyWorkFlowNo())) {
					cellList.add(entity.getApplyWorkFlowNo());// 工作流号
				} else {
					cellList.add("");// 工作流号
				}

				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.BILL_PAYMENT__AUDIT_STATUS,
						entity.getAuditStatus()));
				cellList.add(entity.getVersionNo().toString());// 版本号
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.FOSS_ACTIVE, entity.getActive()));// 是否有效
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.SETTLEMENT__IS_RED_BACK,
						entity.getIsRedBack()));// 是否红单
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE,
						entity.getSourceBillType()));// 来源单据
				cellList.add(entity.getPaymentOrgName());// 付款部门
				cellList.add(entity.getCustomerName());// 客户名称
				cellList.add(entity.getCustomerCode());// 客户编码
				cellList.add(DateUtils.convert(entity.getAccountDate(),
						DateUtils.DATE_TIME_FORMAT));// 记账日期
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.BILL_PAYMENT__REMIT_STATUS,
						entity.getRemitStatus()));// 汇款状态
				if (entity.getAmount() != null) {
					cellList.add(entity.getAmount().toString());// 付款金额
				} else {
					cellList.add("");// 付款金额
				}
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.SETTLEMENT__CREATE_TYPE,
						entity.getCreateType()));// 生成方式
				cellList.add(DateUtils.convert(entity.getCreateTime(),
						DateUtils.DATE_TIME_FORMAT));// 创建时间
				cellList.add(entity.getCreateUserName());// 创建人
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
						DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
						entity.getPaymentType()));// 付款方式
				cellList.add(entity.getNotes());// 备注
				cellList.add(entity.getAccountNo());// 银行帐号

				// 账户类型
				String accountTypeName = "";
				if (StringUtils.isNotEmpty(entity.getAccountType())) {
					if (StringUtils.isEmpty(accountTypeName)) {
						accountTypeName = SettlementUtil
								.getDataDictionaryByTermsName(
										map,
										DictionaryConstants.COD__PUBLIC_PRIVATE_FLAG,
										entity.getAccountType());
					}
					if (StringUtils.isEmpty(accountTypeName)) {
						accountTypeName = SettlementUtil
								.getDataDictionaryByTermsName(map,
										DictionaryConstants.FIN_ACCOUNT_TYPE,
										entity.getAccountType());
					}
					if (StringUtils.isEmpty(accountTypeName)) {
						accountTypeName = SettlementUtil
								.getDataDictionaryByTermsName(map,
										DictionaryConstants.CRM_ACCOUNT_NATURE,
										entity.getAccountType());
					}
				}
				cellList.add(accountTypeName);// 账户类型
				cellList.add(entity.getProvinceName());// 开户行省份
				cellList.add(entity.getCityName());// 开户行城市
				cellList.add(entity.getBankHqName());// 开户银行
				cellList.add(entity.getBankBranchName());// 开户行支行
				cellList.add(entity.getBankBranchCode());// 行号
				cellList.add(entity.getPayeeName());// 开户名

				rowList.add(cellList);
			}
			sheetData.setRowList(rowList);
		}
		return sheetData;
	}

	/**
	 * 设置查询条件
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 下午2:04:11
	 */
	private void setQueryConditionForParam() {
		logger.debug("付款单查询，设置查询条件进入Action.....into......");
		// 设置执行时使用小于结束日期+1天
		// 付款单查询时，加入记账日期和业务日期不为空时，设置
		// 付款记账开始日期+1天和记账结束日期+1
		if (billPaymentVo.getBillPaymentQueryDto().getStartAccountDate() != null
				&& billPaymentVo.getBillPaymentQueryDto().getEndAccountDate() != null) {
			// 获取付款单记账日期并+1
			String dateEndTemp = DateUtils.addDay(billPaymentVo
					.getBillPaymentQueryDto().getEndAccountDate(), 1);
			Date dateEnd = DateUtils.convert(dateEndTemp);
			// 记账结束日期dto
			billPaymentVo.getBillPaymentQueryDto().setEndAccountDate(dateEnd);
		}

		// 付款单按照单号查询（运单和付款单号），处理单号集合
		if (billPaymentVo.getBillPaymentQueryDto().getBillNoArray() != null) {
			// 将界面输入单号数组转化成list
			List<String> billNos = Arrays.asList(billPaymentVo
					.getBillPaymentQueryDto().getBillNoArray());
			// 当单号不等于空值，进行判断是付款单或者是运单
			if (CollectionUtils.isNotEmpty(billNos)) {
				// 付款单list
				List<String> paymentNoList = new ArrayList<String>();
				// 运单list
				List<String> waybillNoList = new ArrayList<String>();
				// 进入循环，截取多个单号
				for (String billNo : billNos) {
					// 判断是否按照付款单查询
					if (billNo.trim().startsWith(
							SettlementConstants.BILL_PREFIX_FK)) {
						// 清除空，并加入付款单结合
						paymentNoList.add(billNo.trim());
					} else {
						// 否则按照运单号查询
						waybillNoList.add(billNo.trim());
					}
				}
				// 将查询结果添加至付款单集合
				billPaymentVo.getBillPaymentQueryDto().setPaymentNos(
						paymentNoList);
				// 将查询结果添加至运单号集合
				billPaymentVo.getBillPaymentQueryDto().setWaybillNos(
						waybillNoList);
			}

		}

		// 否则按照来源单号查询
		if (billPaymentVo.getBillPaymentQueryDto().getSourceBillNoArray() != null) {
			// 获取界面的来源单号结合，将数组转化成list
			List<String> sourceBillNos = Arrays.asList(billPaymentVo
					.getBillPaymentQueryDto().getSourceBillNoArray());
			// 当付款单号的结合不为空
			if (CollectionUtils.isNotEmpty(sourceBillNos)) {
				// 将来源单号结合添加至付款单集合
				billPaymentVo.getBillPaymentQueryDto().setSourceBillNos(
						sourceBillNos);
			}
		}
		// 否则按照工作流号查询
		if (billPaymentVo.getBillPaymentQueryDto().getWorkFlowNosArray() != null) {
			// 获取界面的工作流号，将数组转化成list
			List<String> workFlowNos = Arrays.asList(billPaymentVo
					.getBillPaymentQueryDto().getWorkFlowNosArray());
			// 当付款单号的结合不为空
			if (CollectionUtils.isNotEmpty(workFlowNos)) {
				// 将工作流号添加至付款单集合
				billPaymentVo.getBillPaymentQueryDto().setWorkFlowNos(
						workFlowNos);
			}
		}
		logger.debug("付款单查询，设置查询条件进入Action....exit.......");
	}

	/**
	 * 获取批次号和当前登录人
	 * 
	 * @author 邓大伟
	 * @date 2013-08-19
	 */
	public String uploadAppendix(){
		//获取对接系统参数
		DataDictionaryEntity dictEntity = DictUtil.getDataByTermsCode("SETTLEMENT__PAYTOSYSTEM_TYPE");
		List<DataDictionaryValueEntity> dataList = dictEntity.getDataDictionaryValueEntityList();
		try {
			//如果对接系统数据字典没配置，则抛出异常
			if(CollectionUtils.isEmpty(dataList)){
				throw new SettlementException("FOSS付款的付款工作流对接系统类型数据字典没配置，请去数据字典进行配置！");
			}
			//对接系统必须配置，且必须是1条 Y--财务共享，N--代表费控
			if(dataList.size()!=1){
				throw new SettlementException("付款工作流对接系统类型数据字典配置有误，必须只有1条。其中值为Y代表财务共享，N代表费控！");
			}
			
			//付款对接系统  Y--财务共享，N--代表费控 
			String payToSystem = dataList.get(0).getValueCode();
			//判空
			if(StringUtils.isEmpty(payToSystem)){
				throw new SettlementException("付款工作流对接系统类型数据字典配置有误，不能为空！值必须为Y或N。其中值为Y代表财务共享，N代表费控！");
			}
						
			/**
			 * @author chenzhuang  325369
			 * @date 2016-04-25
			 * 点击付款时根据包装部门名称和客户名称去包装供应商基础资料里检索并带出此供应商是否保理的字段信息
			 */
			if(!StringUtils.isBlank(billPaymentVo.getIsFactoring()) && "Y".equals(billPaymentVo.getIsFactoring())){
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				if(StringUtils.isBlank(billPaymentVo.getBillPaymentQueryDto().getCustomerCode())){
					throw new SettlementException("客户编码不能为空!");
				}
				if(StringUtils.isBlank(currentInfo.getCurrentDeptCode())){
					throw new SettlementException("包装部门编码不能为空!");
				}
				
				//  @author 379106  下方法被queryPackagingSupplierByCodes替代
				/*PackagingSupplierEntity packagingSupplierEntity = packagingSupplierService.queryPackagingSupplierByEntity(currentInfo.getCurrentDeptCode(), 
						billPaymentVo.getBillPaymentQueryDto().getCustomerCode());
				if(null == packagingSupplierEntity){
					throw new SettlementException("根据客户编码和包装部门编码未查询到包装供应商基础资料信息!");
				}*/
				
				if(StringUtils.isBlank(currentInfo.getCurrentDeptName())){
					throw new SettlementException("包装部门名称不能为空!");
				}
				
				if(StringUtils.isBlank(billPaymentVo.getBillPaymentQueryDto().getCustomerName())){
					throw new SettlementException("客户名称不能为空!");
				}	
				//根据客户编码 客户名称 包装部门名称  包装部门编码去查找包装供应商资料信息
				PackagingSupplierEntity packagingSupplierFactoringEntity=packagingSupplierService.queryPackagingSupplierByCodes(currentInfo.getCurrentDeptCode(),
						currentInfo.getCurrentDeptName(), billPaymentVo.getBillPaymentQueryDto().getCustomerCode(), billPaymentVo.getBillPaymentQueryDto().getCustomerName());
				
				if(null==packagingSupplierFactoringEntity){					
					throw new SettlementException("根据客户名称、客户编码、包装部门名称和部门编码未查询到包装供应商基础资料信息!");
				}
			    if(packagingSupplierFactoringEntity.getFactoring().equals("Y")){
				     billPaymentVo.getBillPaymentQueryDto().setFactoring(packagingSupplierFactoringEntity.getFactoring());
				     billPaymentVo.getBillPaymentQueryDto().setFactorBeginTime(packagingSupplierFactoringEntity.getFactorBeginTime());
				     billPaymentVo.getBillPaymentQueryDto().setFactorEndTime(packagingSupplierFactoringEntity.getFactorEndTime());
				     billPaymentVo.getBillPaymentQueryDto().setCusCode(packagingSupplierFactoringEntity.getCusCode());
				     billPaymentVo.getBillPaymentQueryDto().setFactorAccount(packagingSupplierFactoringEntity.getAccount());
				}else{
					billPaymentVo.getBillPaymentQueryDto().setFactoring("N");
				 }
			  
			}
         //if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC.equals(payToSystem)){
			String batchNo = null;
			if(null == billPaymentVo.getPaymentEntity().getBatchNo()
					|| "".equals(billPaymentVo.getPaymentEntity().getBatchNo())) {
				batchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK_BN);
			} else {
				batchNo = billPaymentVo.getPaymentEntity().getBatchNo();
			}
			String userCode = FossUserContext.getCurrentInfo().getEmpCode();
			billPaymentVo.getPaymentEntity().setBatchNo(batchNo);
			billPaymentVo.getPaymentEntity().setCreateUserCode(userCode);
//			}
			billPaymentVo.setPayToSystem(payToSystem);
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return returnError(e.toString());
		}
		return returnSuccess();
	}

	/**
	 * @return billPaymentVo
	 */
	public BillPaymentVo getBillPaymentVo() {
		return billPaymentVo;
	}

	/**
	 * @param billPaymentVo
	 */
	public void setBillPaymentVo(BillPaymentVo billPaymentVo) {
		this.billPaymentVo = billPaymentVo;
	}

	/**
	 * @param paymentQueryService
	 */
	public void setPaymentQueryService(IPaymentQueryService paymentQueryService) {
		this.paymentQueryService = paymentQueryService;
	}

	/**
	 * @return inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return execlName
	 */
	public String getExeclName() {
		return execlName;
	}

	/**
	 * @param execlName
	 */
	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setPackagingSupplierService(
			IPackagingSupplierService packagingSupplierService) {
		this.packagingSupplierService = packagingSupplierService;
	}

}
