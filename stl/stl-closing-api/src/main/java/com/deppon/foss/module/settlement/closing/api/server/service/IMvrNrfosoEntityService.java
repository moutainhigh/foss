package com.deppon.foss.module.settlement.closing.api.server.service;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityResultDto;


/**
 * 01特殊业务始发月报表Service接口
 * @author ddw
 * @date 2013-11-08
 */
public interface IMvrNrfosoEntityService extends IService {
	
	/**
     * 根据条件查询始发应收列表（分页）
     * @author ddw
     * @date 2013-11-08
     */
	MvrNrfosoEntityResultDto selectByConditionsAndPage(MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto,int start, int limit);
    
    /**
     * 根据条件查询始发应收列表（不分页，提供给导出使用）
     * @author ddw
     * @date 2013-11-08
     */
	MvrNrfosoEntityResultDto selectByConditions(MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto);
}
