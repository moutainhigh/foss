package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityTotalDto;

/**
 * 始发应收月报Dao接口
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:14:51
 */
public interface IMvrNrfontEntityDao {

    /**
     * 根据条件查询始发应收列表(分页)
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:53
     */
    List<MvrNrfontEntity> selectByConditionsAndPage(MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto,int start, int limit);
    
    /**
     * 根据条件查询始发应收列表（不分页）
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:53
     */
    List<MvrNrfontEntity> selectByConditions(MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto);
    
    /**
     * 根据条件查询始发应收统计信息
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:56
     */
    MvrNrfontEntityTotalDto selectTotalByConditions(MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto);

}