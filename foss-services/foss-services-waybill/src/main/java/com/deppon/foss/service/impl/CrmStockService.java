package com.deppon.foss.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.service.ICrmStockService;
import com.deppon.foss.util.define.ESBHeaderConstant;
import com.eos.foundation.common.utils.StringUtil;

/**
 * FOSS-TFR提供给CRM的接口
 * @author 273247
 * @date 2016-02-21 上午12:45:05
  
        移动CRM需要向FOSS系统传递未签收的运单号码作为参数查询，FOSS需返回该运单的库存位置和库存时间信息。
*/

public class CrmStockService implements ICrmStockService {	
	
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(CrmStockService.class);
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	private IStockService stockService;

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * @desc:返回库存信息
	 * @param waybillNo
	 * @return
	 * @author:zzq
	 * @date:2016年03月02日上午12:50:26
	 */


	//@Transactional
	@Override
	public List<StockEntity> backStockInfo(String waybillNo) {
		// 设置相应header
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "ESB_CRM2ESB_BACK_STOCKINFO");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		List<StockEntity> result = new ArrayList<StockEntity>();
		try {
			result=handleResult(waybillNo);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			logger.info("成功返回库存信息和库存时间。。。。");
			return result;
		} catch (Exception e) {
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			logger.error("返回库存信息和库存时间异常！");
			return result;
		}		
	}
	
	private List<StockEntity>  handleResult(String waybillNo){
		   if(StringUtil.isBlank(waybillNo)){
	    	   logger.error("运单号为空");
			   throw new TfrBusinessException("运单号为空");	
	        }
		  List<StockEntity> stock = stockService.queryStockByCrmWaybillNo(waybillNo);
		  return stock;
		}
}
	   
	 

