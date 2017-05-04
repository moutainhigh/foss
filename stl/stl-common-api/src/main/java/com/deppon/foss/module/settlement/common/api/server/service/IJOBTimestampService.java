package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.JOBTimestampEntity;

/**
 * 
 * 定时任务时间戳服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-7 上午10:37:53
 */
public interface IJOBTimestampService extends IService {

	/**
	 * 
	 * 新增JOB时间戳
	 * 
	 * @Title: addJOBTimestamp
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 上午10:39:26
	 * @param @param code
	 * @param @param timestamp
	 * @param @param note
	 * @param @return 设定文件
	 * @return JOBTimestampEntity 返回类型
	 * @throws
	 */
	JOBTimestampEntity addJOBTimestamp(String code, Date timestamp, String note);

	/**
	 * 
	 * 根据CODE取出时间戳
	 * 
	 * @Title: getJOBTimestamp
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 上午10:40:23
	 * @param @param code
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	Date getJOBTimestamp(String code);

	/**
	 * 
	 * 更新时间戳
	 * 
	 * @Title: updateJOBTimestamp
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 上午10:41:27
	 * @param @param code
	 * @param @param timestamp 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	void updateJOBTimestamp(String code, Date timestamp);
}
