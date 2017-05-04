package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrWoodenEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrWoodenDto;

/**
 * 功能：代打木架dao
 * @author 045738
 */
public interface IMvrWoodenEntityDao {
	
    /**
     *根据传入查询代打木架月报--分页
     * @author 045738 
     * @param 
     * @date 2014-4-11 上午11:57:20
     * @return 
     */
    List<MvrWoodenEntity> selectMvrWoodenByParam(MvrWoodenDto dto,int start, int limit);
    
    /**
     * 根据传入查询代打木架月报条数
     * @author 045738 
     * @param 
     * @date 2014-4-11 上午11:57:20
     * @return 
     */
    MvrWoodenDto selectMvrWoodenByParamCount(MvrWoodenDto dto);
    
    
    
    /**
     * 根据传入查询代打木架月报表--不分页
     * @author 045738 
     * @date 2014-4-11 上午11:57:20
     * @param dto
     * @return
     */
    List<MvrWoodenEntity> selectMvrWoodenByParam(MvrWoodenDto dto);
	
}
