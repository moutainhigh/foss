package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.DvrReturnCodEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvrReturnCodDto;

/**
 * 退代收货款报表接口
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:15:32
 * @since
 * @version
 */
public interface IDvrReturnCodDao{
	
	/**
	 * 根据多个参数查询退代收货款信息(分页）
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	List<DvrReturnCodEntity> queryDvrReturnCodByConditions(DvrReturnCodDto dvrReturnCodDto,int start,int limit);
	
	/**
	 * 根据多个参数查询退代收货款信息
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	List<DvrReturnCodEntity> queryDvrReturnCodByConditions(DvrReturnCodDto dvrReturnCodDto);
	
	/**
	 * 根据多个参数查询退代收货款信息总和
	 * @author foss-pengzhen
	 * @date 2013-4-10 下午3:45:22
	 * @param dvrReturnCodDto
	 * @return
	 * @see
	 */
	Long queryDvrReturnCodByConditionCount(DvrReturnCodDto dvrReturnCodDto);
}
