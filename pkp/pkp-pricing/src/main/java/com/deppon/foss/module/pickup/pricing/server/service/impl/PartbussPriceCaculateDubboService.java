package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOutbranchPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPartbussValueAddService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IldpCompanyValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.CaculateFeeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPartBusinessPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPartBusinessPriceResultDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IPartbussPriceCaculateService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理理公司运价计算Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-zhangdongping,date:2013-7-18 下午2:55:11 </p>
 * @author 094463-foss-zhangdongping
 * @date 2013-7-24 下午2:55:11
 * @since
 * @version
 */
public class PartbussPriceCaculateDubboService implements
	IPartbussPriceCaculateService {

	private static final double DOUBLE_05 = 0.5;
	private static final int TEN =10 ;
    
    IOutbranchPlanService outbranchPlanService;
    IldpCompanyValueAddService ldpCompanyValueAddService;
    IPartbussValueAddService partbussValueAddService;
	IPartbussPlanService partbussPlanService;
    /**
     * 小数点保留位数
     */
    private int newScale=2;
    private int newScaleTen=TEN;
    /**
     * 重量取数规则
     */
    private static final String ROUNDUP = "ROUNDUP";//向上取整
    private static final String ROUND = "ROUND";//四舍五入

    @Override
    /**
     *快递代理价格计算接口，包括运费和增值服务 
     * @see
     */
    public List<com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceResultDto> caculateFee(
	    List<com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceDto> dubboDtos) {
	if(CollectionUtils.isEmpty(dubboDtos))
	{
	    return null;
	}
	List<QueryPartBusinessPriceDto> dto = new ArrayList<QueryPartBusinessPriceDto>();
	QueryPartBusinessPriceDto priceDto = null;
	for(int i = 0; i < dubboDtos.size(); i++){
		priceDto = this.convertorPriceDto(dubboDtos.get(i));
		if(priceDto!=null){
			dto.add(priceDto);
		}
	}
	  OutbranchPlanEntity priceEntity=null;
	  PartbussValueAddEntity valueAddEntity=null;
	List<QueryPartBusinessPriceResultDto> resultList=new ArrayList<QueryPartBusinessPriceResultDto>();
	for (int i = 0; i < dto.size(); i++) {
	    QueryPartBusinessPriceDto queryParm=dto.get(i);
	    /*******************add by hehaisu***********************/
	    //获取重量取数规则
	    PartbussPlanEntity paramEntity = new PartbussPlanEntity();
	    paramEntity.setActive(FossConstants.YES);
	    if (queryParm.getReceiveDate() != null) {
	    	paramEntity.setBillDate(queryParm.getReceiveDate());
	    } else {
	    	paramEntity.setBillDate(new Date());
	    }
	    paramEntity.setExpressPartbussCode(queryParm.getExpressPartbussCode());
	    List<PartbussPlanEntity> partbussPlanEntities = partbussPlanService.queryInfos(paramEntity,Integer.MAX_VALUE,0);
	    //只对运费进行处理
	    if (CollectionUtils.isNotEmpty(partbussPlanEntities) 
	    		&& PriceEntityConstants.PRICING_CODE_FRT.equalsIgnoreCase(queryParm.getSubType())){
	    	PartbussPlanEntity entity = partbussPlanEntities.get(0);//获取重量取数规则
		    if (StringUtils.isNotBlank(entity.getWeightRule())) {
		    	if (ROUNDUP.equals(entity.getWeightRule())) {//向上取整
		    		BigDecimal weight = new BigDecimal(Math.ceil(queryParm.getWeight().doubleValue()));
		    		queryParm.setWeight(weight);
		    	} else if (ROUND.equals(entity.getWeightRule())) {//四舍五入
		    		if (queryParm.getWeight().doubleValue() < DOUBLE_05) {
		    			queryParm.setWeight(new BigDecimal(1));
		    		} else {
		    			BigDecimal weight = new BigDecimal(Math.round(queryParm.getWeight().doubleValue()));
		    			queryParm.setWeight(weight);
		    		}
		    	}
		    }
		}
	    /*******************end***********************/
	    
	   if(PriceEntityConstants.PRICING_CODE_FRT.equalsIgnoreCase(queryParm.getSubType()))
	      {
	       //如果是运费
	      priceEntity = outbranchPlanService.queryPriceByCode(queryParm.getOuterBranchCode(), queryParm.getReceiveDate());	  
  	      if(priceEntity==null)
	      {
		  // 为了接口能够通过，加入
		  	
		  continue;
	      }
	      
	           BigDecimal frtFee = caculate(priceEntity,queryParm);	      
	           QueryPartBusinessPriceResultDto resultDto=new QueryPartBusinessPriceResultDto();
		   resultDto.setCaculateFee(frtFee);
		   resultDto.setSubType(PriceEntityConstants.PRICING_CODE_FRT);		    
		   resultList.add(resultDto);	
	       
	     }
	   
	   else
	   {
	       if(valueAddEntity==null)
	       {
		   valueAddEntity = partbussValueAddService.queryInfosByPartCode(queryParm.getExpressPartbussCode(), queryParm.getReceiveDate(),queryParm.getDistrictCode());
	       }
	       
	       if(PriceEntityConstants.PRICING_CODE_BF.equalsIgnoreCase(queryParm.getSubType())&&valueAddEntity!=null)
	       {
		   //計算保费		   
		 
		   BigDecimal originnalCost = queryParm.getOriginnalCost();			   
		   MathContext mc=new MathContext(BigDecimal.ROUND_HALF_UP);
		   BigDecimal insdFee = originnalCost.multiply(valueAddEntity.getInsuranceFeeRate(), mc);		   
		   BigDecimal minFee = BigDecimal.valueOf(valueAddEntity.getMinInsuranceFee().doubleValue()/PricingConstants.YUTOFEN).setScale(newScale,  BigDecimal.ROUND_HALF_UP);
		   if(originnalCost.compareTo(new BigDecimal(0))>0 && insdFee.compareTo(minFee)	<0){
		       insdFee=  minFee;
		   }
		   QueryPartBusinessPriceResultDto resultDto=new QueryPartBusinessPriceResultDto();
		   resultDto.setCaculateFee(insdFee);
		   resultDto.setSubType(PriceEntityConstants.PRICING_CODE_BF);		    
		   resultList.add(resultDto);	  		   
	       }
	       if(PriceEntityConstants.PRICING_CODE_HK.equalsIgnoreCase(queryParm.getSubType())&&valueAddEntity!=null)
	       {
		   //計算代收货款
		   BigDecimal originnalCost = queryParm.getOriginnalCost();			   
		   MathContext mc=new MathContext(BigDecimal.ROUND_HALF_UP);
		   BigDecimal hkFee = originnalCost.multiply(valueAddEntity.getCodRate(), mc);		   
		   BigDecimal minFee = BigDecimal.valueOf(valueAddEntity.getMinCodFee().doubleValue()/PricingConstants.YUTOFEN).setScale(newScale,  BigDecimal.ROUND_HALF_UP);
		   if(originnalCost.compareTo(new BigDecimal(0))>0 && hkFee.compareTo(minFee)<0){
		       hkFee=  minFee;
		   }
		   QueryPartBusinessPriceResultDto resultDto=new QueryPartBusinessPriceResultDto();
		   resultDto.setCaculateFee(hkFee);
		   resultDto.setSubType(PriceEntityConstants.PRICING_CODE_HK);		    
		   resultList.add(resultDto);	  		   
	       }
	       if(PriceEntityConstants.PRICING_CODE_FRTFREE.equalsIgnoreCase(queryParm.getSubType())&&valueAddEntity!=null)
		      {
		         
		      if(queryParm.getToPayAmount()==null)
		      {
			  // 为了接口能够通过，加入
			  	
			  continue;
		      }
	           BigDecimal frtFee = caculateFrtFee(queryParm.getToPayAmount(),valueAddEntity);	      
	           QueryPartBusinessPriceResultDto resultDto=new QueryPartBusinessPriceResultDto();
			   resultDto.setCaculateFee(frtFee);
			   resultDto.setSubType(PriceEntityConstants.PRICING_CODE_FRTFREE);		    
			   resultList.add(resultDto);	
		       
		     }
	       
	      
	   }
	    
	   
	}
	List<com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceResultDto> resultDubboDtos = 
			new ArrayList<com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceResultDto>();
	com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceResultDto resultDubboDto = null;
	for(int i=0;i<resultList.size();i++){
		resultDubboDto = this.convertorResultPriceDto(resultList.get(i));
		resultDubboDtos.add(resultDubboDto);
	}
	return resultDubboDtos;
	
	
	
    }
    
    private com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceResultDto convertorResultPriceDto(
			QueryPartBusinessPriceResultDto queryPartBusinessPriceResultDto) {
    	com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceResultDto resultDubboDto = 
    			new com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceResultDto();
    	resultDubboDto.setCaculateFee(queryPartBusinessPriceResultDto.getCaculateFee());
    	resultDubboDto.setSubType(queryPartBusinessPriceResultDto.getSubType());
		return resultDubboDto;
	}

	private QueryPartBusinessPriceDto convertorPriceDto(
			com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.QueryPartBusinessPriceDto priceDubboDto) {
		if(priceDubboDto==null){
			return null;
		}
    	QueryPartBusinessPriceDto dtoEntity = new QueryPartBusinessPriceDto();
    	dtoEntity.setCurrencyCdoe(priceDubboDto.getCurrencyCdoe());
    	dtoEntity.setDistrictCode(priceDubboDto.getDistrictCode());
    	dtoEntity.setExpressPartbussCode(priceDubboDto.getExpressPartbussCode());
    	dtoEntity.setLoadOrgCode(priceDubboDto.getLoadOrgCode());
    	dtoEntity.setOriginnalCost(priceDubboDto.getOriginnalCost());
    	dtoEntity.setOuterBranchCode(priceDubboDto.getOuterBranchCode());
    	dtoEntity.setReceiveDate(priceDubboDto.getReceiveDate());
    	dtoEntity.setSubType(priceDubboDto.getSubType());
    	dtoEntity.setToPayAmount(priceDubboDto.getToPayAmount());
    	dtoEntity.setWeight(priceDubboDto.getWeight());
		return dtoEntity;
	}

	/**
     * 计算到付费用
     * @param waybillEntity
     * @param valueAddEntity
     * @return
     */

    private BigDecimal caculateFrtFee(BigDecimal toPayAmount,
			PartbussValueAddEntity valueAddEntity) {
    		//到付费率
		BigDecimal freightPayFeeRate = valueAddEntity.getFreightPayFeeRate() == null ? BigDecimal.ZERO : valueAddEntity.getFreightPayFeeRate();
		//最低一票
		BigDecimal minFreightPayFee = valueAddEntity.getMinFreightPayFee() == null ? BigDecimal.ZERO : valueAddEntity.getMinFreightPayFee();
		minFreightPayFee = BigDecimal.valueOf(minFreightPayFee.doubleValue()/PricingConstants.YUTOFEN).setScale(newScale,  BigDecimal.ROUND_HALF_UP);
		//到付费
		BigDecimal toPayFee = toPayAmount;
		//计算到付费
		MathContext mc=new MathContext(BigDecimal.ROUND_HALF_UP);
		BigDecimal toResFee = toPayFee.multiply(freightPayFeeRate,mc);
		if(toResFee.compareTo(minFreightPayFee) > 0){
			toPayAmount = toResFee ;
		}else{
			toPayAmount = minFreightPayFee;
		}
	
		return toPayAmount;
	}


	/**
     * 費用计算 
     * @author zhangdongping
     * @date Jul 25, 2013 3:31:33 PM
     * @param priceEntity
     * @return
     * @see
     */
    private  BigDecimal caculate(OutbranchPlanEntity priceEntity, QueryPartBusinessPriceDto queryParm) {
	
	if(CaculateFeeConstants.PRICERULE_EXPRESSION_FIXED.equalsIgnoreCase(priceEntity.getBillType()))
	{
	    List<OubrPlanDetailEntity> priceList= priceEntity.getPriceDetail();	     
	        double doubleWeight = queryParm.getWeight().doubleValue();
		
		  for (OubrPlanDetailEntity oubrPlanDetailEntity : priceList) {
		      double tempLeftrange = oubrPlanDetailEntity.getLeftrange().doubleValue();
		      double tempRightrange = oubrPlanDetailEntity.getRightrange().doubleValue();
		      if (doubleWeight > tempLeftrange && doubleWeight <= tempRightrange) {
			  
			 return  new BigDecimal(oubrPlanDetailEntity.getFee().doubleValue()/PricingConstants.YUTOFEN).setScale(newScale,  BigDecimal.ROUND_HALF_UP);
			  
			}		  
		  }
	}
	else if(CaculateFeeConstants.PRICERULE_EXPRESSION_ADD_WEIGHT.equalsIgnoreCase(priceEntity.getBillType()))
	{
	    List<OubrPlanDetailEntity> priceList= priceEntity.getPriceDetail();	  
	         int flagLoop=0;
	         double doubleWeight = queryParm.getWeight().doubleValue();
	         BigDecimal firstRate=BigDecimal.ZERO;
	         BigDecimal tempRate=BigDecimal.ZERO;
	         BigDecimal tempFee=BigDecimal.ZERO;
	         BigDecimal totalFee=BigDecimal.ZERO;
	         
	         //设置快递代理递代理网点重量上限值
	         double maxTempRightrange = 0;
	         //设置是否配置标识
	         boolean exitsFlag = false;
		 for (OubrPlanDetailEntity oubrPlanDetailEntity : priceList) {
		     flagLoop++;
		     tempRate = oubrPlanDetailEntity.getFee();
		     tempFee=BigDecimal.ZERO;
		     double tempLeftrange = oubrPlanDetailEntity.getLeftrange().doubleValue();
		     double tempRightrange = oubrPlanDetailEntity.getRightrange().doubleValue();
		     //每次计算时将每个区间最大重量赋值快递代理快递代理网点重量上限值
		     if(tempRightrange>maxTempRightrange){
		    	 maxTempRightrange = tempRightrange;
		     }
		     //如果在输入重量参数在当前重量的上下线之间，标记为true
		     if(doubleWeight>tempLeftrange&&doubleWeight<=tempRightrange){
		    	 exitsFlag = true;
		     }
		if (flagLoop == 1) {
		    if (doubleWeight > tempLeftrange
			    && doubleWeight <= tempRightrange) {
			// 若在等级1区间段，价格为等级1对应的价格
			return tempRate.divide(BigDecimal.valueOf(PricingConstants.YUTOFEN), newScale, BigDecimal.ROUND_HALF_UP);
			//return tempRate;
		    } else if (doubleWeight > tempRightrange) {
			// 大于等级一，先记下首重费用
			//firstRate = tempRate.divide(BigDecimal.valueOf(PricingConstants.YUTOFEN), newScale, BigDecimal.ROUND_HALF_UP);
			firstRate = tempRate;
		    } else {
			// 没有价格满足
			return BigDecimal.ZERO;
		    }

		} else {
		    if (doubleWeight > tempLeftrange
			    && doubleWeight <= tempRightrange) {
			 
//			tempFee= tempRate.multiply(BigDecimal.valueOf(doubleWeight - tempLeftrange).divide(oubrPlanDetailEntity.getDimension(),newScale,BigDecimal.ROUND_HALF_UP)).
//				divide(BigDecimal.valueOf(PricingConstants.YUTOFEN), newScale,BigDecimal.ROUND_HALF_UP);
			tempFee= tempRate.multiply(BigDecimal.valueOf(doubleWeight - tempLeftrange).divide(oubrPlanDetailEntity.getDimension(),newScaleTen,BigDecimal.ROUND_HALF_UP));
			 
		    } else if (doubleWeight > tempRightrange) {
			
//			tempFee= tempRate.multiply(BigDecimal.valueOf(tempRightrange - tempLeftrange).divide(oubrPlanDetailEntity.getDimension(),newScale,BigDecimal.ROUND_HALF_UP)).
//				divide(BigDecimal.valueOf(PricingConstants.YUTOFEN),newScale, BigDecimal.ROUND_HALF_UP);
			tempFee= tempRate.multiply(BigDecimal.valueOf(tempRightrange - tempLeftrange).divide(oubrPlanDetailEntity.getDimension(),newScaleTen,BigDecimal.ROUND_HALF_UP));
		    }
		}
		totalFee=totalFee.add(tempFee) ;			  
	  }
		 //快递代理置快递代理网点重量上限值小于传入的实际重量，即该重量的运价续重未配置
		 if(doubleWeight>maxTempRightrange){
			 throw new BusinessException("很抱歉，由于外发货物快递代理过了快递代理网点运价明细配置的最大重量上线，不能直接为您计算出运费");
		 }
		 if(!exitsFlag){
			 throw new BusinessException("很抱歉，由于外发货物重快递代理配置的快递代理网点运价明细重量区间，不能直接为您计算出运费");
		 }
		 //加上首重
		 totalFee=totalFee.add(firstRate).divide(BigDecimal.valueOf(PricingConstants.YUTOFEN), newScale, BigDecimal.ROUND_HALF_UP);
		 return totalFee;
	}
	 
	
	return null;
    }
    
    public IOutbranchPlanService getOutbranchPlanService() {
        return outbranchPlanService;
    }

    
    public void setOutbranchPlanService(IOutbranchPlanService outbranchPlanService) {
        this.outbranchPlanService = outbranchPlanService;
    }


    
    public IPartbussValueAddService getPartbussValueAddService() {
        return partbussValueAddService;
    }


    
    public void setPartbussValueAddService(
    	IPartbussValueAddService partbussValueAddService) {
        this.partbussValueAddService = partbussValueAddService;
    }
    
    
    public void setPartbussPlanService(IPartbussPlanService partbussPlanService) {
		this.partbussPlanService = partbussPlanService;
	}

	public void setLdpCompanyValueAddService(
			IldpCompanyValueAddService ldpCompanyValueAddService) {
		this.ldpCompanyValueAddService = ldpCompanyValueAddService;
	}
    
    

}
 