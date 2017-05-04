package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpDqpaEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpDqpaQueryDto;

/**
 * 合伙人德启代付月报表service
 * 
 * @author gpz
 * @date 2016年3月21日
 */
public interface IMvrPtpDqpaService extends IService {

	/**
	 * 分页查询合伙人德启代付月报表数据
	 * 
	 * @author gpz
	 * @date 2016年3月21日
	 * @param queryDto 合伙人德启代付月报表查询条件dto
	 * @return List<MvrPtpDqpaEntity>  合伙人德启代付月报表数据集合
	 */
	List<MvrPtpDqpaEntity> queryMvrPtpDqpaByParams(MvrPtpDqpaQueryDto queryDto,
			int start, int limit);

	/**
	 * 查询合伙人德启代付月报表数据总数
	 * 
	 * @author gpz
	 * @date 2016年3月21日
	 * @param queryDto 合伙人德启代付月报表查询条件dto
	 * @return Long 合伙人德启代付月报表数据总数
	 */
	Long queryMvrPtpDqpaEntityTotalCount(MvrPtpDqpaQueryDto queryDto);

	
}
