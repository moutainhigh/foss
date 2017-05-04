package com.deppon.pda.bdm.module.foss.clear.server.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.StTaskZoneDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.AdministrativeRegionEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.BigDeptEntity;


/**
 * 
 * 获取清仓时分区接口 
 * @author 200689
 * @date 2014-10-29 上午9:05:21
 * @since
 * @version
 */
public class ClearAdministrativeRegionService implements IBusinessService<List<AdministrativeRegionEntity>,BigDeptEntity>{
	private static final Log LOG = LogFactory.getLog(ClearAdministrativeRegionService.class);
	
	private IPdaStockcheckingService pdaStockcheckingService;
	


	//解析包体
    @Override
    public BigDeptEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
    	BigDeptEntity bigDeptEntity = JsonUtil.parseJsonToObject(BigDeptEntity.class, asyncMsg.getContent());
		return bigDeptEntity;
    }

	@Transactional
    @Override
    public List<AdministrativeRegionEntity> service(AsyncMsg asyncMsg, BigDeptEntity bigDeptEntity) throws PdaBusiException {
       
	       LOG.debug("start get Administrative ...");

	       this.validate(asyncMsg,bigDeptEntity);
	      List<StTaskZoneDto> stTaskZoneDtoList = new ArrayList<StTaskZoneDto>();
	      
	       List<AdministrativeRegionEntity> aRegionlist = new ArrayList<AdministrativeRegionEntity>();
	        try {
	            long startTime = System.currentTimeMillis();
	            stTaskZoneDtoList  = pdaStockcheckingService.queryZoneInfo(bigDeptEntity.getBigDeptCode());
	            long endTime = System.currentTimeMillis();
	            QueueMonitorInfo.addTotalFossTime(endTime-startTime);
	            LOG.info("获取清仓时分区接口消耗时间:"+(endTime-startTime)+"ms");
	            //封装实体
	            if(stTaskZoneDtoList!=null && stTaskZoneDtoList.size()>0){
	            	for(StTaskZoneDto stTaskZoneDto :stTaskZoneDtoList){
	            		AdministrativeRegionEntity aregion = new AdministrativeRegionEntity();
	            		aregion.setZoneCode(stTaskZoneDto.getZoneCode());
	            		aregion.setZoneName(stTaskZoneDto.getZoneName());
	            		aRegionlist.add(aregion);
	            	}
	           }
	        } catch (BusinessException e) {
	            LOG.error("获取清仓时分区接口异常"+e);
	            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
	        }
	        LOG.debug("get Administrative success!");
	    
	    
        return aRegionlist;
    }


	private void validate(AsyncMsg asyncMsg,BigDeptEntity bigDeptEntity) {
		Argument.notNull(bigDeptEntity, "BigDeptEntity");
		Argument.hasText(bigDeptEntity.getBigDeptCode(), "ClearExceReportScanEntity.bigDeptCode");
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.OperType");
	}

	@Override
	public String getOperType() {

		return ClearConstant.OPER_TYPE_CLEAR_ADMINISTRATIVEREGION.VERSION;
		
	}
	
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}
	

    
}
