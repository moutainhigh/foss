package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;


import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailsDto;


/**
 * 合伙人日报表明细查询dao
 * @author 311396
 * @date 2016-3-22 下午4:30:56
 */
public interface IMvrPtpDetailDao {

	
    /**
     * 报表查询
     *
     * @author 311396
     * @date 2016-3-22 下午4:30:56
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    List<MvrPtpAllDetailResultDto> selectByConditions(MvrPtpAllDetailsDto dto,int offset,int limit);
    
   
    /**
     * 查询事发月报报表总条数
     *
     * @author 311396
     * @date 2016-3-22 下午4:30:56
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    MvrPtpAllDetailResultDto queryTotalCounts(MvrPtpAllDetailsDto dto);
    
}
