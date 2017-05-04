package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrHiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrHiDto;

import java.util.List;

/**
 * 家装接口
 * @author 302307
 * @date 2015年12月16日9:12:04
 */
public interface IMvrHiService {

    /**
     *
     * 查询家装月报表列表
     *
     * @author 302307
     * @date 2015年12月16日9:12:04
     */
    List<MvrHiEntity> queryMvrHi(MvrHiDto dto, int offset, int limit);

    /**
     *
     * 查询家装月报表汇总
     *
     * @author 302307
     * @date 2015年12月16日9:12:04
     */
    MvrHiDto queryMvrHiTotal(MvrHiDto dto);
}
