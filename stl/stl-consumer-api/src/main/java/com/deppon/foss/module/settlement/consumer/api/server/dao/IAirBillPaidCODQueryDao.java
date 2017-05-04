package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODQueryDto;

/**
 * 空运代收货款退款审核Dao
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-29 下午5:31:25
 */
public interface IAirBillPaidCODQueryDao {

	/**
	 * 按签收时间进行查询（查询总行数），合计金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:36
	 * @param
	 */
	AirBillPaidCODGridDto queryTotalRowsBySignDate(AirBillPaidCODQueryDto queryDto);

	/**
	 * 按签收时间进行查询(分页查询)
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:36
	 */
	List<AirBillPaidCODGridDto> queryBySignDate(
			AirBillPaidCODQueryDto queryDto, int offset, int limit);

	/**
	 * 按审核时间进行查询（只查询已经审核的代收货款记录的总行数），合计金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:36
	 */
	AirBillPaidCODGridDto queryTotalRowsByAuditDate(AirBillPaidCODQueryDto queryDto);

	/**
	 * 按审核时间进行查询（只查询已经审核的代收货款记录,分页查询）
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:36
	 */
	List<AirBillPaidCODGridDto> queryByAuditDate(
			AirBillPaidCODQueryDto queryDto, int offset, int limit);

	/**
	 * 按运单单号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 下午4:50:46
	 */
	List<AirBillPaidCODGridDto> queryByWaybillNos(
			AirBillPaidCODQueryDto queryDto);

}
