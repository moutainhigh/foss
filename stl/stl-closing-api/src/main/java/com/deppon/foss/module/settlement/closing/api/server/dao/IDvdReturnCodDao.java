package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdReturnCodEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdReturnCodDto;

/**
 * 退代收货款明细报表接口
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:15:32
 * @since
 * @version
 */
public interface IDvdReturnCodDao{
	
	/**
	 * 根据多个参数查询退代收货款明细信息(分页）
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	List<DvdReturnCodEntity> queryDvdReturnCodByConditions(DvdReturnCodDto dvdReturnCodDto,int start,int limit);
	
	/**
	 * 根据多个参数查询退代收货款明细信息
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	List<DvdReturnCodEntity> queryDvdReturnCodByConditions(DvdReturnCodDto dvdReturnCodDto);
	
	/**
	 * 根据多个参数查询退代收货款明细信息总和
	 * @author foss-pengzhen
	 * @date 2013-4-10 下午3:45:22
	 * @param dvdReturnCodDto
	 * @return
	 * @see
	 */
	Long queryDvdReturnCodByConditionCount(DvdReturnCodDto dvdReturnCodDto);
}
