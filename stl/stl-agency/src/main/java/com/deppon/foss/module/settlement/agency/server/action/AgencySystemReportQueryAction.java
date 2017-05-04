package com.deppon.foss.module.settlement.agency.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.settlement.agency.api.server.service.IAgencySystemReportQueryService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportResultDto;
import com.deppon.foss.module.settlement.agency.api.shared.vo.AgencySystemReportQueryVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;

/**
 * 偏线全盘报表Action
 * 
 * @author foss-zhangxiaohui
 * @date Dec 25, 2012 3:51:47 PM
 */
public class AgencySystemReportQueryAction extends AbstractAction {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 7249643672917747312L;

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AgencySystemReportQueryAction.class);

	/**
	 * 偏线全盘报表Service
	 */
	private IAgencySystemReportQueryService agencySystemReportQueryService;

	/**
	 * 偏线全盘报表Vo
	 */
	private AgencySystemReportQueryVo vo;
	
	/**
	 * 导出excel名称
	 */
	private static final String  EXPROTNAME = "偏线全盘报表";
	
	/**
	 * 定义异常返回信息
	 */
	private String errorMessage;
	/**
	 * 导出excel的文件名称
	 */
	private String fileName;
	/**
	 * 导出excel的输入流
	 */
	private InputStream inputStream;
	

	/**
	 * 通过运单号或者Dto查询符合条件的记录
	 * 
	 * @author foss-zhangxiaohui
	 * @param
	 * @date Dec 26, 2012 8:33:15 PM
	 * @return
	 */
	@JSON
	public String queryAgencySystemReportAction() {
		try {

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 传入对象非空判断
			if (null == vo || null == vo.getDto()) {
				// 如果没有值则抛出异常
				throw new SettlementException("传入参数不能为空");
			}
			// 判断action类型
			if (null == vo.getDto().getQueryType()) {
				// 如果没有值则抛出异常
				throw new SettlementException("查询类型异常");
			}
			// 如果传过来的是运单单号类型则按照按运单单号查询
			if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(vo.getDto()
					.getQueryType())) {
				// 判断传入的运单号是否为空
				if (StringUtil.isEmpty(vo.getDto().getWaybillNo())) {
					// 如果没有值则抛出异常
					throw new SettlementException("传入的运单单号不能为空");
				}
				
				// 限制查询出来的是偏线代理
				vo.getDto().setAgencyWayBillNo(
						ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
				
				// 执行查询操作
				List<AgencySystemReportResultDto> list = agencySystemReportQueryService
						.querAgencySystemReportByWayBillNo(vo.getDto(),cInfo);
				// 对List非空判断
				if (CollectionUtils.isNotEmpty(list)) {
					// 把查询结果返回页面
					vo.setList(list);
				}
			}
			// 如果传过来的是Dto则按照按Dto查询
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(vo.getDto()
					.getQueryType())) {
				validaInfo(cInfo);
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("偏线全盘报表查询" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}
		// 返回结果
		return returnSuccess();
	}

	private void validaInfo(CurrentInfo cInfo) {
		// 开始日期非空校验
		if (null == vo.getDto().getStartBusinessDate()) {
			// 开始日期为空则抛出异常
			throw new SettlementException("开始业务日期不能为空");
		}
		// 结束日期非空校验
		if (null == vo.getDto().getEndBusinessDate()) {
			// 结束日期为空则抛出异常
			throw new SettlementException("结束业务日期不能为空");
		}
		//处理开始日期格式
		Date startDate = DateUtils.truncate(vo.getDto().getStartBusinessDate(), Calendar.SECOND);
		//处理结束日期格式
		Date endDate = DateUtils.truncate(vo.getDto().getEndBusinessDate(), Calendar.SECOND);
		
		
		// 判断开始日期是否小于结束日期
		if (vo.getDto().getStartBusinessDate() != null&& vo.getDto().getEndBusinessDate() != null) {
			//开始业务日期大于结束业务日期！
			if (startDate.after(endDate)) {
				throw new SettlementException("开始业务日期大于结束业务日期！");
			}
		}
		
		vo.getDto().setStartBusinessDate(startDate);
		
		//結束日期要在原來日期基礎加1
		vo.getDto().setEndBusinessDate(DateUtils.addDays(endDate, 1));
		
		// 判断开始日期是否小于结束日期
		if (vo.getDto().getHandOverStartTime() != null
				&& vo.getDto().getHandOverEndTime() != null) {
			//处理开始日期格式
			Date startHoDate = DateUtils.truncate(vo.getDto().getHandOverStartTime(), Calendar.SECOND);
			//处理结束日期格式
			Date endHoDate = DateUtils.truncate(vo.getDto().getHandOverEndTime(), Calendar.SECOND);
			
			vo.getDto().setHandOverStartTime(startHoDate);
			vo.getDto().setHandOverEndTime(DateUtils.addDays(endHoDate, 1));
			//开始外发时间大于结束外发时间
			if (startHoDate.after(endHoDate)) {
				throw new SettlementException("开始外发时间大于结束外发时间！");
			}
		}
		// 限制查询出来的是偏线代理
		vo.getDto().setAgencyWayBillNo(
				ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
		// 执行符合条件的数据库总记录条数的查询
		
		vo.getDto().setLastLoadOrgCode(vo.getDto().getArriveOrgCode());
		
		AgencySystemReportResultDto resultDto = agencySystemReportQueryService
				.queryTotalRecordsInDBByDto(vo.getDto(),cInfo);
		// 对Dto进行非空判断及判断总记录条数是否大于0
		if (null != resultDto && resultDto.getTotalRecordsInDB() > 0) {
			// 如果不为空，设置数据总条数
			setTotalCount(Long.valueOf(resultDto.getTotalRecordsInDB()));
			// 执行查询操作
			List<AgencySystemReportResultDto> list = agencySystemReportQueryService
					.querAgencySystemReportByDto(getStart(),
							getLimit(), vo.getDto(),cInfo);
			// 非空判断查询结果
			if (CollectionUtils.isNotEmpty(list)) {
				// 把结果集传回页面显示
				vo.setList(list);
			}
		}
	}
	
	/**
	 * 导出报表。
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-29 上午11:15:41
	 * @return
	 */
	public String exportAgencySystemReport(){
		try {
			List<AgencySystemReportResultDto> list = null;

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 传入对象非空判断
			if (null == vo || null == vo.getDto()) {
				// 如果没有值则抛出异常
				throw new SettlementException("传入参数不能为空");
			}
			// 判断action类型
			if (null == vo.getDto().getQueryType()) {
				// 如果没有值则抛出异常
				throw new SettlementException("查询类型异常");
			}
			// 如果传过来的是运单单号类型则按照按运单单号查询
			if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(vo.getDto()
					.getQueryType())) {
				// 判断传入的运单号是否为空
				if (StringUtil.isEmpty(vo.getDto().getWaybillNo())) {
					// 如果没有值则抛出异常
					throw new SettlementException("传入的运单单号不能为空");
				}
				
				// 限制查询出来的是偏线代理
				vo.getDto().setAgencyWayBillNo(
						ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
				
				// 执行查询操作
				list = agencySystemReportQueryService
						.querAgencySystemReportByWayBillNo(vo.getDto(),cInfo);
			}
			// 如果传过来的是Dto则按照按Dto查询
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(vo.getDto()
					.getQueryType())) {
				list = validaCurrent(cInfo);
			}
			
			// 设置导出excel名称
			
			
			
			try {
				this.setFileName(new String(EXPROTNAME.getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			
			// 导出格式数据
			ExportResource exportResource = getExportResource(list);

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(this.fileName);
			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();

			// 输出到导出流中
			inputStream = executor.exportSync(exportResource, exportSetting);
		} catch (BusinessException e) {
			// 记录日志并返回失败
			LOGGER.error(e.getMessage(),e);
			return returnError("导出偏线全盘报表异常:" + e.getMessage());
		}

		return SUCCESS;
	}

	private List<AgencySystemReportResultDto> validaCurrent(CurrentInfo cInfo) {
		List<AgencySystemReportResultDto> list;
		// 开始日期非空校验
		if (null == vo.getDto().getStartBusinessDate()) {
			// 开始日期为空则抛出异常
			throw new SettlementException("开始业务日期不能为空");
		}
		// 结束日期非空校验
		if (null == vo.getDto().getEndBusinessDate()) {
			// 结束日期为空则抛出异常
			throw new SettlementException("结束业务日期不能为空");
		}
		//处理开始日期格式
		Date startDate = DateUtils.truncate(vo.getDto().getStartBusinessDate(), Calendar.SECOND);
		//处理结束日期格式
		Date endDate = DateUtils.truncate(vo.getDto().getEndBusinessDate(), Calendar.SECOND);
		
		
		// 判断开始日期是否小于结束日期
		if (vo.getDto().getStartBusinessDate() != null&& vo.getDto().getEndBusinessDate() != null) {
			//开始业务日期大于结束业务日期！
			if (startDate.after(endDate)) {
				throw new SettlementException("开始业务日期大于结束业务日期！");
			}
		}
		
		vo.getDto().setStartBusinessDate(startDate);
		
		//結束日期要在原來日期基礎加1
		vo.getDto().setEndBusinessDate(DateUtils.addDays(endDate, 1));
		
		// 判断开始日期是否小于结束日期
		if (vo.getDto().getHandOverStartTime() != null
				&& vo.getDto().getHandOverEndTime() != null) {
			//处理开始日期格式
			Date startHoDate = DateUtils.truncate(vo.getDto().getHandOverStartTime(), Calendar.SECOND);
			//处理结束日期格式
			Date endHoDate = DateUtils.truncate(vo.getDto().getHandOverEndTime(), Calendar.SECOND);
			
			vo.getDto().setHandOverStartTime(startHoDate);
			vo.getDto().setHandOverEndTime(DateUtils.addDays(endHoDate, 1));
			//开始外发时间大于结束外发时间
			if (startHoDate.after(endHoDate)) {
				throw new SettlementException("开始外发时间大于结束外发时间！");
			}
		}
		// 限制查询出来的是偏线代理
		vo.getDto().setAgencyWayBillNo(
				ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
		
		vo.getDto().setLastLoadOrgCode(vo.getDto().getArriveOrgCode());
		// 执行查询操作  此处最多导出10w条数据，大家约定这么多
		list = agencySystemReportQueryService.querAgencySystemReportByDto(SettlementConstants.EXPORT_EXCEL_START_NUMBER,SettlementConstants.EXPORT_EXCEL_MAX_COUNTS, vo.getDto(),cInfo);
		return list;
	}
	
	/**
	 * 获取需要导出的excel数据.
	 * 
	 * @param queryList
	 *            the query list
	 * @return the sheet data
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-8 上午10:42:55
	 */
	private ExportResource getExportResource(List<AgencySystemReportResultDto> queryList) {
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 导出excel表头
		String[] headers = { "运单号", "收货部门", "收货日期", "付款方式", "配载时间", "代理名称",
							"件数", "重量(KG)", "体积(立方米)", "目的站", "到达时间", "是否外发",
							"外发单审核状态"};

		String[] columns = { "waybillNo", "receiveOrgCode", "billDate","paymentMethod", "handOverTime", "agencyCompanyName", 
							"amount","totalWeight", "totalVolume", "targetOrgName", "arriveTime", "registerFlag",
							"auditStatus"};

		// 设置Header
		data.setHeads(headers);
		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(queryList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			
			//声明字典集合
			List<String> termCodes = new ArrayList<String>();
			termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
			//后台获取数据字典对应的数据
			Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
			
			// 循环结果集
			for (AgencySystemReportResultDto entity : queryList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {
					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					//日期转换
					if(fieldValue!=null && column.equals("billDate")
							||column.equals("handOverTime")||column.equals("arriveTime")){
						//进行日期转换
						cellValue =  com.deppon.foss.util.DateUtils.convert((Date)fieldValue,"yyyy-MM-dd");
					//进行数据字典转换--付款方式
					}else if(fieldValue!=null && column.equals("paymentMethod")){
						cellValue = SettlementUtil.getDataDictionaryByTermsName(map,
								DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, fieldValue.toString());
					//进行数据字典转换 --是否外发
					}else if(column.equals("registerFlag")){
						if(fieldValue==null || StringUtils.isBlank(fieldValue.toString())){
							cellValue = "否";
						}else{
							cellValue = "是";
						}
						//进行数据字典转换 --是否外发
					}else if(fieldValue!=null && column.equals("auditStatus")){
						//此处没有数据字典 ，暂时考虑写死
						if(fieldValue.toString().equals("WAITINGAUDIT")){
							cellValue = "待审核";
						}else if(fieldValue.toString().equals("HASAUDITED")){
							cellValue = "已审核";
						}else if(fieldValue.toString().equals("BACKAUDIT")){
							cellValue = "反审核";
						}else if(fieldValue.toString().equals("INVALID")){
							cellValue = "已作废";
						}else{
							cellValue = fieldValue.toString();
						}
						
					}else{
						cellValue = (fieldValue == null ? "" : fieldValue.toString());
					}
					
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}
		// 设置导出数据
		data.setRowList(rowList);
		return data;
	}

	/**
	 * @return vo
	 */
	public AgencySystemReportQueryVo getVo() {
		return vo;
	}

	/**
	 * @param vo
	 */
	public void setVo(AgencySystemReportQueryVo vo) {
		this.vo = vo;
	}

	/**
	 * @param agencySystemReportQueryService
	 */
	public void setAgencySystemReportQueryService(
			IAgencySystemReportQueryService agencySystemReportQueryService) {
		this.agencySystemReportQueryService = agencySystemReportQueryService;
	}

	/**
	 * @GET
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		/*
		 *@get
		 *@ return errorMessage
		 */
		return errorMessage;
	}

	/**
	 * @SET
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		/*
		 *@set
		 *@this.errorMessage = errorMessage
		 */
		this.errorMessage = errorMessage;
	}

	/**
	 * @GET
	 * @return fileName
	 */
	public String getFileName() {
		/*
		 *@get
		 *@ return fileName
		 */
		return fileName;
	}

	/**
	 * @SET
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		/*
		 *@set
		 *@this.fileName = fileName
		 */
		this.fileName = fileName;
	}

	/**
	 * @GET
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		/*
		 *@get
		 *@ return inputStream
		 */
		return inputStream;
	}

	/**
	 * @SET
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		/*
		 *@set
		 *@this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}


	
}
