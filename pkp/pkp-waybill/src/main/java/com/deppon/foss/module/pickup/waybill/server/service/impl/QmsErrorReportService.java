package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQmsErrorReportService;
import com.deppon.foss.module.pickup.waybill.shared.dto.QmsErrorReportRequestDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QmsErrorReportResponseDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.RecordUnnormalSignResponseDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.QmsErrorException;

/**
 * Qms差错自动上报
 * @author 231434-foss-bieyexiong
 * @date 2015-5-13 上午9:12:20
 */
public class QmsErrorReportService implements IQmsErrorReportService {

	/**
	 * 日志
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(QmsErrorReportService.class.getName());

	/**
	 * esb地址
	 */
	private String esbAdd;

	/**
	 * ESB编码
	 */
	private final static String ESB_CODE = "/ESB_FOSS2ESB_ERROR_AUTO_REPORT_FOSS";
	
	/**
	 * 异常签收上报url
	 */
	private String unnormalSignUrl;
	
	/**
	 * 重大货物异常上报url
	 */
	private String importantError;

	/**
	 * qms K反签收差错自动上报
	 * @author 231434-foss-bieyexiong
	 * @date 2015-5-13 上午9:23:01
	 */
	@Override
	public QmsErrorReportResponseDto reportQmsReverseSignError(Map<String, Object> errorInfoMap) {
		if(errorInfoMap == null){
			LOGGER.info("反签收差错上报QMS失败：" + QmsErrorException.QMS_ERRORS_INFO_NULL);
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INFO_NULL);
		}
		//差错类型编号
		String errorTypeId = (String)errorInfoMap.get(ReportConstants.ERRORTYPEID);
		//业务类型
		String errCategoty = (String)errorInfoMap.get(ReportConstants.ERRCATEGOTY);

		QmsErrorReportResponseDto resDto = null;
		//请求参数
		QmsErrorReportRequestDto dto = new QmsErrorReportRequestDto();
		//差错类型编号(反签收 快递/零担)
		dto.setErrorTypeId(errorTypeId);
		//业务类型(快递/零担)
		dto.setErrCategoty(errCategoty);
		//差错主信息
		dto.setMainInfo(errorInfoMap.get(ReportConstants.MAININFO));
		//业务类型为快递时
		if(ReportConstants.BUSINESSEXP.equals(errCategoty)){			
			//快递差错子信息
			dto.setKdsubHasInfo(errorInfoMap.get(ReportConstants.KDSUBHASINFO));
		}else{
			//零担差错子信息
			dto.setLdsubHasInfo(errorInfoMap.get(ReportConstants.LDSUBHASINFO));
		}
		//是否需要立即返回结果
		dto.setReturnResult(false);

		//将请求参数转换成json
		String requestJson = JSONObject.toJSONString(dto);

		try {
			//创建请求实体
			RequestEntity reqEntity = new StringRequestEntity(requestJson,"application/json","UTF-8");
			//post请求
			PostMethod post = new PostMethod(esbAdd+ESB_CODE);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader("Content-Type","application/json;charset=UTF-8");

			LOGGER.info("***************发送反签收差错上报QMS请求 Start**************");
			//发送请求
			new HttpClient().executeMethod(post);

			String resJson = post.getResponseBodyAsString();
			//返回参数dto
			resDto = JSONObject.parseObject(resJson,QmsErrorReportResponseDto.class);
			LOGGER.info(resDto.getMessage());
			LOGGER.info("***************发送反签收差错上报QMS请求 End**************");
		} catch (HttpException e) {
			LOGGER.error("反签收差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		} catch (IOException e) {
			LOGGER.error("反签收差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		} catch (Exception e) {
			LOGGER.error("反签收差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		}
		return resDto;
	}

	/**
	 * qms K内物短少差错自动上报
	 * @author 231434-foss-bieyexiong
	 * @date 2015-5-13 上午9:24:32
	 */
	@Override
	public QmsErrorReportResponseDto reportQmsShortError(Map<String, Object> errorInfoMap) {
		if(errorInfoMap == null){
			LOGGER.info("内物短少差错上报QMS失败：" + QmsErrorException.QMS_ERRORS_INFO_NULL);
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INFO_NULL);
		}
		//差错类型编号
		String errorTypeId = (String)errorInfoMap.get(ReportConstants.ERRORTYPEID);
		//业务类型
		String errCategoty = (String)errorInfoMap.get(ReportConstants.ERRCATEGOTY);

		QmsErrorReportResponseDto resDto = null;
		//请求参数
		QmsErrorReportRequestDto dto = new QmsErrorReportRequestDto();
		//差错类型编号(内物短少 快递/零担)
		dto.setErrorTypeId(errorTypeId);
		//业务类型(快递/零担)
		dto.setErrCategoty(errCategoty);
		//差错主信息
		dto.setMainInfo(errorInfoMap.get(ReportConstants.MAININFO));
		//业务类型为快递时
		if(ReportConstants.BUSINESSEXP.equals(errCategoty)){
			//快递差错子信息
			dto.setKdsubHasInfo(errorInfoMap.get(ReportConstants.KDSUBHASINFO));
		}else{
			//零担差错子信息
			dto.setLdsubHasInfo(errorInfoMap.get(ReportConstants.LDSUBHASINFO));
		}
		//是否需要立即返回结果
		dto.setReturnResult(false);

		//将请求参数转换成json
		String requestJson = JSONObject.toJSONString(dto);

		try {
			//创建请求实体
			RequestEntity reqEntity = new StringRequestEntity(requestJson,"application/json","UTF-8");
			//post请求
			PostMethod post = new PostMethod(esbAdd+ESB_CODE);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader("Content-Type","application/json;charset=UTF-8");

			LOGGER.info("***************发送内物短少差错上报QMS请求 Start**************");
			//发送请求
			new HttpClient().executeMethod(post);

			String resJson = post.getResponseBodyAsString();
			//返回参数dto
			resDto = JSONObject.parseObject(resJson,QmsErrorReportResponseDto.class);
			LOGGER.info(resDto.getMessage());
			LOGGER.info("***************发送内物短少差错上报QMS请求 End**************");
		} catch (ConnectTimeoutException e){
			LOGGER.error("内物短少差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException("连接服务端超时");
		} catch (SocketTimeoutException e){
			LOGGER.error("内物短少差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException("Socket连接超时");
		} catch (HttpException e) {
			LOGGER.error("内物短少差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		} catch (IOException e) {
			LOGGER.error("内物短少差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		} catch (Exception e) {
			LOGGER.error("内物短少差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		}
		return resDto;
	}
	
	/**
	 * @author 306548-foss-honglujun
	 * qms 重大货物异常自动上报
	 */
	@Override
	public QmsErrorReportResponseDto reportQmsImportantError(String errorInfo){
		if(errorInfo == null){
			LOGGER.info("重大货物异常差错上报QMS失败：" + QmsErrorException.QMS_ERRORS_INFO_NULL);
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INFO_NULL);
		}
//		//差错类型编号
//		String errorTypeId = (String)errorInfoMap.get(ReportConstants.ERRORTYPEID);
//		//业务类型
//		String errCategoty = (String)errorInfoMap.get(ReportConstants.ERRCATEGOTY);
//		//请求参数
//		QmsErrorReportRequestDto dto = new QmsErrorReportRequestDto();
//		//差错类型编号(反签收 快递/零担)
//		dto.setErrorTypeId(errorTypeId);
//		//业务类型(快递/零担)
//		dto.setErrCategoty(errCategoty);
//		//差错主信息
//		dto.setMainInfo(errorInfoMap.get(ReportConstants.MAININFO));
//		//零担差错子信息
//		dto.setLdsubHasInfo(errorInfoMap.get(ReportConstants.LDSUBHASINFO));
//		//是否需要立即返回结果
//		dto.setReturnResult(false);
		
		QmsErrorReportResponseDto resDto = null;
		try {
			LOGGER.info("-----------------json----1111111-----"+errorInfo);
			//创建请求实体
			RequestEntity reqEntity = new StringRequestEntity(errorInfo,"application/json","UTF-8");
			//post请求 LDERR_MAJORODDA_AUTO_REPORT_FOSS
			PostMethod post = new PostMethod(importantError);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			
			LOGGER.info("***************发送重大货物异常差错上报QMS请求 Start**************");
			//发送请求
			new HttpClient().executeMethod(post);
			
			String resJson = post.getResponseBodyAsString();
			LOGGER.info("-----------------json---22222------"+resJson);
			//返回参数dto
			resDto = JSONObject.parseObject(resJson,QmsErrorReportResponseDto.class);
			LOGGER.info("-----------------json---33333------"+resDto);
			LOGGER.info(resDto.getMessage());
			LOGGER.info("***************发送重大货物异常差错上报QMS请求 End**************");
		} catch (HttpException e) {
			LOGGER.error("重大货物异常差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		} catch (IOException e) {
			LOGGER.error("重大货物异常差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		} catch (Exception e) {
			LOGGER.error("重大货物异常差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		}
		return resDto;
	}

	/**
	 * qms 异常签收线上划责差错自动上报
	 * @author 231434-foss-bieyexiong
	 * @date 2016-2-23 上午10:28:32
	 */
	@Override
	public RecordUnnormalSignResponseDto reportQmsUnnormalSignError(String errorInfo) {
		if(StringUtils.isBlank(errorInfo)){
			LOGGER.info("异常签收线上划责差错上报QMS失败：" + QmsErrorException.QMS_ERRORS_INFO_NULL);
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INFO_NULL);
		}

		RecordUnnormalSignResponseDto resDto = null;

		try {
			//创建请求实体
			RequestEntity reqEntity = new StringRequestEntity(errorInfo,"application/json","UTF-8");
			//post请求
			PostMethod post = new PostMethod(unnormalSignUrl);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader("Content-Type","application/json;charset=UTF-8");

			LOGGER.info("***************发送异常签收线上划责差错上报QMS请求 Start**************");
			//发送请求
			new HttpClient().executeMethod(post);

			String resJson = post.getResponseBodyAsString();
			LOGGER.info("返回参数：" + resJson);
			//返回参数dto
			resDto = JSONObject.parseObject(resJson,RecordUnnormalSignResponseDto.class);
			LOGGER.info(resDto.getResultMsg());
			LOGGER.info("***************发送异常签收线上划责差错上报QMS请求 End**************");
		} catch (ConnectTimeoutException e){
			LOGGER.error("异常签收线上划责差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException("连接服务端超时");
		} catch (SocketTimeoutException e){
			LOGGER.error("异常签收线上划责差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException("Socket连接超时");
		} catch (HttpException e) {
			LOGGER.error("异常签收线上划责差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		} catch (IOException e) {
			LOGGER.error("异常签收线上划责差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		} catch (Exception e) {
			LOGGER.error("异常签收线上划责差错上报QMS失败：" + e.getMessage());
			throw new QmsErrorException(QmsErrorException.QMS_ERRORS_INACCESSIBLE);
		}
		return resDto;
	}

	public void setEsbAdd(String esbAdd) {
		this.esbAdd = esbAdd;
	}

	public void setUnnormalSignUrl(String unnormalSignUrl) {
		this.unnormalSignUrl = unnormalSignUrl;
	}

	public void setImportantError(String importantError) {
		this.importantError = importantError;
	}

}
