package com.deppon.foss.module.pickup.pricing.server.calculateservice.control.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.beanutils.BeanUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.AbstractCalculateControl;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.ICalculateExecutor;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.task.ExecutorTask;
import com.deppon.foss.util.CollectionUtils;
import com.google.inject.Inject;

public class BillCalculateBgControl extends AbstractCalculateControl{

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
	private ICalculateExecutor executor;
	
	//计算运费
	private ICalculateExecutor executorFRT;
	
	//计算增值
	private ICalculateExecutor executorValueAdd;
	
	private int THREAD_POOL_COUNT = FIVE;
	
	@Override
	public void setExecutor(ICalculateExecutor executor) {
		this.executor = executor;
	}

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
		for(int i = 0; i < subDtos.size(); i++){
			GuiQueryBillCalculateSubDto subDto = subDtos.get(i);
			//封装单个查询对象
			GuiQueryBillCalculateDto queryParam = packageQueryBillCalDto(billCalculateDto,subDto);
			//提交到线程池
			Future<List<GuiResultBillCalculateDto>> future = executorService
					.submit(new ExecutorTask<List<GuiResultBillCalculateDto>
							,GuiQueryBillCalculateDto>(executor,queryParam));
			futureLst.add(future);  
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
	
	@Override
	public List<ProductPriceDto> handlerFRT(
			QueryBillCacilateDto queryBillCacilateDto) {
		return (List<ProductPriceDto>)executorFRT.execute(queryBillCacilateDto);
	}

	@Override
	public List<ValueAddDto> handlerValueAdd(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		return (List<ValueAddDto>)executorValueAdd.execute(queryBillCacilateValueAddDto);
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
		//数据准备
		String originalId = null;
		String destinationId = null;
//		if (StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
//				|| StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
//			/* 始发部门code 定位价格区域信息 */
//			originalId = regionAirService.findRegionOrgByDeptNo(
//					billCalculateDto.getOriginalOrgCode(),
//					billCalculateDto.getReceiveDate(), null,
//					PricingConstants.PRICING_REGION);
//		} else {
			/* 始发部门code 定位价格区域信息 */
			originalId = regionService.findRegionOrgByDeptNo(
					billCalculateDto.getOriginalOrgCode(),
					billCalculateDto.getReceiveDate(), null,
					PricingConstants.PRICING_REGION);
		
//		if (StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
//				|| StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
//			/* 到达部门 code 定位价格区域信息 */
//			destinationId = regionAirService.findRegionOrgByDeptNo(
//					billCalculateDto.getDestinationOrgCode(),
//					billCalculateDto.getReceiveDate(), null,
//					PricingConstants.PRICING_REGION);
//		} else {
			/* 到达部门 code 定位价格区域信息 */
			destinationId = regionArriveService.findRegionOrgByDeptNo(
					billCalculateDto.getDestinationOrgCode(),
					billCalculateDto.getReceiveDate(), null,
					PricingConstants.ARRIVE_REGION);
//		}
		//长短途
		String longOrShort = getLongOrShort(
				billCalculateDto.getOriginalOrgCode(), billCalculateDto.getDestinationOrgCode(), 
				billCalculateDto.getProductCode(), billCalculateDto.getReceiveDate());/* 查询长短途 */
		billCalculateDto.setDeptRegionId(originalId);
		billCalculateDto.setArrvRegionId(destinationId);
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

	public void setExecutorFRT(ICalculateExecutor executorFRT) {
		this.executorFRT = executorFRT;
	}

	public void setExecutorValueAdd(ICalculateExecutor executorValueAdd) {
		this.executorValueAdd = executorValueAdd;
	}

}
