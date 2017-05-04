package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;

/**
 * 临时租车dao
 * @author ChinaSoft-HeHaiSu
 * @date 2014-7-25
 */
public interface ITempRentalDao {
	/**
	 * 登陆部门为营业部
	 * 通过时间段分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> querySaleDepartTempRentalWayBillByDate(Map<Object,Object> args, int start, int limit);
	/**
	 * 登陆部门为驻地营业部
	 * 通过时间段分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> queryStationSaleDepartTempRentalWayBillByDate(Map<Object,Object> args, int start, int limit);
	/**
	 * 登陆部门为车队
	 * 通过时间段分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> queryMotorcadeTempRentalWayBillByDate(Map<Object,Object> args, int start, int limit);

	/**
	 * 登陆部门为营业部
	 * 通过单号分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> querySaleDepartTempRentalWayBillByBillNos(Map<Object, Object> args, int start, int limit);
	/**
	 * 登陆部门为驻地营业部
	 * 通过单号分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> queryStationSaleDepartTempRentalWayBillByBillNos(Map<Object, Object> args, int start, int limit);
	/**
	 * 登陆部门为车队
	 * 通过单号分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> queryMotorcadeTempRentalWayBillByBillNos(Map<Object, Object> args, int start, int limit);
	
	/**
	 * 登陆部门为营业部或驻地营业部
	 * 通过时间段分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> querySaleDepartTempRentalDeliverBillByDate (Map<Object,Object> args, int start, int limit);
	/**
	 * 登陆部门为车队
	 * 通过时间段分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> queryMotorcadeTempRentalDeliverBillByDate (Map<Object,Object> args, int start, int limit);

	/**
	 * 登陆部门为营业部含驻地营业部
	 * 通过单号分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> querySaleDepartTempRentalDeliverBillByBillNos(Map<Object, Object> args, int start, int limit);

	/**
	 * 登陆部门为车队
	 * 通过单号分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	List<RentalMarkEntity> queryMotorcadeTempRentalDeliverBillByBillNos(Map<Object, Object> args, int start, int limit);
	/**
	 * 通过时间段查询临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	Long countTempRentalWayBillByDate (Map<Object, Object> args);
	/**
	 * 通过单号查询临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	Long countTempRentalWayBillByBillNos (Map<Object, Object> args);
	
	/**
	 * 通过单号查询临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	Long countWayBillByBillNos (Map<Object, Object> args);
	/**
	 * 通过时间段查临时租车派送单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	Long countTempRentalDeliverBillByDate (Map<Object, Object> args);
	
	/**
	 * 通过单号查临时租车派送单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	Long countTempRentalDeliverBillByBillNos (Map<Object, Object> args);
	
	List<RentalMarkEntity> querySaleBillByBill(Map<Object, Object> maps,
			int start, int limit);
}
