/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-pay
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.server.action
 * FILE    NAME: OnlionMonitorReportAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlionMonitorReportService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.OnlionMonitorReporVo;
import com.deppon.foss.util.DateUtils;


/**
 * 在线支付监控
 * @author 045738-foss-maojianqiang
 * @date 2012-12-26 下午8:39:02
 */
public class OnlionMonitorReportAction extends AbstractAction {
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(OnlionMonitorReportAction.class);
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -8680724901536313746L;
	
	/**
	 * 导出excel名称
	 */
	private static final String  EXPROTNAME = "在线支付监控";
	
	/**
	 * 注入service
	 */
	private IOnlionMonitorReportService onlionMonitorReportService;
	/**
	 * vo
	 */
	private OnlionMonitorReporVo vo;
	/**
	 * 导出excel的文件名称
	 */
	private String fileName;
	/**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream stream;
	
	/**
	 * 定义异常返回信息
	 */
	private String errorMessage;
	
	
	/**
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	
	/**
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * 查询在线支付信息
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-26 下午8:44:26
	 * @return
	 */
	@JSON
	public String queryOnlionMointorList(){
		try{
			//判断传入参数是否为空
			if(vo==null||vo.getDto()==null){
				throw new SettlementException("查询参数为空，不能进行查询");
			}
			//如果按日期查询
			if(StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_PAYDATE, vo.getDto().getSearchType())
					||StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_VERIFYDATE, vo.getDto().getSearchType())){
				//结束日期加1天
				vo.getDto().setEndDate(DateUtils.convert(DateUtils.addDay(vo.getDto().getEndDate(), 1)));
			}
			//调用service进行查询   此处start从1开始 故而这边需要+1
			int page = this.getStart()/this.getLimit()+1;
			OnlionMonitorReportResultDto resultDto = onlionMonitorReportService.queryOnlionMointorList(vo.getDto(),page,this.getLimit());
			//将查询结果给vo，返回前台
			vo.setResultDto(resultDto);
			this.setTotalCount((long) resultDto.getTotalCount());
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}

	/**
	 * 导出excel
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-27 下午7:16:38
	 * @return
	 */
	public String exportReport(){
		//输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//判断传入参数是否为空
			if(vo==null||vo.getDto()==null){
				throw new SettlementException("查询参数为空，不能进行查询");
			}
			//如果按日期查询
			if(StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_PAYDATE, vo.getDto().getSearchType())
					||StringUtils.equals(SettlementESBDictionaryConstants.BHO_ONLION_QUERYTYPE_VERIFYDATE, vo.getDto().getSearchType())){
				//结束日期加1天
				vo.getDto().setEndDate(DateUtils.convert(DateUtils.addDay(vo.getDto().getEndDate(), 1)));
			}
			//设置excel名称
    		try {
					this.setFileName(new String((EXPROTNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
				} catch (UnsupportedEncodingException e1) {
					throw new SettlementException("导出文件名编码转化错误！");
				}
        		HSSFWorkbook wookBook = onlionMonitorReportService.exportBillPayable(vo.getDto());
			try {
				wookBook.write(baos);
				stream = new ByteArrayInputStream(baos.toByteArray()); 
			} catch (IOException e) {
				throw new SettlementException("生成excel流错误！");
			}
    		return returnSuccess();
		}catch(BusinessException e){
			errorMessage = e.getMessage();
			return returnError(e.getMessage());
		}finally{
			//关闭流
    		if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					throw new SettlementException("流关闭错误！");
				}
			}
		}
	
	}
	
	/**
	 * @return vo
	 */
	public OnlionMonitorReporVo getVo() {
		return vo;
	}

	/**
	 * @param vo
	 */
	public void setVo(OnlionMonitorReporVo vo) {
		this.vo = vo;
	}

	/**
	 * @param onlionMonitorReportService
	 */
	public void setOnlionMonitorReportService(IOnlionMonitorReportService onlionMonitorReportService) {
		this.onlionMonitorReportService = onlionMonitorReportService;
	}

	
	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	
	/**
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	/**
	 * @return stream
	 */
	public ByteArrayInputStream getStream() {
		return stream;
	}

	
	/**
	 * @param stream
	 */
	public void setStream(ByteArrayInputStream stream) {
		this.stream = stream;
	}
	

}
