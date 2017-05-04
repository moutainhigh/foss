package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ITempRentalDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;

/**
 * 临时租车dao
 * 
 * @author ChinaSoft-HeHaiSu
 * @date 2014-7-25
 */
public class TempRentalDao extends iBatis3DaoImpl implements ITempRentalDao {
	
	private static final String NAMESPACE = "foss.pkp.TempRentalEntityMapper.";
	
	/**
	 * 登陆部门为营业部
	 * 通过时间段分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> querySaleDepartTempRentalWayBillByDate(Map<Object,Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);  
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByDate_1",
				args, rowBounds);
	}
	/**
	 * 登陆部门为驻地营业部
	 * 通过时间段分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> queryStationSaleDepartTempRentalWayBillByDate(Map<Object,Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);  
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByDate_2",
				args, rowBounds);
	}
	/**
	 * 登陆部门为车队
	 * 通过时间段分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> queryMotorcadeTempRentalWayBillByDate(Map<Object,Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);  
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByDate_3_4",
				args, rowBounds);
	}

	/**
	 * 登陆部门为营业部
	 * 通过单号分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> querySaleDepartTempRentalWayBillByBillNos(Map<Object, Object> args, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByBills_1",
				args, rowBounds);
	}
	/**
	 * 登陆部门为驻地营业部
	 * 通过单号分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> queryStationSaleDepartTempRentalWayBillByBillNos(Map<Object, Object> args, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByBills_2",
				args, rowBounds);
	}
	/**
	 * 登陆部门为车队
	 * 通过单号分页查询临时租车运单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> queryMotorcadeTempRentalWayBillByBillNos(Map<Object, Object> args, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(NAMESPACE + "selectWayBillByBills_3_4",
				args, rowBounds);
	}
	
	/**
	 * 登陆部门为营业部或驻地营业部
	 * 通过时间段分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> querySaleDepartTempRentalDeliverBillByDate (Map<Object,Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByDeliverByDate_1_2", args, rowBounds);
	}
	/**
	 * 登陆部门为车队
	 * 通过时间段分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> queryMotorcadeTempRentalDeliverBillByDate (Map<Object,Object> args, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByDeliverByDate_3_4", args, rowBounds);
	}

	/**
	 * 通过单号分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> querySaleDepartTempRentalDeliverBillByBillNos(Map<Object, Object> args, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByDeliverBills_1_2", args, rowBounds);
	}
	
	/**
	 * 登陆部门为车队
	 * 通过单号分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> queryMotorcadeTempRentalDeliverBillByBillNos(Map<Object, Object> args, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit); 
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByDeliverBills_3_4", args, rowBounds);
	}

	/**
	 * 通过时间段查询临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countTempRentalWayBillByDate (Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalWayBillByDate", args);
	}
	
	/**
	 * 通过单号查询临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countTempRentalWayBillByBillNos (Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalWayBillByBillNos", args);
	}

	/**
	 * 通过时间段查临时租车派送单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countTempRentalDeliverBillByDate (Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalDeliverBillByDate", args);
	}
	
	/**
	 * 通过单号查临时租车派送单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countTempRentalDeliverBillByBillNos (Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalDeliverBillByBillNos", args);
	}
	
	
	/**
	 * 通过单号分页查询临时租车派送单数据
	 * @param tempRentalQueryDto
	 * @param args
	 * @return
	 */
	public List<RentalMarkEntity> querySaleBillByBill(Map<Object, Object> args,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectWaybillnoBills",args,rowBounds);
		
	}
	@Override
	public Long countWayBillByBillNos(Map<Object, Object> args) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getWayBillByBillNos", args);
	}

	
}