package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultDiscountDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.CountFreightEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.DiscountProgramEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.FreightEffectEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.FreightResEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PricingEntry;


/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file CountFreightService.java 
 * @description 快递统计开单运费服务类
 * @author ChenLiang
 * @created 2013-1-14 上午11:37:24    
 * @version 1.0
 */
public class KdFreightEffectivenService  implements IBusinessService<List<FreightEffectEntity>, CountFreightEntity> {
	private static Logger log = Logger.getLogger(KdCountFreightService.class);
	
	private IPdaPriceService pdaPriceService;

	public void setPdaPriceService(IPdaPriceService pdaPriceService) {
		this.pdaPriceService = pdaPriceService;
	}

	/**
	 * 
	 * @description 包体解析
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public CountFreightEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		CountFreightEntity countFreight = JsonUtil.parseJsonToObject(CountFreightEntity.class, asyncMsg.getContent());
		return countFreight;
	}
	
	/**
	 * 
	 * @description 数据合法性校验
	 * @param asyncMsg
	 * @param countFreight
	 * @throws PdaBusiException 
	 * @created 2013-1-14 下午2:09:04
	 */
	private void validate(AsyncMsg asyncMsg, CountFreightEntity countFreight) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//PDA编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		Argument.notNull(countFreight, "CountFreightEntity");
		// 出发部门编码
		Argument.hasText(countFreight.getOriginalOrgCode(), "CountFreightEntity.originalOrgCode");
		// 到达部门编码
		Argument.hasText(countFreight.getDestinationOrgCode(), "CountFreightEntity.destinationOrgCode");
		//Argument.hasText(countFreight.getProductCode(), "CountFreightEntity.productCode");// 产品编码
		// 开单日期
		Argument.notNull(countFreight.getReceiveDate(), "CountFreightEntity.receiveDate");
		// 重量
		Argument.isPositiveNum(countFreight.getWeight(), "CountFreightEntity.weight");
		// 体积
		//Argument.isPositiveNum(countFreight.getVolume(), "CountFreightEntity.volume");
		// 币种
		Argument.hasText(countFreight.getCurrencyCode(), "CountFreightEntity.currencyCode");
		//Argument.hasText(countFreight.getCustomerCode(), "CountFreightEntity.customerCode");// 客户编码
		//Argument.hasText(countFreight.getGoodsCode(), "CountFreightEntity.goodsCode");// 货物类型
		//Argument.hasText(countFreight.getIsReceiveGoods(), "CountFreightEntity.isReceiveGoods");// 是否接货
		//Argument.hasText(countFreight.getFlightShift(), "CountFreightEntity.flightShift");// 航班班次
		//Argument.hasText(countFreight.getIndustrulCode(), "CountFreightEntity.industrulCode");// 行业
		//Argument.hasText(countFreight.getChannelCode(), "CountFreightEntity.channelCode");// 订单渠道
		// 计价条目列表
		Argument.notEmpty(countFreight.getPriciings(), "CountFreightEntity.priciings");
	}

	/**
	 * 
	 * @description 开单运费计算
	 * @param asyncMsg
	 * @param countFreight
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public List<FreightEffectEntity> service(AsyncMsg asyncMsg, CountFreightEntity countFreight) throws PdaBusiException {
		
		//最终返回结果
		List<FreightEffectEntity> result = null;
				
		this.validate(asyncMsg, countFreight);
		
	    boolean isFlag = false; //是否需要两次调用计算运费操作(原件返单需要调两次)
		
		PdaQueryBillCalculateDto billCalculateDto = new PdaQueryBillCalculateDto();
		// 订单渠道
		billCalculateDto.setChannelCode(countFreight.getChannelCode());
		// 币种
		billCalculateDto.setCurrencyCdoe(countFreight.getCurrencyCode());
		// 客户编码
		billCalculateDto.setCustomerCode(countFreight.getCustomerCode());
		// 到达部门编码
		billCalculateDto.setDestinationOrgCode(countFreight.getDestinationOrgCode());
		// 航班班次
		billCalculateDto.setFlightShift(countFreight.getFlightShift());
		// 货物类型
		billCalculateDto.setGoodsCode(countFreight.getGoodsCode());
		// 行业
		billCalculateDto.setIndustrulCode(countFreight.getIndustrulCode());
		// 是否接货
		billCalculateDto.setIsReceiveGoods(countFreight.getIsReceiveGoods());
		// 出发部门编码
		billCalculateDto.setOriginalOrgCode(countFreight.getOriginalOrgCode());
		// 产品编码
		billCalculateDto.setProductCode(countFreight.getProductCode());
		// 开单日期
		billCalculateDto.setReceiveDate(countFreight.getReceiveDate());
		// 体积
		billCalculateDto.setVolume(BigDecimal.valueOf(countFreight.getVolume()));
		// 重量
		billCalculateDto.setWeight(BigDecimal.valueOf(countFreight.getWeight()));
		//是否自提
		billCalculateDto.setIsSelfPickUp(countFreight.getIsSelfPickUp());
		
		 //CRM 二期上传营销活动编码和名称
		MarkActivitiesQueryConditionDto markActivit = new MarkActivitiesQueryConditionDto();
		markActivit.setCode(countFreight.getMarketingCode());
		//
		billCalculateDto.setActiveDto(markActivit);
		//出发外场
		billCalculateDto.setStartOutFieldCode(countFreight.getStartOutFieldCode());
		//到达外场
		billCalculateDto.setArriveOutFieldCode(countFreight.getArriveOutFieldCode());
		
		//营销活动编码
		//billCalculateDto.setCityMarketCode(countFreight.getMarketingCode());
		
		log.info("MarketingCode:"+countFreight.getMarketingCode());	
		log.info("StartOutFieldCode:"+countFreight.getStartOutFieldCode());				
		log.info("ArriveOutFieldCode:"+countFreight.getArriveOutFieldCode());
		log.info("ChannelCode:"+billCalculateDto.getChannelCode());
		log.info("CurrencyCdoe:"+billCalculateDto.getCurrencyCdoe());
		log.info("CustomerCode:"+billCalculateDto.getCustomerCode());
		log.info("DestinationOrgCode:"+billCalculateDto.getDestinationOrgCode());
		log.info("FlightShift:"+billCalculateDto.getFlightShift());
		log.info("GoodsCode:"+billCalculateDto.getGoodsCode());
		log.info("IndustrulCode:"+billCalculateDto.getIndustrulCode());
		log.info("IsReceiveGoods:"+billCalculateDto.getIsReceiveGoods());
		log.info("OriginalOrgCode:"+billCalculateDto.getOriginalOrgCode());
		log.info("ProductCode:"+billCalculateDto.getProductCode());
		log.info("ReceiveDate:"+billCalculateDto.getReceiveDate());
		log.info("Volume:"+billCalculateDto.getVolume().doubleValue());
		log.info("Weight:"+billCalculateDto.getWeight().doubleValue());
		log.info("isSelfPickUp:"+billCalculateDto.getIsSelfPickUp());
		List<PricingEntry> priciings = countFreight.getPriciings();
		List<PdaQueryBillCalculateSubDto> priceEntities = new ArrayList<PdaQueryBillCalculateSubDto>();
		PdaQueryBillCalculateSubDto dto = null;
		if (priciings != null && !priciings.isEmpty()) {
			for (PricingEntry pricingEntry : priciings) {
				dto = new PdaQueryBillCalculateSubDto();
				// 原始费用
				dto.setOriginnalCost(BigDecimal.valueOf(pricingEntry.getOriginnalCost()));
				log.info("OriginnalCost:"+dto.getOriginnalCost().doubleValue());
				// 计价条目编码
				dto.setPriceEntityCode(pricingEntry.getPricingEntryCode());
				log.info("PriceEntityCode:"+dto.getPriceEntityCode());
				// 子类型
				
				if("ORIGINAL".equals(pricingEntry.getSubType())){
				//如果是原件返单 需要第二次调用接口
				    isFlag=true; 
				}
				
				dto.setSubType(pricingEntry.getSubType());
				log.info("SubType:"+dto.getSubType());
				//木架体积
				dto.setWoodenVolume(BigDecimal.valueOf(pricingEntry.getWoodenVolume()));
				log.info("WoodenVolume:"+dto.getWoodenVolume().doubleValue());
				priceEntities.add(dto);
			}
		}
		// 计价条目列表
		billCalculateDto.setPriceEntities(priceEntities);
		
		log.info("---调用FOSS快递运单费用计算接口开始---");
	
		Map<String,List<PdaResultBillCalculateDto>> map = null;
		try {
			map = pdaPriceService.queryPdaExpressBillPriceNoProduct(billCalculateDto);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.info("---调用FOSS快递运单费用计算接口结束---");
		//没有查询到运费
		if(map == null){			
			return null;
		}
		
		result = new ArrayList<FreightEffectEntity>();		
		FreightEffectEntity detail = null;
		
		Iterator<String> it=map.keySet().iterator(); 
		while(it.hasNext()){ 
			detail = new FreightEffectEntity();						
		    String key; 
		    key=it.next().toString(); 		    
		    List<PdaResultBillCalculateDto>  lists=map.get(key); 
		    
			List<FreightResEntity> freightResList = new ArrayList<FreightResEntity>();

			if (lists != null && !lists.isEmpty()) {
				for(PdaResultBillCalculateDto pdaDto : lists) {
				    if(isFlag &&"ORIGINAL".equals(pdaDto.getSubType())){
				        //原件返单第一次调不保存
				        continue;
				    }
				    // 将值付给PDA可以解析的实体
					freightResList.add(inFreightResEntity(pdaDto));
				}
			}
			
			detail.setProduct(key);
			detail.setFreightResEntitys(freightResList);	 
			
			result.add(detail);
		} 		
	
		/*
		 * 第二次调用计算运费
		 */
		if(isFlag){
		    //重量赋值最小
		    billCalculateDto.setWeight(BigDecimal.valueOf(0.1));
		    //体积赋值最小
		    billCalculateDto.setVolume(BigDecimal.valueOf(0.0001));
		    //是否自提为N  送货
		    billCalculateDto.setIsSelfPickUp("N");	
		    
		    log.info("---原件返单第二次调用FOSS快递运单费用计算接口开始---");

		    try {
		    	map =  pdaPriceService.queryPdaExpressBillPriceNoProduct(billCalculateDto);
	        } catch (BusinessException e) {
	            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
	        }		
			Iterator<String> its=map.keySet().iterator(); 
					while(its.hasNext()){ 				
					  String key; 
					  key=its.next().toString(); 		    
					  List<PdaResultBillCalculateDto>  listsSeconds=map.get(key); 
						    log.info("---原件返单第二次调用FOSS快递运单费用计算接口结束---");
						    if (listsSeconds != null && !listsSeconds.isEmpty()) {
						       //第二次调用时。将返回的运费当做签收回单费用使用  
					            for(PdaResultBillCalculateDto pdaDto : listsSeconds) {
					                if(isFlag && "FRT".equals(pdaDto.getPriceEntityCode())){
					                    //找到运费那项进行处理改成签收回单的，保存到返回PDA前台的集合中
					                    pdaDto.setSubType("ORIGINAL");
					                    pdaDto.setPriceEntityName("签收回单");
					                    pdaDto.setPriceEntityCode("QS");			                     
					                    
					                    for(int i = 0;i<result.size();i++)
					                    		{
					                    	if(result.get(i).getProduct().equals(key)){
					                    		result.get(i).getFreightResEntitys().add(inFreightResEntity(pdaDto));
					                    	   break;
					                    	}
					                    	
					                    } 			                    			                    			                    
					                }	                
					            }
					            
					            
					            
					        }
						 
						 
					}
		    
		      isFlag=false;
		   }				
		log.info("---返回快递运单费用计算信息成功---");
		return result;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_EFFECT_FREIGHT.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	/**
	 * 赋值返回结果
	 */
	public FreightResEntity inFreightResEntity(PdaResultBillCalculateDto pdaDtoTemp) {
	    
	    FreightResEntity freightRes = new FreightResEntity();
        // 最终费率
        freightRes.setActualFeeRate(pdaDtoTemp.getActualFeeRate()==null?0:pdaDtoTemp.getActualFeeRate().doubleValue());
        log.info("ActualFeeRate:"+(pdaDtoTemp.getActualFeeRate()==null?0:pdaDtoTemp.getActualFeeRate().doubleValue()));
        // 价格计算表达式
        freightRes.setCaculateExpression(pdaDtoTemp.getCaculateExpression());
        log.info("CaculateExpression:"+pdaDtoTemp.getCaculateExpression());
        // 原始费用
        freightRes.setCaculateFee(pdaDtoTemp.getCaculateFee()==null?0:pdaDtoTemp.getCaculateFee().doubleValue());
        log.info("CaculateFee:"+(pdaDtoTemp.getCaculateFee()==null?0:pdaDtoTemp.getCaculateFee().doubleValue()));
        // 是否可以删除
        freightRes.setCandelete(pdaDtoTemp.getCanDelete());
        log.info("CanDelete:"+pdaDtoTemp.getCanDelete());
        // 是否可以修改
        freightRes.setCanmodify(pdaDtoTemp.getCanModify());
        log.info("CanModify:"+pdaDtoTemp.getCanModify());
        // 最终费用
        freightRes.setDiscountFee(pdaDtoTemp.getDiscountFee()==null?0:pdaDtoTemp.getDiscountFee().doubleValue());
        log.info("DiscountFee:"+(pdaDtoTemp.getDiscountFee()==null?0:pdaDtoTemp.getDiscountFee().doubleValue()));
        List<DiscountProgramEntity> disCounts = new ArrayList<DiscountProgramEntity>();
        List<PdaResultDiscountDto> dis = pdaDtoTemp.getDiscountPrograms();
        DiscountProgramEntity disCount = null;
        if (dis != null && ! dis.isEmpty()) {
            for (PdaResultDiscountDto pdaResultDiscountDto : dis) {
                disCount = new DiscountProgramEntity();
                // 折扣方案类型
                disCount.setDiscountProgramType(pdaResultDiscountDto.getMarketName());
                log.info("SaleChannelName:"+pdaResultDiscountDto.getSaleChannelName());
                // 折扣率
                disCount.setDiscountRate(pdaResultDiscountDto.getDiscountRate()==null?0:pdaResultDiscountDto.getDiscountRate().doubleValue());
                log.info("DiscountRate:"+(pdaResultDiscountDto.getDiscountRate()==null?0:pdaResultDiscountDto.getDiscountRate().doubleValue()));
                // 减免的费用
                disCount.setReduceFee(pdaResultDiscountDto.getReduceFee()==null?0:pdaResultDiscountDto.getReduceFee().doubleValue());
                log.info("ReduceFee:"+(pdaResultDiscountDto.getReduceFee()==null?0:pdaResultDiscountDto.getReduceFee().doubleValue()));
                disCounts.add(disCount);
            }
        }
        // 采用的折扣方案
        freightRes.setDiscountPrograms(disCounts);
        // 最高费用
        freightRes.setMaxFee(pdaDtoTemp.getMaxFee()==null?0:pdaDtoTemp.getMaxFee().doubleValue());
        log.info("MaxFee:"+(pdaDtoTemp.getMaxFee()==null?0:pdaDtoTemp.getMaxFee().doubleValue()));
        // 最低费用
        freightRes.setMinFee(pdaDtoTemp.getMinFee()==null?0:pdaDtoTemp.getMinFee().doubleValue());
        log.info("MinFee:"+(pdaDtoTemp.getMinFee()==null?0:pdaDtoTemp.getMinFee().doubleValue()));
        // 计价条目编码
        freightRes.setPriceEntityCode(pdaDtoTemp.getPriceEntityCode());
        log.info("PriceEntityCode:"+pdaDtoTemp.getPriceEntityCode());
        // 计价条目名称
        freightRes.setPriceEntityName(pdaDtoTemp.getPriceEntityName());
        log.info("PriceEntityName:"+pdaDtoTemp.getPriceEntityName());
        
        /*
         * 其他类型里面区分类别
         * */
        freightRes.setSubType(pdaDtoTemp.getSubType());
        log.info("SubType:"+pdaDtoTemp.getSubType());
        freightRes.setSubTypeName(pdaDtoTemp.getSubTypeName());
        log.info("SubTypeName:"+pdaDtoTemp.getSubTypeName());
        return freightRes;
	}

	
	
	
}
