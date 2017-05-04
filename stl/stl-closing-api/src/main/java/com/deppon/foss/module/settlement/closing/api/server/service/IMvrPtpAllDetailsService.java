package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailsDto;

public interface IMvrPtpAllDetailsService {
	/**
	 * 报表查询
	 * @param dto
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<MvrPtpAllDetailResultDto> selectByConditions(MvrPtpAllDetailsDto dto,
			int offset, int limit);

	/**
	 * 报表总数
	 * @param dto
	 * @return
	 */
	MvrPtpAllDetailResultDto queryTotalCounts(MvrPtpAllDetailsDto dto);
}
