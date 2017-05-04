package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryOuterPriceCaccilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultOuterPriceCaccilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.OuterPriceCaculateServiceException;
import com.google.inject.Inject;

public class OuterPriceCaculateService implements IOuterPriceCaculateService{
	
	/** 日志 */
	private static Log log = LogFactory.getLog(OuterPriceCaculateService.class);

	/**
	 * 偏线中转费服务
	 */
	@Inject
	IOuterPriceService outerPriceService;
	
	public void setOuterPriceService(IOuterPriceService outerPriceService) {
		this.outerPriceService = outerPriceService;
	}


	/**
	 * 计算偏线中转费
	 * @param queryOuterPriceCaccilateDto
	 */
	@Override
	public ResultOuterPriceCaccilateDto calulateOuterPrice(
			QueryOuterPriceCaccilateDto queryOuterPriceCaccilateDto) {
		
		checkPamenter(queryOuterPriceCaccilateDto);
		OuterPriceEntity outerPriceEntity = outerPriceService.searchOuterPriceByArgument(queryOuterPriceCaccilateDto.getPartialLineCode(), queryOuterPriceCaccilateDto.getOutFieldCode(),
				queryOuterPriceCaccilateDto.getProductCode(), queryOuterPriceCaccilateDto.getReceiveDate());
		ResultOuterPriceCaccilateDto resultOuterPriceCaccilateDto = null;
		if(null!=outerPriceEntity){
			//最低一票
			Long minfee = outerPriceEntity.getMinFee();
			resultOuterPriceCaccilateDto = new ResultOuterPriceCaccilateDto();
			BigDecimal voluemeFee = queryOuterPriceCaccilateDto.getVolume().multiply(outerPriceEntity.getVolumeFeeRate());
		
			//重量计费是否如果小于最低一票
			if(voluemeFee.compareTo(BigDecimal.valueOf(minfee))==-1){
				voluemeFee = BigDecimal.valueOf(minfee);
			}
			
			//体积计费是否如果小于最低一票
			BigDecimal weightFee = queryOuterPriceCaccilateDto.getWeight().multiply(outerPriceEntity.getWeightFeeRate());
			if(weightFee.compareTo(BigDecimal.valueOf(minfee))==-1){
				weightFee = BigDecimal.valueOf(minfee);
			}
			
			//实际费用
			BigDecimal finalFee = weightFee.doubleValue() > voluemeFee.doubleValue() ? weightFee: voluemeFee;
			
			//如果当前是最低一票,设置最低一票信息以及费率费用信息
			if(finalFee.compareTo(BigDecimal.valueOf(minfee))==0){
				resultOuterPriceCaccilateDto.setMinFee(BigDecimal.valueOf(minfee));
				BigDecimal tempVoluemeFee = queryOuterPriceCaccilateDto.getVolume().multiply(outerPriceEntity.getVolumeFeeRate());
				BigDecimal tempWeightFee = queryOuterPriceCaccilateDto.getWeight().multiply(outerPriceEntity.getWeightFeeRate());
				if(tempVoluemeFee.compareTo(tempWeightFee)==1){
					resultOuterPriceCaccilateDto.setRateFee(outerPriceEntity.getVolumeFeeRate());
				}else{
					resultOuterPriceCaccilateDto.setRateFee(outerPriceEntity.getWeightFeeRate());
				}
			}else{
				//默认按照重量费率(偏线如果重量计费与体积计费相等，会报错，需要有一种默认方式，根据BUG-53987修改)
				resultOuterPriceCaccilateDto.setRateFee(outerPriceEntity.getWeightFeeRate());
				//如果体积大于重量，设置体积为实际费率
				if(voluemeFee.compareTo(weightFee)==1){
					resultOuterPriceCaccilateDto.setRateFee(outerPriceEntity.getVolumeFeeRate());
				}
				//如果重量大于体积，设置体积为实际费率
				if(voluemeFee.compareTo(weightFee)==-1){
					resultOuterPriceCaccilateDto.setRateFee(outerPriceEntity.getWeightFeeRate());
				}
			}
			resultOuterPriceCaccilateDto.setCalateFee(finalFee);
			resultOuterPriceCaccilateDto.setProductCode(outerPriceEntity.getProductCode());
			resultOuterPriceCaccilateDto.setPartialLineCode(outerPriceEntity.getPartialLineCode());
			resultOuterPriceCaccilateDto.setOutFieldCode(outerPriceEntity.getOutFieldCode());
			resultOuterPriceCaccilateDto.setVolume(outerPriceEntity.getVolumeFeeRate());
			resultOuterPriceCaccilateDto.setWeight(outerPriceEntity.getWeightFeeRate());
			resultOuterPriceCaccilateDto.setMinFee(BigDecimal.valueOf(minfee));
			return resultOuterPriceCaccilateDto;
		}
		StringBuilder logMsg = new StringBuilder();
		logMsg.append("未查询到偏线价格方案：");
		logMsg.append("##partialLineCode=").append(queryOuterPriceCaccilateDto.getPartialLineCode());
		logMsg.append("##outFieldCode=").append(queryOuterPriceCaccilateDto.getOutFieldCode());
		logMsg.append("##productCode=").append(queryOuterPriceCaccilateDto.getProductCode());
		logMsg.append("##receiveDate=").append(queryOuterPriceCaccilateDto.getReceiveDate());
		log.error(logMsg.toString());
		throw new OuterPriceCaculateServiceException("未查询到偏线价格方案！");
	}
	
	
	public void checkPamenter(QueryOuterPriceCaccilateDto queryOuterPriceCaccilateDto){
		if(null == queryOuterPriceCaccilateDto){
			throw new OuterPriceCaculateServiceException("计算偏线费用所传入的参数不能为空!");
		}
		if(null == queryOuterPriceCaccilateDto.getOutFieldCode()){
			throw new OuterPriceCaculateServiceException("计算偏线费用所传入的外场编码不能为空!");
		}
		if(null == queryOuterPriceCaccilateDto.getVolume()){
			throw new OuterPriceCaculateServiceException("计算偏线费用所传入的体积不能为空!");
		}
		if(null == queryOuterPriceCaccilateDto.getWeight()){
			throw new OuterPriceCaculateServiceException("计算偏线费用所传入的重量不能为空!");
		}
		if(null==queryOuterPriceCaccilateDto.getReceiveDate()){
			throw new OuterPriceCaculateServiceException("计算偏线费用所传入的业务不能为空!");
		}
		if(StringUtils.isEmpty(queryOuterPriceCaccilateDto.getPartialLineCode())){
			throw new OuterPriceCaculateServiceException("计算偏线费用所传入的偏线参数不能为空!");
		}
		if(StringUtils.isEmpty(queryOuterPriceCaccilateDto.getProductCode())){
			throw new OuterPriceCaculateServiceException("计算偏线费用所传入的产品参数不能为空!");
		}
	}


}
