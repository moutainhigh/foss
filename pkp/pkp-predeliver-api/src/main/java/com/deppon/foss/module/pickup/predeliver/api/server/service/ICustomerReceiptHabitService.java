package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptHabitVo;

/** 
 * @ClassName: ICustomerReceiptHabitService 
 * @Description: 客户收货习惯service接口 
 * @author 237982-foss-fangwenjun 
 * @date 2015-3-30 上午8:44:31 
 *  
 */
public interface ICustomerReceiptHabitService extends IService {

	/**
	 * @Title: insertReceiptHabit
	 * @Description: 插入或更新收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 结果为 1:表示执行成功  为-1：表示传入对象里字段有null值或空值
	 */
	int insertCustomerReceiptHabit(CustomerReceiptHabitEntity customerReceiptHabitEntity);
	
	/**
	 * @Title: insertOneReceiptHabit
	 * @Description: 添加收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 结果为 1:表示执行成功  为-1：表示传入对象里字段有null值或空值
	 */
	int insertOneReceiptHabit(CustomerReceiptHabitEntity customerReceiptHabitEntity);
	
	/**
	 * @Title: deleteById
	 * @Description: 根据Id删除收货习惯
	 * @param id 收货的习惯的Id
	 * @return 受影响的行数
	 */
	int deleteReceiptHabitById(String id);
	
	/**
	 * @Title: deleteByIdsAndOrgCode
	 * @Description: 根据多个Id删除收货习惯
	 * @param ids 收货的习惯的多个Id用","隔开
	 * @return 受影响的行数
	 */
	int deleteReceiptHabitByIds(String[] ids);
	
	/**
	 * @Title: updateReceiptHabit
	 * @Description: 修改收货习惯
	 * @param customerReceiptHabitEntity 客户收货习惯对象
	 * @return 结果为 1:表示执行成功  为-1：表示传入对象里字段有null值或空值
	 */
	int updateReceiptHabit(CustomerReceiptHabitEntity  customerReceiptHabitEntity);
	
	/**
	 * @Title: queryReceiptHabitList
	 * @Description: 根据收货习惯值对象的属性值分页查询收货习惯
	 * @param customerReceiptHabitVo 收货习惯值(vo)对象
	 * @return 收货习惯集合
	 */
	List<CustomerReceiptHabitEntity> selectReceiptHabitList(CustomerReceiptHabitVo customerReceiptHabitVo, int start, int limit);
	
	/**
	 * @Title: selectReceiptHabitList
	 * @Description: 导出查询出的收货习惯(Excel)
	 * @param customerReceiptHabitVo 收货习惯值(vo)对象
	 * @return 收货习惯(Excel)的输入流
	 */
	InputStream selectReceiptHabitList(CustomerReceiptHabitVo customerReceiptHabitVo);
	
	/**
	 * @Title: selectOneByParam
	 * @Description: 根据收货习惯对象的属性值查询一个收货习惯
	 * @param CustomerReceiptHabitEntity 收货习惯对象
	 * @return 收货习惯对象
	 */
	CustomerReceiptHabitEntity selectReceiptHabitByParam(CustomerReceiptHabitEntity customerReceiptHabitEntity);
	
	/**
	 * @Title: queryReceiptHabitCount
	 * @Description: 根据收货习惯值对象的属性值查询收货习惯总数
	 * @param customerReceiptHabitVo
	 * @return 查询收货习惯总数
	 */
	Long selectReceiptHabitCount(CustomerReceiptHabitVo customerReceiptHabitVo);
}
