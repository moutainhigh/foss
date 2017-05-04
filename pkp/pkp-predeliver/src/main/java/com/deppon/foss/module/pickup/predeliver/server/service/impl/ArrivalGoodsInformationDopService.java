package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsRequestDto;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArrivalGoodsInformationDopService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsResponseDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;

/**
 * FOSS给DOP主动推送到货信息(特殊增值服务类运单)
 * @author 243921-FOSS-zhangtingting
 * @date 2015-09-12 上午08:52:23
 */
public class ArrivalGoodsInformationDopService implements
		IArrivalGoodsInformationDopService {

	/**
	 * Logger实例
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ArrivalGoodsInformationDopService.class);
	
	/**
	 * esb地址
	 */
	private String esbAdd;
	
	/**
	 * 给DOP推送到货信息
	 * @author 243921-FOSS-zhangtingting
	 * @date 2015-09-12 上午08:53:07
	 * @param waybillNO
	 * @return
	 */
	@Override
	public ArrivalGoodsResponseDto arrivalGoodsInformation(String waybillNO) {
		if(StringUtils.isBlank(waybillNO)){
			LOGGER.info("货物到达信息推送给DOP失败：" + NotifyCustomerException.WAYBILLNO_IS_NULL);
			throw new NotifyCustomerException(NotifyCustomerException.WAYBILLNO_IS_NULL);
		}
		
		//响应的参数
		ArrivalGoodsResponseDto resDto = new ArrivalGoodsResponseDto();
		
		//请求参数
		ArrivalGoodsRequestDto dto = new ArrivalGoodsRequestDto();
		//运单号
		dto.setMailNo(waybillNO);
		//货物到达信息
		dto.setArriveInfo("货物到达");
		
		//将请求参数转换成json
		String requestJson = JSONObject.toJSONString(dto);
		
		try {
			//创建请求实体
			RequestEntity reqEntity = new StringRequestEntity(requestJson,"application/json","UTF-8");
			//post请求
			PostMethod post = new PostMethod(esbAdd);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			
			LOGGER.info("***************发送到货信息请求 Start**************");
			//发送请求
			new HttpClient().executeMethod(post);
			
			String resJson = post.getResponseBodyAsString();
			//返回的结果
			resDto = JSONObject.parseObject(resJson,ArrivalGoodsResponseDto.class);

			String params = new String(resDto.getMessage().getBytes("ISO-8859-1"),"utf-8");
			LOGGER.info("********运单"+waybillNO+"到货信息推送"+params+"********");
			LOGGER.info("***************发送到货信息请求 End**************");
		} catch (Exception e) {
			LOGGER.error("货物到达信息推送给DOP失败：" + e.getMessage());
			throw new NotifyCustomerException(NotifyCustomerException.ARRIVAL_GOODS_INACCESSIBLE);
		}
		return resDto;
	}
	public void setEsbAdd(String esbAdd) {
		this.esbAdd = esbAdd;
	}

}
