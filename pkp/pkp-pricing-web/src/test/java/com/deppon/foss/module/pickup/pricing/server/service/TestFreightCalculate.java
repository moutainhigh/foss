package com.deppon.foss.module.pickup.pricing.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.server.service.impl.GuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.inject.Inject;

/**
 * 
 * 测试运费计算
 * @author DP-Foss-YueHongJie
 * @date 2013-4-16 下午3:25:33
 * @version 1.0
 */
public class TestFreightCalculate {
    
    /** 日志信息 **/
    Logger log = Logger.getLogger(TestFreightCalculate.class);
    
    /** 计算物流运费总接口 **/
//    private IBillCaculateService billCaculateService;
    private IGuiBillCaculateService guiBillCaculateService;
    
    @Before
    public void setUp() throws Exception {
	guiBillCaculateService = (IGuiBillCaculateService)SpringTestHelper.get().getBeanByClass(GuiBillCaculateService.class);
//	billCaculateService = (IBillCaculateService)SpringTestHelper.get().getBeanByClass(BillCaculateService.class);
    }
    
    /**
     * 
     * <p>测试运费</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-16 下午6:42:09
     * @see
     */
    @Test
    public void testTestFreightCalculate(){
	
	//三级产品-精准卡航	"FLF";
	//三级产品-精准城运 "FSF";
	//三级产品-精准汽运(长途) "LRF";
	//三级产品-精准汽运(短途) "SRF";
	//三级产品-汽运偏线  "PLF";
	//三级产品-整车  "WVH"; 
	//三级产品-精准空运   "AF";
	//合票方式-单独开单	  "DDKD";
	
	GuiQueryBillCalculateDto queryBillCacilateDto = new GuiQueryBillCalculateDto();
    	queryBillCacilateDto.setOriginalOrgCode("W011302020515");
    	queryBillCacilateDto.setDestinationOrgCode("W011305080404");
    	queryBillCacilateDto.setProductCode("FLF");
    	queryBillCacilateDto.setCustomerCode("400358968");
    	queryBillCacilateDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
    	queryBillCacilateDto.setVolume(new BigDecimal(5));
    	queryBillCacilateDto.setWeight(new BigDecimal(3333));
    	queryBillCacilateDto.setIsReceiveGoods("N");
    	queryBillCacilateDto.setReceiveDate(new Date());
    	queryBillCacilateDto.setChannelCode("BUSINESS_DEPT");

    	
    	
    	List<GuiResultBillCalculateDto> productPriceDtos =  guiBillCaculateService.queryGuiBillPrice(queryBillCacilateDto);
    	log.debug("productPriceDtos:"+productPriceDtos.size());
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    	    	GuiResultBillCalculateDto discountDto = productPriceDtos.get(i);
    		log.debug("表达式:"+discountDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+discountDto.getPriceEntryCode());
    		log.debug("计价方式NAME:"+discountDto.getPriceEntryName());
    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
    		log.debug("最大值:"+discountDto.getMaxFee());
    		log.debug("最小值:"+discountDto.getMinFee());
    		List<GuiResultDiscountDto> resultDiscountDtos = discountDto.getDiscountPrograms();
    		if(CollectionUtils.isNotEmpty(resultDiscountDtos)) {
    			for (int j = 0; j < resultDiscountDtos.size(); j++) {
    			    	GuiResultDiscountDto priceDiscountDto = resultDiscountDtos.get(j);
        			log.debug("折折方案:"+priceDiscountDto.getMarketName());
        			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
        			log.debug("减免费:"+priceDiscountDto.getReduceFee());
        			log.debug("折扣率:"+priceDiscountDto.getDiscountRate());
    			}
    		}
    		log.debug("-------------------------------------------------");
	}
    }
    /**
     * 
     * <p>测试GUI物流费用</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-16 下午6:46:01
     * @see
     */
    @Test
    @Ignore
    public void testGuiPriceValuation(){
    	GuiQueryBillCalculateDto queryBillCalculateDto = new GuiQueryBillCalculateDto();
//    	queryBillCalculateDto.setOriginalOrgCode("W011303070301");
//    	queryBillCalculateDto.setDestinationOrgCode("W011303070309");
//    	queryBillCalculateDto.setProductCode("FSF");
//    	queryBillCalculateDto.setGoodsCode("H00001");
//    	queryBillCalculateDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
//    	queryBillCalculateDto.setReceiveDate(new Date());
//    	queryBillCalculateDto.setIndustrulCode("ALL");
//    	queryBillCalculateDto.setVolume(new BigDecimal(2));
//    	queryBillCalculateDto.setWeight(new BigDecimal(90));
//
//    	List<GuiQueryBillCalculateSubDto> list = new ArrayList<GuiQueryBillCalculateSubDto>();
//    	
//    	GuiQueryBillCalculateSubDto subDto7 = new GuiQueryBillCalculateSubDto();
//    	subDto7.setOriginnalCost(new BigDecimal(0));
//    	subDto7.setPriceEntityCode("FRT");
//    	
//    	list.add(subDto7);
    	
    	/**
    	 * 400361433
W011305080404 
W011302020515
FLF
N
    	 */
	queryBillCalculateDto.setOriginalOrgCode("W0113080305");
    	queryBillCalculateDto.setDestinationOrgCode("W0600030706060710");
    	queryBillCalculateDto.setProductCode("LRF");
    	queryBillCalculateDto.setReceiveDate(new Date());
    	queryBillCalculateDto.setVolume(new BigDecimal(200));
    	queryBillCalculateDto.setWeight(new BigDecimal(100));
//    	queryBillCalculateDto.setKilom(new BigDecimal(100));
    	List<GuiQueryBillCalculateSubDto> list = new ArrayList<GuiQueryBillCalculateSubDto>();
    	
    	//运费
//    	GuiQueryBillCalculateSubDto subDto1 = new GuiQueryBillCalculateSubDto();
//    	subDto1.setPriceEntityCode("FRT");
//    	
    	//保费 
    	GuiQueryBillCalculateSubDto subDto2 = new GuiQueryBillCalculateSubDto();
    	subDto2.setPriceEntityCode("QT");
    	
//    	//代收货款
//    	GuiQueryBillCalculateSubDto subDto3 = new GuiQueryBillCalculateSubDto();
//    	subDto3.setPriceEntityCode("HK");
    	
    	//其他
    	GuiQueryBillCalculateSubDto subDto4 = new GuiQueryBillCalculateSubDto();
    	subDto4.setPriceEntityCode("QT");
    	subDto4.setSubType("SHJCF");
    	
    	//其他
    	GuiQueryBillCalculateSubDto subDto5 = new GuiQueryBillCalculateSubDto();
    	subDto5.setPriceEntityCode("BZ");
    	subDto5.setSubType("BOX");
    	subDto5.setWoodenVolume(new BigDecimal(2.8));
    	
    	//送货
    	GuiQueryBillCalculateSubDto subDto6 = new GuiQueryBillCalculateSubDto();
    	subDto6.setPriceEntityCode("SH");
//    	
//    	list.add(subDto1);
//    	list.add(subDto2);
//    	list.add(subDto3);
//    	list.add(subDto4);
//    	list.add(subDto5);
    	list.add(subDto6);
    	
    	queryBillCalculateDto.setPriceEntities(list);
    	List<GuiResultBillCalculateDto> productPriceDtos = guiBillCaculateService.queryGuiBillPrice(queryBillCalculateDto);
    	log.debug("productPriceDtos:"+productPriceDtos.size());
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    	    	GuiResultBillCalculateDto productPriceDto = productPriceDtos.get(i);
    	    	log.debug("计费明细:"+productPriceDto.getId());
    		log.debug("表达式:"+productPriceDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+productPriceDto.getPriceEntryCode());
    		log.debug("计价方式NAME:"+productPriceDto.getPriceEntryName());
    		log.debug("实际FeeRATE:"+productPriceDto.getActualFeeRate());
    		log.debug("计算后的价格:"+productPriceDto.getCaculateFee());
    		log.debug("打折后的价格:"+productPriceDto.getDiscountFee());
    		log.debug("是否删除:"+productPriceDto.getCandelete());
    		log.debug("是否修改:"+productPriceDto.getCanmodify());
    		log.debug("归集子类别CODE:"+productPriceDto.getSubType());
    		log.debug("归集子类别名称:"+productPriceDto.getSubTypeName());
    		log.debug("最大值:"+productPriceDto.getMaxFee());
    		log.debug("最小值:"+productPriceDto.getMinFee());
    		List<GuiResultDiscountDto> resultDiscountDtos = productPriceDto.getDiscountPrograms();
    		if(CollectionUtils.isNotEmpty(resultDiscountDtos)) {
    			for (int j = 0; j < resultDiscountDtos.size(); j++) {
        			GuiResultDiscountDto priceDiscountDto = resultDiscountDtos.get(j);
        			log.debug("折折方案:"+priceDiscountDto.getMarketName());
        			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
        			log.debug("减免费:"+priceDiscountDto.getReduceFee());
        			log.debug("折扣、优惠明细ID:"+priceDiscountDto.getDiscountId());
        			log.debug("增值、运费明细ID:"+priceDiscountDto.getChargeDetailId());
    			}
    		}
    		log.debug("-------------------------------------------------");
	}
    }
}
