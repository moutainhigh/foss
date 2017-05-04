/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;

/**
 * 异步代办的请求dao
 * @author 026123-foss-lifengteng
 *
 */
public interface IPendingTodoDao {

    /**
     * 
     * <p>按需插入一条数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:29
     * @param record
     * @return
     * @see
     */
    int insert(PendingTodoEntity record);
    
    /**
     * 
     * <p>按需插入一条数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:29
     * @param record
     * @return
     * @see
     */
    int delete(PendingTodoEntity record);

	/**
	 * 查询所有的需要生成异步代办的请求
	 */
	List<PendingTodoEntity> queryAllTodo(int start, int limited);
	List<PendingTodoEntity> queryAllTodo();
	/**
	 * 更新 异步代办的请求
	 * @param pendingTodoEntity
	 */
	int update(PendingTodoEntity pendingTodoEntity);

	PendingTodoEntity queryPendingTodoByWaybillId(String waybillRfcId);

	List<PendingTodoEntity> queryPendingTodoByIds(List<String> pendTodoIdList);

	
}
