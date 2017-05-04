package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfonoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityTotalDto;

/**
 * 01普通业务始发月报表Dao接口
 * @author ddw
 * @date 2013-11-08
 */
public interface IMvrNrfonoEntityDao {

    /**
     * 根据条件查询始发应收列表(分页)
     * @author ddw
     * @date 2013-11-08
     */
    List<MvrNrfonoEntity> selectByConditionsAndPage(MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto,int start, int limit);
    
    /**
     * 根据条件查询始发应收列表（不分页）
     * @author ddw
     * @date 2013-11-08
     */
    List<MvrNrfonoEntity> selectByConditions(MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto);
    
    /**
     * 根据条件查询始发应收统计信息
     * @author ddw
     * @date 2013-11-08
     */
    MvrNrfonoEntityTotalDto selectTotalByConditions(MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto);

}