package com.deppon.pda.bdm.module.foss.unload.server.service.impl.ExceReport;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.ExceReport.UnldExceReportEntity;

/**
 * 根据条件查询卸车差异报告
 * @author 092038
 *
 */
public class QueryUnldExceReportService implements IBusinessService<List<PDAUnloadDiffReportDto>,UnldExceReportEntity>{
	private static final Log LOG = LogFactory.getLog(UnldExceReportDetailService.class);
	private IPDAUnloadDiffReportService pdaUnloadDiffReportService;
	@Override
	public UnldExceReportEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		UnldExceReportEntity entity=JsonUtil.parseJsonToObject(UnldExceReportEntity.class, asyncMsg.getContent());
		return entity;
	}

	@Override
	public List<PDAUnloadDiffReportDto> service(AsyncMsg asyncMsg,
			UnldExceReportEntity param) throws PdaBusiException {
		LOG.info("start refresh unldExceReportDetail ...");
		List<PDAUnloadDiffReportDto> pdadiffers = new ArrayList<PDAUnloadDiffReportDto>();
		try {
		pdadiffers = pdaUnloadDiffReportService.querySCPDAUnloadDiffReportList(
				asyncMsg.getDeptCode(),//部门编号
				asyncMsg.getUserCode(),	//员工
				param.getReportCode(),//差异报告编号
			    param.getDiffTime()//时间		
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
		return UnLoadConstant.OPER_TYPE_UNLODA_EXCE__QUERY.VERSION;
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
