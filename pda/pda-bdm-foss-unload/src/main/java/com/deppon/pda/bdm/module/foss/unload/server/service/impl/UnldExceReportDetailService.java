package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDetailDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.QryUnldExceReportEntity;


/** 
  * @ClassName ClearExceReportDetailService 
  * @Description 获取卸车差异报告明细 
  * @author 092038 
  * @date 2014-6-9 上午8:44:53 
*/ 
public class UnldExceReportDetailService implements IBusinessService<List<PDAUnloadDiffReportDetailDto>, QryUnldExceReportEntity>{
	private static final Log LOG = LogFactory.getLog(UnldExceReportDetailService.class);
	
	   private IPDAUnloadDiffReportService pdaUnloadDiffReportService;
	
	@Override
	public QryUnldExceReportEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
	    QryUnldExceReportEntity qryClearReport = JsonUtil.parseJsonToObject(QryUnldExceReportEntity.class, asyncMsg.getContent());
		return qryClearReport;
	}

	@Transactional
	@Override
	public List<PDAUnloadDiffReportDetailDto> service(AsyncMsg asyncMsg, QryUnldExceReportEntity param)
			throws PdaBusiException {
		LOG.info("start refresh unldExceReportDetail ...");
		List<PDAUnloadDiffReportDetailDto> pdadiffers = new ArrayList<PDAUnloadDiffReportDetailDto>();
		try {
		pdadiffers = pdaUnloadDiffReportService.queryPDAUnloadDiffReportDetailList(
					param.getReportCode(),
					asyncMsg.getUserCode(),
		            asyncMsg.getPdaCode(),
		            param.getHandInputFlg(),
		            asyncMsg.getDeptCode()
		            );
			
		} catch (BusinessException e) {
			LOG.error("刷新异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
		LOG.info("refresh  unldExceReportDetail end...");
		return pdadiffers;
	}

	

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_EXCE__DETAIL.VERSION;
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
