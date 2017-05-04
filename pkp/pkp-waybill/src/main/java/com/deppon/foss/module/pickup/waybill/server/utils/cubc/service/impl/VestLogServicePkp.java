package com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.impl;

import org.apache.log4j.Logger;

import com.deppon.foss.module.pickup.waybill.server.utils.cubc.dao.IVestLogDaoPkp;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestLogDo;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.IVestLogServicePkp;

/**
 * 
    * @ClassName: VestLogService
    * @Description: 用于记录日志信息的service服务
    * @author 323098
    * @date 2017年1月1日
    *
 */
public class VestLogServicePkp implements IVestLogServicePkp{
	private static final Logger logger = Logger.getLogger(VestLogServicePkp.class);
	private IVestLogDaoPkp vestLogDaoPkp;

	@Override
	public void log(VestLogDo vestLogDo) {
		logger.info(vestLogDo.toString());
		this.insert(vestLogDo);
	}

	@Override
	public int insert(VestLogDo vestLogDo) {
		return vestLogDaoPkp.insert(vestLogDo);
	}

	public void setVestLogDaoPkp(IVestLogDaoPkp vestLogDaoPkp) {
		this.vestLogDaoPkp = vestLogDaoPkp;
	}

}
