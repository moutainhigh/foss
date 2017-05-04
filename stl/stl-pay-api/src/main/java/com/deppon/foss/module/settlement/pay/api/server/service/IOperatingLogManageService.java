package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogResultDto;


/**
 * 操作日志service接口
 * @author foss-qiaolifeng
 * @date 2012-12-10 下午4:45:51
 */
public interface IOperatingLogManageService extends IService {

	/**
	 * 根据日期查询日志
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午4:42:36
	 */
	List<OperatingLogResultDto> queryOperatingLogByDate(OperatingLogQueryDto dto,CurrentInfo cInfo);
	
	/**
	 * 根据日期分页查询日志
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午4:42:36
	 */
	List<OperatingLogResultDto> queryOperatingLogByDateAndPage(OperatingLogQueryDto dto,int start, int limit,CurrentInfo cInfo);
	
	/**
	 * 根据日期查询日志总条数
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午4:44:38
	 */
	Long queryTotalRowsbyDate(OperatingLogQueryDto dto,CurrentInfo cInfo);
	
}
