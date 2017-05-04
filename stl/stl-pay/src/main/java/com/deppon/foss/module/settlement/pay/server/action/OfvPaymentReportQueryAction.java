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
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IOfvPaymentReportQueryService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.OfvPaymentReportVo;
import com.deppon.foss.util.DateUtils;

/**
 * 外请车付款报表action
 * 
 * @author foss-zhangxiaohui
 * @date Dec 25, 2012 11:37:36 AM
 */
public class OfvPaymentReportQueryAction extends AbstractAction {

	/**
	 * 日志打印实体对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OfvPaymentReportQueryAction.class);

	/**
	 * 外请车付款报表action序列号
	 */
	private static final long serialVersionUID = -5064931159288014053L;

	/**
	 * 外请车付款报表vo
	 */
	private OfvPaymentReportVo ofvPaymentReportVo;

	/**
	 * 导出输出流
	 */
	private ByteArrayInputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 外请车付款报表服务service
	 */
	private IOfvPaymentReportQueryService ofvPaymentReportQueryService;

	/**
	 * 查询外请车付款报表列表
	 * 
	 * @author foss-zhangxiaohui
	 * @date 2012-12-13 下午1:58:29
	 */
	@JSON
	public String queryOfvPaymentReportList() {
		try {//判断输入的查询条件是否为空
			if(null == ofvPaymentReportVo || null == ofvPaymentReportVo.getDto()){				
				//如果查询条件为空则抛出异常
				throw new SettlementException("查询输入条件为空！");
			}		
			// 开始日期非空校验
			if (ofvPaymentReportVo.getDto().getStartPaymentDate()!=null) {				
				//结束日期默认为当前日期加一天
				String endDateTemp = DateUtils.addDay(ofvPaymentReportVo.getDto().getEndPaymentDate(), 1);
				Date endDate = DateUtils.convert(endDateTemp);
				// 将结束时间加一天数据设置到查询条件上
				ofvPaymentReportVo.getDto().setEndPaymentDate(endDate);
			}else if(ofvPaymentReportVo.getDto().getWorkFlowNosArray()!=null){
				List<String> workFlowNos = Arrays.asList(ofvPaymentReportVo.getDto().getWorkFlowNosArray());
				if(CollectionUtils.isNotEmpty(workFlowNos)){
					ofvPaymentReportVo.getDto().setWorkFlowNos(workFlowNos);
				}
			}else if(ofvPaymentReportVo.getDto().getVchAbillNosArray()!=null){
				List<String> vchAbillNos = Arrays.asList(ofvPaymentReportVo.getDto().getVchAbillNosArray());
				if(CollectionUtils.isNotEmpty(vchAbillNos)){
					ofvPaymentReportVo.getDto().setVchAbillNos(vchAbillNos);
				}
			}else if(ofvPaymentReportVo.getDto().getContractCodesNosArray()!=null){  //340403 合同编码查询
				List<String> contractCodesNos = Arrays.asList(ofvPaymentReportVo.getDto().getContractCodesNosArray());
				if(CollectionUtils.isNotEmpty(contractCodesNos)){
				ofvPaymentReportVo.getDto().setContractCodesNos(contractCodesNos);
			   }				
			}				
			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//声明ResultDto对象来判断是否有返回结果
			//OfvPaymentReportResultDto resultDto = new OfvPaymentReportResultDto();			
			// 设置查询出来的数据总条数
			OfvPaymentReportResultDto resultDto = ofvPaymentReportQueryService.queryOfvPaymentReportTotalByDate(ofvPaymentReportVo.getDto(),cInfo);
			//返回值非空验证
			if(null != resultDto && resultDto.getTotalRecordsInDB()> 0 ){				
				//设置分页数据的总条数
				this.setTotalCount(Long.valueOf(resultDto.getTotalRecordsInDB()));		
				// 分页查询外请车付款信息列表
				List<OfvPaymentReportResultDto> rtnDtoList = ofvPaymentReportQueryService.queryOfvPaymentReportByDateAndPage(
						getStart(),getLimit(),ofvPaymentReportVo.getDto(),cInfo);
				//对查询结果进行非空验证
				if(CollectionUtils.isNotEmpty(rtnDtoList)){
					// 如果查询结果非空 则返回至页面显示
					ofvPaymentReportVo.setOfvPaymentReportResultDtoList(rtnDtoList);
					ofvPaymentReportVo.setOfvPaymentReportResultDto(resultDto);
					}
			}else{
				ofvPaymentReportVo.setOfvPaymentReportResultDto(resultDto);
			}
		}
		//捕获异常
		catch (BusinessException e) {		
			//打印错误信息
			logger.error("外请车付款报表查询" + e.getMessage(), e);	
			//返回错误信息
			return returnError("外请车付款报表查询" + e);
		}
		//返回结果
		return returnSuccess();
	}

	/**
	 * 导出外请车付款报表的查询结果
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-13 下午2:01:32
	 */
	public String exportOfvpaymentRptList() {
		try {//判断输入的查询条件是否为空
			if(null == ofvPaymentReportVo || null == ofvPaymentReportVo.getDto()){			
				//如果查询条件为空则抛出异常
				throw new SettlementException("查询输入条件为空！");
			}		
			
			// 开始日期非空校验
			if (ofvPaymentReportVo.getDto().getStartPaymentDate() != null) {
				// 结束日期默认为当前日期加一天
				String endDateTemp = DateUtils.addDay(ofvPaymentReportVo
						.getDto().getEndPaymentDate(), 1);
				Date endDate = DateUtils.convert(endDateTemp);
				// 将结束时间加一天数据设置到查询条件上
				ofvPaymentReportVo.getDto().setEndPaymentDate(endDate);
			} else if (ofvPaymentReportVo.getDto().getWorkFlowNosArray() != null) {
				List<String> workFlowNos = Arrays.asList(ofvPaymentReportVo
						.getDto().getWorkFlowNosArray());
				if (CollectionUtils.isNotEmpty(workFlowNos)) {
					ofvPaymentReportVo.getDto().setWorkFlowNos(workFlowNos);
				}
			} else if (ofvPaymentReportVo.getDto().getVchAbillNosArray() != null) {
				List<String> vchAbillNos = Arrays.asList(ofvPaymentReportVo
						.getDto().getVchAbillNosArray());
				if (CollectionUtils.isNotEmpty(vchAbillNos)) {
					ofvPaymentReportVo.getDto().setVchAbillNos(vchAbillNos);
				}
			} else if(ofvPaymentReportVo.getDto().getContractCodesNosArray()!=null){  //340403 合同编码查询
				List<String> contractCodesNos = Arrays.asList(ofvPaymentReportVo.getDto().getContractCodesNosArray());
				if(CollectionUtils.isNotEmpty(contractCodesNos)){
				ofvPaymentReportVo.getDto().setContractCodesNos(contractCodesNos);
			   }	
			}  	
			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//打印日志信息
			logger.debug("导出外请车付款报表开始...");
			List<OfvPaymentReportResultDto> rtnDtoList = null;
			// 查询外请车付款报表
			if(CollectionUtils.isNotEmpty(ofvPaymentReportVo.getDto().getContractCodesNos())){
				rtnDtoList = ofvPaymentReportQueryService.queryOfvPaymentReportByContractCodesNo(ofvPaymentReportVo.getDto(), cInfo);
			}else if(ofvPaymentReportVo.getDto().getStartPaymentDate()!=null&&ofvPaymentReportVo.getDto().getEndPaymentDate()!=null){
				rtnDtoList = ofvPaymentReportQueryService.queryOfvPaymentReportByDate2(ofvPaymentReportVo.getDto(),cInfo);
			}else{
				rtnDtoList = ofvPaymentReportQueryService.queryOfvPaymentReportByDate(ofvPaymentReportVo.getDto(),cInfo);
			}
			
			// 生成Excel代码
			try {
				// 设置Excel名称
				//this.setExeclName(URLEncoder.encode("外请车付款报表", "UTF-8"));
				this.setExeclName(new String("外请车付款报表".getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} 
			//捕获异常
			catch (UnsupportedEncodingException e1) {		
				//打印错误信息
				logger.error("外请车付款报表导出" + e1.getMessage());
			}
			// 查询到的结果集不为空时，执行导出操作
			if (CollectionUtils.isNotEmpty(rtnDtoList)) {
				if (rtnDtoList.size() > SettlementConstants.EXPORT_MAX_COUNT) {
					throw new SettlementException("每次最多只能导出"+SettlementConstants.EXPORT_MAX_COUNT+"条数据,请重新查询并导出");
				}
				//设置Excel导出对象
				ExcelExport export = new ExcelExport();		
				// 设置每次最多导出6万条数据
				HSSFWorkbook work = export.exportExcel(
						fillSheetDataByOfvPaymentReport(rtnDtoList), "sheet", SettlementConstants.EXPORT_MAX_COUNT);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					work.write(baos);
					inputStream = new ByteArrayInputStream(baos.toByteArray());
				} 		
				//捕获IO流异常
				catch (IOException e) {			
					//打印错误信息
					logger.error("外请车付款报表导出" + e.getMessage());
				} 
				//关闭流等资源
				finally {			
					//判断输出流是否为空
					if (baos != null){			
						//不为空则关闭并捕获可能出现的异常
						try {
							baos.close();
						} 
						//捕获关闭流可能出现的异常
						catch (IOException e) {			
							//打印错误信息
							logger.error(e.getMessage());
						}
					}
				}
			} else {
				//抛出错误信息
				return returnError("导出数据为空！");
			}
		} 
		//捕获异常
		catch (BusinessException be) {
			//打印错误信息
			logger.error("外请车付款报表导出" + be.getMessage(), be);
			//返回错误信息
			return returnError("外请车付款报表导出" + be);
		}
		//返回结果
		return returnSuccess();
	}

	/**
	 * 生成外请车付款报表的excel
	 * @author foss-qiaolifeng
	 * @date 2012-12-13 下午5:34:25
	 */
	private SheetData fillSheetDataByOfvPaymentReport(List<OfvPaymentReportResultDto> rtnDtoList) {
		//声明Sheet的对象
		SheetData sheetData = new SheetData();
		//声明Sheet每个表头的信息
		sheetData.setRowHeads(new String[] { "出发部门", "到达部门", "付款类型", "工作流号", "来源单据编码", "车牌号",
				"司机", "配载/合同打印日期", "总金额", "首款", "尾款", "增减变化","出发付款人", "出发审核人", 
				"到达付款人", "到达审核人", "出港会计日期", "进港会计日期","车辆所有类型", "是否押回单","信息部名称","车主姓名" });
		//对查询结果做非空验证
		if (CollectionUtils.isNotEmpty(rtnDtoList)) {
			//声明要添加数据的List
			List<List<String>> rowList = new ArrayList<List<String>>();
			
			List<String> types=new ArrayList<String>();
			types.add(DictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE);// 应付类型
			//获取全部待转换缓存
			Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
			
			//遍历这个数据源
			for (OfvPaymentReportResultDto entity : rtnDtoList) {
				//声明每行数据的List对象
				List<String> cellList = new ArrayList<String>();
				//设置出发部门
				cellList.add(entity.getOrigOrgName());
				//设置到达部门
				cellList.add(entity.getDestOrgName());
				//设置付款类型
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, 
						DictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE,entity.getPayableType()));
				//设置工作流号
				cellList.add(entity.getWorkFlowNo());
				//设置车次号
				cellList.add(entity.getVehicleAssembleBillNo());
				//设置车牌号
				cellList.add(entity.getVehicleNo());
				//设置司机
				cellList.add(entity.getDriverName());
				//设置合同打印日期
				cellList.add(DateUtils.convert(entity.getStowageDate(),
						DateUtils.DATE_TIME_FORMAT));
				//总金额非空判断
				if(entity.getFeeTotal()!=null){
					//总金额不为空则赋值
					cellList.add(entity.getFeeTotal().toString());
				}else{
					//为空则添加空字符串
					cellList.add("");
				}
				//首款非空判断
				if(entity.getPrePaidFee()!= null){
					//如果不为空则添加到这一行显示
					cellList.add(entity.getPrePaidFee().toString());
				}else{
					//否则添加空字符串
					cellList.add("");
				}
				//尾款非空判断
				if(entity.getArrivePaidFee()!= null){
					//如果尾款不为空则添加到这一行显示
					cellList.add(entity.getArrivePaidFee().toString());// 尾款
				}else{
					//否则添加空字符串
					cellList.add("");
				}
				//增减变化金额非空判断
				if(entity.getAdjustPaidFee()!= null){
					//如果不为空则添加到这一行显示
					cellList.add(entity.getAdjustPaidFee().toString());
				}else{
					//如果为空则添加空字符串
					cellList.add("");
				}
				//添加出发付款人
				cellList.add(entity.getPrePaidPayer());
				//添加出发审核人
				cellList.add(entity.getPrePaidAuditer());
				//添加到达付款人
				cellList.add(entity.getArrivePaidPayer());
				//添加到达审核人
				cellList.add(entity.getArrivePaidAuditer());
				//添加出港会计日期
				cellList.add(DateUtils.convert(entity.getActualDepartTime(),
						DateUtils.DATE_TIME_FORMAT));
				//添加进港会计日期
				cellList.add(DateUtils.convert(entity.getActualArriveTime(),
						DateUtils.DATE_TIME_FORMAT));
				//添加车辆所有类型
				cellList.add(entity.getVehicleOwnerShip());
				//添加是否押回单
				cellList.add(entity.getBeReturnReceipt());
				//添加信息部名称
				cellList.add(entity.getInformationName());
				//添加车主姓名
				cellList.add(entity.getCarOwnerName());
				
				//添加这一行数据但这个数据源的List
				rowList.add(cellList);
			}
			//sheet对象添加这个数据源
			sheetData.setRowList(rowList);
		}	
		//返回Excel Sheet对象
		return sheetData;
	}

	/**
	 * @GET
	 * @return ofvPaymentReportVo
	 */
	public OfvPaymentReportVo getOfvPaymentReportVo() {
		/*
		 *@get
		 *@ return ofvPaymentReportVo
		 */
		return ofvPaymentReportVo;
	}

	/**
	 * @SET
	 * @param ofvPaymentReportVo
	 */
	public void setOfvPaymentReportVo(OfvPaymentReportVo ofvPaymentReportVo) {
		/*
		 *@set
		 *@this.ofvPaymentReportVo = ofvPaymentReportVo
		 */
		this.ofvPaymentReportVo = ofvPaymentReportVo;
	}

	/**
	 * @GET
	 * @return inputStream
	 */
	public ByteArrayInputStream getInputStream() {
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
	public void setInputStream(ByteArrayInputStream inputStream) {
		/*
		 *@set
		 *@this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}

	/**
	 * @GET
	 * @return execlName
	 */
	public String getExeclName() {
		/*
		 *@get
		 *@ return execlName
		 */
		return execlName;
	}

	/**
	 * @SET
	 * @param execlName
	 */
	public void setExeclName(String execlName) {
		/*
		 *@set
		 *@this.execlName = execlName
		 */
		this.execlName = execlName;
	}

	/**
	 * @SET
	 * @param ofvPaymentReportQueryService
	 */
	public void setOfvPaymentReportQueryService(
			IOfvPaymentReportQueryService ofvPaymentReportQueryService) {
		/*
		 *@set
		 *@this.ofvPaymentReportQueryService = ofvPaymentReportQueryService
		 */
		this.ofvPaymentReportQueryService = ofvPaymentReportQueryService;
	}

}
