package com.deppon.pda.bdm.module.ocb.server.dao;

import com.deppon.pda.bdm.module.ocb.shared.domain.FlowCountBean;

public interface IFlowCountDao {

	/**
	 * @author：xiaolongwu
	 * @description：保存流量
	 * @parameters：流量统计实体
	 * @return：
	 * @time：2014-12-30 下午4:18:23
	 */
	void saveFlowCount(FlowCountBean param);

}
