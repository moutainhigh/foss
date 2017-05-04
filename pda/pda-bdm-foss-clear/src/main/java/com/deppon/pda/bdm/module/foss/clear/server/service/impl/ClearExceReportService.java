package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaDifferReportService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferEntity;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;

/** 
  * @ClassName ClearExceReportService 
  * @Description 获取清仓差异报告接口 
  * @author 092038 
  * @date 2014-6-6 下午4:30:05 
*/ 
public class ClearExceReportService implements IBusinessService<List<PdaDifferEntity>,Void>{
	private static final Log LOG = LogFactory.getLog(ClearExceReportService.class);
	
	private IPdaDifferReportService pdaDifferReportService;
	

    @Override
    public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
        // TODO Auto-generated method stub
        return null;
    }

	@Transactional
    @Override
    public List<PdaDifferEntity> service(AsyncMsg asyncMsg, Void param) throws PdaBusiException {
       
	       LOG.debug("start get ExceReport ...");

	       List<PdaDifferEntity> pdadiffers =new ArrayList<PdaDifferEntity>();
	        try {
	            long startTime = System.currentTimeMillis();
	            pdadiffers = pdaDifferReportService.queryDifferReportList(
	                    asyncMsg.getDeptCode(),
	                    asyncMsg.getUserCode(),
	                    asyncMsg.getPdaCode()
	                    );
	            long endTime = System.currentTimeMillis();
	            QueueMonitorInfo.addTotalFossTime(endTime-startTime);
	            LOG.info("[asyncinfo]获取清仓差异报告接口消耗时间:"+(endTime-startTime)+"ms");
	            
	        } catch (BusinessException e) {
	            LOG.error("获取差异报告异常异常"+e);
	            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
	        }
	        LOG.debug("get ExceReport success!");
	    
	    
        return pdadiffers;
    }



	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_EXCE_REPORT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

    public void setPdaDifferReportService(IPdaDifferReportService pdaDifferReportService) {
        this.pdaDifferReportService = pdaDifferReportService;
    }









}
