package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfosoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityTotalDto;

/**
 * 01特殊业务始发月报表Dao接口
 * @author ddw
 * @date 2013-11-08
 */
public interface IMvrNrfosoEntityDao {

    /**
     * 根据条件查询始发应收列表(分页)
     * @author ddw
     * @date 2013-11-08
     */
    List<MvrNrfosoEntity> selectByConditionsAndPage(MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto,int start, int limit);
    
    /**
     * 根据条件查询始发应收列表（不分页）
     * @author ddw
     * @date 2013-11-08
     */
    List<MvrNrfosoEntity> selectByConditions(MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto);
    
    /**
     * 根据条件查询始发应收统计信息
     * @author ddw
     * @date 2013-11-08
     */
    MvrNrfosoEntityTotalDto selectTotalByConditions(MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto);

}