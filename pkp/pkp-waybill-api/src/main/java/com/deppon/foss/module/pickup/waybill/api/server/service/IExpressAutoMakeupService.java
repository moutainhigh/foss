package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

public interface IExpressAutoMakeupService extends IService{
	/**
	 * @author Foss-065340-LiuTao
	 * @date 2015-05-15 
	 * @param eWaybillConditionDto
	 * @return
	 * @throws Exception 
	 */
	public ResultDto expressWaybillAutoMakeup(EamDto eamDto);
	/**
	 * 补录生成运单JOB
	 * @author:220125Yangxiaolong
	 * @date 2015-7-8 16:47:58
	 * @throws Exception
	 */
	public void batchGenerateExpressWaybillJobs();
	
}
