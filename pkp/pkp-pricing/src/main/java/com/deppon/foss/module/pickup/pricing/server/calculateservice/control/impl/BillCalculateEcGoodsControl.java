package com.deppon.foss.module.pickup.pricing.server.calculateservice.control.impl;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.*;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.AbstractCalculateControl;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.ICalculateExecutor;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.task.ExecutorTask;
import com.deppon.foss.util.CollectionUtils;
import com.google.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BillCalculateEcGoodsControl extends AbstractCalculateControl{

	private static final int FIVE=5;
	/** 计价明细 Service. */
	@Inject
	private IEffectivePlanDetailService effectivePlanDetailService;
	
	/** 区域 Service. */
	@Inject
	private IRegionService regionService;
	
	/** 到达区域  */
	@Inject
	IRegionArriveService regionArriveService;
	
	//计算集合
	@SuppressWarnings("rawtypes")
	private ICalculateExecutor executor;
	
	//计算运费
	@SuppressWarnings("rawtypes")
	private ICalculateExecutor ecGoodsExecutorFRT;
	
	//计算增值
	@SuppressWarnings("rawtypes")
	private ICalculateExecutor ecGoodsExecutorValueAdd;
	
	private int THREAD_POOL_COUNT = FIVE;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void setExecutor(ICalculateExecutor executor) {
		this.executor = executor;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<GuiResultBillCalculateDto> handler(
			GuiQueryBillCalculateDto billCalculateDto) {
		long startTime = System.nanoTime();
		System.out.println("控制器运行正常...");
		filterParamert(billCalculateDto);
		List<GuiQueryBillCalculateSubDto> subDtos = billCalculateDto.getPriceEntities();
		/*
		 * 
		 * 在进行业务计算之前，将billCalculateDto中的运费计算需要的体积信息存入运费所在的sub条目中，
		 * 防止先计算增值费后计算运费时包装体积覆盖掉运费体积
		 */
		for(GuiQueryBillCalculateSubDto temp : subDtos){
			if(StringUtil.isNotBlank(temp.getPriceEntityCode())) {
				String entryCode = temp.getPriceEntityCode();
				if(StringUtil.isBlank(entryCode)){
					throw new BillCaculateServiceException("计价条目CODE为空");
				}
				String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
					temp.setWoodenVolume(billCalculateDto.getVolume());
					break;
				}
			}
		}
		
		
		List<GuiResultBillCalculateDto> resultLst = new ArrayList<GuiResultBillCalculateDto>();
		List<Future> futureLst = new ArrayList<Future>();
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);

		//遍历subDto集合,判断是否运费，是就让其先执行,将结果封装进后返回。
		for(GuiQueryBillCalculateSubDto subDto:subDtos){
			if(StringUtil.equals(subDto.getPriceEntityCode(),PriceEntityConstants.PRICING_CODE_FRT)){
				GuiQueryBillCalculateDto queryParam = packageQueryBillCalDto(billCalculateDto,subDto);
				List<GuiResultBillCalculateDto> rstBillCalDtoLst = (List<GuiResultBillCalculateDto>) executor.execute(queryParam);
				if(CollectionUtils.isNotEmpty(rstBillCalDtoLst)){
					//遍历将查询结果是否接货和送货，封装至查询条件，以便下面调用
					for(GuiResultBillCalculateDto temp:rstBillCalDtoLst){
						if(StringUtil.equals(temp.getPriceEntryCode(),PriceEntityConstants.PRICING_CODE_FRT)){
							billCalculateDto.setCentralizePickupResult(temp.getCentralizePickupResult());
							billCalculateDto.setCentralizeDeliveryResult(temp.getCentralizeDeliveryResult());
							billCalculateDto.setCaculateType(temp.getCaculateType());
							break;
						}
					}
				}
				resultLst.addAll(rstBillCalDtoLst);
			}
		}

		for(int i = 0; i < subDtos.size(); i++){
			GuiQueryBillCalculateSubDto subDto = subDtos.get(i);
			//封装单个查询对象
			if(!StringUtil.equals(subDto.getPriceEntityCode(),PriceEntityConstants.PRICING_CODE_FRT)){
				GuiQueryBillCalculateDto queryParam = packageQueryBillCalDto(billCalculateDto,subDto);
				//提交到线程池
				Future<List<GuiResultBillCalculateDto>> future = executorService
						.submit(new ExecutorTask<List<GuiResultBillCalculateDto>
								,GuiQueryBillCalculateDto>(executor,queryParam));
				futureLst.add(future);
			}
		}
		executorService.shutdown();
		for (Future<List<GuiResultBillCalculateDto>> fs : futureLst) {
			try {
				resultLst.addAll(fs.get());
			} catch (Exception e) {
				throw new PricingCommonException("", "",e);
			}
		}
		long endTime = System.nanoTime();
		System.out.println("执行器计算时间 ====" + (endTime - startTime));
		return resultLst;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductPriceDto> handlerFRT(
			QueryBillCacilateDto queryBillCacilateDto) {
		return (List<ProductPriceDto>) ecGoodsExecutorFRT.execute(queryBillCacilateDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValueAddDto> handlerValueAdd(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		return (List<ValueAddDto>) ecGoodsExecutorValueAdd.execute(queryBillCacilateValueAddDto);
	}
	
	/**
	 * 封装出查询子对象
	 * @param billCalculateDto
	 * @param queryBillCalculateSubDto
	 * @return
	 */
	private GuiQueryBillCalculateDto packageQueryBillCalDto(GuiQueryBillCalculateDto billCalculateDto
			,GuiQueryBillCalculateSubDto queryBillCalculateSubDto){
		GuiQueryBillCalculateDto dto2 = new GuiQueryBillCalculateDto();
		try{
			BeanUtils.copyProperties(dto2, billCalculateDto);
		}catch(Exception e){
			e.printStackTrace();
		}
		String entryCode = queryBillCalculateSubDto.getPriceEntityCode();
		dto2.setPricingEntryCode(queryBillCalculateSubDto.getPriceEntityCode());
		dto2.setSubType(queryBillCalculateSubDto.getSubType());
		dto2.setOriginnalCost(queryBillCalculateSubDto.getOriginnalCost());
		List<GuiQueryBillCalculateSubDto> priceEntities = new ArrayList<GuiQueryBillCalculateSubDto>();
		priceEntities.add(queryBillCalculateSubDto);
		dto2.setPriceEntities(priceEntities);
		if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_BZ, entryCode)) {
			dto2.setVolume(queryBillCalculateSubDto.getWoodenVolume());
		}
		String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
		if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
			dto2.setVolume(queryBillCalculateSubDto.getWoodenVolume());
		}
		return dto2;
	}
	
	/**
	 * 设置计算参数.
	 *
	 * @param billCalculateDto the bill calculate dto
	 */
	private void filterParamert(GuiQueryBillCalculateDto billCalculateDto){
		String longOrShort = getLongOrShort(
				billCalculateDto.getOriginalOrgCode(), billCalculateDto.getDestinationOrgCode(), 
				billCalculateDto.getProductCode(), billCalculateDto.getReceiveDate());/* 查询长短途 */
		billCalculateDto.setLongOrShort(longOrShort);
	}
	
	/**
	 * 根据出发始发区域ID,目的地区域ID,产品编码,营业日期,确定获得唯一时效明细信息返回长短途标识.
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param productCode 产品编码
	 * @param receiveDate 收货日期
	 * @return the long or short
	 * @author DP-Foss-YueHongJie
	 * @date 2012-11-9 下午2:37:08
	 */
	private String getLongOrShort(String originalOrgCode, String destinationOrgCode,String productCode,Date receiveDate){
	    List<EffectivePlanDto> effPlanDetailList = effectivePlanDetailService.queryEffectivePlanDetailListByOrgCode(originalOrgCode, destinationOrgCode, productCode,receiveDate);
	    if(CollectionUtils.isNotEmpty(effPlanDetailList)){
	    	return effPlanDetailList.get(0).getLongOrShort();    
	    }
	    return null;
	}

	public IEffectivePlanDetailService getEffectivePlanDetailService() {
		return effectivePlanDetailService;
	}

	public void setEffectivePlanDetailService(
			IEffectivePlanDetailService effectivePlanDetailService) {
		this.effectivePlanDetailService = effectivePlanDetailService;
	}

	public IRegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}

	public IRegionArriveService getRegionArriveService() {
		return regionArriveService;
	}

	public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
	}

	@SuppressWarnings("rawtypes")
	public void setEcGoodsExecutorFRT(ICalculateExecutor ecGoodsExecutorFRT) {
		this.ecGoodsExecutorFRT = ecGoodsExecutorFRT;
	}

	@SuppressWarnings("rawtypes")
	public void setEcGoodsExecutorValueAdd(ICalculateExecutor ecGoodsExecutorValueAdd) {
		this.ecGoodsExecutorValueAdd = ecGoodsExecutorValueAdd;
	}

}
