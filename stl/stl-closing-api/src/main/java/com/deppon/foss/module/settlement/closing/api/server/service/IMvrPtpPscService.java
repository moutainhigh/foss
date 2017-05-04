package com.deppon.foss.module.settlement.closing.api.server.service;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpPscEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpPscDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

import java.util.List;

/**
 *
 * 合伙人子公司月结报表
 *
 * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */
public interface IMvrPtpPscService  extends IService {

    /**
     * 查询子公司月报表总记录数
     * @param dto
     * @return
     * @throws SettlementException
     */
    Long queryMvrParentComCount(MvrPtpPscDto dto) throws SettlementException;

    /**
     * 查询子公司月报表集合
     * @param dto
     * @return
     * @throws SettlementException
     */
    List<MvrPtpPscEntity> queryMvrPtpPscList(MvrPtpPscDto dto,int offset,int limit) throws SettlementException;

    /**
     * 导出合伙人子公司月报表
     * @param dto
     * @return
     */
    List<MvrPtpPscEntity> exportMvrParentCom(MvrPtpPscDto dto) throws SettlementException;

}
