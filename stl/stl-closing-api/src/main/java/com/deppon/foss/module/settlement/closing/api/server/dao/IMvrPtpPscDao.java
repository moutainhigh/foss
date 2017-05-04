package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpPscEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpPscDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 
 * 合伙人子公司月报表DAO
 * 
 *  * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */
public interface IMvrPtpPscDao {

    /**
     * 查询子公司月报表总记录数
     * @param dto
     * @return
     * @throws SettlementException
     */
    Long queryMvrParentComCount(MvrPtpPscDto dto);

    /**
     * 查询子公司月报表集合
     * @param dto
     * @return
     * @throws SettlementException
     */
    List<MvrPtpPscEntity> queryMvrPtpPscList(MvrPtpPscDto dto,int offset,int limit);

    /**
     * 导出合伙人子公司月报表
     * @param dto
     * @return
     */
    List<MvrPtpPscEntity> exportMvrParentCom(MvrPtpPscDto dto);

}
