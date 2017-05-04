/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.pricing.server.action;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceTableService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartnerPriceTables;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceTables;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PartnerPriceTableVo;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceTableVo;


/**
 * 查询汽运价格表信息Action
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-2-13 上午10:35:30 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-2-13 上午10:35:30
 * @since
 * @version
 */
public class PriceReportSearchAction extends AbstractAction {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -3406899194596908181L;

    /**
     * 获取日志类.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceReportSearchAction.class);
    
    /**
     * 汽运价格报表信息Service接口.
     */
    private IPriceTableService priceTableService;
    
    /**
     * 汽运价格报表信息VO.
     */
    private PriceTableVo vo = new PriceTableVo();
    
    /**
     * 合伙人汽运价格表信息VO
     */
    private PartnerPriceTableVo partnerPriceTableVo=new PartnerPriceTableVo();
    /**
     * 获取 汽运价格报表信息VO.
     *
     * @return  the vo
     */
    public PriceTableVo getVo() {
        return vo;
    }
    
    /**
     * 设置 汽运价格报表信息VO.
     *
     * @param vo the vo to set
     */
    public void setVo(PriceTableVo vo) {
        this.vo = vo;
    }

    
    /**
     * 设置 汽运价格报表信息Service接口.
     *
     * @param priceTableService the priceTableService to set
     */
    public void setPriceTableService(IPriceTableService priceTableService) {
        this.priceTableService = priceTableService;
    }

    /**
     * <p>分页查询汽运价格表信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-2-14 上午10:56:12
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
	@JSON
    public String queryAllPriceTables() {
	try {
	    //根据传入对象查询符合条件所有快递代理公司运价方案信息
	    PriceTables tables = null;
		//原代码 start
	    /*
	    try {
			tables = priceTableService.queryPriceTableListInfo(vo.getStartDeptCode(), vo.getProductType(), vo.getEffectiveDate(), start, limit);
	    */
	    //原代码 end
	    //[POP] modify foss-148246-sunjianbo 2014-10-30 start
	    Map<String,String> sectionScopeMap = null;
		try {
			Object[] objArray = priceTableService.queryPriceTableListInfo(vo.getStartDeptCode(), vo.getProductType(), vo.getEffectiveDate(), vo.getSectionID(), start, limit); 
			if(null != objArray){
				tables = (PriceTables) objArray[0];
				sectionScopeMap = (Map<String, String>) objArray[1];
			}
		//[POP] modify foss-148246-sunjianbo 2014-10-30 end
		} catch (Exception e1) {
			BusinessException e = new BusinessException("存在数据异常");
//			String message="存在数据异常";
		    LOGGER.error(e.getMessage(),e);
		    return returnError(e);
		} 
	    if(null!= tables){
	    	vo.setPriceTableEntityList(tables.getPriceTableEntitys());
			//[POP] add foss-148246-sunjianbo 2014-11-10 start
			if(null != sectionScopeMap){
				vo.setSectionScopeMap(sectionScopeMap);
			}
			//[POP] add foss-148246-sunjianbo 2014-11-10 end
			this.setTotalCount(Long.valueOf(tables.getTotal()));
	    }
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }

	/**
	 * <p>合伙人汽运价格表查询</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-20 上午10:05:27
	 * @return
	 * @see
	 */
    @SuppressWarnings("unchecked")
  	@JSON
      public String queryPartnerPriceTables() {
    	try {
    		PartnerPriceTables tables = null;
    	    Map<String,String> sectionScopeMap = null;
    		try {
    			Object[] objArray = priceTableService.queryPartnerPriceTableListInfo(partnerPriceTableVo.getStartDeptCode(), partnerPriceTableVo.getProductType(), partnerPriceTableVo.getEffectiveDate(), partnerPriceTableVo.getSectionID(), start, limit); 
    	        	
    			if(null != objArray){
    				tables = (PartnerPriceTables) objArray[0];
    				sectionScopeMap = (Map<String, String>) objArray[1];
    			}
    		} catch (Exception e1) {
    			BusinessException e = new BusinessException("存在数据异常");
    		    LOGGER.error(e.getMessage(),e);
    		    LOGGER.info(e1.toString());
    		    return returnError(e);
    		} 
    	    if(null!= tables){
    	    	partnerPriceTableVo.setPartnerPriceTableEntityList(tables.getPartnerPriceTableEntitys());
    			if(null != sectionScopeMap){
    				partnerPriceTableVo.setSectionScopeMap(sectionScopeMap);
    			}
    			this.setTotalCount(Long.valueOf(tables.getTotal()));
    	    }
    	  
    	    return returnSuccess();
    	} catch (BusinessException e) {
    	    LOGGER.error(e.getMessage(),e);
    	    return returnError(e);
    	}
    }

	public PartnerPriceTableVo getPartnerPriceTableVo() {
		return partnerPriceTableVo;
	}

	public void setPartnerPriceTableVo(PartnerPriceTableVo partnerPriceTableVo) {
		this.partnerPriceTableVo = partnerPriceTableVo;
	}
    
}
