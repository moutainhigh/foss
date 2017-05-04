package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
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
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICODCompositeQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODCompositeConditionVo;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODCompositeQueryVo;
import com.deppon.foss.util.DateUtils;

/**
 * 代收货款综合查询.
 *
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 下午2:51:30
 */
public class CODCompositeQueryAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(CODCompositeQueryAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -350135241353383288L;

	/** 代收货款service. */
	private ICODCompositeQueryService codCompositeQueryService;

	/** VO. */
	private CODCompositeQueryVo vo;
	
	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	private IProductService  productService;

	/**
	 * 查询服务.
	 *
	 * @return the string
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-31 上午9:17:16
	 */
	@JSON
	public String query() {

		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		if(null==vo || null==vo.getQueryVo()){
			return returnError("查询参数不能为空！");
		}
		if (vo.getQueryVo().getQueryType().equals("queryByWaybillNo")) {
			// 如果单号不为空,直接按单号进行查询
			if (!StringUtils.isEmpty(vo.getQueryVo().getWaybillNo())) {

				try {
					// 将字符转换成List
					List<String> waybillNos = new ArrayList<String>();
					String[] wayBillArray = vo.getQueryVo().getWaybillNo().split(",");
					//
					waybillNos = Arrays.asList(wayBillArray);
					
					vo.getQueryVo().setWaybillNos(waybillNos);

					// 按运单号进行查询
					vo.setGridVoSet(codCompositeQueryService
							.queryByWaybillNo(vo.getQueryVo(),currentInfo));
					// 结果集不会空时，设置总条数
					if (!CollectionUtils.isEmpty(vo.getGridVoSet())) {
						// 总行数
						totalCount = (long) vo.getGridVoSet().size();
					} else {
						// 设置为0行
						totalCount = 0L;
					}
					
				} catch (BusinessException ex) {

					LOGGER.error(ex.getMessage());

					return returnError(ex);
				}
			} else {
				return returnError("按运单查询时，运单号不能为空！");
			}
			
		} else if (vo.getQueryVo().getQueryType().equals("queryByMergeCode")) {
			// 如果单号不为空,直接按单号进行查询
			if (!StringUtils.isEmpty(vo.getQueryVo().getMergeCode())) {

				try {
					// 按运单号进行查询
					vo.setGridVoSet(codCompositeQueryService.queryByMergeCode(vo.getQueryVo(),currentInfo));
					// 结果集不会空时，设置总条数
					if (!CollectionUtils.isEmpty(vo.getGridVoSet())) {
						// 总行数
						totalCount = (long) vo.getGridVoSet().size();
					} else {
						// 设置为0行
						totalCount = 0L;
					}
				} catch (BusinessException ex) {

					LOGGER.error(ex.getMessage());

					return returnError(ex);
				}
			} else {
				return returnError("按运单查询时，运单号不能为空！");
			}
		} else {

			try {
				// 如果批次号不为空，只按照批次号来查询
				if(StringUtils.isNotBlank(vo.getQueryVo().getBatchNumber())){
					List<CODCompositeGridDto> codlist = codCompositeQueryService.queryCompositeByBatchNo(vo.getQueryVo(), currentInfo);
					vo.setGridVoSet(codlist);
					totalCount = (long)codlist.size();
				}else{
				// 将字符串日期转换成Date 值
				vo.setQueryVo(this.parseVoJsonParams(vo.getQueryVo()));
				// R1.查询的所有时间段最大范围为7天时间。但是如果存在收款人，则开单时间的范围可以扩大到15天。且三个查询时间只能按照一个时间来查询。
				// 如果三个时间都存在，则其查询先后顺序：先按签收时间查询，如果签收时间不存在，则按照汇款申请时间查询，依次类推。
				// 按业务日期
				if (vo.getQueryVo().getInceptBizDate() != null
						&& vo.getQueryVo().getEndBizDate() != null) {
					try {
						
						//结束日期加1
						// 设置执行时使用小于结束日期+1天
						String dateEndTemp = DateUtils.addDay(vo.getQueryVo().getEndBizDate(), 1);
						// 转换时间为格式
						Date dateEnd = DateUtils.convert(dateEndTemp);
						// 设置查询参数dto的业务结束日期参数
						vo.getQueryVo().setEndBizDate(dateEnd);
						
						// 获得总行数
						totalCount = (long) codCompositeQueryService
								.queryTotalRowsByBizDate(vo.getQueryVo(),currentInfo);
						// 大于0 才执行查询
						if (totalCount > 0) {
							// 获得结果集
							vo.setGridVoSet(codCompositeQueryService
									.queryByBizDate(vo.getQueryVo(), start, limit,currentInfo));
						}
					} catch (BusinessException ex) {
						return returnError(ex);
					}
				}
				// 按签收日期
				else if (vo.getQueryVo().getInceptSignDate() != null
						&& vo.getQueryVo().getEndSignDate() != null) {
					try {
						
						//结束日期加1
						// 设置执行时使用小于结束日期+1天
						String dateEndTemp = DateUtils.addDay(vo.getQueryVo().getEndSignDate(), 1);
						// 转换时间为格式
						Date dateEnd = DateUtils.convert(dateEndTemp);
						// 设置查询参数dto的业务结束日期参数
						vo.getQueryVo().setEndSignDate(dateEnd);
						
						// 获得总行数
						totalCount = (long) codCompositeQueryService
								.queryTotalRowsBySignDate(vo.getQueryVo(),currentInfo);
						// 大于0 才执行查询
						if (totalCount > 0) {
							// 获得结果集
							vo.setGridVoSet(codCompositeQueryService
									.queryBySignDate(vo.getQueryVo(), start, limit,currentInfo));
						}
					} catch (BusinessException ex) {
						return returnError(ex);
					}
				}
				// 按付款时间
				else if (vo.getQueryVo().getInceptPaymentDate() != null
						&& vo.getQueryVo().getEndPaymentDate() != null) {
					try {
						
						//结束日期加1
						// 设置执行时使用小于结束日期+1天
						String dateEndTemp = DateUtils.addDay(vo.getQueryVo().getEndPaymentDate(), 1);
						// 转换时间为格式
						Date dateEnd = DateUtils.convert(dateEndTemp);
						// 设置查询参数dto的业务结束日期参数
						vo.getQueryVo().setEndPaymentDate(dateEnd);
						
						// 获得总行数
						totalCount = (long) codCompositeQueryService
								.queryTotalRowsByPayDate(vo.getQueryVo(),currentInfo);
						// 大于0 才执行查询
						if (totalCount > 0) {
							// 获得结果集
							vo.setGridVoSet(codCompositeQueryService
									.queryByPayDate(vo.getQueryVo(), start, limit,currentInfo));
						}
					} catch (BusinessException ex) {

						LOGGER.error(ex.getMessage());

						return returnError(ex);
					}
				}
				// 按汇款成功时间
				else if (vo.getQueryVo().getInceptRefundSuccessDate() != null
						&& vo.getQueryVo().getEndRefundSuccessDate() != null) {
					try {
						
						//结束日期加1
						// 设置执行时使用小于结束日期+1天
						String dateEndTemp = DateUtils.addDay(vo.getQueryVo().getEndRefundSuccessDate(), 1);
						// 转换时间为格式
						Date dateEnd = DateUtils.convert(dateEndTemp);
						// 设置查询参数dto的业务结束日期参数
						vo.getQueryVo().setEndRefundSuccessDate(dateEnd);
						
						// 获得总行数
						totalCount = (long) codCompositeQueryService
								.queryTotalRowsByRefundSuccessDate(vo.getQueryVo(), currentInfo);
						// 大于0 才执行查询
						if (totalCount > 0) {
							// 获得结果集
							vo.setGridVoSet(codCompositeQueryService
									.queryByRefundSuccessDate(vo.getQueryVo(), start, limit,currentInfo));
						}
					} catch (BusinessException ex) {

						LOGGER.error(ex.getMessage());

						return returnError(ex);
					}
				} else {
					// 如果按日期进行查询，三个日期至少要选定一个日期查询条件！
					return returnError("如果按日期进行查询，四个日期至少要选定一个日期查询条件！");
				}
				}
			} catch (ParseException ex) {

				LOGGER.error(ex.getMessage());

				return returnError(ex.getMessage());
			}
		}
		// 如果结果集为空，则清空数据
		if (totalCount == 0) {
			vo.setGridVoSet(null);
		}
		return returnSuccess();
	}

	/**
	 * 查询代收货款日志.
	 *
	 * @return the string
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-13 下午6:46:06
	 */
	@JSON
	public String queryCodLog() {
		if (vo != null && vo.getQueryVo() != null
				&& !StringUtils.isEmpty(vo.getQueryVo().getWaybillNo())) {
			try {
				// 按运单好进行
				List<CODLogEntity> voSet = codCompositeQueryService
						.queryCodLogByWaybill(vo.getQueryVo()
								.getWaybillNo());
				vo.setLogVoSet(voSet);
				// 返回结果
				return returnSuccess();
			} catch (BusinessException ex) {

				LOGGER.error(ex.getMessage());

				return returnError(ex);
			}

		} else {
			// 参数为空
			return returnError("参数为空!");
		}
	}

	/**
	 * 代收货款综合导出
	 * 
	 * @author guxinhua
	 * @date 2013-3-27 下午2:01:07
	 * @return
	 */
	public String exprotBillPaidCod() {

		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		List<CODCompositeGridDto> codCompositeGridDtoList = new ArrayList<CODCompositeGridDto>();
		try {
			if (vo.getQueryVo().getQueryType().equals("queryByWaybillNo")) {
				// 如果单号不为空,直接按单号进行查询
				if (!StringUtils.isEmpty(vo.getQueryVo().getWaybillNo())) {
					// 将字符转换成List
					List<String> waybillNos = new ArrayList<String>();
					String[] wayBillArray = vo.getQueryVo().getWaybillNo().split(",");
					waybillNos = Arrays.asList(wayBillArray);
					vo.getQueryVo().setWaybillNos(waybillNos);
					// 按运单号进行查询
					codCompositeGridDtoList = codCompositeQueryService.queryByWaybillNo(vo.getQueryVo(), currentInfo);
					
					totalCount = (long) codCompositeGridDtoList.size();
				} else {
					return returnError("按运单查询时，运单号不能为空！");
				}
			} else if (vo.getQueryVo().getQueryType().equals("queryByMergeCode")) {
				// 如果合并编号不为空,直接按编号进行查询
				if (!StringUtils.isEmpty(vo.getQueryVo().getMergeCode())) {
					// 按合并编号进行查询
					codCompositeGridDtoList = codCompositeQueryService.queryByMergeCode(vo.getQueryVo(), currentInfo);
					
					totalCount = (long) codCompositeGridDtoList.size();
				} else {
					return returnError("按合并编号查询时，合并编号不能为空！");
				}
			} else {
				// 如果批次号不为空，只按照批次号来查询
				if(StringUtils.isNotBlank(vo.getQueryVo().getBatchNumber())){
					codCompositeGridDtoList = codCompositeQueryService.queryCompositeByBatchNo(vo.getQueryVo(), currentInfo);
					vo.setGridVoSet(codCompositeGridDtoList);
					totalCount = (long)codCompositeGridDtoList.size();
				}else{
				// 将字符串日期转换成Date 值
				vo.setQueryVo(this.parseVoJsonParams(vo.getQueryVo()));
				// R1.查询的所有时间段最大范围为7天时间。但是如果存在收款人，则开单时间的范围可以扩大到15天。且三个查询时间只能按照一个时间来查询。
				// 如果三个时间都存在，则其查询先后顺序：先按签收时间查询，如果签收时间不存在，则按照汇款申请时间查询，依次类推。
				// 按业务日期
				if (vo.getQueryVo().getInceptBizDate() != null && vo.getQueryVo().getEndBizDate() != null) {
					
					//结束日期加1
					// 设置执行时使用小于结束日期+1天
					String dateEndTemp = DateUtils.addDay(vo.getQueryVo().getEndBizDate(), 1);
					// 转换时间为格式
					Date dateEnd = DateUtils.convert(dateEndTemp);
					// 设置查询参数dto的业务结束日期参数
					vo.getQueryVo().setEndBizDate(dateEnd);	
					
					// 获得总行数
						totalCount = (long) codCompositeQueryService.queryTotalRowsByBizDate(vo.getQueryVo(),currentInfo);
						// 大于0 才执行查询
						if (totalCount > 0) {
							// 获得结果集
							codCompositeGridDtoList = codCompositeQueryService.queryByBizDate(vo.getQueryVo(), 0, totalCount.intValue(), currentInfo);
						}
				}
				// 按签收日期
				else if (vo.getQueryVo().getInceptSignDate() != null && vo.getQueryVo().getEndSignDate() != null) {
					
					//结束日期加1
					// 设置执行时使用小于结束日期+1天
					String dateEndTemp = DateUtils.addDay(vo.getQueryVo().getEndSignDate(), 1);
					// 转换时间为格式
					Date dateEnd = DateUtils.convert(dateEndTemp);
					// 设置查询参数dto的业务结束日期参数
					vo.getQueryVo().setEndSignDate(dateEnd);
					
					// 获得总行数
						totalCount = (long) codCompositeQueryService.queryTotalRowsBySignDate(vo.getQueryVo(),currentInfo);
						// 大于0 才执行查询
						if (totalCount > 0) {
							// 获得结果集
							codCompositeGridDtoList = codCompositeQueryService.queryBySignDate(vo.getQueryVo(), 0, totalCount.intValue(), currentInfo);
						}
				}
				// 按付款时间
				else if (vo.getQueryVo().getInceptPaymentDate() != null && vo.getQueryVo().getEndPaymentDate() != null) {
					
					//结束日期加1
					// 设置执行时使用小于结束日期+1天
					String dateEndTemp = DateUtils.addDay(vo.getQueryVo().getEndPaymentDate(), 1);
					// 转换时间为格式
					Date dateEnd = DateUtils.convert(dateEndTemp);
					// 设置查询参数dto的业务结束日期参数
					vo.getQueryVo().setEndPaymentDate(dateEnd);
					
					// 获得总行数
						totalCount = (long) codCompositeQueryService.queryTotalRowsByPayDate(vo.getQueryVo(),currentInfo);
						// 大于0 才执行查询
						if (totalCount > 0) {
							// 获得结果集
							codCompositeGridDtoList = codCompositeQueryService.queryByPayDate(vo.getQueryVo(), 0, totalCount.intValue(), currentInfo);
						}
				} else if (vo.getQueryVo().getInceptRefundSuccessDate() != null
						&& vo.getQueryVo().getEndRefundSuccessDate() != null) {
					//结束日期加1
					// 设置执行时使用小于结束日期+1天
					String dateEndTemp = DateUtils.addDay(vo.getQueryVo().getEndRefundSuccessDate(), 1);
					// 转换时间为格式
					Date dateEnd = DateUtils.convert(dateEndTemp);
					// 设置查询参数dto的业务结束日期参数
					vo.getQueryVo().setEndRefundSuccessDate(dateEnd);
					
					// 获得总行数
						totalCount = (long) codCompositeQueryService.queryTotalRowsByRefundSuccessDate(vo.getQueryVo(),currentInfo);
						// 大于0 才执行查询
						if (totalCount > 0) {
							// 获得结果集
							codCompositeGridDtoList = codCompositeQueryService.queryByRefundSuccessDate(vo.getQueryVo(), 0, totalCount.intValue(), currentInfo);
						}
				} else {
					// 如果按日期进行查询，四个日期至少要选定一个日期查询条件！
					return returnError("如果按日期进行查询，四个日期至少要选定一个日期查询条件！");
				}
			}
			}
			// 总条数>0时查询
			if (totalCount == null || totalCount <= 0 || CollectionUtils.isEmpty(codCompositeGridDtoList)) {
				return returnError("代收货款综合查询Execl导出为空！");
			}

			// 导出文件名称：
			try {
				String exportExeclName = "代收货款综合信息";
				this.setExeclName(new String(exportExeclName.getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(codCompositeGridDtoList);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName("代收货款信息");
			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,exportSetting));

			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error("代收货款综合查询Execl导出异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("代收货款综合查询Execl导出异常：" + e.getErrorCode());
			// 异常处理
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("代收货款综合查询Execl导出异常：" + e.getMessage(), e);
			// 异常返回
			return returnError("代收货款综合查询Execl导出异常：" + e.getMessage());
			// 异常处理
		}
	}
	
	/**
	 * 获得execl报表数据
	 * @author guxinhua
	 * @date 2013-3-21 下午6:42:11
	 * @param mvrRfdEntityList
	 */
	private ExportResource exportRfdResource(List<CODCompositeGridDto> codCompositeGridDtoList){
		
		ExportResource sheet = new ExportResource();
		//设置表单表头
		sheet.setHeads(new String[]{"代收货款类型","出发部门","运单号","合并编号","运输性质","开单代收货款金额","冲应收金额","应退金额","代收手续费"
									,"是否有效","付款状态","发货客户","收款人","账号","开户行","支行","开单日期"
									,"签收时间","收银确认时间","汇款导出时间","汇款成功时间","汇款失败原因","批次号"});
		// 处理返回的结果集
		List<List<String>> resultList = this.exportBillCod(codCompositeGridDtoList);
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
	private List<List<String>> exportBillCod(List<CODCompositeGridDto> codCompositeGridDtoList) {
		
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		String[] columns = { 
				"codType",
				"payableOrgName",
				"waybillNo",
				"mergeCode",
				"productCode",
				"codAmount",
				"verifyAmount",
				"payeeAmount",
				"codFee",
				"active",
				"status",
				"consignee",
				"payeeName",
				"payeeAccount",
				"bank",
				"bankBranchName",
				"businessDate",
				"signDate",
				"cashConfirmTime",
				"codExportTime",
				"refundSuccessTime",
				"remittanceFailNotes",
				"batchNo"
				};

		List<String> types=new ArrayList<String>();
		types.add(DictionaryConstants.COD__COD_TYPE);// 代收货款类型
		types.add(DictionaryConstants.COD__STATUS);// 代收货款
		types.add(DictionaryConstants.FOSS_ACTIVE);// 有效状态
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
		
		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(codCompositeGridDtoList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (CODCompositeGridDto entity : codCompositeGridDtoList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					if(StringUtils.equals(column, "codType")){
						cellValue = (fieldValue == null ? "" : 
							SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.COD__COD_TYPE,fieldValue.toString()));
					}else if(StringUtils.equals(column, "productCode")){
						cellValue = productService.getProductByCache(fieldValue.toString(),new Date()).getName();
						
					}else if(StringUtils.equals(column, "active")){
						cellValue = (fieldValue == null ? "" : 
							SettlementUtil.getDataDictionaryByTermsName(map
									, DictionaryConstants.FOSS_ACTIVE,fieldValue.toString()));
					}else if(StringUtils.equals(column, "status")){
						cellValue = (fieldValue == null ? "" : 
							SettlementUtil.getDataDictionaryByTermsName(map
									, DictionaryConstants.COD__STATUS,fieldValue.toString()));
					}else if(StringUtils.equals(column, "businessDate")){
						cellValue = (fieldValue == null ? "" : sdfDate.format((Date)fieldValue));
					}else if(StringUtils.equals(column, "signDate")
					 ||StringUtils.equals(column, "codExportTime")
					 ||StringUtils.equals(column, "cashConfirmTime")
					 ||StringUtils.equals(column, "refundSuccessTime")){ // 转换productCode值
						cellValue = (fieldValue == null ? "" : sdfDateTime.format((Date)fieldValue));
					}else if(StringUtils.equals(column, "payeeAmount")){
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
							
							Object codAmountObj = ReflectionUtils.getFieldValue(entity, "codAmount"); // 代收货款总金额
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
	 * 将查询条件的的字符串日期值转换成Date值.
	 *
	 * @param vo the vo
	 * @return the cOD composite condition vo
	 * @throws ParseException the parse exception
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 下午4:42:49
	 */
	private CODCompositeConditionVo parseVoJsonParams(CODCompositeConditionVo vo)
			throws ParseException {
		if (vo == null) {
			return null;
		} else {
			CODCompositeConditionVo resultVo = vo;
			// 业务日期
			if (!StringUtils.isEmpty(vo.getInceptBizDateStr())) {
				resultVo.setInceptBizDate(DateUtils.convert(vo
						.getInceptBizDateStr()));
			}
			if (!StringUtils.isEmpty(vo.getEndBizDateStr())) {
				resultVo.setEndBizDate(DateUtils.convert(vo.getEndBizDateStr()));
			}
			// 签收日期
			if (!StringUtils.isEmpty(vo.getInceptSignDateStr())) {
				resultVo.setInceptSignDate(DateUtils.convert(vo
						.getInceptSignDateStr()));
			}
			if (!StringUtils.isEmpty(vo.getEndSignDateStr())) {
				resultVo.setEndSignDate(DateUtils.convert(vo
						.getEndSignDateStr()));
			}
			// 付款日期
			if (!StringUtils.isEmpty(vo.getInceptPaymentDateStr())) {
				resultVo.setInceptPaymentDate(DateUtils.convert(vo
						.getInceptPaymentDateStr()));
			}
			if (!StringUtils.isEmpty(vo.getEndPaymentDateStr())) {
				resultVo.setEndPaymentDate(DateUtils.convert(vo
						.getEndPaymentDateStr()));
			}
			// 汇款成功日期
			if (!StringUtils.isEmpty(vo.getInceptRefundSuccessDateStr())) {
				resultVo.setInceptRefundSuccessDate(DateUtils.convert(vo
						.getInceptRefundSuccessDateStr()));
			}
			if (!StringUtils.isEmpty(vo.getEndRefundSuccessDateStr())) {
				resultVo.setEndRefundSuccessDate(DateUtils.convert(vo
						.getEndRefundSuccessDateStr()));
			}

			// 初始字符串
			// 退款路径
			if (StringUtils.isEmpty(vo.getRefundPath())) {
				resultVo.setRefundPath(null);
			}
			// 应付组织
			if (StringUtils.isEmpty(vo.getPayableOrgCode())) {
				resultVo.setPayableOrgCode(null);
			}
			// 发货客户
			if (StringUtils.isEmpty(vo.getConsignee())) {
				vo.setConsignee(null);
			}
			// 开户行
			if (StringUtils.isEmpty(vo.getBank())) {
				vo.setBank(null);
			}
			// 收款人
			if (StringUtils.isEmpty(vo.getPayeeName())) {
				vo.setPayeeName(null);
			}
			// 退款批次号
			if (StringUtils.isEmpty(vo.getBatchNumber())) {
				vo.setBatchNumber(null);
			}

			return resultVo;
		}
	}

	/**
	 * Sets the cod composite query service.
	 *
	 * @param codCompositeQueryService the new cod composite query service
	 */
	public void setCodCompositeQueryService(
			ICODCompositeQueryService codCompositeQueryService) {
		this.codCompositeQueryService = codCompositeQueryService;
	}

	/**
	 * Gets the vo.
	 *
	 * @return vo
	 */
	public CODCompositeQueryVo getVo() {
		return vo;
	}

	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(CODCompositeQueryVo vo) {
		this.vo = vo;
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

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

}
