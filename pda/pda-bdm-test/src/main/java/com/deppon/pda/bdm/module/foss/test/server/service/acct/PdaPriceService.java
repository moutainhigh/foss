package com.deppon.pda.bdm.module.foss.test.server.service.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPriceService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.CityMarketPlanDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PreferentialDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;

public class PdaPriceService implements IPdaPriceService {

	@Override
	public PreferentialDto queryPreferentialInfo(String customerCode, Date date) {
		PreferentialDto dto = new PreferentialDto();
		dto.setActive("1");
		dto.setAgentGathRate(new BigDecimal(100));
		dto.setChargeRebate(new BigDecimal(100000));
		dto.setCrmId(new BigDecimal(1000000));
		dto.setCusBargainId(new BigDecimal(100));
		dto.setDeliveryFeeRate(new BigDecimal(100));
		dto.setInsureDpriceRate(new BigDecimal(100));
		dto.setReceivePriceRate(new BigDecimal(100));
		return dto;
	}

	@Override
	public List<PublicPriceDto> queryPublishPriceDetailByCity(String startCityCode, String destinationCityCode, Date billDate) {
		List<PublicPriceDto> dtos = new ArrayList<PublicPriceDto>();
		PublicPriceDto dto1 = new PublicPriceDto();
		dto1.setAddDay(100);
		dto1.setArriveTime("2013-01-13");
		dto1.setArrvRegionCode("10001");
		dto1.setArrvRegionId("10002");
		dto1.setCentralizePickup("123");
		dto1.setDeliveryTime("2013-01-13");
		dto1.setDeptRegionCode("10003");
		dto1.setDeptRegionId("10004");
		dto1.setGoodsTypeCode("10020302");
		dto1.setGoodsTypeName("货物");
		dto1.setHasSalesDept("1242134");
		dto1.setHeavyFeeRate(new BigDecimal(100));
		dto1.setHeavyFeeRatePickUpNo(new BigDecimal(100));
		dto1.setHeavyFeeRatePickUpYes(new BigDecimal(100));
		dto1.setId("80322298982430");
		dto1.setLightFeeRate(new BigDecimal(100));
		dto1.setLightFeeRatePickUpNo(new BigDecimal(100));
		dto1.setLightFeeRatePickUpYes(new BigDecimal(100));
		dto1.setLongOrShort("afskdfj");
		dto1.setMaxTime(100);
		dto1.setMaxTimeUnit("10");
		dto1.setMinFee(new BigDecimal(100));
		dto1.setMinFeePickUpNo(new BigDecimal(100));
		dto1.setMinFeePickUpYes(new BigDecimal(100));
		dto1.setMinTime(300);
		dto1.setMinTimeUnit("98324908");
		dto1.setProductCode("908324908");
		dto1.setProductName("slkfjds");
		dtos.add(dto1);
		return dtos;
	}

	@Override
	public List<PdaResultBillCalculateDto> queryBillCalculate(PdaQueryBillCalculateDto billCalculateDto) {
		List<PdaResultBillCalculateDto> dtos = new ArrayList<PdaResultBillCalculateDto>();
		PdaResultBillCalculateDto dto1 = new PdaResultBillCalculateDto();
		dto1.setActualFeeRate(new BigDecimal(100));
		dto1.setCaculateExpression("skjdfl");
		dto1.setCaculateFee(new BigDecimal(100));
		dto1.setCanDelete("1");
		dto1.setCanModify("1");
		dto1.setDiscountFee(new BigDecimal(100));
		
		List<PdaResultDiscountDto> discountPrograms = new ArrayList<PdaResultDiscountDto>();
		PdaResultDiscountDto dto11 = new PdaResultDiscountDto();
		dto11.setDiscountRate(new BigDecimal(100));
		dto11.setMarketName("A");
		dto11.setReduceFee(new BigDecimal(100));
		dto11.setSaleChannelName("B");
		discountPrograms.add(dto11);
		dto1.setDiscountPrograms(discountPrograms);
		dto1.setMaxFee(new BigDecimal(100));
		dto1.setMinFee(new BigDecimal(100));
		dto1.setPriceEntityCode("978123787");
		dto1.setPriceEntityName("dldakfjs");
		
		dtos.add(dto1);
		return dtos;
	}

	@Override
	public List<PdaResultBillCalculateDto> queryExpressBillCalculate(
			PdaQueryBillCalculateDto arg0) {
	    List<PdaResultBillCalculateDto> dtos = new ArrayList<PdaResultBillCalculateDto>();
        PdaResultBillCalculateDto dto1 = new PdaResultBillCalculateDto();
        dto1.setActualFeeRate(BigDecimal.valueOf(0.015));
        dto1.setCaculateExpression("res = WG*WP");
        dto1.setCaculateFee(BigDecimal.valueOf(79.86));
        dto1.setDiscountFee(BigDecimal.valueOf(100));
        dto1.setSubType("R3");
        dto1.setPriceEntityCode("HK");
        dto1.setDiscountFee(BigDecimal.valueOf(0.0));
        dto1.setMaxFee(BigDecimal.valueOf(99999.0));
        dto1.setMinFee(BigDecimal.valueOf(5.0));
        dto1.setPriceEntityCode("HK");
        dto1.setPriceEntityName("代收费");
        
        PdaResultBillCalculateDto dto2 = new PdaResultBillCalculateDto();
        dto2.setActualFeeRate(BigDecimal.valueOf(0.0040));
        dto2.setCaculateExpression("res = WG*WP");
        dto2.setCaculateFee(BigDecimal.valueOf(13.33));
        dto2.setDiscountFee(BigDecimal.valueOf(100));
        dto2.setSubType("R3");
        dto2.setPriceEntityCode("HK");
        dto2.setDiscountFee(BigDecimal.valueOf(0.0));
        dto2.setMaxFee(BigDecimal.valueOf(9.9999999E7));
        dto2.setMinFee(BigDecimal.valueOf(1.0));
        dto2.setPriceEntityCode("BF");
        dto2.setPriceEntityName("保费");
  
        PdaResultBillCalculateDto dto3 = new PdaResultBillCalculateDto();
        dto3.setActualFeeRate(BigDecimal.valueOf(0.0));
        dto3.setCaculateExpression("res = FX");
        dto3.setCaculateFee(BigDecimal.valueOf(12.0));
        dto3.setDiscountFee(BigDecimal.valueOf(100));
        dto3.setSubType("ORIGINAL");
        dto3.setPriceEntityCode("HK");
        dto3.setDiscountFee(BigDecimal.valueOf(0.0));
        dto3.setMaxFee(BigDecimal.valueOf(0.0));
        dto3.setMinFee(BigDecimal.valueOf(0.0));
        dto3.setPriceEntityCode("QS");
        dto3.setPriceEntityName("签收回单");
        
        PdaResultBillCalculateDto dto4 = new PdaResultBillCalculateDto();
        dto4.setActualFeeRate(BigDecimal.valueOf(130.0));
        dto4.setCaculateExpression(null);
        dto4.setCaculateFee(BigDecimal.valueOf(13.0));
        dto4.setDiscountFee(BigDecimal.valueOf(13.0));
        dto4.setSubType(null);
        dto4.setPriceEntityCode("HK");
        dto4.setDiscountFee(BigDecimal.valueOf(0.0));
        dto4.setMaxFee(BigDecimal.valueOf(0.0));
        dto4.setMinFee(BigDecimal.valueOf(0.0));
        dto4.setPriceEntityCode("FRT");
        dto4.setPriceEntityName("运费");
        dto4.setCentralizePickup(null);
       
        dtos.add(dto1);
        dtos.add(dto2);
        dtos.add(dto3);
        dtos.add(dto4);
        
        return dtos;
	}

    @Override
    public List<CityMarketPlanDto> searchCityMarketPlanEntityList(String deptcode, Date billDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CityMarketPlanDto getCityMarketPlanEntityCode(String code, String deptcode, Date billDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long countMarketPlanEntity(String deptcode, Date billDate) {
        // TODO Auto-generated method stub
        return null;
    }

  /*  @Override
    public List<CityMarketPlanDto> searchCityMarketPlanEntityList(String deptcode, Date billDate) {
        List<CityMarketPlanDto> cityMarketPlanDtos = new ArrayList<CityMarketPlanDto>();
        CityMarketPlanDto ci  = new CityMarketPlanDto();
        ci.setCode("c001");
        ci.setName("活动一");       
        cityMarketPlanDtos.add(ci);
        
        ci  = new CityMarketPlanDto();
        ci.setCode("c002");
        ci.setName("活动二");
        cityMarketPlanDtos.add(ci);
        
        // TODO Auto-generated method stub
        return cityMarketPlanDtos;
    }

    @Override
    public CityMarketPlanDto getCityMarketPlanEntityCode(String code, String deptcode, Date billDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long countMarketPlanEntity(String deptcode, Date billDate) {
        // TODO Auto-generated method stub
        return null;
    }
*/

}
