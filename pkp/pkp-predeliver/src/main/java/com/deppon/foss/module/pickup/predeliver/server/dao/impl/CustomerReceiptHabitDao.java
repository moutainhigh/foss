package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.ICustomerReceiptHabitDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptHabitVo;

/** 
 * @ClassName: CustomerReceiptHabitDao 
 * @Description: 客户收货习惯dao 实现 
 * @author 237982-foss-fangwenjun 
 * @date 2015-3-31 上午9:46:31 
 *  
 */
public class CustomerReceiptHabitDao extends iBatis3DaoImpl implements ICustomerReceiptHabitDao{

	/** 
	 *  收货习惯命名空间 
	 */ 
	private static final String RECEIPTHABIT_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntityMapper.";

	/**
	 * @Title: insertReceiptHabit
	 * @Description: 插入收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 受影响的行数
	 */
	@Override
	public int insertOrUpdateReceiptHabit(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		// 插入数据库表
	    return super.getSqlSession().insert(RECEIPTHABIT_NAMESPACE + "insertOrUpdate", customerReceiptHabitEntity);
	}

	/**
	 * @Title: updateReceiptHabit
	 * @Description: 修改收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 受影响的行数
	 */
	@Override
	public int updateReceiptHabit(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		// 更新数据库表
		return super.getSqlSession().update(RECEIPTHABIT_NAMESPACE + "updateByPrimaryKey", customerReceiptHabitEntity);
	}
	
	/**
	 * @Title: deleteByIdAndOrgCode
	 * @Description: 根据Id和部门编码删除收货习惯
	 * @param idOrgCode 收货的习惯的Id和部门编码
	 * @return 受影响的行数
	 */
	@Override
	public int deleteByIdAndOrgCode(Map<String,String> idOrgCode) {
		// 删除数据库表
		return super.getSqlSession().delete(RECEIPTHABIT_NAMESPACE + "deleteByPrimaryKey", idOrgCode);
	}

	/**
	 * @Title: selectListByParam
	 * @Description: 根据收货习惯值对象的属性值分页查询收货习惯
	 * @param customerReceiptHabitVo 收货习惯值对象
	 * @return 当前页收货习惯集合
	 */
	@Override
	public List<CustomerReceiptHabitEntity> selectListByParam(
			CustomerReceiptHabitVo customerReceiptHabitVo, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		// 查询数据库表 并分页
		return super.getSqlSession().selectList(RECEIPTHABIT_NAMESPACE + "selectListByParam", customerReceiptHabitVo, rowBounds);
	}

	/**
	 * @Title: selectOneByParam
	 * @Description: 根据收货习惯对象的属性值查询一个收货习惯
	 * @param CustomerReceiptHabitEntity 收货习惯对象
	 * @return 收货习惯对象
	 */
	@Override
	public CustomerReceiptHabitEntity selectOneByParam(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		// 查询数据库表 
		return (CustomerReceiptHabitEntity) super.getSqlSession().selectOne(RECEIPTHABIT_NAMESPACE + "selectOneByParam",  customerReceiptHabitEntity);
	}

	/**
	 * @Title: selectCountByParam
	 * @Description: 根据收货习惯值对象的属性值查询收货习惯总数
	 * @param customerReceiptHabitVo
	 * @return 查询收货习惯总数
	 */
	@Override
	public Long selectCountByParam(CustomerReceiptHabitVo customerReceiptHabitVo) {
		return (Long) super.getSqlSession().selectOne(RECEIPTHABIT_NAMESPACE + "selectCountByParam", customerReceiptHabitVo);
	}

	/**
	 * @Title: deleteByIdsAndOrgCode
	 * @Description: 根据多个Id和部门编码删除收货习惯
	 * @param idsOrgCode 收货的习惯的多个Id和部门编码
	 * @return 受影响的行数
	 */
	@Override
	public int deleteByIdsAndOrgCode(Map<String, Object> idsOrgCode) {
		return super.getSqlSession().delete(RECEIPTHABIT_NAMESPACE + "deleteByPrimaryKeys", idsOrgCode);
	}

	/**
	 * @Title: selectListByParam
	 * @Description: 根据收货习惯值对象的属性值查询收货习惯
	 * @param customerReceiptHabitVo 收货习惯值对象
	 * @return 收货习惯集合
	 */
	@Override
	public List<CustomerReceiptHabitEntity> selectListByParam(
			CustomerReceiptHabitVo customerReceiptHabitVo) {
		return super.getSqlSession().selectList(RECEIPTHABIT_NAMESPACE + "selectListByParam", customerReceiptHabitVo);
	}

	@Override
	public int insertOneReceiptHabit(
			CustomerReceiptHabitEntity customerReceiptHabitEntity) {
		return super.getSqlSession().insert(RECEIPTHABIT_NAMESPACE + "insertOne", customerReceiptHabitEntity);
	}

}
