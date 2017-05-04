package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto;

/**
 * 操作日志dao
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-10 下午7:05:19
 */
public interface IOperatingLogQueryDao {

	/**
	 * 根据日期分页查询日志
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午4:42:36
	 */
	List<OperatingLogEntity> queryOperatingLogByDateAndPage(OperatingLogQueryDto dto,int start, int limit);
	
	/**
	 * 根据日期查询日志
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午4:42:36
	 */
	List<OperatingLogEntity> queryOperatingLogByDate(OperatingLogQueryDto dto);

	/**
	 * 根据日期查询日志总条数
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午4:44:38
	 */
	Long queryTotalRowsbyDate(OperatingLogQueryDto dto);
}
