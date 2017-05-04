package com.deppon.pda.bdm.module.foss.unload.server.service.impl.ExceReport;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.unload.server.service.impl.UnldExceReportService;
/**
 * 获取差异报告指令
 * @author 092038
 *
 */
public class GetExceReportService implements IBusinessService<List<PDAUnloadDiffReportDto>,Void> {
	private static final Log LOG = LogFactory.getLog(UnldExceReportService.class);
	
	private IPDAUnloadDiffReportService pdaUnloadDiffReportService;
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}

	@Override
	public List<PDAUnloadDiffReportDto> service(AsyncMsg asyncMsg, Void param)
		throws PdaBusiException {
		LOG.debug("start get ExceReport ...");

	    List<PDAUnloadDiffReportDto> pdadiffers =new ArrayList<PDAUnloadDiffReportDto>();
	      try {
	        long startTime = System.currentTimeMillis();
	         pdadiffers = pdaUnloadDiffReportService.querySCPDAUnloadDiffReportList(
	              asyncMsg.getDeptCode(),
	              asyncMsg.getUserCode()
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
		return UnLoadConstant.OPER_TYPE_UNLODA_EXCE__GET.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaUnloadDiffReportService(
			IPDAUnloadDiffReportService pdaUnloadDiffReportService) {
		this.pdaUnloadDiffReportService = pdaUnloadDiffReportService;
	}
	
}
