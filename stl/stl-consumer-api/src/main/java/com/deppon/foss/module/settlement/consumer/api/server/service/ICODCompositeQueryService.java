package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODCompositeConditionVo;

/**
 * 
 * 代收货款综合查询服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 下午3:50:19
 */
public interface ICODCompositeQueryService {

	/**
	 * 按运单单号查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午3:53:42
	 */
	List<CODCompositeGridDto> queryByWaybillNo(CODCompositeConditionVo vo,CurrentInfo currentInfo);

	/**
	 * 通过分页的方式查询
	 * 
	 * @date 2014-10-30 下午5:40:46
	 */
	List<CODCompositeGridDto> queryByMergeCode(CODCompositeConditionVo vo,CurrentInfo currentInfo);
	
	/**
	 * 按开单时间进行查询（查询总行数）
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:05:17
	 */
	int queryTotalRowsByBizDate(CODCompositeConditionVo queryVO,CurrentInfo currentInfo);
	
	/**
	 * 按开单时间进行查询(分页查询)
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:05:17
	 */
	List<CODCompositeGridDto> queryByBizDate(CODCompositeConditionVo queryVO,int offset, int limit,CurrentInfo currentInfo);
	
	/**
	 * 按批次号查询
	 * @author foss-guxinhua
	 * @date 2013-5-7 下午6:17:10
	 * @param queryVO
	 * @param currentInfo
	 * @return
	 */
	List<CODCompositeGridDto> queryCompositeByBatchNo(CODCompositeConditionVo queryVO,CurrentInfo currentInfo) ;
	
	/**
	 * 按支付日期进行查询(查询总行数)
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:06:05
	 */
	int queryTotalRowsByPayDate( CODCompositeConditionVo queryVO,CurrentInfo currentInfo);
	
	/**
	 * 按支付日期进行查询(分页进行查询)
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:06:05
	 */
	List<CODCompositeGridDto> queryByPayDate( CODCompositeConditionVo queryVO,int offset, int limit,CurrentInfo currentInfo);
	
	
	/**
	 * 按签收日期进行查询
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:06:38
	 */
	int queryTotalRowsBySignDate( CODCompositeConditionVo queryVO,CurrentInfo currentInfo);
	
	
	/**
	 * 按签收日期进行查询
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:06:38
	 */
	List<CODCompositeGridDto> queryBySignDate(CODCompositeConditionVo queryVO,int offset, int limit,CurrentInfo currentInfo);
	
	
	/**
	 * 按运单号查询代收货款操作日志
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-13 下午6:41:20
	 */
	List<CODLogEntity> queryCodLogByWaybill(String waybillNo);
	
	/**
	 * 按汇款成功日期查询总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午4:25:56
	 */
	int queryTotalRowsByRefundSuccessDate(CODCompositeConditionVo queryVO,CurrentInfo currentInfo);

	/**
	 * 按汇款成功日期分页查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午4:32:24
	 */
	List<CODCompositeGridDto> queryByRefundSuccessDate(
			CODCompositeConditionVo queryVO, int offset, int limit,CurrentInfo currentInfo);

}
