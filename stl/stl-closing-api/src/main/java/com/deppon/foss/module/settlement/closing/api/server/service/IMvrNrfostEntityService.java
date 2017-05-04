package com.deppon.foss.module.settlement.closing.api.server.service;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityResultDto;


/**
 * 始发应收月报Service接口
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午4:28:55
 */
public interface IMvrNrfostEntityService extends IService {
	
	/**
     * 根据条件查询始发应收列表（分页）
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:53
     */
	MvrNrfostEntityResultDto selectByConditionsAndPage(MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto,int start, int limit);
    
    /**
     * 根据条件查询始发应收列表（不分页，提供给导出使用）
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:53
     */
	MvrNrfostEntityResultDto selectByConditions(MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto);
}
