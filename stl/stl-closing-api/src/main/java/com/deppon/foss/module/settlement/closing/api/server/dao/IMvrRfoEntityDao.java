package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityTotalDto;

/**
 * 始发应收月报Dao接口
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:14:51
 */
public interface IMvrRfoEntityDao {

    /**
     * 根据条件查询始发应收列表(分页)
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:53
     */
    List<MvrRfoEntity> selectByConditionsAndPage(MvrRfoEntityQueryDto mvrRfoEntityQueryDto,int start, int limit);
    
    /**
     * 根据条件查询始发应收列表（不分页）
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:53
     */
    List<MvrRfoEntity> selectByConditions(MvrRfoEntityQueryDto mvrRfoEntityQueryDto);
    
    /**
     * 根据条件查询始发应收统计信息
     * @author foss-qiaolifeng
     * @date 2013-3-6 下午3:14:56
     */
    MvrRfoEntityTotalDto selectTotalByConditions(MvrRfoEntityQueryDto mvrRfoEntityQueryDto);

}