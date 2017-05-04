package com.deppon.foss.module.settlement.closing.api.server.dao;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpsEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpsDto;

import java.util.List;

/**
 * 
 * 合伙人子奖罚特殊月报表DAO
 * 
 *  * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */
public interface IMvrPtpRpsDao {

    /**
     * 查询合伙人奖罚特殊月报表
     * @param dto
     * @return
     */
    Long queryMvrPtpRpsCount(MvrPtpRpsDto dto);

    /**
     * 查询合伙人奖罚特殊月报表集合
     * @param dto
     * @param offset
     * @param limit
     * @return
     */
    List<MvrPtpRpsEntity> queryMvrPtpRpsEntityList(MvrPtpRpsDto dto,int offset,int limit);

    /**
     * 导出合伙人奖罚特殊月报表
     * @param dto
     * @return
     */
    List<MvrPtpRpsEntity> exportMvrPtpPsc(MvrPtpRpsDto dto);


}
