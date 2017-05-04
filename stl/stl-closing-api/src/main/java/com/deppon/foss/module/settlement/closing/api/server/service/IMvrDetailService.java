package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto;

/**
 * TODO（描述类的职责）
 * 
 * @author 045738-foss-maojianqiang
 * @date 2013-4-2 下午7:36:31
 */
public interface IMvrDetailService extends IService {
	/**
	 * 报表查询dao
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:36:50
	 * @param dto
	 *            isPaging--是否分页
	 * @return
	 */
	List<VoucherDetailResultDto> selectByConditions(VoucherDetailsDto dto,
			int offset, int limit);

	/**
	 * 查询始发到达往来报表总条数
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 * @param start
	 * @param limit
	 * @param dto
	 */
	VoucherDetailResultDto queryTotalCounts(VoucherDetailsDto dto);

}
