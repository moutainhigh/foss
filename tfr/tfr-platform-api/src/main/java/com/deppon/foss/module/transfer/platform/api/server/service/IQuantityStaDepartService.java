package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;

public interface IQuantityStaDepartService extends IService {

	/**
	 * @desc 生成出发货量
	 * @param createTime
	 * @param threadCount
	 * @param threadNo
	 * @date 2014年8月29日下午2:42:49
	 * @author Ouyang
	 */
	void generateDepart(Date createTime, Integer threadCount, Integer threadNo);

	/**
	 * @desc 生成第2天的出发货量
	 * @param createTime
	 * @param threadCount
	 * @param threadNo
	 * @date 2014年8月29日下午2:43:00
	 * @author Ouyang
	 */
	void generateDepart2ndDay(Date createTime, Integer threadCount,
			Integer threadNo);
	
	
	/**
	 * @desc 将T_OPT_STA_DEPART_16DAY中超过16天的数据迁移至T_OPT_STA_DEPART_HIS
	 * @date 2014年9月3日 下午3:41:38
	 * @author Ouyang
	 */
	void migrate16Day2His();
	
	/**
	 * @desc 将T_OPT_STA_DEPART_FCST中超过8天的数据迁移至T_OPT_STA_DEPART_FCST_HIS
	 * @date 2014年9月3日 下午3:42:32
	 * @author Ouyang
	 */
	void migrateFcst2His();

}
