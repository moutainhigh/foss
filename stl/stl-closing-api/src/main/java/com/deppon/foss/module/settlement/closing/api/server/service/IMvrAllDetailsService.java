package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto;

public interface IMvrAllDetailsService {
	/**
	 * 报表查询
	 * @param dto
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<VoucherDetailResultDto> selectByConditions(VoucherDetailsDto dto,
			int offset, int limit);

	/**
	 * 报表总数
	 * @param dto
	 * @return
	 */
	VoucherDetailResultDto queryTotalCounts(VoucherDetailsDto dto);
	
	/**
	 * 往来报表查询
	 * @param dto
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<VoucherDetailResultDto> selectEgByConditions(VoucherDetailsDto dto,
			int offset, int limit);

	/**
	 * 往来报表总数
	 * @param dto
	 * @return
	 */
	VoucherDetailResultDto queryEgTotalCounts(VoucherDetailsDto dto);
}
