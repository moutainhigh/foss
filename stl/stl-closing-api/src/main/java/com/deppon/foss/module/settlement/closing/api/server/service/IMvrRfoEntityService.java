package com.deppon.foss.module.settlement.closing.api.server.service;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityResultDto;


/**
 * 始发应收月报Service接口
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午4:28:55
 */
public interface IMvrRfoEntityService extends IService {
	
	/**
     * 根据条件查询始发应收列表（分页）
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:53
     */
	MvrRfoEntityResultDto selectByConditionsAndPage(MvrRfoEntityQueryDto mvrRfoEntityQueryDto,int start, int limit);
    
    /**
     * 根据条件查询始发应收列表（不分页，提供给导出使用）
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:53
     */
	MvrRfoEntityResultDto selectByConditions(MvrRfoEntityQueryDto mvrRfoEntityQueryDto);
}
