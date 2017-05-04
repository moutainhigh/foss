/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/service/impl/FOSSToOAService.java
 *  
 *  FILE NAME          :FOSSToOAService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-oa-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.oa.server.service.impl
 * FILE    NAME: FOSSToOAServiceImpl.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.esb.interceptor.ClientHeader;
import com.deppon.foss.esb.interceptor.WSHeaderHelper;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearMore;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportNolabel;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportSlipError;
import com.deppon.foss.module.transfer.common.api.shared.dto.ReportOALessGoodsFoundDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseNewDto;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.common.server.utils.UnifiedCodeConvertUtils;

import dip.integrateportal.businessmanagement.errors.foss.OAErrorsForFOSSImpl;

/**
 * foss上传数据给oa
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-16 下午6:17:35
 */
public class FOSSToOAService implements IFOSSToOAService {
	/**
	 * oa接口
	 */
	private OAErrorsForFOSSImpl oaErrorsForFOSSImpl;
	/**
	 * 编码转换工具
	 */
	private UnifiedCodeConvertUtils unifiedCodeConvertUtils;	

	/**
	 * 
	 */
	private static Logger LOGGER = LogManager.getLogger(FOSSToOAService.class);

	/**
	 * 上报无标签多货信息
	 * @param oaReportNolabel 无标签多货数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-16 下午6:28:53
	 */
	@Override
	public ResponseDto reportNolabel(OaReportNolabel oaReportNolabel) {
		// 转化为标杆编码
		if (!StringUtils.isEmpty(oaReportNolabel.getDeptOrgid())) {
			
			oaReportNolabel.setDeptOrgid(unifiedCodeConvertUtils.convertDeptCodeToUnifiedCode(oaReportNolabel.getDeptOrgid()));
		}
		ObjectMapper objectMapper = obtainJSONObjectMapper();
		String noLabel;
		ResponseDto responseDto = new ResponseDto();
		try {
			noLabel = objectMapper.writeValueAsString(oaReportNolabel);
			//
			// Client
			ClientHeader header = new ClientHeader();
			// 无标签运单号
			header.setBusinessId(oaReportNolabel.getNoLabelWayBill());
			header.setBusinessDesc1(oaReportNolabel.getNoLabelSerail());
			header.setEsbServiceCode(TransferConstants.OA_NO_LABEL_SERVICE_CODE);

			// header.setEsbSserviceCode(esbServiceCode)
			WSHeaderHelper.setClientHeader(header);
			// 调用OA接口
			String noLabelResponse = oaErrorsForFOSSImpl.reportNolabel(noLabel);
			// 获取返回对象
			responseDto = obtainResponse(noLabelResponse);
			return responseDto;
		} catch (JsonGenerationException e) {
			LOGGER.error(e);
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (JsonMappingException e) {
			LOGGER.error(e);
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (IOException e) {
			LOGGER.error(e);
			responseDto.setFailureReason(e.toString());
			return responseDto;
		}

	}
	
	
	/**
	* @description 根据OA的差错编号 更新状态
	* @param oaErrorID
	* @return 0:变更成功; 1:货物已被认领 ; 2：其他原因变更失败;
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月22日 下午3:08:43
	*/
	@Override
	public String updateGoodsArriverelay(String oaErrorID) {
		ClientHeader header = new ClientHeader();
		header.setBusinessId(oaErrorID);
		header.setEsbServiceCode(TransferConstants.OA_NO_LABEL_UPDATE_STATEOFGOODS);
		WSHeaderHelper.setClientHeader(header);
		String status = "2";
				
		try {
			LOGGER.error("#############开始调用根据OA的差错编号上报OA 更新无标签货物转弃货 状态 ############");
			LOGGER.error("#############传入OA字符串：" + oaErrorID);
			String  noLabelResponse = oaErrorsForFOSSImpl.updateGoodsArriverelay(oaErrorID);
			LOGGER.error("#############OA返回的字符串：" + noLabelResponse);
			ResponseNewDto temp = this.obtainResponse2(noLabelResponse);
			if(temp!=null){
				status = temp.getSuccee();
				if(StringUtils.isEmpty(status)){
					status="2";
				}
			}
			
			// 获取返回对象
		} catch (Exception e) {
			LOGGER.error("根据OA的差错编号上报OA 更新无标签货物转弃货 状态 发生异常：" + e.getMessage());
			return status;
		} 
		LOGGER.error("#############根据OA的差错编号上报OA 更新无标签货物转弃货 状态 接口结束############");
		return status;
	}



	/**
	 * 上报少货差错（清仓少货，卸车少货）
	 * @param oaReportClearless  差错信息
	 * @return NoLabelResponseDto(failureReason 错误原型，errorID 差错编号)
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:17:50
	 */
	@Override
	public ResponseDto reportLessGoods(OaReportClearless oaReportClearless) {
		ObjectMapper objectMapper = obtainJSONObjectMapper();
		// 转为字符串
		String lessGoods;
		ResponseDto responseDto = new ResponseDto();
		try {
			lessGoods = objectMapper.writeValueAsString(oaReportClearless);
			//
			ClientHeader header = new ClientHeader();
			header.setBusinessId(oaReportClearless.getWayBillId());
			header.setEsbServiceCode(TransferConstants.OA_LESS_GOODS_SERVICE_CODE);
			WSHeaderHelper.setClientHeader(header);
			LOGGER.error("#############开始调用上报OA少货接口############waybillNo:" + oaReportClearless.getWayBillId());
			// 调用OA接口
			LOGGER.error("###########传入OA的字符串：" + lessGoods);
			String lessResponse = oaErrorsForFOSSImpl.reportLessGoods(lessGoods);
			LOGGER.error("###########OA返回的字符串：" + lessResponse);
			// 获取返回对象
			responseDto = obtainResponse(lessResponse);
			LOGGER.error("#############调用上报OA少货接口结束############waybillNo:" + oaReportClearless.getWayBillId());
			return responseDto;
		} catch (JsonGenerationException e) {
			LOGGER.error("上报OA少货发生异常：waybillNo:" + oaReportClearless.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (JsonMappingException e) {
			LOGGER.error("上报OA少货发生异常：waybillNo:" + oaReportClearless.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (IOException e) {
			LOGGER.error("上报OA少货发生异常：waybillNo:" + oaReportClearless.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		}

	}

	/**
	 * 
	 * 上报多货差错（清仓多货，装车多货，卸车多货）
	 * @param oaReportClearMore  差错信息
	 * @return NoLabelResponseDto(failureReason 错误原型，errorID 差错编号)
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:19:40
	 */
	@Override
	public ResponseDto reportMoreGoods(OaReportClearMore oaReportClearMore) {

		ObjectMapper objectMapper = obtainJSONObjectMapper();

		// 转为字符串
		String moreGoods;
		ResponseDto responseDto = new ResponseDto();
		try {
			moreGoods = objectMapper.writeValueAsString(oaReportClearMore);
			// 设置头信息
			ClientHeader header = new ClientHeader();
			//运单号
			header.setBusinessId(oaReportClearMore.getWayBillId());
			header.setEsbServiceCode(TransferConstants.OA_MORE_GOODS_SERVICE_CODE);
			WSHeaderHelper.setClientHeader(header);
			LOGGER.error("#############开始调用上报OA多货接口############waybillNo:" + oaReportClearMore.getWayBillId());
			// 调用OA接口
			LOGGER.error("#############传入OA的字符串：" + moreGoods);
			String moreResponse = oaErrorsForFOSSImpl.reportMoreGoods(moreGoods);
			LOGGER.error("#############OA返回的字符串：" + moreResponse);
			// 获取返回对象
			responseDto = obtainResponse(moreResponse);
			LOGGER.error("#############调用上报OA多货接口结束############waybillNo:" + oaReportClearMore.getWayBillId());
			return responseDto;
		} catch (JsonGenerationException e) {
			LOGGER.error("上报OA多货发生异常：waybillNo:" + oaReportClearMore.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (JsonMappingException e) {
			LOGGER.error("上报OA多货发生异常：waybillNo:" + oaReportClearMore.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (IOException e) {
			LOGGER.error("上报OA多货发生异常：waybillNo:" + oaReportClearMore.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		}

	}

	/**
	 * 
	 * 上报封签差错
	 * 
	 * @param oaReportSlipError
	 *            封签信息
	 * @return NoLabelResponseDto(failureReason 错误原型，errorID 差错编号)
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:21:53
	 */
	@Override
	public ResponseDto reportSlipError(OaReportSlipError oaReportSlipError) {
		ObjectMapper objectMapper = obtainJSONObjectMapper();
		// 转为字符串
		String slipError;
		ResponseDto responseDto = new ResponseDto();
		try {
			slipError = objectMapper.writeValueAsString(oaReportSlipError);
			ClientHeader header = new ClientHeader();
			//车牌号
			header.setBusinessId(oaReportSlipError.getCarNo());
			//封签号码
			header.setBusinessDesc1(oaReportSlipError.getSlipNo());
			//扫描人工号
			header.setBusinessDesc2(oaReportSlipError.getScannerUserID());
			header.setEsbServiceCode(TransferConstants.OA_SLIP_ERROR_SERVICE_CODE);
			WSHeaderHelper.setClientHeader(header);
			// 调用OA接口
			String slipReponse = oaErrorsForFOSSImpl.reportSlipErrors(slipError);
			// 获取返回对象
			responseDto = obtainResponse(slipReponse);
			return responseDto;
		} catch (JsonGenerationException e) {
			LOGGER.error(e);
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (JsonMappingException e) {
			LOGGER.error(e);
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (IOException e) {
			LOGGER.error(e);
			responseDto.setFailureReason(e.toString());
			return responseDto;
		}

	}

	/**
	 * 上报少货找到
	 * 
	 * @param oaErrorNo 差错编号
	 * @param userId  发现人工号
	 * @param userName 发现人姓名
	 * @preOrg 上一部门发现人工号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:39:44
	 */
	@Override
	public ResponseDto reportLessGoodsFound(String oaErrorNo, String userCode, String userName, String preOrg) {
		ObjectMapper objectMapper = obtainJSONObjectMapper();
		// 转为字符串
		String reportInfo;
		ResponseDto responseDto = new ResponseDto();

		ClientHeader header = new ClientHeader();
		header.setEsbServiceCode(TransferConstants.OA_LESS_GOODS_FOUND_SERVICE_CODE);
		//少货差错编号
		header.setBusinessId(oaErrorNo);
		WSHeaderHelper.setClientHeader(header);

		ReportOALessGoodsFoundDto dto = new ReportOALessGoodsFoundDto();
		dto.setOaErrorNo(oaErrorNo);
		dto.setUserCode(userCode);
		dto.setUserName(userName);
		dto.setPreOrg(preOrg);

		try {
			reportInfo = objectMapper.writeValueAsString(dto);
			LOGGER.error("#############开始调用上报OA少货已找到接口############");
			LOGGER.error("#############传入OA字符串：" + reportInfo);
			String lessFoundResponse = oaErrorsForFOSSImpl.reportLessFound(reportInfo);// .queryLessFound(oaErrorNo,
			LOGGER.error("#############OA返回的字符串：" + lessFoundResponse);
			// 获取返回对象
			responseDto = objectMapper.readValue(lessFoundResponse, ResponseDto.class);
		} catch (JsonGenerationException e) {
			LOGGER.error("上报OA少货找到发生异常：" + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (JsonMappingException e) {
			LOGGER.error("上报OA少货找到发生异常：" + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (IOException e) {
			LOGGER.error("上报OA少货找到发生异常：" + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		}
		LOGGER.error("#############调用上报OA少货已找到接口结束############");
		return responseDto;
	}

	/**
	 * 
	 * 查询卸车差异报告处理状态
	 * 
	 * @param errorID
	 *            差异报告编号
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-1 下午5:13:35
	 */
	@Override
	public String queryUnloadErrorStatus(String errorID) {
		ClientHeader header = new ClientHeader();
		//差错编号
		header.setBusinessId(errorID);
		header.setEsbServiceCode(TransferConstants.OA_UNLOAD_ERROR_STATUS_SERVICE_CODE);
		WSHeaderHelper.setClientHeader(header);
		String state = oaErrorsForFOSSImpl.queryUnloadiff(errorID);
		return state;
	}

	/**
	 * 
	 * 查询违禁品信息
	 * @param waybillNo  运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-6 上午9:11:10
	 * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToOAService#queryContrabandGoods(java.lang.String)
	 */
	@Override
	public String queryContrabandGoods(String waybillNo) {
		ClientHeader header = new ClientHeader();
		header.setBusinessId(waybillNo);
		header.setEsbServiceCode(TransferConstants.OA_CONTRABAND_GOODS_SERVICE_CODE);
		WSHeaderHelper.setClientHeader(header);
		String errorID = oaErrorsForFOSSImpl.queryContraband(waybillNo);
		LOGGER.error(errorID);
		return errorID;
	}

	/**
	 * 
	 * 将字符串转为JAVA对象
	 * 
	 * @param response
	 *            OA返回值
	 * @return ResponseDto
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 上午9:30:04
	 */
	private ResponseDto obtainResponse(String response) {

		if (response == null) {
			return null;
		} else {
			ResponseDto responseDto;
			try {
				responseDto = JSONObject.parseObject(response, ResponseDto.class);
				return responseDto;
			} catch (Exception e) {
				return null;
			}
		}

	}
	
	
	/**
	* @description 讲json转换为ResponseNewDto对象
	* @param response
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月25日 下午5:56:36
	*/
	private ResponseNewDto obtainResponse2(String response){
		if (response == null) {
			return null;
		} else {
			ResponseNewDto responseDto;
			try {
				responseDto = JSONObject.parseObject(response, ResponseNewDto.class);
				return responseDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-6 上午11:50:04
	 */
	@SuppressWarnings("deprecation")
	private ObjectMapper obtainJSONObjectMapper() {
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置到objectMapper
		objectMapper.getSerializationConfig().setDateFormat(dateFormat);
		// objectMapper.getSerializationConfig().withDateFormat(dateFormat);
		return objectMapper;
	}


	/**
	 * 设置 oa接口.
	 * 
	 * @param oaErrorsForFOSSImpl
	 *            the new oa接口
	 */
	public void setOaErrorsForFOSSImpl(OAErrorsForFOSSImpl oaErrorsForFOSSImpl) {
		this.oaErrorsForFOSSImpl = oaErrorsForFOSSImpl;
	}

	public void setUnifiedCodeConvertUtils(
			UnifiedCodeConvertUtils unifiedCodeConvertUtils) {
		this.unifiedCodeConvertUtils = unifiedCodeConvertUtils;
	}
	
	

}