package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.LandBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.LandBillPaidCODQueryDto;

/**
 * 快递代理代收货款退款审核Dao
 * 
 * @author foss-guxinhua
 * @date 2012-10-29 下午5:31:25
 */
public interface ILandBillPaidCODQueryDao {

	/**
	 * 按签收时间进行查询（查询总行数），合计金额
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:36
	 * @param
	 */
	LandBillPaidCODGridDto queryTotalRowsBySignDate(LandBillPaidCODQueryDto queryDto);

	/**
	 * 按签收时间进行查询(分页查询)
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:36
	 */
	List<LandBillPaidCODGridDto> queryBySignDate(
			LandBillPaidCODQueryDto queryDto, int offset, int limit);

	/**
	 * 按审核时间进行查询（只查询已经审核的代收货款记录的总行数），合计金额
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:36
	 */
	LandBillPaidCODGridDto queryTotalRowsByAuditDate(LandBillPaidCODQueryDto queryDto);

	/**
	 * 按审核时间进行查询（只查询已经审核的代收货款记录,分页查询）
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:36
	 */
	List<LandBillPaidCODGridDto> queryByAuditDate(
			LandBillPaidCODQueryDto queryDto, int offset, int limit);

	/**
	 * 按运单单号进行查询
	 * 
	 * @author foss-guxinhua
	 * @date 2012-10-29 下午4:50:46
	 */
	List<LandBillPaidCODGridDto> queryByWaybillNos(
			LandBillPaidCODQueryDto queryDto);

}
