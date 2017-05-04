package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;


import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto;

/**
 * 代收货款综合查询
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 下午4:01:57
 */
public interface ICODCompositeQueryDao {
	
	
	/**
	 * 按运单号进行查询(分页查询)
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:02:56
	 */
	List<CODCompositeGridDto> queryByWaybillNo(CODCompositeQueryDto dto);
	
	/**
	 * 按合并编号进行(分页查询)
	 * 
	 * @date 2012-10-30 下午4:17:21
	 */
	List<CODCompositeGridDto> queryByMergeCode(CODCompositeQueryDto dto); 
	
	/**
	 * 按开单时间进行查询（查询总行数）
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:05:17
	 */
	int queryTotalRowsByBizDate(CODCompositeQueryDto dto);
	
	/**
	 * 按开单时间进行查询(分页查询)
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:05:17
	 */
	List<CODCompositeGridDto> queryByBizDate(CODCompositeQueryDto dto,int offset, int limit);
	
	
	/**
	 * 按支付日期进行查询(查询总行数)
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:06:05
	 */
	int queryTotalRowsByPayDate( CODCompositeQueryDto dto);
	
	/**
	 * 按支付日期进行查询(分页进行查询)
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:06:05
	 */
	List<CODCompositeGridDto> queryByPayDate( CODCompositeQueryDto dto,int offset, int limit);
	
	/**
	 * 按批次号查询
	 * @author foss-guxinhua
	 * @date 2013-5-7 下午6:23:17
	 * @param dto
	 * @return
	 */
	List<CODCompositeGridDto> queryCompositeByBatchNo(CODCompositeQueryDto dto) ;
	
	/**
	 * 按签收日期进行查询
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:06:38
	 */
	int queryTotalRowsSignDate( CODCompositeQueryDto dto);
	
	
	/**
	 * 按签收日期进行查询
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:06:38
	 */
	List<CODCompositeGridDto> queryBySignDate(CODCompositeQueryDto dto,int offset, int limit);
	
	/**
	 * 按汇款成功时间查询行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午3:29:07
	 */
	int queryTotalRowsByRefundSuccessDate(CODCompositeQueryDto dto);

	/**
	 * 按汇款成功时间进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午3:28:37
	 */
	List<CODCompositeGridDto> queryByRefundSuccessDate(CODCompositeQueryDto dto,
			int offset, int limit);

}
