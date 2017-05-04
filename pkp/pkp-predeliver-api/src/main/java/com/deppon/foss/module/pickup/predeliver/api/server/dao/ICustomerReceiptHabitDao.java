package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptHabitVo;

/** 
 * @ClassName: ICustomerReceiptHabitDao 
 * @Description: 客户收货习惯Dao 
 * @author 237982-foss-fangwenjun 
 * @date 2015-3-27 下午2:22:03 
 *  
 */
public interface ICustomerReceiptHabitDao {

	
	/**
	 * @Title: insertReceiptHabit
	 * @Description: 插入收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 受影响的行数
	 */
	int insertOrUpdateReceiptHabit(CustomerReceiptHabitEntity customerReceiptHabitEntity);
	
	/**
	 * @Title: insertOneReceiptHabit
	 * @Description: 添加收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 受影响的行数
	 */
	int insertOneReceiptHabit(CustomerReceiptHabitEntity customerReceiptHabitEntity);
	
	/**
	 * @Title: updateReceiptHabit
	 * @Description: 修改收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 受影响的行数
	 */
	int updateReceiptHabit(CustomerReceiptHabitEntity customerReceiptHabitEntity);
	
	/**
	 * @Title: deleteByIdAndOrgCode
	 * @Description: 根据Id和部门编码删除收货习惯
	 * @param idOrgCode 收货的习惯的Id和部门编码
	 * @return 受影响的行数
	 */
	int deleteByIdAndOrgCode(Map<String,String> idOrgCode);
	
	/**
	 * @Title: deleteByIdsAndOrgCode
	 * @Description: 根据多个Id和部门编码删除收货习惯
	 * @param idsOrgCode 收货的习惯的多个Id和部门编码
	 * @return 受影响的行数
	 */
	int deleteByIdsAndOrgCode(Map<String,Object> idsOrgCode);
	
	/**
	 * @Title: selectCountByParam
	 * @Description: 根据收货习惯值对象的属性值查询收货习惯总数
	 * @param customerReceiptHabitVo
	 * @return 查询收货习惯总数
	 */
	Long selectCountByParam(CustomerReceiptHabitVo customerReceiptHabitVo);
	
	/**
	 * @Title: selectListByParam
	 * @Description: 根据收货习惯值对象的属性值分页查询收货习惯
	 * @param customerReceiptHabitVo 收货习惯值对象
	 * @return 当前页收货习惯集合
	 */
	List<CustomerReceiptHabitEntity> selectListByParam(CustomerReceiptHabitVo customerReceiptHabitVo, int start, int limit);
	
	/**
	 * @Title: selectListByParam
	 * @Description: 根据收货习惯值对象的属性值查询收货习惯
	 * @param customerReceiptHabitVo 收货习惯值对象
	 * @return 收货习惯集合
	 */
	List<CustomerReceiptHabitEntity> selectListByParam(CustomerReceiptHabitVo customerReceiptHabitVo);
	
	/**
	 * @Title: selectOneByParam
	 * @Description: 根据收货习惯对象的属性值查询一个收货习惯
	 * @param CustomerReceiptHabitEntity 收货习惯对象
	 * @return 收货习惯对象
	 */
	CustomerReceiptHabitEntity selectOneByParam(CustomerReceiptHabitEntity customerReceiptHabitEntity);
}
