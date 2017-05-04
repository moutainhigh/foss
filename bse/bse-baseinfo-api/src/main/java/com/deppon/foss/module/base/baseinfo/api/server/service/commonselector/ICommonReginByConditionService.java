package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;

/**
 * 公共查询选择器--(多选)行政区域Service
 * @author 130346-foss-lifanghong
 * @date 2013-06-24 
 */
public interface ICommonReginByConditionService {
	 /**
     *根据entity查询 行政区域
     * @author  130346-foss-lifanghong
     * @param 
     * @date 2013-06-24 
     * @return 
     */
	List<AdministrativeRegionsEntity> commonReginByCondition(AdministrativeRegionsEntity administrativeRegionsEntity,int start, int limit);
	
    /**
     * 根据entity查询 行政区域记录总数,用于分页
     * 
     * @author 130346-foss-lifanghong
     * @date 2013-06-24 
     */
    long queryReginCountByCondition(AdministrativeRegionsEntity administrativeRegionsEntity);

}
