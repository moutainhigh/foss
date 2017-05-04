package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockPositionNumberEntity;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.QueryPositionNumberEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.ResultStockPositionNumber;

/**   
 * @ClassName QueryStockPositionService  
 * @Description 根据运单号、部门编号来存储定位编号到库存表中
 * @author  092038 张贞献  
 * @date 2014-12-26    
 */ 
public class QueryStockPositionService implements IBusinessService<List<ResultStockPositionNumber>, QueryPositionNumberEntity>{
	private Logger log = Logger.getLogger(getClass());
	private IPDAStockService pdaStockService;
	
	@Override
	public QueryPositionNumberEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QueryPositionNumberEntity entity =  JsonUtil.parseJsonToObject(QueryPositionNumberEntity.class,
				asyncMsg.getContent());
		return entity;
	}
	
	@Transactional
	@Override
	public List<ResultStockPositionNumber> service(AsyncMsg asyncMsg, QueryPositionNumberEntity param)
			throws PdaBusiException {
		
		List<ResultStockPositionNumber> results = new ArrayList<ResultStockPositionNumber>();
		
		List<StockPositionNumberEntity>  entity = new ArrayList<StockPositionNumberEntity>();
		try {
			
			long startTime = System.currentTimeMillis();
			 entity = pdaStockService.queryStockPositionNumber(param.getWaybillNo(), param.getOrgCode());

			log.debug("---调用FOSS获取获取位置："+results.toString()+"---");
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]调用FOSS获取获取位置:"+(endTime-startTime)+"ms");
			
			
		    
			if(null!=entity){
				ResultStockPositionNumber result=null;
				for(StockPositionNumberEntity sp:entity){
					result = new ResultStockPositionNumber();
					result.setOrgCode(sp.getOrgCode());
					result.setSerialNO(sp.getSerialNO());
					result.setStockPositionNumber(sp.getStockPositionNumber());
					result.setWaybillNO(sp.getWaybillNO());
					results.add(result);
				}
				
				
			}
			
		   
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
		return results;
	}
	
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_QSTOCKPOSITION_NUMBER.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaStockService(IPDAStockService pdaStockService) {
		this.pdaStockService = pdaStockService;
	}


	
}
