package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.Date;

import com.deppon.foss.module.settlement.common.api.shared.domain.JOBTimestampEntity;

/**
 * 
 * 定时任务时间戳服务
 * @author 046644-foss-zengbinwen
 * @date 2013-4-7 下午2:34:27
 */
public interface IJOBTimestampDao {


	/**
	 * 
	 * 新增定时任务时间戳配置
	 * @Title: addJOBTimestamp 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 下午2:35:43
	 * @param @param entity
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int addJOBTimestamp(JOBTimestampEntity entity);

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
	 * @return int 返回类型
	 * @throws
	 */
	int updateJOBTimestamp(JOBTimestampEntity entity);

}
