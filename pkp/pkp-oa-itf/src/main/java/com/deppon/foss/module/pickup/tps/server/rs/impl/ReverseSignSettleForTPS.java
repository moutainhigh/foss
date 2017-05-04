package com.deppon.foss.module.pickup.tps.server.rs.impl;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.sign.api.server.service.ITPSReverseSignSettleService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.TPSReverseSignSettleRequestDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.TPSReverseSignSettleResponseDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.tps.server.rs.IReverseSignSettleForTPS;

/**
 * 同步TPS反签收信息
 * @author 243921 zhangtingting
 * @date 2017-03-02 14:56:37
 *
 */
public class ReverseSignSettleForTPS implements IReverseSignSettleForTPS {

	/**
	* 日志
	*/
	private static final Logger LOGGER = LoggerFactory.getLogger(ReverseSignSettleForTPS.class);

	@Context
	protected HttpServletResponse res;

	private ITPSReverseSignSettleService tpsReverseSignSettleService;

	@Override
	public String reverseSignSettle(String jsonStr) {

		LOGGER.info("TPS推送反签收反结清数据开始....." );

		res.setHeader("ESB-ResultCode", "1");

		//响应的参数
		TPSReverseSignSettleResponseDto response = new TPSReverseSignSettleResponseDto();
		//判空
		if(StringUtils.isBlank(jsonStr)){
			response.setIfSuccess(false);
			response.setErrorMsg("TPS传给FOSS的参数为空！");
			LOGGER.info("TPS传给FOSS的参数为空！");

			return JSON.toJSONString(response);
		}

		//请求的参数
		TPSReverseSignSettleRequestDto request = JSONObject.parseObject(jsonStr, TPSReverseSignSettleRequestDto.class);

		String waybillNo = request.getWaybillNo();//运单号
		String reverseType = request.getAutoReverseType();//自动反操作类型：unsign-反签收   unsettle-反结清

		try{

			//反签收校验+处理
			if("unsign".equals(reverseType)){
				LOGGER.info("TPS推送反签收信息开始！单号为：" + waybillNo);

				tpsReverseSignSettleService.reverseSign(waybillNo);

				response.setIfSuccess(true);
				response.setErrorMsg("FOSS反签收成功success!");
			}

			//反结清校验+处理
			if("unsettle".equals(reverseType)){
				LOGGER.info("TPS推送反结清开始！单号为：" + waybillNo);
				tpsReverseSignSettleService.reverseSettle(waybillNo);

				response.setIfSuccess(true);
				response.setErrorMsg("FOSS反结清成功success!");
			}
			LOGGER.info("TPS推送反签收反结清数据至FOSS响应成功!" + "单号为：" + waybillNo);
		}catch(SignException se){
			se.printStackTrace();
			LOGGER.info("SignException层："+se.getMessage()+"单号为："+waybillNo);
			// 日志处理
			LOGGER.error(se.getErrorCode(), se);

			response.setIfSuccess(false);
			response.setErrorMsg("反签收反结清处理异常:"+se.getErrorCode());
		}catch (BusinessException ex) {
			LOGGER.info("BusinessException层："+ex.getMessage()+"单号为："+waybillNo);
			// 日志处理
			LOGGER.error(ex.getErrorCode(), ex);

			response.setIfSuccess(false);
			response.setErrorMsg("反签收反结清处理异常:"+ex.getErrorCode());
		}catch(Exception e){
			LOGGER.info("Exception层："+e.getMessage()+"单号为："+waybillNo);
			// 日志处理
			LOGGER.error(e.getMessage(), e);

			response.setIfSuccess(false);
			response.setErrorMsg("反签收反结清处理异常:"+e);
		}

		return JSON.toJSONString(response);
	}

	public void setTpsReverseSignSettleService(
			ITPSReverseSignSettleService tpsReverseSignSettleService) {
		this.tpsReverseSignSettleService = tpsReverseSignSettleService;
	}

}
