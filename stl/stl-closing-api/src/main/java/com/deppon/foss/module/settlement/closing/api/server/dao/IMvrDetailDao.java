package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto;


/**
 * 始发月报dao
 * @author foss-qiaolifeng
 * @date 2013-4-8 下午1:57:48
 */
public interface IMvrDetailDao {

	
    /**
     * 报表查询
     *
     * @author foss-qiaolifeng
     * @date 2013-4-8 下午1:57:49
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    List<VoucherDetailResultDto> selectByConditions(VoucherDetailsDto dto,int offset,int limit);
    
   
    /**
     * 查询事发月报报表总条数
     *
     * @author foss-qiaolifeng
     * @date 2013-4-8 下午1:57:51
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    VoucherDetailResultDto queryTotalCounts(VoucherDetailsDto dto);
    
    /**
     * 报表查询
     *
     * @author foss-njs
     * @date 2013-4-8 下午1:57:49
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    List<VoucherDetailResultDto> selectEgByConditions(VoucherDetailsDto dto,int offset,int limit);
    
   
    /**
     * 查询事发月报报表总条数
     *
     * @author foss-njs
     * @date 2013-4-8 下午1:57:51
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    VoucherDetailResultDto queryEgTotalCounts(VoucherDetailsDto dto);
    
}
