package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.settlement.common.api.server.dao.IJOBTimestampDao;
import com.deppon.foss.module.settlement.common.api.server.service.IJOBTimestampService;
import com.deppon.foss.module.settlement.common.api.shared.domain.JOBTimestampEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 
 * 定时任务时间戳服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-7 下午3:31:55
 */
public class JOBTimestampService implements IJOBTimestampService {

	/**
	 * 定时任务时间戳DAO
	 */
	private IJOBTimestampDao jobTimestampDao;

	/**
	 * 新增
	 * @Title: addJOBTimestamp
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 下午3:34:22
	 * @param code
	 * @param timestamp
	 * @param note
	 * @return JOBTimestampEntity
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IJOBTimestampService#addJOBTimestamp(java.lang.String,
	 *      java.util.Date, java.lang.String)
	 */
	@Override
	public JOBTimestampEntity addJOBTimestamp(String code, Date timestamp,
			String note) {

		// 定时任务时间戳编码不能为空
		if (StringUtils.isEmpty(code)) {
			throw new SettlementException("定时任务时间戳编码不能为空");
		}

		// 定时任务执行时间不能为空
		if (timestamp == null) {
			throw new SettlementException("定时任务执行时间不能为空");
		}

		// 构建entity
		JOBTimestampEntity entity = new JOBTimestampEntity();
		entity.setCode(code);
		entity.setTimestamp(timestamp);
		entity.setNote(note);

		// 新增一条记录
		jobTimestampDao.addJOBTimestamp(entity);

		return entity;
	}

	/**
	 * 
	 * 根据编码获取时间戳
	 * 
	 * @Title: getJOBTimestamp
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 下午3:41:04
	 * @param code
	 * @return
	 * @throws
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IJOBTimestampService#getJOBTimestamp(java.lang.String)
	 */
	@Override
	public Date getJOBTimestamp(String code) {

		// 定时任务时间戳编码不能为空
		if (StringUtils.isEmpty(code)) {
			throw new SettlementException("定时任务时间戳编码不能为空");
		}

		return jobTimestampDao.getJOBTimestamp(code);
	}

	/**
	 * 
	 * 更新时间戳
	 * 
	 * @Title: updateJOBTimestamp
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 下午3:41:28
	 * @param code
	 * @param timestamp
	 * @throws
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IJOBTimestampService#updateJOBTimestamp(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public void updateJOBTimestamp(String code, Date timestamp) {
		// 定时任务时间戳编码不能为空
		if (StringUtils.isEmpty(code)) {
			throw new SettlementException("定时任务时间戳编码不能为空");
		}

		// 定时任务执行时间不能为空
		if (timestamp == null) {
			throw new SettlementException("定时任务执行时间不能为空");
		}

		//构建更新实体
		JOBTimestampEntity entity = new JOBTimestampEntity();
		entity.setCode(code);
		entity.setTimestamp(timestamp);
		
		//更新执行时间
		jobTimestampDao.updateJOBTimestamp(entity);
	}

	public void setJobTimestampDao(IJOBTimestampDao jobTimestampDao) {
		this.jobTimestampDao = jobTimestampDao;
	}
}
