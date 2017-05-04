package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODFailService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AuditResultEnum;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODVo;

/**
 * 汇代收货款失败处理层.
 *
 * @author dp-zengbinwen
 * @date 2012-10-18 下午3:05:24
 */
public class CODPayFailedAction extends AbstractAction {

	/** The Constant logger. */
	private static final Logger logger = LogManager
			.getLogger(CODPayFailedAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3741397613432644713L;

	/** 前台页面查询条件值，查询所有. */
	private static final String QUERY_CONDITION_ALL = "all";

	/** 前台页面查询条件值，按运单号查询. */
	private static final String QUERY_CONDITION_WAYBILL = "waybill";

	/** 代收货款汇款失败审核确认服务. */
	private IBillPayCODFailService billPayCODFailService;

	/** 代收货款服务. */
	private ICodCommonService codCommonService;

	/** 汇款失败申请VO. */
	private CODVo codPayFailedVO;

	/** 选中代收货款ID. */
	private String[] codIds;

	/** 审核结果. */
	private String auditResult;
	
	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 查询汇款失败.
	 *
	 * @return the string
	 * @author dp-zengbinwen
	 * @date 2012-10-18 下午3:10:37
	 */
	@JSON
	public String queryPayFailed() {

		try {
			if (codPayFailedVO != null) {

				List<String> waybillNos = null;

				String queryConditon = codPayFailedVO.getQueryCondition();
				// 查询所有
				if (QUERY_CONDITION_ALL.equals(queryConditon)
						|| QUERY_CONDITION_WAYBILL.equals(queryConditon)) {

					// 如果按单号查询
					if (QUERY_CONDITION_WAYBILL.equals(queryConditon)) {

						String waybillNoStr = codPayFailedVO.getWaybillNos();
						if (!StringUtils.isEmpty(waybillNoStr)) {

							// 增加单号
							waybillNos = new ArrayList<String>();
							String[] waybillNoArray = waybillNoStr.split(",");
							for (String waybillNo : waybillNoArray) {
								waybillNos.add(waybillNo);
							}

						}else{
							throw new SettlementException("单号不能为空");
						}
					}
				} else {
					throw new SettlementException("未提供的查询条件");
				}

				// 查询结果
				List<CODDto> payFailedCOD = null;
				int payFailedCODTotalRecords = 0;
				// 合计金额
				BigDecimal totalAmount = BigDecimal.ZERO;

				// 如果运单号不为空，则按运单号查询
				if (CollectionUtils.isNotEmpty(waybillNos)) {

					// 退款失败申请、反汇款成功
					List<String> status = new ArrayList<String>();
					status.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION);
					status.add(SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS);

					// 查询结果和结果大小
					payFailedCOD = codCommonService
							.queryBillOfflineCODByWaybillNo(waybillNos, status);

					if (CollectionUtils.isNotEmpty(payFailedCOD)) {
						payFailedCODTotalRecords = payFailedCOD.size();
						totalAmount = this.totalCODAmount(payFailedCOD);
					}

				}

				// 运单号为空，按其它条件查询
				else {
					// 查询数据和数据大小
					CODDto cod = billPayCODFailService.queryBillCODPayFailedSize();
					payFailedCODTotalRecords = cod.getTotalCount().intValue();
					if(payFailedCODTotalRecords > 0){
						payFailedCOD = billPayCODFailService.queryBillCODPayFailed(
								getStart(), getLimit());
						totalAmount = cod.getTotalAmount();
					}
					
				}

				codPayFailedVO.setCods(payFailedCOD);
				codPayFailedVO.setTotalRecords(payFailedCODTotalRecords);
				codPayFailedVO.setTotalAmount(totalAmount);

			}

			// 返回结果
			return returnSuccess();

		} catch (SettlementException e) {

			logger.error(e.getMessage(), e);

			// 异常处理
			String error = returnError(e);
			setSuccess(true);
			setException(true);
			return error;
		}
	}

	/**
	 * 代收货款付款失败导出
	 * @author foss-guxinhua
	 * @date 2013-5-31 上午11:14:22
	 * @return
	 */
	public String exprotFailedCod() {

		// 查询结果
		List<CODDto> payFailedCOD = null;
		try {
			 
			if (codPayFailedVO != null) {

				List<String> waybillNos = null;

				String queryConditon = codPayFailedVO.getQueryCondition();
				// 查询所有
				if (QUERY_CONDITION_ALL.equals(queryConditon)
						|| QUERY_CONDITION_WAYBILL.equals(queryConditon)) {

					// 如果按单号查询
					if (QUERY_CONDITION_WAYBILL.equals(queryConditon)) {

						String waybillNoStr = codPayFailedVO.getWaybillNos();
						if (!StringUtils.isEmpty(waybillNoStr)) {

							// 增加单号
							waybillNos = new ArrayList<String>();
							String[] waybillNoArray = waybillNoStr.split(",");
							for (String waybillNo : waybillNoArray) {
								waybillNos.add(waybillNo);
							}

						}else{
							throw new SettlementException("单号不能为空");
						}
					}
				} else {
					throw new SettlementException("未提供的查询条件");
				}

				
				int payFailedCODTotalRecords = 0;

				// 如果运单号不为空，则按运单号查询
				if (CollectionUtils.isNotEmpty(waybillNos)) {

					// 退款失败申请、反汇款成功
					List<String> status = new ArrayList<String>();
					status.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION);
					status.add(SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS);

					// 查询结果和结果大小
					payFailedCOD = codCommonService
							.queryBillOfflineCODByWaybillNo(waybillNos, status);
				}

				// 运单号为空，按其它条件查询
				else {
					// 查询数据和数据大小
					CODDto cod = billPayCODFailService.queryBillCODPayFailedSize();
					payFailedCODTotalRecords = cod.getTotalCount().intValue();
					if(payFailedCODTotalRecords > 0){
						payFailedCOD = billPayCODFailService.queryBillCODPayFailed(0, payFailedCODTotalRecords);
					}
					
				}


			}
			
			// 总条数>0时查询
			if (CollectionUtils.isEmpty(payFailedCOD)) {
				return returnError("代收货款付款失败Execl导出为空！");
			}

			// 导出文件名称：
			try {
				String exportExeclName = "汇款失败审核";
				
				this.setExeclName(new String((exportExeclName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(payFailedCOD);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName("代收货款信息");
			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,exportSetting));

			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			logger.error("代收货款付款失败导出：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("代收货款付款失败导出：" + e.getErrorCode());
			// 异常处理
		} catch (Exception e) {
			// 记录日志
			logger.error("代收货款付款失败导出：" + e.getMessage(), e);
			// 异常返回
			return returnError("代收货款付款失败导出：" + e.getMessage());
			// 异常处理
		}
	}
	
	/**
	 * 获得execl报表数据
	 * @author guxinhua
	 * @date 2013-3-21 下午6:42:11
	 * @param mvrRfdEntityList
	 */
	private ExportResource exportRfdResource(List<CODDto> codDtoList){
		
		ExportResource sheet = new ExportResource();
		//设置表单表头
		sheet.setHeads(new String[]{"代收货款类型","出发部门","运单号","付款状态","应退金额","收款人","账号","开户行","省份","城市","支行","对公对私标志"
									,"签收时间","手机号码","银行行号","批次号","汇款导出时间","汇款导出人","汇款失败原因"});
		// 处理返回的结果集
		List<List<String>> resultList = this.exportBillCod(codDtoList);
		//设置表单数据
		sheet.setRowList(resultList);
		
		return sheet;
	}
	
	/**
	 * 代收货款综合查询数据
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:48
	 * @param mvrRfdEntityList
	 * @return
	 */
	private List<List<String>> exportBillCod(List<CODDto> codDtoList) {
		
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] columns = { 
				"codType","payableOrgName","waybillNo","status","returnAmount","payeeName","payeeAccount","bank","province","city","bankSubbranch","publicPrivateFlag","signDate",
				"payeePhone","bankCode","batchNumber","codExportTime","codExportUserName","remittanceFailNotes"};

		List<String> types=new ArrayList<String>();
		types.add(DictionaryConstants.COD__COD_TYPE);// 代收货款类型
		types.add(DictionaryConstants.COD__STATUS);// 代收货款
		types.add(DictionaryConstants.COD__PUBLIC_PRIVATE_FLAG);// 有效状态
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
		
		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(codDtoList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (CODDto entity : codDtoList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					if(StringUtils.equals(column, "codType")){
						cellValue = (fieldValue == null ? "" : 
							SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.COD__COD_TYPE,fieldValue.toString()));
					}else if(StringUtils.equals(column, "publicPrivateFlag")){
						cellValue = (fieldValue == null ? "" : 
							SettlementUtil.getDataDictionaryByTermsName(map
									, DictionaryConstants.COD__PUBLIC_PRIVATE_FLAG,fieldValue.toString()));
					}else if(StringUtils.equals(column, "status")){
						cellValue = (fieldValue == null ? "" : 
							SettlementUtil.getDataDictionaryByTermsName(map
									, DictionaryConstants.COD__STATUS,fieldValue.toString()));
					}else if(StringUtils.equals(column, "signDate")
					 ||StringUtils.equals(column, "codExportTime")){ // 转换productCode值
						cellValue = (fieldValue == null ? "" : sdfDateTime.format((Date)fieldValue));
					}else if(StringUtils.equals(column, "returnAmount")){
						// 应退金额转化，退款成功的金额也要显示。
						BigDecimal payeeAmount = null; // 未核销金额
						if(!(fieldValue == null || StringUtils.isBlank(fieldValue.toString()))){
							payeeAmount = NumberUtils.createBigDecimal(fieldValue.toString());
						}
						
						if(payeeAmount == null || payeeAmount.compareTo(BigDecimal.ZERO) == 0){
							Object verifyAmountObj = ReflectionUtils.getFieldValue(entity, "verifyAmount");// 应收冲代收货款应付的金额
							BigDecimal verifyAmount = null;
							if(!(verifyAmountObj == null || StringUtils.isBlank(verifyAmountObj.toString()))){
								verifyAmount = NumberUtils.createBigDecimal(verifyAmountObj.toString());
							}
							
							Object codAmountObj = ReflectionUtils.getFieldValue(entity, "amount"); // 代收货款总金额
							BigDecimal codAmount = NumberUtils.createBigDecimal(codAmountObj.toString());
							
							if(verifyAmount != null && verifyAmount.compareTo(BigDecimal.ZERO)>0 ){
								cellValue = codAmount.subtract(verifyAmount).toString(); // 减去 应收冲代收货款应付的金额
							}else{
								cellValue = codAmount.toString() ;
							}
							
						}else{
							cellValue = payeeAmount.toString();
						}
						
					}else{
						cellValue = (fieldValue == null ? "" : fieldValue.toString());
					}
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}
		
		return rowList;
	}
	
	/**
	 * 处理代收货款失败审核.
	 *
	 * @return the string
	 * @author dp-zengbinwen
	 * @date 2012-10-25 下午2:00:59
	 */
	@JSON
	public String processPayFailed() {

		try {
			// 判断处理是否为空
			if (auditResult != null) {

				// 判断代收货款数据是否存在
				if (codIds == null || codIds.length == 0) {
					throw new SettlementException("未选择代收货款数据");
				}

				// 获取当前用户
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

				// 审核通过
				if (auditResult.equals(AuditResultEnum.PASSED.getResult())) {

					billPayCODFailService.processPaymentFailed(
							Arrays.asList(codIds), AuditResultEnum.PASSED,
							currentInfo);

				}

				// 退回
				else if (auditResult.equals(AuditResultEnum.RETURNED
						.getResult())) {

					billPayCODFailService.processPaymentFailed(
							Arrays.asList(codIds), AuditResultEnum.RETURNED,
							currentInfo);

				}

				else {
					throw new SettlementException("未识别的审核处理结果:" + auditResult);
				}

			}
		} catch (SettlementException e) {

			logger.error(e.getMessage(), e);

			return returnError(e);
		}

		return returnSuccess();
	}

	
	/**
	 * 合计代收货款的金额.
	 *
	 * @param list the list
	 * @return the big decimal
	 * @author guxinhua
	 * @date 2013-2-2 上午10:29:41
	 */
	private BigDecimal totalCODAmount(List<CODDto> codList){
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (CODDto codDto : codList) {
			
			if(null == codDto.getReturnAmount() || codDto.getReturnAmount().compareTo(BigDecimal.ZERO) == 0){
				if(null == codDto.getVerifyAmount() || StringUtils.isBlank(String.valueOf(codDto.getVerifyAmount()))){
					totalAmount = totalAmount.add(codDto.getAmount());
				}else{
					// 金额
					totalAmount = totalAmount.add(codDto.getAmount().subtract(codDto.getVerifyAmount()));
				}
			}else{
				// 金额
				totalAmount = totalAmount.add(codDto.getReturnAmount());
			}
			
		}
		return totalAmount;
	}
	
	/**
	 * Sets the bill pay cod fail service.
	 *
	 * @param billPayCODFailService the new bill pay cod fail service
	 */
	public void setBillPayCODFailService(
			IBillPayCODFailService billPayCODFailService) {
		this.billPayCODFailService = billPayCODFailService;
	}

	/**
	 * Sets the cod common service.
	 *
	 * @param codCommonService the new cod common service
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Gets the cod pay failed vo.
	 *
	 * @return codPayFailedVO
	 */
	public CODVo getCodPayFailedVO() {
		return codPayFailedVO;
	}

	/**
	 * Sets the cod pay failed vo.
	 *
	 * @param codPayFailedVO the new cod pay failed vo
	 */
	public void setCodPayFailedVO(CODVo codPayFailedVO) {
		this.codPayFailedVO = codPayFailedVO;
	}

	/**
	 * Gets the cod ids.
	 *
	 * @return codIds
	 */
	public String[] getCodIds() {
		return codIds;
	}

	/**
	 * Sets the cod ids.
	 *
	 * @param codIds the new cod ids
	 */
	public void setCodIds(String[] codIds) {
		this.codIds = codIds;
	}

	/**
	 * Gets the audit result.
	 *
	 * @return auditResult
	 */
	public String getAuditResult() {
		return auditResult;
	}

	/**
	 * Sets the audit result.
	 *
	 * @param auditResult the new audit result
	 */
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	/**
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
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

	
}
