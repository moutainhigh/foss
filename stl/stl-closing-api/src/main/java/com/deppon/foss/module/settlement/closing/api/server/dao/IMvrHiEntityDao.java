package com.deppon.foss.module.settlement.closing.api.server.dao;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrHiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrHiDto;

import java.util.List;

/**
 * 家装月报表DAO
 * @author  302307
 * @date 2015年12月16日9:01:17
 */
public interface IMvrHiEntityDao {
    /**
     *
     * 查询家装月报表列表
     *
     * @author 302307
     * @date 2015年12月16日9:15:25
     */
    List<MvrHiEntity> queryMvrHi(MvrHiDto dto, int start, int limit);

    /**
     *
     * 查询家装月报表汇总
     *
     * @author 302307
     * @date 2015年12月16日9:15:16
     */
    MvrHiDto queryMvrHiTotal(MvrHiDto dto);

}
